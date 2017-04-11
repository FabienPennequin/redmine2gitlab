package eu.pennequin.fabien.redmine2gitlab
package models

import play.api.libs.json.Json

case class GitlabProject(
  id: GitlabProjectId,
  name: String,
  description: Option[String]
)

object GitlabProject {
  implicit val jsonFormat = Json.format[GitlabProject]
}
