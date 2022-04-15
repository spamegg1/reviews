module Chapter10 where

stops = "pbtdkg"
vowels = "aeiou"

-- create all 3 letter stop-vowel-stop combos
threes = [[x] ++ [y] ++ [z] | x <- stops, y <- vowels, z <- stops]

-- same as above, but begins with the letter p
pthrees = [['p'] ++ [y] ++ [z] | y <- vowels, z <- stops]
pthrees2 = filter (\x -> x!!0=='p') threes

nouns = ["apple", "banana", "clementine"]
verbs = ["likes", "wants", "beats"]

-- create all 3 word noun-verb-noun sentences
sentences = [(x,y,z) | x <- nouns, y <- verbs, z <- nouns]




-- calculates average length of word in sentence
seekritFunc x = fromIntegral(sum (map length (words x))) / fromIntegral(length (words x))




-- direct recursion, not using (&&)
myAnd1 :: [Bool] -> Bool
myAnd1 [] = True
myAnd1 (x:xs) =
    if x == False
    then False
    else myAnd1 xs
-- direct recursion, using (&&)
myAnd2 :: [Bool] -> Bool
myAnd2 [] = True
myAnd2 (x:xs) = x && myAnd2 xs
-- fold, not point-free
-- in the folding function
myAnd3 :: [Bool] -> Bool
myAnd3 = foldr (\a b -> if a == False then False else b) True
-- fold, both myAnd and the folding
-- function are point-free now
myAnd4 :: [Bool] -> Bool
myAnd4 = foldr (&&) True

myOr :: [Bool] -> Bool
myOr = foldr (||) False

myAny :: (a -> Bool) -> [a] -> Bool
myAny f = myOr . map f

myElem :: Eq a => a -> [a] -> Bool
myElem elt lst = foldr (\x y -> x == elt || y) False lst
myElem2 elt = myAny (\x -> x == elt)

myReverse :: [a] -> [a]
myReverse = foldl (flip (:)) []
myReverse2 :: [a] -> [a]
myReverse2 = foldr (\x y -> y ++ [x]) []

myMap :: (a -> b) -> [a] -> [b]
myMap f = foldr (\x y -> (f x) : y) []

myFilter :: (a -> Bool) -> [a] -> [a]
myFilter f = foldr (\x y -> if (f x) then x:y else y) []

squish :: [[a]] -> [a]
squish = foldr (\x y -> x++y) []

squishMap :: (a -> [b]) -> [a] -> [b]
squishMap f = foldr (\x y -> f x ++ y) []
squishMap2 :: (a -> [b]) -> [a] -> [b]
squishMap2 f = squish . myMap f

squishAgain :: [[a]] -> [a]
squishAgain = squishMap (\x -> x)

myMaximumBy :: (a -> a -> Ordering) -> [a] -> a
myMaximumBy compfunc lst = foldr (\x y -> if compfunc x y == GT then x else y) (last lst) lst

myMinimumBy :: (a -> a -> Ordering) -> [a] -> a
myMinimumBy compfunc lst = foldr (\x y -> if compfunc x y == LT then x else y) (last lst) lst
