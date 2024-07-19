course := "progfun2"
assignment := "codecs"

scalaVersion := "3.4.2"

scalacOptions ++= Seq("-deprecation")

libraryDependencies ++= Seq(
  "org.typelevel" %% "jawn-parser" % "1.6.0",
  "org.scalacheck" %% "scalacheck" % "1.18.0" % Test,
  "org.scalameta" %% "munit" % "1.0.0" % Test
)

testFrameworks += new TestFramework("munit.Framework")
