# Category Theory for Programmers Challenges

## 8. Functoriality

### 8.1. Show that the data type:

> `data Pair a b = Pair a b`

> is a bifunctor. For additional credit implement all three methods of `Bifunctor` and use equational reasoning to show that these definitions are compatible with the default implementations whenever they can be applied.

Quoted from the text:

> If you have a mapping from a pair of categories to a third category, and you prove that it is functorial in each argument separately (i.e., keeping the other argument constant), then the mapping is automatically a bifunctor.

If we keep `a` constant then we get:

```haskell
instance Functor (Pair a) where
    fmap f (Pair a b) = Pair a (f b)
```

Now we need to check that it preserves identity `fmap id = id`:

```haskell
fmap id (Pair a b)
= -- definition of fmap
Pair a (id b)
= -- definition of id
Pair a b
= -- definition of id
id (Pair a b)
```

We also need to check that it preserves composition: `fmap (g . f) = fmap g . fmap f`:

```haskell
fmap (g . f) (Pair a b)
= -- definition of fmap
Pair a ((g . f) b)
= -- function application
Pair a (g (f b))
= -- definition of fmap
fmap g (Pair a (f b))
= -- definition of fmap
fmap g (fmap f (Pair a b))
= -- definition of composition
(fmap g . fmap f) (Pair a b)
```

We can do exactly the same type of proof by fixing `b`.

```haskell
instance Functor (`Pair` b) where
    fmap f (Pair a b) = Pair (f a) b
```

First we need to check that it preserves identity `fmap id = id`:

```haskell
fmap id (Pair a b)
= -- definition of fmap
Pair (id a) b
= -- definition of id
Pair a b
= -- definition of id
id (Pair a b)
```

We also need to check that it preserves composition: `fmap (g . f) = fmap g . fmap f`:

```haskell
fmap (g . f) (Pair a b)
= -- definition of fmap
Pair ((g . f) a) b 
= -- function application
Pair (g (f a)) b
= -- definition of fmap
fmap g (Pair (f a) b)
= -- definition of fmap
fmap g (fmap f (Pair a b))
= -- definition of composition
(fmap g . fmap f) (Pair a b)
```

Here is an implementation of all three methods of `Bifunctor` for `Pair`.

```haskell
instance Bifunctor Pair where
    bimap f g (Pair x y) = Pair (f x) (g y)
    first f (Pair x y) = Pair (f x) g
    second g (Pair x y) = Pair x (g y)
```

We can now use equational reasoning to show that these definitions are compatible with the default implementations:

```haskell
class Bifunctor f where
    bimap :: (a -> c) -> (b -> d) -> f a b -> f c d
    bimap g h = first g . second h
    first :: (a -> c) -> f a b -> f c b
    first g = bimap g id
    second :: (b -> d) -> f a b -> f a d
    second = bimap id
```

First bimap:
```haskell
bimap f g (Pair x y)
= -- apply bimap
(first f . second g) (Pair x y)
= -- apply first
((\(Pair x y) -> Pair (f x) y) . second g) (Pair x y)
= -- apply second
((\(Pair x y) -> Pair (f x) y) . (\(Pair x y) -> Pair x (g y))) (Pair x y)
= -- function application
(\(Pair x y) -> Pair (f x) y) Pair x (g y)
= -- function application
Pair (f x) (g y)
```

Next first:
```haskell
first f (Pair x y)
= -- apply first
bimap f id (Pair x y)
= -- apply bimap
Pair (f x) (id y)
= -- identity
Pair (f x) y
```

Lastly second:
```haskell
second g (Pair x y)
= -- apply second
bimap id g (Pair x y)
= -- apply bimap
Pair (id x) (g y)
= -- identity
Pair x (g y)
```


### 8.2. Show the isomorphism between the standard definition of `Maybe` and this desugaring:

> `type Maybe' a = Either (Const () a) (Identity a)`

> Hint: Define two mappings between the two implementations. For additional credit, show that they are the inverse of each other using equational reasoning.

The Const functor ignores its second type parameter and the first one is kept constant.

```haskell
data Const c a = Const c

fmap :: (a -> b) -> Const c a -> Const c b
fmap _ (Const v) = Const v
```

Lets define a mapping from `Maybe a` to `Maybe' a`:

```haskell
maybeToEither :: Maybe a -> Either (Const () a) (Identity a)
maybeToEither Nothing = Left $ Const ()
maybeToEither (Just j) = Right $ Identity j
```

And now lets define the reverse mapping from `Maybe' a` to `Maybe a`:

```haskell
eitherToMaybe :: Either (Const () a) (Identity a) -> Maybe a
eitherToMaybe (Left (Const ())) -> Nothing
eitherToMaybe (Right (Identity j)) -> Just j
```

If they were the inverse of each other then the following must be true:

```haskell
eitherToMaybe . maybeToEither = id
maybeToEither . eitherToMaybe = id
```

For each of these two equations, we have two possible inputs:

