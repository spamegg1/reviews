object CaseClassTask:
  // Exercise: create a case class which represents a dog.
  // Each dog should have a name, a breed, and a favorite toy.
  // Model these features as Strings for now.
  /* Define the case class here */

  def introduceDog(dog: Dog): Unit =
    dog match
      case /* Add a pattern here */ =>
        println(s"This dog's name is $/* put the dog's name here */, it's a(n) $/* put the dog's breed here */, and its favorite toy is a(n) $/* put the dog's favorite toy here */.")


  @main
  def main(): Unit =
    val yuki = Dog("Yuki", "Akita", "ball")
    val hoops = Dog("Hoops", "Australian Shepherd", "squeaky pig")
    val bowser = Dog("Bowser", "Chow Chow", "dinosaur bone")

    List(yuki, hoops, bowser).foreach(introduceDog)

