# `flatMap`
`def flatMap[B](f: A => IterableOnce[B]): Iterable[B]`

You can consider `flatMap` as a generalized version of the `map` method. The function `f`, used by `flatMap`, takes every element of the original collection and, instead of returning just one new element of a different (or the same) type, produces a whole new collection of new elements. These collections are then "flattened" by the `flatMap` method, resulting in just one large collection being returned.

Essentially, the same effect can be achieved with `map` followed by `flatten`. `flatten` is another method from the Scala collections. If the original collection is a collection of collections — that is, if every element of it is a collection itself — the `flatten` method will combine them into one new collection.

However, the applications of `flatMap` extend far beyond this simplistic use case. It's probably the most crucial method in functional programming in Scala. We will talk more about this in later chapters about monads and chains of execution. For now, let's focus on a straightforward example to demonstrate how exactly `flatMap` works.

Just as in the `map` example, we will use a list of four cats. But this time, for every cat, we will create a list of cars of different brands but the same color as the cat. Finally, we will use `flatMap to combine all four lists of cars of different brands and colors into one list.

```scala
// We define the Color enum
enum Color:
 case Black, White, Ginger

// We define the CarBrand enum
enum CarBrand:
 case Volkswagen, Mercedes, Toyota

// Let’s import the Color and CarBrand enum values for better readability
import Color._
import CarBrand._

// We define the Cat case class with two fields: name and color
case class Cat(name: String, color: Color)

// We define the Car case class with two fields: brand and color
case class Car(brand: CarBrand, color: Color)

// We create four cats: two black, one white, and one ginger
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// We put them all in a list
val cats = List(felix, snowball, garfield, shadow)

// We define a function that takes a cat and produces a list of cars
def catToCars(cat: Cat): List[Car] =
 List(
   Car(Volkswagen, cat.color),
   Car(Mercedes, cat.color),
   Car(Toyota, cat.color)
)

// And we use the flatMap method and the catToCars function to create a new list of all cars of all colors
val cars: List[Car] = cats.flatMap(catToCars)
```

## Exercise 

Use `flatMap` to implement `collectFurCharacteristics`, which aggregates all fur characteristics of the cats into a single set. 
