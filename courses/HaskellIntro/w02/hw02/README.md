[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 2: Higher Order functions and Datatypes

CIS 194: Homework 2
Due Tuesday, September 13

The general remarks about style and submittion from [last week](https://www.seas.upenn.edu/~cis194/fall16/hw/01-intro.html) still apply.

You are free to use the [code from last week’s lecture](https://code.world/haskell#PTU3hQlDx2i-jo0IFldfY8Q) and from your own submission of homework 1. Some of the exercises below reference types and functions from there. In particular, if you have nicely drawn tiles, you should use them instead of my ugly ones.

You learned about local definitions and lambda expressions. Use them when appropriate!

## Exercise 1: The small guy (or girl) moves

We hope to have a complete game by next week, so let us work towards that.

Create a value `player :: Picture` that draws your figure.

Create a value `exercise1 :: IO ()` that calls `interactionOf` with suitable arguments that:

- the player is drawn on top of the maze,
- it starts in a position where there is ground (you can hard-code that position),
- the cursor keys move the figure around (while the maze stays in a fixed position),
- the player moves only on tiles of type `Ground` and `Storage`. Trying to move it into any other position will simply leave it in place.

It might yield nicer code to change the type of `maze` to `Coord -> Tile`. If you find that, do not hesitate to make that change.

[see on CodeWorld](https://code.world/run.html?mode=haskell&amp;dhash=DCcnNyZpZZPSVFgqH3WwY3w)

## Exercise 2: Look the right way!

This is an extension of exercise 1. We want to figure to look the way it is going. So write a function `player2 :: Direction -> Picture` that draws the figure in four variants.

Then extend the code from exercise1 so that after the player has tried to move in some direction, it looks that way. If you can re-use functions and types from `exercise1`, do so, but if you have to change them, rename them, e.g. by appending a `2` to the name.

Hint: Think about types first (e.g of your state), and then about the implementation.

The resulting program should be called `exercise2`.

[see on CodeWorld](https://code.world/run.html?mode=haskell&amp;dhash=Dal2TK1RkY3oFhVAlSz1YAw)

## Exercise 3: Reset!

It would be nice to be able to start a game from the beginning. This is generally useful functionality, no matter what the game, so let us implement it generally.

You will write a function

```haskell
resetableInteractionOf ::
    world ->
    (Double -> world -> world) ->
    (Event -> world -> world) ->
    (world -> Picture) ->
    IO ()
```

which has the same type as `interactionOf`.

This function will behave almost the same as `interactionOf` (which it internally uses, of course), but when `Esc` is pressed, this event is not passed on, but rather the state of the program is reset to the given initial state.

Style hint: An idiomatic definition of `resetableInteractionOf` does not require any other top-level definitions, but likely local functions and/or lambda expressions.

Let `exercise3` be like `exercise2`, but using `resetableInteractionOf` instead of `interactionOf`.

[see on CodeWorld](https://code.world/run.html?mode=haskell&amp;dhash=D7Y0vPUj4D2tE2iKoYnvZrg)

## Exercise 4: New levels

Create a new maze. It should

- fit within the screen (i.e. use coordinates from -10 to 10),
- it should be connected (i.e. starting on a ground tile, and disregarding boxes, the player should be able to reach all ground tiles),
- it should be closed (i.e. the player should not be able to reach blank tiles), and
- it should be solvable (recollect [the rules](https://en.wikipedia.org/wiki/Sokoban#Rules) if necessary).

Use this maze in your exercises above, so that the TA does not get bored while grading.

Also, we will be collecting these mazes and provide them to you so that you can build a sokoban game with different levels next week.

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).