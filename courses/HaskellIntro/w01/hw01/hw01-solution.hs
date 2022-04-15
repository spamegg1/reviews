{-# OPTIONS_GHC -fno-warn-unused-binds #-}
import CodeWorld

main :: IO ()
main = exercise1

-- Fill in the blanks! (When I say blanks, I mean undefineds)

-- Exercise 1

lightBulb :: Color -> Double ->  Picture
lightBulb c dx = colored c (translated 0 dx (solidCircle 1))

frame :: Picture
frame = rectangle 2.5 7.5

trafficLight :: Color -> Color -> Color -> Picture
trafficLight c1 c2 c3 =
  lightBulb c1 2.5 &
  lightBulb c2 0   &
  lightBulb c3 (-2.5) &
  frame

trafficController :: Integer -> Picture
trafficController s
  | s >= 0 && s <= 2 = trafficLight black black  green
  | s == 3           = trafficLight black yellow black
  | s >= 4 && s <= 6 = trafficLight red   black  black
  | otherwise        = trafficLight red   yellow black


trafficLightAnimation :: Double -> Picture
trafficLightAnimation t = trafficController (round t `mod` 8)

exercise1 :: IO ()
exercise1 = animationOf trafficLightAnimation

-- Exercise 2


tree :: Picture -> Integer -> Picture
tree b 0 = b
tree b n = translated 0 1 (rotated (pi/10) (tree b (n-1)) & rotated (- pi/10) (tree b (n-1))) &
           path [(0,0),(0,1)]

blossom :: Double -> Picture
blossom t = colored yellow (solidCircle ((min t 10)/50))

myAnimation :: Double -> Picture
myAnimation t = tree (blossom t) 8

exercise2 :: IO ()
exercise2 = animationOf myAnimation

-- Exercise 3

wall, ground, storage, box :: Picture
wall =    colored (grey 0.4) (solidRectangle 1 1)
ground =  colored yellow     (solidRectangle 1 1)
storage = solidCircle 0.3 & ground
box =     colored brown      (solidRectangle 1 1)

drawTile :: Integer -> Picture
drawTile 1 = wall
drawTile 2 = ground
drawTile 3 = storage
drawTile 4 = box
drawTile _ = blank

pictureOfMaze :: Picture
pictureOfMaze = drawRows (-10)

drawRows :: Integer -> Picture
drawRows 11 = blank
drawRows r  = drawCols r (-10) & drawRows (r+1)

drawCols :: Integer -> Integer -> Picture
drawCols _ 11 = blank
drawCols r c = drawTileAt r c & drawCols r (c+1)

drawTileAt :: Integer -> Integer -> Picture
drawTileAt r c = translated (fromIntegral r) (fromIntegral c) (drawTile (maze r c))

maze :: Integer -> Integer -> Integer
maze x y
  | abs x > 4  || abs y > 4  = 0
  | abs x == 4 || abs y == 4 = 1
  | x ==  2 && y <= 0        = 1
  | x ==  3 && y <= 0        = 3
  | x >= -2 && y == 0        = 4
  | otherwise                = 2

exercise3 :: IO ()
exercise3 = drawingOf pictureOfMaze
