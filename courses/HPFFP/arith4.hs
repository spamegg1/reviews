-- arith4.hs
module Arith4 where
-- id :: a -> a
-- id x = x
roundTrip :: (Show a, Read a) => a -> a
roundTrip = read . show
main = do
    print (roundTrip 4)
    print (id 4)
