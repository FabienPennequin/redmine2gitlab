package eu.pennequin.fabien.redmine2gitlab.services

import play.api.libs.ws.WSClient

import eu.pennequin.fabien.redmine2gitlab.models.{AppConfig, RedmineVersion}

import scala.concurrent.{ExecutionContext, Future}

class Redmine(config: AppConfig, wsClient: WSClient) {

  def versions(projectId: Long)(implicit ec: ExecutionContext): Future[Seq[RedmineVersion]] = {
    httpClient(s"projects/$projectId/versions.json")
      .get()
      .map { response =>
        (response.json \ "versions").as[Seq[RedmineVersion]]
      }
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${config.redmine.url}/$uri")
      .withHeaders(("X-Redmine-API-Key", config.redmine.apiKey))

}
