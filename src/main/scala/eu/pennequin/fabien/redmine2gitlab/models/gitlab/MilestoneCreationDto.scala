package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDate

case class MilestoneCreationDto(
  title: String,
  description: Option[String] = None,
  dueDate: Option[LocalDate] = None,
  startDate: Option[LocalDate] = None
)

object MilestoneCreationDto {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[MilestoneCreationDto]

}