{-# LANGUAGE ViewPatterns #-}
module FunctorExercises where
import Test.QuickCheck
import Test.QuickCheck.Function

-- Add fmap, parentheses, and function composition to the expression
-- as needed for the expression to typecheck and produce the expected result.

-- 1.
a = fmap (+1) $ read "[1]" :: [Int]
-- Prelude> a
-- [2]

-- 2.
b = (fmap.fmap) (++ "lol") (Just ["Hi,", "Hello"])
-- Prelude> b
-- Just ["Hi,lol","Hellolol"]

-- 3.
c = fmap (*2) (\x -> x - 2)
-- c 1 = -2, c 2 = 0, c 3 = 2, c 4 = 4 etc.

-- 4.
d = fmap ((return '1' ++) . show) (\x -> [x, 1..3])
-- Prelude> d 0
-- "1[0,1,2,3]"

-- 5.
e :: IO Integer
e = let ioi = readIO "1" :: IO Integer
        changed = fmap (read . ("123"++) . show) ioi
    in fmap (*3) changed
-- Prelude> e
-- 3693
-------------------------------------------------------------------------------
-- Implement Functor instances for the following datatypes. Use
-- the QuickCheck properties we showed you to validate them.

-- fmap :: Functor f => (a -> b) -> f a -> f b
-- FUNCTOR LAWS
-- fmap id == id
-- fmap (f . g) == fmap f . fmap g

-- 1.--------------------------------------------------------------------------
newtype Identity a = Identity a deriving (Eq, Show)

-- Functor instance
instance Functor Identity where
    fmap f (Identity a) = Identity (f a)

-- Arbitrary instance
instance Arbitrary a => Arbitrary (Identity a) where arbitrary = identityGen
identityGen :: Arbitrary a => Gen(Identity a)
identityGen = do
    x <- arbitrary
    return (Identity x)

-- Types/functions for functorIdentity, functorCompose and functorCompose'
type Identity_id = (Identity Int) -> Bool
prop_Identity_compose :: (Identity Int) -> Bool
prop_Identity_compose x = functorCompose (+1) (*2) x
type Identity_compose' =
    (Identity Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool

-- 2.--------------------------------------------------------------------------
data Pair a = Pair a a deriving (Eq, Show)

-- Functor instance
instance Functor Pair where
    fmap f (Pair x y) = Pair (f x) (f y)

-- Arbitrary instance
instance Arbitrary a => Arbitrary (Pair a) where arbitrary = pairGen
pairGen :: Arbitrary a => Gen(Pair a)
pairGen = do
    x <- arbitrary
    return (Pair x x)

-- Type for QuickCheck
type Pair_id = (Pair Int) -> Bool
prop_Pair_compose :: (Pair Int) -> Bool
prop_Pair_compose x = functorCompose (+1) (*2) x
type Pair_compose' =
    (Pair Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool

-- 3.--------------------------------------------------------------------------
data Two a b = Two a b deriving (Eq, Show)

-- Functor instance
instance Functor (Two a) where
    fmap f (Two a b) = Two a (f b)

-- Arbitrary instance
instance (Arbitrary a, Arbitrary b) => Arbitrary(Two a b) where arbitrary = twoGen
twoGen :: (Arbitrary a, Arbitrary b) => Gen(Two a b)
twoGen = do
    x <- arbitrary
    y <- arbitrary
    return (Two x y)

-- Type for QuickCheck
type Two_id = (Two Int Int) -> Bool
prop_Two_compose :: (Two Int Int) -> Bool
prop_Two_compose x = functorCompose (+1) (*2) x
type Two_compose' =
    (Two Int Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool
-- 4.--------------------------------------------------------------------------
data Three a b c = Three a b c deriving (Eq, Show)

-- Functor instance
instance Functor (Three a b) where
    fmap f (Three a b c) = Three a b (f c)

-- Arbitrary instance
instance (Arbitrary a, Arbitrary b, Arbitrary c)
    => Arbitrary(Three a b c) where arbitrary = threeGen
threeGen :: (Arbitrary a, Arbitrary b, Arbitrary c) => Gen(Three a b c)
threeGen = do
    x <- arbitrary
    y <- arbitrary
    z <- arbitrary
    return (Three x y z)
-- Type for QuickCheck
type Three_id = (Three Int Int Int) -> Bool
prop_Three_compose :: (Three Int Int Int) -> Bool
prop_Three_compose x = functorCompose (+1) (*2) x
type Three_compose' =
    (Three Int Int Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool
-- 5.--------------------------------------------------------------------------
data Three' a b = Three' a b b deriving (Eq, Show)

-- Functor instance
instance Functor (Three' a) where
    fmap f (Three' x y z) = Three' x (f y) (f z)

-- Arbitrary instance
instance (Arbitrary a, Arbitrary b) => Arbitrary(Three' a b)
    where arbitrary = three'Gen
three'Gen :: (Arbitrary a, Arbitrary b) => Gen(Three' a b)
three'Gen = do
    x <- arbitrary
    y <- arbitrary
    return (Three' x y y)

-- Type for QuickCheck
type Three'_id = (Three' Int Int) -> Bool
prop_Three'_compose :: (Three' Int Int) -> Bool
prop_Three'_compose x = functorCompose (+1) (*2) x
type Three'_compose' =
    (Three' Int Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool

-- 6.--------------------------------------------------------------------------
data Four a b c d = Four a b c d deriving (Eq, Show)

-- Functor instance
instance Functor (Four a b c) where
    fmap f (Four x y z w) = Four x y z (f w)

-- Arbitrary instance
instance (Arbitrary a, Arbitrary b, Arbitrary c, Arbitrary d) =>
    Arbitrary(Four a b c d) where arbitrary = fourGen
fourGen :: (Arbitrary a, Arbitrary b, Arbitrary c, Arbitrary d) => Gen(Four a b c d)
fourGen = do
    x <- arbitrary
    y <- arbitrary
    z <- arbitrary
    w <- arbitrary
    return (Four x y z w)

-- Type for QuickCheck
type Four_id = (Four Int Int Int Int) -> Bool
prop_Four_compose :: (Four Int Int Int Int) -> Bool
prop_Four_compose x = functorCompose (+1) (*2) x
type Four_compose' =
    (Four Int Int Int Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool
-- 7.--------------------------------------------------------------------------
data Four' a b = Four' a a a b deriving (Eq, Show)

-- Functor instance
instance Functor (Four' a) where
    fmap f (Four' x y z w) = Four' x y z (f w)

-- Arbitrary instance
instance (Arbitrary a, Arbitrary b) => Arbitrary(Four' a b)
    where arbitrary = four'Gen
four'Gen :: (Arbitrary a, Arbitrary b) => Gen(Four' a b)
four'Gen = do
    x <- arbitrary
    y <- arbitrary
    return (Four' x x x y)

-- Type for QuickCheck
type Four'_id = (Four' Int Int) -> Bool
prop_Four'_compose :: (Four' Int Int) -> Bool
prop_Four'_compose x = functorCompose (+1) (*2) x
type Four'_compose' =
    (Four' Int Int) -> (Fun Int Int) -> (Fun Int Int) -> Bool

-------------------------------------------------------------------------------
-- QuickChecking FUNCTOR LAWS
functorIdentity :: (Functor f, Eq (f a)) => f a -> Bool
functorIdentity f = fmap id f == f

-- needs manual provision of the first two functions (a -> b) and (b -> c)
functorCompose :: (Eq (f c), Functor f) => (a -> b) -> (b -> c) -> f a -> Bool
functorCompose f g x = (fmap g (fmap f x)) == (fmap (g . f) x)

-- making QuickCheck generate the functions too
-- makes use of pattern matching on the Fun value we ask QuickCheck to generate
-- so LANGUAGE ViewPatterns is needed at the top of the file
functorCompose' :: (Eq (f c), Functor f) => f a -> Fun a b -> Fun b c -> Bool
functorCompose' x (Fun _ f) (Fun _ g) =
    (fmap (g . f) x) == (fmap g . fmap f $ x)

-------------------------------------------------------------------------------
-- MAIN --
main :: IO ()
main = do
    quickCheck (functorIdentity :: Identity_id)
    quickCheck prop_Identity_compose
    quickCheck (functorCompose' :: Identity_compose')

    quickCheck (functorIdentity :: Pair_id)
    quickCheck prop_Pair_compose
    quickCheck (functorCompose' :: Pair_compose')

    quickCheck (functorIdentity :: Two_id)
    quickCheck prop_Two_compose
    quickCheck (functorCompose' :: Two_compose')

    quickCheck (functorIdentity :: Three_id)
    quickCheck prop_Three_compose
    quickCheck (functorCompose' :: Three_compose')

    quickCheck (functorIdentity :: Three'_id)
    quickCheck prop_Three'_compose
    quickCheck (functorCompose' :: Three'_compose')

    quickCheck (functorIdentity :: Four_id)
    quickCheck prop_Four_compose
    quickCheck (functorCompose' :: Four_compose')

    quickCheck (functorIdentity :: Four'_id)
    quickCheck prop_Four'_compose
    quickCheck (functorCompose' :: Four'_compose')