module MaybeLib where
-- Small library for Maybe
-- Write the following functions. This may take some time.
-- 1. Simple boolean checks for Maybe values.
-- >>> isJust (Just 1)
-- True
-- >>> isJust Nothing
-- False
isJust :: Maybe a -> Bool
isJust Nothing = False
isJust (Just _) = True
-- >>> isNothing (Just 1)
-- False
-- >>> isNothing Nothing
-- True
isNothing :: Maybe a -> Bool
isNothing (Just _) = False
isNothing Nothing =     True
-- 2. The following is the Maybe catamorphism. You can turn a
-- Maybe value into anything else with this.
-- >>> mayybee 0 (+1) Nothing
-- 0
-- >>> mayybee 0 (+1) (Just 1)
-- 2
mayybee :: b -> (a -> b) -> Maybe a -> b
mayybee base _ Nothing = base
mayybee _ f (Just a) = f a
-- In case you just want to provide a fallback value.
-- Try writing it in terms
-- of the maybe catamorphism
-- >>> fromMaybe 0 Nothing
-- 0
-- >>> fromMaybe 0 (Just 1)
-- 1
fromMaybe :: a -> Maybe a -> a
fromMaybe base x = mayybee base (\y -> y) x
-- Converting between List and Maybe.
-- >>> listToMaybe [1, 2, 3]
-- Just 1
-- >>> listToMaybe []
-- Nothing
listToMaybe :: [a] -> Maybe a
listToMaybe [] = Nothing
listToMaybe (x:_) = Just x
-- >>> maybeToList (Just 1)
-- [1]
-- >>> maybeToList Nothing
-- []
maybeToList :: Maybe a -> [a]
maybeToList Nothing = []
maybeToList (Just x) = [x]
-- For when we want to drop the Nothing values from our list.
-- >>> catMaybes [Just 1, Nothing, Just 2]
-- [1, 2]
-- >>> let xs = take 3 $ repeat Nothing
-- >>> catMaybes xs
-- []
catMaybes :: [Maybe a] -> [a]
catMaybes [] = []
catMaybes (x:xs) = maybeToList x ++ catMaybes xs
-- You’ll see this called “sequence” later.
-- >>> flipMaybe [Just 1, Just 2, Just 3]
-- Just [1, 2, 3]
-- >>> flipMaybe [Just 1, Nothing, Just 3]
-- Nothing
flipMaybe :: [Maybe a] -> Maybe [a]
flipMaybe [] = Just []
flipMaybe (Nothing:_) = Nothing
flipMaybe ((Just x):xs) =
    case flipMaybe xs of
        Nothing -> Nothing
        Just ys -> Just(x:ys)