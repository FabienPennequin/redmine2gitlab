package eu.pennequin.fabien.redmine2gitlab
package services

import scala.concurrent.{ExecutionContext, Future}

import play.api.http.Status
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import eu.pennequin.fabien.redmine2gitlab.models._

class Gitlab(wsClient: WSClient, baseUrl: String, apiKey: String) {

  def getMilestones(projectId: Long)(implicit ec: ExecutionContext): Future[Seq[GitlabMilestone]] = {
    httpClient(s"projects/$projectId/milestones")
      .get()
      .map { response =>
        response.status match {
          case Status.OK => response.json.as[Seq[GitlabMilestone]]
          case _ => throw new Exception(response.body)
        }
      }
  }

  def createMilestone(projectId: GitlabProjectId, milestone: GitlabMilestoneCreationDto)(implicit  ec: ExecutionContext) = {
    httpClient(s"projects/$projectId/milestones")
      .post(Json.toJson(milestone))
      .map { response =>
        response.status match {
          case Status.OK => response.json.as[GitlabMilestone]
          case _ => throw new Exception(response.body)
        }
      }
  }

  def closeMilestone(projectId: GitlabProjectId, milestoneId: GitlabMilestoneId)(implicit  ec: ExecutionContext) = {
    httpClient(s"projects/$projectId/milestones/$milestoneId")
      .put(Json.obj("state_event" -> "close"))
      .map { response =>
        response.status match {
          case Status.OK => response.json.as[GitlabMilestone]
          case _ => throw new Exception(response.body)
        }
      }
  }

  def createIssue(projectId: GitlabProjectId, issue: GitlabIssueCreationDto)(implicit ec: ExecutionContext) = {
    httpClient(s"/projects/$projectId/issues")
      .post(Json.toJson(issue))
      .map { response =>
        response.status match {
          case Status.OK => response.json.as[GitlabIssue]
          case _ => throw new Exception(response.body)
        }
      }
  }

  def getProjectsMembership()(implicit ec: ExecutionContext) = {
    httpClient("projects")
      .withQueryString(("membership", "true"))
      .get()
      .map { response =>
        response.status match {
          case Status.OK => response.json.as[Seq[GitlabProject]]
          case _ => throw new Exception(response.body)
        }
      }
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${apiUrl}/$uri")
      .withHeaders(("PRIVATE-TOKEN", apiKey))


  private val apiUrl = s"$baseUrl/api/v4"

}
