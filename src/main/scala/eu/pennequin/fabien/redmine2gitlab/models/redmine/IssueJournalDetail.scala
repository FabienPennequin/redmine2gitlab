package eu.pennequin.fabien.redmine2gitlab.models.redmine

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class IssueJournalDetail(
  property: String,
  name: String,
  oldValue: String,
  newValue: String
)

object IssueJournalDetail {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[IssueJournalDetail]
}
