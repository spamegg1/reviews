module Main where

import Test.QuickCheck
import Data.Monoid

newtype Combine a b = Combine { unCombine :: a -> b }

instance (Semigroup b) => Semigroup (Combine a b) where
    --a <> _ = a
    (Combine f) <> (Combine g) = Combine $ \a -> (f a) <> (g a)

instance (Monoid b) => Monoid (Combine a b) where
  mempty = Combine $ \_ -> mempty

monoidLeftIdentity :: (Eq m, Monoid m) => m -> Bool
monoidLeftIdentity m = mappend mempty m == m

monoidRightIdentity :: (Eq m, Monoid m) => m -> Bool
monoidRightIdentity m = mappend m mempty == m

monoidLeftIdentityF :: (Eq b, Monoid m) =>
    (Fun a b -> m) -> (m -> a -> b) -> a -> Fun a b -> Bool
monoidLeftIdentityF wrap eval point candidate =
    eval (mappend mempty m) point == eval m point
        where m = wrap candidate

monoidRightIdentityF :: (Eq b, Monoid m) =>
    (Fun a b -> m) -> (m -> a -> b) -> a -> Fun a b -> Bool
monoidRightIdentityF wrap eval point candidate =
    eval (mappend m mempty) point == eval m point
        where m = wrap candidate

main :: IO ()
main = do
    quickCheck $ (monoidLeftIdentityF (Combine . applyFun) unCombine
                    :: Int -> Fun Int (Sum Int) -> Bool)
    quickCheck $ (monoidRightIdentityF (Combine . applyFun) unCombine
                    :: Int -> Fun Int (Sum Int) -> Bool)
