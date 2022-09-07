# Notes and Tips for Section 5

Acknowledgment: This concise list was contributed by Charilaos Skiadas. 

**Notes on material:**

- Always use `#lang racket` at the top of the file. 
- Need `(provide (all-defined-out))` near the top.  
- Comments start with a semicolon, go to end of line. 
- Many things have the form `(a b c ...)` where first term is operator, the rest are the arguments. 
- Use `define` to define new variables.  
- `lambda` similar to ML's `fn`.  
- Some arithmetic operations can take any number of arguments. 
- Two ways to define functions: `(define f (lambda (x) body))` or `(define (f x) body)`. Most often use the latter.  They mean the same thing. 
- if-then-else is `(if eTest eThen eElse)`. 
- Parentheses *ALWAYS* matter in Racket! (Adding "extra" parentheses changes the meaning.)
- List operations named differently: `[] -> null, :: -> cons, hd -> car, tl -> cdr, null -> null?`. Can also use `'()` instead of `null`.  
- The library function `list` is convenient for making lists: `(list 4 2 1 2 ...)`. 
- Hyphens are allowed in variable names.  It is conventional to use them as separators (rather than `_`). 
- `true -> #t`, `false -> #f`. 
- In conditionals, anything other than `#f` is "truthy" (so empty lists, empty strings, 0, etc. are all "true").
- Can use square brackets instead of parentheses.
- Let expressions: `(let ([x1 e1] [x2 e2] [x3 e3]) body)`. 
- Variety of let expressions, with different semantics (`let , let*, letrec, define`). 
- Top-level bindings can refer to later bindings, but do so only inside functions (so they don't actually get used until after they have been defined). 
- Mutation allowed via `set!` -- essentially an assignment. (Should only be used when really appropriate.) 
- `cons` makes pairs. ``#1 -> car`, `#2 -> cdr` . 
- Documentation for pairs and list: http://docs.racket-lang.org/reference/pairs.html.
- A list is just nested pairs (made with `cons` ) ending with a `cdr` of `null`. 
- Use thunks to delay evaluation of expressions.
- Be careful not to evaluate thunks too soon when defining streams. 
- Memoization: Store previous results and reuse on same input to avoid recomputation.
- `begin` used to sequentially group expressions (useful in conjunction with `set!`). 

**Notes on homework:**

- Because Section 4 in Part A had no homework, this module is Section *5* but with Homework *4*.  This "lag" will continue for the next module and through Part C.
- Some questions expect you to look up and use specific Racket library functions. Do so! 
- `funny-number-stream`  needs to be the stream (i.e. the thunk), not a function that would return the stream. Same for `dan-then-dog` . 
- In problems 9 and 10, when searching in the `vector/list`, you need to return the whole pair, not just the `cdr`. 
- Test your functions! A lot. 
- Extra parentheses are *NEVER OKAY*. Be careful to use only where needed. 
- Remember that you cannot write arithmetic the "normal" way.

**Racket forms for common tasks:**

Defining a function:

```scheme
(define (f x y) body)

(define (f x y)
   body)

(define f (lambda (x y) body))

; no-argument function:
(define (f) body)
(define f (lambda () body))
```

Let expressions:

```scheme
(let ([x1 e1]
      [x2 e2]
      [f1 (lambda (x) body)])
   letBody))
```

If expressions:

```scheme
(if testExp
    thenExp
    elseExp)
```

Cond expressions:

```scheme
(cond [test1 exp1]
      [test2 exp2]
      [test3 exp3]
      [#t expDefault])
```

# Extra Practice Problems

  Contributed by Pavel Lepin and Charilaos Skiadas

1. Write a function `palindromic` that takes a list of numbers and evaluates to a list of numbers of the same length, where each element is obtained as follows: the first element should be the sum of the first and the last elements of the original list, the second one should be the sum of the second and second to last elements of the original list, etc.  Example: `(palindromic (list 1 2 4 8))` evaluates to `(list 9 6 6 9)`.
2. Define a stream `fibonacci`, the first element of which is 0, the second one is 1, and each successive element is the sum of two immediately preceding elements.
3. Write a function `stream-until` that takes a function `f` and a stream `s`, and applies `f` to the values of `s` in succession until `f` evaluates to `#f`.
4. Write a function `stream-map` that takes a function `f` and a stream `s`, and returns a new stream whose values are the result of applying `f` to the values produced by `s`.
5. Write a function `stream-zip` that takes in two streams `s1` and `s2` and returns a stream that produces the pairs that result from the other two streams (so the first value for the result stream will be the pair of the first value of `s1` and the first value of `s2`).
6. Thought experiment: Why can you *not* write a function `stream-reverse` that is like Racket's reverse function for lists but works on streams.
7. Write a function `interleave` that takes a list of streams and produces a new stream that takes one element from each stream in sequence. So it will first produce the first value of the first stream, then the first value of the second stream and so on, and it will go back to the first stream when it reaches the end of the list. Try to do this without ever adding an element to the end of a list.
8. Define a function `pack` that takes an integer `n` and a stream `s`, and returns a stream that produces the same values as `s` but packed in lists of `n` elements. So the first value of the new stream will be the list consisting of the first `n` values of `s`, the second value of the new stream will contain the next `n` values, and so on.
9. We'll use *Newton's Method* for approximating the square root of a number, but by producing a stream of ever-better approximations so that clients can "decide later" how approximate a result they want:  Write a function `sqrt-stream` that takes a number n, starts with n as an initial guess in the stream, and produces successive guesses applying $$ f_n(x)=\frac{1}{2}(x+\frac{n}{x})$$ to the current guess.
10. Now use `sqrt-stream` from the previous problem to define a function `approx-sqrt` that takes two numbers `n` and `e` and returns a number `x` such that $$x\cdot x$$ is within `e` of `n`.  Be sure not to create more than one stream nor ask for the same value from the stream more than once.  Note: Because Racket defaults to fully precise rational values, you may wish to use a floating-point number for `n` (e.g., `10.0` instead of `10`) as well as for `e`.
11. Write a macro perform that has the following two forms: 

```scheme
(perform e1 if e2)
(perform e1 unless e2)
```

`e1` should be evaluated (once) depending on the result of evaluating `e2` -- only if `e2` evaluates to `#f` in the latter case, and only if it doesn't in the former case. If `e1` is never evaluated, the entire expression should evaluate to `e2`. Neither `e1` nor `e2` should be evaluated more than once in any case.