package eu.pennequin.fabien.redmine2gitlab

import java.nio.file.{Files, Paths}

import models._
import services._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.Logger
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._

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
  val wsClient = AhcWSClient()

  val fut = convertData(config, wsClient)

  fut.recover {
    case err =>
      logger.warn("Error during data conversion", err)
      ()
  }.andThen {
  case _ =>
    wsClient.close()
    Await.result(system.terminate(), 15.seconds)
    logger.info("Migration done!")
    ()
  }

  def convertData(config: AppConfig, wSClient: WSClient) = {
    val redmine = new Redmine(config, wsClient)
    val gitlab = new Gitlab(config, wsClient)

    new Converter(redmine, gitlab).run()
  }

}
