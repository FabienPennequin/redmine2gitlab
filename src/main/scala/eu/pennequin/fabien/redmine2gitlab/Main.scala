package eu.pennequin.fabien.redmine2gitlab

import play.api.Logger
import play.api.libs.ws._
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcWSClient, AhcWSClientConfig, StandaloneAhcWSClient}
import play.shaded.ahc.org.asynchttpclient.DefaultAsyncHttpClient

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.sslconfig.ssl.{SSLConfigSettings, SSLLooseConfig}
import eu.pennequin.fabien.redmine2gitlab.models._
import eu.pennequin.fabien.redmine2gitlab.services._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import java.nio.file.{Files, Paths}

object Main extends App {
  implicit val logger = Logger("redmine2gitlab")

  logger.info("Starting migration from redmine to gitlab...")

  if (args.length < 1) {
    System.err.println("Config file needed")
    System.exit(-1)
  }

  val configpath = Paths.get(args(0))
  if (!Files.exists(configpath)) {
    System.err.println("Config file does not exist")
    System.exit(-1)
  }

  val config = new ConfigLoader().load(configpath)

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val wsClient = buildWsClient()

  val fut = convertData(config, wsClient)

  fut.recover {
    case err =>
      System.err.println(err.getMessage)
      logger.warn("Error during data conversion", err)
      ()
  }.andThen {
  case _ =>
    wsClient.close()
    Await.result(system.terminate(), 15.seconds)
    logger.info("Migration done!")
    ()
  }

  private def convertData(config: AppConfig, wSClient: WSClient) = {
    val redmine = new Redmine(config.redmine, wsClient)
    val gitlab = new Gitlab(wsClient, config.gitlab.url)

    new Converter(config, redmine, gitlab).run()
  }

  private def buildWsClient() = {
    val wsConfig = AhcWSClientConfig().copy(
      wsClientConfig = WSClientConfig().copy(
        ssl = SSLConfigSettings()
          .withLoose(
            SSLLooseConfig()
              .withAcceptAnyCertificate(config.ws.acceptAnyCertificate)
          )
      )
    )
    val builder = new AhcConfigBuilder(wsConfig)
    val ahcBuilder = builder.configure()
    val ahcConfig = ahcBuilder.build()
    val asyncHttpClient = new DefaultAsyncHttpClient(ahcConfig)

    new AhcWSClient(new StandaloneAhcWSClient(asyncHttpClient))
  }

}
