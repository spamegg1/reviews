package org.epfl.testing

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

class ProgramProperties extends munit.ScalaCheckSuite:

  val fibonacciDomain = Gen.choose(1, 47)
  
  property("fibonacci(n) == fibonaci(n - 1) + fibonacci(n - 2)") {
    forAll(fibonacciDomain.suchThat(_ >= 3)) { (n: Int) =>
      fibonacci(n) == fibonacci(n - 1) + fibonacci(n - 2)
    }
  }
  
  property("Fibonacci numbers are positive") {
    forAll(fibonacciDomain) { (n: Int) =>
      fibonacci(n) >= 0
    }
  }
  
end ProgramProperties