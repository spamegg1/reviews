{-# LANGUAGE FlexibleContexts #-}
import CapitalizeWords
import Test.QuickCheck
import Data.List (sort)

-- QuickCheck without hspec
prop_additionGreater :: Int -> Bool
prop_additionGreater x = x + 1 > x

-- for a function
half x = x / 2
-- this property should hold
halfIdentity = (*2) . half
prop_halfIdentity :: Double -> Bool
prop_halfIdentity x = x == halfIdentity x

-- for any list you apply sort to
-- this property should hold
listOrdered :: (Ord a) => [a] -> Bool
listOrdered xs = snd $ foldr go (Nothing, True) xs
    where   go _ status@(_, False) = status
            go y (Nothing, t) = (Just y, t)
            go y (Just x, t) = (Just y, x >= y)

prop_listOrdered :: [Int] -> Bool
prop_listOrdered xs = listOrdered (sort xs)

-- checking associativity and commutativity of addition and multiplication on Int
plusAssociative :: Int -> Int -> Int -> Bool
plusAssociative x y z = x + (y + z) == (x + y) + z

plusCommutative :: Int -> Int -> Bool
plusCommutative x y = x + y == y + x

timesAssociative :: Int -> Int -> Int -> Bool
timesAssociative x y z = x * (y * z) == (x * y) * z

timesCommutative :: Int -> Int -> Bool
timesCommutative x y = x * y == y * x

-- quot rem
prop_quotrem :: Int -> Int -> Bool
prop_quotrem x y =
    if y /= 0
    then (quot x y)*y + (rem x y) == x
    else True
-- div mod
prop_divmod :: Int -> Int -> Bool
prop_divmod x y =
    if y /= 0
    then (div x y)*y + (mod x y) == x
    else True

-- list reversing twice
prop_reverse :: [Int] -> Bool
prop_reverse xs = (reverse . reverse) xs == id xs

-- dollar and composition
prop_dollar :: Int -> Bool
prop_dollar a = (f $ a) == f a
    where   f :: Int -> Int
            f x = x*2 + 3

prop_comp :: Double -> Bool
prop_comp a = (f . g) a == (\x -> f (g x))a
    where   f :: Double -> Double
            f x = x / 2
            g :: Double -> Double
            g x = 3*x

-- are these two functions equal? NOPE
--prop_foldrappend :: [Int] -> [Int] -> Bool
--prop_foldrappend xs ys = foldr (:) xs ys == (++) xs ys

-- what about these two functions?
prop_foldrconcat :: [[Int]] -> Bool
prop_foldrconcat xs = (foldr (++) [] xs) == concat xs

-- length and take: FALSE
--prop_lengthtaken :: Int -> [Int] -> Bool
--prop_lengthtaken n xs = length (take n xs) == n

-- read and show
prop_readshowInt :: Int -> Bool
prop_readshowInt x = (read (show x)) == x
prop_readshowChar :: Char -> Bool
prop_readshowChar x = (read (show x)) == x

-- idempotence for capitalizeWord and sort
twice f = f . f
fourTimes = twice . twice

prop_capitalizeWord :: String -> Bool
prop_capitalizeWord x =
    (capitalizeWord x == twice capitalizeWord x)
    &&
    (capitalizeWord x == fourTimes capitalizeWord x)

prop_sort :: [Int] -> Bool
prop_sort x = (sort x == twice sort x) && (sort x == fourTimes sort x)

-- main
runQc :: IO ()
runQc = do
    quickCheck prop_additionGreater
    quickCheck prop_halfIdentity
    quickCheck prop_listOrdered
    quickCheck plusAssociative
    quickCheck plusCommutative
    quickCheck timesAssociative
    quickCheck timesCommutative
    quickCheck prop_quotrem
    quickCheck prop_divmod
    quickCheck prop_reverse
    quickCheck prop_dollar
    quickCheck prop_comp
    --quickCheck prop_foldrappend
    quickCheck prop_foldrconcat
    --quickCheck prop_lengthtaken
    quickCheck prop_readshowInt
    quickCheck prop_readshowChar
    quickCheck prop_capitalizeWord