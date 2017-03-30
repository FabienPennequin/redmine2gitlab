package eu.pennequin.fabien.redmine2gitlab
package models

import java.time.{LocalDate, LocalDateTime}

import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class GitlabMilestone(
  id: Long,
  iid: Long,
  projectId: Long,
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
