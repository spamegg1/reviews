import Data.Monoid
import Test.QuickCheck

-- - f ~ Constant e
-- type C = Constant
-- (<*>) :: f (a -> b) -> f a -> f b
-- (<*>) :: C e (a -> b) -> C e a -> C e b
-- pure :: a -> f a
-- pure :: a -> C e a

-- Prelude> let f = Constant (Sum 1)
-- Prelude> let g = Constant (Sum 2)
-- Prelude> f <*> g
-- Constant {getConstant = Sum {getSum = 3}
-- Prelude> Constant undefined <*> g
-- Constant (Sum {getSum =
-- *** Exception: Prelude.undefined
-- Prelude> pure 1
-- 1
-- Prelude> pure 1 :: Constant String Int
-- Constant {getConstant = ""}

newtype Constant a b = Constant { getConstant :: a } deriving (Eq, Ord, Show)
instance Functor (Constant a) where
    fmap _ Constant { getConstant = a } = Constant { getConstant = a }
instance Monoid a => Applicative (Constant a) where
    pure _ = Constant { getConstant = mempty }
    (Constant f) <*> (Constant g) = Constant (f <> g)


-- main
main :: IO()
main = do
    let f = Constant (Sum 1) :: Constant (Sum Int) (Int->String)
        g = Constant (Sum 2) :: Constant (Sum Int) Int
    quickCheck ( 1==1 )
    quickCheck ( (f <*> g) == Constant {getConstant = Sum {getSum = 3}} )
    quickCheck ( (pure 1 :: Constant String Int) == Constant {getConstant = ""} )
