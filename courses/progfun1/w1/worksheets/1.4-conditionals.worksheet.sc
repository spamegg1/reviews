def loop: Boolean = loop

def x = loop

// val y = loop // StackOverFlow

def and(x: Boolean, y: => Boolean): Boolean =
  if x then y else false

and(true, true)
and(true, false)
and(false, true)
and(false, false)

and(false, loop)