```
eitherToMaybe . maybeToEither = id -- input `Nothing`
eitherToMaybe . maybeToEither = id -- input `(Just j)`
maybeToEither . eitherToMaybe = id -- input `Left (Const ())`
maybeToEither . eitherToMaybe = id -- input `Right (Identity j)`
```

All of these can be proven with simple function application:

1. `eitherToMaybe . maybeToEither = id` input `Nothing`:

```haskell
eitherToMaybe . maybeToEither Nothing
= -- function application
eitherToMaybe (Left (Const ()))
= -- function application
Nothing
= -- identity
id Nothing
```

2. `eitherToMaybe . maybeToEither = id` input `(Just j)`:

```haskell
eitherToMaybe . maybeToEither (Just j)
= -- function application
eitherToMaybe (Right (Identity j))
= -- function application
Just j
= -- identity
id (Just j)
```

3. `maybeToEither . eitherToMaybe = id` input `Left (Const ())`:

```haskell
maybeToEither . eitherToMaybe (Const ())
= -- function application
eitherToMaybe Nothing
= -- function application
Const ()
= -- identity
id (Const ())
```

4. `maybeToEither . eitherToMaybe = id` input `Right (Identity j)`:

```haskell
maybeToEither . eitherToMaybe (Right (Identity j))
= -- function application
eitherToMaybe (Just j)
= -- function application
Right (Identity j)
= -- identity
id (Right (Identity j))
```

### 8.3. Let's try another data structure. I call it a `PreList` because it’s a precursor to a `List`. It replaces recursion with a type parameter `b`.

> `data PreList a b = Nil | Cons a b`

> You could recover our earlier definition of a `List` by recursively applying `PreList` to itself (we’ll see how it’s done when we talk about fixed points).

> Show that `PreList` is an instance of `Bifunctor`.

Showing `PreList` is an instance of `Bifunctor` involves checking whether:

  A. `PreList` is a functor when fixing `a` for:
    N.  `Nil`
      i. where a functor preserves identity and
      c. a functor preserves composition
    C. `Cons`
      i. where a functor preserves identity and
      c. a functor preserves composition
  B. `PreList` is a functor when fixing `b` for:
    N.  `Nil`
      i. where a functor preserves identity and
      c. a functor preserves composition
    C. `Cons`
      i. where a functor preserves identity and
      c. a functor preserves composition

A. First, if we keep `a` constant then we get:

```haskell
instance (Functor b) => Functor (PreList a) where
    fmap f Nil = Nil
    fmap f (Cons a b) = Cons a (fmap f b)
```

A.N `Nil`

A.N.i `Nil` Preserves identity `fmap id = id`

```haskell
fmap id Nil
= -- definition of fmap
Nil
= -- identity
= id Nil
```

A.N.c `Nil` Preserves composition `fmap (g . f) = fmap g . fmap f`

```haskell
fmap (g . f) Nil
= -- definition of fmap
Nil
= -- definition of fmap
fmap g Nil
= -- definition of fmap
fmap g (fmap f Nil)
= fmap g . fmap f
```

A.C `Cons a`

A.C.i `Cons a` preserves identity `fmap id = id`

```haskell
fmap id (Cons a b)
= -- definition of fmap
Cons a (fmap id b)
= -- b implements fmap
Cons a (id b)
= -- identity
Cons a b
= -- identity
id (Cons a b)
```

A.C.c `Cons a` preserves composition `fmap (g . f) = fmap g . fmap f`

```haskell
fmap (g . f) (Cons a b)
= -- definition of fmap
Cons a (fmap (g . f) b)
= -- b implements fmap
Cons a ((fmap g . fmap f) b)
= Cons a (fmap g (fmap f (b)))
= -- definition of fmap
fmap g (Cons a (fmap f b))
= -- definition of fmap
fmap g (fmap f (Cons a b))
= fmap g . fmap f (Cons a b)
```

B. Now we keep `b` constant:

```haskell
instance (Functor a) => Functor (`PreList` b) where
    fmap f Nil = Nil
    fmap f (Cons a b) = Cons (fmap f a) b
```

B.N

  - B.N.i = A.N.i
  - B.N.c = A.N.c

B.C.i `Cons b` preserves identity `fmap id = id`

```haskell
fmap id (Cons a b)
= -- definition of fmap
Cons (fmap id a) b 
= -- a implements fmap
Cons (id a) b
= -- identity
Cons a b
= -- identity
id (Cons a b)
```

B.C.c `Cons b` preserves composition `fmap (g . f) = fmap g . fmap f`

```haskell
fmap (g . f) (Cons a b)
= -- definition of fmap
Cons (fmap (g . f) a) b 
= -- b implements fmap
Cons ((fmap g . fmap f) a) b
= Cons (fmap g (fmap f (a))) b 
= -- definition of fmap
fmap g (Cons (fmap f a) b)
= -- definition of fmap
fmap g (fmap f (Cons a b))
= fmap g . fmap f (Cons a b)
```

### 8.4. Show that the following data types define bifunctors in `a` and `b`:

> `data K2 c a b = K2 c`

