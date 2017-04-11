package eu.pennequin.fabien.redmine2gitlab.models

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class GitlabNote(
  id: Long,
  body: String,
  attachment: Option[String],
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
  system: Boolean,
  noteableId: Long,
  noteableType: String
)

object GitlabNote {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[GitlabNote]

}