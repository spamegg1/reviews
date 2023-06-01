package taxipark

data class TaxiPark(
        val allDrivers: Set<Driver>,
        val allPassengers: Set<Passenger>,
        val trips: List<Trip>)

data class Driver(val name: String)
data class Passenger(val name: String)

data class Trip(
        val driver: Driver,
        val passengers: Set<Passenger>,
        // the trip duration in minutes
        val duration: Int,
        // the trip distance in km
        val distance: Double,
        // the percentage of discount (in 0.0..1.0 if not null)
        val discount: Double? = null
) {
    // the total cost of the trip
    val cost: Double
        get() = (1 - (discount ?: 0.0)) * (duration + distance)
}