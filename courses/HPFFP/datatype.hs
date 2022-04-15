data Price = Price Integer deriving (Eq, Show)

data Size = Size Integer deriving (Eq, Show)

data Manufacturer = Mini | Mazda | Tata deriving (Eq, Show)

data Airline = PapuAir | CatapultsR'Us | TakeYourChancesUnited deriving (Eq, Show)

data Vehicle = Car Manufacturer Price | Plane Airline Size deriving (Eq, Show)

myCar = Car Mini (Price 14000)
urCar = Car Mazda (Price 20000)
clownCar = Car Tata (Price 7000)
doge = Plane PapuAir (Size 1000)


isCar :: Vehicle -> Bool
isCar (Car _ _) = True
isCar _ = False

isPlane :: Vehicle -> Bool
isPlane (Plane _ _) = True
isPlane _ = False

areCars :: [Vehicle] -> [Bool]
areCars = map isCar


getManu :: Vehicle -> Maybe Manufacturer
getManu (Car manu price) = Just manu
getManu _ = Nothing




class TooMany a where
    tooMany :: a -> Bool
instance TooMany Int where
    tooMany n = n > 42
newtype Goats = Goats Int deriving (Eq, Show)
instance TooMany Goats where
    tooMany (Goats n) = tooMany n



data Person = Person { name :: String , age :: Int } deriving (Eq, Show)
papu = Person "Papu" 5



data Expr =
      Number Int
    | Add Expr Expr
    | Minus Expr
    | Mult Expr Expr
    | Divide Expr Expr
-- or
type Number = Int
type Add = (Expr, Expr)
type Minus = Expr
type Mult = (Expr, Expr)
type Divide = (Expr, Expr)
type Expr = Either Number (Either Add (Either Minus (Either Mult Divide)))




data GuessWhat = Chickenbutt deriving (Eq, Show)
data Id a = MkId a deriving (Eq, Show)
data Product a b = Product a b deriving (Eq, Show)
data Sum a b = First a | Second b deriving (Eq, Show)
data RecordProduct a b = RecordProduct { pfirst :: a , psecond :: b } deriving (Eq, Show)




data OperatingSystem =
    GnuPlusLinux
    | OpenBSDPlusNevermindJustBSDStill
    | Mac
    | Windows deriving (Eq, Show)
data ProgLang =
    Haskell
    | Agda
    | Idris
    | PureScript deriving (Eq, Show)
data Programmer = Programmer { os :: OperatingSystem, lang :: ProgLang } deriving (Eq, Show)
nineToFive :: Programmer
nineToFive = Programmer { os = Mac, lang = Haskell }
-- We can reorder stuff
-- when we use record syntax
feelingWizardly :: Programmer
feelingWizardly = Programmer { lang = Agda, os = GnuPlusLinux }