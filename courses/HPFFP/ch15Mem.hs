--{-# LANGUAGE DeriveGeneric #-}
-- Chapter 15 Last Exercise (Mem)
-- import GHC.Generics
import Data.Monoid
import Data.Semigroup
import Test.QuickCheck

-- data definition
newtype Mem s a = Mem {runMem :: s -> (a, s)}

-- Show instance (just a fake one, impossible to show functions in general)
instance Show (Mem s a) where
    show Mem {runMem = f} = "Mem"

-- Eq instance (kinda fake, f==g if they agree on 20 int points)
instance (Enum s, Num s, Eq s, Eq a) => Eq (Mem s a) where
    Mem {runMem = f} == Mem {runMem = g} =
        (all (\x -> (f x == g x)) [-10..10])

-- semigroup instance
instance (Semigroup a) => Semigroup (Mem s a) where
    Mem x <> Mem y = Mem $ \s -> (fst(x s) <> fst(y s), snd(y (snd(x s))))

-- monoid instance
instance (Monoid a) => Monoid (Mem s a) where
    mempty = Mem $ \s -> (mempty, s)

-- arbitrary instance
instance (CoArbitrary s, Arbitrary a, Arbitrary s) => Arbitrary(Mem s a)
    where arbitrary = memGen
memGen :: (CoArbitrary s, Arbitrary a, Arbitrary s) => Gen(Mem s a)
memGen = do
    f <- arbitrary
    return Mem {runMem = f}


-- test type to cast semigroupAssoc
type MemAssoc =
    (Mem Int String) -> (Mem Int String) -> (Mem Int String) -> Bool

-- tests
-- Semigroup Associativity test
semigroupAssoc :: (Eq m, Semigroup m) => m -> m -> m -> Bool
semigroupAssoc a b c = (a <> (b <> c)) == ((a <> b) <> c)

-- Monoid Identity tests
monoidLeftIdentity :: (Eq m, Monoid m) => m -> Bool
monoidLeftIdentity a = (mempty <> a) == a

monoidRightIdentity :: (Eq m, Monoid m) => m -> Bool
monoidRightIdentity a = (a <> mempty) == a

-- main
main = do
    let f' :: (Mem Int String)
        f' = Mem $ \s -> ("hi", s + 1)
        rmzero = runMem mempty 0
        rmleft = runMem (f' <> mempty) 0
        rmright = runMem (mempty <> f') 0
    print $ rmleft
    print $ rmright
    print $ (rmzero :: (String, Int))
    print $ rmleft == runMem f' 0
    print $ rmright == runMem f' 0

    quickCheck (semigroupAssoc :: MemAssoc)
    quickCheck (monoidLeftIdentity :: (Mem Int String) -> Bool)
    quickCheck (monoidRightIdentity :: (Mem Int String) -> Bool)
