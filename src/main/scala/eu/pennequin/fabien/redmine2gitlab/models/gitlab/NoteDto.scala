package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class NoteDto(
  body: String,
  createdAt: Option[LocalDateTime] = None
)

object NoteDto {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[NoteDto]
}
