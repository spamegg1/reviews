# What is a function? 

A function is a standalone block of code that takes arguments, performs some calculations, and returns a result. 
It may or may not have side effects; that is, it may have access to the data in the program, and should the data be modifiable, the function might alter it. 
If it doesn't — meaning, if the function operates solely on its arguments — we state that the function is pure. 
In functional programming, we use pure functions whenever possible, although this rule does have important exceptions,  
which we will discuss them later.
The main difference between a function and a method is that a method is associated with a class or an object. 
On the other hand, a function is treated just like any other value in the program: it can be created in any place in the code, passed as an argument, returned from another function or method, etc.

Consider the following code:
```Scala

// A function defined with `def`
def addAsFunction(x: Int, y: Int): Int = x + y

// A function defined as a value
val addAsValue: (Int, Int) => Int = (x, y) => x + y

// A method associated with a class
class Calculator:
  def add(x: Int, y: Int): Int = x + y

```

Both `add` functions take two input parameters, `x` and `y`, perform a pure computation of adding them together, and return the result. 
They do not alter any external state.
In the first case, we define a function with the `def` keyword. 
After def comes the function's name, then the list of arguments with their types, then the result type of the function, and then the function's calculations, that is, `x + y`.

Compare this with the second approach to define a function, with the `val` keyword, which we also use for all other kinds of data. 
Here, after `val` comes the function's name, then the type of the function, `(Int, Int) => Int`, 
which consists of both the argument types and the result type, then come the arguments (this time without the types), and finally the implementation. 
You will probably find the first way to define functions more readable, and you will use it more often. 
However, it is important to remember that in Scala, a function is data, just like integers, strings, and instances of case classes — and it can be defined as data if needed.

The third example illustrates a method. 
We simply call it `add`. 
Its definition appears the same as the definition of the function `addAsFunction`, but we refer to add as a method because it is associated with the class `Calculator`.
In this way, if we create an instance of `Calculator`, we can call `add` on it, and it will have access to the internal state of the instance. 
It is also possible, for example, to override it in a subclass of `Calculator`.

```scala
// An instance of the class Calculator. The instance has no internal state.
val calc = new Calculator
// We call add(1, 2) on calc. It returns 3 (1 + 2).
calc.add(1, 2)

// A subclass of Calculator that has an internal state: the integer n.
class CalculatorPlusN(n: Int) extends Calculator:
  // The overridden method `add` that adds n from the internal state to the result of addition.
  override def add(x: Int, y: Int): Int = super.add(x, y) + n

// An instance of CalculatorPlusN with an internal state, n == 3.
val calc3 = new CalculatorPlusN(3)
// We call add on calc3. It returns 6 (1 + 2 + 3)
calc3.add(1 , 2)

```
<div class="hint" title="See additional materials on the topic">

A blog post <a href="https://makingthematrix.wordpress.com/2020/12/15/programming-with-functions-2-functions-as-data">here</a>.

A video: 

<iframe width="560" height="315" src="https://www.youtube.com/embed/RX1_EJp9Vxk" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>

## Exercise 

Implement multiplication as both a function and as a value; additionally, implement multiplication as a method of a class.  


