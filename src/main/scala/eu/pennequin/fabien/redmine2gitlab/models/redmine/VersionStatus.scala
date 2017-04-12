package eu.pennequin.fabien.redmine2gitlab.models.redmine

import play.api.libs.json._

sealed trait VersionStatus {
  def code: String
}
case class VersionStatusImpl(code: String) extends VersionStatus

object VersionStatus {

  lazy val Closed = VersionStatusImpl("closed")
  lazy val Open = VersionStatusImpl("open")

  implicit val jsonFormat = new Format[VersionStatus] {
    override def reads(json: JsValue): JsResult[VersionStatus] = json.as[String] match {
      case Closed.code => JsSuccess(Closed)
      case Open.code => JsSuccess(Open)
      case status => JsError(s"Invalid version status '$status'")
    }

    override def writes(o: VersionStatus): JsValue = JsString(o.code)
  }
}