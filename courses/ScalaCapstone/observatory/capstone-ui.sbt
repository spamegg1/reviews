val capstoneUI =
  project.in(file("capstone-ui"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      scalaVersion := "3.1.0",
      // Add the sources of the main project
      Compile / unmanagedSources ++= {
        val rootSourceDirectory = baseDirectory.value / ".." / "src" / "main" / "scala" / "observatory"
        Seq(
          rootSourceDirectory / "Interaction2.scala",
          rootSourceDirectory / "Signal.scala",
          rootSourceDirectory / "models.scala",
          rootSourceDirectory / "common.scala"
        )
      },
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "2.1.0",
        "com.lihaoyi" %%% "scalatags" % "0.11.0"
      ),
      scalaJSUseMainModuleInitializer := true
    )
