course := "bigdata"
assignment := "wikipedia"

scalaVersion := "2.12.8"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  "com.novocode" % "junit-interface" % "0.11" % Test,
  ("org.apache.spark" %% "spark-core" % "2.4.3"),
  ("org.apache.spark" %% "spark-sql" % "2.4.3")
)
dependencyOverrides ++= Seq(
  ("com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7")
)

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v", "-s")
