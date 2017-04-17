package eu.pennequin.fabien.redmine2gitlab.models.gitlab

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

import java.time.{LocalDate, LocalDateTime}

case class Milestone(
  id: MilestoneId,
  iid: MilestoneId,
  projectId: ProjectId,

  title: String,
  description: Option[String] = None,
  state: String,

  startDate: Option[LocalDate] = None,
  dueDate: Option[LocalDate] = None,

  createdAt: LocalDateTime,
  updatedAt: LocalDateTime
)

object Milestone {

  implicit val config = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[Milestone]

}
