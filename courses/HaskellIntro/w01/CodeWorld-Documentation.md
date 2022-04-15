### Reference

- [Entry points](https://code.world/doc.html?shelf=help/haskell.shelf#1)
- [Pictures](https://code.world/doc.html?shelf=help/haskell.shelf#2)
- [Colors](https://code.world/doc.html?shelf=help/haskell.shelf#3)
- [Events](https://code.world/doc.html?shelf=help/haskell.shelf#4)
- [Debugging](https://code.world/doc.html?shelf=help/haskell.shelf#5)

### Reference (Reflex)

### Reference (Sketches)

### Editor

### Examples

| Safe Haskell | None        |
| ------------ | ----------- |
| Language     | Haskell2010 |

CodeWorld

Contents

- [Entry points](https://code.world/doc.html?shelf=help/haskell.shelf#g:1)
- [Pictures](https://code.world/doc.html?shelf=help/haskell.shelf#g:2)
- [Colors](https://code.world/doc.html?shelf=help/haskell.shelf#g:3)
- [Events](https://code.world/doc.html?shelf=help/haskell.shelf#g:4)
- [Debugging](https://code.world/doc.html?shelf=help/haskell.shelf#g:5)

Synopsis



# Entry points

drawingOf[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:drawingOf)

| :: [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) | The picture to show on the screen. |
| ------------------------------------------------------------ | ---------------------------------- |
| -> IO ()                                                     |                                    |

Draws a `Picture`. This is the simplest CodeWorld entry point.

Example: a program which draws a circle of radius 1 in the middle of canvas

```haskell
main = drawingOf $ circle 1
```

animationOf[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:animationOf)

| :: (Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)) | A function that produces animation frames, given the time in seconds. |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| -> IO ()                                                     |                                                              |

Shows an animation, with a picture for each time given by the parameter.

Example: a program showing a square which rotates once every two seconds

```haskell
main = animationOf rotatingSquare

rotatingSquare :: Double -> Picture
rotatingSquare seconds = rotated angle square
  where
    square = rectangle 2 2
    angle = pi * seconds
```

activityOf[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:activityOf)

| :: world                                                     | The initial state of the activity.                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| -> ([Event](https://code.world/doc.html?shelf=help/haskell.shelf#t:Event) -> world -> world) | The event handling function, which updates the state given an event. |
| -> (world -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)) | The visualization function, which converts the state into a picture to display. |
| -> IO ()                                                     |                                                              |

Runs an interactive CodeWorld program that responds to `Event`s. Activities can interact with the user, change over time, and remember information about the past.

Example: a program which draws a circle and changes its radius when user presses Up or Down keys on her keyboard

```haskell
 {-# LANGUAGE OverloadedStrings #-}
import CodeWorld

main = activityOf initialRadius updateRadius circle
   where
     initialRadius = 1

     updateRadius event radius =
       case event of
         KeyPress Up   -> radius + 1
         KeyPress Down -> radius - 1
         _               -> radius
```

debugActivityOf[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:debugActivityOf)

| :: world                                                     | The initial state of the interaction.                        |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| -> ([Event](https://code.world/doc.html?shelf=help/haskell.shelf#t:Event) -> world -> world) | The event handling function, which updates the state given an event. |
| -> (world -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)) | The visualization function, which converts the state into a picture to display. |
| -> IO ()                                                     |                                                              |

A version of `activityOf` which runs an interactive CodeWorld program in debugging mode. In this mode, the program gets controls to pause and manipulate time, and even go back in time to look at past states.

groupActivityOf[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:groupActivityOf)

| :: Int                                                       | The number of participants to expect. The participants will be numbered starting at 0. |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| -> StaticPtr (StdGen -> world)                               | The function to create initial state of the activity. `StdGen` parameter can be used to generate random numbers. |
| -> StaticPtr (Int -> [Event](https://code.world/doc.html?shelf=help/haskell.shelf#t:Event) -> world -> world) | The event handling function, which updates the state given a participant number and user interface event. |
| -> StaticPtr (Int -> world -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)) | The visualization function, which converts a participant number and the state into a picture to display. |
| -> IO ()                                                     |                                                              |

Runs an interactive multi-user CodeWorld program that is joined by several participants over the internet.

Example: a skeleton of a game for two players

```haskell
{-# LANGUAGE StaticPointers, OverloadedStrings #-}
import CodeWorld

main = groupActivityOf 2 init step view
  where
    init = static (\gen -> {- initialize state of the game world, possibly using random number generator -})
    step = static (\playerNumber event world -> {- modify world based on event occuring for given player -})
    view = static (\playerNumber world -> {- generate a picture that will be shown to given player in the given state of the world-})
```

unsafeGroupActivityOf[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:unsafeGroupActivityOf)

| :: Int                                                       | The number of participants to expect. The participants will be numbered starting at 0. |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| -> (StdGen -> world)                                         | The initial state of the activity.                           |
| -> (Int -> [Event](https://code.world/doc.html?shelf=help/haskell.shelf#t:Event) -> world -> world) | The event handling function, which updates the state given a participant number and user interface event. |
| -> (Int -> world -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)) | The visualization function, which converts a participant number and the state into a picture to display. |
| -> IO ()                                                     |                                                              |

A version of `groupActivityOf` that avoids static pointers, and does not check for consistency.



# Pictures

data Picture[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)

A design, diagram, or drawing that can be displayed and seen. In technical terms, a picture is an assignment of a color to every point of the coordinate plane. CodeWorld contains functions to create pictures from simple geometry primitives, to transform existing pictures, and to combine simpler pictures into more complex compositions.

Ultimately, a picture can be drawn on the screen using one of the CodeWorld entry points such as `drawingOf`.

data TextStyle[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:TextStyle)

Constructors

| Plain  | Plain letters with no style                     |
| ------ | ----------------------------------------------- |
| Bold   | Heavy, thick lettering used for emphasis        |
| Italic | Slanted script-like lettering used for emphasis |

data Font[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Font)

Constructors

| SansSerif       |      |
| --------------- | ---- |
| Serif           |      |
| Monospace       |      |
| Handwriting     |      |
| Fancy           |      |
| NamedFont !Text |      |

blank :: [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:blank)

A blank picture

polyline :: [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:polyline)

A thin sequence of line segments, with these points as endpoints

thickPolyline :: Double -> [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickPolyline)

A thick sequence of line segments, with given line width and endpoints

polygon :: [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:polygon)

A thin polygon with these points as vertices

thickPolygon :: Double -> [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickPolygon)

A thick polygon with this line width and these points as vertices

solidPolygon :: [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:solidPolygon)

A solid polygon with these points as vertices

curve :: [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:curve)

A smooth curve passing through these points.

thickCurve :: Double -> [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickCurve)

A thick smooth curve with this line width, passing through these points.

closedCurve :: [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:closedCurve)

A smooth closed curve passing through these points.

thickClosedCurve :: Double -> [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickClosedCurve)

A thick smooth closed curve with this line width, passing through these points.

solidClosedCurve :: [[Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:solidClosedCurve)

A solid smooth closed curve passing through these points.

rectangle :: Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:rectangle)

A thin rectangle, with this width and height

solidRectangle :: Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:solidRectangle)

A solid rectangle, with this width and height

thickRectangle :: Double -> Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickRectangle)

A thick rectangle, with this line width, and width and height

circle :: Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:circle)

A thin circle, with this radius

solidCircle :: Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:solidCircle)

A solid circle, with this radius

thickCircle :: Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickCircle)

A thick circle, with this line width and radius

arc :: Double -> Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:arc)

A thin arc, starting and ending at these angles, with this radius

Angles are in radians.

sector :: Double -> Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:sector)

A solid sector of a circle (i.e., a pie slice) starting and ending at these angles, with this radius

Angles are in radians.

thickArc :: Double -> Double -> Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:thickArc)

A thick arc with this line width, starting and ending at these angles, with this radius.

Angles are in radians.

lettering :: Text -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:lettering)

A rendering of text characters.

styledLettering :: [TextStyle](https://code.world/doc.html?shelf=help/haskell.shelf#t:TextStyle) -> [Font](https://code.world/doc.html?shelf=help/haskell.shelf#t:Font) -> Text -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:styledLettering)

A rendering of text characters onto a Picture, with a specific choice of font and style.

colored :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:colored)

A picture drawn entirely in this color.

coloured :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:coloured)

A picture drawn entirely in this colour.

translated :: Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:translated)

A picture drawn translated in these directions.

scaled :: Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:scaled)

A picture scaled by these factors in the x and y directions. Scaling by a negative factoralso reflects across that axis.

dilated :: Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:dilated)

