module Assoc where

import Data.Monoid
import Test.QuickCheck

monoidAssoc :: (Eq m, Monoid m) => m -> m -> m -> Bool
monoidAssoc a b c = (a <> (b <> c)) == ((a <> b) <> c)

monoidLeftIdentity :: (Eq m, Monoid m) => m -> Bool
monoidLeftIdentity a = (mempty <> a) == a

monoidRightIdentity :: (Eq m, Monoid m) => m -> Bool
monoidRightIdentity a = (a <> mempty) == a

main :: IO ()
main = do
    quickCheck (monoidAssoc :: String -> String -> String -> Bool)
    quickCheck (monoidLeftIdentity :: String -> Bool)
    quickCheck (monoidRightIdentity :: String -> Bool)
