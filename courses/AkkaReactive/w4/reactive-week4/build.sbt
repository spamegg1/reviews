name := "linkchecker"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.6.0",
  "com.typesafe.akka" %% "akka-testkit" % "2.6.0",
  "com.typesafe.akka" %% "akka-cluster" % "2.6.0",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "com.ning" % "async-http-client" % "1.9.40",
  "org.jsoup" % "jsoup" % "1.8.1",
  "ch.qos.logback" % "logback-classic" % "1.1.4")

Test / parallelExecution := false
