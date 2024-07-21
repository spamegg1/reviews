lazy val webUI = project
  .in(file("web-ui"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaVersion := "3.4.2",
    // Add the sources of the calculator project
    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "src" / "main" / "scala" / "calculator",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0",
    scalaJSUseMainModuleInitializer := true,
    Compile / fastOptJS / artifactPath := target.value / ((fastOptJS / moduleName).value + "-fastopt.js")
  )
