course := "progfun2"
assignment := "quickcheck"

scalaVersion := "3.0.0"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "0.7.26" % Test,
  "org.scalacheck" %% "scalacheck" % "1.15.4"
)

testFrameworks += new TestFramework("munit.Framework")
