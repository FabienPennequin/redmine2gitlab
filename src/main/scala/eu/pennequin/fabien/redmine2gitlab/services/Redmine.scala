package eu.pennequin.fabien.redmine2gitlab.services

import play.api.libs.ws.WSClient

import eu.pennequin.fabien.redmine2gitlab.models.AppConfig
import eu.pennequin.fabien.redmine2gitlab.models.redmine.Version

import scala.concurrent.{ExecutionContext, Future}

class Redmine(config: AppConfig, wsClient: WSClient) {

  def versions(projectId: Long)(implicit ec: ExecutionContext): Future[Seq[Version]] = {
    httpClient(s"projects/$projectId/versions.json")
      .get()
      .map { response =>
        (response.json \ "versions").as[Seq[Version]]
      }
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${config.redmine.url}/$uri")
      .withHeaders(("X-Redmine-API-Key", config.redmine.apiKey))

}
