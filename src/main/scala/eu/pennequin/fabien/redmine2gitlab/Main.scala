package eu.pennequin.fabien.redmine2gitlab

import java.nio.file.{Files, Paths}

import models._
import services._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

object Main extends App {
  println("Starting migration from redmine to gitlab...")

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

  runImport(wsClient)
    .andThen {
      case state =>
        for {
          _ <- Future(wsClient.close())
          _ <- system.terminate()
        } yield {
          println("Migration done!")
          System.exit(if(state.isSuccess) 0 else -1)
          ()
        }
    }

  def runImport(wsClient: WSClient) =
    Future.successful(())

}
