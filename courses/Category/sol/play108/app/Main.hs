module Main where

import Lib
import Data.Bifunctor
import Data.Char

main :: IO ()
main = print $ bimap toUpper (+1) (Pair 'a' 1)
