module WordNumber where

import Data.List (intersperse)

digitToWord :: Int -> String
digitToWord n =
    case n of
    0 -> "zero"
    1 -> "one"
    2 -> "two"
    3 -> "three"
    4 -> "four"
    5 -> "five"
    6 -> "six"
    7 -> "seven"
    8 -> "eight"
    9 -> "nine"

digits :: Int -> [Int]
digits n
    | n < 10 = [n]
    | otherwise = digits (div n 10) ++ digits (mod n 10)

hyphen :: [String] -> String
hyphen [] = ""
hyphen [x] = x
hyphen (x:xs) = x ++ "-" ++ hyphen xs

wordNumber :: Int -> String
wordNumber n = hyphen $ map digitToWord (digits n)