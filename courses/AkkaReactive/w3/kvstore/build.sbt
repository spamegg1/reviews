course := "reactive"
assignment := "kvstore"

scalaVersion := "3.1.0"

val akkaVersion = "2.6.18"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "org.scalameta" %% "munit" % "0.7.26" % Test
)

Test / parallelExecution := false
