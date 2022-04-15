module Phone where

-- fill in the rest.
data DaPhone = DaPhone

convo :: [String]
convo =["Wanna play 20 questions",
        "Ya",
        "U 1st haha",
        "Lol ok. Have u ever tasted alcohol",
        "Lol ya",
        "Wow ur cool haha. Ur turn",
        "Ok. Do u think I am pretty Lol",
        "Lol ya",
        "Just making sure rofl ur turn"]

-- validButtons = "1234567890*#"
type Digit = Char
-- Valid presses: 1 and up
type Presses = Int

reverseTaps :: DaPhone -> Char -> [(Digit, Presses)]
reverseTaps = undefined
-- assuming the default phone definition
-- 'a' -> [('2', 1)]
-- 'A' -> [('*', 1), ('2', 1)]

cellPhonesDead :: DaPhone -> String -> [(Digit, Presses)]
cellPhonesDead = undefined

fingerTaps :: [(Digit, Presses)] -> Presses
fingerTaps = undefined

-- combine reverseTaps and fingerTaps
mostPopularLetter :: String -> Char
mostPopularLetter = undefined

coolestLtr :: [String] -> Char
coolestLtr = undefined

coolestWord :: [String] -> String
coolestWord = undefined