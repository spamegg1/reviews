course := "effective-scala"
assignment := "todo"

val DottyVersion = "3.0.0"
val Http4sVersion = "1.0.0-M4"
val CirceVersion = "0.13.0"
val LogbackVersion = "1.2.3"

scalaVersion := DottyVersion

Global / onChangedBuildSource := ReloadOnSourceChanges

libraryDependencies ++= Seq(
  ("org.http4s"     %% "http4s-ember-server" % Http4sVersion).cross(CrossVersion.for3Use2_13),
  ("org.http4s"     %% "http4s-circe"        % Http4sVersion).cross(CrossVersion.for3Use2_13),
  ("org.http4s"     %% "http4s-dsl"          % Http4sVersion).cross(CrossVersion.for3Use2_13),
  "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
  "org.scalameta"   %% "munit"               % "0.7.26" % Test,
  "com.novocode"    %  "junit-interface"     % "0.11" % Test,
  ("io.circe" %% "circe-parser" % CirceVersion).cross(CrossVersion.for3Use2_13)
)

scalacOptions += "-language:implicitConversions"
