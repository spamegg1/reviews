-- main = do
    -- x1 <- getLine
    -- x2 <- getLine
    -- return (x1 ++ x2)

main :: IO ()
main=do c <- getChar
        c' <- getChar
        if c == c'
        then putStrLn "True"
        else return ()