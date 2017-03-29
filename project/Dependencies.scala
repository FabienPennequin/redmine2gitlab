import sbt._

object Dependencies {
  val playVersion = "2.6.0-M3"

  lazy val playJson = "com.typesafe.play" %% "play-json" % playVersion
  lazy val playWsAhc = "com.typesafe.play" %% "play-ahc-ws" % playVersion

  lazy val ficus = "com.iheart" %% "ficus" % "1.4.0"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"

}
