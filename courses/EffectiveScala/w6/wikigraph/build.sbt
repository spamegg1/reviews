course := "effective-scala"
assignment := "wikigraph"

scalaVersion := "3.1.0"

Test / parallelExecution := false


libraryDependencies ++= Seq(
  ("com.typesafe.slick" %% "slick" % "3.3.3").cross(CrossVersion.for3Use2_13),
  "org.xerial"%"sqlite-jdbc" % "3.7.2",
  "org.scalameta" %% "munit" % "0.7.26" % Test,
  "org.scalacheck" %% "scalacheck" % "1.15.4" % Test
)
