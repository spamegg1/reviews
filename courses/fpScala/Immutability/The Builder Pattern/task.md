## The Builder Pattern

The Builder pattern is a design pattern often used in object-oriented programming to provide 
a flexible solution for constructing complex objects. 
It's especially handy when an object needs to be created with numerous possible configuration options. 
The pattern involves separating the construction of a complex object from its representation 
so that the same construction process can make different representations.

Here's why the Builder Pattern is used:
* To encapsulate the construction logic of a complex object.
* To allow an object to be constructed step by step, often through method chaining.
* To avoid having constructors with many parameters, which can be confusing and error-prone (often referred to as the telescoping constructor anti-pattern).

Below is a Scala example using the Builder Pattern to create instances of a `User` case class, with mandatory `firstName` 
and `lastName` fields and optional `email`, `twitterHandle`, and `instagramHandle` fields.

Note that:
* The `User` case class defines a user with mandatory `firstName` and `lastName`, optional `email`, `twitterHandle`, and `instagramHandle`.
* `UserBuilder` facilitates the creation of a `User` object. 
  The mandatory parameters are specified in the builder's constructor, while methods like `setEmail`, `setTwitterHandle`, 
  and `setInstagramHandle` are available to set optional parameters. 
  Each of these methods returns the builder itself, enabling method chaining.
* Finally, the execution of the `build` method employs all specified parameters (whether default or set) to construct a `User` object.

This pattern keeps object creation understandable and clean, mainly when dealing with objects that can have multiple optional parameters.



```
case class User( firstName: String,
                 lastName: String,
                 email: Option[String] = None,
                 twitterHandle: Option[String] = None,
                 instagramHandle: Option[String] = None
               )
               
class UserBuilder(private val firstName: String, private val lastName: String):
  private var email: Option[String] = None
  private var twitterHandle: Option[String] = None
  private var instagramHandle: Option[String] = None
  
  def setEmail(e: String): UserBuilder =
    email = Some(e)
    this

  def setTwitterHandle(t: String): UserBuilder =
    twitterHandle = Some(t)
    this

  def setInstagramHandle(i: String): UserBuilder =
    instagramHandle = Some(i)
    this

  def build: User =
    User(firstName, lastName, email, twitterHandle, instagramHandle)

  // usage
  val user: User = new UserBuilder("John", "Doe")
    .setEmail("john.doe@example.com")
    .setTwitterHandle("@johnDoe")
    .setInstagramHandle("@johnDoe_insta")
    .build
   
  println(user)
  // prints out User("John", "Doe", Some("john.doe@example.com"), Some("@johndoe"), Some("@johnDoe_insta"))
```

### Exercise

Implement the builder pattern for constructing a message that has optional sender, receiver, and content fields. 
