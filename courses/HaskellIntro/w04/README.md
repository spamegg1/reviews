[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

Type classes

- Type classes
  - [The Eq type class](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#the-eq-type-class)
  - [The `Eq Coord` instanace](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#the-eq-coord-instanace)
  - [Default implementations](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#default-implementations)
  - [The `Eq Tile` instance](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#the-eq-tile-instance)
  - [Equality is not for everyone](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#equality-is-not-for-everyone)
  - Benefits of type classes
    - [Overloading of names](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#overloading-of-names)
    - [Laws!](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#laws)
    - [Generic algorithms](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#generic-algorithms)
    - [Instance resolution](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#instance-resolution)
    - [Coherence](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#coherence)
  - [Use case: Undo stacks](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#use-case-undo-stacks)
  - [Type classes vs. object-oriented classes](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#type-classes-vs.object-oriented-classes)
  - [Other type classes you should know about](https://www.seas.upenn.edu/~cis194/fall16/lectures/04-typeclasses.html#other-type-classes-you-should-know-about)

This class brings equality to the `world`.

# Type classes

In last week’s homework, you have developed a working implementation of Sokoban. The resulting code might look like [this example solution](https://code.world/haskell#PubDDSw-wNxzTsQt1SxJ6GA).

While implementing it, some of you might have wished to be able to use the equality operator (`(==)`) on your own data types, such as `Coord` or `Tile`. But you could not and had to work around it using `case` expressions, helper functions or functions like `eqCoord`.

But you may have already observed that `(==)` can be used not just types: `Integer`, `Double`, `Bool` (although you should not use it with `Double`!). And I claimed that `Bool` is not special. So there must be a way of using `(==)` with, say, `Coord`.

Maybe the error message that we see if we try to use it, can shed some light on this:

```haskell
No instance for (Eq Coord) arising from a use of ‘==’
```

It seems that we need some kind of instance. Before we talk about instances, though, we have to talk about classes.

I’ll use the Haskell interpreter to do a bit exploration. I would have used the online docs, but a [bug](https://github.com/haskell/haddock/issues/549) in the tool that generates the documentation unfortunately gets in the way right now. You will learn about the Haskell interpreter eventually as well.

I can ask for the type of `(==)`:

```haskell
Prelude> :t (==)
(==) :: Eq a => a -> a -> Bool
```

On the right of the `=>` arrow, we have the function type that we know: The operator takes two arguments of some type, and returns a boolean. On the left of the `=>` arrow, we have a *type class constraint*. This says that `(==)` can only be used on types that are members of this type class.

## The Eq type class

We can find out more about this `Eq`, using the interpreter:

```haskell
Prelude> :info Eq
class Eq a where
  (==) :: a -> a -> Bool
  (/=) :: a -> a -> Bool
	-- Defined in ‘GHC.Classes’
instance Eq Integer
  -- Defined in ‘integer-gmp-1.0.0.0:GHC.Integer.Type’
instance (Eq a, Eq b) => Eq (Either a b)
  -- Defined in ‘Data.Either’
instance Eq a => Eq [a] -- Defined in ‘GHC.Classes’
instance Eq Word -- Defined in ‘GHC.Classes’
…
```

We have to scroll up to get to the interesting bits:

- The first three lines are the definition of the `Eq` class, as you would write them in your code, if you would define your own type class. Here is a rough overview of the components.

  A type class definition gives the class a name (here `Eq`), applied to to a type variable. It then contains any number of *methods*. Methods are given with *just* their type signature – the implementation will be in the class instances. Also, the type is given without the `Eq` constraint in the type signature; this is added automatically.

  We see that the `Eq` type class has two methods: `(==)` and `(/=)`.

- Then, a number of lines starting with `instance` are printed. These tell us what instances already exists: For example for `Integer`, as expeced. These are not the complete instances, because instances come with code.

## The `Eq Coord` instanace

Now that we know what method the class as, we can write our own instance for `Coord`:

```haskell
instance Eq Coord where
  C x1 y1 == C x2 y2 = x1 == x2 && y1 == y2
  c1 /= c2 = not (c1 == c2)
```

So after the `instance` keyword, we name the type class that we want to instantiate and the type for which we want to define an instance. Then, after `where` and indented, we give function definitions for the methods. We can use multiple patterns, guards, and all that as usual. We do *not* give a type signature, becuase the type of these methods is already determined by the class definition and the *instance head*.

Now our code compiles again.

## Default implementations

Note that I was lazy and did not really implement `(/=)`; I just refered to the implementation of `(==)`. Obvoiusly, that is fair game for *every* instance of `Eq` that one might want to implement.

Therefore, the `Eq` class already comes with a *default implementation* of `(/=)` in terms of `(==)` and we can simply skip the definition in our code.

How do we know that we can leave out `(/=)`? We can try (the compiler will warn us about missing instances), or we can check out [the documentation](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Eq) or [the source](http://hackage.haskell.org/package/ghc-prim-0.4.0.0/docs/src/GHC-Classes.html) (which are a bit harder to find for `Eq` because it is so basic).

By making a function a method of the class with an default implementation, the authors of an instance have the option of implementing it (for example if a more efficient implementation is possible), but they do not have to.

## The `Eq Tile` instance

So great: We can now use `==` on `Coords`. We also want it on `Tiles`. That is easy to write:

```haskell
instance Eq Tile where
  Wall == Wall = True
  Ground == Ground = True
  Storage == Storage = True
  Box == Box = True
  Blank == Blank = True
  _ == _ = False
```

Now that works, but I am sure you can immediately tell me why this is not satisfying: That is a lot of code to write, it is repetitive, and if we add another constructor to `Tile`, the code will be wrong.

Luckily, at least for some of the basic type classes, the compiler can write the instance for us. We just have to instruct it to:

```haskell
data Tile = Wall | Ground | Storage | Box | Blank deriving Eq
```

(If are are playing around with this locally, then pass `-ddump-deriv` to the compiler to see that it actually creates the same code that we wrote above.)

## Equality is not for everyone

So great, we get to use `(==)` on all our types! All our types? Unfortunately not. Last week we defined the type `Interaction`. Some fields of this data type are functions, and equality on functios is, in general, undecidable. If we tried to use `deriving Eq` on `Interaction`, we would get the error message

```haskell
No instance for (Eq (world -> Picture))
```

## Benefits of type classes

So great, we get to use `(==)` on almost all our types! This is also called *overloading*, and is a nice feature of type classes, but not the only and probably not the most important one. The important features are:

### Overloading of names

As just discussed.

### Laws!

When you see `(==)` and `(/=)`, you immediately assume that `a /= b` is `True` if and only if `a == b` is `False`. By convention, every instance of `(==)` fulfills this law, and the derived instances do as well. For other type classes, the laws are more interesting and we will discuss them in depth later.

Note that such laws are not checked by the compiler (it cannot do that, in general), and nothing is stopping you from implementing a bogus instance. But if you do, do not complain if things break in weird ways.

### Generic algorithms

Have a look at the definition of `moveFromTo`:

```haskell
moveFromTo :: Coord -> Coord -> Coord -> Coord
moveFromTo c1 c2 c | c1 == c   = c2
                   | otherwise = c
```

There is nothing `Coord`-specific going on any more! And indeed, if we remove the type signature, and let the compiler propose one to us, we get the suggestion to write

```haskell
moveFromTo :: Eq a => a -> a -> a -> a
moveFromTo c1 c2 c | c1 == c   = c2
                   | otherwise = c
```

This means that `moveFromTo` is a very generally usable function. We only write it once and can use it with many different types.

Now, this function is not very impressive, but can implement very complex functionality in a generic and reusable way this way, especially when the type class in question comes with laws (see above).

### Instance resolution

When using overloaded functions, the compiler has to find the relevant instance for the type you are using it. With polymorphic types, this instance might require another one (as we will see shortly). With some trickery and/or some language extensions supported by GHC this *instance resolution* process can be a powerful machinery that not only relieves you from writing some tedious code, but can actually solve some puzzling problems for you. In a way, it is a small logic programming language embedded in the type system.

I will demonstrate this in a moment, but let’s continue with the general remarks first.

### Coherence

Haskell guarantees that for a particular type and a particular type class, there is at most one instance. If we would try to define `instance Eq Coord` again, the compiler will bark at us.

This means that the meaning of an overloaded function depends only on the concrete type it is used with, but not in what context it is used. This is used by the library implementation of search trees, which uses the `Ord` instance of the type of keys to build the tree, and this would go horribly wrong if you build the tree with one particular ordering, and then search in it using a completely different ordering.

## Use case: Undo stacks

Let us demonstrate how we can make use of instance resolution, by implementing generic undo functionality. When testing your homework, you surely moved your box onto the wall and wished you did not have to start over again. So let us implement this, in generic way, as a `Interaction`-modifying function:

```haskell
data WithUndo a = WithUndo a (List a)

withUndo :: Interaction a -> Interaction (WithUndo a)
withUndo (Interaction state0 step handle draw)
  = Interaction state0' step' handle' draw'
  where
    state0' = WithUndo state0 Empty

    step' t (WithUndo s stack) = WithUndo (step t s) stack

    handle' (KeyPress key) (WithUndo s stack) | key == "U"
      = case stack of Entry s' stack' -> WithUndo s' stack'
                      Empty           -> WithUndo s Empty
    handle' e              (WithUndo s stack)
       = WithUndo (handle e s) (Entry s stack)

    draw' (WithUndo s _) = draw s
```

This code ([open on CodeWorld](https://code.world/haskell#PE2_h1f2oVa3WhCl8Lq0TGA)) looks good, but if we we use it (by adding `withUndo` in `main`), it does not seem to work. What went wrong?

The problem is that we push the state to the stack on every event. That includes mouse moves, that includes button releases. What we really would like to do is push a change only on to the stack if the event actually had an effect!

So we need to check thatin the `handle'` function, before we push a new state onto the stack:

```haskell
    handle' (KeyPress key) (WithUndo s stack) | key == "U"
      = case stack of Entry s' stack' -> WithUndo s' stack'
                      Empty           -> WithUndo s Empty
    handle' e              (WithUndo s stack)
       | s' == s = WithUndo s stack
       | otherwise = WithUndo (handle e s) (Entry s stack)
      where s' = handle e s
```

This does not work yet, the compiler complains:

```haskell
No instance for (Eq a) arising from a use of ‘==’
```

which makes sense: In order to compare the state of the interaction we are wrapping, the state needs to be comparable! So we have to extend the type signature:

```haskell
withUndo :: Eq a => Interaction a -> Interaction (WithUndo a)
```

But the compiler is still not satisfied:

```haskell
No instance for (Eq State) arising from a use of ‘withUndo’
```

We have not yet defined an `Eq` instance for `State`! But we know how to do that quickly: Using `deriving Eq`. This will, as one might expect, also ask for `Eq` instances for `Direction` and `List`, which we give the same way.

Yay, that works ([open on CodeWorld](https://code.world/haskell#PI8Rd7guupXOJc-76-bF32A)):

[see on CodeWorld](https://code.world/run.html?mode=haskell&amp;hash=PI8Rd7guupXOJc-76-bF32A)

Now what if we swap the use of `withUndo` and `withStartScreen`? We get an error message saying

```haskell
No instance for (Eq (SSState State))
```

So we should give an `Eq` instance for `SSState`. We could use `deriving`, but lets do it by hand to learn something.

We have introduced `withStartScreen`, and hence `SSState`, to be polymorphic and work with any possible wrapped state. So likewise, we do not want to write an instance just for `SSState State`, but rather `SSState s` for any type `s`. So lets write that:

```haskell
instance Eq (SSState s) where
  StartScreen == StartScreen = True
  Running s == Running s' = s == s'
  _ == _ = False
```

This does not work yet, we get an error message:

```haskell
No instance for (Eq s) arising from a use of ‘==’
```

This makes sense: If the underlying state type does not support equality, then the extended type cannot do it either. But where do we add the constraint that this `Eq` instance does only work if `s` itself is a member of the `Eq` typeclass? We add this to the instance head:

```haskell
instance Eq s => Eq (SSState s) where
  StartScreen == StartScreen = True
  Running s == Running s' = s == s'
  _ == _ = False
```

This works, and now pressing U gets us back to the start screen.

What is happening here? As we compose functions that transform `Interaction`s, the type of the state of these interactions grows. And then, when there is a function like `withUndo` that has an `Eq` constraint, the compiler uses the existing instances of `Eq` to break this complex type down again, and this way constructs, on the spot, a way to equate values of this complex type.

Naturally, this example has been very small, but I hope it gave you an impression at what might be possible.

**Question**: Are there other designs that do not require an explicit equality check to implement undo? Which one would you prefer?

## Type classes vs. object-oriented classes

Many of you might have experience with object-oriented programming languages like Java. You are in danger! Do not fall in the trap of confusing type classes (in Haskell) with classes (in Java)! They are not very similar, and you do not use them to solve the same problems.

If anything, type classes correspond to *interfaces* in Java: Both contain methods without implementation and their type signatures, and instances provide the implementation.

Classes and objects as in Java do not have a direct correspondence in Haskell, and that is OK, because problems are approached differently. But the `Interaction` type that we defined last week is, in some sense, an approximation of a class. The concrete `Interaction`s that we defined are instances of this class, and functions like `withStartScreen` relate to inheritance (or maybe to the decorator pattern).

## Other type classes you should know about

Besides `Eq`, you should know about these type classes:

- [`Ord`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Ord), with methods like `(<=)`, `min` and others, for types that can be (totally) ordered. Can be derived.
- Many numeric type classes:
  - [`Num`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Num), with methods like `(+)`, `(*)`, `abs` and `fromInteger`, for basic numeric types. Speaking mathematically, this type class captures the operations of a *ring*. Cannot be derived. Instances for `Int`, `Integer`, `Double` and others.
  - [`Integral`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Integral) with methods like `div` and `mod`, for integral types that allow these operations. Instances for `Int`, `Integer`, and others.
  - [`Fractional`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Fractional) with method `(/)` for anything that can properly be divided. In particular, `Double`.
  - [`Floating`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Floating) with methods like `pi`, `exp`, `sin`, `(**)` which require floating point numbers. Instance for `Double`.
  - [`RealFrac`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:RealFrac) with methods like `round`, `truncate` etc. that convert from floating point numbers to integral numbers.

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).