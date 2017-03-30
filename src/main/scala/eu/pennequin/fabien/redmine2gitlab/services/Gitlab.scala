package eu.pennequin.fabien.redmine2gitlab
package services

import models.{GitlabMilestone, GitlabProject, RedmineVersion}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

class Gitlab(wsClient: WSClient, baseUrl: String, apiKey: String) {

  def milestones(projectId: Long)(implicit ec: ExecutionContext): Future[Seq[GitlabMilestone]] = {
    httpClient(s"projects/$projectId/milestones")
      .get()
      .map { response =>
        response.json.as[Seq[GitlabMilestone]]
      }
  }

  def projects()(implicit ec: ExecutionContext) = {
    httpClient("projects")
      .withQueryString(("membership", "true"))
      .get()
      .map { response =>
        println(response)
        val statusText: String = response.statusText
        println(s"Got a response $statusText")

        val projects = response.json.as[Seq[GitlabProject]]
        println(projects)
      }
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${apiUrl}/$uri")
      .withHeaders(("PRIVATE-TOKEN", apiKey))


  private val apiUrl = "s$baseUrl/api/v4"

}
