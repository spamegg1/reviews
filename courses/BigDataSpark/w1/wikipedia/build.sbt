course := "bigdata"
assignment := "wikipedia"

scalaVersion := "3.1.0"
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "0.7.26" % Test,
  excludes(("org.apache.spark" %% "spark-core" % "3.2.0").cross(CrossVersion.for3Use2_13)),
  excludes(("org.apache.spark" %% "spark-sql" % "3.2.0").cross(CrossVersion.for3Use2_13))
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
    exclude("javax.annotation", "javax.annotation-api")

// Without forking, ctrl-c doesn't actually fully stop Spark
run / fork := true
Test / fork := true

Compile / resourceGenerators += downloadDataset.map(Seq(_)).taskValue
