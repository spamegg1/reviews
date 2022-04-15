{-# LANGUAGE OverloadedStrings #-}
{-# OPTIONS_GHC -fno-warn-unused-binds #-}
import CodeWorld

main :: IO ()
main = exercise3

wall, ground, storage, box :: Picture
wall =    colored (grey 0.4) (solidRectangle 1 1)
ground =  colored yellow     (solidRectangle 1 1)
storage = colored white (solidCircle 0.3) & ground
box =     colored brown      (solidRectangle 1 1)

data Tile = Wall | Ground | Storage | Box | Blank

drawTile :: Tile -> Picture
drawTile Wall    = wall
drawTile Ground  = ground
drawTile Storage = storage
drawTile Box     = box
drawTile Blank   = blank

pictureOfMaze :: Picture
pictureOfMaze = draw21times (\r -> draw21times (\c -> drawTileAt (C r c)))

draw21times :: (Integer -> Picture) -> Picture
draw21times something = go (-10)
  where
    go :: Integer -> Picture
    go 11 = blank
    go n  = something n & go (n+1)

drawTileAt :: Coord -> Picture
drawTileAt c = atCoord c (drawTile (maze c))

maze :: Coord -> Tile
maze (C x y)
  | abs x > 4  || abs y > 4  = Blank
  | abs x == 4 || abs y == 4 = Wall
  | x ==  2 && y <= 0        = Wall
  | x ==  3 && y <= 0        = Storage
  | x >= -2 && y == 0        = Box
  | otherwise                = Ground

data Direction = R | U | L | D

data Coord = C Integer Integer

initialCoord :: Coord
initialCoord = C 0 0

atCoord :: Coord -> Picture -> Picture
atCoord (C x y) pic = translated (fromIntegral x) (fromIntegral y) pic

adjacentCoord :: Direction -> Coord -> Coord
adjacentCoord R (C x y) = C (x+1) y
adjacentCoord U (C x y) = C  x   (y+1)
adjacentCoord L (C x y) = C (x-1) y
adjacentCoord D (C x y) = C  x   (y-1)

-- Exercise 1

handleEvent :: Event -> Coord -> Coord
handleEvent (KeyPress key) c
    | key == "Right" = tryGoTo c R
    | key == "Up"    = tryGoTo c U
    | key == "Left"  = tryGoTo c L
    | key == "Down"  = tryGoTo c D
handleEvent _ c      = c

tryGoTo :: Coord -> Direction -> Coord
tryGoTo from d
  | isOk (maze to) = to
  | otherwise      = from
  where to = adjacentCoord d from

isOk :: Tile -> Bool
isOk Ground = True
isOk Storage = True
isOk _ = False

player :: Picture
player = translated 0 0.3 cranium
       & path [(0,0),(0.3,0.05)]
       & path [(0,0),(0.3,-0.05)]
       & path [(0,-0.2),(0,0.1)]
       & path [(0,-0.2),(0.1,-0.5)]
       & path [(0,-0.2),(-0.1,-0.5)]
  where cranium = circle 0.18 & sector (7/6*pi) (1/6*pi) 0.18

drawState :: Coord -> Picture
drawState c = atCoord c player & pictureOfMaze

exercise1 :: IO ()
exercise1 = interactionOf (C 0 1) (\_ c -> c) handleEvent drawState


-- Exercise 2

data State = State Coord Direction

initialState :: State
initialState = State (C 0 1) R

player2 :: Direction -> Picture
player2 R = translated 0 0.3 cranium
          & path [(0,0),(0.3,0.05)]
          & path [(0,0),(0.3,-0.05)]
          & path [(0,-0.2),(0,0.1)]
          & path [(0,-0.2),(0.1,-0.5)]
          & path [(0,-0.2),(-0.1,-0.5)]
  where cranium = circle 0.18
                & sector (7/6*pi) (1/6*pi) 0.18
player2 L = scaled (-1) 1 (player2 R) -- Cunning!
player2 U = translated 0 0.3 cranium
          & path [(0,0),(0.3,0.05)]
          & path [(0,0),(-0.3,0.05)]
          & path [(0,-0.2),(0,0.1)]
          & path [(0,-0.2),(0.1,-0.5)]
          & path [(0,-0.2),(-0.1,-0.5)]
  where cranium = solidCircle 0.18
player2 D = translated 0 0.3 cranium
          & path [(0,0),(0.3,-0.05)]
          & path [(0,0),(-0.3,-0.05)]
          & path [(0,-0.2),(0,0.1)]
          & path [(0,-0.2),(0.1,-0.5)]
          & path [(0,-0.2),(-0.1,-0.5)]
  where cranium = circle 0.18
                & translated   0.06  0.08 (solidCircle 0.04)
                & translated (-0.06) 0.08 (solidCircle 0.04)

handleEvent2 :: Event -> State -> State
handleEvent2 (KeyPress key) s
    | key == "Right" = tryGoTo2 s R
    | key == "Up"    = tryGoTo2 s U
    | key == "Left"  = tryGoTo2 s L
    | key == "Down"  = tryGoTo2 s D
handleEvent2 _ s      = s

tryGoTo2 :: State -> Direction -> State
tryGoTo2 (State from _) d
  | isOk (maze to) = State to d
  | otherwise      = State from d
  where to = adjacentCoord d from

drawState2 :: State -> Picture
drawState2 (State c d) = atCoord c (player2 d) & pictureOfMaze

exercise2 :: IO ()
exercise2 = interactionOf initialState (\_ c -> c) handleEvent2 drawState2

-- Exercise 3

resetableInteractionOf ::
    world -> (Double -> world -> world) ->
    (Event -> world -> world) -> (world -> Picture) ->
    IO ()
resetableInteractionOf state0 step handle draw
  = interactionOf state0 step handle' draw
  where handle' (KeyPress key) _ | key == "Esc" = state0
        handle' e s = handle e s

exercise3 :: IO ()
exercise3 = resetableInteractionOf initialState (\_ c -> c) handleEvent2 drawState2
