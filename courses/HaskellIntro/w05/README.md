[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

Real World Haskell

So far, we have confined ourselves to the walled garden of CodeWorld. This provided us a smooth start, but of course Haskell is much more than that. This week we will learn about “real” Haskell: How to run it locally, how to compile programs, and what the standard libray provides for us.

# More ways to run Haskell

In order to use Haskell locally, we have to install it first. I will not go into the details of that now. Just follow the [links on the resources](http://cis.upenn.edu/~cis194/fall16/resources.html) page. There are plenty of competing installation instructions out there in the world. I do not particularly care which one you follow, but make sure you get GHC-7.10.3.

Once installed, you have two new programs `ghc` and `ghci`. The former is the compiler:

```bash
$ ghc
ghc: no input files
Usage: For basic information, try the `--help' option.
```

Without an input file it does little.

## The interpreter

The latter is an interpreter:

```bash
$ ghci
GHCi, version 7.10.3: http://www.haskell.org/ghc/  :? for help
Prelude>
```

and running it gives us a prompt where we can now go ahead and evalute Haskell expressions:

```haskell
Prelude> 1+1
2
Prelude> 2^2^2
16
Prelude> 2^2^2^2
65536
Prelude> 2^2^2^2^2
2003529930406846464979072351560255…
```

Besides evaluating expressions, we can bind variables or define functions (using `let`), and even declare data types:

```haskell
Prelude> data Pair a = Pair a a deriving Show
Prelude> let four x = Pair (Pair x x) (Pair x x)
Prelude> let fourTrues = four True
Prelude> fourTrues
Pair (Pair True True) (Pair True True)
```

There are a few useful command that allow us to query the interpreter:

```haskell
Prelude> :t four
four :: a -> Pair (Pair a)
Prelude> :i Pair
data Pair a = Pair a a           -- Defined at <interactive>:15:1
instance Show a => Show (Pair a) -- Defined at <interactive>:15:31
```

## Loading files

It is also possible to enter mult-line functions definition into the interpreter, but that gets quickly unweildy. But there is a better way: You can load files into the interpreter.

So here I load last week’s homework. The file gets loaded as `Main`, and I can use the `:browse` command to see the contents of the file:

```haskell
$ ghci LastWeek.hs
GHCi, version 7.10.3: http://www.haskell.org/ghc/  :? for help
[1 of 1] Compiling Main             ( LastWeek.hs, interpreted )
Ok, modules loaded: Main.
*Main> :browse Main
main :: IO ()
data List a = Empty | Entry a (List a)
mapList :: (a -> b) -> List a -> List b
combine :: List Picture -> Picture
allList :: List Bool -> Bool
```

So we can play around in the file:

```haskell
*Main> let list = Entry 23 (Entry 42 Empty)
*Main> nth list 2
42
*Main> mapList (+1) list

<interactive>:7:1:
    No instance for (Show (List b0)) arising from a use of ‘print’
    In the first argument of ‘print’, namely ‘it’
    In a stmt of an interactive GHCi command: print it
```

The latter did not work. We can deduce from the error message that the interpreter is using the command `print` to print out the results, and that this function requires its argument to be of a type that has a `Show` instance.

Luckily, that instance can also be derived. So after editing the file, adding `deriving Show` to the `data List a = …` line, I can reload the file with `:r` and now this works (but I have to redefine `list` – all definitions are lost when you reload):

```haskell
*Main> :r
[1 of 1] Compiling Main             ( LastWeek.hs, interpreted )
Ok, modules loaded: Main.
*Main> let list = Entry 23 (Entry 42 Empty)
*Main> mapList (+1) list
Entry 24 (Entry 43 Empty)
```

I can also, if I so want, run the `main` function from the interpreter:

```haskell
*Main> main
Open me on http://127.0.0.1:3000/
```

In this case, because `main` calls `interactionOf`, this starts a local webserver that I can access in the browser and play our game. It keeps running even when I close the tab; I have to press Ctrl-C to return to the prompt of the interpreter.

## Compiling a program

But clearly, entering `main` in the interpreter is not the desired way of running a program. Therefore, we use the compiler to produce a binary that we can run like any other compiled program of any other programming language:

```haskell
$ ghc -O LastWeek.hs
[1 of 1] Compiling Main             ( LastWeek.hs, LastWeek.o )
Linking LastWeek ...
$ ./LastWeek
Open me on http://127.0.0.1:3000/
Application needs to be re-compiled with -threaded flag
ExitFailure 1
```

Nice, the program runs, but complains about a missing compile-time flag. So let’s just do what it says – for your own non-codeworld programs, this is not necessary:

```haskell
$ ghc --make -O -threaded LastWeek.hs
Linking LastWeek ...
$ ./LastWeek
Open me on http://127.0.0.1:3000/
^Cuser interrupt
```

The flag `-O` tells the compiler to optimize the code. Without it, it would favor quick compilation over runtime performance.

## Documentation

Haskell comes with API documentation in HTML format. Depending on how you installed GHC, it may lie in different places. In my case, I open [file:///usr/share/doc/ghc-doc/html/libraries/index.html](file:///usr/share/doc/ghc-doc/html/libraries/index.html). One online copy of that is at https://downloads.haskell.org/~ghc/latest/docs/html/libraries/index.html.

As you can see, there are many modules, most of which you probably do not care about. The important one to start with is the [`Prelude`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html), which contains all the functions, types and classes that are in scope automatically.

# The standard library

Let me introduce some of these now.

## `Bool`

```haskell
data Bool = False | True
```

We have already used [`Bool`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Bool) a lot, and there is not much to see here. Fun fact: [`otherwise`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#v:otherwise) is not built in or anything, but simply a value that is defined to be equal to `True`, with the sole purpose of making guards easier to read.

## `Maybe`

Often, a given type happens to have just one element to few. For example our `nth` function with type `nth :: List a -> Integer -> a`: It claims that for every list of `a`s and any index, it returns an `a`. But that is a lie: For many indices it does not return a `a`, but rather returns!

So the way to do this properly is to have a type that is only maybe an `a`. And precisely such a type is [`Maybe`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Maybe):

```haskell
data Maybe a = Nothing | Just a
```

An idiomatic implementation of `nth` would thus be

```haskell
nth :: List a -> Integer -> Maybe a
nth Empty _ = Nothing
nth (Entry x _) 1 = Just x
nth (Entry _ xs) n = nth xs (n-1)
```

and every *user* of `nths` that needs to get its hand on the actual `a` has to pattern-match against the `Maybe`, and is thus forced to handle the `Nothing` case.

Contrast this to the usual pattern in other languages, where you would simply return a NULL-pointer (or `None` or `null` or `undef`) here, and the caller *may* test for that, but more often than not does not, thus crashing the program.

You can find some more convenience functions around `Maybe` in the module [`Data.Maybe`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Data-Maybe.html).

## Pairs and tuples

So we have seen how to return one or no result from a list. What if we want to return more than one result? We could always define a new data type with a constructor with multiple arguments, and that would be a fine thing to do. But there is also a convenient thing, namely tuples of (almost) any size:

```haskell
Prelude> (True,1)
(True,1)
Prelude> (True,1,2)
(True,1,2)
Prelude> (True,1,2,Nothing)
(True,1,2,Nothing)
```

While these introduce new syntax, they are behaving just like any other product data type. The way to think about them is as if they were defined using

```haskell
data (a,b) = (a,b)
data (a,b,c) = (a,b,c)
```

although that does not work.

Useful functions are [`fst`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#v:fst) and [`snd`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#v:snd) to obtain the element of a pair, in cases where a pattern match is inconvenient:

```haskell
fst :: (a, b) -> a
snd :: (a, b) -> b
```

These only exist for pairs (i.e. two-tuples). Some people say that larger tuples should not be used anyways, and data types with more helpful names introduced for them.

## `Char`

There is a built-in data type for *characters* called [`Char`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Char). It represents exactly one character, which includes all the fancy unicode characters out there. A character literal is enclosed in single quotes. Control characters can be escaped with a backslash:

```haskell
Prelude> 'a'
'a'
Prelude> '1'
'1'
Prelude> 'ä'
'\228'
Prelude> '∞'
'\8734'
Prelude> '💩'
'\128169'
Prelude> '☃'
'\9731'
Prelude> '☺'
'\9786'
Prelude> '\0'
'\NUL'
```

A large number of functions related to characters can be found in [`Data.Char`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Data-Char.html). These include predicates like `isDigit` or `isSpace` and conversion functions such as `ord` and `chr`.

## Lists

I made you implement lists yourself, but I am sure you know already that lists already exist in Haskell. And because they are so ubiquitous in functional programming, they have special syntax. Again, just like with tuples, this is only syntax, otherwise lists behave just like the `List` type you have defined.

Instead of `List a`, you write `[a]`. Instead of `Empty`, you write `[]`, which is called *Nil*. And instead of `Entry x xs`, you write `x:xs`, which is called *cons*.

The *cons* operator is right-associative, so you can write a four element list as `a:b:c:d:[]` without parethesis.

Furthermore, if you write `[a,b,c,d]` (either as an expression or as a pattern), then this desugars to `a:b:c:d:[]`:

```haskell
Prelude> 1:2:3:4:[]
[1,2,3,4]
Prelude> 1:2:3:4:[] == [1,2,3,4]
True
```

The list operations that you had to define – `map`, `filter`, `length` – are also provided by the prelude. Since a recent version of Haskell, though, they are *generalized*:

```haskell
Prelude> :t length
length :: Foldable t => t a -> Int
```

As you can see, the type of `length` does not mention the list type, but some type class constraint `Foldable`. We will not look into that type class in detail now. Simply imagine it is not there and instead of `t a` it would say `[a]`. Indeed, that is also a valid type for `length`:

```haskell
Prelude> :t length :: [a] -> Int
length :: [a] -> Int :: [a] -> Int
Prelude> length [1,2,3,4]
4
```

You can concatenate two lists using the `(++)` operator; this corresponds to `appendList` from the exercise.

Please have a look at the section “[List operations](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#g:13)” in the documentation of the `Prelude` and familiarize yourself with these functions. Some will be very useful. More on that in the homework. And if these are insufficient, you can find even more in [`Data.List`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Data-List.html).

## Strings

Next is a type for strings. I say “a”, because there are multiple, and things get a bit hairy.

The string type that comes with the Prelude is very simple: It is simply a list of characters. But since writing `[Char]` is not very nice, the Prelude defines a *type synonym* called [`String`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#g:13):

```haskell
type String = [Char]
```

Type synonyms introduce alias names for existing types, but do not introduce new types. So a value of type `String` is *also* a value of type `[Char]`. In contrast to definitions using `data`, no type safety is achieved here.

The string type has advantages: It is very simple to understand; you can use all the list-related functions on it, and [some more](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#g:21) that you find in the Prelude, such as `lines`, `unlines`, `words` and `unwords`.

Strings have their own convenient syntax: Instead of `['H','e','l','l','o']` you may write “Hello”. As usual, special characters need to be escaped: `"First line\nSecond line\nLine\twith\ttabs"`.

Unfortunately, it is quite an inefficient data structure if you have to manage any serious amount of text. As long as it is only for filenames, status messages etc., that is not a worry, but if you need to handle more, you should reach out for a better suited data type.

We will work with `String` for now, but just for reference: There are two `String`-like data types out there that you would reach out to in real code: [`Text`](http://hackage.haskell.org/package/text-1.2.2.1/docs/Data-Text.html) and [`ByteString`](http://hackage.haskell.org/package/bytestring-0.10.6.0/docs/Data-ByteString.html). The former handles Unicode text, and should be used for, well text. The latter handles packed arrays of bytes, and should be used for binary data. Mixing up strings and binary data is another major cause of bugs in any programming language, and again the distinction here helps.

## `Show` and `Read`

We have already noticed that we need an instance of the `Show` type class in order get stuff printed out by the interpreter. The [`Show`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Show) type class provides the function

```haskell
show :: Show a => a -> String
```

which is supposed to provide a textual representation of the given argument.

There is a corresponding converse type class [`Read`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Read) which provides a function

```haskell
read :: Read a => String -> a
```

For well behaving types, `read (show x) == x` should hold. Note that the type of `read` is a lie (historical accident): There are many arguments for which it will raise an exception because the string cannot be parsed! Therefore, do not use this function unless you can guarantee it is safe (e.g. `read`ing an integer after you have otherwise checked that the input consists only of digits)! Better to import [`Text.Read`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Text-Read.html) and use [`readMaybe`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Text-Read.html#v:readMaybe)!

Both `Show` and `Read` can be derived, but you can also define your own instances. It is tempting to use the `Show` instance to create a “pretty” presentation for your datatype, and use that in your program when communicating with the user. This is discouraged practice: The output of `Show` should be usable as code; you should be able to paste it into the source code or interpreter and work with it!

If you want to use type classes to create output to be consumed by the user, create a new type class for that (commonly called `Pretty` or `PP`) or build upon dedicated libraries like [`pretty`](http://hackage.haskell.org/package/pretty) (which exports the module `Text.PrettyPrint.HughesPJClass`).

An exception to this rule are numbers: Using `show` is the usual way of turning an `Integer` or an `Int` into a `String`.

Note: There are is no `Show` instance for function types. Can you imagine why?

## `Ord`

The [`Ord`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#t:Ord) type class provides the methods required to compare elements. This is used by, for example `maximum`.

Besides the expected operations like `(<=)` etc. the type class has a method

```haskell
data Ordering = LT | EQ | GT
compare :: Ord a => a -> a -> Ordering
```

which is a bit more general.

You find this type again in functions like `maximumBy` in `Data.List`, where you can give a custom comparison function. So to use these, you have to return an ordering. But usually you don’t have to create it yourself, but rather use a function like `comparing` in [`Data.Ord`](http://hackage.haskell.org/package/base-4.8.2.0/docs/Data-Ord.html).

## Operators for functions

The Prelude defines two useful operators for functions that you should know about.

```haskell
(.) :: (b -> c) -> (a -> b) -> a -> c
```

The dot composes two functions, just like \(\circ\) in math. So instead of writing

```haskell
foo xs = map show (filter (> 0) xs)
```

you can write

```haskell
foo xs = (map show . filter (> 0)) xs
```

or simply

```haskell
foo = map show . filter (> 0)
```

Thinking about functions that you compose can yield nicer and more typical Haskell code.

```haskell
($) :: (a -> b) -> a -> b
```

The other operator is `($)`. This does not really do anything: `f $ x` is the same as `f x`. The trick is that `$` has a low precedence, so you can use it to avoid parenthesis. Instead of

```haskell
trice f x = f (f (f x))
```

you can write

```haskell
trice f x = f $ f $ f x
```

When your arguments get large, this is a real time-saver.

## A glimpse of IO

We will go into it in more detail later, but here is a glimpse.

When you compile a program using `ghc`, its behaviour is determined by the `main` function, which needs to be of type `IO ()`. In CodeWorld, we have had functions such as `drawingOf` or `interactionOf` that created a value of type `IO ()`. In the prelude, these functions might be of interest to you at this point:

```haskell
putStr   :: String -> IO ()
print    :: Show a => a -> IO ()
interact :: (String -> String) -> IO ()
```

The function [putStr](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#v:putStr) simply outputs a string, [print](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#v:print) uses `show` to print some value, which is good for debugging, and finally [interact](http://hackage.haskell.org/package/base-4.8.2.0/docs/Prelude.html#v:interact) reads the standard input of the program, runs the given function on it, and prints the result. You can already write many useful programs using `interact`!

# Modules

I have mentioned that there are modules besides `Prelude`, and you have already used the `CodeWorld` module. Here is a quick primer how to use modules:

In the simplest case, you simply import a module

```haskell
import Data.List
import CodeWorld
```

## Restricted imports

You can restrict the import to certain types and functions. This is recommended in large, well-maintained code, as it is less likely to break your code if a newer version of the imported module contains a new name that clashes with one of yours:

```haskell
import Data.Function (on, (&))
import Data.Fixed (Fixed(MkFixed), E0)
import Data.Complex (Complex(..))
```

You can selectively not import some function (and you can even do that with the Prelude):

```haskell
import Prelude hiding (head, tail)
```

## Qualifed imports

You can also import a module qualified. You need to fully qualify its functions then:

```haskell
import qualified Data.Text
```

With this, you would use the type `Data.Text.text` and the function `Data.Text.lines`. A common idiom is to import the type unqualified, and the whole module with a short name:

```haskell
import Data.Text (Text)
import qualified Data.Text as T
```

Now you can use `Text` in your type signatures, and use `T.lines` in the code.

Certain modules, such as `Data.Text` or `Data.Map`, are designed to be imported qualified and intentionally use names that would otherwise clash with those in the Prelude.

## Install more packages

Many more modules come with separate *packages*, which need to be downloaded and installed separately. You can use the `cabal` tool for that. For example to install the `text` package, you would run

```bash
$ cabal update
$ cabal install text
```

To find packages, look at the [package repository Hackage](http://hackage.haskell.org/packages/) or consult the API search engine [hayoo](http://hayoo.fh-wedel.de/).

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).