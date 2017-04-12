package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.LocalDateTime

case class Issue(
  id: IssueId,
  iid: IssueId,
  projectId: ProjectId,

  title: String,
  description: Option[String] = None,
  confidential: Option[Boolean] = None,
  labels: Seq[String] = Seq.empty,

  state: String,

  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
  dueDate: Option[LocalDateTime] = None,

  webUrl: String
)


object Issue {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[Issue]

}