# Category Theory for Programmers Challenges

## 10. Natural Transformations 

### 10.1. Define a natural transformation from the Maybe functor to the list functor. Prove the naturality condition for it.

A natural transformation from the Maybe functor to the list functor:

```haskell
alpha :: Maybe a -> [a]
alpha Nothing -> []
alpha (Just x) -> [x]
```

Here follows the prove for the naturality condition, using equational reasoning:

```haskell
fmapList f . alpha = alpha . fmapMaybe f
```

For the Nothing case we have:

```haskell
fmapList f (alpha Nothing) = fmapList f [] = []
alpha (fmapMaybe f Nothing) = alpha Nothing = []
```

For the Just case we have:

```haskell
fmapList f (alpha (Just x)) = fmapList f [x] = [f x]
alpha (fmapMaybe f (Just x)) = alpha (Just (f x)) = [f x]
```

In all cases both sides are equal.

### 10.2. Define at least two different natural transformations between Reader () and the list functor. How many different lists of () are there?

> Copied from the text

```haskell
newtype Reader e a = Reader (e -> a)
instance Functor (Reader e) where  
    fmap f (Reader g) = Reader (\x -> f (g x))
```

We can mirror the two natural transformations from the Reader () to the Maybe functor:

```haskell
dumb :: Reader () a -> [a]
dumb (Reader _) = []
```

Proof for dumb:

```haskell
fmapList f . dumb = dumb . fmapReader f
fmapList f (dumb (Reader g)) = dumb (fmapReader f (Reader g))
fmapList f [] = dumb (Reader (\x -> f (g x)))
[] = []
```

```
obvious :: Reader () a -> [a]
obvious (Reader g) = [g ()] 
```

Proof for obvious:

```haskell
fmapList f . obvious = obvious . fmapReader f
fmapList f (obvious (Reader g)) = obvious (fmapReader f (Reader g))
fmapList f [g ()] = obvious (Reader (\() -> f (g ())))
[f (g ())] = [f (g ())]
```

And then we can come up with more:

```
double :: Reader () a -> [a]
double (Reader g) = [g (), g ()] 
```

with a proof:

```haskell
fmapList f . double = double . fmapReader f
fmapList f (double (Reader g)) = double (fmapReader f (Reader g))
fmapList f [g (), g ()] = double (Reader (\() -> f (g ())))
[f (g ()), f (g ())] = [f (g ()), f (g ())]
```

What about an infinite list:

```
more :: Reader () a -> [a]
more (Reader g) = g () : more (Reader g) 
```

with a proof:

```haskell
fmapList f . more = more . fmapReader f
fmapList f (more (Reader g)) = more (fmapReader f (Reader g))
fmapList f (g (): more (Reader g)) = more (Reader (f . g))
f (g ()) : fmapList f (more (Reader g)) = f (g ()) : more (Reader (f . g))
f (g ()) : fmapList f (more (Reader g)) = f (g ()) : more (fmapReader f (Reader g))
f (g ()) : fmapList f . more = f (g ()) : more . fmapReader f
```

Both sides generate an infinite list of `f (g ())`

This means there are an infinite amount of natural transformations between the Reader () and list functors.

### 10.3. Continue the previous exercise with Reader Bool and Maybe.

Only three natural transformations are possible:

```haskell
none :: Reader Bool a -> Maybe a
none (Reader g) = Nothing

true :: Reader Bool a -> Maybe a
true (Reader g) = Just (g True)

false :: Reader Bool a -> Maybe a
false (Reader g) = Just (g False)
```

### 10.4. Show that horizontal composition of natural transformation satisfies the naturality condition (hint: use components). It’s a good exercise in diagram chasing.

**TODO**

### 10.5. Write a short essay about how you may enjoy writing down the evident diagrams needed to prove the interchange law.

**TODO**

### 10.6. Create a few test cases for the opposite naturality condition of transformations between different Op functors. 

> Here’s one choice: 

```haskell
op :: Op Bool Int
op = Op (\x -> x > 0)
```

> and

```haskell
f :: String -> Int
f x = read x
```

**TODO**