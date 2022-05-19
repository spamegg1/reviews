course := "capstone"
assignment := "observatory"

scalaVersion := "3.1.0"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked"
)

libraryDependencies ++= Seq(
  ("org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4").cross(CrossVersion.for3Use2_13),
  "com.sksamuel.scrimage" % "scrimage-core" % "4.0.12", // for visualization
  ("com.sksamuel.scrimage" %% "scrimage-scala" % "4.0.12").cross(CrossVersion.for3Use2_13), // for visualization
  // You don’t *have to* use Spark, but in case you want to, we have added the dependency
  excludes("org.apache.spark" %% "spark-sql" % "3.2.0").cross(CrossVersion.for3Use2_13),
  excludes("io.github.vincenzobaz" %% "spark-scala3" % "0.1.2"), // See https://github.com/vincenzobaz/spark-scala3 on how to use datasets with Scala 3
  // You don’t *have to* use akka-stream, but in case you want to, we have added the dependency
  "com.typesafe.akka" %% "akka-stream" % "2.6.18",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.18",
  // You don’t *have to* use Monix, but in case you want to, we have added the dependency
  "io.monix" %% "monix" % "3.4.0",
  // You don’t *have to* use fs2, but in case you want to, we have added the dependency
  "co.fs2" %% "fs2-io" % "2.5.6",
  "org.scalacheck" %% "scalacheck" % "1.15.4" % Test,
  "org.scalameta" %% "munit" % "0.7.26" % Test
)

//netty-all replaces all these excludes
def excludes(m: ModuleID): ModuleID =
  m.exclude("io.netty", "netty-common").
    exclude("io.netty", "netty-handler").
    exclude("io.netty", "netty-transport").
    exclude("io.netty", "netty-buffer").
    exclude("io.netty", "netty-codec").
    exclude("io.netty", "netty-resolver").
    exclude("io.netty", "netty-transport-native-epoll").
    exclude("io.netty", "netty-transport-native-unix-common").
    exclude("javax.xml.bind", "jaxb-api").
    exclude("jakarta.xml.bind", "jaxb-api").
    exclude("javax.activation", "activation").
    exclude("jakarta.annotation", "jakarta.annotation-api").
    exclude("javax.annotation", "javax.annotation-api").
    exclude("org.slf4j", "slf4j-log4j12").
    exclude("com.google.protobuf", "protobuf-java")

Test / parallelExecution := false // So that tests are executed for each milestone, one after the other
