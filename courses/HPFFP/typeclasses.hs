data DayOfWeek = 
    Mon | Tue | Wed | Thu | Fri | Sat | Sun deriving (Ord, Show )
    
-- day of week and numerical day of month
data Date = 
    Date DayOfWeek Int deriving Show 
    
-- implementing Eq for DayOfWeek
instance Eq DayOfWeek where
    (==) Mon Mon = True
    (==) Tue Tue = True
    (==) Wed Wed = True
    (==) Thu Thu = True
    (==) Fri Fri = True
    (==) Sat Sat = True
    (==) Sun Sun = True
    (==)  _   _  = False
    
-- implementing Eq for Date
instance Eq Date where
    (==) (Date weekday dayOfMonth) (Date weekday' dayOfMonth') = 
        weekday == weekday' && dayOfMonth == dayOfMonth'
        
--------------------------
data Identity a = Identity a deriving Show

instance Eq a => Eq (Identity a) where
    (==) (Identity v) (Identity v') = v == v'
---------------------------    
data TisAnInteger = TisAn Integer deriving Show

instance Eq TisAnInteger where
    (==) (TisAn integer) (TisAn integer') = integer == integer'
---------------------------
data TwoIntegers = Two Integer Integer deriving Show

instance Eq TwoIntegers where
    (==) (Two integer1 integer2) (Two integer3 integer4) = 
        integer1 == integer3 && integer2 == integer4
---------------------------
data StringOrInt = TisAnInt Int | TisAString String

instance Eq StringOrInt where
    (==) (TisAnInt int) (TisAnInt int') = int == int'
    (==) (TisAString str) (TisAString str') = str == str'
    (==) _ _ = False
---------------------------
data Person = Person Bool deriving Show
printPerson :: Person -> IO ()
printPerson person = putStrLn (show person)
---------------------------
data Mood = Blah | Woot deriving (Eq, Show)
settleDown x = 
    if x == Woot
    then Blah
    else x
---------------------------
type Subject = String
type Verb = String
type Object = String
data Sentence = Sentence Subject Verb Object deriving (Eq, Show)
s1 = Sentence "dogs" "drool"
s2 = Sentence "Julie" "loves" "dogs"    
---------------------------
data Rocks = Rocks String deriving (Eq, Show)
data Yeah = Yeah Bool deriving (Eq, Show)
data Papu = Papu Rocks Yeah deriving (Eq, Show)
truth = Papu (Rocks "chomskydoz") (Yeah True)
equalityForall :: Papu -> Papu -> Bool
equalityForall p p' = p == p'