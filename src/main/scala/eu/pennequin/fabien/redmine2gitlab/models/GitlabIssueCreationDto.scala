package eu.pennequin.fabien.redmine2gitlab
package models

import java.time.LocalDateTime

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class GitlabIssueCreationDto(
  title: String,
  description: Option[String] = None,
  confidential: Option[Boolean] = None,
  assigneeId: Option[Long] = None,
  milestoneId: Option[GitlabMilestoneId] = None,
  labels: Option[String] = None,
  createdAt: Option[LocalDateTime] = None,
  dueDate: Option[LocalDateTime] = None
)

object GitlabIssueCreationDto {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[GitlabIssueCreationDto]

}
