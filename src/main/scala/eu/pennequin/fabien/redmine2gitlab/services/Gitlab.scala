package eu.pennequin.fabien.redmine2gitlab
package services

import play.api.http.Status
import play.api.libs.json.{Json, Reads}
import play.api.libs.ws.{WSClient, WSResponse}

import eu.pennequin.fabien.redmine2gitlab.models._

import scala.concurrent.{ExecutionContext, Future}

sealed trait GitlabResult[T]
case class GitlabResultSuccess[T](value: T) extends GitlabResult[T]
case class GitlabResultError[T](message: String) extends GitlabResult[T]

class Gitlab(wsClient: WSClient, baseUrl: String, apiKey: String) {

  def getMilestones(projectId: Long)(implicit ec: ExecutionContext): Future[GitlabResult[Seq[GitlabMilestone]]] = {
    httpClient(s"projects/$projectId/milestones")
      .get()
      .map(asResult[Seq[GitlabMilestone]])
  }

  def createMilestone(projectId: GitlabProjectId, milestone: GitlabMilestoneCreationDto)(implicit  ec: ExecutionContext): Future[GitlabResult[GitlabMilestone]] = {
    httpClient(s"projects/$projectId/milestones")
      .post(Json.toJson(milestone))
      .map(asResult[GitlabMilestone])
  }

  def closeMilestone(projectId: GitlabProjectId, milestoneId: GitlabMilestoneId)(implicit ec: ExecutionContext): Future[GitlabResult[GitlabMilestone]] = {
    httpClient(s"projects/$projectId/milestones/$milestoneId")
      .put(Json.obj("state_event" -> "close"))
      .map(asResult[GitlabMilestone])
  }

  def createIssue(projectId: GitlabProjectId, issue: GitlabIssueCreationDto)(implicit ec: ExecutionContext): Future[GitlabResult[GitlabIssue]] = {
    httpClient(s"/projects/$projectId/issues")
      .post(Json.toJson(issue))
      .map(asResult[GitlabIssue])
  }

  def getProjectsMembership()(implicit ec: ExecutionContext): Future[GitlabResult[Seq[GitlabProject]]] = {
    httpClient("projects")
      .withQueryString(("membership", "true"))
      .get()
      .map(asResult[Seq[GitlabProject]])
  }

  private def httpClient(uri: String) =
    wsClient
      .url(s"${apiUrl}/$uri")
      .withHeaders(("PRIVATE-TOKEN", apiKey))

  private def asResult[T](response: WSResponse)(implicit fjs: Reads[T]): GitlabResult[T] = {
    response.status match {
      case Status.OK =>
        response.json.validate[T].fold(
          errors => GitlabResultError(errors.mkString),
          value => GitlabResultSuccess(value)
        )
      case _ =>
        GitlabResultError(s"Got ${response.statusText} from Gitlab: ${response.body}")
    }
  }

  private val apiUrl = s"$baseUrl/api/v4"

}
