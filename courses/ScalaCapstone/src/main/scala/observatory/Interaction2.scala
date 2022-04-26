package observatory

/**
  * 6th (and last) milestone: user interface polishing
  *
  * This (last!) milestone consists in implementing an interactive user
  * interface so that users can select which data set (either the temperatures
  * or the deviations) as well as which year they want to observe.
  *
  * We provide a sub-project, capstoneUI, which contains the actual user
  * interface implementation.
  * This sub-project uses the methods you are going to implement.
  *
  * Last but not least, you will use the tiles that you previously generated
  * for the temperatures and the deviations,
  * so check that you had not deleted them from your file system!
  *
  *
  * ******
  * Layers
  * ******
  * This milestone introduces the concept of Layers.
  * A Layer describes the additional information shown on a map.
  * In your case, you will have two layers: one showing temperatures over time,
  * and one showing the temperature deviations over time.
  *
  * You will have to implement the following method:
  *         def availableLayers: Seq[Layer]
  *
  * This method returns the layers you want the user to be able to visualize.
  * Each layer has a name, a color scale and a range of supported years.
  *
  * The user interface implementation will use your availableLayers to build
  * buttons allowing to choose which layer to enable.
  * The value over time of the enabled layer is represented by a Signal[Layer]
  * value (the same Signal as in the progfun2 course).
  *
  *
  * *******
  * Signals
  * *******
  * In this part of the assignment you are going to reuse the Signal abstraction
  * introduced in the “Functional Program Design in Scala” course.
  * Reminder on Signals
  * In case you didn’t follow this course or you need to refresh your memory,
  * here is a short reminder on Signals.
  *
  * A Signal is a value that can change over time:
  *     val x = Signal(0)  // Initialize x’s value to “0”
  *     println(x())       // Read x’s current value (“0”)
  *     x()= x() + 1       // Change x’s value
  *     println(x())       // “1”
  *
  * Signals can depend on other Signals. In such a case, when the value of a
  * Signal changes, the Signals that depend on it are automatically updated:
  *     val x = Signal(0)
  *     val y = Signal(x() * 2)  // y depends on x’s value
  *     println(y())             // “0”
  *     x()= x() + 1
  *     println(y())             // “2”
  *
  * Note that, in the above example, if we didn’t want to introduce a dependency
  * between x and y we would have to first capture the 
  * current value of x in a usual val:
  *     val x = Signal(0)
  *     val currentX = x()            // currentX is a stable value
  *     val y = Signal(currentX * 2)  // y is initialized with currentX’s value,
  *                                   // but does not depend on x
  *     x() = x() + 1
  *     println(y())         // “0” (update of x did not trigger an update on y)
  *
  *
  * ********************
  * Methods to implement
  * ********************
  * You will have to implement the following signals:
  *
  *         def yearBounds(selectedLayer: Signal[Layer]): Signal[Range]
  * This method takes the selected layer signal and 
  * returns the years range supported by this layer.
  *
  *         def yearSelection(selectedLayer: Signal[Layer],
  *                           sliderValue: Signal[Year]
  *                           ): Signal[Year]
  * This method takes the selected layer and the year slider value and
  * returns the actual selected year,
  * so that this year is not out of the layer bounds
  * (remember that Year is just a type alias for Int).
  *
  *         def layerUrlPattern(selectedLayer: Signal[Layer],
  *                             selectedYear: Signal[Year]
  *                             ): Signal[String]
  * This method takes the selected layer and the selected year and
  * returns the pattern of the URL to use to retrieve the tiles.
  * You will return a relative URL (starting by target/).
  * Note that the LayerName id member corresponds to the sub-directory
  * name you had generated the tiles in.
  * This URL pattern is going to be used by the mapping library to retrieve the
  * tiles, so it must follow a special syntax, as described here
  * (you can ignore the “s” parameter).
  *
  *         def caption(selectedLayer: Signal[Layer],
  *                     selectedYear: Signal[Year]
  *                     ): Signal[String]
  * This method takes the selected layer and the selected year and
  * returns the text information to display.
  * The text to display should be the name of the layer followed by the selected
  * year, between parenthesis.
  * For instance, if the selected layer is the temperatures layer
  * and the selected year is 2015, it should return “Temperatures (2015)”.
  *
  *
  * ***************************
  * Running the Web application
  * ***************************
  * Once you have implemented the above methods, you are ready to finally run
  * the whole application.
  * Execute the following sbt command:
  *         capstoneUI/fastOptJS
  * This will compile part of your Scala code to JavaScript instead of JVM
  * bytecode, using Scala.js.
  * To see it in action, just open the interaction2.html file in your browser!
  * Note that some of the source files you had written are going to be shared
  * with the capstoneUI sub-project.
  * That’s the case for Interaction2.scala, obviously, but also models.scala
  * (which contains the definition of the Color data type).
  * Why does that matter? Because while Scala.js supports a wealth of libraries,
  * many others just aren't compatible. This means that your code in this
  * milestone must not depend on libraries that have not been compiled for
  * JavaScript (like the scrimage library, for instance).
  */
