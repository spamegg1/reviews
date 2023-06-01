## Taxi Park

The `TaxiPark` class stores information about registered drivers, passengers,
and their trips. Your task is to implement six functions which collect
different statistics about the data.

#### Task 1

```
fun TaxiPark.findFakeDrivers(): Collection<Driver>
```

Find all the drivers who didn't perform any trips.


#### Task 2

```
fun TaxiPark.findFaithfulPassengers(minTrips: Int): List<Passenger>
```

Find all the clients who completed at least the given number of trips.

#### Task 3

```
fun TaxiPark.findFrequentPassengers(driver: Driver): List<Passenger>
```

Find all the passengers who were driven by a certain driver more than once.

#### Task 4

```
fun TaxiPark.findSmartPassengers(): Collection<Passenger>
```

If we consider "smart", a passenger who had a discount for the majority of the trips they made or took part in
(including the trips with more than one passenger), find all the "smart" passengers.
A "smart" passenger should have strictly more trips with discount than trips without discount,
the equal amounts of trips with and without discount isn't enough.

Note that the discount can't be `0.0`, it's always non-zero if it's recorded. 

#### Task 5

```
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange?
```

Find the most frequent trip duration period among minute periods 0..9, 10..19, 20..29, and so on.
Return any suitable period if many are the most frequent, return `null` if there're no trips.
 

#### Task 6

```
fun TaxiPark.checkParetoPrinciple(): Boolean
```

Check whether no more than 20% of the drivers contribute 80% of the income.
The function should return true if the top 20% drivers (meaning the top 20% best
performers) represent 80% or more of all trips total income, or false if not.
The drivers that have no trips should be considered as contributing zero income. 
If the taxi park contains no trips, the result should be `false`.

For example, if there're 39 drivers in the taxi park, we need to check that no more than
20% of the most successful ones, which is seven drivers (39 * 0.2 = 7.8), contribute
at least 80% of the total income. Note that eight drivers out of 39 is 20.51% which
is more than 20%, so we check the income of seven the most successful drivers.

To find the total income sum up all the trip costs. Note that the discount is already
applied while calculating the cost.  
