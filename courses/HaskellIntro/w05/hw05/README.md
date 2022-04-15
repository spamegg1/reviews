[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Homework 5: Real World Haskell

CIS 194: Homework 5
Due Tuesday, October 4

The general remarks about style and submission from [the first week](https://www.seas.upenn.edu/~cis194/fall16/hw/01-intro.html) still apply.

## Exercise 0: Install Haskell locally

From this week on, we will work locally. Install Haskell (with GHC version 7.10.3), and make sure you can open files in the editor.

## Exercise 1: Lists, lists, lists

This exercise is mostly about discovering the functions provided by the `Prelude` and other modules such as `Data.List`, `Data.Char`, `Data.Ord`, `Data.Function`. Try to implement the following functions while making best use of the provided library functions. None of these should require you to write a recursive function! Use the given examples to understand the function better, if required.

1. ```haskell
   halveEvens :: [Integer] -> [Integer]
   ```

   From a list of integers, remove any odd entry and halve every even entry.

   ```haskell
   ex_halveEvens =
       [ halveEvens [] == []
       , halveEvens [1,2,3,4,5] == [1,2]
       , halveEvens [6,6,6,3,3,3,2,2,2] == [3,3,3,1,1,1]
       ]
   ```

2. ```haskell
   safeString :: String -> String
   ```

   In a string, replace every character that is a control character or not an ASCII character by an underscore. Use the Data.Char module.

   ```haskell
   ex_safeString =
       [ safeString [] == []
       , safeString "Hello World!" == "Hello World!"
       , safeString "That’s your line:\n" == "That_s your line:_"
       , safeString "🙋.o(“Me Me Me”)" == "_.o(_Me Me Me_)"
       ]
   ```

3. ```haskell
   holes :: [a] -> [[a]]
   ```

   Given a list, return the a list of lists that contains every list that is obtained by the original list by removing one element, in order. (The examples might be more helpful).

   ```haskell
   ex_holes =
      [ holes "" == []
      , holes "Hello" == ["ello", "Hllo", "Helo", "Helo", "Hell"]
      ]
   ```

4. ```haskell
   longestText :: Show a => [a] -> a
   ```

   Given a non-empty list, find the entry for which `show` results the longest text shown. If there are ties, prefer the last one.

   ```haskell
   ex_longestText =
      [ longestText [True,False] == False
      , longestText [2,4,16,32] == (32::Int)
      , longestText (words "Hello World") == "World"
      , longestText (words "Olá mundo") ==  "Olá"
   ```

5. ```haskell
   adjacents :: [a] -> [(a,a)]
   ```

   Pair each element with the next one in the list.

   ```haskell
   ex_adjacents =
      [ adjacents "" == []
      , adjacents [True] == []
      , adjacents "Hello" == [('H','e'),('e','l'),('l','l'),('l','o')]
      ]
   ```

6. ```haskell
   commas :: [String] -> String
   ```

   Add commas between strings.

   ```haskell
   ex_commas =
      [ commas [] == ""
      , commas ["Hello"] == "Hello"
      , commas ["Hello", "World"] == "Hello, World"
      , commas ["Hello", "", "World"] == "Hello, , World"
      , commas ["Hello", "new", "World"] == "Hello, new, World"
      ]
   ```

7. ```haskell
   addPolynomials :: [[Integer]] -> [Integer]
   ```

   Given coefficients to polynomial equations as lists of the same length, output the coefficients for the sum of these equations.

   You may assume that at least one polynomial is given.

   ```haskell
   ex_addPolynomials =
      [ addPolynomials [[]] == []
      , addPolynomials [[0, 1], [1, 1]] == [1, 2]
      , addPolynomials [[0, 1, 5], [7, 0, 0], [-2, -1, 5]] == [5, 0, 10]
      ]
   ```

8. ```haskell
   sumNumbers :: String -> Integer
   ```

   Output the sum of all natural numbers contained in the given string. A natural number in this sense is any maximal subsequence of digits, i.e. one that is neither preceded nor followed by an integer. (The examples should provide more clarification.)

   ```haskell
   ex_sumNumbers =
      [ sumNumbers "" == 0
      , sumNumbers "Hello world!" == 0
      , sumNumbers "a1bc222d3f44" == 270
      , sumNumbers "words0are1234separated12by3integers45678" == 46927
      , sumNumbers "000a." == 0
      , sumNumbers "0.00a." == 0
      ]
   ```

## Exercise 2: Word count

Write a function

```haskell
wordCount :: String -> String
```

that returns a few statistics on the input string. Some of the functions above might be useful.

Use the following example output as specification:

```haskell
Number of lines: 23
Number of empty lines: 10
Number of words: 40
Number of unique words: 25
Number of words followed by themselves: 3
Length of the longest line: 5
```

A line and a word is what `lines` respectively `words` return.

Note that if you define `main = interact wordCount` and compile that, you have create a generally useful program!

## Exercise 3: Test suite

Exercise 1 defines test cases for each function, which are conveniently all of type `[Bool]`. Copy them into your file. Also add and complete the following definition:

```haskell
testResults :: [(String, [Bool])]
testResults = [ ("halveEvens",      ex_halveEvens)
              , ("safeString",      ex_safeString)
              , ("holes",           ex_holes)
              …
              ]
```

Write a function

```haskell
formatTests :: [(String, [Bool])] -> String
```

which presents the data nicely. Here is a possible output (but feel free to be more creative):

```bash
halveEvens: 3/3 successful tests
safeString: 1/3 successful tests. Failing tests: 1, 3 and 4
holes: All 2 tests failed.
```

Define `main` to print the string returned by `formatTests` applied to `testResults`.

(Naturally, all your tests are failing. You can add some bogus data to `testResults` to test your `formatTests` function.)

Powered by [shake](http://community.haskell.org/~ndm/shake/), [hakyll](http://jaspervdj.be/hakyll/index.html), [pandoc](http://johnmacfarlane.net/pandoc/), [diagrams](http://projects.haskell.org/diagrams), and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).