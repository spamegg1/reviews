module PersonLib where

type Name = String
type Age = Integer

data Person = Person Name Age deriving Show

data PersonInvalid =
    NameEmpty
    | AgeTooLow
    | PersonInvalidUnknown String
    deriving (Eq, Show)

mkPerson :: Name-> Age-> Either PersonInvalid Person
mkPerson name age
    | name /= "" && age > 0 = Right $ Person name age
    | name == "" = Left NameEmpty
    | not (age > 0) = Left AgeTooLow
    | otherwise = Left $ PersonInvalidUnknown $
                    "Name was: " ++ show name ++ " Age was: " ++ show age

gimmePerson :: IO ()
gimmePerson = do
    putStr "Enter name: "
    name <- getLine
    putStr "Enter age: "
    age <- getLine
    let person = mkPerson name (read age::Integer)
    case person of
        Left NameEmpty -> putStrLn "Error: name was the empty string."
        Left AgeTooLow -> putStrLn "Error: age was not a positive integer."
        Left (PersonInvalidUnknown message) -> putStrLn message
        Right x@(Person _ _) ->
            putStrLn ("Yay! Successfully got a person: " ++ show x)