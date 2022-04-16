[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Functor and Applicative

In your homework you were implementing the `Supply` monad, and while you did implement the necessary functionality, I provided you with the necessary boilerplate code, which was:

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

We have already discussed the `Monad` type class; today I will introduce the other two.

# Functor

Remember that type classes are used when you find that a common functionality is repeated at different types. The functionality captured by the `Functor` type class is that of “applying a pure function under a type constructor”.

## Type constructors

I believe this is the first time I mention the word type constructor, so let me digress for a moment. Consider this list of types:

- `Bool`
- `Integer`
- `Maybe`
- `Stream`
- `Supply`

It is obvious what a `Bool` or what an `Integer` is: it describes some value at runtime, such as `True` or `42`. The same cannot be said about `Maybe`, or `Stream`. There are no values of type `Maybe`, that simply does not make sense. The type `Maybe` is not a proper type of its own, but rather it is a *type constructor*, in that if you *apply* `Maybe` to another type – such as `Bool`, then you get a proper type `Maybe Bool` that has values, such as `Just True` and `Nothing`, that you can inspect and pass around and so on.

The type constructor `Supply` is even worse: `Supply` is not a proper type, but `Supply Bool` is *still* not a proper type. You need to apply `Supply` to two proper types to get something that has values. So `Supply` is a type constructor, but of a different kind than `Maybe`.

## Kinds

Does `Maybe Stream` make sense? It does not: `Maybe` takes a proper type (like `Bool`) and turns it into a new proper type `Maybe Bool`. But since `Stream` is not a proper type itself, `Maybe Stream` does not make sense.

So we have basic types and type constructors, and not all combinations make sense. This is just like with functions and values! And indeed, types and type constructors have their own, separate, type system that ensures that only sensible combinations are used. To keep things separate, the type of a type is called its *kind*, so we have a kind system.

The basic kind is called *star* and written `*` (in future versions of Haskell, this will be called `Type`). `Bool` and `Integer` have kind `*`.

Type constructors such as `Maybe` and `Stream` have kind `* -> *`: They are functions (on the type level) that map types (of kind star) to types (of kind star).

And our `Supply` type constructor has kind `* -> * -> *`. From this kind signature you can read that you have to apply it to two types of kind star to get a type of kind star.

Another type constructor of that kind is the arrow itself:

```haskell
Prelude> :kind (->)
(->) :: * -> * -> *
```

Some more complicated kind signatures, such as `(* -> *) -> *` exist, although we did not yet see an example for that. What can you do with such a type constructor?

You can ask GHCi for the kind of a type or type constructor:

```haskell
Prelude> :kind Bool
Bool :: *
Prelude> :kind Maybe
Maybe :: * -> *
```

You can also ask for the kind of a type class:

```haskell
Prelude> :kind Eq
Eq :: * -> GHC.Prim.Constraint
Prelude> :kind Monad
Monad :: (* -> *) -> GHC.Prim.Constraint
```

This tells us that the `Eq` type class has one parameter, which has to be a type of kind star, while `Monad` is a type class whose instances are type constructors of kind `* -> *`. This makes sense, because in the type signatures of the methods of the Monad type class the parameter of the type class `m` is applied to a type. We can therefore not have an instance `Monad Bool`, we can have `Monad Maybe`, and we cannot have `Monad Supply`. But did we not have that in the homework? No! We defined a `Monad` instance for `Supply s` (for any type s). If you partially apply the type constructor `Supply` to one argument, you get something of kind `* -> *`.

All you learned about types on the term level applies here as well.

## Back to Functor

If I now say that the functionality captured by the `Functor` type class is that of “applying a pure function under a type constructor”, that hopefully means more to you.

We have seen the pattern before twice:

```haskell
mapList :: (a -> b) -> List a -> List b
mapSupply :: (a -> b) -> Supply s a -> Supply s b
```

and I’m sure you would not doubt the existence and usefulness of a functions

```haskell
mapMaybe :: (a -> b) -> Maybe a -> Maybe b
mapTree :: (a -> b) -> Tree a -> Tree b -- the tree from last homework
```

The common pattern that emerges here is

```haskell
class Functor f where
  fmap :: (a -> b) -> f a -> f b
```

Like any good type class, this comes with a few laws:

```haskell
fmap id  ==  id
fmap (f . g)  ==  fmap f . fmap g
```

Remember that `f . g` is the function composition of `f` and `g`.

One implication of these laws is that if `f` is something with an effect of some sort (like `IO`), then applying a function via `fmap` does not *not* change the effect.

## Examples

Functor instances are usually straight-forward to implement. Here are examples:

```haskell
data Tree a
    = Node [Tree a] -- a different tree!
    | Leaf a

instance Functor Tree where
    fmap f (Node ts) = Node (map (fmap f) ts)
    fmap f (Leaf x)  = Leaf (f x)

data GenTree f a -- kind (* -> *) -> * -> *
    = Node (f (GenTree f a))
    | Leaf a

instance Functor f => Functor (GenTree f) where
    fmap f (Node ts) = Node (fmap (fmap f) ts)
    fmap f (Leaf x)  = Leaf (f x)

data Proxy a = Proxy
instance Functor Proxy where
    fmap _ Proxy = Proxy
```

Not all type constructors can be made instances of `Functor`. Here are two examples:

```haskell
data Powerset a = Powerset (a -> Bool)
data Enum a     = Enum (a -> Integer) (Integer -> a)
```

But the vast majority of data types can be nice functions, and it is really useful.

By the way: There is the operator `(<$>)` that can be used instead of `fmap` in case infix is nicer. This makes the correspondence between `f x` (apply `f` to the pure value `x`) and `f <$> x` (apply `f` to the result of the computation `x`) clearer.

# Applicative

The other type class that we instantiated in the homework is `Applicative`, which is a generalization of `Functor`. There are two ways of motivating the primitive `Applicative` combinator:

## Deriving `<*>`

- Compare the types of pure function application, conveniently expressed using `$`, and `fmap`:

  ```haskell
  ($)   ::                    (a -> b) ->   a ->   b
  (<$>) :: Functor f =>       (a -> b) -> f a -> f b
  ```

  We see that the argument is now hidden behind a type constructor. We reach the primitive combinator of `Applicative` if we do the same to the function:

  ```haskell
  (<*>) :: Applicative f => f (a -> b) -> f a -> f b
  ```

  This is a real generalization, as you cannot implement that function with just a `Functor` instance.

  If you think of `f` describing some kind of effect (IO, failure, nondeterminism etc.), then this means that not only the argument in the (generalized) function application is obtained in an effectful way, but also the function.

- Looking again at the type of `fmap`, we see that it applies an unary function to a now effectfully captured argument:

  ```haskell
  fmap ::   Functor f =>     (a -> r)      -> f a        -> f r
  ```

  We can try to generalize that to binary functions:

  ```haskell
  liftA2 :: Applicative f => (a -> b -> r) -> f a -> f b -> f r
  ```

  (The function `liftA2` is not part of the `Prelude`, but you get it if you `import Control.Applicative`.)

  Again we find that just using what is provided by `Functor`, we cannot implement this combinator, so this is a real generalization.

Both views are equivalent. If we have `<*>` and `<$>` from `Functor`, we can get `liftA2`:

```haskell
liftA2 f x y = f <$> x <*> y
```

We can also do the other direction:

```haskell
f <*> x = liftA2 id f x
```

What if we generalize `liftA2` again to ternary function? Do we get yet another concept? No, we do not! The applicative interface is enough to implement

```haskell
liftA3 :: Applicative f => (a -> b -> c -> r) -> f a -> f b -> f c -> f r
liftA3 f x y z =  f <$> x <*> y <*> z
```

and in fact, `liftA2` etc. are rarely used and chains of `<$>` and `<*>` are used instead in idiomatic code using some applicative-based interface.

## Return of the purity

In order to instantiate the `Applicative` type class, besides giving a definition for `<*>`, we have to give a definition for

```haskell
pure :: Applicative f => a -> f a
```

which injects a pure value into whatever `f` represents. This is exactly the same as the `return` that we discussed two weeks ago, so I will not dwell on it.

## Laws

The Applicative type class has laws:

```haskell
pure id <*> v = v                            -- identity
pure (.) <*> u <*> v <*> w = u <*> (v <*> w) -- composition
pure f <*> pure x = pure (f x)               -- homomorphism
u <*> pure y = pure ($ y) <*> u              -- interchange
```

The main intuition here is that that `<*>` is associative (due to the composition law) and that `pure` really is pure, and thus can be pushed around.

## Example instances

Some types have only one sensible instance for `Applicative`. For example

```haskell
instance Applicative Maybe where
    pure = Just
    Just f <*> Just x = Just (f x)
    _ <*> _ = Nothing
```

For lists there are two possible instances that come to mind. The first one corresponds to the notion of nondeterministic evaluation that we already know form its `Monad` instance, where we apply every function on the left to every argument on the right:

```haskell
instance Applicative [] where
    pure x = [x]
    []     <*> xs = []
    (f:fs) <*> xs = map f xs ++ fs <*> x
```

The other one involved zipping lists. Note the peculiar choice of `pure`: it has to be that one for the identity law to hold:

```haskell
instance Applicative [] where
    pure x = repeat x
    (<*>) = zipWith ($)
```

The Haskell standard library provides the former instance. If you want the latter, you have to use the `ZipList` wrapper provided in `Control.Applicative`.

# Monad ⊂ Applicative ⊂ Functor

Applicative lies in between `Functor` and `Monad` in the sense that when you have a `Monad` instance, you also have an `Applicative` instance, and when you have an `Applicative` instance, you have a `Functor` instance.

To see this, we have to define two `Applicative` methods using the `Monad` combinators, either directly, or using `do`-notation (which might be more educating). `pure` is boring, as that is just `return`. Here is `<*>`:

```haskell
f <*> x = f >>= (\f' -> x >>= (\x' -> return (f' x')))
-- or
f <*> x = do
    f' <- f
    x' <- x
    return (f' x')
```

And now we can define `fmap` in terms of the `Applicative` combinators:

```haskell
fmap f x = pure f <*> x
```

Using these definitions we could prove the `Applicative` laws from the `Monad` laws, and similarly the `Functor` laws from the `Applicative` laws, but we do not do that now.

It is also expected, i.e. part of the laws, that these equalities hold for any type constructor that has instances of `Functor`, `Applicative` and `Monad`, although they are free to have possibly more efficient implementations.

So every `Monad` is an `Applicative`. Are there `Applicative` instances that do not have a corresponding `Monad` instance? Yes there are.

- Here is one example:

  ```haskell
  data ConstString a = ConstString String -- special case of data Const a b = Const a
  instance Applicative ConstString where
      pure x = ConstString ""
      ConstString s1 <*> ConstString s2 = ConstString (s1 ++ s2)
  ```

  The monad laws require that

  ```haskell
  return x >>= f = f x
  ```

  holds, in particular for `x = "Hello"` and `f = ConstString`. But since `return = pure` and `pure` throws away its argument, it is easy to see that no definition of `(>>=)` can do that.

- Another example is `ZipList`. Unfortunately, there is no simple argument why that is the case, but if you want you can find many posts online of people saying they found `Monad` instances, only to be told in what way their `Monad` instance does not work.

# Why all this?

So what is the point of having `Applicative` if `Monad` is stronger, and you can do more with it? Because these are *interfaces*, e.g. between a library implementation and the library-using code, one side’s power is the other side’s constraint.

If you look at the type signature of monadic bind it is clear that sequencing is forced there: Until the first computation has not yielded a result, the second cannot start. This is different for `<*>` or `liftM2`: If the effect would be “read something from the network”, then it is now clear that the implementation could choose to parallelize these requests and start with the second even before the first one ended. The power to do this optimization relies on the restraint that you put on the user of the interface. Facebook is using that with good results in their `HaXL` library.

We will look in depth at an example for that power next week, and the homework sets the stage for that.

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).