# Total and Partial Functions
We have already discussed how a function can be categorized as pure or impure. 
A pure function does not produce side effects; instead, it operates solely on its arguments and produces a result. 
Conversely, an impure function may induce side effects and draw input from contexts other than its arguments.

This chapter introduces another important distinction: a function can be ушерук total or partial. 
A total function is one that is defined for all input values within its domain. 
In other words, it yields an output value for every possible input value. 
A total function never throws an exception or results in a runtime error arising from undefined input values. 
On the other hand, a partial function is only defined for a subset of its domain. 
In other words, it may not supply a valid output value for certain input values. 
When a partial function is applied to an undefined input value, it may throw an exception, induce a runtime error, or remain unevaluated.

For example, consider these two functions:
```scala
def multiply(x: Int, y: Int): Int = x * y
def divide(x: Int, y: Int): Int = x / y
```
The first one, `multiply`, is a total function: we can supply it with any two integers and it will return a valid output. 
In contrast, the `divide` function is partial: if we input 0 as the second argument, the function throws a `java.lang.ArithmeticException` because we just attempted to divide by zero. 
Additionally, if the result of the division is not an integer, `divide` will round it downwards.

Now, you might wonder, why should we use partial functions at all? 
Shouldn't we always try to write total functions? 
After all, in the code of the divide function above, we could first check if y equals 0, and return some special value. 
And indeed, this would be the right approach in many cases. 
But sometimes, a partial function can be useful. 
Consider the following example that employs the `collect` method from Scala collections:

```scala
enum Color:
 case Black, White, Ginger

import Color.*

trait Animal:
 def name: String
 def color: Color

case class Cat(name: String, color: Color) extends Animal
case class Dog(name: String, color: Color) extends Animal

// We create three instances of cats
val felix = Cat("Felix", Black)
val garfield = Cat("Garfield", Ginger)
val shadow = Cat("Shadow", Black)

// and two instances of dogs
val fido = Dog("Fido", Black)
val snowy = Dog("Snowy", White)

// We put all cats and dogs in a sequence of type Seq[Animal]
val animals: Seq[Animal] = Seq(felix, garfield, shadow, fido, snowy)

// Using the collect method, we create a new sequence containing only black cats
val blackCats: Seq[Cat] = animals.collect {
 case cat: Cat if cat.color == Black => cat
}
```
In this example, we first create an enum `Color` with three values: `Black`, `White`, and `Ginger`. 
We define a trait, `Animal`, with two abstract methods: `name` and `color`. 
We create case classes, `Cat` and `Dog`, that extend the `Animal` trait, and override the name and color methods with respective values. 
Then, we create three instances of `Cat` (two black and one ginger) and two instances of `Dog` (one black and one white). 
We consolidate them all into a sequence of type `Seq[Animal]`.

Ultimately, we use the `collect` method on the sequence to create a new sequence containing only black cats. 
The collect method applies a partial function to the original collection and constructs a new collection with only the elements for which the partial function is defined. 
You can perceive it as combibibg the filter and map methods. 
In the above example, we provide collect with the following partial function:

```scala
case cat: Cat if cat.color == Black => cat
```
The `case` keyword at the beginning tells us that the function will provide a valid result only in the following case: 
the input value needs to be of the type `Cat` (not just any `Animal` from our original sequence), 
and the color of that cat needs to be `Black`. 
If these conditions are met, the function will return the cat, however, as an instance of the type `Cat`, not just `Animal`. 
Thanks to this, we can specify that the new collection created by the collect method is a sequence of the type `Seq[Cat]`.

## Exercise 

Define a partial function `division` to handle division by 0.
Use the `case` keyword with an appropriate check to do this. 
Then, use the `isDefinedAt` method to verify if the function is defined for its arguments.
