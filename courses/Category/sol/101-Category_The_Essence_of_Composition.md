# Category Theory for Programmers Challenges

## 1. Category: The Essence of Composition

### 1. Implement, as best as you can, the identity function in your favorite language (or the second favorite, if your favorite language happens to be Haskell).

This relies on the empty interface, so this will probably result in a type assertion later.

```go
func id(a interface{}) interface{} {
    return a
}
```

### 2. Implement the composition function in your favorite language. It takes two functions as arguments and returns a function that is their composition.

This uses reflect and the empty interface, so this will probably result in a type assertion later.

```go
func compose(f, g interface{}) interface{} {
    fv := reflect.ValueOf(f)
    gv := reflect.ValueOf(g)
    gins := make([]reflect.Type, gv.NumIn())
    for i := range gins {
        gins[i] = gv.Out(i)
    }
    fouts := make([]reflect.Type, gv.NumOut())
    for i := range fouts {
        fouts[i] = fv.Out(i)
    }
    htyp := reflect.FuncOf(gins, fouts, false)
    hv := reflect.MakeFunc(htyp, func(args []Value) []Value {
        return fv.Call(gv.Call(args))
    })
    return hv.Interface()
}
```

### 3. Write a program that tries to test that your composition function respects identity.

This simply tests one function, since generating random functions would be quite a bit of work.

```go
func TestCompose(t *testing.T) {
    f := func(a int) string {
        return strconv.Itoa(a)
    }
    fid := compose(f, id)
    idf := compose(id, f)
    for i := 0; i < 100; i++ {
        num := rand.Int()
        want := f(num)
        gotfid := fid(num).(string)
        gotidf := idf(num).(string)
        if gotfid != want || gotidf != want {
            t.Fatalf("f . id <%d> != want <%d> or id . f<%d> != want <%d>", gotfid, want, gotidf, want)
        }
    }
}
```

### 4. Is the world-wide web a category in any sense? Are links morphisms?

Yes.  If you assume links are composable.

### 5. Is Facebook a category, with people as objects and friendships as morphisms?

No.  My friend's friend is not necessarily my friend, which means morphisms are not composable.

### 6. When is a directed graph a category?

When each node has a self loop and if every link of the form: `a -> b -> c` implies there is also a link `a -> c`.

