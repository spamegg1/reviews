
def house(facade: Double, window: Double) =
  val door = 2 * 1
  val subtractedArea = door + window * 2
  facade - subtractedArea

val alicesHouse = house(5 * 3, 1 * 1)

val bobsHouse = house(4 * 4, 1.5 * 1.5)

def showPrice(paintingArea: Double, paintPrice: Double): String =
  val price = paintingArea * paintPrice
  if price > 100 then "This is too expensive"
  else if price < 10 then "This is very cheap"
  else price.toString

showPrice(alicesHouse, 2)

def marathonDuration(speed: Double): Double =
  val distance = 42.195 // (km)
  val duration = distance / speed
  duration * 60 // (minutes)

marathonDuration(12)

marathonDuration(14)
