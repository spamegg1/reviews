{-# LANGUAGE FlexibleInstances #-}
import Data.Monoid
import Test.QuickCheck

-- -- monoid instance for lists
-- instance Monoid [a] where
    -- mempty = []
    -- mappend = (++)

-- -- monoid laws
-- -- left identity
-- mappend mempty x = x
-- -- right identity
-- mappend x mempty = x
-- -- associativity
-- mappend x (mappend y z) =
-- mappend (mappend x y) z

-- mconcat = foldr mappend mempty

-- -- new monoid instances from old ones
-- instance Monoid b => Monoid (a -> b)
-- instance (Monoid a, Monoid b) => Monoid (a, b)
-- instance (Monoid a, Monoid b, Monoid c) => Monoid (a, b, c)

-- we need: a type, a semigroup instance, and a monoid instance
-- THIS IS FROM HASKELL WEBSITE:
-- newtype Sum n = Sum n deriving (Eq, Show)

-- instance Num n => Semigroup (Sum n) where
  -- Sum x <> Sum y = Sum (x + y)

-- instance Num n => Monoid (Sum n) where
  -- mempty = Sum 0

-- another example, from HPFFP
data Booly a = False' | True' deriving (Eq, Show)

-- the example in the book is WRONG,
-- GHC demands a semigroup instance before Monoid instance can be declared
-- MONOID = SEMIGROUP WITH IDENTITY
instance Semigroup (Booly a) where
    False' <> False' = False'
    False' <> True' = False'
    True' <> False' = False'
    True' <> True' = True'
-- conjunction (this is from the book)
instance Monoid (Booly a) where
    mempty = False'
    -- these are optional, derived from Semigroup instance above
    -- mappend is the same as <>
    -- mappend False' _    = False'
    -- mappend _ False'    = False'
    -- mappend True' True' = True'


-- exercise: write semigroup and monoid instance for this type
data Optional a = Nada | Only a deriving (Eq, Show)

-- once again, the book is wrong, GHC demands semigroup instance before Monoid
-- assuming type a is a semigroup, derive semigroup for Optional a
instance (Semigroup a) => Semigroup (Optional a) where
    Nada <> Nada = Nada
    Nada <> (Only a) = Only a
    (Only a) <> Nada = Only a
    (Only a) <> (Only b) = Only (a <> b)

-- this is the intended solution,
-- it works only after declaring semigroup instance above
instance (Semigroup a) => Monoid (Optional a) where
    mempty                   = Nada
    -- these are optional, derived from semigroup instance above
    -- mappend is the same as <>
    -- mappend Nada Nada        = Nada
    -- mappend Nada (Only a)    = (Only a)
    -- mappend (Only a) Nada    = (Only a)
    -- mappend (Only a) (Only b)= Only (mappend a b)

-- tests for the (Optional a) monoid
prop_optional :: Int -> Int -> Bool
prop_optional x y =
       Only (Sum x) `mappend` Only (Sum y) == Only (Sum {getSum = (x+y)})
    && Only (Product x) `mappend` Only (Product y)
       == Only (Product {getProduct = x*y})
    && Only (Sum x) `mappend` Nada == Only (Sum {getSum = x})
    && Only [1..x] `mappend` Nada == Only [1..x]
    && Nada `mappend` Only (Sum x) == Only (Sum {getSum = x})

main :: IO ()
main = do quickCheck prop_optional