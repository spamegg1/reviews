{-# LANGUAGE NoMonomorphismRestriction #-}

module DetermineTheType where

example = head [(0 :: Integer, "doge"), (1, "kitteh")]
example2 = if False then True else False
example3 = length [1,2,3,4,5]
example4 = (length [1,2,3,4]) > (length "TACOCAT")

x = 5
y = x + 5
w = y * 10
z y = y * 10
f = 4 / y

a = "Julie"
b = " <3 "
c = "Haskell"
g = a ++ b ++ c