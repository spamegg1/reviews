--{-# LANGUAGE DeriveGeneric #-}
-- Chapter 15 Exercises
--import GHC.Generics
import Data.Monoid
import Data.Semigroup
import Test.QuickCheck


-- 1. Given datatype, implement Semigroup instance.
-- data declaration
data Trivial = Trivial deriving (Eq, Show)

-- semigroup instance
instance Semigroup Trivial where
    Trivial <> Trivial = Trivial

-- monoid instance
instance Monoid Trivial where
    mempty = Trivial

-- arbitrary instance
instance Arbitrary Trivial where arbitrary = return Trivial

-- test type to cast semigroupAssoc
type TrivAssoc = Trivial -> Trivial -> Trivial -> Bool

-- 2. -------------------------------------------------------------------------
-- data declaration
newtype Identity a = Identity a deriving (Eq, Show)

-- semigroup instance
instance (Semigroup a) => Semigroup (Identity a) where
    (Identity x) <> (Identity y) = Identity (x <> y)

-- monoid instance
instance Monoid a => Monoid (Identity a) where
    mempty = Identity mempty

-- arbitrary instance
instance Arbitrary a => Arbitrary (Identity a) where arbitrary = identityGen
identityGen :: Arbitrary a => Gen (Identity a)
identityGen = do
    a <- arbitrary
    return (Identity a)

-- test types to cast semigroupAssoc
type IdentityAssoc1 =
    Identity String -> Identity String -> Identity String -> Bool
type IdentityAssoc2 =
    Identity [Int] -> Identity [Int] -> Identity [Int] -> Bool

-- 3. -------------------------------------------------------------------------
-- (similar solution for types 4. (Three a b c) and 5. (Four a b c d))
-- data declaration
data Two a b = Two a b deriving (Eq, Show)

-- semigroup instance
instance (Semigroup a, Semigroup b) => Semigroup (Two a b) where
    (Two w x) <> (Two y z) = Two (w <> y) (x <> z)

-- monoid instance
instance (Monoid a, Monoid b) => Monoid (Two a b) where
    mempty = Two mempty mempty

-- arbitrary instance
instance (Arbitrary a, Arbitrary b) => Arbitrary (Two a b)
    where arbitrary = twoGen
twoGen :: (Arbitrary a, Arbitrary b) => Gen (Two a b)
twoGen = do
    a <- arbitrary
    b <- arbitrary
    return (Two a b)

-- test type to cast semigroupAssoc
type TwoAssoc =
    (Two String [Int]) -> (Two String [Int]) -> (Two String [Int]) -> Bool

-- 6. -------------------------------------------------------------------------
-- data declaration
newtype BoolConj = BoolConj Bool deriving (Eq, Show)

-- semigroup instance
instance Semigroup BoolConj where
    (BoolConj True) <> (BoolConj True) = (BoolConj True)
    (BoolConj True) <> (BoolConj False) = (BoolConj False)
    (BoolConj False) <> (BoolConj True) = (BoolConj False)
    (BoolConj False) <> (BoolConj False) = (BoolConj False)

-- monoid instance
instance Monoid BoolConj where
    mempty = BoolConj True

-- arbitrary instance
instance Arbitrary BoolConj where arbitrary = boolconjGen
boolconjGen :: Gen BoolConj
boolconjGen = do
    a <- arbitrary
    return (BoolConj a)

-- test type to cast semigroupAssoc
type BoolConjAssoc =
    (BoolConj) -> (BoolConj) -> (BoolConj) -> Bool

-- 7. -------------------------------------------------------------------------
-- data declaration
newtype BoolDisj = BoolDisj Bool deriving (Eq, Show)

-- semigroup instance
instance Semigroup BoolDisj where
    (BoolDisj True) <> (BoolDisj True) = (BoolDisj True)
    (BoolDisj True) <> (BoolDisj False) = (BoolDisj True)
    (BoolDisj False) <> (BoolDisj True) = (BoolDisj True)
    (BoolDisj False) <> (BoolDisj False) = (BoolDisj False)

-- monoid instance
instance Monoid BoolDisj where
    mempty = BoolDisj False

-- arbitrary instance
instance Arbitrary BoolDisj where arbitrary = booldisjGen
booldisjGen :: Gen BoolDisj
booldisjGen = do
    a <- arbitrary
    return (BoolDisj a)

-- test type to cast semigroupAssoc
type BoolDisjAssoc =
    (BoolDisj) -> (BoolDisj) -> (BoolDisj) -> Bool

-- 8. -------------------------------------------------------------------------
-- data declaration
data Or a b = Fst a | Snd b deriving (Eq, Show)

-- semigroup instance
instance Semigroup (Or a b) where
    (Fst a) <> (Fst b) = (Fst b)
    (Fst a) <> (Snd b) = (Snd b)
    (Snd a) <> (Fst b) = (Snd a)
    (Snd a) <> (Snd b) = (Snd a)

-- arbitrary instance
instance (Arbitrary a, Arbitrary b) => Arbitrary (Or a b)
    where arbitrary = orGen
orGen :: (Arbitrary a, Arbitrary b) => Gen (Or a b)
orGen = do
    a <- arbitrary
    b <- arbitrary
    elements[Fst a, Snd b]

-- test type to cast semigroupAssoc
type OrAssoc1 =
    (Or String Int) -> (Or String Int) -> (Or String Int) -> Bool
type OrAssoc2 =
    (Or Bool (Maybe Int)) -> (Or Bool (Maybe Int)) ->
    (Or Bool (Maybe Int)) -> Bool

-- 9. -------------------------------------------------------------------------
-- data declaration
newtype Combine a b = Combine { unCombine :: (a -> b) }

-- Show instance (just a fake one, impossible to show functions in general)
instance Show (Combine a b) where
    show Combine {unCombine = f} = "Combine"

-- Eq instance (kinda fake, f==g if they agree on 20 int points)
instance (Enum a, Num a, Eq b) => Eq (Combine a b) where
    Combine {unCombine = f} == Combine {unCombine = g} =
        (all (\x -> (f x == g x)) [-10..10])

-- semigroup instance
instance Semigroup b => Semigroup (Combine a b) where
    Combine f <> Combine g = Combine(\x -> ((f x)<>(g x)))

-- monoid instance
instance Monoid b => Monoid (Combine a b) where
    mempty = Combine (\x -> mempty)

-- how to generate arbitrary functions a -> b with Show instance, no Eq
-- funGen :: (Function a, CoArbitrary a, Arbitrary b) => Gen(Fun a b)
-- funGen = arbitrary

-- arbitrary instance, no Show or Eq instances, quickCheck complains
instance (CoArbitrary a, Arbitrary b) => Arbitrary(Combine a b)
    where arbitrary = combineGen
combineGen :: (CoArbitrary a, Arbitrary b) => Gen(Combine a b)
combineGen = do
    f <- arbitrary
    return Combine { unCombine = f}

-- test type to cast semigroupAssoc
type CombineAssoc =
    (Combine Int [Int]) -> (Combine Int [Int]) -> (Combine Int [Int]) -> Bool

-- 10. ------------------------------------------------------------------------
-- data declaration
newtype Comp a = Comp { unComp :: (a -> a) }

-- Show instance (just a fake one, impossible to show functions in general)
instance Show (Comp a) where
    show Comp {unComp = f} = "Comp"

-- Eq instance (kinda fake, f==g if they agree on 20 int points)
instance (Enum a, Num a, Eq a) => Eq (Comp a) where
    Comp {unComp = f} == Comp {unComp = g} =
        (all (\x -> (f x == g x)) [-10..10])

-- semigroup instance
instance Semigroup (Comp a) where
    Comp f <> Comp g = Comp(f . g)

-- monoid instance
instance Monoid (Comp a) where
    mempty = Comp (\x -> x)

-- how to generate arbitrary functions a -> a with Show instance, no Eq
-- funGen :: (Function a, CoArbitrary a, Arbitrary a) => Gen(Fun a a)
-- funGen = arbitrary

-- arbitrary instance, no Show or Eq instance, quickCheck complains
instance (CoArbitrary a, Arbitrary a) => Arbitrary(Comp a)
    where arbitrary = compGen
compGen :: (CoArbitrary a, Arbitrary a) => Gen(Comp a)
compGen = do
    f <- arbitrary
    return Comp { unComp = f}

-- test type to cast semigroupAssoc
type CompAssoc =
    (Comp Int) -> (Comp Int) -> (Comp Int) -> Bool
-- 11. ------------------------------------------------------------------------
-- data declaration (changed names b/c of conflict with Test.QuickCheck)
data Validation a b = Failre a | Sccess b deriving (Eq, Show)

-- semigroup instance
instance Semigroup a => Semigroup (Validation a b) where
    Failre x <> Failre y = Failre (x <> y)
    Failre x <> Sccess y = Sccess y
    Sccess x <> Failre y = Sccess x
    Sccess x <> Sccess y = Sccess x

-- arbitrary instance
instance (Arbitrary a, Arbitrary b) => Arbitrary (Validation a b)
    where arbitrary = validationGen
validationGen :: (Arbitrary a, Arbitrary b) => Gen (Validation a b)
validationGen = do
    a <- arbitrary
    b <- arbitrary
    elements[Failre a, Sccess b]

-- test type to cast semigroupAssoc
type ValidationAssoc =
    (Validation String Int) -> (Validation String Int)
        -> (Validation String Int) -> Bool
-- tests ----------------------------------------------------------------------
-- Semigroup Associativity test
semigroupAssoc :: (Eq m, Semigroup m) => m -> m -> m -> Bool
semigroupAssoc a b c = (a <> (b <> c)) == ((a <> b) <> c)

-- Monoid Identity tests
monoidLeftIdentity :: (Eq m, Monoid m) => m -> Bool
monoidLeftIdentity a = (mempty <> a) == a

monoidRightIdentity :: (Eq m, Monoid m) => m -> Bool
monoidRightIdentity a = (a <> mempty) == a

main :: IO ()
main = do
    quickCheck (semigroupAssoc :: TrivAssoc)
    quickCheck (semigroupAssoc :: IdentityAssoc1)
    quickCheck (semigroupAssoc :: IdentityAssoc2)
    quickCheck (semigroupAssoc :: TwoAssoc)
    quickCheck (semigroupAssoc :: BoolConjAssoc)
    quickCheck (semigroupAssoc :: BoolDisjAssoc)
    quickCheck (semigroupAssoc :: OrAssoc1)
    quickCheck (semigroupAssoc :: OrAssoc2)
    quickCheck (semigroupAssoc :: CombineAssoc)
    quickCheck (semigroupAssoc :: CompAssoc)
    quickCheck (semigroupAssoc :: ValidationAssoc)

    quickCheck(monoidLeftIdentity :: Trivial -> Bool)
    quickCheck(monoidLeftIdentity :: Identity String -> Bool)
    quickCheck(monoidLeftIdentity :: Identity [Int] -> Bool)
    quickCheck(monoidLeftIdentity :: Two String [Int] -> Bool)
    quickCheck(monoidLeftIdentity :: BoolConj -> Bool)
    quickCheck(monoidLeftIdentity :: BoolDisj -> Bool)
    quickCheck(monoidLeftIdentity :: (Combine Int [Int]) -> Bool)
    quickCheck(monoidLeftIdentity :: Comp Int -> Bool)
    -- quickCheck(monoidLeftIdentity :: )
    -- quickCheck(monoidLeftIdentity :: )
    -- quickCheck(monoidLeftIdentity :: )

    quickCheck(monoidRightIdentity :: Trivial -> Bool)
    quickCheck(monoidRightIdentity :: Identity String -> Bool)
    quickCheck(monoidRightIdentity :: Identity [Int] -> Bool)
    quickCheck(monoidRightIdentity :: Two String [Int] -> Bool)
    quickCheck(monoidRightIdentity :: BoolConj -> Bool)
    quickCheck(monoidRightIdentity :: BoolDisj -> Bool)
    quickCheck(monoidRightIdentity :: (Combine Int [Int]) -> Bool)
    quickCheck(monoidRightIdentity :: Comp Int -> Bool)
    -- quickCheck(monoidRightIdentity :: )
    -- quickCheck(monoidRightIdentity :: )
    -- quickCheck(monoidRightIdentity :: )
