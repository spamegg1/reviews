# Smart Constructors and the `apply` Method

In Scala, `apply` is a special method that can be invoked without specifying its name.

```scala 3
class Cat:
  def apply(): String = "meow"

  val cat = Cat()
  cat() // returns "meow"
```

Technically this sums it up — you can implement `apply` any way you want, for any reason you want. 
However, by convention, the most popular way to use `apply` is as a smart constructor. 
This convention is very important, and we would advise you to follow it. 

There are a few other ways you can use `apply`.
For example, the Scala collections library often uses it to retrieve data from a collection. This might look 
as if Scala has traded the square brackets, common in more traditional languages, for parentheses:

```scala 3
val entry1 = listOfEntries(5) // listOfEntries: List[Entry]
val entry2 = listOfEntries.apply(5) // this is the same as above
``` 

This use is popular enough that people understand it when they see it. However, if you try to use it for something really different,
you may make your code harder to read for other Scala developers. 
The default expectation is that a pair of parentheses after a name indicates a call to a smart constructor.
A smart constructor is a design pattern often used in functional programming languages. 
Its main purpose is to encapsulate the creation logic of an object, thus enforcing some constraints or rules 
whenever an instance of a class is created.
For example, you can use it to ensure that the object is always created in a valid state.

This pattern can be especially useful in situations where:
* The construction of an object is complex and needs to be abstracted away.
* You want to control how objects are created and ensure they're always in a valid state.
* You need to enforce a specific protocol for object creation, such as caching objects, creating singleton objects, or generating objects through a factory. 

The idiomatic way to use `apply` as a smart constructor is to place it in the companion object of a class 
and call it with the name of the class and a pair of parentheses. 
For example, let's consider again the `Cat` class with a companion object that has an `apply` method:

```scala 3
class Cat private (val name: String, val age: Int)

object Cat:
  def apply(name: String, age: Int): Cat =
    if (age < 0) new Cat(name, 0)
    else new Cat(name, age)

  val fluffy = Cat("Fluffy", -5) // the age of Fluffy is set to 0, not -5
```

The `Cat` class has a primary constructor that takes a `String` and an `Int` to set the name and age of the new cat, respectivelym. 
Besides, we create a companion object and define the `apply` method in it. 
This way, when we later call `Cat("fluffy", -5)`, the `apply` method, not the primary constructor, is invoked. 
In the `apply` method, we check the provided age of the cat, and if it's less than zero, we create a cat instance 
with the age set to zero, instead of the input age.

Please also notice how we distinguish between calling the primary constructor and the `apply` method. 
When we call `Cat("Fluffy", -5)`, the Scala 3 compiler checks if a matching `apply` method exists. 
If it does, the `apply` method is called. 
Otherwise, Scala 3 calls the primary constructor (again, if the signature matches). 
This makes the `apply` method transparent to the user. 
If you need to call the primary constructor explicitly, bypassing the `apply` method, you can use the `new` keyword,
for example, `new Cat(name age)`. 
We use this trick in the given example to avoid endless recursion — if we didn't, calling `Cat(name, age)` or `Cat(name, 0)` 
would again call the `apply` method.

You might wonder how to prevent the user from bypassing our `apply` method by calling the primary constructor `new Cat("Fluffy", -5)`. 
Notice that in the first line of the example, where we define the `Cat` class, 
there is a `private` keyword between the name of the class and the parentheses. 
The `private` keyword in this position means that the primary constructor of the class `Cat` can be called only by 
the methods of the class or its companion object. 
This way, we can still use `new Cat(name, age)` in the `apply` method, since it is in the companion object, 
but it's unavailable to the user.

## Exercise 

Consider the `Dog` class, which contains fields for `name`, `breed`, and `owner`. 
Sometimes a dog get lost, and the person who finds it knows as little about the dog as its name on the collar.
Until the microchip is read, there is no way to know who the dog's owner is or what breed the dog is. 
To allow for the creation of `Dog` class instances in these situations, it's wise to use a smart constructor. 
We represent the potentially unknown `breed` and `owner` fields with `Option[String]`. 
Implement the smart constructor that uses `defaultBreed` and `defaultOwner` to initialize the corresponding fields.  
