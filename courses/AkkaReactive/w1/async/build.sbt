course := "reactive"
assignment := "async"

testFrameworks += new TestFramework("munit.Framework")
Test / parallelExecution := false

scalaVersion := "3.0.0"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked"
)

libraryDependencies += "org.scalameta" %% "munit" % "0.7.26" % Test
