-- direct recursion, using (&&)
myAnd :: [Bool] -> Bool
myAnd [] = True
myAnd (x:xs) = x && myAnd xs


myOr :: [Bool] -> Bool
myOr [] = False
myOr (x:xs) = x || myOr xs


myAny :: (a -> Bool) -> [a] -> Bool
myAny f = myOr . map f


myAll :: [Bool] -> Bool
myAll [] = True
myAll (x:xs) = x && myAll xs


myElem :: Eq a => a -> [a] -> Bool
myElem _ [] = False
myElem elt (x:xs) = x == elt || myElem elt xs


myReverse :: [a] -> [a]
myReverse [] = []
myReverse (x:xs) = myReverse xs ++ [x]


squish :: [[a]] -> [a]
squish [] = []
squish (x:xs) = x ++ squish xs


squishMap :: (a -> [b]) -> [a] -> [b]
squishMap _ [] = []
squishMap f (x:xs) = f x ++ squishMap f xs


squishAgain :: [[a]] -> [a]
squishAgain = squishMap (\x -> x)


myMaximumBy :: (a -> a -> Ordering) -> [a] -> a
myMaximumBy _ [x] = x
myMaximumBy compfunc (x:y:xs) =
    case compfunc x y of
    GT -> myMaximumBy compfunc (x:xs)
    EQ -> myMaximumBy compfunc (y:xs)
    LT -> myMaximumBy compfunc (y:xs)


myMinimumBy :: (a -> a -> Ordering) -> [a] -> a
myMinimumBy _ [x] = x
myMinimumBy compfunc (x:y:xs) =
    case compfunc x y of
    GT -> myMinimumBy compfunc (y:xs)
    EQ -> myMinimumBy compfunc (x:xs)
    LT -> myMinimumBy compfunc (x:xs)


myMaximum :: (Ord a) => [a] -> a
myMaximum = myMaximumBy compare


myMinimum :: (Ord a) => [a] -> a
myMinimum = myMinimumBy compare
