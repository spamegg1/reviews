lazy val subProjectReferences = file(".").listFiles
  .filter(_.isDirectory)
  .filter(isNotIgnored)
  .flatMap { dir => getTasks(dir) }
  .map { dir => ProjectRef(dir, dir.getName.replace(" ", "-").toLowerCase()) }

lazy val root = Project(id = "FP_Demo", base = file(".")).aggregate(subProjectReferences: _*)

def getTasks(dir: File, level: Int = 0): Seq[File] = {
  if (level > 2) return Seq()
  dir.listFiles()
    .filter(_.isDirectory)
    .flatMap { subDir => if (isTaskDir(subDir)) Seq(subDir) else getTasks(subDir, level + 1) }
}

def isTaskDir(dir: File): Boolean = new File(dir, "src").exists()
def isNotIgnored(dir: File): Boolean = !Seq(".idea", ".coursecreator", "project", "target").contains(dir.getName)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15"
scalaVersion := "3.2.0"