object Interaction2 extends Interaction2Interface {

  /**
    * @return The available layers of the application
    */
  def availableLayers: Seq[Layer] = {
    Seq(
      Layer(
        LayerName.Temperatures,
        Seq(
          ( 60, Color(255, 255, 255)),
          ( 32, Color(255, 0, 0)),
          ( 12, Color(255, 255, 0)),
          (  0, Color(0, 255, 255)),
          (-15, Color(0, 0, 255)),
          (-27, Color(255, 0, 255)),
          (-50, Color(33, 0, 107)),
          (-60, Color(0, 0, 0))
        ),
        1975 to 2015
      ),
      Layer(
        LayerName.Deviations,
        Seq(
          ( 7, Color(0, 0, 0)),
          ( 4, Color(255, 0, 0)),
          ( 2, Color(255, 255, 0)),
          ( 0, Color(255, 255, 255)),
          (-2, Color(0, 255, 255)),
          (-7, Color(0, 0, 255))
        ),
        1975 to 2015
      )
    )
  }

  /**
    * @param selectedLayer A signal carrying the layer selected by the user
    * @return A signal containing year bounds corresponding to selected layer
    */
  def yearBounds(selectedLayer: Signal[Layer]): Signal[Range] =
    Signal(selectedLayer().bounds)

  /**
    * @param selectedLayer The selected layer
    * @param sliderValue The value of the year slider
    * @return The value of the selected year, so that it never goes
    *         out of the layer bounds.
    *         If the value of `sliderValue` is out of `selectedLayer` bounds,
    *         this method should return the closest value that is included
    *         in the `selectedLayer` bounds.
    */
  def yearSelection(selectedLayer: Signal[Layer],
                    sliderValue  : Signal[Year])
                                 : Signal[Year] =
    Signal(
      sliderValue()
        /* slider val should be greater than selected layer's min */
        .max(yearBounds(selectedLayer)().min)
        /* slider val should be less than selected layer's max */
        .min(yearBounds(selectedLayer)().max)
    )

  /**
    * @param selectedLayer The selected layer
    * @param selectedYear The selected year
    * @return The URL pattern to retrieve tiles
    */
  def layerUrlPattern(selectedLayer: Signal[Layer],
                      selectedYear : Signal[Year])
                                   : Signal[String] = Signal(
    s"generated/${selectedLayer().layerName.id}/${selectedYear()}/{z}/{x}/{y}.png")

  /**
    * @param selectedLayer The selected layer
    * @param selectedYear The selected year
    * @return The caption to show
    */
  def caption(selectedLayer: Signal[Layer],
              selectedYear : Signal[Year])
                           : Signal[String] = Signal(
    s"${selectedLayer().layerName.id.capitalize} (${selectedYear().toString})")

}

// Interface used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.
trait Interaction2Interface {
  def availableLayers: Seq[Layer]
  def yearBounds(selectedLayer: Signal[Layer]): Signal[Range]
  def yearSelection(selectedLayer: Signal[Layer],
                    sliderValue  : Signal[Year]): Signal[Year]
  def layerUrlPattern(selectedLayer: Signal[Layer],
                      selectedYear : Signal[Year]): Signal[String]
  def caption(selectedLayer: Signal[Layer],
              selectedYear : Signal[Year]): Signal[String]
}

sealed abstract class LayerName(val id: String)
object LayerName {
  case object Temperatures extends LayerName("temperatures")
  case object Deviations extends LayerName("deviations")
}

/**
  * @param layerName Name of the layer
  * @param colorScale Color scale used by the layer
  * @param bounds Minimum and maximum year supported by the layer
  */
case class Layer(layerName: LayerName,
                 colorScale: Seq[(Temperature, Color)],
                 bounds: Range)

