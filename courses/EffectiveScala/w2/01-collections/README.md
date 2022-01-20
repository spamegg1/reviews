This page complements the previous lecture by showing a short syntax for defining functions.

## Functions Taking a Single Parameter

Consider again the increment function, which returns the value of its argument incremented by one:

```scala
val increment: Int => Int = x => x + 1
```

Note that the function body uses its argument x just once (in the expression x + 1).

Similarly, remember the function that returns whether a Contact has an email that ends with `@sca.la`:

```scala
val endsWithScaDotLa: Contact => Boolean =
  contact => contact.email.endsWith("@sca.la")
```

Again, the function body uses its argument contact only once.

When a function uses its argument only once, you don’t have to give it a name. Instead, you can use the so-called *placeholder syntax* like the following:

```scala
val increment: Int => Int = _ + 1

val endsWithScaDotLa: Contact => Boolean = _.email.endsWith("@sca.la")
```

The character underscore _ is a placeholder. You can think of _ as the place the argument will occupy when the function will be applied. For instance, calling increment(0) evaluates to 0 + 1 (the underscore has been replaced with the argument 0).

## Explicitly Typed Placeholder

Since the compiler is able to infer the result type of functions, it only needs to know the type of its arguments. So, instead of writing:

```scala
val increment: Int => Int = x => x + 1
```

You can just write:

```scala
val increment = (x: Int) => x + 1
```

Or, with the placeholder syntax:

```scala
val increment = (_: Int) + 1
```

The compiler knows that the placeholder in (_: Int) + 1 has type Int, and that the result of adding one to it also has type Int, so it infers that the value increment has type Int => Int.

## Functions Taking Multiple Parameters

The placeholder syntax also works with multiple parameters:

```scala
val add: (Int, Int) => Int = _ + _
```

This is equivalent to:

```scala
val add: (Int, Int) => Int = (x1, x2) => x1 + x2
```

## Warning: Wildcard Argument vs Placeholder

Consider now the following definition:

```scala
val puzzle: Int => Int = _ => 42
```

Note that we have an underscore here, but it is not a placeholder. Let’s go through this.

What is the type of the value puzzle? It is a function taking an integer and returning an integer (Int => Int).

What is the body of the function puzzle? It always returns the value 42, regardless of its argument value.

So, puzzle is a function that never uses its argument. Since its argument is not used, it does not even need to have a name, hence the underscore _, which represents a *wildcard argument* here.

In fact, you have already seen a similar construct, the wildcard pattern in match expressions:

```scala
someShape match
  case Circle(radius) => s"This is a circle with radius ${radius}"
  case _              => "This is not a circle"
```

In this example, the wildcard pattern handles all the values that are not circles, and returns the same result (the text "This is not a circle") regardless of the value `someShape`.

Wildcard arguments should not be confused with the placeholder syntax. The difference between wildcard arguments and placeholders is that wildcard arguments are followed by an arrow =>.

```scala
val placeholder = (_: Int) + 1

val wildcard = (_: Int) => 42
```

## Summary

Functions whose body use their arguments exactly once don’t need to name them. Instead, you can use a placeholder represented by the character underscore _.

For instance:

```scala
val increment = (_: Int) + 1
```

