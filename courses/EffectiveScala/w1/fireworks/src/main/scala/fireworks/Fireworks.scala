package fireworks

import doodle.core.{Angle, Color, Point}
import scala.util.Random

/**
 * A firework can be in the following states:
 *
 * - [[Waiting]] to be launched,
 * - [[Launched]] (it has not exploded yet, it’s going up in the sky),
 * - [[Exploding]],
 * - [[Done]] (all the particles have burnt).
 */
sealed trait Firework

object Firework:

  /**
   * @return A random initial state of a firework
   */
  def init(): Firework = Waiting.init()

  /**
   * @return The next state of a firework
   * @param firework The current state of a firework
   *
   * If the firework is in the [[Done]] state, it stays in the
   * same [[Done]] state. Otherwise, the next state is computed
   * by calling the operation `next` on the underlying state type
   * (see e.g., [[Waiting.next]]).
   *
   * Hint: choose what to do by pattern matching on the given `firework`.
   * You will have to use “typed patterns” to match on case classes and
   * “literal patterns” to match on case objects.
   */
  def next(firework: Firework): Firework = firework match                // TODO
    case Done         => Done
    case w: Waiting   => w.next
    case l: Launched  => l.next
    case e: Exploding => e.next

end Firework

/**
 * A firework waiting to be launched
 * @param countDown         count-down before transitioning to [[Launched]]
 * @param startPosition     location where this firework will be launched
 * @param numberOfParticles number of particles this will produce when exploding
 * @param particlesColor    color of this firework
 */
case class Waiting(countDown: Int,
                   startPosition: Point,
                   numberOfParticles: Int,
                   particlesColor: Color) extends Firework:

  /**
   * @return The next state of this firework
   *
   * If the [[countDown]] is greater than zero, the firework stays in the
   * [[Waiting]] state and simply decrements the [[countDown]] of one unit.
   * Otherwise, it transitions to the [[Launched]] state.
   *
   * Note that we use `def` here instead of `val`
   * to avoid evaluating eagerly all the next states.
   *
   * Hint: use the [[Launched.init]] operation to transition the
   * firework to the [[Launched]] state.
   */
  def next: Firework =                                                   // TODO
    if countDown > 0 then copy(countDown = countDown - 1)
    else Launched.init(startPosition, numberOfParticles, particlesColor)

end Waiting

object Waiting:

  /**
   * @return A randomly initialized firework in a [[Waiting]] state
   */
  def init(): Waiting =
    // Take one color, randomly, among `Settings.colors`
    val color = Settings.colors(Random.nextInt(Settings.colors.length))
    val numberOfParticles = 40

    // Random initial position in the image boundary
    val position = Point(
      (Random.nextInt(Settings.width / 2) - Settings.width / 4).toDouble,
      (-Settings.height / 2).toDouble
    )

    // Random count-down between 0 and 60
    val countDown = Random.nextInt(60)

    Waiting(countDown, position, numberOfParticles, color)

end Waiting

/**
 * A firework going up in the sky before exploding.
 *
 * We assume that the speed of the firework is constant during this phase.
 *
 * @param countDown         count-down before exploding
 * @param position          current position
 * @param direction         current direction
 * @param numberOfParticles number of particles this will produce when exploding
 * @param particlesColor    color of this firework
 */
case class Launched(countDown: Int,
                    position: Point,
                    direction: Angle,
                    numberOfParticles: Int,
                    particlesColor: Color) extends Firework:
  /**
   * @return The next state of this firework
   *
   *         As long as the [[countDown]] is greater than zero,
   *         the firework stays in the [[Launched]] state:
   *         it moves one step further in its [[direction]]
   *         and decrements its [[countDown]] of one unit.
   *         Otherwise, it transitions to the [[Exploding]] state.
   *
   *         Hints: use the operation [[Motion.movePoint]]
   *         to compute the next position of the firework,
   *         use the operation [[Exploding.init]] to transition the firework to
   *         the [[Exploding]] state,
   *         and use the constant [[Settings.propulsionSpeed]]
   *         for the speed of the firework.
   */
  def next: Firework =                                                   // TODO
    if countDown > 0 then
      val newPosition =
        Motion.movePoint(position, direction,
                         Settings.propulsionSpeed)
      Launched(countDown - 1,
               newPosition,
               direction,
               numberOfParticles,
               particlesColor)
    else Exploding.init(numberOfParticles,
                        direction,
                        position,
                        particlesColor)

end Launched

object Launched:

  /**
   * @return A firework in a [[Launched]] state, with a random direction
   * @param position          position of the launch
   * @param numberOfParticles number of particles this will produce when exploding
   * @param particlesColor    color of this firework
   */
  def init(position: Point, numberOfParticles: Int, particlesColor: Color): Launched =
    // A random vertical direction
    val direction = Angle(math.Pi / 2 + (Random.nextDouble() - 0.5) / 5)
    Launched(countDown = 30,
             position,
             direction,
             numberOfParticles,
             particlesColor)

