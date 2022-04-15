sayHello :: String -> IO ()
sayHello x = 
    putStrLn ("Hello, " ++ x ++ "!")