import Test.QuickCheck


-- f ~ Identity
-- Applicative f =>
-- type Id = Identity
-- (<*>) :: f (a -> b) -> f a -> f b
-- (<*>) :: Id (a -> b) -> Id a -> Id b
-- pure :: a -> f a
-- pure :: a -> Id a

newtype Identity a = Identity a deriving (Ord, Show, Eq)

instance Functor Identity where
    fmap f (Identity a) = Identity (f a)

instance Applicative Identity where
    pure a = Identity a
    (<*>) (Identity f) (Identity a) = Identity (f a)


main :: IO()
main = do
    quickCheck( (const <$> [1,2,3] <*> [9,9,9]) == [1,1,1,2,2,2,3,3,3] )
    quickCheck( (const <$> (Identity [1,2,3]) <*> (Identity [9,9,9]))
                    == Identity[1,2,3] )
