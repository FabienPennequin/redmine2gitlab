package eu.pennequin.fabien.redmine2gitlab.models

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class GitlabNoteDto(
  body: String,
  createdAt: Option[LocalDateTime] = None
)

object GitlabNoteDto {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[GitlabNoteDto]
}
