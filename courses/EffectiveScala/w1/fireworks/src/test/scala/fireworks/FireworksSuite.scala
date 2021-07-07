package fireworks

import munit.*
import org.scalacheck.Prop
import org.scalacheck.Prop.propBoolean

class FireworksSuite extends FunSuite, FireworksGenerators:

  test("'Firework.next' returns 'Done' when given 'Done' `") {
    assertEquals(Firework.next(Done), Done, "'Firework.next(Done)' did not return 'Done'.")
  }

  property("'Firework.next' returns 'Waiting' or 'Launched' when given 'Waiting'") {
    Prop.forAll { (waiting: Waiting) =>
      Firework.next(waiting) match {
        case _: Waiting | _: Launched => Prop.passed
        case _ => fail("'Firework.next' did not return a value of type 'Waiting' or a value of type 'Launched'.")
      }
    }
  }

  property("'Firework.next' returns 'Launched' or 'Exploding' when given 'Launched'") {
    Prop.forAll { (launched: Launched) =>
      Firework.next(launched) match {
        case _: Launched | _: Exploding => Prop.passed
        case _ => fail("'Firework.next' did not return a value of type 'Launched' or a value of type 'Exploding'.")
      }
    }
  }

  property("'Firework.next' returns 'Exploding' or 'Done' when given 'Exploding'") {
    Prop.forAll { (exploding: Exploding) =>
      Firework.next(exploding) match {
        case _: Exploding | Done => Prop.passed
        case _ => fail("'Firework.next' did not return a value of type 'Exploding' or 'Done'.")
      }
    }
  }

  property("Operation 'next' on class 'Waiting' should return the same 'Waiting' value with a decremented 'countDown' until it is zero or negative") {
    Prop.forAll { (waiting: Waiting) =>
      (waiting.countDown > 0) ==> {
        waiting.next match {
          case nextWaiting: Waiting =>
            assertEquals(nextWaiting.countDown, waiting.countDown - 1, "'countDown' must be decremented by one")
            assertEquals(nextWaiting.startPosition, waiting.startPosition, "'startPosition' should not be changed")
            assertEquals(nextWaiting.numberOfParticles, waiting.numberOfParticles, "'numberOfParticles' should not be changed")
            assertEquals(nextWaiting.particlesColor, waiting.particlesColor, "'particlesColor' should not be changed")
            Prop.passed
          case _ =>
            fail("'next' should return a value of type 'Waiting' as long as the 'countDown' is positive")
        }
      }
    }
  }

  property("Operation 'next' on class 'Waiting' should return a 'Launched' value if the 'countDown' is zero or negative") {
    Prop.forAll { (waiting: Waiting) =>
      (waiting.countDown <= 0) ==> {
        waiting.next match {
          case launched: Launched =>
            assertEquals(launched.position, waiting.startPosition, "'Launched' value should have the same 'position' as the 'Waiting' value’s 'startPosition'")
            assertEquals(launched.numberOfParticles, waiting.numberOfParticles, "'Launched' value should have the same 'numberOfParticles' as the 'Waiting' value")
            assertEquals(launched.particlesColor, waiting.particlesColor, "'Launched' value should have the same 'particlesColor' as the 'Waiting' value")
            Prop.passed
          case _ =>
            fail("'next' should return a value of type 'Launched' if the 'countDown' is zero or negative")
        }
      }
    }
  }

  property("Operation 'next' on class 'Launched' should return a 'Launched' value with a decremented 'countDown' until it is zero or negative") {
    Prop.forAll { (launched: Launched) =>
      (launched.countDown > 0) ==> {
        launched.next match {
          case nextLaunched: Launched =>
            assertEquals(nextLaunched.countDown, launched.countDown - 1, "'countDown' must be decremented by one")
            assertEquals(nextLaunched.direction, launched.direction, "'direction' should not be changed")
            assertEquals(nextLaunched.numberOfParticles, launched.numberOfParticles, "'numberOfParticles' should not be changed")
            assertEquals(nextLaunched.particlesColor, launched.particlesColor, "'particlesColor' should not be changed")
            Prop.passed
          case _ =>
            fail("'next' should return a value of type 'Launched' as long as the 'countDown' is positive")
        }
      }
    }
  }

  property("Operation 'next' on class 'Launched' should return a 'Launched' value with an updated 'position' until the 'countDown' is zero or negative") {
    Prop.forAll { (launched: Launched) =>
      (launched.countDown > 0) ==> {
        launched.next match {
          case nextLaunched: Launched =>
            val expectedPosition =
              Motion.movePoint(launched.position, launched.direction, Settings.propulsionSpeed)
            assertEqualsDouble(nextLaunched.position.x, expectedPosition.x, 0.01, "The 'position' of the firework should move according to its 'direction' and 'Settings.propulsionSpeed'")
            assertEqualsDouble(nextLaunched.position.y, expectedPosition.y, 0.01, "The 'position' of the firework should move according to its 'direction' and 'Settings.propulsionSpeed'")
            Prop.passed
          case _ =>
            fail("'next' should return a value of type 'Launched' as long as the 'countDown' is positive")
        }
      }
    }
  }

  property("Operation 'next' on class 'Launched' should return an 'Exploding' value if the 'countDown' is zero or negative") {
    Prop.forAll { (launched: Launched) =>
      (launched.countDown <= 0) ==> {
        launched.next match {
          case exploding: Exploding =>
            assertEquals(exploding.particles.value.size, launched.numberOfParticles, "'Exploding' value should have the same number of particles as the 'numberOfParticles' of the 'Launched' value")
            assert(exploding.particles.value.forall(_.color == launched.particlesColor), "'particles' should all be of the same color as the 'particlesColor' of the 'Launched' value")
            assert(exploding.particles.value.forall(_.position == launched.position), "'particles' should have the same 'position' as the 'position' of the 'Launched' value")
            Prop.passed
          case _ =>
            fail("'next' should return a value of type 'Exploding' if the 'countDown' is zero or negative")
        }
      }
    }
  }

  property("Operation 'next' on class 'Exploding' should return an 'Exploding' value with a decremented 'countDown' until it is zero or negative") {
    Prop.forAll { (exploding: Exploding) =>
      (exploding.countDown > 0) ==> {
        exploding.next match {
          case nextExploding: Exploding =>
            assertEquals(nextExploding.countDown, exploding.countDown - 1, "'countDown' must be decremented by one")
            assertEquals(nextExploding.particles, exploding.particles.next, "'particles' must be updated to their next state")
            Prop.passed
          case _ =>
            fail("'next' should return a value of type 'Exploding' as long as the 'countDown' is positive")
        }
      }
    }
  }

  property("Operation 'next' on class 'Exploding' should return a 'Done' value if the 'countDown' is zero or negative") {
    Prop.forAll { (exploding: Exploding) =>
      (exploding.countDown <= 0) ==> {
        exploding.next match {
          case Done => Prop.passed
          case _ => fail("'next' should return the value 'Done' if the 'countDown' is zero or negative")
        }
      }
    }
  }

  property("Operation 'next' on class 'Particle' should return a 'Particle' value that has moved") {
    Prop.forAll { (particle: Particle) =>
      val nextParticle = particle.next
      val expectedHorizontalSpeed = Motion.drag(particle.horizontalSpeed)
      val expectedVerticalSpeed = Motion.drag(particle.verticalSpeed - Settings.gravity)
      assertEqualsDouble(nextParticle.horizontalSpeed, expectedHorizontalSpeed, 0.01, "'horizontalSpeed' should be reduced because of air friction")
      assertEqualsDouble(nextParticle.verticalSpeed, expectedVerticalSpeed, 0.01, "'verticalSpeed' should be subject to air friction and to gravity")
      assertEquals(nextParticle.color, particle.color, "'color' should not be changed")
      Prop.passed
    }
  }

  def property(name: String)(prop: => Prop)(using Location) =
    test(name)(checkProperty(prop))

  // We don't use ScalaCheckSuite in this assignment to provide simpler error messages to the students
  def checkProperty(prop: Prop): Unit =
    val result = org.scalacheck.Test.check(org.scalacheck.Test.Parameters.default, prop)
    def failure(labels: Set[String], fallback: String): Nothing =
      if labels.isEmpty then throw AssertionError(fallback)
      else throw AssertionError(labels.mkString(". "))
    result.status match
      case org.scalacheck.Test.Passed | _: org.scalacheck.Test.Proved => ()
      case org.scalacheck.Test.Failed(_, labels) => failure(labels, "A property failed.")
      case org.scalacheck.Test.PropException(_, e: munit.FailException, labels) => failure(labels, e.message)
      case org.scalacheck.Test.PropException(_, e, labels) => failure(labels, s"An exception was thrown during property evaluation: $e")
      case org.scalacheck.Test.Exhausted => failure(Set.empty, "Unable to generate data.")

end FireworksSuite
