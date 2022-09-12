[![Open Source Society University - Computer Science](https://img.shields.io/badge/OSSU-computer--science-blue.svg)](https://github.com/ossu/computer-science)

The fifth and last course of the Coursera Functional Programming in Scala Specialization.

Interactively views temperatures and deviations all over the world from 1975 to 2015.

An on-line working version of the project:
https://s3-eu-west-1.amazonaws.com/scala-capstone/index.html

Temperature data (not included in repository, 444 MB zip file) is from: http://alaska.epfl.ch/files/scala-capstone-data.zip

-   [Introduction](#Introduction)
    -   [Before you start coding](#Before-you-start-coding)
    -   [Notes for Spark users](#Notes-for-Spark-users)
-   [1st milestone data extraction](#1st-milestone-data-extraction)
    -   [Milestone overview](#Milestone-overview)
    -   [Data files](#Data-files)
    -   [Stations](#Stations)
    -   [Temperatures](#Temperatures)
    -   [Data extraction](#Data-extraction)
-   [2nd milestone basic visualization](#2nd-milestone-basic-visualization)
    -   [Milestone overview](#Milestone-overview)
    -   [Spatial interpolation](#Spatial-interpolation)
    -   [Linear interpolation](#Linear-interpolation)
    -   [Visualization](#Visualization)
    -   [Appendix on scrimage cheat sheet](#Appendix-on-scrimage-cheat-sheet)
        -   [Image type and companion object.](#Image-type-and-companion-object.)
-   [3rd milestone interactive visualization](#3rd-milestone-interactive-visualization)
    -   [Milestone overview](#Milestone-overview)
    -   [Tile generation](#Tile-generation)
    -   [Integration with a Web application](#Integration-with-a-Web-application)
    -   [Future integration with a more complete Web application](#Future-integration-with-a-more-complete-Web-application)
-   [4th milestone Data manipulation](#4th-milestone-Data-manipulation)
    -   [Milestone overview](#Milestone-overview)
    -   [Grid generation](#Grid-generation)
    -   [Average and deviation computation](#Average-and-deviation-computation)
-   [5th milestone value-added information visualization](#5th-milestone-value-added-information-visualization)
    -   [Visualization](#Visualization)
    -   [Deviation tiles generation](#Deviation-tiles-generation)
-   [6th (and last) milestone user interface polishing](#6th-(and-last)-milestone-user-interface-polishing)
    -   [Layers](#Layers)
    -   [Signals](#Signals)
        -   [Reminder on Signals](#Reminder-on-Signals)
    -   [Methods to implement](#Methods-to-implement)
    -   [Running the Web application](#Running-the-Web-application)

# Introduction
Download the assignment([https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3/observatory.zip](https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3/observatory.zip "assignment")) and the [dataset](https://moocs.scala-lang.org/~dockermoocs/scala-capstone-data.zip) and extract them somewhere on your file system. The assignment archive contains an sbt project starter, while the dataset only contains the data you are going to use.

Note that the sbt project requires Java 8. Be sure that you have this version of the JVM installed on your environment.

First, copy the content of the dataset (the “resources” directory) into the directory **observatory/src/main/**, so that the **.csv** files are located under the **observatory/src/main/resources/** directory.

While the whole capstone project is graded via a single assignment, we divided the whole project into 6 milestones (one per week). Concretely, this means that you will hack on the same code base from the beginning to the end, and that the starter project already contains some files that are going to be used by later milestones.

Furthermore, you will have to configure which milestones should be graded. You can achieve that by changing the **Grading.milestone** value to the milestone / week number that you completed. This value is initially set to 1. After you completed the 1st milestone you should set it to 2, and so on.

In the grader output, you can easily identify the tests which are relevant to a given milestone: tests names are prefixed by the number of the milestone and its name (e.g. “#2 - Raw data display”). If you have any errors, the grader will clearly indicate where it is deducting points in both current and past milestones.

Note that since there only is a single assignment, you will only get a passing grade after having completed all milestones. Every completed milestone will add points to the total, until you achieve a passing grade in the final project milestone.

To grade your work, run the following sbt command (after sbt is launched and shows its prompt):
```sh
> submit <your-email> <your-token>
```
Where, `<your-email>` is the email associated with your Coursera’s account, and `<your-token>` is the token shown in the “How to submit” section on the top of this page.

The grader uses a JVM with limited memory: only 1.5 GB are available. It means that even if your code runs on your machine, it might fail on the grader. It is your job to design a program that fits in the available memory at run-time.

Our goal in this project is to give you as much freedom as possible in the solution space. Unfortunately, in order to be able to grade your work, we will ask you to implement some methods, for which we have fixed the type signature, and which may influence your solution. For instance, most of them are using Scala’s standard **Iterable** datatype, but not all concrete implementations of **Iterable** may scale to the high volume of data required by the project (the grader is not going to use a high volume of data, though). 

So, while you must not change the code that is provided with the project, your actual implementation should use appropriate and efficient data types in order to perform incremental and parallel computations. For this purpose, we have added several dependencies to the build: [Spark](http://spark.apache.org/), [Akka Streams](http://doc.akka.io/docs/akka/2.4/scala/stream/), [Monix](https://monix.io/) and [fs2](http://fs2.co/). You can use any of these libraries, if you want to, or just use the standard Scala library. However, note that the provided build just makes these libraries available in the class path, but it does not configure their execution environment.

If you decide to use one of the above libraries, you might find it easier to implement the actual functionality in separate methods with different signatures. You can just consider the provided methods as "probes" into your code that the grader can use. For instance, if you want to use Spark RDDs and we provide a **locateTemperatures** method that requires an **Iterable**, you could implement it in the following way:
```scala
// Provided method:
def locationYearlyAverageRecords(
  records: Iterable[(LocalDate, Location, Temperature)]
): Iterable[(Location, Temperature)] =
  sparkAverageRecords(sc.parallelize(records)).collect().toSeq
 
// Added method:
def sparkAverageRecords(
  records: RDD[(LocalDate, Location, Temperature)]
): RDD[(Location, Temperature)] =
  ??? // actual work done here
```
Ultimately, you'll have to write your own **Main** to generate the map, so you have the freedom to organize your code the way you want. The **Main** doesn't have to use the provided methods, you can simply chain your own methods together instead. We want you to think about program structure, so don't feel too boxed in by the provided methods.

Last, note that there is a **src/test/** directory with test files for each milestone.

We recommend that you write tests here to check your solutions, but do not remove the existing code.

## Before you start coding

Before you start coding, there are two files that you should know about. First, **package.scala** introduces type aliases that will be used throughout the project:
```scala
type Temperature = Double
type Year = Int
```
We will introduce them again when appropriate. Second, **models.scala** has a few useful case classes:
```scala
case class Location(lat: Double, lon: Double)
case class Tile(x: Int, y: Int, zoom: Int)
case class GridLocation(lat: Int, lon: Int)
case class CellPoint(x: Double, y: Double)
case class Color(red: Int, green: Int, blue: Int)
```
There is one case class for every coordinate system that we use, and an additional **Color** case class that may be useful for creating images. We will introduce each one of these again when relevant. **You are free to add methods to these case classes if you deem it appropriate, but you must not change their parameters.**

## Notes for Spark users

The output of the grader is limited to 64 kB, but Spark produces a lot of logs by default. Often, this makes it impossible to read the whole grader feedback. Consequently, we suggest you to tune the log level of Spark like so:
```scala
import org.apache.log4j.{Level, Logger}
Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
```
You may also want to refer to the [Dataset vs DataFrame vs RDD](https://www.coursera.org/learn/scala-spark-big-data/lecture/yrfPh/datasets) discussion at the end of the last video of the [Spark course](https://www.coursera.org/learn/scala-spark-big-data) so that you can choose the best API for this project.

# 1st milestone data extraction

## Milestone overview

First milestone consists in extracting meaningful information from dataset. The methods to implement live in the `Extraction.scala` file. You are given several `.csv` files containing two kinds of data:
- Weather station’s locations (`stations.csv` file) ;
- Temperature records for a year (files `1975.csv`, `1976.csv`, etc.).
Your goal is to merge the data from these sources to get a series of information of the form `date × location × temperature`. 

You can monitor your progress by submitting your work at any time during the development of this milestone. Your submission token and the list of your graded submissions is available on this page.

*Reminder*: You can’t get a 10/10 score an an individual milestone (rather only when all milestones are completed).
The maximum grade you can get in this milestone is 2.33.

## Data files
The data you will use comes from the National Center for Environmental Information of the United States. Our files use the comma-separated values format: each line contains an information record, which is itself made of several columns.

## Stations
The `stations.csv` file contains one row per weather station, with the following columns:
|STN identifier|WBAN identifier|	Latitude|	Longitude|
|:--:|:--:|:--:|:--:|

You might have noticed that there are two identifiers. Indeed, weather stations are uniquely identified by the compound key `(STN, WBAN)`.

Note that on some lines some columns might be empty. Let’s illustrate this with the following excerpt:
```
010013,,,
724017,03707,+37.358,-078.438
724017,,+37.350,-078.433
```
Here, the first line describes a station whose 
- STN identifier is 010013, with 
- no WBAN identifier and 
- no GPS coordinates.

The second line describes a station whose
- STN identifier is 724017, 
- WBAN identifier is 03707, 
- latitude is 37.358 and 
- longitude is -078.438.

Finally, third line describes a station whose 
- STN id is (again) 724017,
- WBAN identifier is missing, 
- latitude is 37.350 and 
- longitude is -078.433.

## Temperatures
The temperature files contain one row per day of the year, with the following columns:
|STN identifier| WBAN identifier| Month| Day| Temperature (in degrees Fahrenheit)|
|:--:|:--:|:--:|:--:|:--:|
- The `STN` and `WBAN` identifiers refer to the weather station’s identifiers.
- The temperature field contains a decimal value (or 9999.9 if missing).
- The year number is given in the file name. Again, all columns are not always provided for each line.

Here is an hypothetical excerpt of such files:
```
010013,,11,25,39.2
724017,,08,11,81.14
724017,03707,12,06,32
724017,03707,01,29,35.6
```
Here, the lines respectively indicate that:
- The average temperature was 39.2 degrees Fahrenheit on November 25th at the station whose `STN` identifier is 010013.
- The average temperature was 81.1 °F on August 11th at the station whose `STN` identifier is 724017.
- The average temperature was 32 °F on December 6th at the station whose `WBAN` identifier is 03707.
- At the same station, the average temperature was 35.6 °F on January 29th.

## Data extraction

To make our method signatures as clear as possible, we've introduced the following global type aliases in `package.scala`:
```scala
type Temperature = Double // °C
type Year = Int
```
In this project, Temperature will always represent a (type `Double`) of °C. We're also providing you with a case class for location, defined in `models.scala` as:
```scala
case class Location(lat: Double, lon: Double)
```
You will first have to implement a method `locateTemperatures` with the following signature:
```scala
def locateTemperatures(
  year            : Year,
  stationsFile    : String,
  temperaturesFile: String
): Iterable[(LocalDate, Location, Temperature)]
```
This method should return the list of all the temperature records converted in degrees Celsius along with their date and location (ignore data coming from stations that have no GPS coordinates). You should not round the temperature values.

The file paths are resource paths, so they must be absolute locations in your class path (so that you can read them with `getResourceAsStream`). For instance, the path for the resource file `1975.csv` is `/1975.csv`, and loading it using `scala.io.Source` can be achieved as follows:
```scala
val path = "/1975.csv"
Source.fromInputStream(getClass.getResourceAsStream(path), "utf-8")
```
With the data given in the examples, the `locateTemperatures` method would return the following sequence:
```scala
Seq(
  (LocalDate.of(2015, 8, 11), Location(37.35, -78.433), 27.3),
  (LocalDate.of(2015, 12, 6), Location(37.358, -78.438), 0.0),
  (LocalDate.of(2015, 1, 29), Location(37.358, -78.438), 2.0)
)
```

In order to study the climate we want to remove variations due to seasons. So, we want to compute average temperature, over a year, for every station. To achieve that, you will have to implement the following method:
```scala
def locationYearlyAverageRecords(
  records: Iterable[(LocalDate, Location, Temperature)]
): Iterable[(Location, Temperature)]
```
This method should return average temperature at each location, over a year. For instance, with the data given in the examples, this method would return the following sequence:
```scala
Seq(
  (Location(37.35, -78.433), 27.3),
  (Location(37.358, -78.438), 1.0)
)
```
Note that the method signatures use the collection type `Iterable`, so, at the end, you will have to produce such values, but your internal implementation might use some other data type, if you think that it would have better performance.

# 2nd milestone basic visualization
## Milestone overview
This milestone consists in producing images showing the content of the temperature records. You will have to complete the file **Visualization.scala**. But first, remember to update the grading milestone number:
```scala
val milestone: Int = 2
```
**Reminder**: You can’t get a 10/10 score an an individual milestone (rather only when all milestones are completed). The maximum grade you can get in this milestone is **4.89**.

Your records contain the average temperature over a year, for each station’s location. Your work consists in building an image of 360×180 pixels, where each pixel shows the temperature at its location. The point at latitude 0 and longitude 0 (the intersection between the Greenwich meridian and the equator) will be at the center of the image:
![mercator projection](mercator.svg)

In this figure, the red crosses represent the weather stations. As you can see, you will have to spatially interpolate the data in order to guess the temperature corresponding to the location of each pixel (such a pixel is represented by a green square in the picture). Then you will have to convert this temperature value into a pixel color based on a color scale:

![colors](color-scale.png)

This color scale means that
- a temperature of 60°C or above should be represented in white (255, 255, 255, 255),
- a temperature of 32°C should be represented in red (255, 0, 0, 255),
- a temperature of 12°C should be represented in yellow (255, 255, 0, 255),
and so on.

For temperatures between thresholds, say, between 12°C and 32°C, you will have to compute a linear interpolation between the yellow and red colors. Here are the RGB values of these colors:
| Temperature (°C) | Red | Green | Blue |
|:--: | :--: | :--: | :--:|
|60| 255| 255| 255|
|32| 255|   0|   0|
|12| 255| 255|   0|
|0 |   0| 255| 255|
|-15|   0|   0| 255|
|-27| 255|   0| 255|
|-50|  33|   0| 107|
|-60|   0|   0|   0|

## Spatial interpolation
You will have to implement the method `predictTemperature`. This method takes a sequence of known temperatures at the given locations, and a location where we want to guess the temperature, and returns an estimate based on the inverse distance weighting algorithm:  https://en.wikipedia.org/wiki/Inverse_distance_weighting (you can use any `p` value greater or equal to 2; try and use whatever works best for you!). To approximate distance between two locations, we suggest you use great-circle distance formula: https://en.wikipedia.org/wiki/Great-circle_distance

Note that the great-circle distance formula is known to have rounding errors for short distances (a few meters), but that’s not a problem for us because we don’t need such a high degree of precision. Thus, you can use the first formula given on the Wikipedia page, expanded to cover some edge cases like equal locations and antipodes:
- `deltaSigma = 0` for equal points, 
- `deltaSigma = pi` for antipodes, and
- `arccos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(abs(lon1 - lon2)))`
- `dist = r * deltaSigma`

![great circle angle formula](great-circle-angle-formula.svg)

However, running the inverse distance weighting algorithm with small distances will result in huge numbers (since we divide by the distance raised to the power of `p`), which can be a problem. A solution to this problem is to directly use the known temperature of the close (less than 1 km) location as a prediction.

## Linear interpolation
We're providing you with a simple case class for representing color; you can see Color's documentation in `models.scala` for more information.
```scala
case class Color(red: Int, green: Int, blue: Int)
```
You will have to implement the following method:
```scala
def interpolateColor(
  points: Iterable[(Temperature, Color)], 
   value: Temperature
): Color
```
This method takes a sequence of reference temperature values and their associated color, and a temperature value, and returns an estimate of the color corresponding to the given value, by applying a linear interpolation algorithm: https://en.wikipedia.org/wiki/Linear_interpolation

Note that the given points are not sorted in a particular order.

## Visualization

Once you have completed the above steps you can implement the `visualize` method to build an image (using the [scrimage](https://index.scala-lang.org/sksamuel/scrimage) library) where each pixel shows the temperature corresponding to its location.
```scala
def visualize(
  temperatures: Iterable[(Location, Temperature)],
        colors: Iterable[(Temperature, Color)]
): Image
```

Note that 
- the `(x,y)` coordinates of the top-left pixel is `(0,0)`, and
- the `x` axis grows to the right and the `y` axis grows to the bottom, to `(359,179)`, whereas 
- the latitude and longitude origin, `(0,0)`, is at the center of the image `(180,90)`,  and
- the top-left pixel `(0,0)` has GPS coordinates `(90, -180)`, and
- the top-right pixel `(359,0)` has GPS coordinates `(90, 179)`, and
- the bottom-left pixel `(0,179)` has GPS coordinates `(-89, -180)`, and
- the bottom-right pixel `(359,179)` has GPS coordinates `(-89, 179)`.

So... 
- pixel's x location goes from 0 to 359... 
- where longitude goes from -180 to 179 
- this means that `longitude = x - 180`
- pixel's y location goes from 0 to 179... 
- where latitude goes from 90 to -89 
- this means that `latitude = 90 - y`

## Appendix on scrimage cheat sheet

Here is a description of scrimage’s API parts that are relevant for your work.

-   **ImmutableImage** [https://www.javadoc.io/doc/com.sksamuel.scrimage/scrimage-core/4.0.12/com/sksamuel/scrimage/ImmutableImage.html](https://www.javadoc.io/doc/com.sksamuel.scrimage/scrimage-core/4.0.12/com/sksamuel/scrimage/ImmutableImage.html "doc")
-   A simple way to construct an image is to use the constructor **Image.wrapPixels** ([https://www.javadoc.io/static/com.sksamuel.scrimage/scrimage-core/4.0.12/com/sksamuel/scrimage/ImmutableImage.html#wrapPixels-int-int-com.sksamuel.scrimage.pixels.Pixel:A-com.sksamuel.scrimage.metadata.ImageMetadata-](https://www.javadoc.io/static/com.sksamuel.scrimage/scrimage-core/4.0.12/com/sksamuel/scrimage/ImmutableImage.html#wrapPixels-int-int-com.sksamuel.scrimage.pixels.Pixel:A-com.sksamuel.scrimage.metadata.ImageMetadata- "doc")). It takes as parameters the image width and height, the pixels of the image as a flat `Array[Pixel]`, and the image metadata (of type **ImageMetadata**)
  
  The array must contain exactly width × height elements, in the following order: the first element is the top-left pixel, followed by all the pixels of the top row, followed by the other rows. You can create an instance of **ImageMetadata** by calling the method **empty** as shown in the code snippet below:
```scala
// Create an image of 200 by 150 pixels
val width = 200
val height = 150
val pixels =
  for
    y <- 0 until width
    x <- 0 until height
    red = 0
    green = 0
    blue = 0
    alpha = 0
  yield Pixel(x, y, red, green, blue, alpha)

ImmutableImage.wrapPixels(width, height, pixels.toArray, ImageMetadata.empty)
```
-   A simple way to construct a pixel from RGB values is to use this constructor: [https://www.javadoc.io/doc/com.sksamuel.scrimage/scrimage-core/latest/com/sksamuel/scrimage/pixels/Pixel.html#%3Cinit%3E(int,int,int,int,int,int)](https://www.javadoc.io/doc/com.sksamuel.scrimage/scrimage-core/latest/com/sksamuel/scrimage/pixels/Pixel.html#%3Cinit%3E(int,int,int,int,int,int) "doc"). The first two parameters are **x** and **y**, which are the location of the constructed pixel.

- To write an image into a PNG file, use one of the extension methods provided in the package object under **com.sksamuel.scrimage.implicits**. Those implicits are already imported for you. For instance, **myImage.output(new java.io.File("target/some-image.png"))**
```scala
implicit class RichImmutableImage(image: ImmutableImage) {
  def output(file: File)(implicit writer: ImageWriter): File = image.output(writer, file)
  def output(path: Path)(implicit writer: ImageWriter): Path = image.output(writer, path)
  def output(path: String)(implicit writer: ImageWriter): Path = image.output(writer, path)
}
```

- To check that some predicate holds for all the pixels of an image, use the **forall** extension method provided under the package object **com.sksamuel.scrimage.implicits**, which is imported for you:
```scala
def forall(predicate: Pixel => Boolean): Boolean
```

Also, note that `scrimage` defines a `Color` type, which could be ambiguous with our `Color` definition. Beware to not import `scrimage`’s `Color`.

# 3rd milestone interactive visualization
## Milestone overview
This milestone consists in producing images compatible with most Web based map visualization tools, so that you can see your data in an interactive Web page. You will have to complete the file **Interaction.scala**. But first, remember to update the grading milestone number:
```scala
val milestone: Int = 3
```
**Reminder**: You can’t get a 10/10 score an an individual milestone (rather only when all milestones are completed). The maximum grade you can get in this milestone is **6.33**.

The end result will look like the following (for 2015 with _p = 6_):
![temperatures](temperatures.png)

In Web based mapping applications, the whole map is broken down into small images of size 256×256 pixels, called tiles. Each tile shows a part of the map at a given location and zoom level.

Your work consists in producing these tiles using Web Mercator projection: https://en.wikipedia.org/wiki/Web_Mercator_projection

## Tile generation
To describe the position and size of tiles, we need to introduce the tile coordinate system: https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#X_and_Y which is composed of an x value, a y value and a zoom level.

Coordinates in this system are represented by the Tile case class, defined in `models.scala`:
```scala
case class Tile(x: Int, y: Int, zoom: Int)
```
Remember that you are free to add methods to this case class, but that you should not remove or rename its fields.
You have to implement the following two methods:
```scala
def tileLocation(tile: Tile): Location
```
This method converts a tile's geographic position to its corresponding GPS coordinates, by applying the Web Mercator [projection](http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Mathematics).
```scala
def tile(
  temperatures: Iterable[(Location, Temperature)],
  colors      : Iterable[(Temperature, Color)],
  tile        : Tile
): Image
```

This method returns a 256×256 image showing the given temperatures, using the given color scale, at the location corresponding to the given zoom, x and y values.

Note that the pixels of the image must be a little bit transparent so that when we will overlay the tile on the map, the map will still be visible. We recommend using an alpha value of 127.

*Hint*: you will have to compute the corresponding latitude and longitude of each pixel within a tile. A simple way to achieve that is to rely on the fact that each pixel in a tile can be thought of a [subtile](https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Subtiles) at a higher zoom level (256 = 2⁸):

## Integration with a Web application
Once you are able to generate tiles, you can embed them in a Web page. To achieve this you first have to generate all the tiles for zoom levels going from 0 to 3. (Actually you don’t have to generate all the tiles, since this operation consumes a lot of CPU. You can choose to generate tiles for just one zoom level, e.g. 2).

To each zoom level corresponds tiles partitioning the space. For instance, for the zoom level “0” there is only one tile,
whose (x, y) coordinates are (0, 0). For the zoom level “1”, there are four tiles, whose coordinates are
- (0, 0) (top-left),
- (0, 1) (bottom-left),
- (1, 0) (top-right) and
- (1, 1) (bottom-right).

The `interaction.html` file contains a minimalist Web application displaying a map and a temperature overlay.
In order to integrate your tiles with the application, you must generate them in files located according to the following scheme:

```sh
target/temperatures/2015/<zoom>/<x>-<y>.png
```
Where `<zoom>` is replaced by the zoom level, and `<x>` and `<y>` are replaced by the tile coordinates. For instance, the tile located at coordinates (0, 1), for the zoom level 1 will have to be located in the following file:
```sh
target/temperatures/2015/1/0-1.png
```

Once you have generated the files you want to visualize, just open the `interaction.html` file in a Web browser.

## Future integration with a more complete Web application
At the end of the project you will be going to display these temperature data in a more complete Web application,
allowing for example to select which year to visualize. You can prepare this integration by generating the tiles for
all the years between 1975 and 2015.

You should put the generated images in the following location:
```sh
target/temperatures/<year>/<zoom>/<x>-<y>.png
```

This is going to take a lot of time, but you can make the process faster:
- Identify which parts of the process are independent and perform them in parallel ;
- Reduce the quality of the tiles. For instance, instead of computing 256×256 images, compute 128×128 images (that’s going to be 4 times fewer pixels to compute) and then scale them to fit the expected tile size.

Finally, you will have to implement the following method:
```scala
def generateTiles[Data](
  yearlyData   : Iterable[(Year, Data)],
  generateImage: (Year, Tile, Data) => Unit
): Unit
```

This method generates all the tiles for a given dataset `yearlyData`, for zoom levels 0 to 3 (included).
The dataset contains pairs of `(Year, Data)` values, or, said otherwise, data associated with years.

In your case, this data will be the result of `Extraction.locationYearlyAverageRecords`. The second parameter of the `generateTiles` method is a function that takes
- a year,
- the coordinates of the tile to generate, and
- the data associated with the year, and 
- computes the tile and writes it on your file system.

# 4th milestone Data manipulation

## Milestone overview
One of the primary goals of this project is to be able to visualize the evolution of the climate. If you tried to visualize the temperatures of different years in the previous milestone, you might have noticed that it is actually quite hard to really measure how the temperatures have evolved since 1975.

That’s why we propose to visualize the _deviations_ of the temperatures over the years, rather than just the temperatures themselves. The goal of this milestone is to compute such deviations. You will have to complete the file **Manipulation.scala**. But first, remember to update the grading milestone number:
```scala
val milestone: Int = 4
```
**Reminder**: You can’t get a 10/10 score an an individual milestone (rather only when all milestones are completed). The maximum grade you can get in this milestone is **7.78**.

Computing deviations means comparing a value to a previous value which serves as a reference, or a “normal” temperature. You will first compute the average temperatures all over the world between 1975 and 1990. This will constitute your reference temperatures, which we refer to as “normals”. You will then compare the yearly average temperatures, for each year between 1991 and 2015, to the normals.

In order to make things faster, you will first spatially interpolate your scattered data into a regular **grid**:
![grid](grid.svg)
The above figure illustrates the grid points (in green) and the actual data points (in red). You will have to guess the temperature at the green locations based on the known temperatures at the red locations.

Once you will have such a grid for each year, you will easily be able to compute average (coordinate wise) over years and deviations.

You can monitor your progress by submitting your work at any time during the development of this milestone. Your submission token and the list of your graded submissions is available on [this page](https://www.coursera.org/learn/scala-capstone/programming/NXfKi/scaffolding-material).
## Grid generation
To describe a grid point's location, we'll use integer latitude and longitude values. This way, every grid point (in green above) is the intersection of a circle of latitude and a line of longitude. Since this is a new coordinate system, we're introducing another case class, quite similar to Location but with integer coordinates:
```scala
case class GridLocation(lat: Int, lon: Int)
```
- The latitude can be any integer between -89 and 90, and 
- the longitude can be any integer between -180 and 179.
- The top-left corner has coordinates (90, -180),and 
- the bottom-right corner has coordinates (-89, 179).

The grid associates every grid location with a temperature. You are free to internally represent the grid as you want
(e.g. using a class `Grid`), but to inter-operate with the grading system you will have to convert it to a function of type `GridLocation => Temperature`, which returns the temperature at the given grid location.

You will have to implement the following helper method:
```scala
def makeGrid(
  temperatures: Iterable[(Location, Temperature)]
): GridLocation => Temperature
```
It takes as parameter the temperatures associated with their location and returns the corresponding grid.

There are two approaches here:
- Pre-calculate all temperatures and return a getter function
- Calculate values when requested, using memoization to avoid recalculations: https://en.wikipedia.org/wiki/Memoization

When we generate the map, we'll have to get the temperature of every point on the grid at least once. With this information in mind, think about which approach is more suitable and chose the best one for you.

## Average and deviation computation

You will have to implement the following two methods:
```scala
def average(
  temperatures: Iterable[Iterable[(Location, Temperature)]]
): GridLocation => Temperature
```
This method takes a sequence of temperature data over several years (each “temperature data” for one year being a sequence of pairs of average yearly temperature and location), and returns a grid containing the average temperature over the given years at each location.
```scala
def deviation(
  temperatures: Iterable[(Location, Temperature)],
       normals: GridLocation => Temperature
): GridLocation => Temperature
```
This method takes temperature data and a grid containing normal temperatures, and returns a grid containing temperature deviations from the normals.

# 5th milestone value-added information visualization
The goal of this milestone is to produce tile images from the grids generated at the previous milestone. You will have to complete the file `Visualization2.scala`. But first, remember to update the grading milestone number:
```scala
val milestone: Int = 5
```
**Reminder**: You can’t get a 10/10 score an an individual milestone (rather only when all milestones are completed). The maximum grade you can get in this milestone is **8.89**.

As in the 3rd milestone, you will have to compute the color of every pixel of the tiles. But now the situation has changed: instead of working with a set of scattered points, you have a regular grid of points:
![bilinear interpolation](bilinear-interpolation.svg)

In this figure, the square in the middle  materializes a pixel that you want to compute. You can leverage the grid to use a faster interpolation algorithm: you can now use bilinear interpolation rather than inverse distance weighting.
More precisely, you will implement a simplified form of bilinear interpolation:
![bilinear interpolation 2](bilinear-interpolation-2.svg)

In this form of bilinear interpolation, the location of the point to estimate is given by coordinates `x` and `y`, which are numbers between 0 and 1. The algorithm considers that the four known points, `d00`, `d01`, `d10` and `d11`, form a unit square whose origin is its top-left corner. As such, the coordinates of a pixel inside of a grid cell can be described by the following case class, defined in `models.scala`:
```scala
case class CellPoint(x: Double, y: Double)
```
You will also have to decide on a color scale to use, to represent the temperature deviations. You can for instance use one like the following: 

![deviations colors](deviations-colors.png)

Here are the RGB values of these colors:
|Temperature (°C)|	Red|	Green|	Blue|
|:--:|:--:|:--:|:--:|
|7|  0|	0|	0|
|4|	               255|	0|	0|
|2|	               255|	255|	0|
|0|	               255|	255|	255|
|-2|	               0|	255|	255|
|-7|	               0|	0|	255|

## Visualization
You will have to implement the following methods:
```scala
def bilinearInterpolation(
  point: CellPoint,
    d00: Temperature,
    d01: Temperature,
    d10: Temperature,
    d11: Temperature
): Temperature
```
This method takes the coordinates (`x` and `y` values between 0 and 1) of the location to estimate the temperature at, and the 4 known temperatures as shown in the above figure, and returns the estimated temperature at location `(x, y)`.
```scala
def visualizeGrid(
  grid  : GridLocation => Temperature,
  colors: Iterable[(Temperature, Color)],
  tile  : Tile
): Image
```
This method takes a grid, a color scale and the coordinates of a tile, and returns the 256×256 image of this tile, where each pixel has a color computed according to the given color scale applied to the grid values.

*Hint*: remember that our grid is a rectangular projection of a sphere, so `IndexOutOfBoundsExceptions` on coordinates should not be possible!

## Deviation tiles generation
Once you have implemented the above methods, you are ready to generate the tiles showing the deviations for all the years between 1990 and 2015, so that the final application (in last milestone) will nicely display them:

- Compute normals from yearly temperatures between 1975 and 1990 ;
- Compute deviations for years between 1991 and 2015 ;
- Generate tiles for zoom levels going from 0 to 3, showing the deviations.

Use the output method of `Image` to write the tiles on your file system, under a location named according to the following scheme: `target/deviations/<year>/<zoom>/<x>-<y>.png`

Note that this process is going to be very CPU consuming, or might even crash if your implementation tries to load too much data into memory. That being said, even a smart solution performing incremental data manipulation and parallel computations might take a lot of time (several days). You can reduce this time by using some of these ideas:
- Identify which parts of the process are independent and perform them in parallel; 
- Reduce the quality of the tiles. For instance, instead of computing 256×256 images, compute 128×128 images (that’ll be 4 times fewer pixels to compute) and then scale them to fit the expected tile size; 
- Reduce the quality of the spatial interpolation. For instance, instead of having grids with 360×180 points, you can use a grid with 120×60 points (that’s going to be 9 times fewer points to compute).

# 6th (and last) milestone user interface polishing
This (last!) milestone consists in implementing an interactive user interface so that users can select which data set (either the temperatures or the deviations) as well as which year they want to observe. You will have to complete the file **Interaction2.scala**. But first, remember to update the grading milestone number:
```scala
val milestone: Int = 6
```
![milestone 6](milestone-6.png)

We provide a sub-project, `capstoneUI`, which contains the actual user interface implementation. This sub-project uses the methods you are going to implement.

Last but not least, you will use the tiles that you previously generated for the temperatures and the deviations, so check that you had not deleted them from your file system!

## Layers
This milestone introduces the concept of Layers. A Layer describes the additional information shown on a map. In your case, you will have two layers: one showing temperatures over time, and one showing the temperature deviations over time.

You will have to implement the following method:
```scala
def availableLayers: Seq[Layer]
```
This method returns the layers you want the user to be able to visualize. Each layer has a name, a color scale and a range of supported years.

The user interface implementation will use your `availableLayers` to build buttons allowing to choose which layer to enable. The value over time of the enabled layer is represented by a `Signal[Layer]` value (the same Signal as in the progfun2 course).

## Signals

In this part of the assignment you are going to reuse the Signal abstraction introduced in the “Functional Program Design in Scala” course.

### Reminder on Signals

In case you didn’t follow this course or you need to refresh your memory, here is a short reminder on Signals.

A Signal is a value that can change over time:
```scala
val x = Signal(0)  // Initialize x’s value to “0”
println(x())       // Read x’s current value (“0”)
x() = x() + 1      // Change x’s value
println(x())       // “1”
```
Signals can depend on other Signals. In such a case, when the value of a Signal changes, the Signals that depend on it are automatically updated:
```scala
val x = Signal(0)
val y = Signal(x() * 2)  // y depends on x’s value
println(y())             // “0”
x() = x() + 1
println(y())             // “2”
```
Note that, in the above example, if we didn’t want to introduce a dependency between `x` and `y` we would have to first capture the current value of `x` in a usual `val`:
```scala
val x = Signal(0)
val currentX = x()            // currentX is a stable value
val y = Signal(currentX * 2)  // y is initialized with currentX’s value,
                              // but does not depend on x
x() = x() + 1
println(y())         // “0” (update of x did not trigger an update on y)
```

## Methods to implement

You will have to implement the following signals:
```scala
def yearBounds(selectedLayer: Signal[Layer]): Signal[Range]
```
This method takes the selected layer signal and returns the years range supported by this layer.
```scala
def yearSelection(
  selectedLayer: Signal[Layer],
    sliderValue: Signal[Year]
): Signal[Year]
```
This method takes the selected layer and the year slider value and returns the actual selected year, so that this year is not out of the layer bounds (remember that `Year` is just a type alias for `Int`).
```scala
def layerUrlPattern(
  selectedLayer: Signal[Layer],
   selectedYear: Signal[Year]
): Signal[String]
```
This method takes the selected layer and the selected year and returns the pattern of the URL to use to retrieve the tiles. You will return a relative URL (starting by target/).

Note that the `LayerName` id member corresponds to the sub-directory name you had generated the tiles in. This URL pattern is going to be used by the mapping library to retrieve the tiles, so it must follow a special syntax, as described here (you can ignore the “s” parameter).
```scala
def caption(
  selectedLayer: Signal[Layer],
   selectedYear: Signal[Year]
): Signal[String]
```
This method takes the selected layer and the selected year and returns the text information to display. The text to display should be the name of the layer followed by the selected year, between parenthesis.

For instance, if the selected layer is the temperatures layer and the selected year is 2015, it should return “Temperatures (2015)”.

## Running the Web application

Once you have implemented the above methods, you are ready to finally run the whole application. Execute the following sbt command:
```sh
capstoneUI/fastOptJS
```
This will compile part of your Scala code to JavaScript instead of JVM bytecode, using Scala.js. To see it in action, just open the `interaction2.html` file in your browser!

Note that some of the source files you had written are going to be shared with the `capstoneUI` sub-project. That’s the case for `Interaction2.scala`, obviously, but also `models.scala`(which contains the definition of the `Color` data type).

Why does that matter? Because while Scala.js supports a wealth of libraries, many others just aren't compatible. This means that your code in this milestone must not depend on libraries that have not been compiled for JavaScript (like the `scrimage` library, for instance).

If you'd like to know more after this short introduction on Scala.js, you can check out [their resources](https://www.scala-js.org/tutorial/).
