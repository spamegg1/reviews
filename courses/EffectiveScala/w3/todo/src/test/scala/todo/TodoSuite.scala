package todo

import todo.data.*
import munit.FunSuite

/**
 * This suite aggregates the tests for the InMemoryModel and the
 * PersistentModel. It is used by the grading infrastructure.
 */
class TodoSuite extends FunSuite:

  // Data we use for testing

  val task1 = Task(State.Active, "An active task", Some("The notes"), List(Tag("a"), Tag("b")))
  val task2 = Task(State.completedNow, "An inactive task", None, List(Tag("c")))
  val task3 = Task(State.Active, "Another active task", None, List(Tag("a"), Tag("c")))

  // Fixtures handle setting up and cleaning up state

  def makeModelFixture(name: String, model: Model) = FunFixture[(String, Model)](
    setup = { test =>
      model.clear()
      (name, model)
    },
    teardown = { case (_, model) => model.clear() }
  )

  val inMemoryFixture = makeModelFixture("InMemoryModel", InMemoryModel)
  val persistentFixture = makeModelFixture("PersistentModel", PersistentModel)

  // Custom assertions. These make our code more readable and give more useful messages on errors

  def assertTask(
    modelName: String,
    actual: Option[Task],
    expected: Task
  )(using loc: munit.Location): Unit =
    actual match
      case None =>
        fail(s"Using $modelName: We expected the task $expected but we received None instead.")
      case Some(task) =>
        assertEquals(task, expected, s"Using $modelName: We expected the task $expected but we received $task instead.")

  def assertTaskActive(
    modelName: String,
    task: Option[Task]
  )(using loc: munit.Location): Unit =
    task match
      case None =>
        fail(s"Using $modelName: We expected a task but we received None instead.")
      case Some(t) =>
        assert(t.state.active, s"Using $modelName: We expected the task's state to be active but it was ${t.state}")

  def assertTaskCompleted(
    modelName: String,
    task: Option[Task]
  )(using loc: munit.Location): Unit =
    task match
      case None =>
        fail(s"Using $modelName: We expected a task but we received None instead.")
      case Some(t) =>
        assert(t.state.completed, s"Using $modelName: We expected the task's state to be completed but it was ${t.state}")

  // The tests, parameterized by a fixture

  def allTests(fixture: FunFixture[(String, Model)]): Unit =
    fixture.test("Created tasks can be read"){ case (name, model) =>
      val id1 = model.create(task1)
      assertTask(name, model.read(id1), task1)

      val id2 = model.create(task2)
      assertTask(name, model.read(id2), task2)
    }

    fixture.test("Updated tasks can be read"){ case (name, model) =>
      val id = model.create(task1)
      val returned = model.update(id)(_ => task2)
      val read = model.read(id)

      assertTask(name, returned, task2)
      assertTask(name, read, task2)
    }

    fixture.test("Deleted tasks are no longer read"){ case (name, model) =>
      val id1 = model.create(task1)
      val id2 = model.create(task2)

      assert(model.delete(id1), s"Using $name: We expected deleting $id1 to return true")

      assertEquals(model.read(id1),
                   None,
                   s"Using $name: The deleted task was still returned when read")
      assertEquals(model.read(id2),
                   Some(task2),
                   s"Using $name: The task that was not deleted was not returned when read")
    }

    fixture.test("Tasks returns all inserted tasks in insertion order"){ case (name, model) =>
      val id1 = model.create(task1)
      val t1 = model.tasks

      assertEquals(t1.toList,
                   List((id1 -> task1)),
                   s"Using $name: The list of tasks is different to the tasks that were created")

      val id2 = model.create(task2)
      val t2 = model.tasks

      assertEquals(t2.toList,
                   List((id1 -> task1), (id2 -> task2)),
                   s"Using $name: The list of tasks is different to the tasks that were created, or it is not in order of creation")
    }

    fixture.test("Tasks(tag) returns only tasks with given tag"){ case (name, model) =>
      val id1 = model.create(task1)
      val id2 = model.create(task2)
      val id3 = model.create(task3)

      assertEquals(model.tasks(Tag("a")).toList, List(id1 -> task1, id3 -> task3))
      assertEquals(model.tasks(Tag("b")).toList, List(id1 -> task1))
      assertEquals(model.tasks(Tag("c")).toList, List(id2 -> task2, id3 -> task3))
    }

    fixture.test("complete changes state to completed, if task is not already completed"){ case (name, model) =>
      val id1 = model.create(task1)
      val id2 = model.create(task2)

      assertTaskActive(name, model.read(id1))
      assertTaskCompleted(name, model.read(id2))

      model.complete(id1)

      assertTaskCompleted(name, model.complete(id1))
      assertTaskCompleted(name, model.complete(id2))
    }

    fixture.test("tags returns all tags"){ case (name, model) =>
      val id1 = model.create(task1)
      assertEquals(model.tags.toList, List(Tag("a"), Tag("b")), s"Using $name")

      val id2 = model.create(task2)
      assertEquals(model.tags.toList, List(Tag("a"), Tag("b"), Tag("c")), s"Using $name")

      val id3 = model.create(task3)
      assertEquals(model.tags.toList, List(Tag("a"), Tag("b"), Tag("c")), s"Using $name")
    }

  // Finally, create the tests with the two fixtures

  allTests(inMemoryFixture)
  allTests(persistentFixture)
