module Unfold where
import Data.List

mehSum :: Num a => [a] -> a
mehSum xs = go1 0 xs
    where go1 :: Num a => a -> [a] -> a
          go1 n [] = n
          go1 n (x:xs) = (go1 (n+x) xs)

niceSum :: Num a => [a] -> a
niceSum = foldl' (+) 0

mehProduct :: Num a => [a] -> a
mehProduct xs = go2 1 xs
    where go2 :: Num a => a -> [a] -> a
          go2 n [] = n
          go2 n (x:xs) = (go2 (n*x) xs)

niceProduct :: Num a => [a] -> a
niceProduct = foldl' (*) 1

-- Remember the redundant structure when we looked at folds?
mehConcat :: [[a]] -> [a]
mehConcat xs = go3 [] xs
    where go3 :: [a] -> [[a]] -> [a]
          go3 xs' [] = xs'
          go3 xs' (x:xs) = (go3 (xs' ++ x) xs)

niceConcat :: [[a]] -> [a]
niceConcat = foldr (++) []
-- Write the function myIterate using direct recursion.
-- Compare the behavior with the built-in iterate to gauge correctness.
-- Do not look at the source or any examples of
-- iterate so that you are forced to do this yourself.
-- >>> take 10 $ myIterate (+1) 0
-- [0,1,2,3,4,5,6,7,9]
myIterate :: (a -> a) -> a -> [a]
myIterate f a = a : myIterate f (f a)
-- Write the function myUnfoldr using direct recursion.
-- Compare with the built-in unfoldr to check your implementation.
-- Again, don’t look at implementations of unfoldr so
-- that you figure it out yourself.
-- >>> take 10 $ myUnfoldr (\b -> Just (b, b+1)) 0
-- [0,1,2,3,4,5,6,7,8,9]
myUnfoldr :: (b -> Maybe (a, b)) -> b -> [a]
myUnfoldr f b =
    case f b of
    Nothing     -> []
    Just (a, b') -> a : myUnfoldr f b'
-- 3. Rewrite myIterate into betterIterate using myUnfoldr. A
-- hint — we used unfoldr to produce the same results as
-- iterate earlier. Do this with different functions and see if
-- you can abstract the structure out.
-- It helps to have the types in front of you
-- myUnfoldr :: (b -> Maybe (a, b)) -> b -> [a]
-- Remember, your betterIterate should have the same results as iterate.
-- >>> take 10 $ betterIterate (+1) 0
-- [0,1,2,3,4,5,6,7,8,9]
betterIterate :: (a -> a) -> a -> [a]
betterIterate f = myUnfoldr (\a -> Just(a, f a))




