package eu.pennequin.fabien.redmine2gitlab
package models

import java.time.{LocalDate, LocalDateTime}

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class GitlabMilestone(
  id: GitlabMilestoneId,
  iid: GitlabMilestoneId,
  projectId: GitlabProjectId,
  title: String,
  description: String,
  dueDate: LocalDate,
  startDate: LocalDate,
  state: String,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime
)

object GitlabMilestone {

  implicit val config = JsonConfiguration(SnakeCase)

  implicit val jsonFormat = Json.format[GitlabMilestone]

}
