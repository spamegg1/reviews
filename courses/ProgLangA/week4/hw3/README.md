## Notes on the Challenge Problem

Contributed by Edwin Dalorzo

Let's imagine that we have a case expression with different patterns, like

```haskell
  case x of p1 | p2 | ... | pn
```

Where `x` is of certain type `t` that we could infer out of the patterns `p1`, `p2`, `...`, `pn`.

In summary, the objective of the challenge exercise is to create an algorithm that (like the SML compiler), is capable of inferring the type `t` of `x` based on the patterns `p1, p2, ..., pn`.

These patterns are provided as the second argument of the challenge exercise function and they represent every one of the branches in a case expression.

If all the patterns in the case expression are compatible with some type t then the answer is `SOME` t, otherwise `NONE`.

We would not need the first argument of the challenge exercise except constructor patterns do not "tell us" what type they are. For instance, consider a case expression like:

```haskell
case c of Red => ... | Green => ... | _ => ...
```

We cannot tell what is the type of `Red` or `Green` here. Likewise, in the challenge exercise if we found a constructor like:

```haskell
Constructor("Red",UnitP)
```

How could we possibly infer the type of this constructor unless we had some additional information?  And so this explains why we need a first argument of the challenge function containing a type list. It is nothing but our definition of datatypes. 

```haskell
datatype color = Red | Green | Blue
```

  Would become somewhat like:

```haskell
[ (  "Red", "color", UnitT),
  ("Green", "color", UnitT),
  ( "Blue", "color", UnitT) ]
```

Now let's consider several examples:

**Example 1**

Suppose we had this function:

```haskell
fun b(x) = 
   case x of
       (10) => 1
      | a => 3
```

The compiler would determine that `x` has type `int`. How? Easy: one of the patterns is a integer constant. Thus, the other pattern named `a` must be an integer as well. And there you have it, we just inferred the type of `x`.

In our challenge exercise, this pattern would be expressed as

```haskell
[ConstP 10, Variable "a"]
```

And our algorithm should say that the answer is `SOME` `IntT` which corresponds with the type the compiler would infer.

**Example 2:**

A piece of code like the following would not even compile, because we cannot infer a common type for all patterns. The types in the different patterns are conflicting. We cannot tell if `x` is an int or an option.

```haskell
fun b(x) = 
   case x of
      (10) => 1
      | SOME x => 3
      | a => 3
```

Thus, consider the following pattern, corresponding with the code above:

```haskell
[ConstP 10, Variable "a", ConstructorP("SOME",Variable "x")]
```

This cannot produce a common type and the answer our algorithm yields should be `NONE`, equivalent with the compiler throwing an error due to incapacity to determine a common type.

**Example 3:**

Let's do a more complicated example now:

```haskell
fun c(x) = 
    case x of
        (a,10,_) => 1
      | (b,_,11) => 2
      | _ => 3
```

What is the type of `x`?

Well, we can easily infer it's a tuple of three elements. Based on the patterns, we know the second and third elements of this tuple are integers. The first one, on the other hand, can be "anything".

This would correspond with:

```haskell
[TupleP[Variable "a", ConstP 10, Wildcard], TupleP[Variable "b", Wildcard, ConstP 11], Wildcard]
```

And the answer given by our algorithm should be: `SOME TupleT[Anything,IntT,IntT]`.

**Example 4:**

Let's use a datatype now.

```haskell
datatype color = Red | Green | Blue
```

Then we need to define the first argument of our challenge function as:

```haskell
[("Red","color",UnitT),("Green","color",UnitT),("Blue","color",UnitT)]
```

Let's say now that we have a function like this:

```haskell
fun f(x) = 
   case x of
     Red => 0
     | _ => 1
```

Corresponding with something like:

```haskell
[ConstructorP("Red", UnitP), Wildcard]
```

Our algorithm should go over the patterns and say this is of type:

```haskell
SOME (Datatype "color")
```

**Example 5:**

Let's use now a more complex datatype

```haskell
datatype auto =  Sedan of color
               | Truck of int * color
               | SUV
```

This would correspond to a first argument as follows:

```haskell
[("Sedan","auto", Datatype "color"),("Truck","auto",TupleT[IntT, Datatype "color"]),("SUV","auto",UnitT)]
```

