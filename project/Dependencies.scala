import sbt._

object Dependencies {

  lazy val scalaTestDependency: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.1.2" % Test
  )

}
