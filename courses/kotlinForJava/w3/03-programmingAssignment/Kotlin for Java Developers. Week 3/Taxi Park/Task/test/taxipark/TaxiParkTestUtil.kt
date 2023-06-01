package taxipark

fun driver(i: Int) = Driver("D-$i")
fun passenger(i: Int) = Passenger("P-$i")

fun drivers(indices: List<Int>) = indices.map(::driver).toSet()
fun drivers(range: IntRange) = drivers(range.toList())
fun drivers(vararg indices: Int) = drivers(indices.toList())

fun passengers(indices: List<Int>) = indices.map(::passenger).toSet()
fun passengers(range: IntRange) = passengers(range.toList())
fun passengers(vararg indices: Int) = passengers(indices.toList())

fun taxiPark(driverIndexes: IntRange, passengerIndexes: IntRange, vararg trips: Trip) =
        TaxiPark(drivers(driverIndexes), passengers(passengerIndexes), trips.toList())

fun trip(driverIndex: Int, passengerIndexes: List<Int>, duration: Int = 10, distance: Double = 3.0, discount: Double? = null) =
        Trip(driver(driverIndex), passengers(passengerIndexes), duration, distance, discount)

fun trip(driverIndex: Int, passenger: Int, duration: Int = 10, distance: Double = 3.0, discount: Double? = null) =
        Trip(driver(driverIndex), passengers(passenger), duration, distance, discount)

fun TaxiPark.display() = buildString {
    appendln()
    appendln("Taxi park:")
    appendln("Drivers: ${allDrivers.map { it.name }}")
    appendln("Passengers: ${allPassengers.map { it.name }}")
    appendln("Trips: ${trips.map { it.display() }}")
}

fun Trip.display(): String {
    val discountText = discount?.let { ", $it" } ?: ""
    return "(${driver.name}, ${passengers.map { it.name }}, $duration, $distance$discountText)"
}
