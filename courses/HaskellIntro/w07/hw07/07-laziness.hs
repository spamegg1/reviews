import Control.Monad (replicateM)
import System.Random

-- Exercise 1

fib :: Integer -> Integer
fib 0 = 1
fib 1 = 1
fib n = fib (n-1) + fib (n-2)

fibs1 :: [Integer]
fibs1 = map fib [0..]

fibs2 :: [Integer]
fibs2 = 1 : 1 : zipWith (+) fibs2 (tail fibs2)

-- Exercise 2

data Stream a = Cons a (Stream a)

streamToList :: Stream a -> [a]
streamToList (Cons x xs) = x : streamToList xs

instance Show a => Show (Stream a) where
    show s = init (show (take 20 (streamToList s))) ++ "…"

streamRepeat :: a -> Stream a
streamRepeat x = Cons x (streamRepeat x)

streamMap :: (a -> b) -> Stream a -> Stream b
streamMap f (Cons x xs) = Cons (f x) (streamMap f xs)

streamIterate :: (a -> a) -> a -> Stream a
streamIterate f x = Cons x (streamIterate f (f x))

streamInterleave :: Stream a -> Stream a -> Stream a
streamInterleave (Cons x xs) ys = Cons x (streamInterleave ys xs)

nats :: Stream Integer
nats = streamIterate (+1) 0

ruler :: Stream Integer
ruler = streamInterleave (streamRepeat 0) (streamMap (+1) ruler)

-- Exercise 3

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


data Tree a = Node (Tree a) (Tree a) | Leaf a deriving Show

labelTree :: Tree a -> Tree Integer
labelTree t = runSupply nats (go t)
  where
    go :: Tree a -> Supply s (Tree s)
    go (Node t1 t2) = Node <$> go t1 <*> go t2
    go (Leaf _) = Leaf <$> get

{-
 do x <- f
    g
===
 f >>= (\x -> g)
-}


-- Non-Exercise

type Rand a = Supply Integer a

randomDice :: RandomGen g => g -> Stream Integer
randomDice gen =
    let (roll, gen') = randomR (1,6) gen
    in Cons roll (randomDice gen')

runRand :: Rand a -> IO a
runRand r = do
    stdGen <- getStdGen
    let diceRolls = randomDice stdGen
    return $ runSupply diceRolls r

averageOfTwo :: Rand Double
averageOfTwo = do
    d1 <- get
    d2 <- get
    return (fromIntegral (d1 + d2) / 2)

bestOutOfTwo :: Rand Double
bestOutOfTwo = do
    d1 <- get
    d2 <- get
    return $ fromIntegral $ if (d1 > d2) then d1 else d2

-- Look, ma, I’m recursive!
sumUntilOne :: Rand Double
sumUntilOne = do
    d <- get
    if (d == 1) then return 0
                else do s <- sumUntilOne
                        return (s + fromIntegral d)

sample :: Int -> Rand Double -> Rand (Double, Double)
sample n what = do
    samples <- replicateM n what
    return (maximum samples, sum samples / fromIntegral n)

main = mapM_ go [ ("average of two", averageOfTwo)
                , ("bestOutOfTwo",   bestOutOfTwo)
                , ("sumUntilOne",    sumUntilOne)
                ]
  where
    n = 10000
    go (name, what) = do
        (max, avg) <- runRand (sample n what)
        putStrLn $ "Playing \"" ++ name ++ "\" " ++ show n ++ " times " ++
                   "yields a max of " ++ show max ++ " and an average of " ++
                   show avg ++ "."
