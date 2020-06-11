Global / name := "Shapes"
Global / organization := "cz.matejcerny"
Global / scalaVersion := "2.13.2"

lazy val client = project
  .in(file("client"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "Shapes.Client",
    scalaJSLinkerConfig ~= { _.withOptimizer(false).withClosureCompilerIfAvailable(false) }
  )
  .dependsOn(sharedJS)

lazy val server = project
  .in(file("server"))
  .settings(
    name := "Shapes.Server",
    // allows to read the generated JS on client
    (resources in Compile) += {
      (fastOptJS in (client, Compile)).value
      (fastOptJS in (client, Compile)).value.data
      (artifactPath in (client, Compile, fastOptJS)).value
    },
    // lets the backend to read the .map file for js
    resources in Compile += (fastOptJS in (client, Compile)).value
      .map((x: sbt.File) => new File(x.getAbsolutePath + ".map"))
      .data,
    // do a fastOptJS on reStart
    reStart := (reStart dependsOn (fastOptJS in (client, Compile))).evaluated,
    mainClass in reStart := Some("Server"),
    // makes reStart to rebuild if a scala.js file changes on the client
    watchSources ++= (watchSources in client).value,
    libraryDependencies ++=
      Dependencies.Cats ++
        Dependencies.Http4s ++
        Dependencies.Logging
  )
  .dependsOn(sharedJVM)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    name := "Shapes.Shared",
    libraryDependencies ++= Seq(
      Dependencies.CirceCore.value,
      Dependencies.CirceGeneric.value,
      Dependencies.CirceParser.value,
      Dependencies.ScalaTags.value,
      Dependencies.ScalaTest.value
    )
  )

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

ThisBuild / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:higherKinds", // Allow higher-kinded types
  "-Ywarn-unused:imports" // Warn if an import selector is not referenced.
)
