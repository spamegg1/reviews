{-# LANGUAGE RankNTypes #-}
-- changes structure without changing content
type Nat f g = forall a . f a -> g a

-- This'll work
-- here the structure is Nothing/Just, the content is "a"
-- structure changes to [ ], "a" remains the same
maybeToList :: Nat Maybe []
maybeToList Nothing = []
maybeToList (Just a) = [a]