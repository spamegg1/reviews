import Test.QuickCheck


-- Exercise 1

-- Code split up into several functions for clarity; all in one is also ok.

instance Arbitrary a => Arbitrary (Tree a) where
    arbitrary = genTree

genTree :: Arbitrary a => Gen (Tree a)
genTree = sized $ \size ->
        frequency [ (1, genLeaf), (size, genNode) ]

genLeaf :: Arbitrary a => Gen (Tree a)
genLeaf = Leaf <$> arbitrary

genNode :: Arbitrary a => Gen (Tree a)
genNode = sized $ \size -> do
    x <- choose (0, size)
    Node <$> resize x arbitrary
         <*> resize (size - x) arbitrary

-- Exercise 2

size :: Tree a -> Int
size (Leaf _) = 1
size (Node l r) = size l + size r

toList :: Tree a -> [a]
toList (Leaf x) = [x]
toList (Node l r) = toList l ++ toList r

-- Exercise 3

prop_lengthToList :: Tree Integer -> Bool
prop_lengthToList t = length (toList t) == size t

prop_sizeLabelTree :: Tree Integer -> Bool
prop_sizeLabelTree t = size (labelTree t) == size t

prop_labelTree :: Tree Integer -> Bool
prop_labelTree t = toList (labelTree t) == [0..n]
    where n = fromIntegral $ size t - 1

prop_labelTreeIdempotent :: Tree Integer -> Bool
prop_labelTreeIdempotent t = labelTree (labelTree t) == labelTree t

-- From earlier homework


data Stream a = Cons a (Stream a)

streamIterate :: (a -> a) -> a -> Stream a
streamIterate f x = Cons x (streamIterate f (f x))

nats :: Stream Integer
nats = streamIterate (+1) 0

data Supply s a = S (Stream s -> (a, Stream s))

get :: Supply s s
get = S (\(Cons x xs) -> (x, xs))

pureSupply :: a -> Supply s a
pureSupply x = S (\xs -> (x, xs))

mapSupply :: (a -> b) -> Supply s a -> Supply s b
mapSupply f (S t) = S go
  where go xs = let (a, xs') = t xs
                in  (f a, xs')

mapSupply2 :: (a -> b -> c) -> Supply s a -> Supply s b -> Supply s c
mapSupply2 f (S t1) (S t2) = S go
  where go xs = let (a, xs')  = t1 xs
                    (b, xs'') = t2 xs'
                in  (f a b, xs'')

bindSupply :: Supply s a -> (a -> Supply s b) -> Supply s b
bindSupply (S t1) k = S go
  where go xs = let (a, xs')  = t1 xs
                    (S t2)    = k a
                    (b, xs'') = t2 xs'
                in  (b, xs'')

runSupply :: Stream s -> Supply s a -> a
runSupply s (S t) = fst (t s)

instance Functor (Supply s) where
    fmap = mapSupply

instance Applicative (Supply s) where
    pure = pureSupply
    (<*>) = mapSupply2 id

instance Monad (Supply s) where
    return = pureSupply
    (>>=) = bindSupply


data Tree a = Node (Tree a) (Tree a) | Leaf a deriving (Show, Eq)

labelTree :: Tree a -> Tree Integer
labelTree t = runSupply nats (go t)
  where
    go :: Tree a -> Supply s (Tree s)
    go (Node t1 t2) = Node <$> go t1 <*> go t2
    go (Leaf _) = Leaf <$> get

