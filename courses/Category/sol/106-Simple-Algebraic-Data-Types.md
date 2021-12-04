# Category Theory for Programmers Challenges

## 6. Simple Algebraic Data Types

### 6.1. Show the isomorphism between Maybe a and Either () a.

```haskell
m2e :: Maybe a -> Either () a
m2e Nothing = Left ()
m2e (Just a) = Right a

e2m :: Either () a -> Maybe a
e2m (Left ()) = Nothing
e2m (Right a) = (Just a)
```

### 6.2. Here’s a sum type defined in Haskell:

```haskell
data Shape = Circle Float
           | Rect Float Float
```

> When we want to define a function like `area` that acts on a `Shape`, we do it by pattern matching on the two constructors:

```haskell
area :: Shape -> Float
area (Circle r) = pi * r * r
area (Rect d h) = d * h
```

> Implement `Shape` in C++ or Java as an interface and create two classes: `Circle` and `Rect`. Implement `area` as a virtual function.

I am going to implement this in Go.

```go
type Shape interface {
    isShape()
    area() float64
}

func newCircle(r float64) Shape {
    return &cirlce{r}
}
type circle struct {
    r float64
}
func (*circle) isShape() {}
func (c *circle) area() float64 {
    return math.Pi * c.r * c.r
}

func newRect(d, h float64) Shape {
    return &rect{d, h}
}
type rect struct {
    d float64
    h float64
}
func (*rect) isShape() {}
func (r *rect) area() float64 {
    return r.d * r.h
}
```

### 6.3. Continuing with the previous example: We can easily add a new function `circ` that calculates the circumference of a `Shape`. We can do it without touching the definition of `Shape`:

```haskell
circ :: Shape -> Float
circ (Circle r) = 2.0 * pi * r
circ (Rect d h) = 2.0 * (d + h)
```

> Add `circ` to your C++ or Java implementation. What parts of the original code did you have to touch?

In Go, we will need to update the interface and also add a method to each struct.

```go
type Shape interface {
    isShape()
    area() float64
    circ() float64
}

func (c *circle) circ() float64 {
    return 2.0 * math.Pi * c.r
}

func (r *rect) circ() float64 {
    return 2.0 * (r.d + r.h)
}
```

Luckily the extra methods can be added in a separate file in the same package.
So in that way it is a little bit more flexible than Java, but it is still far from perfect.

### 6.4. Continuing further: Add a new shape, `Square`, to `Shape` and make all the necessary updates. What code did you have to touch in Haskell vs. C++ or Java? (Even if you’re not a Haskell programmer, the modifications should be pretty obvious.)

In the Go implementation we could simply add a new implementation totally separately from the previous definitions.

```go
func newSquare(d, h float64) Shape {
    return &square{d, h}
}
type square struct {
    s float64
}
func (*square) isShape() {}
func (s *square) area() float64 {
    return s.s * s.s
}
func (s *square) circ() float64 {
    return 4.0 * s.s
}
```

In Haskell we would have to modify each function: `area` and `circ`, as well as the `Shape` data type definition.

### 6.5. Show that `a + a = 2 * a` holds for types (up to isomorphism). Remember that `2` corresponds to `Bool`, according to our translation table.

```haskell
s2p :: Either a a -> (Bool, a)
s2p (Left a) = (False, a)
s2p (Right a) = (True, a)

p2s :: (Bool, a) -> Either a a
p2s (False, a) = Left a
p2s (True, a) = Right a
```