Let's say now that we had a function like this:

```haskell
fun g(x) = 
   case x of
        Sedan(a) => 1
      | Truck(b,_) => 2
      | _ => 3
```

What is the type of `x`? Well, we can easily infer they are all of type auto.

So, the following argument:

```haskell
[ConstructorP("Sedan", Variable "a"), ConstructorP("Truck", TupleP[Variable "b", Wildcard]), Wildcard]
```

Should yield `SOME (Datatype "auto")`.

**Example 6:**

Let's now define a polymorphic type to make this interesting

```haskell
datatype 'a list = Empty | List of 'a * 'a list
```

So, we must first define our first argument:

```haskell
[("Empty","list",UnitT),("List","list",TupleT[Anything, Datatype "list"])]
```

The trick is to notice that the polymorphic type `'a` corresponds to anything here, and so the type inference becomes a bit trickier later on.

Now if we had this function

```haskell
fun j(x) = 
   case x of
       Empty => 0
     | List(10,Empty) => 1 
     | _ => 3
```

Evidently the patterns are of type list, but not just that, but a list of integers.

So, the following argument corresponding to the patterns in the function:

```haskell
[ConstructorP("Empty",UnitP),ConstructorP("List",TupleP[ConstP 10, ConstructorP("Empty",UnitP)]), Wildcard]
```

Should yield: `SOME (Datatype "list")`.

This case is tricky, because `ConstP(10)` needs to correspond with `Anything` in the constructors metadata as you can see above.

**Example 7:**

Let's consider this variation of the previous case:

```haskell
fun h(x) = 
   case x of
      Empty => 0
    | List(k,_) => 1
```

In this case `k` could be anything. So, we represent these branches as:

```haskell
[ConstructorP("Empty",UnitP),ConstructorP("List",TupleP[Variable "k", Wildcard])]
```

And the answer should be `Datatype "List"`.

And once more, notice how `Variable "k"` needs to correspond with `Anything` in the datatype definition.

So, in the previous example `ConstP(10)` and now `Variable "x"` can be considered "compatible with" `Anything`.

**Example 8:**

Just another example

```haskell
fun g(x) = 
   case x of
      Empty => 0
    | List(Sedan(c),_) => 1
```

Corresponding with:

```haskell
[ConstructorP("Empty",UnitP),ConstructorP("List",TupleP[ConstructorP("Sedan", Variable "c"), Wildcard])]
```

Should evidently yield `SOME (Datatype "list")`.

**Example 9:**

Now for the "most lenient" pattern. In the assignment we get two examples.

The first one suggest that we have two patterns of the form:

```haskell
TupleP[Variable "x", Variable "y"] 
TupleP[Wildcard, Wildcard]
```

This would correspond to something like

```haskell
fun m(w) = 
    case w of
          (x,y) => 0
        | (_,_) => 1
```

Interestingly this would not compile, since the patterns are redundant, namely, we would always go out through the first branch. But this was simply used with illustration purposes.

We can infer that `w `is a tuple with two elements that can be of anything. So the answer to this type of patterns should be:

```haskell
TupleT[Anything, Anything]
```

What is meant by "most lenient" is that the type `TupleT[IntT, IntT]` (for example) is also a fine type for all the patterns, but it is not as "lenient" (does not match as many values as) `TupleT[Anything,Anything]` so `TupleT[IntT, IntT]` is not correct.

**Example 10:**

The second example of the "most lenient" requirement is similar but a little more interesting.

The second example suggest a list of patterns like this:

```haskell
TupleP[Wildcard, Wildcard]
TupleP[Wildcard, TupleP[Wildcard,Wildcard]]
```

Which would correspond with

```haskell
fun m(w) = 
    case w of
      (_,(_,_)) => 0
    | (_,_) => 1
```

We can infer that w is a tuple of two elements, the first one can be anything, the second one is evidently a tuple of other two elements, which in turn can be anything.

So, if we had to infer this we had to say the type of this is

```haskell
TupleT[Anything, TupleT[Anything, Anything]]
```

Which is the expected answer by the challenge exercise. But yet again, the compiler would not handle this type of expression without errors.