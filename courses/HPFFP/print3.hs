module Print3 where

myGreeting :: String
myGreeting = "hello" ++ " world!"


hello :: String
hello = "hello"

world :: String
world = "world!"

main :: IO ()
main = do
    putStrLn myGreeting
    putStr secondGreeting where
        secondGreeting = concat [hello, " ", world]