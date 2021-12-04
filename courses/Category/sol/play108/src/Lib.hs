module Lib (
  Pair(..), K2(..)
) where

import Data.Bifunctor

data Pair a b = Pair a b
    deriving Show

-- A correct implementation of bimap
-- instance Bifunctor Pair where
--     bimap f g (Pair x y) = Pair (f x) (g y)

-- Bimap only implementing first and not second
instance Bifunctor Pair where
    first f (Pair x y) = Pair (f x) y

-- The compiler flag -Wmissing-methods results in an error being printed:
-- • No explicit implementation for
-- either ‘bimap’ or ‘second’
-- • In the instance declaration for ‘Bifunctor Pair’
-- |
-- 11 | instance Bifunctor Pair where
-- |          ^^^^^^^^^^^^^^
-- This shows that Haskell is smart enough to find missing implementations of methods.

-- This is only a warning an Haskell still compiles.
-- When the program is run we get the following output:
-- play108-exe: <<loop>>

data K2 c a b = K2 c

instance Bifunctor (K2 c) where
    bimap _ _ (K2 c) = K2 c

data Fst a b = Fst a

instance Bifunctor Fst where
    bimap f _ (Fst a) = Fst (f a)

data Snd a b = Snd b

instance Bifunctor Snd where
    bimap _ g (Snd b) = Snd (g b)