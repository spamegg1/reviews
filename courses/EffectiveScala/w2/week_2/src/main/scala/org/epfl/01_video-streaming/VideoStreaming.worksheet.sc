enum Network:
  case Fixed, Mobile

case class Experience(duration: Int, definition: Double, network: Network)

val lowQuality  = 0.3 // MB/s
val highQuality = 0.6 // MB/s

val thirtyMinutes = 30 * 60 // seconds

val highQualityAndMobile =
  Experience(thirtyMinutes, highQuality, Network.Mobile)
val lowQualityAndFixed =
  Experience(thirtyMinutes, lowQuality, Network.Fixed)

val dataCenterEnergy = 0.000072 // Units
val kgCO2perkWh = 0.5           // kg CO2 emitted per kWh consumed

def networkEnergy(network: Network) = network match
  case Network.Mobile => 0.00088
  case Network.Fixed  => 0.00043

def footprint(experience: Experience): Double =
  val megabytes = experience.duration * experience.definition
  val energy    = dataCenterEnergy + networkEnergy(experience.network)
  energy * megabytes * kgCO2perkWh

footprint(highQualityAndMobile)

footprint(lowQualityAndFixed)
