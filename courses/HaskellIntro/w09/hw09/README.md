[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 9: More Parser/Grammar

CIS 194: Homework 9
 Due Tuesday, November 1

This is only half a homework assignment (10 points). Your other task this week is to write your [final project](http://cis.upenn.edu/~cis194/fall16/final.html) proposal, also due on November 1st.

## Exercise 1

Start with [this code](https://www.seas.upenn.edu/~cis194/fall16/hw/09-more-applicative.hs), which roughly corresponds to the result of the code developed during this week’s lecture.

Your task is to write a combined parser/grammar generator for EBNF

```haskell
parseBNF :: Descr f => f BNF
```

## BNF in BNF

Here is a BNF for BNF:

```haskell
identifier = letter, {letter | digit | '-'};
spaces = {' ' | newline};
quoted-char = non-quote-or-backslash | '\\', '\\' | '\\', '\'';
terminal = '\'', {quoted-char}, '\'', spaces;
non-terminal = identifier, spaces;
option = '[', spaces, rhs, spaces, ']', spaces;
repetition = '{', spaces, rhs, spaces, '}', spaces;
group = '(', spaces, rhs, spaces, ')', spaces;
atom = terminal | non-terminal | option | repetition | group;
sequence = atom, {spaces, ',', spaces, atom}, spaces;
choice = sequence, {spaces, '|', spaces, sequence}, spaces;
rhs = choice;
production = identifier, spaces, '=', spaces, rhs, ';', spaces;
bnf = production, {production};
```

This grammar is set up so that the precedence of `,` and `|` is correctly implemented: `a , b | c` will parse as `(a, b) | c`.

In this syntax for BNF, terminal characters are quoted, i.e. inside `'…'`, a `'` is replaced by `\'` and a `\` is replaced by `\\`. Also see the function `quote` in `ppRHS`.

If something is unclear about the grammar, please ask!

## Recursion

You will notice that, in contrast to the grammars in class, this is recursive: Many productions refer to `rhs`, and `rhs` itself refers to these productions. With plain recursion on the Haskell level, and the implementation of `Grammar` in class, this will not work.

Therefore, you will find a new function

```haskell
recNonTerminal :: Descr f => String -> (f a -> f a) -> f a
```

in the provided file that makes the recursion explicit, and hence  allows the grammar generator to inspect and it and make sense of it.

You use `recNonTerminal "foo"` to define a non-terminal symbol `foo`. The second argument of `recNonTerminal` is a function that returns the right-hand side of the production. The  argument given to that function is a description to be used whenever `foo` is referenced.

So instead of the naively recursive (and non-functional) variant

```haskell
aList :: Descr f => f [Item]
aList = nonTerminal "list" $
    ((:) <$> anItem <*> aList) `orElse` pure []
```

you would encode that as

```haskell
aList :: Descr f => f [Item]
aList = recNonTerminal "list" $ \list ->
    ((:) <$> anItem <*> list) `orElse` pure []
```

## Larger example

The following is an example that shows how to use `recNonTerminal` and is otherwise also similar to your exercise. You can use it as an template.

In this example, we have a parser and a grammar generator for simple  arithmetic expressions with addition, multiplication and constant  integers.

```haskell
data Expr = Plus Expr Expr | Mult Expr Expr | Const Integer
    deriving Show

mkPlus :: Expr -> [Expr] -> Expr
mkPlus = foldl Plus

mkMult :: Expr -> [Expr] -> Expr
mkMult = foldl Mult

parseExpr :: Descr f => f Expr
parseExpr = recNonTerminal "expr" $ \ exp ->
    ePlus exp

ePlus :: Descr f => f Expr -> f Expr
ePlus exp = nonTerminal "plus" $
    mkPlus <$> eMult exp
           <*> many (spaces *>  char '+' *>  spaces *> eMult exp)
           <*  spaces

eMult :: Descr f => f Expr -> f Expr
eMult exp = nonTerminal "mult" $
    mkPlus <$> eAtom exp
           <*> many (spaces *>  char '*' *>  spaces *> eAtom exp)
           <*  spaces

eAtom :: Descr f => f Expr -> f Expr
eAtom exp = nonTerminal "atom" $
    aConst `orElse` eParens exp

aConst :: Descr f => f Expr
aConst = nonTerminal "const" $ Const . read <$> many1 digit

eParens :: Descr f => f a -> f a
eParens inner =
    id <$  char '('
       <*  spaces
       <*> inner
       <*  spaces
       <*  char ')'
       <*  spaces
```

## BNF helper functions

Note the functions

```haskell
mkChoices :: RHS -> [RHS] -> RHS
mkSequences :: RHS -> [RHS] -> RHS
```

in the provided code. They might be useful in when parsing the `sequence` and `choice` productions (just as `mkPlus` and `mkMult` in the example).

## Testing and debugging your code

- If you write many small functions, maybe one for each production, on the top level, you can easily test them in GHCi:

  ```haskell
  *Main> parse aProduction  "identifier = letter, {letter | digit | '-'};"
  Just ("identifier",Sequence (NonTerminal "letter")
  (Repetition (Choice (Choice (NonTerminal "letter") (NonTerminal "digit")) (Terminal "-"))))
  ```

  (linebrake added manually by me)

- If you run

  ```haskell
  *Main> putStr $ ppGrammar "bnf" parseBNF
  ```

  you should get the grammar above

- You should be able to round-trip with the pretty-printer, i.e. parse back what it wrote:

  ```haskell
  *Main> let bnf1 = runGrammer "expr" parseExpr
  *Main> let bnf2 = runGrammer "expr" parseBNF
  *Main> let f = Data.Maybe.fromJust . parse parseBNF. ppBNF
  *Main> f bnf1 == bnf1
  True
  *Main> f bnf2 == bnf2
  True
  ```

  The last line is quite meta: You are unsing `parseBNF` as a parser on the pretty-printed grammar produced from interpreting `parseBNF` as a grammar.

# Optional exercise: A better recursion

This is not part of the homework, but if find this brain food yummy, feel free to implement it.

There is a way to get around defining `recNonTerminal` and explicitly passing around the recursive call (i.e. the `exp` in the example), if we adjust our `Grammar` type as follows:

```haskell
newtype Grammar a = G ([String] -> (BNF, RHS))
```

The idea is that the list of strings is those non-terminals that we are currently defining. So in `nonTerminal`, we check if the non-terminal to be introduced is currently in the  process of being defined, and then simply ignore the body. This way, the recursion is stopped automatically:

```haskell
nonTerminalG :: String -> (Grammar a) -> Grammar a
nonTerminalG name (G g) = G $ \seen ->
    if name `elem` seen
    then ([], NonTerminal name)
    else let (prods, rhs) = g (name : seen)
         in (prods ++ [(name, rhs)], NonTerminal name)
```

Adjust the other primitives on `Grammar` to type-check again, and observe that this parser/grammar generator for expressions, with genuine recursion, works now:

```haskell
parseExp :: Descr f => f Expr
parseExp = nonTerminal "expr" $
    ePlus

ePlus :: Descr f => f Expr
ePlus = nonTerminal "plus" $
    mkPlus <$> eMult
           <*> many (spaces *>  char '+' *>  spaces *> eMult)
           <*  spaces

eMult :: Descr f => f Expr
eMult = nonTerminal "mult" $
    mkPlus <$> eAtom
           <*> many (spaces *>  char '*' *>  spaces *> eAtom)
           <*  spaces

eAtom :: Descr f => f Expr
eAtom = nonTerminal "atom" $
    aConst `orElse` eParens parseExp
```

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          

  