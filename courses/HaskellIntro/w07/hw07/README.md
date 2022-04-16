[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 7: Laziness

CIS 194: Homework 7
Due Tuesday, October 18

## Exercise 1: Fibonacci numbers

The *Fibonacci numbers* `F_n` are defined as the sequence of integers, beginning with `1` and `1`, where every integer in the sequence is the sum of the previous two. That is,

```
F_0 = 1 
F_1 = 1 
F_n = F_{n-1} + F_{n-2}
```

For example, the first fifteen Fibonacci numbers are: 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, … 

It’s quite likely that you’ve heard of the Fibonacci numbers before. The reason they’re so famous probably has something to do with the simplicity of their definition combined with the astounding variety of ways that they show up in various areas of mathematics as well as art and nature.

- Translate the above definition of Fibonacci numbers directly into a recursive function definition of type

  ```haskell
  fib :: Integer -> Integer
  ```

  so that `fib n` computes the nth Fibonacci number F_n.

- Now use `fib` to define the of all Fibonacci numbers,

  ```haskell
  fibs1 :: [Integer]
  ```

  (*Hint*: You can write the list of all positive integers as `[0..]`.)

Try evaluating `fibs1` at the `ghci` prompt. You will probably get bored watching it after the first 30 or so Fibonacci numbers, because `fib` is ridiculously slow. Although it is a good way to *define* the Fibonacci numbers, it is not a very good way to *compute* them – in order to compute `F_n` it essentially ends up adding 1 to itself `F_n` times!

For example, below is the tree of recursive calls made by evaluating `fib 5`.![img](https://www.seas.upenn.edu/~cis194/fall16/images/fibcalls.svg)

As you can see, it does a lot of repeated work. In the end, `fib` has running time `O(F_n)`, which (it turns out) is equivalent to `O(phi^n)`, where `phi = (1 + sqrt{5})/2` is the “golden ratio”. That’s right, the running time is *exponential* in `n`. What’s more, all this work is also repeated from each element of the list `fibs1` to the next. Surely we can do better:

- Your task [1](https://www.seas.upenn.edu/~cis194/fall16/hw/07-laziness.html#fn1) for this exercise is to come up with more efficient implementation. Specifically, define the infinite list

  ```haskell
  fibs2 :: [Integer]
  ```

  so that it has the same elements as `fibs1`, but computing the first `n` elements of `fibs2` requires only (roughly) `n` addition operations.

  *Hint:* You know that the list of Fibonacci numbers starts with 1 and 1, so

  ```haskell
  fibs2 = 1 : 1 : …
  ```

  is a great start. The thing after the second `(:)` will have to mention `fibs2`, of course, because subsequent Fibonacci numbers are built using previous ones. Oh, and `zipWith` and `tail` will be helpful, too. (Why is `tail` here OK?)

## Exercise 2: Streams

We can be more explicit about infinite lists by defining a type `Stream` representing lists that *must be* infinite. (The usual list type represents lists that *may* be infinite but may also have some finite length.)

In particular, streams are like lists but with *only* a “cons” constructor – whereas the list type has two constructors, `[]` (the empty list) and `(:)` (cons), there is no such thing as an *empty stream*. So a stream is simply defined as an element followed by a stream:

```haskell
data Stream a = Cons a (Stream a)
```

- Write a function to convert a `Stream` to an infinite list,

  ```haskell
  streamToList :: Stream a -> [a]
  ```

- To test your `Stream` functions in the succeeding exercises, it will be useful to have an instance of `Show` for `Stream s`. However, if you put deriving `Show` after your definition of `Stream`, as one usually does, the resulting instance will try to print an entire `Stream` – which, of course, will never finish. Instead, make your own instance of `Show` for `Stream`,

  ```haskell
  instance Show a => Show (Stream a) where
  show …
  ```

  which works by showing only some prefix of a stream (say, the first 20 elements).

- Write a function

  ```haskell
  streamRepeat :: a -> Stream a
  ```

  which generates a stream containing infinitely many copies of the given element.

- Write a function

  ```haskell
  streamMap :: (a -> b) -> Stream a -> Stream b
  ```

  which applies a function to every element of a Stream .

- Write a function

  ```haskell
  streamIterate :: (a -> a) -> a -> Stream a
  ```

  which generates a `Stream` from a “seed” of type `a`, which is the first element of the stream, and an “unfolding rule” of type `a -> a` which specifies how to transform the seed into a new seed, to be used for generating the rest of the stream.

  Example:

  ```haskell
  streamIterate ('x' :) "o" == ["o", "xo", "xxo", "xxxo", "xxxxo", …
  ```

- Write a function

  ```haskell
  streamInterleave :: Stream a -> Stream a -> Stream a
  ```

  which interleaves the elements from 2 `Stream`s. You will want `streamInterleave` to be *lazy* in its second parameter. This means that you should *not* deconstruct the second `Stream` in the function.

  Example:

  ```haskell
  streamInterleave (streamRepeat 0) (streamRepeat 1) ==
      [0, 1, 0, 1, 0, 1, …
  ```

Now that we have some tools for working with streams, let’s create a few:

- Define the stream

  ```haskell
  nats :: Stream Integer
  ```

  which contains the infinite list of natural numbers (0, 1, 2, …

- Define the stream

  ```haskell
  ruler :: Stream Integer
  ```

  which corresponds to the *ruler function* `[ 0,1,0,2,0,1,0,3,0,1,0,2,0,1,0,4,… ]` where the \(n\)th element in the stream (assuming the first element corresponds to \(n=1\)) is the largest power of \(2\) which evenly divides \(n\).

  *Hint:* Try to find the a pattern that `ruler` follows. Use `streamInterleave` to implement `ruler` in a clever way that does not have to do any divisibility testing. Do you see why you had to make `streamInterleave` lazy in its second parameter?

## Exercise 3: The Supply monad

In many applications, you want to write code that takes an element of some supply: If you are writing a compiler, you need to assign unique identifiers to elements. If you are modelling some random computation, you need to draw random numbers. If you are assigning students to seats for an exam, you need to find the next free seat.

Obviously, taking one element from a supply is a side effect, so we need to model it in terms of pure functions. One obvious model is

```haskell
type Supply s a = Stream s -> (a, Stream s)
```

Now a value of type `Supply a` models a computation that uses elements from a stream of `s` value and returns both its result of type `a` and the remaining stream.

This type is unfortunately not tight: It allows for functions that take something of the stream but leave it unchanged, or add new elements to the stream, or change it completely. To avoid this, we use *abstraction*: We define `Supply` as its own data type. Only functions that use the constructor `S` can then create `Supply`-values. If all those (few, primitive) functions are well-behaving, everything built on top are also well-behaving. In a real application we would put this in a separate module and not export the `S` constructor, thereby enforcing that every `Supply` is well-behaving.

```haskell
data Supply s a = S (Stream s -> (a, Stream s))
```

Implement the following functions. Because of the constructor `S`, one of these two idioms might be handy

```haskell
idiom1 :: Supply s a
idiom1 = S (\xs -> …)

idiom2 :: Supply s a
idiom2 = S go
  where go xs = …
```

- The most primitive function is

  ```haskell
  get :: Supply s s
  ```

  which takes the first element of the stream, returning it as the result, and the remaining stream.

- Another important function is

  ```haskell
  pureSupply :: a -> Supply s a
  ```

  which produces a supply-using computation that does not actually consume any of the stream.

- We have seen `map`-like functions for many data structures now. We also want one for supply:

  ```haskell
  mapSupply :: (a -> b) -> Supply s a -> Supply s b
  ```

- We might do the same with binary functions:

  ```haskell
  mapSupply2 :: (a -> b -> c) -> Supply s a -> Supply s b -> Supply s c
  ```

  From the type signature it is not clear whether the supply computation in the second or in the first argument get to read from the supply first. We want the first one to read first, and the second one gets whatever is left over.

- Towards making `Supply` a `Monad`, you need to define the bind function, which has type

  ```haskell
  bindSupply :: Supply s a -> (a -> Supply s b) -> Supply s b
  ```

  Compare the implementation of the previous two functions, and observe their similarities and differences.

- Eventually, we want to run a computation of type `Supply s`. Implement

  ```haskell
  runSupply :: Stream s -> Supply s a -> a
  ```

These building blocks are sufficient to turn `Supply s` into a Monad. This is the magical incarnation (we will talk more about `Functor` and `Applicative` later):

```haskell
instance Functor (Supply s) where
    fmap = mapSupply

instance Applicative (Supply s) where
    pure = pureSupply
    (<*>) = mapSupply2 id

instance Monad (Supply s) where
    return = pureSupply
    (>>=) = bindSupply
```

Use the supply monad to label the leaves of a tree from left to right with the natural numbers:

```haskell
data Tree a = Node (Tree a) (Tree a) | Leaf a deriving Show

labelTree :: Tree a -> Tree Integer
labelTree t = runSupply nats (go t)
  where
    go :: Tree a -> Supply s (Tree s)
    go …
```

You should be able get this in GHCi:

```haskell
*Main> let t = let l = Leaf () ; n = Node in n (n (n l l) l) (n l l)
*Main> labelTree t
Node (Node (Node (Leaf 0) (Leaf 1)) (Leaf 2)) (Node (Leaf 3) (Leaf 4))
```

# Non-Exercise: Dice throws

You don’t actually have to do anything here, besides reading the code, running it, and cherish how pretty it became to implement probabilistic sampling using the Monad you defined. Maybe you also want to change some of the algorithms there.

Here is a type synonym that makes it more explicit what we want here, and a function to run such a computation, feeding it random numbers in the `IO` monad:

```haskell
import System.Random

type Rand a = Supply Integer a

randomDice :: RandomGen g => g -> Stream Integer
randomDice gen =
    let (roll, gen') = randomR (1,6) gen
    in Cons roll (randomDice gen')

runRand :: Rand a -> IO a
runRand r = do
    stdGen <- getStdGen
    let diceRolls = randomDice stdGen
    return $ runSupply diceRolls r

averageOfTwo :: Rand Double
averageOfTwo = do
    d1 <- get
    d2 <- get
    return $ fromIntegral (d1 + d2) / 2

bestOutOfTwo :: Rand Double
bestOutOfTwo = do
    d1 <- get
    d2 <- get
    return $ fromIntegral $ if (d1 > d2) then d1 else d2

-- Look, ma, I’m recursive!
sumUntilOne :: Rand Double
sumUntilOne = do
    d <- get
    if (d == 1) then return 0
                else do s <- sumUntilOne
		        return (s + fromIntegral d)

sample :: Int -> Rand Double -> Rand (Double, Double)
sample n what = do
    samples <- replicateM n what
    return (maximum samples, sum samples / fromIntegral n)

main = mapM_ go [ ("average of two", averageOfTwo)
	        , ("bestOutOfTwo",   bestOutOfTwo)
	        , ("sumUntilOne",    sumUntilOne)
	        ]
  where
    n = 10000
    go (name, what) = do
	(max, avg) <- runRand (sample n what)
	putStrLn $ "Playing \"" ++ name ++ "\" " ++ show n ++ " times " ++
	           "yields a max of " ++ show max ++ " and an average of " ++
		   show avg ++ "."
```

Here is the output of one run. If your numbers differ greatly, check your code. (I had a bug in `mapSupply2` which let the average equal the max. Can you guess what I did wrong?)

```haskell
Playing "average of two" 10000 times yields a max of 6.0 and an average of 3.5168.
Playing "bestOutOfTwo" 10000 times yields a max of 6.0 and an average of 4.4871.
Playing "sumUntilOne" 10000 times yields a max of 191.0 and an average of 20.4356.
```

------

1. Of course there are several billion Haskell implementations of the Fibonacci numbers on the web, and I have no way to prevent you from looking at them; but you’ll probably learn a lot more if you try to come up with something yourself first.[↩](https://www.seas.upenn.edu/~cis194/fall16/hw/07-laziness.html#fnref1)

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).