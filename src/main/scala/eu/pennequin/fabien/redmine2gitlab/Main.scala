package eu.pennequin.fabien.redmine2gitlab

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

object Main extends App {
  println("Starting migration from redmine to gitlab...")

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
