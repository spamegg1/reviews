case class Card(shape: Shape, number: Int, color: Color, shading: Shading)

enum Shape:
  case Diamond, Squiggle, Oval

enum Color:
  case Red, Green, Purple

enum Shading:
  case Open, Striped, Solid

val deck = List(
  Card(Shape.Diamond,  1, Color.Purple, Shading.Striped),
  Card(Shape.Squiggle, 2, Color.Red,    Shading.Open),
  Card(Shape.Oval,     3, Color.Green,  Shading.Solid)
)

val nonsensicalCard = Card(Shape.Diamond, 0, Color.Purple, Shading.Striped)

