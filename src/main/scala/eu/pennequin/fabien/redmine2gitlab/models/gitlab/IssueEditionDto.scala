package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class IssueEditionDto(
  title: Option[String] = None,
  description: Option[String] = None,
  confidential: Option[Boolean] = None,
  assigneeId: Option[Long] = None,
  milestoneId: Option[MilestoneId] = None,
  labels: Option[String] = None,
  stateEvent: Option[String] = None,
  updatedAt: Option[LocalDateTime] = None,
  dueDate: Option[LocalDateTime] = None
)

object IssueEditionDto {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[IssueEditionDto]

}
