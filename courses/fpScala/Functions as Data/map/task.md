# `map`

`def map[B](f: A => B): Iterable[B]`

The `map` method works on any Scala collection that implements `Iterable`. 
It takes a function `f` and applies it to each element in the collection, similar to `foreach`. However, in the case of `map`, we are more interested in the results of `f` and not its side effects. 
As you can see from the declaration of `f`, it takes an element of the original collection of type `A` and returns a new element of type `B`. 
Finally, the map method returns a new collection of elements of type `B`. 
In a special case, `B` can be the same as `A`, so for example, we use the `map` method to take a collection of cats of certain colors and create a new collection of cats of different colors. 
But, we can also, for example, take a collection of cats and create a collection of cars with colors that match the colors of our cats.

```scala
// We define the Color enum
enum Color:
 case Black, White, Ginger

// Let's import the Color enum values for better readability
import Color._

// We define the Cat case class with two fields: name and color
case class Cat(name: String, color: Color)

// We define the Car case class with only one field: color
case class Car(color: Color)

// We create four cats: two black, one white, and one ginger
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// and we put them all in a list
val cats: List[Cat] = Set(felix, snowball, garfield, shadow)

// We define the catToCar function, which for a given cat creates a car with the same color
def catToCar(cat: Cat): Car = Car(color = cat.color)

// Using the map method and the catToCar function, we create a new list of cars with the same color as our cats
val cars: List[Car] = cats.map(catToCar)
```

Please note that in this example, we've placed our cats in a `List` rather than a `Set`. 
This is because we want to avoid confusion — for each cat, we want a car of a matching color. 
However, since we have two black cats of the same color, `Felix` and `Shadow`, our `catToCar` function will produce two identical black cars. Since a `Set` is a collection that holds only unique elements, one of them would have to be removed.
Therefore, instead of a `Set`, we need a collection that allows multiple identical elements, like a `List`.

## Exercise 

In functional programming, we usually separate performing side effects from computations. 
For example, if we want to print all fur characteristics of a cat, we first transform each characteristic into a `String`, and then output each one in a separate step. 
Implement the `furCharacteristicsDescription` function, which completes this transformation using `map`. 
