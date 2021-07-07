class Vehicle // Fake Vehicle class

object UserID:
  opaque type UserID = Long
  def parse(string: String): Option[UserID] = string.toLongOption
  def value(userID: UserID): Long = userID
end UserID

object VehicleID:
  opaque type VehicleID = Long
  def parse(string: String): Option[VehicleID] = string.toLongOption
  def value(vehicleID: VehicleID): Long = vehicleID
end VehicleID

import VehicleID.VehicleID
import UserID.UserID

def findVehicle(vehicleID: VehicleID): Option[Vehicle] = ???

// Uncomment the following lines to see the compilation error
//def mistake(userID: UserID): Unit =
//  findVehicle(userID)
