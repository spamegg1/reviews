module LookUp where
import Control.Applicative
import Test.QuickCheck
import Data.List (elemIndex)

-- f 3 = Just "hello"
f x = lookup x [ (3, "hello"), (4, "julie"), (5, "kbai")]
-- g 7 = Just "sup?"
g y = lookup y [ (7, "sup?"), (8, "chris"), (9, "aloha")]
-- h 5 = 6
h z = lookup z [(2, 3), (5, 6), (7, 8)]
-- m 6 = Nothing
m x = lookup x [(4, 10), (8, 13), (1, 9001)]


-- Exercises
-- In the following exercises you will need to use the following
-- terms to make the expressions typecheck:
-- 1. pure
-- 2. (<$>) or fmap
-- 3. (<*>)
-- Make the following expressions typecheck.

-- 1.
added :: Maybe Integer
added = (<$>) (+3) (lookup 3 $ zip [1, 2, 3] [4, 5, 6])

-- 2.
y1 :: Maybe Integer
y1 = lookup 3 $ zip [1, 2, 3] [4, 5, 6]

z :: Maybe Integer
z = lookup 2 $ zip [1, 2, 3] [4, 5, 6]

tupled :: Maybe (Integer, Integer)
tupled = (,) <$> y1 <*> z

-- 3.
x :: Maybe Int
x = elemIndex 3 [1, 2, 3, 4, 5]

y2 :: Maybe Int
y2 = elemIndex 4 [1, 2, 3, 4, 5]

max' :: Int -> Int -> Int
max' = max

maxed :: Maybe Int
maxed = max' <$> x <*> y2

-- 4.
xs = [1, 2, 3]
ys = [4, 5, 6]

x2 :: Maybe Integer
x2 = lookup 3 $ zip xs ys

y3 :: Maybe Integer
y3 = lookup 2 $ zip xs ys

summed :: Maybe Integer
summed = fmap sum ( (,) <$> x2 <*> y3 )


-- main
main :: IO()
main = do
    quickCheck ( ((++) <$> f 3 <*> g 7) == Just "hellosup?" )
    quickCheck ( ((+) <$> h 5 <*> m 1) == Just 9007 )
    quickCheck ( ((+) <$> h 5 <*> m 6) == Nothing )
    quickCheck ( (liftA2 (++) (g 9) (f 4)) == Just "alohajulie" )
    quickCheck ( (liftA2 (^) (h 5) (m 4)) == Just 60466176 )
    quickCheck ( (liftA2 (*) (h 5) (m 4)) == Just 60 )
    quickCheck ( (liftA2 (*) (h 1) (m 1)) == Nothing )
