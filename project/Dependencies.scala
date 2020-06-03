import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {

  object Versions {
    val Cats = "2.1.0"
    val Circe = "0.13.0"
    val Http4s = "0.21.4"
    val Logback = "1.2.3"
    val ScalaLogging = "3.9.2"
    val ScalaTags = "0.9.1"
    val ScalaTest = "3.1.2"
    val SLF4J = "1.7.9"
  }

  val Cats = Seq(
    "org.typelevel" %% "cats-core" % Versions.Cats,
    "org.typelevel" %% "cats-effect" % Versions.Cats
  )

  val Http4s = Seq(
    "org.http4s" %% "http4s-blaze-server" % Versions.Http4s,
    "org.http4s" %% "http4s-circe" % Versions.Http4s,
    "org.http4s" %% "http4s-dsl" % Versions.Http4s
  )

  val Logging = Seq(
    "ch.qos.logback" % "logback-classic" % Versions.Logback,
    "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging,
    "org.slf4j" % "slf4j-api" % Versions.SLF4J
  )

  val CirceCore = Def.setting("io.circe" %%% "circe-core" % Versions.Circe)
  val CirceGeneric = Def.setting("io.circe" %%% "circe-generic" % Versions.Circe)
  val CirceParser = Def.setting("io.circe" %%% "circe-parser" % Versions.Circe)
  val ScalaTags = Def.setting("com.lihaoyi" %%% "scalatags" % Versions.ScalaTags)
  val ScalaTest = Def.setting("org.scalatest" %%% "scalatest" % Versions.ScalaTest)

}
