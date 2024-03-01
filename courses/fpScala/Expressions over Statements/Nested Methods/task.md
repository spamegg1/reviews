## Nested Methods 

In Scala, it's possible to define methods within other methods. 
This is useful when you have a function that is only intended for one-time use. 
For example, you may wish to implement the factorial function using an accumulator to enhance the program's efficiency.
Simultaneously, you would not want to allow the user to call the function with an arbitrary accumulator parameter. 
In this situation, you can expose a standard one-parameter function `factorial`, which calls the nested tail-recursive implementation 
`fact` with the appropriate accumulator: 

```scala 3
def factorial(x: Int): Int =
  def fact(x: Int, accumulator: Int): Int =
    if x <= 1 then accumulator
    else fact(x - 1, x * accumulator)
  fact(x, 1)
```

An alternative option is to place the `fact` function on the same level as `factorial` and make it `private`. 
This still permits other functions in the same module to access `fact`, whereas nesting it renders it exclusively accessible 
from inside the `factorial` function. 
You can also have nested methods within other nested methods, with the rules of scoping being consistent: the nested function is 
only accessible from within its outer function: 

```scala 3
def foo() = {
  def bar() = {
    def baz() = { }
    baz()
  }
  def qux() = {
    def corge() = { }
    corge() // A nested function can be called
    bar() // A function on the same level can be called
    // A function nested within the other function cannot: 
    // baz() // not found: baz
  }
  // Functions on this level can be called...
  bar()
  qux()
  // ... but their nested functions cannot: 
  // baz() // not found: baz
  // corge() // not found: corge
}
```

Note that we've used curly braces to delineate scopes more clearly; however, they are not needed in Scala 3. 

Nested functions can access the parameters of their parents, so you can avoid passing parameters that do not change: 

```scala 3
def f(x: Int, y: Int): Int =
  def g(z: Int): Int =
    def h(): Int =
      x + y + z
    h()
  //  def i(): Int = z // z is not visible outside g
  g(42)
```

Another instance where nested methods prove particularly useful is when you create a chain of calls to higher-order 
functions, utilizing nested methods to assign meaningful names to their arguments. 
Consider the example where we count the number of kittens that are either white or ginger. 

```scala 3
enum Color:
  case Black
  case White
  case Ginger

// We have a model in which any cat has a color and an age (in years)
class Cat(val color: Color, val age: Int)

val bagOfCats = Set(Cat(Color.Black, 0), Cat(Color.White, 1), Cat(Color.Ginger, 3))

// Count the number of white or ginger kittens (cats that are not older than 1 year) 
val numberOfWhiteOrGingerKittens =
  def isWhiteOrGinger(cat: Cat): Boolean = cat.color == Color.White || cat.color == Color.Ginger
  def isKitten(cat: Cat): Boolean = cat.age <= 1
  bagOfCats.filter(isWhiteOrGinger).count(isKitten)
```

We could have written the latter function as shown below, but it is obviously less readable: 

```scala 3
val numberOfWhiteOrGingerKittens =
  bagOfCats
    .filter(cat => cat.color == Color.White || cat.color == Color.Ginger)
    .count(cat => cat.age <= 1)
```

### Exercise 

Explore the scopes of the nested methods. Make the code in the file `NestedTask.scala` compile.