A picture scaled uniformly in all directions by this scale factor. Dilating by a negative factor also reflects across the origin.

rotated :: Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:rotated)

A picture rotated by this angle about the origin.

Angles are in radians.

reflected :: Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:reflected)

A picture reflected across a line through the origin at this angle, in radians. For example, an angle of 0 reflects the picture vertically across the x axis, while an angle of `pi / 2` reflects the picture horizontally across the y axis.

clipped :: Double -> Double -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:clipped)

A picture clipped to a rectangle around the origin with this width and height.

pictures :: [[Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)] -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:pictures)

(<>) :: Semigroup a => a -> a -> a[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:-60--62-)

(&) :: [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) -> [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture) infixr 0[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:-38-)

Binary composition of pictures.

coordinatePlane :: [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:coordinatePlane)

A coordinate plane. Adding this to your pictures can help you measure distances more accurately.

Example: `main = drawingOf (myPicture <> coordinatePlane) myPicture = ...`

codeWorldLogo :: [Picture](https://code.world/doc.html?shelf=help/haskell.shelf#t:Picture)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:codeWorldLogo)

The CodeWorld logo.

type Point = (Double, Double)[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)

A point in two dimensions. A point is written with the x coordinate first, and the y coordinate second. For example, (3, -2) is the point with x coordinate 3 a y coordinate -2.

translatedPoint :: Double -> Double -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:translatedPoint)

Moves a given point by given x and y offsets

```haskell
>>> translatedPoint 1 2 (10, 10)
(11.0, 12.0)
>>> translatedPoint (-1) (-2) (0, 0)
(-1.0, -2.0)
```

