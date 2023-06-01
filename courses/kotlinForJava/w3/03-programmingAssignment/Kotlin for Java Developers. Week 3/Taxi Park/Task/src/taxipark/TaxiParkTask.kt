package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> = // TODO()
    allDrivers - trips.map { it.driver }.toSet()

/*
 * Task #2. Find all the clients who completed
 * at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> = // TODO()
    allPassengers.filter { passenger ->
        trips.count { passenger in it.passengers } >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken
 * by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> = // TODO()
    allPassengers.filter { passenger ->
        trips.count { it.driver == driver && passenger in it.passengers } > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> { // TODO()
    val (discounted, notDiscounted) = trips.partition { it.discount != null }

    fun majorityDiscount(passenger: Passenger): Boolean =
        discounted.count { passenger in it.passengers } >
        notDiscounted.count { passenger in it.passengers }

    return allPassengers.filter { majorityDiscount(it) }.toSet()
}

/*
 * Task #5. Find the most frequent trip duration among minute periods
 * 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent,
 * return `null` if there are no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? { // TODO()
    fun tripRange(duration: Int): IntRange {
        val tens = (duration / 10) * 10
        return tens..(tens + 9)
    }
    return trips
        .groupBy { trip -> tripRange(trip.duration) }
        .maxBy { (_, list) -> list.size }
        ?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean { // TODO()
    if (trips.isEmpty()) return false
    val totalIncome: Double = trips.sumByDouble { it.cost }

    fun driverIncome(driver: Driver): Double = trips
        .filter { trip -> trip.driver == driver }
        .sumByDouble { trip -> trip.cost }

    val driversByIncome = allDrivers.sortedByDescending { driverIncome(it) }
    val top20Drivers = driversByIncome.take(driversByIncome.size / 5)
    val top20Income = top20Drivers.sumByDouble { driverIncome(it) }

    return top20Income >= totalIncome * 0.8
}
