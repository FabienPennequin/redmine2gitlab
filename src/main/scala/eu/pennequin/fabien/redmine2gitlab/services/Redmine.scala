package eu.pennequin.fabien.redmine2gitlab.services

import play.api.libs.ws.WSClient

import eu.pennequin.fabien.redmine2gitlab.models.RedmineConfig
import eu.pennequin.fabien.redmine2gitlab.models.redmine.{Issue, Version}

import scala.concurrent.{ExecutionContext, Future}

class Redmine(config: RedmineConfig, wsClient: WSClient) {

  def getIssuesForVersion(versionId: Long)(implicit ec: ExecutionContext): Future[Seq[Issue]] = {
    httpClient("issues.json")
      .withQueryString(
        ("fixed_version_id", versionId.toString),
        ("set_filter", "1"),
        ("status_id", "*")
      )
      .get()
      .map { response =>
        (response.json \ "issues").as[Seq[Issue]]
      }
  }

  def issue(issueId: Long, include: Seq[String] = Seq.empty)(implicit ec: ExecutionContext): Future[Issue] = {
    httpClient(s"issues/$issueId.json")
      .withQueryString(("include", include.mkString(",")))
      .get()
      .map { response =>
        (response.json \ "issue").as[Issue]
      }
  }

  def versions(projectId: Long)(implicit ec: ExecutionContext): Future[Seq[Version]] = {
    httpClient(s"projects/$projectId/versions.json")
      .get()
      .map { response =>
        (response.json \ "versions").as[Seq[Version]]
      }
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${config.url}/$uri")
      .withHeaders(("X-Redmine-API-Key", config.apiKey))

}
