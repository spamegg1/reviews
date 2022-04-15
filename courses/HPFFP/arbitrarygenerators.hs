module Generators where
import Test.QuickCheck

-- trivial generator of values
trivialInt :: Gen Int
trivialInt = return 1

-- different means of generating values
oneThroughThree :: Gen Int
oneThroughThree = elements [1, 2, 3]

-- choose :: System.Random.Random a => (a, a) -> Gen a
-- elements :: [a] -> Gen a
genBool :: Gen Bool
genBool = choose (False, True)

genBool' :: Gen Bool
genBool' = elements [False, True]

genOrdering :: Gen Ordering
genOrdering = elements [LT, EQ, GT]

genChar :: Gen Char
genChar = elements ['a'..'z']

-- with these two below, you can use something like
-- sample' (genTuple :: Gen(Int, Float))
genTuple :: (Arbitrary a, Arbitrary b) => Gen (a, b)
genTuple = do
    a <- arbitrary
    b <- arbitrary
    return (a, b)

genThreeple :: (Arbitrary a, Arbitrary b, Arbitrary c) => Gen (a, b, c)
genThreeple = do
    a <- arbitrary
    b <- arbitrary
    c <- arbitrary
    return (a, b, c)

genEither :: (Arbitrary a, Arbitrary b) => Gen (Either a b)
genEither = do
    a <- arbitrary
    b <- arbitrary
    elements [Left a, Right b]

-- equal probability
genMaybe :: Arbitrary a => Gen (Maybe a)
genMaybe = do
    a <- arbitrary
    elements [Nothing, Just a]

-- What QuickCheck does so
-- you get more Just values
genMaybe' :: Arbitrary a => Gen (Maybe a)
genMaybe' = do
    a <- arbitrary
    -- frequency :: [(Int, Gen a)] -> Gen a
    frequency [ (1, return Nothing), (3, return (Just a))]

-- Chapter 14 Exercises
data Fool = Fulse | Frue deriving (Eq, Show)
-- equal probability for Frue and Fulse
foolGen1 :: Gen Fool
foolGen1 = elements[Frue, Fulse]
-- 2/3 Fulse, 1/3 Frue
foolGen2 :: Gen Fool
foolGen2 = frequency[(1, elements[Frue]), (2, elements[Fulse])]
