import Dependencies._

Global / name := "Shapes"
Global / organization := "cz.matejcerny"
Global / scalaVersion := "2.13.2"

lazy val client = project
  .in(file("client"))
  .settings(name := "Shapes.Client")
  .dependsOn(sharedJS)

lazy val server = project
  .in(file("server"))
  .settings(
    name := "Shapes.Server",
    libraryDependencies ++= scalaTestDependency
  )
  .dependsOn(sharedJVM)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(name := "Shapes.Shared")

lazy val sharedJVM = shared.jvm
lazy val sharedJS = shared.js

lazy val assemblySettings = Seq(
  assembly / assemblyJarName := s"${name.value}.jar",
  assembly / test := {},
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", _ @_*) => MergeStrategy.discard
    case x =>
      val oldStrategy = (assembly / assemblyMergeStrategy).value
      oldStrategy(x)
  }
)
