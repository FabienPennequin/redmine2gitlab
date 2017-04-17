package eu.pennequin.fabien.redmine2gitlab
package models

case class AppConfig(
  redmine: RedmineConfig,
  gitlab: GitlabConfig,
  users: Seq[UserConfig],
  ws: WsConfig
)

case class RedmineConfig(url: String, apiKey: String, projectId: Long)

case class GitlabConfig(url: String, apiKey: String, projectId: Long)



case class UserConfig(redmineId: Long, gitlabId: Long, gitlabKey: Option[String] = None)

case class WsConfig(acceptAnyCertificate: Boolean)