-- matchingTuples1.hs
module TupleFunctions where
-- These have to be the same type because
-- (+) is a -> a -> a
addEmUp2 :: Num a => (a, a) -> a
addEmUp2 (x, y) = x + y
-- addEmUp2 could also be written like so
addEmUp2Alt :: Num a => (a, a) -> a
addEmUp2Alt tup = (fst tup) + (snd tup)
fst3 :: (a, b, c) -> a
fst3 (x, _, _) = x
third3 :: (a, b, c) -> c
third3 (_, _, x) = x