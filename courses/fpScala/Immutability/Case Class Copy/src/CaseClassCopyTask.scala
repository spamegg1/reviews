object CaseClassCopyTask:
  case class User( firstName: String,
                   lastName: String,
                   email: Option[String] = None,
                   twitterHandle: Option[String] = None,
                   instagramHandle: Option[String] = None
                 ):

    // `myCopy` should function in the exact same way as the auto-generated copy does.
    def myCopy(firstName: String = this.firstName,
               lastName: String = this.lastName,
               email: Option[String] = this.email,
               twitterHandle: Option[String] = this.twitterHandle,
               instagramHandle: Option[String] = this.instagramHandle
              ): User = /* put parameters here */
      /* put implementation here */
      copy(firstName, lastName, email, twitterHandle, instagramHandle)


  @main
  def main() =
    // usage
    val originalUser = User("Jane", "Doe", Some("jane.doe@example.com"))
    // Create a copy of originalUser, changing the email and adding a twitter handle
    val updatedUser = originalUser.copy(
      email = Some("new.jane.doe@example.com"),
      twitterHandle = Some("@newJaneDoe")
    )
    val myUpdatedUser = originalUser.myCopy(
      email = Some("new.jane.doe@example.com"),
      twitterHandle = Some("@newJaneDoe")
    )

    println(s"Original user: $originalUser")
    // prints out User("Jane", "Doe", Some("jane.doe@example.com"), None, None)

    println(s"Updated user: $updatedUser")
    // prints out User("Jane", "Doe", Some("new.jane.doe@example.com"), Some("@newJaneDoe"), None)

    println(s"User updated with myCopy: $updatedUser")
// prints out User("Jane", "Doe", Some("new.jane.doe@example.com"), Some("@newJaneDoe"), None)
