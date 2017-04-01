package eu.pennequin.fabien.redmine2gitlab
package services

import java.nio.file.{Files, Path}

import com.typesafe.config.{Config, ConfigFactory}
import eu.pennequin.fabien.redmine2gitlab.models.{AppConfig, UserConfig}
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import net.ceedubs.ficus.readers.ValueReader

class ConfigLoader {

  def load(filepath: Path) = {
    if (!Files.exists(filepath)) {
      throw new IllegalArgumentException(s"Config file $filepath does not exist")
    }

    implicit val userConfigValueReader = new ValueReader[UserConfig] {
      def read(config: Config, path: String) = config.as[UserConfig](path)
    }

    ConfigFactory
      .parseFile(filepath.toFile)
      .withFallback(ConfigFactory.parseResources("reference.conf"))
      .resolve()
      .as[AppConfig]("migration")
  }

}
