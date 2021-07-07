course := "effective-scala"
assignment := "democracy"

scalaVersion := "3.0.0"

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "0.7.26" % Test
)

Compile / scalacOptions ++= Seq("-deprecation")

Test / testFrameworks += new TestFramework("munit.Framework")
