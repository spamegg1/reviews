course := "effective-scala"
assignment := "fireworks"

scalaVersion := "3.0.0"

libraryDependencies ++= Seq(
  ("org.creativescala" %% "doodle"           % "0.9.21").cross(CrossVersion.for3Use2_13),
  "org.scalameta"      %% "munit"            % "0.7.26"  % Test,
  "org.scalacheck"     %% "scalacheck"       % "1.15.4"  % Test
)

Compile / scalacOptions ++= Seq("-deprecation")
