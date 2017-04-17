package eu.pennequin.fabien.redmine2gitlab.services

import play.api.Logger
import play.api.http.Status
import play.api.libs.json.{Json, Reads}
import play.api.libs.ws.{WSClient, WSResponse}

import eu.pennequin.fabien.redmine2gitlab.models.gitlab._

import scala.concurrent.{ExecutionContext, Future}
import java.time.LocalDateTime

sealed trait GitlabResult[T]
case class GitlabResultSuccess[T](value: T) extends GitlabResult[T]
case class GitlabResultError[T](message: String) extends GitlabResult[T]

class Gitlab(wsClient: WSClient, baseUrl: String) {

  val logger = Logger("gitlab")

  def getMilestones(projectId: Long)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Seq[Milestone]]] = {
    httpClient(s"projects/$projectId/milestones")
      .get()
      .map(r => asResult[Seq[Milestone]](r, Status.OK))
  }

  def createMilestone(projectId: ProjectId, milestone: MilestoneCreationDto)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Milestone]] = {
    httpClient(s"projects/$projectId/milestones")
      .post(Json.toJson(milestone))
      .map(r => asResult[Milestone](r, Status.CREATED))
  }

  def closeMilestone(projectId: ProjectId, milestoneId: MilestoneId)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Milestone]] = {
    httpClient(s"projects/$projectId/milestones/$milestoneId")
      .put(Json.obj("state_event" -> "close"))
      .map(r => asResult[Milestone](r, Status.OK))
  }

  def createIssue(projectId: ProjectId, issue: IssueCreationDto)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Issue]] = {
    httpClient(s"projects/$projectId/issues")
      .post(Json.toJson(issue))
      .map(r => asResult[Issue](r, Status.CREATED))
  }

  def closeIssue(projectId: ProjectId, issueIid: IssueId, updatedAt: Option[LocalDateTime] = None)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Issue]] = {
    httpClient(s"projects/$projectId/issues/$issueIid")
      .put(Json.toJson(IssueEditionDto(
        stateEvent = Some("close"),
        updatedAt = updatedAt
      )))
      .map(r => asResult[Issue](r, Status.OK))
  }

  def deleteIssue(projectId: ProjectId, issueIid: IssueId)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Unit]] = {
    httpClient(s"projects/$projectId/issues/$issueIid")
      .delete()
      .map(r => if (r.status == Status.NO_CONTENT) GitlabResultSuccess(()) else GitlabResultError(r.body))
  }

  def createIssueNote(projectId: ProjectId, issueIid: IssueId, dto: NoteDto)(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Note]] = {
    httpClient(s"projects/$projectId/issues/$issueIid/notes")
      .post(Json.toJson(dto))
      .map(r => asResult[Note](r, Status.CREATED))
  }

  def getProjectsMembership()(implicit ec: ExecutionContext, apiKey: ApiPrivateKey): Future[GitlabResult[Seq[Project]]] = {
    httpClient("projects")
      .withQueryString(("membership", "true"))
      .get()
      .map(r => asResult[Seq[Project]](r, Status.OK))
  }

  private def httpClient(uri: String)(implicit apiKey: ApiPrivateKey) = {
    val url = s"${apiUrl}/$uri"
    logger.info(url)
    wsClient
      .url(url)
      .withHeaders(("PRIVATE-TOKEN", apiKey.key))
  }

  private def asResult[T](response: WSResponse, expectedStatus: Int)(implicit fjs: Reads[T]): GitlabResult[T] = {
    response.status match {
      case `expectedStatus` =>
        logger.info(s"Got '${expectedStatus}'")
        response.json.validate[T].fold(
          errors => GitlabResultError(errors.mkString),
          value => GitlabResultSuccess(value)
        )
      case _ =>
        logger.warn(s"Got '${response.statusText}' with content: ${response.body}")
        GitlabResultError(s"Got '${response.statusText}' from Gitlab: ${response.body}")
    }
  }

  private val apiUrl = s"$baseUrl/api/v4"

}
