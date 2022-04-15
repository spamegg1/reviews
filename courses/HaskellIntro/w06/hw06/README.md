[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Non-Homework 6: IO

CIS 194: Homework 6
This homework is optional (due to the fall brake). If you do it, you can still send your solution to the TA for feedback.

The general remarks about style and submission from [the first week](https://www.seas.upenn.edu/~cis194/fall16/hw/01-intro.html) still apply.

## Exercise 1: ASCII-Sokoban

The goal of this homework is to take your Sokoban game from Homework 4, and replace the CodeWorld environment with your own, text based environment.

- Take your code, or [this code](https://code.world/haskell#Pny8KKiYCum4pB0cM3FNvdA) as a starting base. Remove anything not related to the sokoban game.

- Change the `Interaction` type to be suitable for text output:

  ```haskell
  data Interaction world = Interaction
      world
      (String -> world -> world)
      (world -> String)
  ```

  The first `String` replaces the `Event` type, and contains the description of one key press. The second `String` replaces the `Picture` type and should contain the content of the screen. This string should contain 21 `'\n'`-terminated lines of 21 characters.

- Remove the `import CodeWorld`.

- Rewrite the `drawState` function to produce text output. Use suitable characters for the various things, e.g. `#` for walls and so on. There is probably little need to distinguish blank and ground.

  You can rewrite it from scratch, or you can actually try to recreate the API of CodeWorld so that the structure of the code changes only little.

  There are various ways of doing that:

  1. You might define

     ```haskell
     type Picture = Integer -> Integer -> Maybe Char
     ```

     with, for example, `blank _ _ = Nothing`, and `(&)` letting the function on the left take precedence over the function on the right, and `translated` changes the parameters appropriately.

  2. More fancy in terms of functional programming, you might want to define

     ```haskell
     type DrawFun = Integer -> Integer -> Char
     type Picture = DrawFun -> DrawFun
     ```

     so that a `Picture` is actually a function that changes an existing picture. Now, `blank = id` and `(&) = (.)`. For `translated` you have to shift coordinates twice [1](https://www.seas.upenn.edu/~cis194/fall16/hw/06-io-and-monads.html#fn1).

- Create your own

  ```haskell
  runInteraction :: Interaction s -> IO ()
  ```

  Initially, make sure input goes through to the program directly (and not after enter is pressed):

  ```haskell
  hSetBuffering stdin NoBuffering
  ```

  Then blank the screen (using `putStr "\ESCc"`) and render the the initial state. Now go to a recursive function `go`:

  1. Use `getChar` to read the next character.
  2. Passes it to the handle function in the `Interaction` to get the new state.
  3. Blanks the screen using `putStr "\ESCc"`.
  4. Draws the state (using the draw function in the `Interaction` and `putStr`).
  5. Calls itself, with the new state.

You can make the `go` function detect presses of `q` to quit the game. In this case, simply do not call `go` again.

Handling arrow keys is a bit tricky, as pressing the “→” key actually yields the sequence `"\ESC[C"?`. (Simply run `getLine` in ghci, press a key and then enter to see that.), but pressing the escape key produces simply `"\ESC"`. That is tricky!

Here is one way of handling it:

1. Create a function `getAllInput :: IO [Char]` that uses `getChar` to read a character, then uses `hReady stdin` from `System.IO` to check if there is another character to read, and so on, until all characters that are currently available are read.
2. Pattern match on the initial segment of that list to detect `'\ESC':'[':'C':rest`, or `'\ESC':rest`, or simply `c:rest`, and act accordingly.
3. Pass the unmatched portion of the input on to the next iteration of `go`, and prepend it to what you read from `readAllInput` before matching on it, so that no key presses are lost.

If you are still bored, you can make it colorful, using [ANSI escape sequences](https://en.wikipedia.org/wiki/ANSI_escape_code#Colors)!

Enjoy your own Sokoban.

------

1. If you like group theory: it’s a conjugate![↩](https://www.seas.upenn.edu/~cis194/fall16/hw/06-io-and-monads.html#fnref1)

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).