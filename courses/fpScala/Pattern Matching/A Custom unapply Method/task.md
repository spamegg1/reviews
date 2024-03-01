# A Custom unapply Method 

You can also implement a custom `unapply` method for both a regular class that lacks an automatically generated `unapply`,
and for providing an additional way to destructure a case class. 
Here's an example of a custom `unapply` method for the `Cat` case class we defined in the previous chapter:

```scala 3
object Cat:
  def unapply(cat: Cat): (String, Int, String) =
    val ageDescription = if (cat.age < 2) "kitten" else "adult"
    (cat.name, cat.age, ageDescription)
```

Here, we've defined `unapply` that not only returns the name and age of the `Cat`, 
but also a description of the cat's age (`"kitten"` or `"adult"`). 
Now, we can use this custom `unapply` method in pattern matching:

```scala 3
val mittens = Cat("Mittens", 1)

mittens match
  case Cat(name, age, description) =>
    println(s"$name is a $description.")
    // This will print out "Mittens is a kitten."
``` 

Take note that our `unapply` works in all situations — regardless of the cat's age, deconstructing it produces a valid result. 
This is called the *Universal Apply Method*, a new feature in Scala 3. 
Previously in Scala 2, every `unapply` had to return an `Option` of the collection of fields produced during the destructuring. 
That `Option` would be `Some(...)` if the destructuring succeeded, or `None` if it failed. 
When could it fail? 
Imagine that we're developing a system for handling driver's licenses. 
In Germany, if you want to drive a standard car, you must be at least 18 years old. 
However, a license for a small motorcycle can be obtained at 16, and for a moped, the minimum age is 15. 
Therefore, we will create an enum for `VehicleType` and a class `Applicant`, which will include the name of the person 
applying for a driver's license, their age, and the vehicle type they want to drive:

```scala 3
enum VehicleType:
  case Car
  case SmallMotorcycle
  case Moped

class Applicant(name: String, age: Int, vehicleType: VehicleType)
```

Now, somewhere in our code, we have a sequence of all applicants, and we want to get the names of those 
who are eligible for a driver's license based on their age and the vehicle type they're applying to drive. 
Just as we did in the previous chapter when searching for cats older than one year, we could define a Universal Apply Method 
and use guards within pattern matching. However instead of `foreach`, this time we will use `collect`:

```scala 3
object Applicant:
  def unapply(applicant: Applicant): (String, Int, VehicleType) =
    (applicant.name, applicant.age, applicant.vehicleType)

  val applicants: Seq[Applicant] = ???
  val names = applicants.collect {
    case Applicant(name, age, VehicleType.Car) if age >= 18 => name
    case Applicant(name, age, VehicleType.SmallMotorcycle) if age >= 16 => name
    case Applicant(name, age, VehicleType.Moped) if age >= 15 => name
}
```

However, since we're defining our own `unapply` method anyway in this example, we might as well incorporate the logic that checks 
if the applicant is eligible for a driver's license within it. This would return an `Option` of their name or `None`:

```scala 3
object Applicant:
  def unapply(applicant: Applicant): Option[String] = applicant.vehicleType match
    case VehicleType.Car if age >= 18 => Some(applicant.name)
    case VehicleType.SmallMotorcycle if age >= 16 => Some(applicant.name)
    case VehicleType.Moped if age >= 15 => Some(applicant.name)
    case _ => None

  val applicants: Seq[Applicant] = ???
  val names = applicants.collect {
    case Applicant(name) => name
}
``` 

As you can see, we've shifted the logic from the `collect` method to the `unapply` method. 
While this example does not necessarily make the code shorter or more readable, 
the ability to move the logic that checks if an entity is valid for a specific operation to a separate place in your codebase 
could prove valuable, depending on the situation.

### Exercise 

Given that each component in the RGB range can only be between `0` and `255`, it only uses 8 bits. 
The 4 components of the RGB representation fit neatly into a 32-bit integer, which allows for better memory usage.
Many color operations can be performed directly using bitwise operations on this integer representation. 
However, sometimes it's more convenient to access each components as a number, 
and this is where the custom `unapply` method may come in handy. 

Implement the `unapply` method for the int-based RGB representation. 
The alpha channel resides in the leading bits, followed by red, green, and then blue. 
