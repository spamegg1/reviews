ThisBuild / scalaVersion := "3.0.0"

val js =
  project.in(file("js"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      name := "reasoning",
      scalaJSUseMainModuleInitializer := true,
      libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13)
    )

val jvm =
  project.in(file("jvm"))
    .settings(
      libraryDependencies ++=
        Seq(
          "org.scalameta" %% "munit"            % "0.7.26" % Test,
          "org.scalameta" %% "munit-scalacheck" % "0.7.26" % Test
        )
    )
