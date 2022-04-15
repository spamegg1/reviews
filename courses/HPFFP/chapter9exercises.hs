import Data.Char

filterUpper :: String -> String
filterUpper = filter isUpper

capitalize :: String -> String
capitalize str = [toUpper (str!!0)] ++ (snd $ splitAt 1 str)

upperize :: String -> String
upperize "" = ""
upperize str = [toUpper (str!!0)] ++ upperize(snd $ splitAt 1 str)

headcapital :: String -> Char
headcapital = toUpper . head
