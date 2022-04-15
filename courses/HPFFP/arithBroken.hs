-- arith3broken.hs
module Arith3Broken where
main :: IO ()
main = do
        print (1 + 2)
        putStrLn "10"
        print (negate (-1))
        print ((+) 0 blah)
            where blah = negate 1
