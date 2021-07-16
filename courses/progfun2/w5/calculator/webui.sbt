lazy val webUI = project.in(file("web-ui")).
  enablePlugins(ScalaJSPlugin).
  settings(
    scalaVersion := "3.0.0",
    // Add the sources of the calculator project
    Compile / unmanagedSourceDirectories += baseDirectory.value / ".." / "src" / "main" / "scala" / "calculator",
    libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13),
    scalaJSUseMainModuleInitializer := true
  )
