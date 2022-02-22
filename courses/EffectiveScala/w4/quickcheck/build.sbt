course := "effective-scala"
assignment := "quickcheck"

scalaVersion := "3.1.0"
scalacOptions ++= Seq("-deprecation")
libraryDependencies += "org.scalameta" %% "munit" % "0.7.26" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.4"
