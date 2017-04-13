package eu.pennequin.fabien.redmine2gitlab.models.redmine

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class ObjectRef(
  id: Long,
  name: String
)

object ObjectRef {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[ObjectRef]
}
