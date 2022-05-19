package observatory

import leaflet.{L, MapOptions, ZoomOptions}

import scala.language.implicitConversions
import scala.scalajs.js
import org.scalajs.dom.{Event, Node, document}
import org.scalajs.dom.html.Input

import scalatags.{DataConverters, LowPriorityImplicits}
import scalatags.JsDom.{Cap, Aggregate, tags, attrs, styles, Frag, Modifier}

object Implicits extends Cap with Aggregate with DataConverters with LowPriorityImplicits
import Implicits.given
import Signal.Var

object JSMain:

  def main(args: Array[String]): Unit =
    val availableLayers = Interaction2.availableLayers
    val (radioButtonElement, selectedLayer) = makeRadioButtons(availableLayers)
    val (sliderElement, selectedYear) = makeSlider(selectedLayer)
    val captionElement = makeCaptionElement(selectedLayer)
    setupMap(selectedLayer, selectedYear)
    val app =
      tags.div(
        radioButtonElement,
        sliderElement,
        captionElement
      )
    document.body.appendChild(app.render)
    ()

  def setupMap(selectedLayer: Signal[Layer], selectedYear: Signal[Int]): Unit =
    val mapElement = tags.div(styles.height := "100%").render
    val map = L.map(mapElement, new MapOptions { val zoomControl = false ; val maxZoom = 3 })
    map.setView(L.latLng(48.0, 14.0), 3)
    L.tileLayer("https://maps.wikimedia.org/osm-intl/{z}/{x}/{y}.png").addTo(map)
    val urlSignal = Interaction2.layerUrlPattern(selectedLayer, selectedYear)
    val layer = L.tileLayer(urlSignal.currentValue)
    layer.addTo(map)
    Signal {
      layer.setUrl(urlSignal())
    }
    map.addControl(L.control.zoom(new ZoomOptions { val position = "bottomright" }))
    document.body.appendChild(mapElement.render)
    map.invalidateSize()

  def makeRadioButtons(availableLayers: Seq[Layer]): (Frag, Signal[Layer]) =
    val initialValue = availableLayers.head
    val radioButtonValue = Var[Layer](initialValue)
    def makeRadioButton(layer: Layer): Frag =
      tags.input(
        attrs.`type` := "radio",
        attrs.name := "layer",
        attrs.onclick := { (ev: Event) =>
          val input = ev.target.asInstanceOf[Input]
          if input.checked then radioButtonValue() = layer
        },
        if layer == initialValue then Some[Modifier](attrs.checked) else Option.empty[Modifier]
      )
    val root =
      tags.div(
        styles.position.absolute,
        styles.top := 0,
        styles.right := 0,
        styles.left := 0,
        styles.height := 2.em,
        styles.zIndex := 1500
      )(
        for layer <- availableLayers yield {
          tags.label(
            makeRadioButton(layer),
            layer.layerName.toString
          )
        }
      )
    (root, radioButtonValue)

  def makeSlider(selectedLayer: Signal[Layer]): (Frag, Signal[Int]) =
    val yearBounds = Interaction2.yearBounds(selectedLayer)
    val sliderValue = Var[Int](yearBounds().max)
    val input = Signal {
      tags.input(
        attrs.`type` := "range",
        attrs.min := yearBounds().min,
        attrs.max := yearBounds().max,
        attrs.value := yearBounds().max,
        attrs.onchange := { (ev: Event) =>
          val input = ev.target.asInstanceOf[Input]
          sliderValue.update(input.value.toInt)
        },
        styles.width := 40.em
      )
    }

    val selectedYear = Interaction2.yearSelection(selectedLayer, sliderValue)
    val captionSignal = Interaction2.caption(selectedLayer, selectedYear)

    val caption = Signal {
      tags.span(captionSignal())
    }

    val root =
      tags.div(
        styles.position.absolute,
        styles.bottom := 0,
        styles.right := 0,
        styles.left := 0,
        styles.height := 2.em,
        styles.zIndex := 1500
      )(
        tags.label(
          input,
          caption
        )
      )
    (root, selectedYear)

  def makeCaptionElement(selectedLayer: Signal[Layer]): Frag =
    Signal {
      tags.div(
        styles.position.absolute,
        styles.top := 5.px,
        styles.right := 5.px,
        styles.zIndex := 1500,
        styles.backgroundColor := "white"
      )(
        for (t, Color(red, green, blue)) <- selectedLayer().colorScale.reverse yield {
          tags.div(
            styles.margin := 5.px,
            styles.textAlign.right
          )(
            tags.span((if t > 0 then "+" else "") + t + " "),
            tags.span(
              styles.width := 20.px,
              styles.height := 15.px,
              styles.backgroundColor := s"rgb($red, $green, $blue)",
              styles.display.`inline-block`,
              styles.border := "thin solid black"
            )
          )
        }
      )
    }

  given [A] (using aToFrag: A => Frag): Conversion[Signal[A], Frag] with
    override def apply(signalA: Signal[A]): Frag =
      def render(a: A): Node = tags.span(aToFrag(a)).render
      var last = render(signalA.currentValue)
      Signal {
        val current = render(signalA.currentValue)
        last.replaceChild(current, last.firstChild)
        last = current
      }
      last

