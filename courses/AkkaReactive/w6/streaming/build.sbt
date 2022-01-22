course := "reactive"
assignment := "streaming"

scalaVersion := "3.1.0"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked"
)

val akkaVersion = "2.6.18"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "org.scalameta" %% "munit" % "0.7.26" % Test
)

Test / parallelExecution := false
