[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 8: Functor and Applicative

CIS 194: Homework 8
Due Tuesday, October 25

## Exercise 1: A `Functor` instance

Given the following two data type definitions:

```haskell
data ComplicatedA a b
    = Con1 a b
    | Con2 [Maybe (a -> b)]

data ComplicatedB f g a b
    = Con3 (f a)
    | Con4 (g b)
    | Con5 (g (g [b]))
```

Implement a `Functor` instance for both data types. You may have to add a `Functor` constraint on some of `ComplicatedB`’s type parameters.

## Exercise 2: Rewriting monadic code

The following functions are written using the `Monad` combinators and/or `do`-notation, but not all of them use the full power of `Monads`. Some can be rewritten to use only `Applicative` or even only `Functor` laws.

Copy the following functions into your file and rewrite them to have only a `Functor` or `Applicative` constraint, if possible. Append a `'` to the name of the rewritten function, so that you can test both in GHCi. As an example, I have done it for the first function.

If it is not possible, add a small note in a comment why you think it is not possible.

The rewritten functions should behave identical for any lawful instance of `Monad`.

```haskell
func0 :: Monad f => (a -> a) -> f a -> f a
func0 f xs = do
    x <- xs
    return (f (f x))

func0' :: Functor f => (a -> a) -> f a -> f a
func0' f xs = (f . f) <$> xs

func1 :: Monad f => f a -> f (a,a)
func1 xs = xs >>= (\x -> return (x,x))

func2 :: Monad f => f a -> f (a,a)
func2 xs = xs >>= (\x -> xs >>= \y -> return (x,y))

func3 :: Monad f => f a -> f (a,a)
func3 xs = xs >>= (\x -> xs >>= \y -> return (x,x))

func4 :: Monad f => f a -> f a -> f (a,a)
func4 xs ys = xs >>= (\x -> ys >>= \y -> return (x,y))

func5 :: Monad f => f Integer -> f Integer -> f Integer
func5 xs ys = do
    x <- xs
    let x' = x + 1
    y <- (+1) <$> ys
    return (x' + y)

func6 :: Monad f => f Integer -> f (Integer,Integer)
func6 xs = do
    x <- xs
    return $ if x > 0 then (x, 0)
                      else (0, x)

func7 :: Monad f => f Integer -> f (Integer,Integer)
func7 xs = do
    x <- xs
    if x > 0 then return (x, 0)
             else return (0, x)

func8 :: Monad f => f Integer -> Integer -> f Integer
func8 xs x = pure (+) <*> xs <*> pure x

func9 :: Monad f => f Integer -> f Integer -> f Integer -> f Integer
func9 xs ys zs = xs >>= \x -> if even x then ys else zs

func10 :: Monad f => f Integer -> f Integer
func10 xs = do
    x <- xs >>= (\x -> return (x * x))
    return (x + 10)
```

## Exercise 3: A parser monad

A great example for the convenience and usefulness of monads is parser combinators. In this exercise, you will build a small parser combinator library yourself. In the end, the following code will, for example, parse CSV files:

```haskell
parseCSV :: Parser [[String]]
parseCSV = many parseLine
  where
    parseLine = parseCell `sepBy` char ',' <* char '\n'
    parseCell = do
        char '"'
        content <- many (anyCharBut '"')
        char '"'
        return content
```

Try to understand the above code! The operator `<*` comes with the `Applicative` class. Read the documentation to see what it does.

Some of the code you will write will bear resemblance with the `Supply` monad code from last week.

1. Define the type `Parser` of kind `* -> *`:

   ```haskell
   data Parser a = P (…)
   ```

   A value of type `Parser a` is a function that takes a string (the remaining input at this point) and returns either `Nothing`, if parsing failed, or otherwise returns a value of type `a` and the remaining input.

   Also define

   ```haskell
   runParser :: Parser a -> …
   runParser (P p) = p
   ```

   to remove the `P` constructor. Use this rather than explicit pattern matches in the functions below, as otherwise some recursive definitions will loop, because pattern matching (and hence evaluation) happens too early.

2. Define the function

   ```haskell
   parse :: Parser a -> String -> Maybe a
   ```

   which is the main entry point to run a parser. It shall return successfully only if the parser consumed the whole input, i.e. if the function inside the `Parser a` returns a value of type `a` along with the empty string.

3. Implement a function

   ```haskell
   noParser :: Parser a
   ```

   which represents the always failing parser.

   You should have

   ```haskell
   parse noParser input == Nothing
   ```

   for all inputs `input`, even the empty string.

4. Implement a function

   ```haskell
   pureParser :: a -> Parser a
   ```

   which represents the parser that consumes no input and returns its argument.

   You should have

   ```haskell
   parse (pureParser x) "" == Just x
   xs ≠ "" ⇒ parse (pureParser x) xs == Nothing
   ```

5. Declare

   ```haskell
   instance Functor Parser where
       fmap …
   ```

   You should have

   ```haskell
   parse (fmap f p) input == fmap f (parse p input)
   ```

   for all `f`, `p` and `input`.

6. Declare

   ```haskell
   instance Applicative Parser where
       pure = pureParser
       fp <*> fx = …
   ```

   which applies the left parser to the input first to get the function. If it succeeds, it applies the right parser to the remaining input to get the argument, and returns the function applied to the argument, and the leftover input by the right argument.

7. Declare

   ```haskell
   instance Monad Parser where
       return = pureParser
       fa >>= k = …
   ```

   which works quite similar to `<*>`.

8. Define the primitive parser

   ```haskell
   anyChar :: Parser Char
   ```

   that fails if the input is empty, and takes one character off the input otherwise:

   ```haskell
   parse anyChar "" == Nothing
   parse anyChar [c] == Just c
   length xs > 1 ⇒ parse anyChar xs == Nothing
   ```

9. Define the functions

   ```haskell
   char :: Char -> Parser ()
   anyCharBut :: Char -> Parser Char
   ```

   *without* breaking the abstraction introduced by the `Parser` data type, i.e. using only the combinators introduced above. You can use do-notation if you want.

10. Define the combinator

    ```haskell
    orElse :: Parser a -> Parser a -> Parser a
    ```

    which tries the left parser. If it succeeds, it uses that, otherwise it runs the second parser on its input. This implements backtracking in a very naive way (so don’t expect this parser to have the best performance characteristics – there are highly optimized parser libraries out there).

    You should have

    ```haskell
    parse (noParser `orElse` p) input == parse p input
    parse (pureParser x `orElse` p) input == parse (pureParser x) input
    parse (anyChar `orElse` pureParser '☃') "" == Just '☃'
    parse (anyChar `orElse` pureParser '☃') [c] == Just c
    length xs > 1 ⇒ parse (anyChar `orElse` pureParser '☃') xs == Nothing
    ```

11. Define the combinator

    ```haskell
    many :: Parser a -> Parser [a]
    ```

    which applies the given parser as often as possible until it fails, and then returns all results as a list. Implement this again *without* breaking the abstraction, using the combinators above.

    You should have

    ```haskell
    parse (many anyChar) xs = Just xs
    parse (many noParser) "" = Just []
    not (null xs) ⇒ parse (many noParser) xs = Nothing
    
    -- if no '\n' in xs, then also:
    parse (many anyCharBut '\n' <* char '\n') (xs++"\n") = Just xs
    ```

    Fun facts about `many` that you should ponder (not part of the homework):

    - The parser `many p` never fails.
    - The expression `many (pure x)` is not very useful. Do you see why?
    - What happens if you apply `many` to `many`?

12. Define the combinator

    ```haskell
    sepBy :: Parser a -> Parser () -> Parser [a]
    ```

    so that `p1 sepBy p2` applies the `p1`, then `p2`, then `p1` and so on. Succeeds if the very first invocation of `p1` fails, returning the empty string. Also succeeds if any invocation of `p2` fails, returning the results of all `p1` invocations as a list. Implement this again *without* breaking the abstraction, using the combinators above.

    You should have

    ```haskell
    -- if xs is non-empty and does not end with '\n', then
    parse (many (anyCharBut '\n') `sepBy` char '\n') xs = Just (lines xs)
    ```

With all this in place, test the CSV parser:

```haskell
parse parseCSV "\"ab\",\"cd\"\n\"\",\"de\"\n\n"
    == Just [["ab", "cd"],["","de"],[]]
```

## Exercise 4: Parsing an INI file

Here is a specification of an INI-like file format.

> The file consists of one or more sections. Each section starts with a section header, which is a line consisting of just the section title enclosed in square brackets. Section titles are identifiers.
>
> The body of each section is a (possibly empty) list of declarations, which is an identifier, followed by any number of space character, followed by the = sign, followed by more spaces, followed by the value. The value is the remaining content of the line, and may contain any characters except for the newline character ‘’, which terminates the line.
>
> The body of each section may contain empty lines and comment lines (lines starting with `#`). These are to be ignored.
>
> An identifier consists of letters or digits (see `isAlphaNum` in `Data.Char`) and must be non-empty.

And here is an example file:

```haskell
[requests]
desiredFood = cookies
desiredQuantity = 20

[supply]
flour = 20 ounzes

sugar = none!
[conclusion]
# none!
```

Write a parser for this file format:

```haskell
type Identifer = String
type Declaration = (Identifer, String)
type Section = (Identifer, [Declaration])
type INIFile = [Section]
parseINI :: Parser INIFile
```

The above file should parse as

```haskell
[ ("requests",[("desiredFood","cookies"),("desiredQuantity","20")])
, ("supply",[("flour","20 ounzes") ,("sugar","none!")])
, ("conclusion",[])]
```

You might want to add further general combinators to your parser library, e.g. a variant `many1` that works similar to `many` but fails if it cannot parse at least once, and maybe `letterOrDigit :: Parser Char` might be useful.

You can use this main function as a driver (and learn from it):

```haskell
import System.Environment
import System.IO
import System.Exit

main :: IO ()
main = do
    args <- getArgs
    input <- case args of
        [] -> getContents
        [fileName] -> readFile fileName
        _ -> hPutStrLn stderr "Too many arguments given" >> exitFailure
    case parse parseINI input of
        Just i -> print i
        Nothing -> do
            hPutStrLn stderr "Failed to parse INI file."
            exitFailure
```

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).