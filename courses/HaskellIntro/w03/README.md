[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

Polymorphism

- Polymorphism
  - [Parametricity](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#parametricity)
  - [Polymorphic Data Types](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#polymorphic-data-types)
  - [Wholemeal interactions](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#wholemeal-interactions)
- Recursive data types
  - [Lists](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#lists)
  - [mapList](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#maplist)
  - [combine](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#combine)
- [Case expressions](https://www.seas.upenn.edu/~cis194/fall16/lectures/03-polymorphism.html#case-expressions)

Today, we will explore how polymorphism allows for yet more abstract and compositional thinking, and how to apply wholemeal programming.

# Polymorphism

Polymorphism is a property of a function that it can work with any type. We have seen this in the type signature of `interactionOf`:

```haskell
interactionOf :: world ->
                 (Double -> world -> world) ->
		 (Event -> world -> world) ->
		 (world -> Picture) ->
		 IO ()
```

This function is polymorphic becaues the type `world` is arbitrary, and can be chosen by the caller. We did that in last week’s class when we instantiated with `Coord`, and you did it again in the homework, using your custom `State` type.

## Parametricity

It is important to keep in mind that *the caller picks the type*. Therefore, the implementation has to be able to handle any possible type. And since in Haskell you cannot make decisions based on the type of an argument (only on the value of the argument), this implies that Haskell is **parametric**: Polymorphic code behaves “the same” for any type that you pass it to.

Why is parametricity important?

1. It makes **type erasure** possible. Type erasure means that no information about the type of values is present at run-time. The compiler erases the types from your program as it compiles it to machine code. The benefit is that he code runs much faster than, say, Python code, where type information needs to be carried around and inspected at run-time.

2. It restricts what polymorphic functions can do. Consider the following type signature:

   ```haskell
   riddle :: a -> a
   ```

   We do not see its implementation. What can we say about it?

   It will have to return a value of type `a`, in a way that works for every possible type. So it cannot return `3`, it cannot return `True`. The only value of type `a` that it has access to is its parameter. So the only sensible definition of this function is

   ```haskell
   riddle :: a -> a
   riddle x = x
   ```

   Similarly, consider a function

   ```haskell
   riddle2 :: (a -> a) -> a -> a
   ```

   which takes two arguments: a function, and a value, again of some type that `riddle2` cannot know anything about. We can again wonder: What values of type `a` can `riddle2` return? Well, it can return its second argument. Or it can apply its first argument (the function) to the second argument. Or it can apply it twice… so there are many possible `riddle2` implementations, but we still can tell a lot about them from the type signature:

   ```haskell
   riddle2 :: (a -> a) -> a -> a
   riddle2 f x = x
   riddle2 f x = f x
   riddle2 f x = f (f x)
   …
   ```

   In-class exercise: What can you know about a function with this type:

   ```haskell
   riddle3 :: a -> a -> a
   …
   ```

   What about this riddle:

   ```haskell
   riddle4 :: a -> b
   ```

   Such a function cannot be written in Haskell! `riddle4` has no way of producing a value that will work as any possible type `b`!

   If you want to learn more about what one can find out about a function from its polymorphic type, read up on *free theorems*.

The above is not completely accurate. It is possible to implement a function of type `riddle4`:

```haskell
riddle4 :: a -> b
riddle4 x = riddle4 x
```

But this function will send the program into a infinite loop. The above observations are true for *total* functions, i.e. functions that always return. Haskell does allow for partial functions (i.e. functions that go into an infinite loop, or that throw an exception).

## Polymorphic Data Types

Not only functions can be polymorphic, but also data types. Let us motivate that by an example.

In last week’s homework ([example solution](https://code.world/haskell#PWc3Dtw35PArCBLII3HtK5w)), we created a variant of `interactionOf` that would allow the game to be reset (and it was indeed polymorphic). Let us do something similar and create a variant of `interactionOf` that would initially show a startup-screeen, and start the game proper only when the space key is pressed.

We define a very simple start screen:

```haskell
startScreen :: Picture
startScreen = scaled 3 3 (text "Sokoban!")
```

In order to implement this functionality, we need, in the state, remember whether we are showing the start screen, or whether we are already playing the game. But the type of `startScreenInteractionOf` (which is the same as `interactionOf`) is polymorphic in the state! We do not know what kind of state is used for the interaction we are wrapping, so we cannot use it to find out whether we are showing the start screen or not!

So we need to define our own state data type. And clearly, it is in one of two states: It is either showing the start screen, or the game is running, in which case we need to hold the game’s state. We can define a data type that does that:

```haskell
data SSState = StartScreen | Running world
```

With this code, we get the error message `Not in scope: type variable ‘world’`. To fix this, we have to give the type `SSState` a *type parameter*:

```haskell
data SSState world = StartScreen | Running world
```

So while `SSState` on its own is not a proper type yet, `SSState` applied to any type (e.g. `SSState Coord`) is.

Now we can implement the bits required for `startScreenInteractionOf`:

```haskell
startScreenInteractionOf ::
    world -> (Double -> world -> world) ->
    (Event -> world -> world) -> (world -> Picture) ->
    IO ()
startScreenInteractionOf state0 step handle draw
  = interactionOf state0' step' handle' draw'
  where
    state0' = StartScreen

    step' _ StartScreen = StartScreen
    step' t (Running s) = Running (step t s)

    handle' (KeyPress key) StartScreen
         | key == " "                  = Running state0
    handle' _              StartScreen = StartScreen
    handle' e              (Running s) = Running (handle e s)

    draw' StartScreen = startScreen
    draw' (Running s) = draw s
```

It was easy to write down the code, because we can program type-driven: With the type of the local functions in mind, we could simply program by cases. Every case on its own is rather obvious, and once we handled all the cases, we have a working program. ([Open on CodeWorld](https://code.world/haskell#PgWdfKuq3lKWArNmdLLlm_A))

## Wholemeal interactions

We now have this nice start screen, so it is a bit unfortunate that once we start the game, we cannot go back and see it again. So we really would want to combine this functionality with the reset functionality that we did in last week’s homework. But we can’t! Both these generic interaction-modifiers produce complete programs, there is no way to apply one to the other.

Surely, we can do better.

Imagine we had a type `Interaction` that captures everything about an Interaction. Then what we had functions

```haskell
resetable :: Interaction -> Interaction
withStartScreen :: Interaction -> Interaction
```

that modify interactions? Then we can obviously compose them. We would also need a type

```haskell
runInteraction :: Interaction -> IO ()
```

that actually runs this interaction.

It is possible to define such a type `Interaction`. Can you imagine how?

An interaction is defined by a state type, and by the four parameters that we so far have passed to `interactionOf`. So let us not pass them to `interactionOf`, but rather store them in a datatype:

```haskell
data Interaction world = Interaction
        world
	(Double -> world -> world)
	(Event -> world -> world)
	(world -> Picture)
```

Because of the type variable `world`, the type signatures of `resetable` and `withStartScreen` are a bit more complicated. We can use type inference to let the compile figure it out for us, though.

Changing the existing functions `startScreenInteractionOf` to `withStartScreen`, and `resetableInteractionOf` to `resetable` turns out to be trivial: Just replace the four parameters by one, pattern matching on `Interaction`, and replace the use of `interactionOf` with the constructor `Interaction`:

```haskell
resetable :: Interaction s -> Interaction s
resetable (Interaction state0 step handle draw)
  = Interaction state0 step handle' draw
  where handle' (KeyPress key) _ | key == "Esc" = state0
        handle' e s = handle e s

withStartScreen :: Interaction s -> Interaction (SSState s)
withStartScreen (Interaction state0 step handle draw)
  = Interaction state0' step' handle' draw'
  where
    state0' = StartScreen

    step' _ StartScreen = StartScreen
    step' t (Running s) = Running (step t s)

    handle' (KeyPress key) StartScreen
         | key == " "                  = Running state0
    handle' _              StartScreen = StartScreen
    handle' e              (Running s) = Running (handle e s)

    draw' StartScreen = startScreen
    draw' (Running s) = draw s
```

The function `runInteraction` is also very simply to write: We just pattern match on `Interaction` to get our hands on the individual functions, and pass them to `interactionOf`:

```haskell
runInteraction :: Interaction s -> IO ()
runInteraction (Interaction state0 step handle draw)
  = interactionOf state0 step handle draw
```

The final bit we need to change, before we can put everything together, is to actually have an `Interaction` that represents our exercise2 from last homework. Again, we simply have to change `interactionOf` to `Interaction`, and the type of `exercise2`:

```haskell
exercise2 :: Interaction State
exercise2 = Interaction initialState (\_ c -> c) handleEvent2 drawState2
```

And finally, we can compose our interactions ([open on CodeWorld](https://code.world/haskell#PJWfm6wWdR7J83ePoNG_e8A)): 

```haskell
main = runInteraction (resetable (withStartScreen exercise2))
```

Think about what happens if we switch the order of `resetable` and `withStartScreen`!

# Recursive data types

An important concept that you will need in more complex programs (such as a proper Sokoban implementation), is recursive datatypes. These can be used to implement lists and trees and many other data structures.

In our game, the boxes will have to be moved around. It is likely that we will want to manage a list of coordiates of these boxes, and clearly, this is going to be part of our state.

Our code should work with any number of boxes (as various levels have various number of boxes). What type is suitable to store any number of coordiates? Or – as you would expect after we talked about polymorphism – any number of values of some arbitrary type?

### Lists

Of course, the standard library comes with a suitable data type, but let us, for the sake of learning, define it outself. Just like we used recursion in functions to replace loops, we can use recursion in types to implement lists:

```haskell
data List a = Empty | Entry a (List a)
```

So a value of type `List a` is either the constructor `Empty`, or there is a list entry, which is a value of type `a`, and then the remainder of the list, i.e. another value of tye `List a`.

We can define a value of that type, by using the constructors, as you would expect:

```haskell
someBoxCoords :: List Coord
someBoxCoords = Entry (C 2 2) (Entry (C 3 3) (Entry (C (-1) 0) Empty))
```

Similarly, we can look at the elements of a list by pattern matching:

```haskell
firstBox :: List Coord -> Picture
firstBox Empty = blank
firstBox (Entry c _) = atCoord c (drawTile Box)

main :: IO ()
main = drawingOf (firstBox someBoxCoords)
```

Now, what if we want to draw all boxes in the list? We would also start by pattern-matchin on the list, handle the empty case, and handle one entry, and then recurse on the remaining list ([open on CodeWorld](https://code.world/haskell#PoXPnTHilAaDgwkSoJ-SWgw)):

```haskell
pictureOfBoxes :: List Coord -> Picture
pictureOfBoxes Empty = blank
pictureOfBoxes (Entry c cs) = atCoord c (drawTile Box) & boxes cs

main :: IO ()
main = drawingOf (pictureOfBoxes someBoxCoords)
```

We observe that the recursion on the value level (in the function `pictureOfBoxes`) corresponds to the recursion on the type level (in the type `List`). This is a common idiom.

Let us make this interactive again: We want to use the arrow keys to move *all* boxes. Here is one way of doing that:

```haskell
movingBoxes :: Interaction (List Coord)
movingBoxes = Interaction someBoxCoords (\_ s -> s) handle draw
  where
    draw = boxes
    handle (KeyPress key) s
        | key == "Right" = moveAllBoxes R s
        | key == "Up"    = moveAllBoxes U s
        | key == "Left"  = moveAllBoxes L s
        | key == "Down"  = moveAllBoxes D s
    handle _ s      = s

moveAllBoxes :: Direction -> List Coord -> List Coord
moveAllBoxes _ Empty = Empty
moveAllBoxes d (Entry c cs) = Entry (adjacentCoord d c) (moveAllBoxes d cs)
```

This works! But we are not satisfied. The function `moveAllBoxes` does a bit too much for my taste: We there have the logic of *both* moving a single box *and* traversing the list. Can we separate them? Sure we can, using higher order functions!

### mapList

We start by writing a very general apply-a-function-to-every-element-in-a-list function:

```haskell
mapList _ Empty = Empty
mapList f (Entry c cs) = Entry (f c) (mapList f cs)
```

and then we can use it in `handle` (note how partially appliying `adjacentCoord` works out so nicely here!):

```haskell
    handle (KeyPress key) s
        | key == "Right" = mapList (adjacentCoord R) s
        | key == "Up"    = mapList (adjacentCoord U) s
        | key == "Left"  = mapList (adjacentCoord L) s
        | key == "Down"  = mapList (adjacentCoord D) s
    handle _ s      = s
```

What do you think is the type signature of `mapList`?

### combine

The function `mapList` is very useful, and you will most likely want to use it in your code. In fact, it is generally recommended to use such general higher-order functions instead of writing out an explicit recursion.

Where can we do a similar refactoring? In the function `pictureOfBoxes`! This function also perform two things that can be separated into smaller, more generally useful parts: For every entry in the list, it creates a `Picture`, and then it combines all these. So how about this:

```haskell
combine :: List Picture -> Picture
combine Empty = blank
combine (Entry p ps) = p & combine ps

pictureOfBoxes :: List Coord -> Picture
pictureOfBoxes cs = combine (mapList (\c -> atCoord c (drawTile Box)) cs)
```

Granted, the code did not get much smaller. But we did gain a generally useful `combine` function. ([Open on CodeWorld](https://code.world/haskell#PwQ-ZMRVHGA40r0MKOnO8cg))

# Case expressions

Finally, a small syntactic gimmick, that you might find useful when doing the homework.

So far, the only place where we can do pattern matching is when we define a function. This is sufficient, but sometimes annoying, as it requires local functions that we might want to do without.

So let us say we want to remove all boxes from the maze. We could write it like this:

```haskell
noBoxMaze :: Coord -> Tile
noBoxMaze c = noBox (maze c)
 where
   noBox :: Tile -> Tile
   noBox Box = Ground
   noBox t   = t
```

but what we would rather write is

```haskell
noBoxMaze :: Coord -> Tile
noBoxMaze c = case maze c of
   Box -> Ground
   t   -> t
```

The syntax is the keyword `case`, followed by the expression that we want to analyze (also called the scrutinee), then keyword `of`, and then, vertically aligned, any number of alternatives. The alternatives consist of a pattern, just like in a function equation, an arrow, and the expression this should evaluate to in case the pattern matches. Even guards can be used here!

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).