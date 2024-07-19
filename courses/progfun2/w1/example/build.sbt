course := "progfun2"
assignment := "example"
scalaVersion := "3.4.2"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test

testFrameworks += new TestFramework("munit.Framework")
