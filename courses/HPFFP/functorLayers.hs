module FunctorLayers where
import Test.QuickCheck
import Test.QuickCheck.Function

-- datatype with layers (notice data constructor has 1 argument only)
data Wrap f a = Wrap (f a) deriving (Eq, Show)

-- functor instance, requires Functor instance for f that is inside (Wrap f)
instance Functor f => Functor (Wrap f) where
    fmap g (Wrap fa) = Wrap (fmap g fa)

-- main
main :: IO()
main = do
    quickCheck (fmap (+1) (Wrap (Just 1)) == Wrap (Just 2))
    quickCheck (fmap (+1) (Wrap [1, 2, 3]) == Wrap [2, 3, 4])