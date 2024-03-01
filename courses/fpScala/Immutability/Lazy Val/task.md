## Lazy `val`

**Laziness** refers to the deferral of computation until it is necessary.
This strategy can enhance performance and allow programmers to work with infinite data structures, among other benefits.
In a lazy evaluation strategy, expressions are not evaluated when bound to a variable but when used for the first time.
If they are never used, they are never evaluated.
In some contexts, lazy evaluation can also avert exceptions, since it can prevent the evaluation of erroneous computations.

In Scala, the keyword `lazy` is used to implement laziness.
When `lazy` is used in a `val` declaration, the initialization of that `val` is deferred until its first access.
Here’s a breakdown of how `lazy val` works internally:

* **Declaration**: When a `lazy val` is declared, no memory space is allocated for the value, and no initialization code is executed.
* **First access**: Upon the first access of the `lazy val`, the expression on the right-hand side of the `=` operator is evaluated,
  and the resultant value is stored.
  This computation generally happens thread-safe to avoid potential issues in a multi-threaded context
  (there’s a check-and-double-check mechanism to ensure that the value is only computed once, even in a concurrent environment).
* **Subsequent accesses**: For any subsequent accesses, the previously computed and stored value is returned directly
  without re-evaluating the initializing expression.

Consider the following example:

```
lazy val lazyComputedValue: Int = {
  println("Computing...")
  // Some heavy computation
  Thread.sleep(1000)
  42  // Computed value
}

val timeOffset = System.currentTimeMillis

def now = System.currentTimeMillis - timeOffset

println(s"lazyComputedValue declared at $now.")
// The value is computed and printed only when it's accessed for the first time.
println(s"Accessing: $lazyComputedValue")
println(s"time now is $now") // takes more than 1000 milliseconds
// This time, it's not computed but fetched from memory.
println(s"Accessing again: $lazyComputedValue")
println(s"time now is $now") // should take only a few milliseconds at most
```

In the above code:
* The `lazy val lazyComputedValue` is declared but not computed immediately upon declaration.
* Once it is accessed in the first `println` that includes it, the computation is executed, `"Computing..."` is printed to the console,
  and the computation (here simulated with `Thread.sleep(1000)`) takes place before returning the value `42`.
* Any subsequent accesses to `lazyComputedValue`, like the second `println`, do not trigger the computation again.
  The stored value (`42`) is used directly.
