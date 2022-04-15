-- src/Main.hs
module Main where
import Control.Monad (forever)
import Data.Char (toLower)
import Data.Maybe (isJust)
import Data.List (intersperse)
import System.Exit (exitSuccess)
import System.Random (randomRIO)

minWordLength :: Int
minWordLength = 5
maxWordLength :: Int
maxWordLength = 9
maxGuesses :: Int
maxGuesses = 7

-- converts dictionary to word list
newtype WordList = WordList [String] deriving (Eq, Show)
allWords :: IO WordList
allWords = do
    dict <- readFile "data/dict.txt"
    return $ WordList(lines dict)

-- filters out words from dictionary that are too short or too long
gameWords :: IO WordList
gameWords = do
    (WordList aw) <- allWords
    return $ WordList(filter gameLength aw)
    where gameLength w =
            let l = length (w :: String)
            in  l >= minWordLength && l < maxWordLength

-- picks randomly indexed word from wordlist
randomWord :: WordList -> IO String
randomWord (WordList wl) = do
    randomIndex <- randomRIO (0, length wl - 1)
    return $ wl !! randomIndex

-- feeds the output of gameWords as input to randomWord
randomWord' :: IO String
randomWord' = gameWords >>= randomWord

-- Puzzle (word we're trying to guess)
--        (chars we filled in correctly so far)
--        (letters we guessed so far)
--        (number of incorrect guesses)
data Puzzle = Puzzle String [Maybe Char] [Char] Int

-- Show instance for Puzzle
instance Show Puzzle where
    show (Puzzle _ discovered guessed incorrect) =
        (intersperse ' ' $ fmap renderPuzzleChar discovered)
        ++ " Guessed so far: " ++ guessed
        ++ " Guesses left: " ++ show (maxGuesses - incorrect)

-- fresh Puzzle object, filled with Nothings
freshPuzzle :: String -> Puzzle
freshPuzzle string = Puzzle string (map (const Nothing) string) [] 0

-- produces True if guessed char is in word we're trying to guess
charInWord :: Puzzle -> Char -> Bool
charInWord (Puzzle string _ _ _) char = elem char string

-- produces True if char was already guessed previously
alreadyGuessed :: Puzzle -> Char -> Bool
alreadyGuessed (Puzzle _ _ guessed _) char = elem char guessed

-- renders Puzzle object. Letters not guessed so far are hidden with _
renderPuzzleChar :: Maybe Char -> Char
renderPuzzleChar (Just a) = a
renderPuzzleChar Nothing = '_'

-- fills in guess char, returns new updated Puzzle object
fillInCharacter :: Puzzle -> Char -> Puzzle
fillInCharacter (Puzzle word filledInSoFar s incorrect) c =
    Puzzle word newFilledInSoFar (c : s) newincorrect
    where
        zipper guessed wordChar guessChar =
            if wordChar == guessed
            then Just wordChar
            else guessChar
        newFilledInSoFar = zipWith (zipper c) word filledInSoFar
        newincorrect = if filledInSoFar == newFilledInSoFar
                       then incorrect + 1
                       else incorrect

-- handles guesses
handleGuess :: Puzzle -> Char -> IO Puzzle
handleGuess puzzle guess = do
    putStrLn $ "Your guess was: " ++ [guess]
    case (charInWord puzzle guess, alreadyGuessed puzzle guess) of
        (_, True) -> do
            putStrLn "You already guessed that character, pick something else!"
            return puzzle
        (True, _) -> do
            putStrLn "This character was in the word, filling in the word accordingly"
            return (fillInCharacter puzzle guess)
        (False, _) -> do
            putStrLn "This character wasn't in the word, try again."
            return (fillInCharacter puzzle guess)

-- ends game after certain number of guesses
gameOver :: Puzzle -> IO ()
gameOver (Puzzle wordToGuess _ guessed incorrect) =
    if incorrect >= maxGuesses
    then do putStrLn "You lose!"
            putStrLn $ "The word was: " ++ wordToGuess
            exitSuccess
    else return ()

-- ends game after a win happens
gameWin :: Puzzle -> IO ()
gameWin (Puzzle wordToGuess filledInSoFar _ _) =
    if all isJust filledInSoFar
    then do putStrLn $ "You win! The word was: " ++ wordToGuess
            exitSuccess
    else return ()

-- runs the game forever in a loop, until game is over
runGame :: Puzzle -> IO ()
runGame puzzle = forever $ do
    gameOver puzzle -- check for game over
    gameWin puzzle  -- check for winning condition
    putStrLn $ "Current puzzle is: " ++ show puzzle ++ "\n Guess a letter: "
    guess <- getLine
    case guess of
        [c] -> handleGuess puzzle c >>= runGame -- recursive loop
        _ -> putStrLn "Your guess must be a single character"

-- Main
main :: IO ()
main = do
    word <- randomWord'
    let puzzle = freshPuzzle (fmap toLower word)
    runGame puzzle