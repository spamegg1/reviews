module ApplicativeLaws where
import Test.QuickCheck

main :: IO()
main = do
    quickCheck ( 1 == 1 )
