object SmartConstructorsTask:
  class Dog /* prevent the user from by-passing the smart constructor */ (var name: String, var breed: String, var owner: String):
    override def toString(): String = s"Dog($name,$breed,$owner)"

  object Dog:
    val defaultBreed = /* pick a string to use as a default value for a breed */
    val defaultOwner = /* pick a string to use as a devalue value for an owner */

    // Implement the smart constructor which uses the default values
    // when the breed or the owner are not known
    def apply(name: String, breed: Option[String], owner: Option[String]): Dog =
      /* implement the method */

  @main
  def main(): Unit = {
    val jack = Dog("Yuki", None, None)
    val yuki = Dog("Yuki", Some("Akita"), None)
    val hoops = Dog("Hoops", Some("Australian Shepherd"), Some("Alex"))

    List(jack, yuki, hoops).foreach(x => println(x.toString()))
  }

























