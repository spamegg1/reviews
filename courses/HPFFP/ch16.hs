{-# LANGUAGE FlexibleInstances #-}
module Chapter16 where
import Test.QuickCheck
import Test.QuickCheck.Function
import GHC.Arr

-- Determine if a valid Functor can be written for the datatype provided.
-- 1. data Bool = False | True -- NOT POSSIBLE
-- 2.
data BoolAndSomethingElse a = False' a | True' a
instance Functor BoolAndSomethingElse where
    fmap f (False' a) = False' (f a)
    fmap f (True' a) = True' (f a)
-- 3.
data BoolAndMaybeSomethingElse a = Falsish | Truish a
instance Functor BoolAndMaybeSomethingElse where
    fmap _ Falsish = Falsish
    fmap f (Truish a) = Truish (f a)
-- 4. Use the kinds to guide you on this one, don’t get too hung
-- up on the details.
-- newtype Mu f = InF { outF :: f (Mu f) } -- NOT POSSIBLE

-- 5. Again, follow the kinds and ignore the unfamiliar parts
-- data D = D (Array Word Word) Int Int -- NOT POSSIBLE
-------------------------------------------------------------------------------
-- Rearrange the arguments to the type constructor of the
-- datatype so the Functor instance works.
-- 1.
data Sum a b = First b | Second a deriving (Eq, Show)
instance Functor (Sum e) where
    fmap f (First a) = First (f a)
    fmap f (Second b) = Second b
-- 2.
data Company a b c = DeepBlue a b | Something c
instance Functor (Company e e') where
    fmap f (Something b) = Something (f b)
    fmap _ (DeepBlue a c) = DeepBlue a c
-- 3.
data More a b = L b a b | R a b a deriving (Eq, Show)
instance Functor (More x) where
    fmap f (L a b a') = L (f a) b (f a')
    fmap f (R b a b') = R b (f a) b'
-------------------------------------------------------------------------------
-- Write Functor instances for the following datatypes.
-- 1.
data Quant a b = Finance | Desk a | Bloor b
instance Functor (Quant a) where
    fmap _ Finance = Finance
    fmap _ (Desk a) = Desk a
    fmap f (Bloor b) = Bloor (f b)
-- 2. No, it’s not interesting by itself.
data J a b = J a
instance Functor (J a) where
    fmap _ (J a) = J a
-- 3.
newtype Flip f a b = Flip (f b a) deriving (Eq, Show)
newtype K a b = K a
-- should remind you of an instance you've written before
instance Functor (Flip K a) where
    fmap f (Flip (K a)) = Flip $ K (f a)
-- 4.
data EvilGoateeConst a b = GoatyConst b
instance Functor (EvilGoateeConst a) where
    fmap f (GoatyConst b) = GoatyConst (f b)
-- 5. Do you need something extra to make the instance work?
data LiftItOut f a = LiftItOut (f a)
-- Functor instance for f would look something like
-- fmap g (f a) = f (g a)
instance Functor f => Functor (LiftItOut f) where
    fmap g (LiftItOut fa) = LiftItOut (fmap g fa)
-- 6.
data Parappa f g a = DaWrappa (f a) (g a)
instance (Functor f, Functor g) => Functor (Parappa f g) where
    fmap h (DaWrappa fa ga) = DaWrappa (fmap h fa) (fmap h ga)
-- 7. Don’t ask for more typeclass instances than you need. You
-- can let GHC tell you what to do.
data IgnoreOne f g a b = IgnoringSomething (f a) (g b)
instance Functor g => Functor (IgnoreOne f g a) where
    fmap h (IgnoringSomething fa gb) = IgnoringSomething (fa) (fmap h gb)
-- 8.
data Notorious g o a t = Notorious (g o) (g a) (g t)
instance Functor g => Functor (Notorious g o a) where
    fmap h (Notorious go ga gt) = Notorious (go) (ga) (fmap h gt)
-- 9. You’ll need to use recursion.
data List a = Nil | Cons a (List a)
instance Functor List where
    fmap _ Nil = Nil
    fmap f (Cons a lista) = Cons (f a) (fmap f lista)
-- 10. A tree of goats forms a Goat-Lord, fearsome poly-creature.
data GoatLord a = NoGoat | OneGoat a
                | MoreGoats (GoatLord a) (GoatLord a) (GoatLord a)
instance Functor GoatLord where
    fmap _ NoGoat = NoGoat
    fmap f (OneGoat a) = OneGoat (f a)
    fmap f (MoreGoats x y z) = MoreGoats (fmap f x) (fmap f y) (fmap f z)
-- 11. You’ll use an extra functor for this one, although your solution might
-- do it monomorphically without using fmap.
-- Keep in mind that you will probably not be able to validate this one in
-- the usual manner. Do your best to make it work.
data TalkToMe a = Halt | Print String a | Read (String -> a)
instance Functor TalkToMe where
    fmap _ Halt = Halt
    fmap f (Print s a) = Print s (f a)
    fmap f (Read sa) = Read (f.sa)
