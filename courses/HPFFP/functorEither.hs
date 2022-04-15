module FunctorEither where
import Test.QuickCheck
import Test.QuickCheck.Function


incIfRight :: Num a => Either e a -> Either e a
incIfRight (Right n) = Right $ n + 1
incIfRight (Left e) = Left e

showIfRight :: Show a => Either e a -> Either e String
showIfRight (Right s) = Right $ show s
showIfRight (Left e) = Left e

-- simplified using fmap and Either's Functor instance
incEither :: Num a => Either e a -> Either e a
incEither m = fmap (+1) m

showEither :: Show a => Either e a -> Either e String
showEither s = fmap show s

-- further simplified
incEither' :: Num a => Either e a -> Either e a
incEither' = fmap (+1)

showEither' :: Show a => Either e a -> Either e String
showEither' = fmap show

-- lifted versions that are more general, apply to more datatypes
liftedInc :: (Functor f, Num b) => f b -> f b
liftedInc = fmap (+1)

liftedShow :: (Functor f, Show a) => f a -> f String
liftedShow = fmap show

-- Exercise:
-- Write a Functor instance for a datatype identical to Either.
-- We’ll use our own datatype because Either has a Functor instance.
data Sum a b = First a | Second b deriving (Eq, Show)
instance Functor (Sum a) where
    fmap f (First a) = First a
    fmap f (Second b) = Second (f b)

-- Constant datatype and its functor instance
newtype Constant a b = Constant { getConstant :: a } deriving (Eq, Show)

instance Functor (Constant m) where
    fmap _ (Constant v) = Constant v

-- Main (testing)
main = do
    quickCheck (const 2 (getConstant (Constant 3)) == 2)
    quickCheck (fmap (const 2) (Constant 3) == Constant {getConstant = 3})
    quickCheck (getConstant (fmap (const 2) (Constant 3)) == 3)
    quickCheck (getConstant (fmap (const "Blah") (Constant 3)) == 3)
    quickCheck (getConstant (id (Constant 3))
                == getConstant (fmap id (Constant 3)))
    quickCheck (((const 3) . (const 5)) 10 == 3)
    quickCheck (((const 5) . (const 3)) 10 == 5)
    let fc = fmap (const 3)
    let fc' = fmap (const 5)
    let separate = fc . fc'
    let fused :: (Functor f, Num b1) => f b2 -> f b1
        fused = fmap (const 3 . const 5)
    let cw = Constant "WOOHOO"
    let cdr = Constant "Dogs rule"
    quickCheck (getConstant(separate (cw)) == "WOOHOO")
    quickCheck (getConstant(fused cdr) == "Dogs rule")