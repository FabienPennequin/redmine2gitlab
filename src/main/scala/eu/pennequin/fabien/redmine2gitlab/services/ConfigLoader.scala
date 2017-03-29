package eu.pennequin.fabien.redmine2gitlab
package services

import models.AppConfig
import java.nio.file.{Files, Path, Paths}

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

class ConfigLoader {

  def load(filepath: Path) = {
    if (!Files.exists(filepath)) {
      throw new IllegalArgumentException(s"Config file $filepath does not exist")
    }

    ConfigFactory
      .parseFile(filepath.toFile)
      .withFallback(ConfigFactory.parseResources("reference.conf"))
      .resolve()
      .as[AppConfig]("migration")
  }

}
