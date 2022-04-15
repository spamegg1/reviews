module Io where
import Test.QuickCheck

-- getLine :: IO String
-- read :: Read a => String -> a
getInt :: IO Int
getInt = fmap read getLine

-- Prelude> getInt
-- 10
-- 10

-- Prelude> let getInt = 10 :: Int
-- Prelude> const () getInt
-- ()
-- Prelude> const getInt 5
-- 10

-- Prelude> return 1 :: IO Int
-- 1
-- Prelude> ()
-- ()
-- Prelude> return () :: IO ()
-- Prelude>

-- Prelude> fmap (+1) getInt
-- 10
-- 11

-- Prelude> fmap (++ " and me too!") getLine
-- hello
-- "hello and me too!"

meTooIsm :: IO String
meTooIsm = do
    input <- getLine
    return (input ++ "and me too!")

bumpIt :: IO Int
bumpIt = do
    intVal <- getInt
    return (intVal + 1)

