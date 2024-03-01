# `filter`

```def filter(pred: A => Boolean): Iterable[A]```

The `filter` method works on any Scala collection that implements `Iterable`. 
It takes a predicate that returns `true` or `false` for every element in the collection, and it produces a new 
collection that comprises only the elements for which the predicate returns `true`.
The `filter` method returns an empty collection if the predicate does not succeed on any element.

We have already used `filter` in a previous example, but for consistency, let's consider the example below one more time. 

```scala
// We define the Color enum
enum Color:
 case Black, White, Ginger

// We define the Cat case class with two fields: name and color
case class Cat(name: String, color: Color)

// Let’s import the Color enum values for better readability
import Color._

// We create four cats, two black, one white, and one ginger
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// We put them all in a set
val cats = Set(felix, snowball, garfield, shadow)

// We filter the set to keep only black cats
val blackCats = cats.filter { cat => cat.color == Black }

```


## Exercise

In the exercises, we will be working with a more detailed representation of cats than in the lessons. 
Check out the `Cat` class in `src/Cat.scala`.
A cat has multiple characteristics: its name, breed, color, pattern, and a set of additional fur characteristics, such as
`Fluffy` or `SleekHaired`.
Familiarize yourself with the corresponding definitions in other files in `src/`.

Imagine you've entered an animal shelter intending to adopt a cat.
There are multiple cats available, and you wish to adopt a cat with one of the following characteristics:

* The cat is calico.
* The cat is fluffy.
* The cat's breed is Abyssinian.

To simplify decision making, you first identify all cats which possess at least one of the characteristics above. Your task is to implement the necessary functions and then apply the filter. 
