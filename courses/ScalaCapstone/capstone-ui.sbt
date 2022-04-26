val capstoneUI =
  project.in(file("capstone-ui"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      scalaVersion := "2.13.1",
      // Add the sources of the main project
      unmanagedSources in Compile ++= {
        val rootSourceDirectory = baseDirectory.value / ".." / "src" / "main" / "scala" / "observatory"
        Seq(
          rootSourceDirectory / "Interaction2.scala",
          rootSourceDirectory / "Signal.scala",
          rootSourceDirectory / "models.scala",
          rootSourceDirectory / "package.scala"
        )
      },
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.7",
        "com.lihaoyi" %%% "scalatags" % "0.7.0"
      ),
      scalaJSUseMainModuleInitializer := true
    )
