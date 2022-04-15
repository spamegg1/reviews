-- filter multiples of 3 from a list
filterthrees xs = filter (\x -> mod x 3 == 0) xs

-- returns how many multiples of 3 there are in list
howmanythrees = length . filterthrees

-- remove all articles (the, a, an) from a sentence
myFilter str = filter (\x -> not (elem x ["a", "an", "the"])) (words str)

-- my version of zip
myZip :: [a] -> [b] -> [(a,b)]
myZip [] _ = []
myZip _ [] = []
myZip (x:xs) (y:ys) = (x,y) : myZip xs ys

-- my version of zipWith
myZipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
myZipWith _ _ [] = []
myZipWith _ [] _ = []
myZipWith f (x:xs) (y:ys) = (f x y) : myZipWith f xs ys

-- rewrite zip in terms of zipWith
myZip2 = myZipWith (\x y -> (x,y))