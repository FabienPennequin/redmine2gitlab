import sbt._

object Dependencies {
  val playVersion = "2.6.0-M4"

  lazy val playJson = "com.typesafe.play" %% "play-json" % playVersion
  lazy val playWsAhc = "com.typesafe.play" %% "play-ahc-ws" % playVersion
  lazy val playLogback = "com.typesafe.play" %% "play-logback" % playVersion

  lazy val ficus = "com.iheart" %% "ficus" % "1.4.0"

  // lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"

}