```haskell
instance Bifunctor (K2 c) where
    bimap _ _ (K2 c) = K2 c
```

> `data Fst a b = Fst a`

```haskell
instance Bifunctor Fst where
    bimap f _ (Fst a) = Fst (f a)
```

> `data Snd a b = Snd b`

```haskell
instance Bifunctor Snd where
    bimap _ g (Snd b) = Snd (g b)
```

> For additional credit, check your solutions agains Conor McBride’s paper [Clowns to the Left of me, Jokers to the Right](http://strictlypositive.org/CJ.pdf).

### 8.5. Define a bifunctor in a language other than Haskell. Implement `bimap` for a generic pair in that language.

Go does not have generics, so the only options are reflection and code generation.
This time I chose reflection:

```go
package main

import (
	"fmt"
	"reflect"
)

func bimap(f, g, tuple interface{}) interface{} {
	fv, gv, tuplev := reflect.ValueOf(f), reflect.ValueOf(g), reflect.ValueOf(tuple)
	out := tuplev.Call(nil)
	fst, snd := out[0], out[1]
	fout := fv.Call([]reflect.Value{fst})
	gout := gv.Call([]reflect.Value{snd})
	typeof := reflect.TypeOf(tuple)
	return reflect.MakeFunc(typeof, func([]reflect.Value) []reflect.Value {
		return []reflect.Value{fout[0], gout[0]}
	}).Interface()
}

func not(b bool) bool { return !b }

func inc(i int) int { return i+1 }

func main() {
    // the only way to represent a tuple in go is as multiple return parameters or as a struct.  
    // This time I opted for a function with multiple return paramaters.
    tuple := func() (int, bool) { return 1, false }
	b := bimap(inc, not, tuple)
	two, yes := b.(func() (int, bool))()
	fmt.Printf("%d %v\n", two, yes)
}
```

[Run it here](https://play.golang.org/p/De_EAxve-o-)

### 8.6. Should `std::map` be considered a bifunctor or a profunctor in the two template arguments `Key` and `T`? How would you redesign this data type to make it so?

I assume `std::map` refers to a hashmap in C++.
I am going to tackle this question as if it was posed about Go's `map`, which is also a hashmap.

There are several reasonable ways to implement a `map`, which can be related to a function:

  1. one key can have multiple return values: `Key -> [Type]`
  2. one key can refer to one or zero values: `Key -> Maybe Type`
  
Go decided to go another route: 

3. `Key -> (Bool, Type)`, where it is either `(False, zero)` or `(True, Type)`.

`zero` returns a different default value for each type:

```haskell
zero :: String
zero = ""

zero :: Int
zero = 0

zero :: Bool
zero = False

zero :: Maybe T
zero = Nil
```

By convention (so not checked by the compiler) the programmer should check the `Bool` to know whether the value was present.

4. `Key -> Type`.

The programmer can also skip that and simply use the default value if the value was not present, which then results our forth possible implementation.

Now that we have four possible interpretations of map, I suspect they are all Profunctors:

```haskell
class Profunctor p where
  dimap :: (a -> b) -> (c -> d) -> p b c -> p a d
  dimap f g = lmap f . rmap g
  lmap :: (a -> b) -> p b c -> p a c
  lmap f = dimap f id
  rmap :: (b -> c) -> p a b -> p a c
  rmap = dimap id
```

The easiest is number 4 `Key -> Type`, which is just a function and that has been shown to be a Profunctor.

```haskell
Get a b :: a -> b

instance Profunctor Get where
  dimap keyf valuef get = valuef . get . keyf
  lmap = flip (.)
  rmap = (.)
```

Next lets try number 2 `Key -> Maybe Type`:

```haskell
GetMaybe a b :: a -> Maybe b

instance Profunctor GetMaybe where
  dimap keyf valuef get = lmap f . rmap g
  lmap keyf get = \k -> get (keyf k)
  rmap valuef get = \k -> fmap valuef (get k)
```

Number 1 is the same as number 2 and we can generalize it:

```haskell
GetF f a b :: a -> f b

instance (Functor f) => Profunctor (GetF f) where
  dimap keyf valuef get = lmap f . rmap g
  lmap keyf get = \k -> get (keyf k)
  rmap valuef get = \k -> fmap valuef (get k)
```

Finally we have number 3, the Go map `Key -> (Bool, Type)`.
We could implement this in two ways:

1. As a Maybe

```haskell
GetGo a b = a -> (Bool, b)

instance Profunctor GetGo where
  dimap keyf valuef get = lmap f . rmap g
  lmap keyf get = \k -> get (keyf k)
  rmap valuef get = \k -> let (ok, v) = get k
    in if ok 
        then (True, valuef v)
        else (False, v)
```

2. Or as a Tuple

```haskell
GetGo a b = a -> (Bool, b)

instance Profunctor GetGo where
  dimap keyf valuef get = lmap f . rmap g
  lmap keyf get = \k -> get (keyf k)
  rmap valuef get = \k -> let (ok, v) = get k
    in if ok 
        then (True, valuef v)
        else (False, valuef v)
```