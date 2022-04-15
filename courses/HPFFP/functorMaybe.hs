module FunctorMaybe where
import Test.QuickCheck

-- defining Maybe functions explicitly with pattern matching
incIfJust :: Num a => Maybe a -> Maybe a
incIfJust (Just n) = Just $ n + 1
incIfJust Nothing = Nothing

showIfJust :: Show a => Maybe a -> Maybe String
showIfJust (Just s) = Just $ show s
showIfJust Nothing = Nothing

-- defining the same functions with fmap instead
incMaybe :: Num a => Maybe a -> Maybe a
incMaybe m = fmap (+1) m

showMaybe :: Show a => Maybe a -> Maybe String
showMaybe s = fmap show s

-- defining the same functions with fmap, w/o naming the arguments
incMaybe' :: Num a => Maybe a -> Maybe a
incMaybe' = fmap (+1)

showMaybe' :: Show a => Maybe a -> Maybe String
showMaybe' = fmap show

-- even more general, for any datatype with a Functor instance
liftedInc :: (Functor f, Num b) => f b -> f b
liftedInc = fmap (+1)

liftedShow :: (Functor f, Show a) => f a -> f String
liftedShow = fmap show

-- Exercise: write Functor instance for datatype identical to Maybe
data Possibly a = LolNope | Yeppers a deriving (Eq, Show)

instance Functor Possibly where
    fmap f (LolNope) = LolNope
    fmap f (Yeppers a) = Yeppers (f a)

-- main (testing)
main :: IO()
main = do
    quickCheck (incIfJust(Just 1) == incMaybe(Just 1))
    quickCheck (incIfJust Nothing == incMaybe Nothing)
    quickCheck (showIfJust(Just 9001) == showMaybe(Just 9001))
    quickCheck (showIfJust (Nothing :: Maybe Int)
              == showMaybe (Nothing :: Maybe Int))

    quickCheck (incIfJust(Just 1) == incMaybe'(Just 1))
    quickCheck (incIfJust Nothing == incMaybe' Nothing)
    quickCheck (showIfJust(Just 9001) == showMaybe'(Just 9001))
    quickCheck (showIfJust (Nothing :: Maybe Int)
              == showMaybe' (Nothing :: Maybe Int))

    quickCheck (incIfJust(Just 1) == liftedInc(Just 1))
    quickCheck (incIfJust Nothing == liftedInc Nothing)
    quickCheck (showIfJust(Just 9001) == liftedShow(Just 9001))
    quickCheck (showIfJust (Nothing :: Maybe Int)
              == liftedShow (Nothing :: Maybe Int))

    -- lifted versions are more reusable:
    quickCheck (liftedInc [1..5] == [2,3,4,5,6])
    quickCheck (liftedShow [1..5] == ["1", "2", "3", "4", "5"])