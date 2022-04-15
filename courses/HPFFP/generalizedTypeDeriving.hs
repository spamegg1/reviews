{-# LANGUAGE GeneralizedNewtypeDeriving #-}
class TooMany a where
    tooMany :: a -> Bool
instance TooMany Int where
    tooMany n = n > 42
newtype Goats = Goats Int deriving (Eq, Show, TooMany)