package eu.pennequin.fabien.redmine2gitlab
package models

case class AppConfig(
  redmine: RedmineConfig,
  gitlab: GitlabConfig,
  projects: Seq[ProjectConfig],
  users: Seq[UserConfig],
  ws: WsConfig
)

case class RedmineConfig(url: String, apiKey: String)

case class GitlabConfig(url: String, apiKey: String)

case class ProjectConfig(redmineId: Long, gitlabId: String)

case class UserConfig(redmineId: Long, gitlabId: Long, gitlabKey: Option[String] = None)

case class WsConfig(acceptAnyCertificate: Boolean)