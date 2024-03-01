package berlinerPattern

import scala.collection.mutable

/**
 * The user model for the database
 * @param name a username used as the identifier
 * @param password a password - any string; it's not used in the exercise
 * @param verified a boolean flag, simulating if the user verified if it's actually them who requested registration
 */
case class User(name: String, password: String, verified: Boolean = false)

/**
 * A fake implementation of the database
 *
 * You can use it to add new users to the "database", look them up by name, and set their `verified` flag.
 */
class Database {
  private val users = mutable.ArrayBuffer[User]()

  /**
   * Looks for a user with the given name in the database.
   *
   * @param name The name of the user (it serves as an id)
   * @return `Some(user)` if the user with the given name is found, `None` otherwise
   */
  def get(name: String): Option[User] = users.find(_.name == name)

  /**
   * Adds a new user to the fake database.
   *
   * @param user A new user
   * @return `true` if adding succeeded, `false` otherwise
   */
  def add(user: User): Boolean =
    if users.exists(_.name == user.name) then
      false
    else
      users += user
      true

  /**
   * Sets the `verified` flag of the given user to `true`.
   *
   * @param name The name of the user you want to verify
   * @return `true` if the user exists and is not yet verified, `false` otherwise
   */
  def verify(name: String): Boolean =
    get(name) match
      case Some(user) if !user.verified =>
        users -= user
        users += user.copy(verified = true)
        true
      case _ =>
        false

  /**
   * Changes the `password` of the given user to `newPassword`.
   * You can only change the password of a verified user and only if their password are the same as the `oldPassword`.
   *
   * @param name The name of the user whose password you want to change
   * @param oldPassword The old password
   * @param newPassword A new password
   * @return `true` if the user exists and is verified, `false` otherwise
   */
  def changePassword(name: String, oldPassword: String, newPassword: String): Boolean =
    /* TODO */

  /**
   * Removes the given user from the database.
   * Only verified users can be removed.
   *
   * @param name The name of the user to remove
   * @return `true` if the user exists and is verified, `false` otherwise
   */
  def removeUser(name: String): Boolean =
    /* TODO */
}
