# Anonymous functions

An anonymous function is a function that, quite literally, does not have a name. I
t is defined only by its arguments list and computations. 
Anonymous functions are also known as lambda functions, or simply lambdas.

Anonymous functions are particularly useful when we need to pass a function as an argument to another function, or when we want to create a function that is only used once and is not worth defining separately.

Here's an example:

```scala
// We create a sequence of numbers.
val numbers = Seq(1, 2, 3, 4, 5)

// We use the Seq.map method to double each number in the sequence by utilizing an anonymous function.
val doubled = numbers.map(x => x * 2)
```

Here, we create a sequence of `numbers` and we want to double each of them. 
To do that, we use the `map` method. 
We define an anonymous function `x => x * 2` and give it to the `map` method as its only argument. 
The `map` method applies this anonymous function to each element of `numbers` and returns a new list, which we call `doubled`, containing the doubled values.

Anonymous functions can access variables that are in scope at their definition.
Consider the `multiplyList` function, which multiplies every number in a list by a `multiplier`. 
The parameter `multiplier` can be used inside `map` without any issues.  

```scala
def multiplyList(multiplier: Int, numbers: List[Int]): List[Int] =
  // We can use multiplier inside map 
  numbers.map(x => multiplier * x)

```

When a parameter is only used once in the anonymous function, Scala allows omitting the argument's name by using `_` instead.
However, note that if a parameter is used multiple times, you must use names to avoid confusion. 
The Scala compiler will report an error if you fail to adhere to this rule. 

```scala
// We multiply each pair of numbers in a list using an anonymous function.
def multiplyPairs(numbers: List[(Int, Int)]): List[Int] = numbers.map((x, y) => x * y)

// Here we also multiply each pair of numbers in a list, but we omit parameters' names in the anonymous function.
// Scala associates the wildcards with the parameters in the order they are passed.  
def multiplyPairs1(numbers: List[(Int, Int)]): List[Int] = numbers.map(_ * _)

// We compute a square of each element of the list using an anonymous function. 
def squareList(numbers: List[Int]): List[Int] = numbers.map(x => x * x)

// In this case, omitting parameters' names is disallowed.
// You can see how it can be confusing, if you compare it with `multiplyPairs1`. 
def squareList1(numbers: List[Int]): List[Int] = numbers.map(_ * _)
```

## Exercise

Implement the `multiplyAndOffsetList` function that multiplies and offsets each element of the list. 
Use `map` and an anonymous function. 
