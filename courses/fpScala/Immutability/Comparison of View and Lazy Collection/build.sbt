scalaSource in Compile := baseDirectory.value / "src"
scalaSource in Test := baseDirectory.value / "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15"
scalaVersion := "3.2.0"