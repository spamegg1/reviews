module StringProcessing where
import Data.List

-- Write a recursive function named replaceThe which takes a
-- text/string, breaks it into words and replaces each instance
-- of “the” with “a”. It’s intended only to replace exactly
-- the word “the”. notThe is a suggested helper function for
-- accomplishing this.

-- >>> replaceThe "the cow loves us"
-- "a cow loves us"
replaceThe :: String -> String
replaceThe string =
    intercalate " " [(if x == "the" then "a" else x) | x <- words string]

-- Write a recursive function that takes a text/string, breaks
-- it into words, and counts the number of instances of ”the”
-- followed by a vowel-initial word.
-- >>> countTheBeforeVowel "the cow"
-- 0
-- >>> countTheBeforeVowel "the evil cow"
-- 1
counthelper :: [String] -> Integer
counthelper [] = 0
counthelper [_] = 0
counthelper (x:y:ys) =
    if x == "the" && elem (y!!0) "aeiou"
    then 1 + counthelper ys
    else counthelper (y:ys)
countTheBeforeVowel :: String -> Integer
countTheBeforeVowel string = counthelper (words string)

-- Return the number of letters that are vowels in a word.
-- Hint: it’s helpful to break this into steps. Add any helper
-- functions necessary to achieve your objectives.
-- a) Test for vowelhood
-- b) Return the vowels of a string
-- c) Count the number of elements returned
-- >>> countVowels "the cow"
-- 2
-- >>> countVowels "Mikolajczak"
-- 4
countVowels :: String -> Int
countVowels "" = 0
countVowels string =
    let rest = countVowels (tail string) in
    if elem (string!!0) "aeiou" then (1 + rest) else rest
countConsonants string = length string - countVowels string

-- Use the Maybe type to write a function that counts the number
-- of vowels in a string and the number of consonants. If the
-- number of vowels exceeds the number of consonants, the
-- function returns Nothing. In many human languages, vowels
-- rarely exceed the number of consonants so when they do, it
-- may indicate the input isn’t a word (that is, a valid input to your
-- dataset):
newtype Word' = Word' String deriving (Eq, Show)
vowels = "aeiou"
mkWord :: String -> Maybe Word'
mkWord string =
    if countVowels string > countConsonants string
    then Nothing
    else Just (Word' string)