rotatedPoint :: Double -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:rotatedPoint)

Rotates a given point by given angle, in radians

```haskell
>>> rotatedPoint 45 (10, 0)
(7.071, 7.071)
```

reflectedPoint :: Double -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:reflectedPoint)

Reflects a given point across a line through the origin at this angle, in radians. For example, an angle of 0 reflects the point vertically across the x axis, while an angle of `pi / 2` reflects the point horizontally across the y axis.

scaledPoint :: Double -> Double -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:scaledPoint)

Scales a given point by given x and y scaling factor. Scaling by a negative factor also reflects across that axis.

```haskell
>>> scaledPoint 2 3 (10, 10)
(20, 30)
```

dilatedPoint :: Double -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) -> [Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:dilatedPoint)

Dilates a given point by given uniform scaling factor. Dilating by a negative factor also reflects across the origin.

```haskell
>>> dilatedPoint 2 (10, 10)
(20, 20)
```

type Vector = (Double, Double)[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector)

A two-dimensional vector

vectorLength :: [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:vectorLength)

The length of the given vector.

```haskell
>>> vectorLength (10, 10)
14.14
```

vectorDirection :: [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:vectorDirection)

The counter-clockwise angle, in radians, that a given vector make with the X-axis

```haskell
>>> vectorDirection (1,0)
0.0
>>> vectorDirection (1,1)
0.7853981633974483
>>> vectorDirection (0,1)
1.5707963267948966
```

vectorSum :: [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:vectorSum)

The sum of two vectors

vectorDifference :: [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:vectorDifference)

The difference of two vectors

scaledVector :: Double -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:scaledVector)

Scales a given vector by a given scalar multiplier.

```haskell
>>> scaledPoint 2 (10, 10)
(20, 20)
```

rotatedVector :: Double -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:rotatedVector)

Rotates a given vector by a given angle in radians

```haskell
>>> rotatedVector pi (1.0, 0.0)
(-1.0, 1.2246467991473532e-16)
>>> rotatedVector (pi / 2) (1.0, 0.0)
(6.123233995736766e-17, 1.0)
```

dotProduct :: [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> [Vector](https://code.world/doc.html?shelf=help/haskell.shelf#t:Vector) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:dotProduct)

The dot product of two vectors



# Colors

data Color[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)

Constructors

| RGBA !Double !Double !Double !Double |      |
| ------------------------------------ | ---- |
|                                      |      |

type Colour = [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Colour)

pattern RGB :: Double -> Double -> Double -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:RGB)

pattern HSL :: Double -> Double -> Double -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:HSL)

black :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:black)

white :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:white)

red :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:red)

green :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:green)

blue :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:blue)

yellow :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:yellow)

orange :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:orange)

brown :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:brown)

pink :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:pink)

purple :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:purple)

gray :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:gray)

grey :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:grey)

mixed :: [[Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)] -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:mixed)

lighter :: Double -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:lighter)

light :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:light)

darker :: Double -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:darker)

dark :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:dark)

brighter :: Double -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:brighter)

bright :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:bright)

duller :: Double -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:duller)

dull :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:dull)

translucent :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:translucent)

assortedColors :: [[Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color)][#](https://code.world/doc.html?shelf=help/haskell.shelf#v:assortedColors)

An infinite list of colors.

hue :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:hue)

saturation :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:saturation)

luminosity :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:luminosity)

alpha :: [Color](https://code.world/doc.html?shelf=help/haskell.shelf#t:Color) -> Double[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:alpha)



# Events

data Event[#](https://code.world/doc.html?shelf=help/haskell.shelf#t:Event)

An event initiated by the user.

Values of this type represent events that the user triggers when using an interactive program.

Key events describe the key as `Text`. Most keys are represented by a single character text string, with the capital letter or other symbol from the key. Keys that don't correspond to a single character use longer names from the following list. Keep in mind that not all of these keys appear on all keyboards.

- Up, Down, Left, and Right for the cursor keys.
- F1, F2, etc. for function keys.
- Backspace
- Tab
- Enter
- Shift
- Ctrl
- Alt
- Esc
- PageUp
- PageDown
- End
- Home
- Insert
- Delete
- CapsLock
- NumLock
- ScrollLock
- PrintScreen
- Break
- Separator
- Cancel
- Help

Constructors

| KeyPress !Text                                               |      |
| ------------------------------------------------------------ | ---- |
| KeyRelease !Text                                             |      |
| PointerPress ![Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) |      |
| PointerRelease ![Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) |      |
| PointerMovement ![Point](https://code.world/doc.html?shelf=help/haskell.shelf#t:Point) |      |
| TextEntry !Text                                              |      |
| TimePassing !Double                                          |      |



# Debugging

trace :: Text -> a -> a[#](https://code.world/doc.html?shelf=help/haskell.shelf#v:trace)

Prints a debug message to the CodeWorld console when a value is forced. This is equivalent to the similarly named function in `Trace`, except that it sets appropriate buffering to use the CodeWorld console.

Produced by [Haddock](http://www.haskell.org/haddock/) version 2.20.0