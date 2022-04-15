import Data.Char
import Data.List
import Data.Ord
import Data.Function

halveEvens :: [Integer] -> [Integer]
halveEvens = map (`div` 2) . filter even

safeString :: String -> String
safeString = map fix
  where fix c | isControl c = '_'
              | isAscii c   = c
              | otherwise   = '_'

holes :: [a] -> [[a]]
holes xs = zipWith (++) (inits xs) (map tail (init (tails xs)))

longestText :: Show a => [a] -> a
longestText = maximumBy (comparing (length . show))

adjacents :: [a] -> [(a,a)]
adjacents xs = zip xs (tail xs)

commas :: [String] -> String
commas = intercalate ", "

addPolynomials :: [[Integer]] -> [Integer]
addPolynomials = foldl1 (zipWith (+))

sumNumbers :: String -> Integer
sumNumbers = sum . map read . filter (isDigit.head) . groupBy ((==) `on` isDigit)

wordCount input = unlines
    [ "Number of lines: " ++ show (length ls)
    , "Number of empty lines: " ++ show (length empty_ls)
    , "Number of words: " ++ show (length ws)
    , "Number of unique words: " ++ show (length (nub ws))
    , "Number of words followed by themselves: " ++ show repeated
    , "Length of the longest line: " ++ show (length (maximumBy (comparing length) ls))
    ]
  where
    ls = lines input
    empty_ls = filter null ls
    ws = words input
    repeated = length (filter (uncurry (==)) (adjacents ws))

testResults :: [(String, [Bool])]
testResults = [ ("halveEvens",      ex_halveEvens)
              , ("safeString",      ex_safeString)
              , ("holes",           ex_holes)
              , ("longestText",     ex_longestText)
              , ("commas",          ex_commas)
              , ("addPolynomials",  ex_addPolynomials)
              , ("sumNumbers",      ex_sumNumbers)
              ]

ex_halveEvens =
    [ halveEvens [] == []
    , halveEvens [1,2,3,4,5] == [1,2]
    , halveEvens [6,6,6,3,3,3,2,2,2] == [3,3,3,1,1,1]
    ]

ex_safeString =
    [ safeString [] == []
    , safeString "Hello World!" == "Hello World!"
    , safeString "That’s your line:\n" == "That_s your line:_"
    , safeString "🙋.o(“Me Me Me”)" == "_.o(_Me Me Me_)"
    ]

ex_holes =
   [ holes "" == []
   , holes "Hello" == ["ello", "Hllo", "Helo", "Helo", "Hell"]
   ]

ex_longestText =
   [ longestText [True,False] == False
   , longestText [2,4,16,32] == (32::Int)
   , longestText (words "Hello World") == "World"
   , longestText (words "Olá mundo") ==  "Olá"
   ]

ex_adjacents =
   [ adjacents "" == []
   , adjacents [True] == []
   , adjacents "Hello" == [('H','e'),('e','l'),('l','l'),('l','o')]
   ]

ex_commas =
   [ commas [] == ""
   , commas ["Hello"] == "Hello"
   , commas ["Hello", "World"] == "Hello, World"
   , commas ["Hello", "", "World"] == "Hello, , World"
   , commas ["Hello", "new", "World"] == "Hello, new, World"
   ]

ex_addPolynomials =
   [ addPolynomials [[]] == []
   , addPolynomials [[0, 1], [1, 1]] == [1, 2]
   , addPolynomials [[0, 1, 5], [7, 0, 0], [-2, -1, 5]] == [5, 0, 10]
   ]

ex_sumNumbers =
   [ sumNumbers "" == 0
   , sumNumbers "Hello world!" == 0
   , sumNumbers "a1bc222d3f44" == 270
   , sumNumbers "words0are1234separated12by3integers45678" == 46927
   , sumNumbers "000a." == 0
   , sumNumbers "0.00a." == 0
   ]

ex_bad1 = [ ]
ex_bad2 = [ False, False ]
ex_bad3 = [ False, True, False ]

formatTests :: [(String, [Bool])] -> String
formatTests = unlines . map formatTest

formatTest :: (String, [Bool]) -> String
formatTest (name, res)
    | and res
    = name ++ ": " ++ show n ++ "/" ++ show n ++ " successful tests"
    | or res
    = name ++ ": " ++ show m ++ "/" ++ show n ++ " successful tests. " ++
      "Failing tests: " ++ commaAnd (map show (findFailing res))
    | otherwise
    = name ++ ": All " ++ show n ++ " tests failed."
  where
    n = length res
    m = length (filter id res)

commaAnd :: [String] -> String
commaAnd [] = ""
commaAnd [x] = x
commaAnd [x,y] = x ++ " and " ++ y
commaAnd (x:xs) = x ++ ", " ++ commaAnd xs

findFailing :: [Bool] -> [Integer]
findFailing = go 1
  where
    go n [] = []
    go n (False:xs) = n : go (n+1) xs
    go n (True :xs) =     go (n+1) xs

main = putStr (formatTests testResults)
