{-# LANGUAGE DeriveGeneric #-}
module CoArbitrary where

import GHC.Generics
import Test.QuickCheck
import Test.QuickCheck.Function

data Bool' = True'| False' deriving (Generic)

instance CoArbitrary Bool'

trueGen :: Gen Int
trueGen = coarbitrary True' arbitrary

falseGen :: Gen Int
falseGen = coarbitrary False' arbitrary

-- how to generate arbitrary functions a -> b
funGen :: (Function a, CoArbitrary a, Arbitrary b) => Gen(Fun a b)
funGen = arbitrary