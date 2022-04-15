module EitherLib where
-- Small library for Either
-- Write each of the following functions. If more than one possible unique
-- function exists for the type, use common sense to
-- determine what it should do.
-- 1. Try to eventually arrive at a solution that uses foldr, even
-- if earlier versions don’t use foldr.
leftpicker :: (Either a b) -> [Either a b] -> [Either a b]
leftpicker (Left x) xs = x:xs
leftpicker (Right x) xs = xs
lefts' :: [Either a b] -> [a]
lefts' = foldr leftpicker []
-- 2. Same as the last one. Use foldr eventually
rightpicker :: (Either a b) -> [Either a b] -> [Either a b]
rightpicker (Left x) xs = xs
rightpicker (Right x) xs = x:xs
rights' :: [Either a b] -> [b]
rights' = foldr rightpicker []
-- 3.
partitionEithers' :: [Either a b] -> ([a], [b])
partitionEithers' lst = (lefts' lst, rights' lst)
-- 4.
-- >>> eitherMaybe' (+1) (Left "hello")
-- Nothing
-- >>> eitherMaybe' (+1) (Right 1)
-- Just 2
eitherMaybe' :: (b -> c) -> Either a b -> Maybe c
eitherMaybe' _ (Left _) = Nothing
eitherMaybe' btoc (Right b) = Just (btoc b)
-- 5. This is a general catamorphism for Either values.
-- >>> either' (+1) (*5) (Left 2)
-- 3
-- >>> either' (+1) (*5) (Right 2)
-- 10
either' :: (a -> c) -> (b -> c) -> Either a b -> c
either' atoc _ (Left a) = atoc a
either' _ btoc (Right b) = btoc b
-- 6. Same as before, but use the either' function you just wrote.
eitherMaybe'' :: (b -> c) -> Either a b -> Maybe c
eitherMaybe'' btoc = either' (\a -> Nothing) (Just . btoc)