end Launched

/**
 * A firework that explodes
 * @param countDown count-down before the explosion is finished
 * @param particles Particles of this firework
 */
case class Exploding(countDown: Int, particles: Particles) extends Firework:

  /**
   * @return The next state of this firework
   *
   * As long as the [[countDown]] is greater than zero, the firework stays in
   * the [[Exploding]] state: it updates the state of
   * its [[particles]] and decrements its [[countDown]].
   * Otherwise, it transitions to the [[Done]] state.
   *
   * Hint: use the operation [[Particles.next]] to compute the
   *       next state of the particles of this firework.
   */
  def next: Firework =                                                   // TODO
    if countDown > 0 then
      Exploding(countDown - 1, particles.next)
    else Done

end Exploding

object Exploding:

  /**
   * @return The initial state of a firework that is exploding
   * @param numberOfParticles number of particles in the explosion
   * @param direction         direction of the firework when launched
   * @param position          position of the explosion
   * @param color             color of the firework
   */
  def init(numberOfParticles: Int,
           direction: Angle,
           position: Point,
           color: Color): Exploding =
    // Create a group of `numberOfParticles` random particles
    val particles = List.fill(numberOfParticles)
                             (Particle.init(direction, position, color))
    Exploding(countDown = 30, Particles(particles))

end Exploding

/**
 * A firework that has been completely burnt
 */
case object Done extends Firework

/**
 * A single particle in a firework explosion
 * @param horizontalSpeed horizontal component of the particle speed
 * @param verticalSpeed   vertical component of the particle speed
 * @param position        position of the particle
 * @param color           color of the particle
 */
case class Particle(horizontalSpeed: Double,
                    verticalSpeed: Double,
                    position: Point,
                    color: Color):

  /**
   * @return The next state of this particle
   *
   *         Particles are subject to gravity and friction with air.
   *
   *         Hint: use the operation [[Motion.drag]] to simulate the friction
   *         with the surrounding air,
   *         and the constant [[Settings.gravity]] to simulate the gravity.
   */
  def next: Particle =
    // Horizontal speed is only subject to air friction, its next value
    // should be the current value reduced by air friction
    // Hint: use the operation `Motion.drag`
    val updatedHorizontalSpeed: Double = Motion.drag(horizontalSpeed)    // TODO

    // Vertical speed is subject to both air friction and gravity, its next
    // value should be the current value minus the gravity, then reduced by
    // air friction
    val updatedVerticalSpeed: Double =                                   // TODO
      Motion.drag(verticalSpeed - Settings.gravity)

    // Particle position is updated according to its new speed
    val updatedPosition = Point(position.x + updatedHorizontalSpeed,
                                position.y + updatedVerticalSpeed)

    // Construct a new particle with the updated position and speed
    Particle(updatedHorizontalSpeed,                                     // TODO
             updatedVerticalSpeed,
             updatedPosition, color)

end Particle

// --- Assignment ends here! Once you have reached this point you
// --- can run your program (invoke the `run` sbt task) and enjoy
// --- the show! … or fix your implementation, if necessary…

/**
 * A group of particles constituting an explosion
 * @param value group of particles
 */
case class Particles(value: List[Particle]):
  /**
   * @return The next state of this group of particles
   */
  def next: Particles = Particles(value map (_.next))

end Particles

object Particle:

  /**
   * @return The initial state of a particle
   * @param initialDirection direction of the firework during 
                             its [[Launched]] phase
   * @param position         position of the particle
   * @param color            color of the particle
   */
  def init(initialDirection: Angle,
           position: Point, color: Color): Particle =

    val r = Angle(Random.nextDouble() * math.Pi / 4 - math.Pi / 8)
    val angle = initialDirection + r
    val velocity = Random.nextDouble() * 10 + 20

    Particle(angle.cos * velocity, angle.sin * velocity, position, color)

end Particle

/**
 * Utility operations to handle motion
 */
object Motion:

  /**
   * @return The next position of the given `point`, assuming that
   *         it moves towards the given `direction` at the given `speed`
   * @param point     current position of the point
   * @param direction direction of the point
   */
  def movePoint(point: Point, direction: Angle, speed: Double): Point =
    Point(point.x + direction.cos * speed, point.y + direction.sin * speed)

  /**
   * @return The given `speed` after applying friction to it
   */
  def drag(speed: Double): Double =
    if speed > Settings.friction then
      math.max(speed - Settings.friction, 0)
    else if speed < -Settings.friction then
      math.min(speed + Settings.friction, 0)
    else 0

end Motion

object Settings:

  val width = 800
  val height = 600
  val colors: Array[Color] =
    Array(Color.red, Color.yellow, Color.white, Color.blue, Color.violet)

  // These values have no standard units. 
  // They just work well for the animation.
  val friction = 0.2
  val gravity = 1.5
  val propulsionSpeed = 8.0

end Settings
