import Data.Monoid
import Control.Applicative
import Test.QuickCheck
-- class Functor f => Applicative f where
    -- pure :: a -> f a
    -- (<*>) :: f (a -> b) -> f a -> f b

-- Compare with Functor
--   ($) ::                    (a -> b) ->   a ->   b
-- (<$>) :: Functor     f =>   (a -> b) -> f a -> f b
-- (<*>) :: Applicative f => f (a -> b) -> f a -> f b

-- Library lift functions
-- liftA :: Applicative f => (a -> b) -> f a -> f b
-- liftA2 :: Applicative f => (a -> b -> c) -> f a -> f b -> f c
-- liftA3 :: Applicative f => (a -> b -> c -> d) -> f a -> f b -> f c -> f d

-- Compare Tuple Monoid and Tuple Applicative instances
-- instance (Monoid a, Monoid b) => Monoid (a,b) where
    -- mempty = (mempty, mempty)
    -- (a, b) `mappend` (a',b') = (a `mappend` a', b `mappend` b')
-- instance Monoid a => Applicative ((,) a) where
    -- pure x = (mempty, x)
    -- (u, f) <*> (v, x) = (u `mappend` v, f x)

-- Compare Maybe Monoid and Maybe Applicative instances
-- instance Monoid a => Monoid (Maybe a) where
    -- mempty = Nothing
    -- mappend m Nothing = m
    -- mappend Nothing m = m
    -- mappend (Just a) (Just a') = Just (mappend a a')
-- instance Applicative Maybe where
    -- pure = Just
    -- Nothing <*> _ = Nothing
    -- _ <*> Nothing = Nothing
    -- Just f <*> Just a = Just (f a)

-- List Applicative
-- (<*>) :: f (a -> b) -> f a -> f b
-- (<*>) :: [ ] (a -> b) -> [ ] a -> [ ] b
-- more syntactically typical
-- (<*>) :: [(a -> b)] -> [a] -> [b]

-- Here is how Applicative <*> works:
prop_List1 = ([(*2), (*3)] <*> [4, 5]) == [2 * 4, 2 * 5, 3 * 4, 3 * 5]
prop_List2 = ([(+1), (*2)] <*> [2, 4]) == [ (+1) 2 , (+1) 4 , (*2) 2 , (*2) 4 ]
prop_Maybe1 = (Just (*2) <*> Just 2) == Just 4
prop_Maybe2 = (Just (*2) <*> (Nothing :: Maybe Int)) == Nothing
prop_Maybe3 = ((Nothing :: Maybe(Int -> Int)) <*> Just (2 :: Int)) == Nothing
prop_Maybe4 = ((Nothing :: Maybe(Int -> Int)) <*> Nothing) == Nothing
prop_String = (("Woo", (+1)) <*> (" Hoo!", 0)) == ("Woo Hoo!", 1)
prop_Tuple1 = ((Sum 2, (+1)) <*> (Sum 0, 0)) == (Sum {getSum = 2},1)
prop_Tuple2 = ((Product 3, (+9))<*>(Product 2, 8)) == (Product {getProduct = 6},17)
prop_Tuple3 = ((All True, (+1))<*>(All False, 0)) == (All {getAll = False},1)
prop_ListTuple = ((,) <$> [1, 2] <*> [3, 4]) == [(1,3),(1,4),(2,3),(2,4)]
prop_ListSum = ((+) <$> [1, 2] <*> [3, 5]) == [1+3, 1+5, 2+3, 2+5]
prop_ListMax = (max <$> [1, 2] <*> [1, 4]) == [1,4,2,4]
prop_liftA21 = (liftA2 (,) [1, 2] [3, 4]) == [(1,3),(1,4),(2,3),(2,4)]
prop_liftA22 = (liftA2 (+) [1,2] [3,5]) == [4,6,5,7]
prop_liftA23 = (liftA2 max [1,2] [1,4]) == [1,4,2,4]

-- some examples, testing
prop_purePlusOneList x = (pure (+1) <*> [1..x]) == [2..(x+1)]
prop_pureInsertIntoList x = (pure x :: [Int]) == [x]
prop_pureInsertIntoMaybe x = (pure x :: Maybe Int) == Just x
prop_pureInsertIntoEither x = (pure x :: Either Int Int) == Right x
prop_pureInsertIntoPair x = (pure x :: ([Int], Int)) == ([], x)

-- main
main :: IO()
main = do
    quickCheck (prop_purePlusOneList :: Int -> Bool)
    quickCheck (prop_pureInsertIntoList :: Int -> Bool)
    quickCheck (prop_pureInsertIntoMaybe :: Int -> Bool)
    quickCheck (prop_pureInsertIntoEither :: Int -> Bool)
    quickCheck (prop_pureInsertIntoPair :: Int -> Bool)
    quickCheck prop_List1
    quickCheck prop_List2
    quickCheck prop_ListSum
    quickCheck prop_ListMax
    quickCheck prop_Maybe1
    quickCheck prop_Maybe2
    quickCheck prop_Maybe3
    quickCheck prop_Maybe4
    quickCheck prop_String
    quickCheck prop_Tuple1
    quickCheck prop_Tuple2
    quickCheck prop_Tuple3
    quickCheck prop_ListTuple
    quickCheck prop_liftA21
    quickCheck prop_liftA22
    quickCheck prop_liftA23
