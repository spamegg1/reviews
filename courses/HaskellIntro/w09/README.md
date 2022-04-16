[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# More Applicative    

Today we will not learn much new stuff, but rather dwell a bit on the `Monad` vs. `Applicative` distinction, and see a rather impressive example abstraction due to these concepts, and due to type classes.

The code for the lecture needs

```haskell
import Data.Char
import Data.Maybe
import Data.List

import System.Environment
import System.IO
import System.Exit
```

# The parser

Here is a parser as introduced in last week’s homework:

```haskell
newtype Parser a = P (String -> Maybe (a, String))

runParser :: Parser t -> String -> Maybe (t, String)
runParser (P p) = p

parse :: Parser a -> String -> Maybe a
parse p input = case runParser p input of
    Just (result, "") -> Just result
    _ -> Nothing -- handles both no result and leftover input

noParserP :: Parser a
noParserP = P (\_ -> Nothing)

pureParserP :: a -> Parser a
pureParserP x = P (\input -> Just (x,input))

instance Functor Parser where
    fmap f p = P p'
      where
        p' input = case runParser p input of
            Just (result, rest) -> Just (f result, rest)
            Nothing             -> Nothing

instance Applicative Parser where
    pure = pureParserP
    p1 <*> p2 = P $ \input -> do
        (f, rest1) <- runParser p1 input
        (x, rest2) <- runParser p2 rest1
        return (f x, rest2)

instance Monad Parser where
    return = pure
    p1 >>= k = P $ \input -> do
        (x, rest1) <- runParser p1 input
        runParser (k x) rest1

anyCharP :: Parser Char
anyCharP = P $ \input -> case input of
    (c:rest) -> Just (c, rest)
    []       -> Nothing

charP :: Char -> Parser ()
charP c = do
    c' <- anyCharP
    if c == c' then return ()
               else noParserP

anyCharButP :: Char -> Parser Char
anyCharButP c = do
    c' <- anyCharP
    if c /= c' then return c'
               else noParserP

letterOrDigitP :: Parser Char
letterOrDigitP = do
    c <- anyCharP
    if isAlphaNum c then return c else noParserP

orElseP :: Parser a -> Parser a -> Parser a
orElseP p1 p2 = P $ \input -> case runParser p1 input of
    Just r -> Just r
    Nothing -> runParser p2 input

manyP :: Parser a -> Parser [a]
manyP p = (pure (:) <*> p <*> manyP p) `orElseP` pure []

many1P :: Parser a -> Parser [a]
many1P p = pure (:) <*> p <*> manyP p

sepByP :: Parser a -> Parser () -> Parser [a]
sepByP p1 p2 = ((:) <$> p1 <*> (manyP (p2 *> p1))) `orElseP` pure []
```

It differs slightly from the example homework solution:

- It has `P` appended to all the definitions, to avoid name clashes later.
- It uses `newtype` instead of `data`.
- It uses `pure (:) <*>` instead of `(:) <$>`. Of course, these expressions are the same; this is for didactic reasons later.
- Some monadic operations (`return`, `(>>)`) are replaced their applicative counterparts (`pure`, `(*>)`).

# newtypes

So what is this `newtype` about? Two weeks ago we visualized the memory representation of data in Haskell, and we saw that a constructor like `Just` is a box of its own, with a pointer to the actual data. This was necessary to distinguish it from `Nothing`.

But for a type such as

```haskell
data Parser a = P (String -> Maybe (a, String))
```

where there is *exactly one* constructor with *exactly one* parameter, this box does not really add anything. The only point of using `data` here is to give the type `String -> Maybe (a, String)` a new, separate name – operationally, these two types are isomorphic, and we’d rather not have this box.

By writing

```haskell
newtype Parser a = P (String -> Maybe (a, String))
```

we avoid the indirection. Using this keyword, we still get a new,  separate type, and on the language level, not much changes – we produce  values of type `Parser` using `P`, and get hold of the function by pattern matching. But the operational overhead is eliminated.

Contrast this with `type` which introduces only a synonym, so does not incur an operational overhead, but also does not introduce a separate type.

# EBNF producing code

Usually when we write a parser for a file format, we might also want  to have a formal specification of the format, commonly given as an [EBNF syntax](https://en.wikipedia.org/wiki/Extended_Backus–Naur_Form). Here, for example, for CSV files:

```haskell
cell = '"', {not-quote}, '"';
line = (cell, {',', cell} | ''), newline;
csv  = {line};
```

## An EBNF library

It is straight-forward to create a Haskell data type to represent an  EBNF syntax description. Here is a simple EBNF library (data type and  pretty-printer) for your convenience:

```haskell
data RHS
  = Terminal String
  | NonTerminal String
  | Choice RHS RHS
  | Sequence RHS RHS
  | Optional RHS
  | Repetition RHS
  deriving (Show, Eq)

mkChoices :: RHS -> [RHS] -> RHS
mkChoices = foldl Choice

mkSequences :: RHS -> [RHS] -> RHS
mkSequences = foldl Sequence

ppRHS :: RHS -> String
ppRHS = go 0
  where
    go _ (Terminal s)     = surround "'" "'" $ concatMap quote s
    go _ (NonTerminal s)  = s
    go a (Choice x1 x2)   = p a 1 $ go 1 x1 ++ " | " ++ go 1 x2
    go a (Sequence x1 x2) = p a 2 $ go 2 x1 ++ ", "  ++ go 2 x2
    go _ (Optional x)     = surround "[" "]" $ go 0 x
    go _ (Repetition x)   = surround "{" "}" $ go 0 x

    surround c1 c2 x = c1 ++ x ++ c2

    p a n | a > n     = surround "(" ")"
          | otherwise = id

    quote '\'' = "\\'"
    quote '\\' = "\\\\"
    quote c    = [c]

type Production = (String, RHS)
type BNF = [Production]

ppBNF :: BNF -> String
ppBNF = unlines . map (\(i,rhs) -> i ++ " = " ++ ppRHS rhs ++ ";")
```

## Grammar combinators

We had a good time writing combinators that create complex parsers  from primitive pieces. Let us do the same for EBNF grammars. We could  simply work on the `RHS` type directly, but we can do something more nifty: We create a data type that keeps track, via a *phantom* type parameter, of what Haskell type the given EBNF syntax is the specification:

### The definition

```haskell
newtype Grammar a = G RHS

ppGrammar :: Grammar a -> String
ppGrammar (G rhs) = ppRHS rhs
```

So a value of type `Grammar t` is a description of the textual representation of the Haskell type `t`.

### Primitive combinators

Here is one simple example:

```haskell
anyCharG :: Grammar Char
anyCharG = G (NonTerminal "char")
```

Here is another one. This one does not describe any interesting Haskell type:

```haskell
charG :: Char -> Grammar ()
charG c = G (Terminal [c])
```

A combinator that creates new grammars from two existing grammars:

```haskell
orElseG :: Grammar a -> Grammar a -> Grammar a
orElseG (G rhs1) (G rhs2) = G (Choice rhs1 rhs2)
```

### Instances

We want to use our nice type classes for type constructors to work with these values:

```haskell
instance Functor Grammar where
    fmap _ (G rhs) = G rhs

instance Applicative Grammar where
    pure x = G (Terminal "")
    (G rhs1) <*> (G rhs2) = G (Sequence rhs1 rhs2)
```

Note how the `Functor` instance does not actually use the function. How should it? There are no values inside a `Grammar`!

We cannot define a `Monad` instance for `Grammar`: We would start with `(G rhs1) >>= k = …`, but there is simply no way of getting a value of type `a` that we can feed to `k`. So we will do without a `Monad` instance. This is interesting, and we will come back to that later.

### Derived combinators

Like with the parser, we can now begin to build on the primitive example to build more complicated combinators:

```haskell
manyG :: Grammar a -> Grammar [a]
manyG p = (pure (:) <*> p <*> manyG p) `orElseG` pure []

many1G :: Grammar a -> Grammar [a]
many1G p = pure (:) <*> p <*> manyG p

sepByG :: Grammar a -> Grammar () -> Grammar [a]
sepByG p1 p2 = ((:) <$> p1 <*> (manyG (p2 *> p1))) `orElseG` pure []
```

### Loops and laws

Lets run a small example

```haskell
dottedWordsG :: Grammar [String]
dottedWordsG = many1G (manyG anyCharG <* charG '.')
*Main> putStrLn $ ppGrammar dottedWordsG
'', ('', char, ('', char, ('', char, ('', char, ('', char, ('', …
```

Oh my, that is not good. Looks like the recursion in `manyG` does not work well, so we need to avoid that. But anyways we want to be explicit in the EBNF grammars about where something can be repeated, so let us make `many` primitive as well:

```haskell
manyG :: Grammar a -> Grammar [a]
manyG (G rhs) = G (Repetition rhs)
```

With this definition, we already get a simple grammar for `dottedWordsG`:

```haskell
*Main> putStrLn $ ppGrammar dottedWordsG
'', {char}, '.', {{char}, '.'}
```

This already looks like a proper EBNF grammar. One thing that is not nice about it is that there is an empty string (`''`) in a sequence (`…,…`). We do not want that.

Why is it there in the first place? Because our `Applicative` instance is not lawful! Remember that `pure id <*> g == g` should hold. One way to achieve that is to improve the `Applicative` instance to optimize this case away:

```haskell
instance Applicative Grammar where
    pure x = G (Terminal "")
    G (Terminal "") <*> G rhs2 = G rhs2
    G rhs1 <*> G (Terminal "") = G rhs1
    (G rhs1) <*> (G rhs2) = G (Sequence rhs1 rhs2)
```

Now we get what we want:

```haskell
*Main> putStrLn $ ppGrammar dottedWordsG
{char}, '.', {{char}, '.'}
```

### EBNF for CSV files

We had a parser for CSV files:

```haskell
parseCSVP :: Parser [[String]]
parseCSVP = manyP parseLine
  where
    parseLine = parseCell `sepByP` charP ',' <* charP '\n'
    parseCell = do
        charP '"'
        content <- manyP (anyCharButP '"')
        charP '"'
        return content
```

We want to derive the `Grammar` for `CSV` files from that. First we transform it into using only applicative operators:

```haskell
parseCSVP :: Parser [[String]]
parseCSVP = manyP parseLine
  where
    parseLine = parseCell `sepByP` charG ',' <* charP '\n'
    parseCell = charP '"' *> manyP (anyCharButP '"') <* charP '"'
```

And now we try to rewrite the code to produce `Grammar` instead of `Parser`. This is straight forward with the exception of `anyCharButP`. The parser code for that in inherently monadic, and we just do not have a monad instance. So we work around the issue by making that a  “primitive” grammar, i.e. introducing a non-terminal in the EBNF without a production rule – pretty much like we did for `anyCharG`:

```haskell
primitiveG :: String -> Grammar a
primitiveG s = G (NonTerminal s)

parseCSVG :: Grammar [[String]]
parseCSVG = manyG parseLine
  where
    parseLine = parseCell `sepByG` charG ',' <* charG '\n'
    parseCell = charG '"' *> manyG (primitiveG "not-quote") <* charG '"'
```

Of course the names `parse…` are not quite right any more, but let’s just leave that for now.

Here is the result:

```haskell
*Main> putStrLn $ ppGrammar parseCSVG
{('"', {not-quote}, '"', {',', '"', {not-quote}, '"'} | ''), '
'}
```

The line break is weird. We do not really want newlines in the grammar. So let’s make that primitive as well, and replace `charG '\n'` with `newlineG`:

```haskell
newlineG :: Grammar ()
newlineG = primitiveG "newline"
```

Now we get

```haskell
*Main> putStrLn $ ppGrammar parseCSVG
{('"', {not-quote}, '"', {',', '"', {not-quote}, '"'} | ''), newline}
```

which is nice and correct, but not quite the easily readable EBNF that we saw further up.

## Tracking productions

We currently let our grammars produce only the right-hand side of one EBNF production, but really, we want to produce a RHS that may refer to other productions. So let us change the type accordingly:

```haskell
newtype Grammar a = G (BNF, RHS)

ppGrammar :: String -> Grammar a -> String
ppGrammar main (G (prods, rhs)) = ppBNF $ prods ++ [(main, rhs)]
```

Now we have to adjust all our primitive combinators (but not the derived ones):

```haskell
charG :: Char -> Grammar ()
charG c = G ([], Terminal [c])

anyCharG :: Grammar Char
anyCharG = G ([], NonTerminal "char")

manyG :: Grammar a -> Grammar [a]
manyG (G (prods, rhs)) = G (prods, Repetition rhs)

mergeProds :: [Production] -> [Production] -> [Production]
mergeProds prods1 prods2 = nub $ prods1 ++ prods2

orElseG :: Grammar a -> Grammar a -> Grammar a
orElseG (G (prods1, rhs1)) (G (prods2, rhs2))
    = G (mergeProds prods1 prods2, Choice rhs1 rhs2)

instance Functor Grammar where
    fmap _ (G bnf) = G bnf

instance Applicative Grammar where
    pure x = G ([], Terminal "")
    G (prods1, Terminal "") <*> G (prods2, rhs2)
        = G (mergeProds prods1 prods2, rhs2)
    G (prods1, rhs1) <*> G (prods2, Terminal "")
        = G (mergeProds prods1 prods2, rhs1)
    G (prods1, rhs1) <*> G (prods2, rhs2)
        = G (mergeProds prods1 prods2, Sequence rhs1 rhs2)

primitiveG :: String -> Grammar a
primitiveG s = G (NonTerminal s)
```

The use of `nub` when combining productions removes  duplicates that might be used in different parts of the grammar. Not  efficient, but good enough for now.

Did we gain anything? Not yet:

```haskell
*Main> putStr $ ppGrammar "csv" (parseCSVG)
csv = {('"', {not-quote}, '"', {',', '"', {not-quote}, '"'} | ''), newline};
```

But we can now introduce a function that lets us tell the system where to give names to a piece of grammar:

```haskell
nonTerminal :: String -> Grammar a -> Grammar a
nonTerminal name (G (prods, rhs))
  = G (prods ++ [(name, rhs)], NonTerminal name)
```

Ample use of this in `parseCSVG` yields the desired result:

```haskell
parseCSVG :: Grammar [[String]]
parseCSVG = manyG parseLine
  where
    parseLine = nonTerminal "line" $
        parseCell `sepByG` charG ',' <* newline
    parseCell = nonTerminal "cell" $
        charG '"' *> manyG (primitiveG "not-quote") <* charG '"
*Main> putStr $ ppGrammar "csv" (parseCSVG)
cell = '"', {not-quote}, '"';
line = (cell, {',', cell} | ''), newline;
csv = {line};
```

This is great!

# Combining Parsers and Grammars

But note how similar `parseCSVG` and `parseCSVP` are! Would it not be great if we could implement that functionality only once, and get both a parser *and* a grammar description out of it? This way, the two would never be out of sync!

And surely this must be possible. The tool to reach for is of course to define a type class that abstracts over the parts where `Parser` and `Grammer` differ. So we have to identify all functions that are primitive in one  of the two worlds, and turn them into type class methods. This includes `char` and `orElse`. It includes `many`, too: Although `manyP` is not primitive, `manyG` is. It also includes `nonTerminal`, which does not exist in the world of parsers (yet), but we need it in grammars.

The `primitiveG` function is tricky. We use it in grammars when the code that we might use while parsing is not expressible as a  grammar. So the solution is to let it take two arguments: A `String`, when used as a descriptive non-terminal in a grammar, and a `Parser a`, used in the parsing code.

Finally, the type classes that we except, `Applicative` (and thus `Functor`), are added as constraints on our type class:

```haskell
class Applicative f => Descr f where
    char :: Char -> f ()
    many :: f a -> f [a]
    orElse :: f a -> f a -> f a
    primitive :: String -> Parser a -> f a
    nonTerminal :: String -> f a -> f a
```

The instances are easily written:

```haskell
instance Descr Parser where
    char = charP
    many = manyP
    orElse = orElseP
    primitive _ p = p
    nonTerminal _ p = p

instance Descr Grammar where
    char = charG
    many = manyG
    orElse = orElseG
    primitive s _ = primitiveG s
    nonTerminal s g = nonTerminalG s g
```

And we can now take the derived definitions, of which so far we had two copies, and define them once and for all:

```haskell
many1 :: Descr f => f a -> f [a]
many1 p = pure (:) <*> p <*> many p

anyChar :: Descr f => f Char
anyChar = primitive "char" anyCharP

dottedWords :: Descr f => f [String]
dottedWords = many1 (many anyChar <* char '.')

sepBy :: Descr f => f a -> f () -> f [a]
sepBy p1 p2 = ((:) <$> p1 <*> (many (p2 *> p1))) `orElse` pure []

newline :: Descr f => f ()
newline = primitive "newline" (charP '\n')
```

And thus we now have our CSV parser/grammar generator:

```haskell
parseCSV :: Descr f => f [[String]]
parseCSV = many parseLine
  where
    parseLine = nonTerminal "line" $
        parseCell `sepBy` char ',' <* newline
    parseCell = nonTerminal "cell" $
        char '"' *> many (primitive "not-quote" (anyCharButP '"')) <* char '"'
```

We can now use this definition both to parse and to generate grammars:

```haskell
*Main> putStr $ ppGrammar2 "csv" (parseCSV)
cell = '"', {not-quote}, '"';
line = (cell, {',', cell} | ''), newline;
csv = {line};
*Main> parse parseCSV "\"ab\",\"cd\"\n\"\",\"de\"\n\n"
Just [["ab","cd"],["","de"],[]]
```

## The INI file parser

As a final exercise, let us transform the INI file parser into a  combined thing. Here is the parser, as it could have been written for  last week’s homework (if one had used applicative style):

```haskell
parseINIP :: Parser INIFile
parseINIP = many1P parseSection
  where
    parseSection =
        pure (,) <*  charP '['
                 <*> parseIdent
                 <*  charP ']'
                 <*  charP '\n'
                 <*> (catMaybes <$> manyP parseLine)
    parseIdent = many1P letterOrDigitP
    parseLine = parseDecl `orElseP` parseComment `orElseP` parseEmpty

    parseDecl = Just <$> (
        pure (,) <*> parseIdent
                 <*  manyP (charP ' ')
                 <*  charP '='
                 <*  manyP (charP ' ')
                 <*> many1P (anyCharButP '\n')
                 <*  charP '\n')

    parseComment =
        Nothing <$ charP '#'
                <* many1P (anyCharButP '\n')
                <* charP '\n'

    parseEmpty = Nothing <$ charP '\n'
```

Transforming that to generic description is quite straight-forward. We use `primitive` again to wrap `letterOrDigitP`:

```haskell
descrINI :: Descr f => f INIFile
descrINI = many1 parseSection
  where
    parseSection =
        pure (,) <*  char '['
                 <*> parseIdent
                 <*  char ']'
                 <*  newline
                 <*> (catMaybes <$> many parseLine)
    parseIdent = many1 (primitive "alphanum" letterOrDigitP)
    parseLine = parseDecl `orElse` parseComment `orElse` parseEmpty

    parseDecl = Just <$> (
        pure (,) <*> parseIdent
                 <*  many (char ' ')
                 <*  char '='
                 <*  many (char ' ')
                 <*> many1 (primitive "non-newline" (anyCharButP '\n'))
		 <*  newline)

    parseComment =
        Nothing <$ char '#'
                <* many1 (primitive "non-newline" (anyCharButP '\n'))
		<* newline

    parseEmpty = Nothing <$ newline
```

This yields this not very helpful grammar:

```haskell
*Main> putStr $ ppGrammar2 "ini" descrINI
ini = '[', alphanum, {alphanum}, ']', newline, {alphanum, {alphanum}, {' '}…
```

But with a few uses of `nonTerminal`, we get something really nice:

```haskell
descrINI :: Descr f => f INIFile
descrINI = many1 parseSection
  where
    parseSection = nonTerminal "section" $
        pure (,) <*  char '['
                 <*> parseIdent
                 <*  char ']'
                 <*  newline
                 <*> (catMaybes <$> many parseLine)
    parseIdent = nonTerminal "identifier" $
        many1 (primitive "alphanum" letterOrDigitP)
    parseLine = nonTerminal "line" $
        parseDecl `orElse` parseComment `orElse` parseEmpty

    parseDecl = nonTerminal "declaration" $ Just <$> (
        pure (,) <*> parseIdent
                 <*  spaces
                 <*  char '='
                 <*  spaces
                 <*> remainder)

    parseComment = nonTerminal "comment" $
        Nothing <$ char '#' <* remainder

    remainder = nonTerminal "line-remainder" $
        many1 (primitive "non-newline" (anyCharButP '\n')) <* newline

    parseEmpty = Nothing <$ newline

    spaces = nonTerminal "spaces" $ many (char ' ')
*Main> putStr $ ppGrammar "ini" descrINI
identifier = alphanum, {alphanum};
spaces = {' '};
line-remainder = non-newline, {non-newline}, newline;
declaration = identifier, spaces, '=', spaces, line-remainder;
comment = '#', line-remainder;
line = declaration | comment | newline;
section = '[', identifier, ']', newline, {line};
ini = section, {section};
```

## A new main

We can adjust the `main` function of the program to parse a file, if given on the command line, or to print the grammar otherwise:

```haskell
main :: IO ()
main = do
    args <- getArgs
    case args of
        [] -> putStr $ ppGrammar2 "ini" descrINI
        [fileName] -> do
            input <- readFile fileName
            case parse descrINI input of
                Just i -> print i
                Nothing -> do
                    hPutStrLn stderr "Failed to parse INI file."
                    exitFailure
        _ -> hPutStrLn stderr "Too many arguments" >> exitFailure
```

# Conclusion

We have again seen an example of excellent support for abstraction:  Being able to define so very different things such as a parser and a  grammar description with the same code is great. Type classes helped us  here.

Note that it was crucial that our combined parser/grammars are only able to use the methods of `Applicative`, and not `Monad`. `Applicative` is less powerful, so by giving less power to the user of our `Descr` interface, there is more power on the other side.

The reason why `Applicative` is OK, but `Monad` is not, is that in `Applicative`, the *results do not affect the shape of the computation*, whereas in `Monad`, the whole point of the bind operator `(>>=)` is that *the result of the computation is used to decide the next computation*. And while this is perfectly fine for a parser, it just makes no sense  for a grammar generator, where there simply are no values around!

We have also seen that a phantom type, namely the parameter of `Grammar`, can be useful, as it lets the type system make sure we do not write nonsense. For example, the type of `orElseG` ensures that both grammars that are combined here indeed describe something of the same type.

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          

  