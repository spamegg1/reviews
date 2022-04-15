[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# IO and monads

Remember that Haskell is *lazy* and therefore *pure*. This means two primary things:

1. Functions may not have any external effects. For example, a function may not print anything on the screen. Functions may only compute their outputs.
2. Functions may not depend on external stuff. For example, they may not read from the keyboard, or filesystem, or network. Functions may depend only on their inputs—put another way, functions should give the same output for the same input every time.

But—sometimes we *do* want to be able to do stuff like this! If the only thing we could do with Haskell is write functions which we can then evaluate at the ghci prompt, it would be theoretically interesting but practically useless.

In fact, it *is* possible to do these sorts of things with Haskell, but it looks very different than in most other languages.

# The `IO` type

The solution to the conundrum is a special type called `IO`. Values of type `IO a` are *descriptions of* effectful computations, which, if executed would (possibly) perform some effectful I/O operations and (eventually) produce a value of type `a`. There is a level of indirection here that’s crucial to understand. A value of type `IO a`, *in and of itself*, is just an inert, perfectly safe thing with no effects. It is just a *description* of an effectful computation. One way to think of it is as a *first-class imperative program*.

As an illustration, suppose you have

```haskell
c :: Cake
```

What do you have? Why, a delicious cake, of course. Plain and simple.

![img](https://www.seas.upenn.edu/~cis194/fall16/images/cake.jpg)

By contrast, suppose you have

```haskell
r :: Recipe Cake
```

What do you have? A cake? No, you have some *instructions* for how to make a cake, just a sheet of paper with some writing on it.

![img](https://www.seas.upenn.edu/~cis194/fall16/images/recipe.gif)

Not only do you not actually have a cake, merely being in possession of the recipe has no effect on anything else whatsoever. Simply holding the recipe in your hand does not cause your oven to get hot or flour to be spilled all over your floor or anything of that sort. To actually produce a cake, the recipe must be *followed* (causing flour to be spilled, ingredients mixed, the oven to get hot, *etc.*).

![img](https://www.seas.upenn.edu/~cis194/fall16/images/fire.jpg)

In the same way, a value of type `IO a` is just a “recipe” for producing a value of type `a` (and possibly having some effects along the way). Like any other value, it can be passed as an argument, returned as the output of a function, stored in a data structure, or (as we will see shortly) combined with other `IO` values into more complex recipes.

So, how do values of type `IO a` actually ever get executed? There is only one way: the Haskell compiler looks for a special value

```haskell
main :: IO ()
```

which will actually get handed to the runtime system and executed. That’s it! Think of the Haskell runtime system as a master chef who is the only one allowed to do any cooking.

![img](https://www.seas.upenn.edu/~cis194/fall16/images/chef.jpg)

If you want your recipe to be followed then you had better make it part of the big recipe (`main`) that gets handed to the master chef. Of course, `main` can be arbitrarily complicated, and will usually be composed of many smaller `IO` computations.

So let’s write our first actual, executable Haskell program! We can use the function

```haskell
putStrLn :: String -> IO ()
```

which, given a `String`, returns an `IO` computation that will (when executed) print out that `String` on the screen. So we simply put this in a file called `Hello.hs`:

```haskell
main = putStrLn "Hello, Haskell!"
```

Then typing `runhaskell Hello.hs` at a command-line prompt results in our message getting printed to the screen! We can also use `ghc -O Hello.hs` to produce an executable version called `Hello` (or `Hello.exe` on Windows).

## There is no `String` “inside” an `IO String`

Many new Haskell users end up at some point asking a question like “I have an `IO String`, how do I turn it into a `String`?”, or, “How do I get the `String` out of an `IO String`”? Given the above intuition, it should be clear that these are nonsensical questions: a value of type `IO String` is a description of some computation, a *recipe*, for generating a `String`. There is no `String` “inside” an `IO String`, any more than there is a cake “inside” a cake recipe. To produce a `String` (or a delicious cake) requires actually *executing* the computation (or recipe). And the only way to do that is to give it (perhaps as part of some larger `IO` value) to the Haskell runtime system, via `main`.

## Combining `IO`

As should be clear by now, we need a way to *combine* `IO` computations into larger ones.

### Sequencing

Thinking about our recipe analogy, we could combine two recipes by writing one below the other (and discarding the result of the first one). Does it make sense to perform the steps of a recipe without using the result? Yes, it may: There might be a recipe for “heating up the oven”, and although no food comes out of it, we still want to perform it for the effects it have.

We can do the equivalent thing with `IO` actions, using the `(>>)` operator (pronounced “and then”), which has the type

```haskell
(>>) :: IO a -> IO b -> IO b
```

This simply creates an `IO` computation which consists of running the two input computations in sequence. Notice that the result of the first computation is discarded; we only care about it for its *effects*. For example:

```haskell
main = putStrLn "Hello" >> putStrLn "world!"
```

### Tupling

This works fine for code of the form “do this; do this; do this” where the results don’t really matter. However, in general this is insufficient. What if we don’t want to throw away the result from the first computation?

Speaking in terms of recipes, it would be nice to be able to tack two recipes together and get both results, i.e. to create a multi-course menu.

For that, we would need a function of type `IO a -> IO b -> IO (a,b)`, or, more generally, a function

```haskell
liftM2 :: (a -> b -> r) -> IO a -> IO b -> IO r
```

where we can give a (pure) function that indicates how to combine the two courses.

### Bind

However, this is still insufficient. The recipe analogy gets a bit stretched here, but imagine you have a recipe, suitable for very late night hunger attacks, of the form “pull some random stuff from the freezer”. That would have type `Recipe FrozenStuff`. Now how to prepare the frozen stuff depends on what particular thing you pulled out, and a frozen pizza is prepared differently than a frozen stew. So you need to actually look at `FrozenStuff` to decide what recipe to use to make the stuff yummy. So the second step is actually a *function* from frozen stuff to a recipe that produces yummy stuff: `FrozenStuff -> Recipe YummyStuff`, and you will notice that we cannot combine these two recipes using `liftM2`.

Therefore, there is the operator `(>>=)` (pronounced “bind”) with the type

```haskell
(>>=) :: IO a -> (a -> IO b) -> IO b
```

It takes a computation which will produce a value of type `a`, and a *function* which gets to *compute* a second computation based on this intermediate value of type `a`. The result of `(>>=)` is a (description of a) computation which performs the first computation, uses its result to decide what to do next, and then does that.

For example, we can write a program to read a number from the user and print out its successor. Note our use of `readLn :: Read a => IO a` which is a computation that reads input from the user and converts it into any type which is an instance of `Read`.

```haskell
main :: IO ()
main = putStrLn "Please enter a number: " >>
       readLn >>= \n ->
       let m = n + 1 in
       putStrLn (show m)
```

Of course, this looks kind of ugly, but there are better ways to write it, which we’ll talk about in late today.

Side remark: `readLn` is not a good citizen, as it throws an exception if the input value is not a number. In real code, better write something like:

```haskell
import Text.Read
main = putStrLn "Hello World. Please enter a number:" >>
       getLine >>= \s ->
       case readMaybe s of
           Just n ->  let m = n + 1 in
                      putStrLn (show m)
           Nothing -> putStrLn "That’s not a number! Try again"
```

# Monads

While introducing the `IO` combinators, I talked about recipes. So there seems to be some abstract concept here, and because we like abstraction, let us search for more instances of the pattern.

## Maybe a monad?

Consider the following function, which combines two trees if they have the same shape, and returns `Nothing` if they do not have the same shape:

```haskell
data Tree a = Node (Tree a) a (Tree a) | Empty deriving (Show)

zipTree :: (a -> b -> c) -> Tree a -> Tree b -> Maybe (Tree c)
zipTree _ (Node _ _ _) Empty = Nothing
zipTree _ Empty (Node _ _ _) = Nothing
zipTree _ Empty Empty        = Just Empty
zipTree f (Node l1 x r1) (Node l2 y r2) =
    case zipTree f l1 l2 of
      Nothing -> Nothing
      Just l  -> case zipTree f r1 r2 of
                   Nothing -> Nothing
                   Just r  -> Just $ Node l (f x y) r
```

Clearly, there is some repetition in the last case that obscures whats going on. Twice, we look at some value of type `Maybe c`. If it is `Nothing`, we return `Nothing`. If it is `Just` something, we continue with some code that uses something. But that is precisely the pattern that we saw above when we combined recipes or `IO` actions. And in fact if we factor out the pattern as a separate function, we find that that function has a type signature quite similar to bind:

```haskell
bindMaybe :: Maybe a -> (a -> Maybe b) -> Maybe b
bindMaybe mx f = case mx of
                   Nothing -> Nothing
                   Just x  -> f x

zipTree :: (a -> b -> c) -> Tree a -> Tree b -> Maybe (Tree c)
zipTree _ (Node _ _ _) Empty = Nothing
zipTree _ Empty (Node _ _ _) = Nothing
zipTree _ Empty Empty        = Just Empty
zipTree f (Node l1 x r1) (Node l2 y r2) =
    bindMaybe (zipTree f l1 l2) $ \l ->
    bindMaybe (zipTree f r1 r2) $ \r ->
    Just (Node l (f x y) r)
```

This is already better. But if `bindMaybe` and the bind of `IO` have such a similar type signature, and capture such similar concepts, then we would want to use the same name for it.

## Bind is overloaded

Luckily, we can. In fact, we can use `(>>=)`, as it is in fact overloaded using the `Monad` type class, of which both `IO` and `Maybe` are instances:

```haskell
zipTree :: (a -> b -> c) -> Tree a -> Tree b -> Maybe (Tree c)
zipTree _ (Node _ _ _) Empty = Nothing
zipTree _ Empty (Node _ _ _) = Nothing
zipTree _ Empty Empty        = Just Empty
zipTree f (Node l1 x r1) (Node l2 y r2) =
    zipTree f l1 l2 >>= \l ->
    zipTree f r1 r2 >>= \r ->
    Just (Node l (f x y) r)
```

## A note about Monads

Despite their scary reputation, there’s nothing all that frightening about monads. The concept of a monad started its life as an abstract bit of mathematics from the field of Category Theory (Monads are just monoids on the category of endofunctors!). It so happened that functional programmers stumbled upon it as a useful programming construct!

A monad is handy whenever a programmer wants to sequence actions. The details of the monad says exactly how the actions should be sequenced. A monad may also store some information that can be read from and written to while performing actions.

We’ve already learned about the `IO` monad, which sequences its actions quite naturally, performing them in order, and gives actions access to read and write anything, anywhere. We’ll also see the `Maybe` and `[]` (pronounced “list”) monads, which don’t give any access to reading and writing, but do interesting things with sequencing. There are other monads such as the `Rand` monad, which doesn’t much care about sequencing, but it does allow actions to read from and update a random generator.

One of the beauties of programming with monads is that monads allow programmers to work with mutable state from a pure language. Haskell doesn’t lose its purity when monads come in (although monadic code is often called “impure”). Instead, the degree to which code can be impure is denoted by the choice of monad. For example, the `Rand` monad means that an action can generate random numbers, but can’t for example, write strings to the user. And the `Maybe` monad doesn’t give you any extra capabilities at all, but makes writing possibly-erroring computations much easier to write.

In the end, the best way to really understand monads is to work with them for a while. After programming using several different monads, you’ll be able to abstract away the essence of what a monad really is.

## Another example: Lists

If lists are an instance of monads, then what does its bind operator do? Its type is

```haskell
(>>=) :: [a] -> (a -> [b]) -> [b]
```

From the type signature, we can guess that it replaces every `a` in the first list with the list produced by the second argument, producing one flat list of `b`s.

The mental model here is that `[a]` represents nondeterministic programs that can have multiple possible results.

Here is one example using it, which calculates the Cartesian product of the given list and then keeping the ordered pairs:

```haskell
ordPairs :: Ord t => [a] -> [(a, a)]
ordPairs xs =
    xs >>= \x1 ->
    xs >>= \x2 ->
    if x1 < x2 then [(x1,x2)] else []
```

(This is the first time you see `if…then…else`. It does what you expect.)

## The other methods of Monad

Above, we have already seen the `>>` combinator for `IO`. Indeed, that operator is also overloaded:

```haskell
(>>) :: Monad m => m a -> m b -> m b
```

It is, in fact, just a special case of `(>>=)`:

```haskell
f >> g == f >>= \_ -> g
```

What does it do for `Maybe`? What does it do for the list monad?

The other method that `Monad` provides is this:

```haskell
return :: Monad m => a -> m a
```

The name is a bit misleading, as it is unrelated to `return` as a control statement in other programming languages. And maybe it should have been called `pure` instead…

The purpose of this function is to take something you already have, and then create the “computation” that produces this thing. In terms of recipes: Consider a cake with a label attached: “Recipe. Done”.

What is `return` for `Maybe`, and what for lists? (For `IO` the implementation is hidden, so it would not be fair to ask that.)

The point of having this is to write generic monad functions that you can use with `IO`, or with `Maybe` or with lists, or with one of the myriads of other types that have a `Monad` instance. Here is one example:

```haskell
mapM :: Monad m => (a -> m b) -> [a] -> m [b]
mapM f [] = return []
mapM f (a:as) = f a >>= \b -> mapM f as >>= \bs -> return (b:bs)
```

## The Monad laws

Two weeks ago, when we introduced type classes, I said that good type classes are those that come with laws, and `Monad` is one such type class. The following three are called the monad laws:

```haskell
return a >>= k          = k a
m >>= return            = m
m >>= (\x -> k x >>= h) = (m >>= k) >>= h
```

These effectively say that `return` really has no effect (whatever effect is supposed to mean), and that bind is associative, i.e. it does not matter which way these things nest.

## Do notation

Now that we have monads that allow us to write code that do `IO` (in addition to other fancy stuff, like nondeterministic calculations), the only thing missing is nice syntax. And we have that. Recall the `main` function above:

```haskell
main :: IO ()
main = putStrLn "Please enter a number: " >>
       readLn >>= \n ->
       let m = n + 1 in
       putStrLn (show m)
```

We can rewrite that using `do`-notation as follows:

```haskell
main' :: IO ()
main' = do putStrLn "Please enter a number: "
           n <- readLn
           let m = n + 1
           putStrLn (show m)
```

That looks almost like your good old imperative programming language, doesn’t it? No wonder there are people that say that Haskell is the best imperative programming language…

Keep in mind that this is purely syntactic sugar, and both functions are equivalent. Every line in a `do` block can be either

- A single expression `e`, which is then prepended to the rest with `e >> …`
- A monadic bind `p <- e`, which is replaced by `e >>= (\p -> …)`. On the left of the arrow you can not only use a single variable, but also a full pattern, for example to take a pair apart.
- A let binding `let p = e`, which is translated to `let e = p in …`.

Here, as another example, is the `zipTree` example, rewritten using `do`-notation and `return`:

```haskell
zipTree :: (a -> b -> c) -> Tree a -> Tree b -> Maybe (Tree c)
zipTree _ (Node _ _ _) Empty = Nothing
zipTree _ Empty (Node _ _ _) = Nothing
zipTree _ Empty Empty        = return Empty
zipTree f (Node l1 x r1) (Node l2 y r2) =
  do l <- zipTree f l1 l2
     r <- zipTree f r1 r2
     return (Node l (f x y) r)
```

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).