package fireworks

import doodle.core.{Angle, Point}
import org.scalacheck.{Arbitrary, Gen, Prop, Test}

trait FireworksGenerators:

  given pointGenerator: Gen[Point] =
    for
      x <- Arbitrary.arbitrary[Int]
      y <- Arbitrary.arbitrary[Int]
    yield Point(x.toDouble, y.toDouble)

  given angleGenerator: Gen[Angle] =
    for
      theta <- Gen.chooseNum(0.0, math.Pi * 2)
    yield Angle.radians(theta)

  given waitingGenerator: Gen[Waiting] =
    for
      color <- Gen.oneOf(Settings.colors.toSeq)
      numberOfParticles <- Gen.posNum[Int]
      position <- pointGenerator
      countDown <- Arbitrary.arbitrary[Int]
    yield Waiting(countDown, position, numberOfParticles, color)

  given launchedGenerator: Gen[Launched] =
    for
      color <- Gen.oneOf(Settings.colors.toSeq)
      numberOfParticles <- Gen.posNum[Int]
      position <- pointGenerator
      direction <- angleGenerator
      countDown <- Arbitrary.arbitrary[Int]
    yield Launched(countDown, position, direction, numberOfParticles, color)

  given particleGenerator: Gen[Particle] =
    for
      hSpeed <- Arbitrary.arbitrary[Double]
      vSpeed <- Arbitrary.arbitrary[Double]
      position <- pointGenerator
      color <- Gen.oneOf(Settings.colors.toSeq)
    yield Particle(hSpeed, vSpeed, position, color)

  given particlesGenerator: Gen[Particles] =
    for
      particles <- Gen.nonEmptyListOf(particleGenerator)
    yield Particles(particles)

  given explodingGenerator: Gen[Exploding] =
    for
      particles <- particlesGenerator
      countDown <- Arbitrary.arbitrary[Int]
    yield Exploding(countDown, particles)

  given arbitraryGenerator[A](using gen: Gen[A]): Arbitrary[A] = Arbitrary(gen)

end FireworksGenerators
