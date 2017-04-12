package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class IssueCreationDto(
  title: String,
  description: Option[String] = None,
  confidential: Option[Boolean] = None,
  assigneeId: Option[Long] = None,
  milestoneId: Option[MilestoneId] = None,
  labels: Option[String] = None,
  createdAt: Option[LocalDateTime] = None,
  dueDate: Option[LocalDateTime] = None
)

object IssueCreationDto {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[IssueCreationDto]

}
