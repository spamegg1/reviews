module AsPatterns where
import Data.Char

f :: Show a => (a, b) -> IO (a, b)
f t@(a, _) = do
    print a
    return t

doubleUp :: [a] -> [a]
doubleUp [] = []
doubleUp xs@(x:_) = x : xs

isSubseqOf :: (Eq a) => [a] -> [a] -> Bool
isSubseqOf [] [] = True
isSubseqOf [] _ = True
isSubseqOf _ [] = False
isSubseqOf a@(x:xs) (y:ys) =
       (x == y && isSubseqOf xs ys)
    || isSubseqOf a ys

testisSubseqOf :: IO ()
testisSubseqOf =
    if (isSubseqOf "blah" "blahwoot"
        && isSubseqOf "blah" "wootblah"
        && isSubseqOf "blah" "wboloath"
        && isSubseqOf "blah" "blawhoot"
        && isSubseqOf "blah" "wootbla" == False
        && isSubseqOf "blah" "halbwoot" == False)
    then putStrLn "isSubseqOf fine!"
    else putStrLn "Bad news bears."
