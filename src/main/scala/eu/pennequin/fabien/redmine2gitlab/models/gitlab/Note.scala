package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class Note(
  id: Long,
  body: String,
  attachment: Option[String],
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
  system: Boolean,
  noteableId: Long,
  noteableType: String
)

object Note {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[Note]

}