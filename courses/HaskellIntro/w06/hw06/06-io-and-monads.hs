{-# LANGUAGE OverloadedStrings #-}

import Control.Monad
import Data.Char
import System.IO

main :: IO ()
main = exercise4

-- Lists

data List a = Empty | Entry a (List a) deriving Eq

infixr `Entry`

mapList :: (a -> b) -> List a -> List b
mapList _ Empty = Empty
mapList f (Entry c cs) = Entry (f c) (mapList f cs)

combine :: List Picture -> Picture
combine Empty = blank
combine (Entry p ps) = p & combine ps

allList :: List Bool -> Bool
allList Empty = True
allList (Entry b bs) = b && allList bs


elemList :: Eq a => a -> List a -> Bool
elemList _ Empty = False
elemList x (Entry y ys) | x == y = True
                        | otherwise = elemList x ys

appendList :: List a -> List a -> List a
appendList Empty ys = ys
appendList (Entry x xs) ys = Entry x (xs `appendList` ys)

listLength :: List a -> Integer
listLength Empty = 0
listLength (Entry _ xs) = 1 + listLength xs

nth :: List a -> Integer -> a
nth Empty _ = error "list too short"
nth (Entry x _) 1 = x
nth (Entry _ xs) n = nth xs (n-1)

filterList :: (a -> Bool) -> List a -> List a
filterList _ Empty = Empty
filterList p (Entry x xs) | p x       = Entry x (filterList p xs)
                          | otherwise =          filterList p xs

-- Graph Search


isGraphClosed :: Eq a => a -> (a -> List a) -> (a -> Bool) -> Bool
isGraphClosed initial adjacent isOk = go Empty (Entry initial Empty)
  where
    go _    Empty = True
    go seen (Entry c todo) | c `elemList` seen = go seen todo
    go _    (Entry c _)    | not (isOk c)      = False
    go seen (Entry c todo) = go (Entry c seen) (adjacent c `appendList` todo)

-- Coordinates


data Coord = C Integer Integer

data Direction = R | U | L | D deriving Eq

eqCoord :: Coord -> Coord -> Bool
eqCoord (C x1 y1) (C x2 y2) = x1 == x2 && y1 == y2

instance Eq Coord where
  C x1 y1 == C x2 y2 = x1 == x2 && y1 == y2

adjacentCoord :: Direction -> Coord -> Coord
adjacentCoord R (C x y) = C (x+1) y
adjacentCoord U (C x y) = C  x   (y+1)
adjacentCoord L (C x y) = C (x-1) y
adjacentCoord D (C x y) = C  x   (y-1)

moveFromTo :: Eq a => a -> a -> a -> a
moveFromTo c1 c2 c | c1 == c   = c2
                   | otherwise = c


-- The maze

data Tile = Wall | Ground | Storage | Box | Blank deriving Eq

data Maze = Maze Coord (Coord -> Tile)


isClosed :: Maze -> Bool
isClosed (Maze c0 maze) = isGoodStart && isGraphClosed c0 adjacent isOk
  where
    isGoodStart = case maze c0 of
      Ground -> True
      Storage -> True
      _ -> False
    isOk c = case maze c of
      Blank -> False
      _ -> True
    adjacent c = filterList check (mapList (\d -> adjacentCoord d c) allDirections)
      where
        check c = case maze c of
          Wall -> False
          _    -> True

allDirections :: List Direction
allDirections = Entry U (Entry L (Entry D (Entry R Empty)))

noBoxMaze :: (Coord -> Tile) -> Coord -> Tile
noBoxMaze maze c = case maze c of
  Box -> Ground
  t   -> t

mazeWithBoxes :: (Coord -> Tile) -> List Coord -> Coord -> Tile
mazeWithBoxes maze Empty c' = noBoxMaze maze c'
mazeWithBoxes maze (Entry c cs) c'
  | eqCoord c c' = Box
  | otherwise  = mazeWithBoxes maze cs c'

isOnStorage :: (Coord -> Tile) -> Coord -> Bool
isOnStorage maze c = case maze c of Storage -> True
                                    _       -> False

gameWon :: (Coord -> Tile) ->  List Coord -> Bool
gameWon maze cs = allList (mapList (isOnStorage maze) cs)


-- The state

data State = State Coord Direction (List Coord) Integer deriving Eq


initialBoxes :: (Coord -> Tile) -> List Coord
initialBoxes maze = go (-10) (-10)
  where
    go 11 11 = Empty
    go x  11 = go (x+1) (-10)
    go x  y  = case maze (C x y) of
      Box -> Entry (C x y) (go x (y+1))
      _   ->                go x (y+1)

loadMaze :: Integer -> State
loadMaze n = State c R (initialBoxes maze) n
  where (Maze c maze) = nth mazes n

initialState :: State
initialState = loadMaze 1

nthMaze :: Integer -> (Coord -> Tile)
nthMaze n = case nth mazes n of Maze _ maze -> maze

-- Event handling

tryGoTo :: State -> Direction -> State
tryGoTo (State from _ bx i) d
  = case currentMaze to of
    Box -> case currentMaze beyond of
      Ground -> movedState
      Storage -> movedState
      _ -> didn'tMove
    Ground -> movedState
    Storage -> movedState
    _ -> didn'tMove
  where to          = adjacentCoord d from
        beyond      = adjacentCoord d to
        maze        = nthMaze i
        currentMaze = mazeWithBoxes maze bx
        movedState  = State to d movedBx i
        movedBx     = mapList (moveFromTo to beyond) bx
        didn'tMove  = State from d bx i

handleEvent :: String -> State -> State
handleEvent key (State _ _ bx i)
    | gameWon (nthMaze i)  bx
    , i < listLength mazes
    , key == " "     = loadMaze (i+1)
handleEvent _ (State c d bx i)
    | gameWon (nthMaze i) bx
                     = State c d bx i
handleEvent key s
    | key == "Right" = tryGoTo s R
    | key == "Up"    = tryGoTo s U
    | key == "Left"  = tryGoTo s L
    | key == "Down"  = tryGoTo s D
handleEvent _ s      = s

-- Our custom picture type


type DrawFun = Int -> Int -> Char
type Picture = DrawFun -> DrawFun

blank :: Picture
blank = id

(&) :: Picture -> Picture -> Picture
(&) = (.)

char :: Char -> Picture
char c f 0 0 = c
char _ f x y = f x y

translated :: Int -> Int -> Picture -> Picture
translated dx dy p f x y = p (\x y -> f (x + dx) (y + dy)) (x - dx) (y - dy)


text :: String -> Picture
text s f x y | x >= -nh
             , x < -nh + n
             , y == 0
             = s !! (x + nh)
             | x >= -nh-1 , x <= -nh + n
             , y >= -1,     y <= 1
             = '+'
             | otherwise
             = f x y
  where n = length s
        nh = n `div` 2

renderPicture :: Picture -> String
renderPicture p = unlines [ [f x y | x <- [-10..10] ] | y <- [10,9 .. -10] ]
  where f = p (\_ _ -> ' ')

-- Drawing

wall, ground, storage, box :: Picture
wall =    char '#'
ground =  blank
storage = char '*'
box =     char 'H'

drawTile :: Tile -> Picture
drawTile Wall    = wall
drawTile Ground  = ground
drawTile Storage = storage
drawTile Box     = box
drawTile Blank   = blank

pictureOfMaze :: (Coord -> Tile) -> Picture
pictureOfMaze maze  = draw21times (\r -> draw21times (\c -> drawTileAt maze (C r c)))

draw21times :: (Integer -> Picture) -> Picture
draw21times something = go (-10)
  where
    go :: Integer -> Picture
    go 11 = blank
    go n  = something n & go (n+1)

drawTileAt :: (Coord -> Tile) ->  Coord -> Picture
drawTileAt maze c = atCoord c (drawTile (noBoxMaze maze c))


atCoord :: Coord -> Picture -> Picture
atCoord (C x y) pic = translated (fromIntegral x) (fromIntegral y) pic


player :: Direction -> Picture
player R = char '→'
player L = char '←'
player U = char '↑'
player D = char '↓'

showWin :: State -> Picture
showWin (State _ _ cs i)
  | gameWon (nthMaze i) cs
  , i == listLength mazes
  = text "Game Over!"
  | gameWon (nthMaze i) cs
  = text "Level done!"
  | otherwise  = blank

pictureOfBoxes :: List Coord -> Picture
pictureOfBoxes cs = combine (mapList (\c -> atCoord c (drawTile Box)) cs)

drawState :: State -> Picture
drawState (State c d boxes i)
  = showWin (State c d boxes i)
  & atCoord c (player d)
  & pictureOfBoxes boxes
  & pictureOfMaze (nthMaze i)

-- The complete interaction

sokoban :: Interaction State
sokoban = Interaction initialState handleEvent drawState

-- The general interaction type

data Interaction world = Interaction
        world
        (String -> world -> world)
        (world -> Picture)

runInteraction :: Interaction s -> IO ()
runInteraction (Interaction state0 handle draw)
  = do hSetBuffering stdin NoBuffering
       blankScreen
       putStr (renderPicture (draw state0))
       go "" state0
  where
    go keys s = do
        new_keys <- getAllInput
        let (key,rest) = parse (keys ++ new_keys)
        unless (key == "Q") $ do
            let s' = handle key s
            blankScreen
            putStr (renderPicture (draw s'))
            go rest s'

    parse ('\ESC':'[':'C':r) = ("Right", r)
    parse ('\ESC':'[':'D':r) = ("Left", r)
    parse ('\ESC':'[':'A':r) = ("Up", r)
    parse ('\ESC':'[':'B':r) = ("Down", r)
    parse ('\ESC':r) = ("Esc", r)
    parse (c:r) = ([toUpper c], r)
    parse r = ([], r)



getAllInput :: IO String
getAllInput = nextChar
  where
    nextChar = do
        c <- getChar
        cs <- more
        return (c:cs)
    more = do
        r <- hReady stdin
        if r then nextChar else return []

blankScreen :: IO ()
blankScreen = putStr "\ESCc"


-- Resetable interactions

resetable :: Interaction s -> Interaction s
resetable (Interaction state0 handle draw)
  = Interaction state0 handle' draw
  where handle' key _ | key == "Esc" = state0
        handle' e s = handle e s

-- Start screen

startScreen :: Picture
startScreen = text "Sokoban!"

data SSState world = StartScreen | Running world

instance Eq s => Eq (SSState s) where
  StartScreen == StartScreen = True
  Running s == Running s' = s == s'
  _ == _ = False

withStartScreen :: Interaction s  -> Interaction (SSState s)
withStartScreen (Interaction state0 handle draw)
  = Interaction state0' handle' draw'
  where
    state0' = StartScreen

    handle' key StartScreen | key == " " = Running state0
    handle' _   StartScreen              = StartScreen
    handle' e   (Running s)              = Running (handle e s)

    draw' StartScreen = startScreen
    draw' (Running s) = draw s

-- Undoable interactions

-- We need to remember the current state, and all past states:

data WithUndo a = WithUndo a (List a)

withUndo :: Eq a => Interaction a -> Interaction (WithUndo a)
withUndo (Interaction state0 handle draw)
  = Interaction state0' handle' draw'
  where
    state0' = WithUndo state0 Empty


    handle' key (WithUndo s stack) | key == "U"
      = case stack of Entry s' stack' -> WithUndo s' stack'
                      Empty           -> WithUndo s Empty
    handle' e              (WithUndo s stack)
       | s' == s = WithUndo s stack
       | otherwise = WithUndo s' (Entry s stack)
      where s' = handle e s

    draw' (WithUndo s _) = draw s


-- The main function

exercise4 :: IO ()
exercise4 = runInteraction (resetable (withUndo (withStartScreen sokoban)))


mazes :: List Maze
mazes =
  Entry (Maze (C 1 1)       maze9) $
  Entry (Maze (C 0 0)       maze8) $
  Entry (Maze (C (-3) 3)    maze7) $
  Entry (Maze (C (-2) 4)    maze6) $
  Entry (Maze (C 0 1)       maze5) $
  Entry (Maze (C 1 (-3))    maze4) $
  Entry (Maze (C (-4) 3)    maze3) $
  Entry (Maze (C 0 1)       maze1) $
  Empty

maze1 :: Coord -> Tile
maze1 (C x y)
  | abs x > 4  || abs y > 4  = Blank
  | abs x == 4 || abs y == 4 = Wall
  | x ==  2 && y <= 0        = Wall
  | x ==  3 && y <= 0        = Storage
  | x >= -2 && y == 0        = Box
  | otherwise                = Ground

maze3 :: Coord -> Tile
maze3 (C (-5) (-5)) = Wall
maze3 (C (-5) (-4)) = Wall
maze3 (C (-5) (-3)) = Wall
maze3 (C (-5) (-2)) = Wall
maze3 (C (-5) (-1)) = Wall
maze3 (C (-5)   0 ) = Wall
maze3 (C (-5)   1 ) = Wall
maze3 (C (-5)   2 ) = Wall
maze3 (C (-5)   3 ) = Wall
maze3 (C (-5)   4 ) = Wall

maze3 (C (-4) (-5)) = Wall
maze3 (C (-4) (-4)) = Ground
maze3 (C (-4) (-3)) = Ground
maze3 (C (-4) (-2)) = Ground
maze3 (C (-4) (-1)) = Ground
maze3 (C (-4)   0 ) = Ground
maze3 (C (-4)   1 ) = Ground
maze3 (C (-4)   2 ) = Ground
maze3 (C (-4)   3 ) = Ground
maze3 (C (-4)   4 ) = Wall

maze3 (C (-3) (-5)) = Wall
maze3 (C (-3) (-4)) = Ground
maze3 (C (-3) (-3)) = Wall
maze3 (C (-3) (-2)) = Wall
maze3 (C (-3) (-1)) = Wall
maze3 (C (-3)   0 ) = Wall
maze3 (C (-3)   1 ) = Ground
maze3 (C (-3)   2 ) = Wall
maze3 (C (-3)   3 ) = Ground
maze3 (C (-3)   4 ) = Wall
maze3 (C (-3)   5 ) = Wall

maze3 (C (-2) (-5)) = Wall
maze3 (C (-2) (-4)) = Box
maze3 (C (-2) (-3)) = Ground
maze3 (C (-2) (-2)) = Ground
maze3 (C (-2) (-1)) = Ground
maze3 (C (-2)   0 ) = Wall
maze3 (C (-2)   1 ) = Ground
maze3 (C (-2)   2 ) = Box
maze3 (C (-2)   3 ) = Box
maze3 (C (-2)   4 ) = Ground
maze3 (C (-2)   5 ) = Wall

maze3 (C (-1) (-6)) = Wall
maze3 (C (-1) (-5)) = Wall
maze3 (C (-1) (-4)) = Ground
maze3 (C (-1) (-3)) = Ground
maze3 (C (-1) (-2)) = Ground
maze3 (C (-1) (-1)) = Ground
maze3 (C (-1)   0 ) = Wall
maze3 (C (-1)   1 ) = Ground
maze3 (C (-1)   2 ) = Ground
maze3 (C (-1)   3 ) = Box
maze3 (C (-1)   4 ) = Ground
maze3 (C (-1)   5 ) = Wall
maze3 (C (-1)   6 ) = Wall

maze3 (C   0  (-6)) = Wall
maze3 (C   0  (-5)) = Ground
maze3 (C   0  (-4)) = Ground
maze3 (C   0  (-3)) = Ground
maze3 (C   0  (-2)) = Ground
maze3 (C   0  (-1)) = Ground
maze3 (C   0    0 ) = Wall
maze3 (C   0    1 ) = Wall
maze3 (C   0    2 ) = Wall
maze3 (C   0    3 ) = Wall
maze3 (C   0    4 ) = Ground
maze3 (C   0    5 ) = Ground
maze3 (C   0    6 ) = Wall

maze3 (C   1  (-6)) = Wall
maze3 (C   1  (-5)) = Ground
maze3 (C   1  (-4)) = Ground
maze3 (C   1  (-3)) = Ground
maze3 (C   1  (-2)) = Ground
maze3 (C   1  (-1)) = Ground
maze3 (C   1    0 ) = Wall
maze3 (C   1    1 ) = Storage
maze3 (C   1    2 ) = Storage
maze3 (C   1    3 ) = Storage
maze3 (C   1    4 ) = Ground
maze3 (C   1    5 ) = Ground
maze3 (C   1    6 ) = Wall

maze3 (C   2  (-6)) = Wall
maze3 (C   2  (-5)) = Wall
maze3 (C   2  (-4)) = Ground
maze3 (C   2  (-3)) = Ground
maze3 (C   2  (-2)) = Ground
maze3 (C   2  (-1)) = Ground
maze3 (C   2    0 ) = Wall
maze3 (C   2    1 ) = Wall
maze3 (C   2    2 ) = Wall
maze3 (C   2    3 ) = Wall
maze3 (C   2    4 ) = Wall
maze3 (C   2    5 ) = Wall
maze3 (C   2    6 ) = Wall

maze3 (C   3  (-5)) = Wall
maze3 (C   3  (-4)) = Ground
maze3 (C   3  (-3)) = Ground
maze3 (C   3  (-2)) = Storage
maze3 (C   3  (-1)) = Ground
maze3 (C   3    0 ) = Wall

maze3 (C   4  (-5)) = Wall
maze3 (C   4  (-4)) = Wall
maze3 (C   4  (-3)) = Wall
maze3 (C   4  (-2)) = Wall
maze3 (C   4  (-1)) = Wall
maze3 (C   4    0 ) = Wall

maze3 _ = Blank

maze4 :: Coord -> Tile
maze4 (C x y)
  | abs x > 4  || abs y > 4      = Blank
  | abs x == 4 || abs y == 4     = Wall
  | x ==  2 && y <   0           = Wall
  | x >= -1 && y ==  1 && x <= 2 = Wall
  | x == -3 && y ==  1           = Wall
  | x ==  0 && y ==  3           = Wall
  | x ==  0 && y ==  0           = Wall
  | x ==  3 && y == -3           = Storage
  | x ==  1 && y ==  2           = Storage
  | x == -3 && y ==  2           = Storage
  | x ==  1 && y == -1           = Storage
  | x == -2 && y ==  1           = Box
  | x ==  2 && y ==  2           = Box
  | x <=  1 && y == -2 && x >= 0 = Box
  | otherwise                    = Ground

maze5 :: Coord -> Tile
maze5 (C x y)
  | abs x >  4 || abs y >  4           = Blank
  | abs x == 4 || abs y == 4           = Wall
  | x ==     1 && y <      0           = Wall
  | x ==    -3 && y ==    -2           = Wall
  | x <=     1 && x >     -2 && y == 0 = Wall
  | x >     -3 && x <      3 && y == 2 = Wall
  | x ==     3 && y >      1           = Storage
  | y ==    -2 && x <      0           = Box
  | y ==    -2 && x ==     2           = Box
  | y ==    0  && x ==     3           = Box
  | y == -1    && x > 1      && x < 4  = Storage
  | otherwise                          = Ground

maze6 :: Coord -> Tile
maze6 (C x y)
  | abs x > 3  || abs y > 5                 = Blank
  | abs x == 3 || (abs y == 5 && abs x < 4) = Wall
  | x == 0 && abs y < 4                     = Storage
  | x == -1 && (y == 0 || abs y == 2)       = Box
  | x == 1 && (abs y == 1 || abs y == 3)    = Box
  | x == (-2) &&  y == 1                    = Wall
  | otherwise                               = Ground

maze7 :: Coord -> Tile
maze7 (C x y)
  | abs x > 4  || abs y > 4   = Blank
  | abs x == 4 || abs y == 4  = Wall
  | not (x == 2)  && y == 2   = Wall
  | not (x == -2)  && y == -1 = Wall
  | x ==  3 && y == -3        = Storage
  | x == 2 && y == 2          = Box
  | otherwise                 = Ground

maze8 :: Coord -> Tile
maze8 (C x y)
  | abs x > 10 || abs y > 10    = Blank
  | x == 0 && y == 0            = Ground
  | abs x == 9 && abs y == 9    = Wall
  | abs x == 10 || abs y == 10  = Wall
  | x == y                      = Storage
  | abs x == abs y              = Box
  | x < 0 && x > (-9) && y == 0 = Box
  | x > 0 && x < 9 && y == 0    = Storage
  | otherwise                   = Ground

maze9 :: Coord -> Tile
maze9 (C x y)
  | abs x > 4  || abs y > 4                  = Blank
  | abs x == 4 || abs y == 4 || x == -3      = Wall
  | x == -2 && (y == 3 || y == 0)            = Wall
  | x == -1 &&  y == -1                      = Wall
  | x == -0 &&  y == 1                       = Wall
  | x ==  3 &&  y == 0                       = Wall
  | x <   0 && (y == 2 || y == -3)           = Storage
  | x == -1 &&  y == 1                       = Storage
  | x ==  0 && (y == 2 || y == 0 || y == -1) = Box
  | x ==  1 &&  y == -2                      = Box
  | x ==  2 &&  y == -3                      = Box
  | otherwise                                = Ground
