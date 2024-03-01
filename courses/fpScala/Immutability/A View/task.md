## A View 

A view in Scala collections is a lazy rendition of a standard collection. 
While a lazy list needs to be constructed as such, you can create a view from any "eager" Scala collection by calling `.view` on it. 
A view computes its transformations (like map, filter, etc.) in a lazy manner,  
meaning these operations are not immediately executed; instead, they are computed on the fly each time a new element is requested, 
which can enhabce both performance and memory usage. 
On top of that, with a view, you can chain multiple operations without the need for intermediary collections — 
the operations are applied to the elements of the original "eager" collection only when requested. 
This can be particularly beneficial in scenarios where operations like map and filter are chained, so a significant number of 
elements can be filtered out, eliminating the need for subsequent operations on them.

Let's consider an example where we use a view to find the first even number squared that is greater than 100 from a list of numbers.

```scala 3
val numbers = (1 to 100).toList

// Without using view
val firstEvenSquareGreaterThan100_NoView = numbers
  .map(n => n * n)
  .filter(n => n > 100 && n % 2 == 0)
  .head
println(firstEvenSquareGreaterThan100_NoView)


// Using view
val firstEvenSquareGreaterThan100_View = numbers.view
  .map(n => {
    println(s"Square of $n being calculated.")  // To observe the lazy evaluation
    n * n
  })
  .filter(n => n > 100 && n % 2 == 0)
  .head
println(firstEvenSquareGreaterThan100_View)
```


Without using a view, all the numbers in the list are initially squared and then filtered, even though we are only interested in 
the first square that satisfies the condition. 
With a view, transformation operations are computed lazily. 
Therefore, squares are calculated, and conditions are checked for each element sequentially until the first match is found. 
This avoids unnecessary calculations and hence is more efficient in this scenario.

To learn more about the methods of Scala View, read its [documentation](https://www.scala-lang.org/api/current/scala/collection/View.html).

### Exercise 

Consider a simplified log message: it is a comma-separated string where the first substring before the comma specifies its severity, 
the second substring is the numerical error code, and the last one is the message itself.  
Implement the function `findLogMessage`, which searches for the first log message with the given `severity` and `errorCode` within a list.
As the list is assumed to be large, utilize `view` to avoid creating intermediate data structures. 
