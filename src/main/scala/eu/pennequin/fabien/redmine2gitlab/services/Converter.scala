package eu.pennequin.fabien.redmine2gitlab.services

import scala.concurrent.{ExecutionContext, Future}

class Converter(redmine: Redmine, gitlab: Gitlab) {
  def run()(implicit ec: ExecutionContext) = Future.successful(())
}
