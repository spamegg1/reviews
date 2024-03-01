## Case Class Copy

In Scala, case classes automatically come equipped with a few handy methods upon declaration, one of which is the `copy` method. 
The `copy` method is used to create a new instance of the case class, which is a copy of the original instance; however, you can also 
modify some (or none) of the fields during the copying process. 
This feature adheres to functional programming principles, where immutability is often favored. 
You can derive new instances while maintaining the immutability of existing instances, and so, for example, 
avoid bugs where two threads work on the same data structure, each assuming that it is the sole modifier of it.

Another valuable characteristic of the `copy` method is that it’s a convenient and readable means of creating new instances of the same case class. 
Instead of building one from scratch, you can grab an existing instance and make a copy modified to your liking.

Below, you will find a Scala example using a User case class with mandatory `firstName` and `lastName` fields, along with optional 
`email`, `twitterHandle`, and `instagramHandle` fields. 
We will first create one user with its default constructor and then another with the `copy` method of the first one. 

Note that:

* `originalUser` is initially an instance of `User` with `firstName = "Jane"`, `lastName = "Doe"`, and `email = "jane.doe@example.com"`. 
  The other fields use their default values (i.e., `None`).
* `updatedUser` is created using the copy method on `originalUser`. 
  This creates a new instance with the same field values as `originalUser`, except for the fields provided as parameters to `copy`:
   * `email` is updated to `"new.jane.doe@example.com"`
   * `twitterHandle` is set to `"@newJaneDoe"`
* `originalUser` remains unmodified after the `copy` method is used, adhering to the principle of immutability.

``` 
case class User( firstName: String,
                 lastName: String,
                 email: Option[String] = None,
                 twitterHandle: Option[String] = None,
                 instagramHandle: Option[String] = None
               )

// usage
val originalUser = User("Jane", "Doe", Some("jane.doe@example.com"))

// Create a copy of originalUser, changing the email and adding a twitter handle
val updatedUser = originalUser.copy(
email = Some("new.jane.doe@example.com"),
twitterHandle = Some("@newJaneDoe")
)

println(s"Original user: $originalUser")
// prints out User("Jane", "Doe", Some("jane.doe@example.com"), None, None)

println(s"Updated user: $updatedUser")
// prints out User("Jane", "Doe", Some("new.jane.doe@example.com"), Some("@newJaneDoe"), None)
```

### Exercise 

Let's unravel the `copy` function. 
Implement your own function `myCopy` that operates in exactly the same way as `copy` does. 
You should be able to pass values only for those fields you wish to modify. 
As a result, a new copy of the instance should be created. 
