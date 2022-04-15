{-# LANGUAGE ViewPatterns #-}
module Functors where
import Test.QuickCheck
import Test.QuickCheck.Function


-- fmap :: Functor f => (a -> b) -> f a -> f b

-- FUNCTOR LAWS
-- fmap id == id
-- fmap (f . g) == fmap f . fmap g

-- this won't work:
-- data FixMePls = FixMe | Pls deriving (Eq, Show)

-- instance Functor FixMePls where
    -- fmap = error "it doesn't matter, it won't compile"

-- this works now:
data FixMePls a = FixMe | Pls a deriving (Eq, Show)
instance Functor FixMePls where -- here, (FixMePls a) doesn't work!
    -- it will compile now!
    fmap _ FixMe = FixMe
    fmap f (Pls a) = Pls (f a)

-- examples
data WhoCares a = ItDoesnt | Matter a | WhatThisIsCalled deriving (Eq, Show)

instance Functor WhoCares where
    fmap _ ItDoesnt = ItDoesnt
    fmap _ WhatThisIsCalled = WhatThisIsCalled
    fmap f (Matter a) = Matter (f a)

-- breaks identity functor law
-- instance Functor WhoCares where
    -- fmap _ ItDoesnt = WhatThisIsCalled
    -- fmap f WhatThisIsCalled = ItDoesnt
    -- fmap f (Matter a) = Matter (f a)

data CountingBad a = Heisenberg Int a deriving (Eq, Show)
-- breaks composition functor law
-- instance Functor CountingBad where
    -- fmap f (Heisenberg n a) = Heisenberg (n+1) (f a)

-- making kinds match
data Two a b = Two a b deriving (Eq, Show) -- Two has kind * -> * -> *
-- doesn't work
-- instance Functor (Two a b) where
    -- fmap f (Two a b) = Two $ (f a) (f b)

-- this works, b/c of partial application
instance Functor (Two a) where
    fmap f (Two a b) = Two a (f b)


data Or a b = First a | Second b deriving (Eq, Show)
-- similarly this works
instance Functor (Or a) where
    fmap _ (First a) = First a
    fmap f (Second b) = Second (f b)


-- QuickChecking FUNCTOR LAWS
functorIdentity :: (Functor f, Eq (f a)) => f a -> Bool
functorIdentity f = fmap id f == f

prop_functorIdentity :: [Int] -> Bool
prop_functorIdentity f = functorIdentity f

functorCompose :: (Eq (f c), Functor f) => (a -> b) -> (b -> c) -> f a -> Bool
functorCompose f g x = (fmap g (fmap f x)) == (fmap (g . f) x)

prop_functorCompose :: [Int] -> Bool
prop_functorCompose x = functorCompose (+1) (*2) x


-- making QuickCheck generate the functions too
-- makes use of pattern matching on the Fun value we ask QuickCheck to generate
-- so LANGUAGE ViewPatterns is needed at the top of the file
functorCompose' :: (Eq (f c), Functor f) => f a -> Fun a b -> Fun b c -> Bool
functorCompose' x (Fun _ f) (Fun _ g) =
    (fmap (g . f) x) == (fmap g . fmap f $ x)

-- types needed to test functorCompose'
type IntToInt = Fun Int Int
type IntFC = [Int] -> IntToInt -> IntToInt -> Bool


main :: IO ()
main = do
    quickCheck prop_functorIdentity
    quickCheck prop_functorCompose
    quickCheck (functorCompose' :: IntFC)