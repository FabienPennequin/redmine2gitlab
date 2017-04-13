package eu.pennequin.fabien.redmine2gitlab.models.redmine

import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

import java.time.LocalDateTime

case class IssueJournal(
  id: Long,
  user: ObjectRef,
  createdOn: LocalDateTime,
  notes: Option[String] = None
)

object IssueJournal {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[IssueJournal]
}
