module ApplicativeMaybe where
import Control.Applicative
import Test.QuickCheck

-- f ~ Maybe
-- type M = Maybe
-- (<*>) :: f (a -> b) -> f a -> f b
-- (<*>) :: M (a -> b) -> M a -> M b
-- pure :: a -> f a
-- pure :: a -> M a

-- instance Functor Maybe where
    -- fmap _ Nothing = Nothing
    -- fmap f (Just a) = Just (f a)

-- instance Applicative Maybe where
    -- pure = Just
    -- Nothing <*> _ = Nothing
    -- _ <*> Nothing = Nothing
    -- Just f <*> Just a = Just (f a)

validateLength :: Int -> String -> Maybe String
validateLength maxLen s =
    if (length s) > maxLen
    then Nothing
    else Just s

newtype Name = Name String deriving (Eq, Show)
newtype Address = Address String deriving (Eq, Show)

mkName :: String -> Maybe Name
mkName s = fmap Name (validateLength 25 s)

mkAddress :: String -> Maybe Address
mkAddress a = fmap Address (validateLength 100 a)

data Person = Person Name Address deriving (Eq, Show)

mkPerson' :: String -> String -> Maybe Person
mkPerson' n a =
    case mkName n of
        Nothing -> Nothing
        Just n' -> case mkAddress a of
                    Nothing -> Nothing
                    Just a' -> Just $ Person n' a'

-- mkPerson with Applicative instead
mkPerson :: String -> String -> Maybe Person
mkPerson n a = Person <$> mkName n <*> mkAddress a
-------------------------------------------------------------------------------
-- Another example
data Cow = Cow {name :: String, age :: Int, weight :: Int} deriving (Eq, Show)

noEmpty :: String -> Maybe String
noEmpty "" = Nothing
noEmpty str = Just str

noNegative :: Int -> Maybe Int
noNegative n
    | n >= 0 = Just n
    | otherwise = Nothing

-- Validating to get rid of empty strings, negative numbers
cowFromString :: String -> Int -> Int -> Maybe Cow
cowFromString name' age' weight' =
    case noEmpty name' of
        Nothing -> Nothing
        Just nammy ->
            case noNegative age' of
                Nothing -> Nothing
                Just agey ->
                    case noNegative weight' of
                        Nothing -> Nothing
                        Just weighty -> Just (Cow nammy agey weighty)

-- cowFromString with Applicative instead
cowFromString' :: String -> Int -> Int -> Maybe Cow
cowFromString' name' age' weight' =
    Cow <$> noEmpty name' <*> noNegative age' <*> noNegative weight'

-- same as above, but rewritten with liftA3
cowFromString'' :: String -> Int -> Int -> Maybe Cow
cowFromString'' name' age' weight' =
    liftA3 Cow (noEmpty name') (noNegative age') (noNegative weight')


-- Exercise: Fixer Upper
-- Given the function and values provided, use (<$>) from Functor,
-- (<*>) and pure from the Applicative typeclass to fill in missing
-- bits of the broken code to make it work.
-- 1.
x = const <$> Just "Hello" <*> pure "World"
z = const <$> Just <$> "Hello" <*> "World"
-- 2.
y = (,,,) <$> Just 90 <*> Just 10 <*> Just "Tierness" <*> pure [1, 2, 3]


-- main
main :: IO()
main = do
    quickCheck ( (Person <$> mkName "Babe" <*> (mkAddress "old macdonald's"))
                   == Just (Person (Name "Babe") (Address "old macdonald's")) )
    quickCheck ( mkPerson "Babe" "old macdonald's"
                   == Just (Person (Name "Babe") (Address "old macdonald's")) )
    quickCheck ( (cowFromString "Cow" 10 200)
                   == Just (Cow {name="Cow", age=10, weight=200}))
    quickCheck ( (cowFromString' "Cow" 10 200)
                   == Just (Cow {name="Cow", age=10, weight=200}))
    quickCheck ( (cowFromString'' "Cow" 10 200)
                   == Just (Cow {name="Cow", age=10, weight=200}))
    let cow1 = Cow <$> noEmpty "Bess" -- cow1 :: Maybe (Int -> Int -> Cow)
        cow2 = cow1 <*> noNegative 1  -- cow2 :: Maybe (Int -> Cow)
        cow3 = cow2 <*> noNegative 2  -- cow3 :: Maybe Cow
    quickCheck ( cow3 == Just Cow {name="Bess", age=1, weight=2} )
    let cow4 = liftA3 Cow
    -- cow4 :: Applicative f => f String -> f Int -> f Int -> f Cow
        cow5 = cow4 (noEmpty "Bess")
    -- cow5 :: Maybe Int -> Maybe Int -> Maybe Cow
        cow6 = cow5 (noNegative 1) -- cow6 :: Maybe Int -> Maybe Cow
        cow7 = cow6 (noNegative 2) -- cow7 :: Maybe Cow
    quickCheck ( cow3 == cow7 )
