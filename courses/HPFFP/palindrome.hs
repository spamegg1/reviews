module Palindrome where
import Control.Monad
import System.Exit (exitSuccess)
import Data.Char (toLower, isAlpha)
palindrome :: IO ()
palindrome = forever $ do
    line1 <- getLine
    let line2 = [toLower x | x <- line1, isAlpha x]
    case (line2 == reverse line2) of
        True -> putStrLn "It's a palindrome!"
        False -> do putStrLn "Nope!"
                    exitSuccess