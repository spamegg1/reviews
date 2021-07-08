package org.epfl.testing

class ProgramSuite extends munit.FunSuite:

  test("add") {
    val obtained = add(1, 1)
    val expected = 2
    assertEquals(obtained, expected)
  }
  
  test("fibonacci") {
    assertEquals(fibonacci(1), 0)
    assertEquals(fibonacci(2), 1)
    assertEquals(fibonacci(3), 1)
    assertEquals(fibonacci(4), 2)
    assertEquals(fibonacci(5), 3)
  }
  
end ProgramSuite
