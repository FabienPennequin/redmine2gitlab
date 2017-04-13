package eu.pennequin.fabien.redmine2gitlab.models.redmine

import play.api.libs.functional.syntax._
import play.api.libs.json._

import java.time.LocalDateTime

case class Issue(
  id: Long,
  subject: String,
  description: String,
  project: ObjectRef,
  tracker: ObjectRef,
  status: ObjectRef,
  priority: ObjectRef,
  author: ObjectRef,
  category: ObjectRef,
  fixedVersion: Option[ObjectRef] = None,
  createdOn: LocalDateTime,
  updatedOn: LocalDateTime,
  doneRatio: Option[Int] = None,
  closedOn: Option[LocalDateTime] = None,
  journals: Seq[IssueJournal] = Seq.empty
)

object Issue {
  implicit val jsonReader: Reads[Issue] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "subject").read[String] and
    (JsPath \ "description").read[String] and
    (JsPath \ "project").read[ObjectRef] and
    (JsPath \ "tracker").read[ObjectRef] and
    (JsPath \ "status").read[ObjectRef] and
    (JsPath \ "priority").read[ObjectRef] and
    (JsPath \ "author").read[ObjectRef] and
    (JsPath \ "category").read[ObjectRef] and
    (JsPath \ "fixed_version").readNullable[ObjectRef] and
    (JsPath \ "created_on").read[LocalDateTime] and
    (JsPath \ "updated_on").read[LocalDateTime] and
    (JsPath \ "done_ratio").readNullable[Int] and
    (JsPath \ "closed_on").readNullable[LocalDateTime] and
    (JsPath \ "journals").readWithDefault[Seq[IssueJournal]](Seq.empty)
  )(Issue.apply _)
}