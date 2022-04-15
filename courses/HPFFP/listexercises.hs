module PoemLines where

myWords :: String -> [String]
myWords "" = []
myWords str = takeWhile (/=' ') str : (myWords $ dropWhile (==' ') $ dropWhile (/=' ') str)


firstSen = "Tyger Tyger, burning bright\n"
secondSen = "In the forests of the night\n"
thirdSen = "What immortal hand or eye\n"
fourthSen = "Could frame thy fearful symmetry?"
sentences = firstSen ++ secondSen ++ thirdSen ++ fourthSen

-- putStrLn sentences -- should print
-- Tyger Tyger, burning bright
-- In the forests of the night
-- What immortal hand or eye
-- Could frame thy fearful symmetry?

-- Implement this
myLines :: String -> [String]
myLines "" = []
myLines str = takeWhile (/='\n') str : (myLines $ dropWhile (=='\n') $ dropWhile (/='\n') str)

-- What we want 'myLines sentences'
-- to equal
shouldEqual =
    [ "Tyger Tyger, burning bright"
    , "In the forests of the night"
    , "What immortal hand or eye"
    , "Could frame thy fearful symmetry?"
    ]

-- The main function here is a small test
-- to ensure you've written your function
-- correctly.
main :: IO ()
main = print $ "Are they equal? " ++ show (myLines sentences == shouldEqual)

mySplit :: Char -> String -> [String]
mySplit ch "" = []
mySplit ch str = takeWhile (/=ch) str : (mySplit ch $ dropWhile (==ch) $ dropWhile (/=ch) str)
