# `foldLeft`

`def foldLeft[B](acc: B)(f: (B, A) => B): B`

The `foldLeft` method is another method in Scala collections that can be percieved as a generalized version of `map`, but generalized differently than `flatMap`. 
Let's say that we call `foldLeft` on a collection of elements of type `A`. 
`foldLeft` takes two arguments: the initial "accumulator" of type `B` (usually different from `A`) and a function `f`, which again takes two arguments: the accumulator (of type `B`) and the element from the original collection (of type `A`). 
`foldLeft` starts its work by taking the initial accumulator and the first element of the original collection and assigning them to `f`. 
The `f` function uses these two to produce a new version of the accumulator — i.e., a new value of type `B`. 
This new value, the new accumulator, is again provided to `f`, this time together with the second element in the collection. 
The process repeats itself until all elements of the original collection have been iterated over. 
The final result of `foldLeft` is the accumulator value after processing the last element of the original collection.

The "fold" part of the `foldLeft` method's name derives from the idea that `foldLeft`'s operation might be viewed as "folding" a collection of elements, one into another, until ultimately, a single value — the final result. 
The suffix "left" is there to indicate that in the case of ordered collections, we are proceeding from the beginning of the collection (left) to its end (right). 
There is also `foldRight`, which works in the reverse direction.

Let's see how we can implement a popular coding exercise, *FizzBuzz*, using `foldLeft`. 
In *FizzBuzz*, we are supposed to print out a sequence of numbers from 1 to a given number (let's say 100). 
However, each time the number we are to print out is divisible by 3, we print "Fizz"; if it's divisible by 5, we print "Buzz"; and if it's divisible by 15, we print "FizzBuzz". 
Here is how we can accomplish this with foldLeft in Scala 3:

```scala
def fizzBuzz(n: Int): Int | String = n match
 case _ if n % 15 == 0 => "FizzBuzz"
 case _ if n % 3  == 0 => "Fizz"
 case _ if n % 5  == 0 => "Buzz"
 case _ => n

// Generate a range of numbers from 1 to 100
val numbers = 1 to 100

// Use foldLeft to iterate through the numbers and apply the fizzBuzz function
val fizzBuzzList = numbers.foldLeft[List[Int | String]](Nil) { (acc, n) => acc :+ fizzBuzz(n) }

println(fizzBuzzList)
```

First, we write the `fizzBuzz` method, which takes an `Int` and returns either an `Int` (the number that it received) or a `String: "Fizz", "Buzz", or "FizzBuzz". 
With the introduction of union types in Scala 3,
we can declare that our method can return any of two or more unrelated types, but it will definitely be one of them.

Next, we create a range of numbers from 1 to 100 using `1 to 100`.

We call the `foldLeft` method on the numbers range, stating that the accumulator will be of the `List[String | Int]` type and that initially, it will be empty (`Nil`).

The second argument to `foldLeft` is a function that takes the current accumulator value (`acc`) and an element from the numbers range (`n`). 
This function calls our `fizzBuzz` method with the number and appends the result to the accumulator list using the `:+` operator.

Once all the elements have been processed, `foldLeft returns the final accumulator value, which is the complete list of numbers and strings "Fizz", "Buzz", and "FizzBuzz", replaceing numbers that were divisible by 3, 5, and 15, respectively.

Finally, we print out the results.

## Exercise 

Implement the following functions using `foldLeft`: 
* The `computeAverage` function, which computes the average of a list of numbers;
* The `maximum` function, which finds the maximum number in the list;
* The `reverse` function, which reverses the list.
