-- registeredUser2.hs
module RegisteredUser where

newtype Username = Username String
newtype AccountNumber = AccountNumber Integer
data User = UnregisteredUser | RegisteredUser Username AccountNumber

printUser :: User -> IO ()
printUser UnregisteredUser = putStrLn "UnregisteredUser"
printUser (RegisteredUser (Username name) (AccountNumber acctNum)) =
            putStrLn $ name ++ " " ++ show acctNum
            
rUser = RegisteredUser myUser myAcct 
    where myUser = Username "callen"
          myAcct = AccountNumber 10456