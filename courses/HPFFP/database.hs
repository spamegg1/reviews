module Database where

import Data.Time

data DatabaseItem =
      DbString String
    | DbNumber Integer
    | DbDate UTCTime    deriving (Eq, Ord, Show)


theDatabase :: [DatabaseItem]
theDatabase = [
    DbDate (UTCTime (fromGregorian 1911 5 1) (secondsToDiffTime 34123)),
    DbNumber 9001,
    DbNumber 999,
    DbString "Hello, world!",
    DbDate (UTCTime (fromGregorian 1921 5 1) (secondsToDiffTime 34123))]


helperDate :: DatabaseItem -> [UTCTime]
helperDate dbi =
    case dbi of
    DbDate utctime -> [utctime]
    _ -> []
filterDbDate :: [DatabaseItem] -> [UTCTime]
filterDbDate db = foldr (\x y -> (helperDate x) ++ y) [] db


helperNumber :: DatabaseItem -> [Integer]
helperNumber dbi =
    case dbi of
    DbNumber number -> [number]
    _ -> []
filterDbNumber :: [DatabaseItem] -> [Integer]
filterDbNumber db = foldr (\x y -> (helperNumber x) ++ y) [] db


myMaximumBy :: (a -> a -> Ordering) -> [a] -> a
myMaximumBy _ [x] = x
myMaximumBy compfunc (x:y:xs) =
    case compfunc x y of
    GT -> myMaximumBy compfunc (x:xs)
    EQ -> myMaximumBy compfunc (y:xs)
    LT -> myMaximumBy compfunc (y:xs)
myMaximum :: (Ord a) => [a] -> a
myMaximum = myMaximumBy compare

mostRecent :: [DatabaseItem] -> UTCTime
mostRecent = myMaximum . filterDbDate


sumDb :: [DatabaseItem] -> Integer
sumDb db = foldr (+) 0 (filterDbNumber db)


avgDb :: [DatabaseItem] -> Double
avgDb db = fromIntegral(sumDb db) / fromIntegral(length (filterDbNumber db))