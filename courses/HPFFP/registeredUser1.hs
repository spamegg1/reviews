-- registeredUser1.hs
module RegisteredUser where

newtype Username = Username String
newtype AccountNumber = AccountNumber Integer
data User = UnregisteredUser | RegisteredUser Username AccountNumber