## Pure vs Impure Functions

Not all functions are created equal; some of them are better than others. 
A large group of such superior functions are designated as *pure*. 
A pure function always yields the same value if given the same inputs. 
For example, the mathematical function `double(x) = 2 * x`, for doubling a number, always returns `42` when given `21` as an 
argument. 
Conversely, the function `g`, which takes a number as an argument, reads another from the standard input, and then multiplies them,
does not always compute the same result when called with `21`. 

```scala 3
def g(x: Int): Int =
  val y = StdIn.readInt()
  x * y

println(g(21)) // Input: 1 => printed 21
println(g(21)) // Input: 3 => printed 63
```

Another consequence of having to always produce the same result is that a pure function cannot perform any side effects. 
For instance, a pure function cannot output anything, interact with a database, or write into a file. 
It cannot read from the console, database, or a file, modify its arguments, or throw an exception. 
The result solely depends on the arguments and the implementation of the function itself. 
Its performance should neither be influenced by the external world nor impact it.

You might argue that pure functions seem entirely useless. 
If they cannot interact with the outer world or mutate anything, how is it possible to derive any value from them? 
Why even use pure functions? 
The fact is, they conform much better than impure counterparts.
Since there are no hidden interactions, it's much easier to verify that your pure function does what it 
is supposed to do and nothing more. 
Moreover, they are much easier to test, as you do not need to mock a database if the function never interacts with one. 

Some programming languages, such as Haskell, restrict impurity and reflect any side effects in types. 
However, it can be quite restricting and is not an approach utilized in Scala.  
The idiomatic method is to write your code in such a way that the majority of it is pure, and impurity is only used 
where it is absolutely necessary, similar to what we did with mutable data. 
For example, the function `g` can be split into two: the one that reads the number from the standard input and the one 
that is responsible for multiplication: 

```scala 3
def gPure(x: Int, y: Int): Int =
  x * y 
  
def g(x: Int): Int =
  val y = StdIn.readInt()
  gPure(x, y)
```

### Exercise

Implement the pure function `calculateAndLogPure` which does the same thing as `calculateAndLogImpure`, but without 
using global variable.
