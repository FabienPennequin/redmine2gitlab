package eu.pennequin.fabien.redmine2gitlab
package models

import java.time.{LocalDate, LocalDateTime}

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class RedmineVersion(
  id: Long,
  name: String,
  description: String,
  status: RedmineVersionStatus,
  dueDate: Option[LocalDate],
  createdOn: LocalDateTime,
  updatedOn: LocalDateTime
)

object RedmineVersion {

  implicit val config = JsonConfiguration(SnakeCase)

  implicit val jsonFormat = Json.format[RedmineVersion]

}