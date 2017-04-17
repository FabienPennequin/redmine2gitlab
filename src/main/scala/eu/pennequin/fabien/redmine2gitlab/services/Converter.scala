package eu.pennequin.fabien.redmine2gitlab.services

import scala.concurrent.{ExecutionContext, Future}

class Converter(appConfig: AppConfig, redmine: Redmine, gitlab: Gitlab) {

  def run()(implicit ec: ExecutionContext) = Future.successful(())

}
