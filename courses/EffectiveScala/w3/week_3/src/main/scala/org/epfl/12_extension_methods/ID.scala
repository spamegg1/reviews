object UserID:

  opaque type UserID = Long

  def parse(string: String): Option[UserID] = string.toLongOption

  extension (userID: UserID)
    def value: Long = userID

end UserID

@main def run(): Unit =
  val maybeUserID = UserID.parse("42")
  maybeUserID match
    case Some(userID) => println(s"Successfully parsed ${userID.value}")
    case None         => println("Unable to parse user ID")
