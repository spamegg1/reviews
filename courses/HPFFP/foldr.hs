myAny :: (a -> Bool) -> [a] -> Bool
myAny f xs = foldr (\x y -> f x || y) False xs