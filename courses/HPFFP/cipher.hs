module Cipher where

import Data.Char
import Test.QuickCheck
-- for Caesar cipher
-- uppercase letters are converted to integers 65-90
-- lowercase letters are converted to integers 97-122
-- for example;
-- ord 'A' = 65, ord 'Z' = 90; ord 'a' = 97, ord 'z' = 122
-- chr 65 = 'A', chr 90 = 'Z'; chr 97 = 'a', chr 122 = 'z'

shiftby :: Int -> Char -> Char
shiftby shift char
    | isUpper char = chr(65 + mod((ord char - 65) + shift) 26)
    | isLower char = chr(97 + mod((ord char - 97) + shift) 26)
    | otherwise = char

caesar :: Int -> String -> String
caesar _ "" = ""
caesar shift plaintext =
    [shiftby shift (head plaintext)] ++ caesar shift (tail plaintext)

uncaesar :: Int -> String -> String
uncaesar shift = caesar (-shift)

---------------------------------------------------------
-- For Vigenere cipher
-- Converts letter in keyword to shift integer 0-25
-- for example, 'a' and 'A' are 0, 'z' and 'Z' are 25, etc.
getshift :: Char -> Int
getshift char
    | isUpper char = (ord char) - 65
    | isLower char = (ord char) - 97
    | otherwise = 0

-- gets next position in keyword
-- increasing position in keyword each time, wrapping around if at end
-- non-letter chars in plaintext are skipped, keyword letter not consumed
nextpos :: Int -> Int -> Char -> Int
nextpos position size currentchar =
    if isLetter currentchar
    then (mod (position + 1) size)
    else position

-- applies Vigenere cipher according to current position in keyword
-- recursively goes letter by letter in plaintext
vigenerehelper :: Int -> String -> String -> String
vigenerehelper _ _ "" = ""
vigenerehelper position keyword plaintext =
    [shiftby (getshift (keyword!!position)) (head plaintext)]
    ++ vigenerehelper
        (nextpos position (length keyword) (head plaintext))
        keyword (tail plaintext)

vigenere :: String -> String -> String
vigenere = vigenerehelper 0


getunshift :: Char -> Int
getunshift char
    | isUpper char = (mod (26 - getshift char) 26) + 65
    | isLower char = (mod (26 - getshift char) 26) + 97
    | otherwise = 0

-- calculates "opposite" of keyword in lowercase
unshiftby :: String -> String
unshiftby "" = ""
unshiftby keyword =
    [chr(getunshift (head keyword))] ++ unshiftby(tail keyword)

unvigenere :: String -> String -> String
unvigenere keyword = vigenere (unshiftby keyword)

-- tests

prop_caesar :: Int -> String -> Bool
prop_caesar shift string =
    if all isAlpha string
    then uncaesar shift (caesar shift string) == string
    else True

prop_vigenere :: String -> String -> Bool
prop_vigenere keyword plaintext =
    if all isAlpha plaintext && all isAlpha keyword
        && plaintext /= "" && keyword /= ""
    then unvigenere keyword (vigenere keyword plaintext) == plaintext
    else True

main :: IO ()
main = do
    quickCheck prop_caesar
    quickCheck prop_vigenere
