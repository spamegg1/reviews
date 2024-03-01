# Case Class
In Scala, a case class is a special kind of class that comes equipped with some useful default behaviors and methods, 
beneficial for modeling immutable data.
While Scala's compiler does place some limitations on it, it concurrently enriches it with features that 
otherwise we would have to code manually:

1. Fields of a case class are immutable by default. 
   To modify this, they need to be explicitly marked as `var`, but this practice is frowned upon as highly 
   unidiomatic in Scala. 
   Instances of case classes should serve as immutable data structures, 
   as modifying them can result in less intuitive and readable code.
2. A case class provides a default constructor with public, read-only parameters, thus reducing theboiler-plate associated with case class instantiation.
3. Scala automatically defines some useful methods for case classes, such as `toString`, `hashCode`, and `equals`. 
   The `toString` method gives a string representation of the object, 
   `hashCode` is used for hashing collections like `HashSet` and `HashMap`, 
   and `equals` checks structural equality, rather than reference equality 
   (i.e., checks the equality of the respective fields of the case class, 
   rather than verifying if the two references point to the same object).
4. Case classes come with the `copy` method that can be used to create a copy of the case class instance: 
   exactly the same as the original or with some parameters modified 
   (the signature of the `copy` method mirrors that of the default constructor).
5. Scala automatically creates a companion object for the case class, 
   which contains factory `apply` and `unapply` methods. 
   The `apply` method matches the default constructor and lets you create class instances without using the `new` keyword. 
   The `unapply` method is used in pattern matching.
6. Case classes can be conveniently used in pattern matching, as they have the default `unapply` method, 
   which lets you destructure the case class instance.
7. On top of that, case classes are conventionally not extended. 
   They can extend traits and other classes, but they shouldn't be used as superclasses for other classes. 
   Technically though, extending case classes is not forbidden by default. 
   If you want to ensure that a case class is be extended, mark it with the `final` keyword.

You should already be familiar with some of these features, as we used them in the previous module. 
The difference here is that we want you to focus on distinct aspects that you'll see in the examples and exercises.

Below is a simple example of a case class that models cats. 
We create a `Cat` instance called `myCat` and then use pattern matching against `Cat` to access its name and color.  

```scala 3
case class Cat(name: String, color: String)

val myCat = Cat("Whiskers", "white")
myCat match {
  case Cat(name, color) => println(s"I have a $color cat named $name.")
}
```

## Exercise 

Create a case class that represents a dog.
Each dog should have a name, a breed, and a favorite toy.
For the time being, model these features as Strings.
Use pattern matching to introduce the dog. 
