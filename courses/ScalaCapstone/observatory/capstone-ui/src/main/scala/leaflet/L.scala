package leaflet

import org.scalajs.dom.Element

import scala.annotation.meta.field
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSGlobal}

@JSGlobal("L")
@js.native
object L extends js.Object:

  def map(elementId: Element, options: MapOptions): Map = js.native

  def tileLayer(urlPattern: String): TileLayer = js.native

  def control: ControlFactory = js.native

  def imageOverlay(url: String, latLngBounds: LatLngBounds): ImageLayer = js.native

  def latLng(lat: Double, lng: Double): LatLng = js.native

  def latLngBounds(southWest: LatLng, northEast: LatLng): LatLngBounds = js.native


@js.native
sealed trait Map extends js.Object:

  def setView(coordinates: LatLng, zoomLevel: Int): Unit = js.native

  def addControl(control: Control): Unit = js.native

  def invalidateSize(): Unit = js.native

  def getBounds(): LatLngBounds = js.native

  def getPixelBounds(): Bounds = js.native

  def getPixelWorldBounds(): Bounds = js.native

  def on(eventName: String, callback: js.Function1[Event, _]): Unit = js.native


trait MapOptions extends js.Object:
  val zoomControl: Boolean
  val maxZoom: Int

@js.native
sealed trait TileLayer extends js.Object:

  def addTo(map: Map): Unit = js.native

  def setUrl(url: String): Unit = js.native


@js.native
sealed trait ControlFactory extends js.Object:
  def zoom(options: ZoomOptions): Zoom

@js.native
sealed trait Control extends js.Object

trait ZoomOptions extends js.Object:
  val position: String

@js.native
sealed trait Zoom extends Control

@js.native
sealed trait LatLng extends js.Object

@js.native
sealed trait LatLngBounds extends js.Object

@js.native
sealed trait Layer extends js.Object:
  def addTo(map: Map): Unit = js.native
  def remove(): Unit = js.native

@js.native
sealed trait ImageLayer extends Layer:
  def setImage(url: String): Unit = js.native

@js.native
sealed trait Bounds extends js.Object:
  def getBottomLeft(): Point = js.native
  def getTopRight(): Point = js.native

@js.native
sealed trait Point extends js.Object:
  def x: Double = js.native
  def y: Double = js.native

@js.native
sealed trait Event extends js.Object