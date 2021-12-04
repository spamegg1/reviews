# Category Theory for Programmers Challenges

## 7. Functors

### 7.1. Can we turn the Maybe type constructor into a functor by defining:

```haskell
fmap _ _ = Nothing
```

> which ignores both of its arguments? (Hint: Check the functor laws.)

The functor laws are that the constructor for the functor with `fmap`:

1. preserves identity: `fmap id = id`
2. preserves composition: `fmap (g . f) = fmap g . fmap f`

We can use equational reasoning (substitution) to prove these laws hold.

Lets try to preserve identity by looking at both cases for Maybe.

The case for Nothing holds:

```haskell
fmap id Nothing 
= -- definition of fmap
Nothing
= -- definition of id
id Nothing
```

The case for Just does not hold:
```haskell
fmap id (Just j)
= -- definition of fmap
Nothing
= -- definition of id
id Nothing
-- expected id (Just j)
```

This means that we cannot turn the Maybe type constructor into a functor with the challenge's definition of `fmap`.

### 7.2. Prove functor laws for the reader functor. Hint: it’s really simple.

The implementation of fmap for the reader functor

```haskell
fmap :: (a -> b) -> (r -> a) -> (r -> b)

instance Functor ((->) r) where
    fmap f g = f . g
    fmap f g = (.) f g
    fmap = (.)
```

The reader functor preserves identity:

```haskell
fmap id g
= -- definition of fmap
id . g
= -- definition of id
g
= -- definition of id
id g
```

The reader functor preserves composition:

```haskell
fmap (g . f) h
= -- definition of fmap
(g . f) . h
= -- associativity
g . (f . h)
= -- definition of fmap
g . (fmap f h)
= -- definition of fmap
fmap g (fmap f h)
= -- definition of composition
(fmap g . fmap f) h
```

### 7.3. Implement the reader functor in your second favorite language (the first being Haskell, of course).

It should be simple to add another fmap function to [goderive](https://github.com/awalterschulze/goderive) and so add the reader functor to Go.

Currently there exists deriveFmap for:
```go
deriveFmap(func(A) B, func() (A, error)) (B, error)
```

We would need the following derivation to be added:
```go
func deriveFmap(f func(A) B, g func(R) A) func(R) B {
    return func(r R) B {
        return f(g(r))
    }
}
```

### 7.4. Prove the functor laws for the list functor. Assume that the laws are true for the tail part of the list you’re applying it to (in other words, use induction).

The functor implementation for list is:

```haskell
instance Functor List where
    fmap _ Nil = Nil
    fmap f (Cons x t) = Cons (f x) (fmap f t)
```

The list functor preserves identity for Nil:
```haskell
fmap id Nil
= -- definition of fmap
Nil
= -- definition of id
id Nil
```

The list functor preserves identity for Cons:
```haskell
fmap id (Cons x t)
= -- definition of fmap
Cons (id x) (fmap id t)
= -- induction
Cons (id x) t
= -- definition of id
Cons x t
= -- definition of id
id (Cons x t)
```

This implies that the list functor preserves identity for both its cases Nil and Cons.

The list functor preserves composition for Nil:
```haskell
fmap (g . f) Nil
= -- definition of fmap
Nil
= -- definition of fmap
fmap g Nil
= -- definition of fmap
fmap g (fmap f Nil)
= -- definition of composition
(fmap g . fmap f) h
```

The list functor preserves composition for Cons:
```haskell
fmap (g . f) (Cons x t)
= -- definition of fmap
Cons ((g . f) x) (fmap (g . f) t)
= -- induction
Cons ((g . f) x) (fmap g (fmap f t))
= -- definition of composition
Cons (g (f x)) (fmap g (fmap f t))
= -- definition of fmap
fmap g (Cons (f x) (fmap f t))
= -- definition of fmap
fmap g (fmap f (Cons x t))
= -- definition of composition
(fmap g . fmap f) (Cons x t)
```

This implies that the list functor preserves composition for both its cases Nil and Cons.

This implies that the list functor obeys the functor laws.
