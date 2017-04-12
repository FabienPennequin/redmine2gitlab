package eu.pennequin.fabien.redmine2gitlab.models.redmine

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.{LocalDate, LocalDateTime}

case class Version(
  id: Long,
  name: String,
  description: String,
  status: VersionStatus,
  dueDate: Option[LocalDate],
  createdOn: LocalDateTime,
  updatedOn: LocalDateTime
)

object Version {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[Version]

}