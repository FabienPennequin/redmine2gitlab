package eu.pennequin.fabien.redmine2gitlab
package services

import scala.concurrent.{ExecutionContext, Future}

import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import eu.pennequin.fabien.redmine2gitlab.models._

class Gitlab(wsClient: WSClient, baseUrl: String, apiKey: String) {

  def getMilestones(projectId: Long)(implicit ec: ExecutionContext): Future[Seq[GitlabMilestone]] = {
    httpClient(s"projects/$projectId/milestones")
      .get()
      .map { response =>
        response.json.as[Seq[GitlabMilestone]]
      }
  }

  def createMilestone(projectId: GitlabProjectId, milestone: GitlabMilestoneCreationDto)(implicit  ec: ExecutionContext) = {
    httpClient(s"projects/$projectId/milestones")
      .post(Json.toJson(milestone))
      .map { response =>
        response.json.as[GitlabMilestone]
      }
  }

  def closeMilestone(projectId: GitlabProjectId, milestoneId: GitlabMilestoneId)(implicit  ec: ExecutionContext) = {
    httpClient(s"projects/$projectId/milestones/$milestoneId")
      .put(Json.obj("state_event" -> "close"))
      .map { response =>
        response.json.as[GitlabMilestone]
      }
  }

  def projects()(implicit ec: ExecutionContext) = {
    httpClient("projects")
      .withQueryString(("membership", "true"))
      .get()
      .map { response =>
        response.json.as[Seq[GitlabProject]]
      }
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${apiUrl}/$uri")
      .withHeaders(("PRIVATE-TOKEN", apiKey))


  private val apiUrl = s"$baseUrl/api/v4"

}
