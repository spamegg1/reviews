package fireworks

import doodle.core.Color
import doodle.java2d.*
import doodle.interact.syntax.*
import doodle.syntax.*
import monix.reactive.Observable
import cats.instances.unit.*
import cats.instances.list.*

/**
 * Program entry point.
 *
 * Create 10 random fireworks and compute their evolution over time until they are all done.
 *
 * You should NOT modify this file.
 */
@main def run(): Unit =

  // Create a list of 10 fireworks randomly initialized
  val initFireworks = List.fill(10)(Firework.init())

  // Compute the evolution of the fireworks until they are all in state `Done`
  val fireworksOverTime =
    Observable.unfold(initFireworks) { fireworks =>
      if fireworks.isEmpty then
        None
      else
        val updatedFireworks =
          fireworks.flatMap { firework =>
            Firework.next(firework) match
              case Done  => None
              case other => Some(other)
          }
        Some((fireworks, updatedFireworks))
    }

  def launchedPicture(launched: Launched): Picture[Unit] =
    shape
      .circle[Algebra, Drawing](diameter = 5)
      .fillColor(Color.lightGoldenrodYellow)
      .at(launched.position)

  def explodingPicture(exploding: Exploding): Picture[Unit] =
    exploding.particles.value.map { particle =>
      shape
        .circle[Algebra, Drawing](diameter = 5)
        .fillColor(particle.color)
        .at(particle.position)
    }.allOn

  val pictures =
    fireworksOverTime.map { fireworks =>
      fireworks.map { firework =>
        firework match
          case _: Waiting           => shape.empty[Algebra, Drawing]
          case Done                 => shape.empty[Algebra, Drawing]
          case launched: Launched   => launchedPicture(launched)
          case exploding: Exploding => explodingPicture(exploding)
      }.allOn
    }

  val frame = Frame.size(Settings.width.toDouble, Settings.height.toDouble).background(Color.black)
  pictures.animate(frame)

end run
