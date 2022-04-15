import Data.Char
import Data.Maybe

import System.Environment
import System.IO
import System.Exit

-- Exercise 1

data ComplicatedA a b
    = Con1 a b
    | Con2 [Maybe (a -> b)]

data ComplicatedB f g a b
    = Con3 (f a)
    | Con4 (g b)
    | Con5 (g (g [b]))

instance Functor (ComplicatedA a) where
    fmap f (Con1 a b) = Con1 a (f b)
    fmap f (Con2 l) = Con2 (map (fmap (f .)) l)

instance Functor g => Functor (ComplicatedB f g a) where
    fmap f (Con3 fa) = Con3 fa
    fmap f (Con4 gb) = Con4 (fmap f gb)
    fmap f (Con5 gglb) = Con5 (fmap (fmap (map f)) gglb)

-- Exercise 2

func0 :: Monad f => (a -> a) -> f a -> f a
func0 f xs = do
    x <- xs
    return (f (f x))

func0' :: Functor f => (a -> a) -> f a -> f a
func0' f xs = (f . f) <$> xs

func1 :: Monad f => f a -> f (a,a)
func1 xs = xs >>= (\x -> return (x,x))

func1' :: Functor f => f a -> f (a,a)
func1' xs = (\x -> (x,x)) <$> xs

func2 :: Monad f => f a -> f (a,a)
func2 xs = xs >>= (\x -> xs >>= \y -> return (x,y))

func2' :: Applicative f => f a -> f (a,a)
func2' xs = (,) <$> xs <*> xs

func3 :: Monad f => f a -> f (a,a)
func3 xs = xs >>= (\x -> xs >>= \y -> return (x,x))

func3' :: Applicative f => f a -> f (a,a)
func3' xs = (\x _ -> (x,x)) <$> xs <*> xs
-- the second xs cannot be ignored, as it may have side-effects

func4 :: Monad f => f a -> f a -> f (a,a)
func4 xs ys = xs >>= (\x -> ys >>= \y -> return (x,y))

func4' :: Applicative f => f a -> f a -> f (a,a)
func4' xs ys = (,) <$> xs <*> ys

