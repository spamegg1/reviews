module CapitalizeWords where
import Data.Char

capitalizeWord :: String -> String
capitalizeWord "" = ""
capitalizeWord (x:xs) = [toUpper x] ++ xs

capitalizeWords :: String -> [(String, String)]
capitalizeWords sentence =
    [(x, capitalizeWord x) | x <- words sentence]

capitalizeParagraph :: String -> String
capitalizeParagraph sentences =
    let pairs = capitalizeWords sentences in
    snd(pairs!!0) ++
    concat [" " ++ (if elem '.' (fst (pairs!!(x-1)))
                    then snd(pairs!!x)
                    else fst(pairs!!x))
            | x <- [1..(length pairs - 1)]]
