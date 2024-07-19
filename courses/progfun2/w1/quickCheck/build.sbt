course := "progfun2"
assignment := "quickcheck"

scalaVersion := "3.4.2"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.0.0" % Test,
  "org.scalacheck" %% "scalacheck" % "1.18.0"
)

testFrameworks += new TestFramework("munit.Framework")
