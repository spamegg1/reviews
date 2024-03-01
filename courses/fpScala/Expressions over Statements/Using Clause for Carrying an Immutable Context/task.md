## `Using` Clause for Carrying Immutable Context

When writing pure functions, we often end up carrying some immutable context, such as configurations, as extra arguments.
A common example is when a function expects a specific comparator of objects, such as in computing the maximum value or sorting: 

```scala 3
trait Comparator[A]:
  def compare(x: A, y: A): Int

class IntComparator extends Comparator[Int]:
  def compare(x: Int, y: Int): Int = x - y

def max[A](x: A, y: A, comparator: Comparator[A]): A =
  if comparator.compare(x,y) >= 0 then x
  else y

@main
def main() =
  println(s"${max(13, 42, IntComparator())}")
```

Here, we have a trait `Comparator` to specify a method for comparing values. 
Every time we want to call the function `max`, we need to provide a specific `Comparator`. 
However, there is usually only one reasonable comparator for a specific type, and passing an extra argument hinders
readability. 
Scala solves this problem with the `using` clause, also known as a contextual parameter, along with what is called `given`s. 

```scala 3
trait Comparator[A]:
  def compare(x: A, y: A): Int

object Comparator:
  given Comparator[Int] with
    def compare(x: Int, y: Int): Int =
      x - y
end Comparator

def max[A](x: A, y: A)(using comparator: Comparator[A]): A =
  if comparator.compare(x,y) >= 0 then x
  else y

@main
def main() =
  println(s"${max(13, 42)}")
```

By marking an argument with `using` in Scala 3 or `implicit` in Scala 2, we tell the compiler to find an appropriate 
value based on its type among those marked as `given`. 
This marking is used when there is a single canonical value of the type, as with integer comparison. 
If the compiler cannot find a given value of the type needed, it will raise an error. 
You can read more about how the compiler searches for `given`s [in this StackOverflow answer](https://stackoverflow.com/questions/5598085/where-does-scala-look-for-implicits/5598107#5598107).

In some cases, there are two or more reasonable implementations of `Comparator`.  
For example, `String`s can be compared lexicographically (`"aa"` comes before `"b"`, which comes before `"bb"`) or primarily 
based on their lengths and then lexicographically (`"b"` comes before `"aa"`, which comes before `"bb"`).
Even when a contextual parameter is used, you can still use your custom value instead of the `given`. The only thing
you need to do is to explicitly pass it as an argument and mark it with the `using` keyword when calling the function: 


```scala 3
trait Comparator[A]:
  def compare(x: A, y: A): Int

object Comparator:
  given Comparator[Int] with
    def compare(x: Int, y: Int): Int =
      x - y
  given Comparator[String] with
    def compare(x: String, y: String): Int =
      x.compareTo(y)
end Comparator

object StringLengthComparator extends Comparator[String]:
  def compare(x: String, y: String): Int =
    if x.length != y.length then y.length - x.length
    else x.compareTo(y)

def max[A](x: A, y: A)(using comparator: Comparator[A]): A =
  if comparator.compare(x,y) >= 0
    then x
  else y

@main
def main() =
  println(s"${max("b", "aa")}")  // prints b
  println(s"${max("b", "aa")(using StringLengthComparator)}") // prints aa  
```

### Exercise 

Implement a `sort` function to sort an array of values based on the `Comparator` provided. Make it use a contextual 
parameter to avoid carrying around the immutable context. You can use any kind of sorting algorithm. 
