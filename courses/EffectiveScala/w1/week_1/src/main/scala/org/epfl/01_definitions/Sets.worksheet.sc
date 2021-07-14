enum Shape:
  case Diamond, Squiggle, Oval

enum Num:
  case One, Two, Three

enum Color:
  case Red, Green, Purple

enum Shading:
  case Open, Striped, Solid

case class Card(shape: Shape, number: Num, color: Color, shading: Shading)

def allSame[A](things: List[A]): Boolean =
  things.distinct.size == 1

def allDifferent[A](things: List[A]): Boolean =
  things.distinct.size == things.size

def check[A](things: List[A]): Boolean =
  allSame(things) || allDifferent(things)

def isValidSet(cards: List[Card]): Boolean =
  val shapes = cards.map(_.shape)
  val numbers = cards.map(_.number)
  val colors = cards.map(_.color)
  val shadings = cards.map(_.shading)
  check(shapes) && check(numbers) && check(colors) && check(shadings)

val deck1 = List(
  Card(Shape.Diamond, Num.One, Color.Purple, Shading.Striped),
  Card(Shape.Squiggle, Num.Two, Color.Red, Shading.Open),
  Card(Shape.Oval, Num.Three, Color.Green, Shading.Solid)
)

val deck2 = List(
  Card(Shape.Diamond, Num.One, Color.Red, Shading.Striped),
  Card(Shape.Diamond, Num.Two, Color.Red, Shading.Open),
  Card(Shape.Diamond, Num.Three, Color.Red, Shading.Solid)
)

val deck3 = List(
  Card(Shape.Diamond, Num.One, Color.Red, Shading.Striped),
  Card(Shape.Diamond, Num.Two, Color.Red, Shading.Open),
  Card(Shape.Oval, Num.Three, Color.Red, Shading.Solid)
)

isValidSet(deck1)
isValidSet(deck2)
isValidSet(deck3)

























