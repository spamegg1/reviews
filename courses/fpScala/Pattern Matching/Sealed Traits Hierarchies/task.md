# Sealed Traits Hierarchies

Sealed  traits in Scala are used to represent restricted class hierarchies that provide exhaustive type checking. 
When a trait is declared as sealed, it can only be extended within the same file. 
This allows the compiler to know all the subtypes, which allows for more precise compile-time checking.

With the introduction of enums in Scala 3, many use cases of sealed traits are now covered by them, and their syntax is more concise. 
However, sealed traits are more flexible than enums — they  allow for the addition of new behavior in each subtype. 
For instance, we can override the default implementation of a given method differently in each case class that extends the parent trait. 
In enums, all enum cases share the same methods and fields.

```scala 3 
sealed trait Tree[+A]:
  def whoAmI: String

case class Branch[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]:
  override def whoAmI: String = "I'm a branch!"

case class Leaf[A](value: A) extends Tree[A]:
  override def whoAmI: String = "I'm a leaf!"

case object Stump extends Tree[Nothing]:
  override def whoAmI: String = "I'm a stump!"
```

The code for creating the tree looks exactly the same:

```scala 3
import Tree.*

val tree: Tree[Int] =
  Branch(
    Branch(Leaf(1), 2, Stump),
    3,
    Branch(Leaf(4), 5, Leaf(6))
  )
```

## Exercise 

Our trees are immutable, so we can compute their heights and check if they are balanced at the time of creation. 
To do this, we added the `height` and `isBalanced` members into the `Tree` trait declaration. 
The only thing that is left is to override these members in all classes that extend the trait in this exercise.  
This way, no extra passes are needed to determine whether a tree is balanced.

Consider this as an exercise. 
