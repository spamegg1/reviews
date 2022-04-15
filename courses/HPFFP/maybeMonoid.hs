--{-# LANGUAGE FlexibleInstances #-}
--{-# LANGUAGE FlexibleContexts #-}
--{-# LANGUAGE UndecidableInstances #-}
import Data.Monoid
import Test.QuickCheck

-- (Optional a)
data Optional a = Nada | Only a deriving (Eq, Show)

-- Semigroup (Optional a)
instance (Semigroup a) => Semigroup (Optional a) where
    Nada <> Nada = Nada
    Nada <> (Only a) = Only a
    (Only a) <> Nada = Only a
    (Only a) <> (Only b) = Only (a <> b)

-- Monoid (Optional a)
instance (Semigroup a) => Monoid (Optional a) where
    mempty = Nada

-- Arbitrary instance for (Optional a)
instance Arbitrary a => Arbitrary (Optional a) where arbitrary = optionalGen
optionalGen :: Arbitrary a => Gen (Optional a)
optionalGen = do
    a <- arbitrary
    elements [Nada, (Only a)]
------------------------------------------------------
-- (First' a)
newtype First' a = First' {getFirst' :: Optional a} deriving (Eq, Show)

-- Semigroup (First' a)
instance (Semigroup a) => Semigroup (First' a) where
    First' x <> First' y = First' (x <> y)

-- Monoid (First' a)
instance (Semigroup a) => Monoid (First' a) where
    mempty = First' {getFirst' = Nada}
    -- mappend not required, inherits from Semigroup

firstMappend :: (Semigroup a) => First' a -> First' a -> First' a
firstMappend = mappend

-- Arbitrary instance for (First' a)
instance Arbitrary a => Arbitrary (First' a) where arbitrary = firstaGen
firstaGen :: Arbitrary a => Gen (First' a)
firstaGen = do
    a <- arbitrary
    return (First' a)
--------------------------------------------------------
-- tests
monoidAssoc :: (Eq m, Monoid m) => m -> m -> m -> Bool
monoidAssoc a b c = (a <> (b <> c)) == ((a <> b) <> c)

monoidLeftIdentity :: (Eq m, Monoid m) => m -> Bool
monoidLeftIdentity a = (mempty <> a) == a

monoidRightIdentity :: (Eq m, Monoid m) => m -> Bool
monoidRightIdentity a = (a <> mempty) == a

type FirstMappend = First' String -> First' String -> First' String -> Bool
type FstId = First' String -> Bool

main :: IO ()
main = do
    quickCheck (monoidAssoc :: FirstMappend)
    quickCheck (monoidLeftIdentity :: FstId)
    quickCheck (monoidRightIdentity :: FstId)