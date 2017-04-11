package eu.pennequin.fabien.redmine2gitlab
package models

import java.time.LocalDateTime

import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class GitlabIssue(
  id: GitlabIssueId,
  iid: GitlabIssueId,
  projectId: GitlabProjectId,

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


object GitlabIssue {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[GitlabIssue]

}