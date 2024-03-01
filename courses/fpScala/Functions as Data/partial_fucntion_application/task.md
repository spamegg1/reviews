# Partial function application
Returning functions from functions is related to, but not the same as, [partial application](https://en.wikipedia.org/wiki/Partial_application).
The former allows you create functions that behave as though they have a "hidden" list of arguments that you provide at the moment of creation, rather than at the moment of usage.
Each function returns a new function that accepts the next argument until all arguments are accounted for, and the final function returns the result.

On the other hand, partial function application refers the process of assigning fixed values to some of the arguments of a function and returning a new function that only takes the remaining arguments.
The new function is a specialized version of the original function with some arguments already provided.
This technique enables code reuse — we can write a more generic function and then construct its specialized versions to use in various contexts.
Here's an example:

```scala
// Define a function that takes three arguments
def addN(x: Int, y: Int, n: Int) = x + y + n
// Partially apply the 'addN' function to set the last argument to 3
val add3 = addN(_: Int, _: Int, 3)
// Call the partially applied function with the remaining arguments
val result = add3(1, 2) // the result is 6 (1 + 2 + 3)
```

In the above example, we define a function `addN` that takes three arguments, `x`, `y`, and `n`, and returns their sum.
We then partially apply the `addN` function to set the last argument to 3, using the `_` placeholder for the first two.
This way, we create a new function, `add3`, which takes only two arguments, `x`, and `y`.
Finally, we call `add3` with only two arguments, obtaining the same result as with the function returning a function from the previous chapter and the `CalculatorPlusN` example from the first task.

## Exercise 

Implement a function `filterList` that can then be partially applied.
You can use the `filter` method in the implementation.

