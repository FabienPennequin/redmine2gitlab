package eu.pennequin.fabien.redmine2gitlab
package models

import play.api.libs.json._

sealed trait RedmineVersionStatus {
  def code: String
}
case class RedmineVersionStatusImpl(code: String) extends RedmineVersionStatus

object RedmineVersionStatus {

  lazy val Closed = RedmineVersionStatusImpl("closed")
  lazy val Open = RedmineVersionStatusImpl("open")

  implicit val jsonFormat = new Format[RedmineVersionStatus] {
    override def reads(json: JsValue): JsResult[RedmineVersionStatus] = json.as[String] match {
      case Closed.code => JsSuccess(Closed)
      case Open.code => JsSuccess(Open)
      case status => JsError(s"Invalid version status '$status'")
    }

    override def writes(o: RedmineVersionStatus): JsValue = JsString(o.code)
  }
}