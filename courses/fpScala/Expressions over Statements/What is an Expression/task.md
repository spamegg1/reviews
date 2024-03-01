## What is an Expression? 

When programming in an imperative style, we tend to build functions out of statements. 
We instruct the compiler on the exact steps and the order in which they should be performed to achieve the 
desired result.
A typical function follows this approach: retrieve the data from here, modify it, save it there. 
It manipulates data that resides somewhere outside the function itself.
This idea contradicts the definition of a function that we learned in school maths. 
There, functions didn't modify anything. 
Instead, they took arguments and produced a new result. 
They were expressions. 

An expression can be viewed as a combination of values, variables, functions, and operators that are evaluated by the
programming language's interpreter or compiler to produce other values. 
For example, `1+2`, `x*3` and `f(42)` are all expressions. 
Typically, an expression evaluates to a *value* which can be used in subsequent computations. 
Expressions are also *composable*, meaning that an expression can be nested within other expressions, enabling complex 
computations. 
You can often identify an expression by the context in which it is used: they most often appear in if-conditions, as arguments to 
functions, and on the right-hand side of assignments. 

The main purpose of a statement is to execute some specific *action*: declare a variable, run a loop, execute a conditional
statement, etc. 
For instance, examples of statements are `val x = 13;`, `return 42`, and `println("Hello world")`. 
They generally don't return a value; instead, they serve as the building blocks of a program written in an imperative style. 
Statements define the *control flow* of the program. 

Of course, in real programming languages, the distinctions between expressions and statements can be less clear-cut 
than in theory. 
Many languages permit expressions to have what are called *side effects*: they can throw exceptions, write to logs, or 
read from memory. 
On the other hand, statements may return values and even be composed. 
What is important is what we consider to be the primary purpose of a given language feature. 

In functional programming languages, we tend to favor expressions for various reasons. 
We will talk about these reasons in the further lessons. 
For now, let's consider how using expressions may affect the way we write code, using the example of a program that 
determines if a number is even or odd. 
First, consider a statement-based implementation. 

```scala 3
def even(number: Int): Unit = {
  if (number % 2 == 0)
    println("Number is even")
  else
    println("Number is odd")
}

def main(): Unit = {
  val number = 42
  even(42)
}
```

Here, we use an if-statement to check if the number is even. 
Depending on the condition, we execute one of the two `println` statements. 
Notice that no value is returned. Instead, everything the function does is a side effect of printing to the console. 

This style is not considered idiomatic in Scala. 
Instead, it's preferably for a function to return a string value, which is then printed, like so: 

```scala 3
def even(number: Int): String = { 
  if (number % 2 == 0) "even" else "odd"
} 

@main
def main(): Unit = {
  val number = 42 
  val result = even(12)
  println(s"The number is $result")
}
```

This way, you separate the logic of computing the values from outputting them. 
It also makes your code more readable. 

### Exercise 

Rewrite the `abs` and `concatStrings` functions as expressions to perform the same tasks as their original implementations. 
Implement the `sumOfAbsoluteDifferences` and `longestCommonPrefix` functions using the expression style.

`abs` computes the absolute value of a given integer number. 

`concatStrings` concatenates a list of strings. 

`longestCommonPrefix` determines the longest common prefix among the strings in the input list.

`sumOfAbsoluteDifferences` first computes the absolute differences between numbers at corresponding positions in two arrays and 
then sums them up. 
For example, given arrays `[1, 2]` and `[3, 4]`, it results in `abs(1 - 3) + abs(2 - 4) == 4`. 
It is assumed that the arrays always have the same length. 



































