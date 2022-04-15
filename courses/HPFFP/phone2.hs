module Phone where

import Data.Char (isUpper, toLower)
import Data.List (elemIndex, find)

type Digit = Char
type Presses = Int -- 1 and up

data Button = Button Digit String

-- 1
data Phone = Phone [Button]

validDigits :: String
validDigits = ['0'..'9'] ++ "*#"

validLetters :: String
validLetters = ['a'..'z'] ++ ['A'..'Z'] ++ ['0'..'9'] ++ " ."

isValidDigit :: Char -> Bool
isValidDigit = (flip elem) validDigits

isValidLetter :: Char -> Bool
isValidLetter = (flip elem) validLetters

phone :: Phone
phone = Phone
  [ Button '1' "",     Button '2' "abc", Button '3' "def"
  , Button '4' "ghi",  Button '5' "jkl", Button '6' "mno"
  , Button '7' "pqrs", Button '8' "tuv", Button '9' "wxyz"
  , Button '*' "",     Button '0' " ",   Button '#' "."
  ]

tapsOnButton :: Button -> Char -> [(Digit, Presses)]
tapsOnButton (Button d s) c
  | not (isValidLetter c) = []
  | c == d                = [(d, length s + 1)]
  | isUpper c             = case elemIndex (toLower c) s of
                              Nothing -> []
                              Just n  -> [('*', 1), (d, n + 1)]
  | otherwise             = case elemIndex c s of
                              Nothing -> []
                              Just n  -> [(d, n + 1)]

-- 2
reverseTaps :: Phone -> Char -> [(Digit, Presses)]
reverseTaps (Phone buttons) c =
  maybe [] id $ find nonEmpty $ map (flip tapsOnButton c) buttons
  where
    nonEmpty [] = False
    nonEmpty _  = True

convo :: [String]
convo =
  [ "Wanna play 20 questions"
  , "Ya"
  , "U 1st haha"
  , "Lol ok. Have u ever tasted alcohol lol"
  , "Lol ya"
  , "Wow ur cool haha. Ur turn"
  , "Ok. Do u think I am pretty Lol"
  , "Lol ya"
  , "Haha thanks just making sure rofl ur turn"
  ]

cellPhonesDead :: Phone -> String -> [(Digit, Presses)]
cellPhonesDead phone = concatMap (reverseTaps phone)

-- The following will convert convo into the keypresses
-- required for each message
keypresses :: [[(Digit, Presses)]]
keypresses = map (cellPhonesDead phone) convo

-- 3
fingerTaps :: [(Digit, Presses)] -> Presses
fingerTaps = foldr (\(_, n) accum -> n + accum) 0

-- The following will compute how many times the digits
-- need to be pressed for each message
pressesPerMessage :: [Presses]
pressesPerMessage = map fingerTaps keypresses

-- 4
mostPopularLetter :: String -> Char
mostPopularLetter = undefined

-- 5
coolestLtr :: [String] -> Char
coolestLtr = undefined

coolestWord :: [String] -> String
coolestWord = undefined