func5 :: Monad f => f Integer -> f Integer -> f Integer
func5 xs ys = do
    x <- xs
    let x' = x + 1
    y <- (+1) <$> ys
    return (x' + y)

func5' :: Applicative f => f Integer -> f Integer -> f Integer
func5' xs ys = (\x y -> (x+1) + (y+1)) <$> xs <*> ys


func6 :: Monad f => f Integer -> f (Integer,Integer)
func6 xs = do
    x <- xs
    return $ if x > 0 then (x, 0)
                      else (0, x)

func6' :: Functor f => f Integer -> f (Integer,Integer)
-- slightly unorthodox idiom, with an partially applied fmap
func6' = fmap $ \x -> if x > 0 then (x,0) else (0,x)

-- func7 cannot be implemented without Monad if we care about the precise
-- evaluation and layzness behaviour:
-- > isJust (func7 (Just undefined))
-- *** Exception: Prelude.undefined
--
-- If we care not, then it is equivalent to func6, and there we can. Note that
-- > sJust (func6 (Just undefined))
-- True
func7 :: Monad f => f Integer -> f (Integer,Integer)
func7 xs = do
    x <- xs
    if x > 0 then return (x, 0)
             else return (0, x)

func8 :: Monad f => f Integer -> Integer -> f Integer
func8 xs x = pure (+) <*> xs <*> pure x

func8' :: Functor f => f Integer -> Integer -> f Integer
func8' xs x = (+x) <$> xs

-- func9 cannot be implemented without Monad: The structure of the computation
-- depends on the result of the first argument.
func9 :: Monad f => f Integer -> f Integer -> f Integer -> f Integer
func9 xs ys zs = xs >>= \x -> if even x then ys else zs

func10 :: Monad f => f Integer -> f Integer
func10 xs = do
    x <- xs >>= (\x -> return (x * x))
    return (x + 10)

func10' :: Functor f => f Integer -> f Integer
func10' xs = (\x -> (x*x) + 10) <$> xs

-- Exercise 3

data Parser a = P (String -> Maybe (a, String))

runParser :: Parser t -> String -> Maybe (t, String)
runParser (P p) = p

parse :: Parser a -> String -> Maybe a
parse p input = case runParser p input of
    Just (result, "") -> Just result
    _ -> Nothing -- handles both no result and leftover input

noParser :: Parser a
noParser = P (\_ -> Nothing)

pureParser :: a -> Parser a
pureParser x = P (\input -> Just (x,input))

instance Functor Parser where
    fmap f p = P p'
      where
        p' input = case runParser p input of
            Just (result, rest) -> Just (f result, rest)
            Nothing             -> Nothing

instance Applicative Parser where
    pure = pureParser
    p1 <*> p2 = P $ \input -> do
        (f, rest1) <- runParser p1 input
        (x, rest2) <- runParser p2 rest1
        return (f x, rest2)
{- The above is clever use of the Maybe monad. Here the same written by hand
    p1 <*> p2 = P $ \input -> case runParser p1 input of
        Just (f, rest1) -> case runParser p2 rest1 of
            Just (x, rest2) -> Just (f x, rest2)
            Nothing -> Nothing
        Nothing -> Nothing
-}


instance Monad Parser where
    return = pureParser
    p1 >>= k = P $ \input -> do
        (x, rest1) <- runParser p1 input
        runParser (k x) rest1

anyChar :: Parser Char
anyChar = P $ \input -> case input of
    (c:rest) -> Just (c, rest)
    []       -> Nothing

char :: Char -> Parser ()
char c = do
    c' <- anyChar
    if c == c' then return ()
               else noParser

anyCharBut :: Char -> Parser Char
anyCharBut c = do
    c' <- anyChar
    if c /= c' then return c'
               else noParser

orElse :: Parser a -> Parser a -> Parser a
orElse p1 p2 = P $ \input -> case runParser p1 input of
    Just r -> Just r
    Nothing -> runParser p2 input

many :: Parser a -> Parser [a]
many p = ((:) <$> p <*> many p) `orElse` return []

sepBy :: Parser a -> Parser () -> Parser [a]
sepBy p1 p2 = ((:) <$> p1 <*> (many (p2 >> p1))) `orElse` return []

parseCSV :: Parser [[String]]
parseCSV = many parseLine
  where
    parseLine = parseCell `sepBy` char ',' <* char '\n'
    parseCell = do
        char '"'
        content <- many (anyCharBut '"')
        char '"'
        return content

-- Exercise 4

type Identifer = String
type Declaration = (Identifer, String)
type Section = (Identifer, [Declaration])
type INIFile = [Section]

many1 :: Parser a -> Parser [a]
many1 p = (:) <$> p <*> many p

letterOrDigit :: Parser Char
letterOrDigit = do
    c <- anyChar
    if isAlphaNum c then return c else noParser

parseINI :: Parser INIFile
parseINI = many1 parseSection
  where
    parseSection = do
        char '['
        t <- parseIdent
        char ']'
        char '\n'
        decls <- many parseLine
        return (t, catMaybes decls)
    parseIdent = many1 letterOrDigit
    parseLine = parseDecl `orElse` parseComment `orElse` parseEmpty

    parseDecl = do
        i <- parseIdent
        many (char ' ')
        char '='
        many (char ' ')
        c <- many1 (anyCharBut '\n')
        char '\n'
        return (Just (i,c))

    parseComment = do
        char '#'
        many1 (anyCharBut '\n')
        char '\n'
        return Nothing

    parseEmpty = do
        char '\n'
        return Nothing

main :: IO ()
main = do
    args <- getArgs
    input <- case args of
        [] -> getContents
        [fileName] -> readFile fileName
        _ -> hPutStrLn stderr "Too many arguments given" >> exitFailure
    case parse parseINI input of
        Just i -> print i
        Nothing -> do
            hPutStrLn stderr "Failed to parse INI file."
            exitFailure

