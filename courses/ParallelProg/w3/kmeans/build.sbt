course := "parprog1"
assignment := "kmeans"

scalaVersion := "3.4.2"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  ("com.storm-enroute" %% "scalameter-core" % "0.21")
    .cross(CrossVersion.for3Use2_13),
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
  "org.scalameta" %% "munit" % "1.0.0" % Test
)

testFrameworks += new TestFramework("munit.Framework")
