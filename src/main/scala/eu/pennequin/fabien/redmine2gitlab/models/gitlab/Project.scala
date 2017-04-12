package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.Json

case class Project(
  id: ProjectId,
  name: String,
  description: Option[String]
)

object Project {
  implicit val jsonFormat = Json.format[Project]
}
