package eu.pennequin.fabien.redmine2gitlab
package models

import java.time.LocalDate

import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class GitlabMilestoneCreationDto(
  title: String,
  description: Option[String] = None,
  dueDate: Option[LocalDate] = None,
  startDate: Option[LocalDate] = None
)

object GitlabMilestoneCreationDto {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[GitlabMilestoneCreationDto]

}