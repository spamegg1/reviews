package org.epfl.tasks

case class Task(name: String, duration: Int, requirements: List[Task])

val csSetup = Task("cs setup", 4, Nil)
val ide     = Task("IDE",      3, Nil)
val hack    = Task("hack",     8, List(csSetup, ide))
val deploy  = Task("deploy",   3, List(hack))

@main def recursion(): Unit =
  def maxTotalDuration(tasks: List[Task]): Int =
    tasks match
      case Nil          => 0
      case head :: tail =>
        val tailDuration = maxTotalDuration(tail)
        val headDuration = totalDuration(head)
        if headDuration < tailDuration then tailDuration
        else headDuration

  def totalDuration(task: Task): Int =
    task.duration + maxTotalDuration(task.requirements)

  println(totalDuration(deploy))
end recursion

// Note: this is actually not a "collections-based" implementation
// It’s just a refactoring of the recursive implementation to
// benefit from the existing operations on List (map and maxOption)
@main def collections(): Unit =
  def totalDuration(task: Task): Int =
    val requirementsMaxTotalDuration =
      task.requirements
        .map(totalDuration)
        .maxOption
        .getOrElse(0)
    task.duration + requirementsMaxTotalDuration

  println(totalDuration(deploy))
end collections
