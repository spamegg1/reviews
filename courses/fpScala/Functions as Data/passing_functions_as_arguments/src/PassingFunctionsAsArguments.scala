object PassingFunctionsAsArguments:
  // We model colors as enums.
  enum Color:
    case Black
    case White
    case Ginger

  // We model a cat as a class. In this example we are interested only in color of the cat.
  class Cat(val color: Color)

  // We create our bag (a set) of cats. Each cat has a different color.
  val bagOfCats: Set[Cat] = Set(Cat(Color.Black), Cat(Color.White), Cat(Color.Ginger))

  // Implement a function which checks if a cat is white go ginger
  def isCatWhiteOrGinger(cat: Cat): Boolean = /* Check if the cat is white */
    cat.color == Color.White

  // Pass the appropriate function into `filter` to create a bag of white cats.
  val bagOfWhiteOrGingerCats: Set[Cat] = bagOfCats.filter(/* Pass a function as an argument */
    isCatWhiteOrGinger
  )