[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 3: Let’s play Sokoban!

CIS 194: Homework 3
Due Tuesday, September 20

The general remarks about style and submittion from [the first week](https://www.seas.upenn.edu/~cis194/fall16/hw/01-intro.html) still apply.

The goal of this week is simply: Implement Sokoban!

In order to guide you through the process, and also to make partial grading possible, we will break it down into smaller steps.

You can start from [this code](https://code.world/haskell#PC8zcugVZZ1W5SW0RGS33SA). It already includes type signatures for some of the functions described below. Make sure you adjust everything defined to be `undefined` or marked as `FIXME`. Please use your own code from previous homeworks where appropriate (i.e. your custom maze, tiles and player).

## Step 1: The state

Define a data type `State` that capture the state of the game. It needs to store these bits of information:

- The current position of the player.
- The direction the player is facing.
- The current positions of all boxes, as a `List`.

## Step 2: The initial state

Define a value `initialState :: State` for the initial state:

- Manually define a sensible position for the player (i.e. on some `Ground` tile).
- Use an arbitrary position.
- Find where the boxes are.

The latter is a bit tricky: The information is there (in the definition of `maze`), but not very accessible. Do **not** just write down the list of coordinates by hand! Instead, define a value `initialBoxes :: List Coord` that is calcuated from looking at each coordinate (going from -10 to 10, as usual), and adding that coordinate to the list if there is a box.

There are two ways of doing that. Pick one (but try to understand both):

1. Define a function

   ```haskell
   appendList :: List a -> List a -> List a
   ```

   which appends two lists. Then you can implement `initialBoxes` similar to `pictureOfMaze`, using `appendList` instead of `(&)`.

   This is called *folding `appendList` over the set of coordinate*.

2. Alternatively, in your recursions that traverse the coordinate space, pass a list down as a parameter. Initially, this list is `Empty`. If the current coordinate is not a `Box`, you simply pass it on. If the current coordinate is a box, you add an `Entry` to the list, and pass the extended list on.

   The helper function could possibly have type

   ```haskell
   go :: Integer -> Integer -> List Coord -> List Coord
   ```

   Such a parameter is called an *accumulating parameter*.

You can test your `initialBoxes` value using

```haskell
main = drawingOf (pictureOfBoxes initialBoxes)
```

## Step 3: Many mazes

The `maze` as given has boxes in the initial position. That is no good in the long run. Therefore, you need to define two more mazes:

- Define

  ```haskell
  noBoxMaze :: Coord -> Maze
  ```

  which behaves like `maze` with the exception that where `maze` would return a `Box`, this returns `Ground`.

  Use this in `pictureOfMaze` instead of `maze`. You can test this using

  ```haskell
  main = drawingOf pictureOfMaze
  ```

- Define

  ```haskell
  mazeWithBoxes :: List Coord -> (Coord -> Maze)
  ```

  which behaves like `noBoxMaze` for every coordinate that is not in the list, but returns `Box` if queried for a coordinate that is in the given list of coordinates.

  It will be useful (also below) to define a function

  ```haskell
  eqCoord :: Coord -> Coord -> Bool
  ```

  that checks if two coordinates are the same.

  Note that `mazeWithBoxes`, partially applied to one argument (the list with the curren positions of the boxes), has the same type as `maze`.

## Step 4: Draw the state

Implement

```haskell
draw :: State -> Picture
```

The picture consists of three elements:

- The player, at the current position (use `player` and `atCoord`).
- The boxes in their current positions (use `pictureOfBoxes`).
- The `pictureOfMaze` (which uses `noBoxMaze` internally).

## Step 5: Handling event

Implement

```haskell
handleEvent :: Event -> State -> State
```

React to the arrow keys only. such an event can either succeed or fail.

It succeeds if the tile moved to is either `Ground` or `Storage` or `Box`. If it is `Box`, the next tile in that direction has to be `Ground` or `Storage`. It fails otherwise.

If the move succeeds, update the state with the new position of the player, the direction he walked to, and the updated position of the boxes.

Hint (you can do it differently if you want): To update the position of the boxes, define a function

```haskell
moveFromTo :: Coord -> Coord -> (Coord -> Coord)
```

that takes a position to move from, a position to move to, and the coordinate to adjust. If the first parameter matches the third, return the second, otherwise the third. You can use this function with `mapList` to update the whole list of coordinates. This conveniently does the right thing (i.e. nothing) if no box is pushed, and does the right thing with boxes that are not pushed. Do not worry about efficiency here.

If the move fails, update only the direction the player.

## Step 6: Putting it all together

Define an `Interaction` based on the functions you defined in the prevoius steps. Wrap it in `resetable` and `withStartScreen`, so that pressing the escape key returns to the start screen.

It should now behave roughly like this:

[see on CodeWorld](https://code.world/run.html?mode=haskell&amp;dhash=De_0DFUfkO9MxkNAEQ5DhFA)

## Step 7: Winning

One bit is missing: If the game has been won, you need to say so, and futher interaction should stop.

Define a function

```haskell
isWon :: State -> Bool
```

which is `True` if the game is won. It is won if every box is on a `Storage` tile. To that end, define two helper functions:

- `haskell isOnStorage :: Coord -> Bool` that returns true if the given coordinate is a `Storage` field.
- `haskell allList :: List Bool -> Bool` which returns true if all entries in the list are `True`, and `False` otherwise. If given an empty list it should also return `True`.

You can implement `isWon` using these two functions and `mapList`.

Use `isWon` in two places:

- In `draw`, if the game is won, write `You won!` or something similar across the drawn picture of the game.
- In `handleEvent`, if the game is won, ignore all events (i.e. return the state unaltered)

The final game should now behave like this:

[see on CodeWorld](https://code.world/run.html?mode=haskell&amp;dhash=DDav7JxxBO9jwO6B8-3ejRA)

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).