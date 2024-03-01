package berlinerPattern

import scala.collection.mutable

/**
 * A request for a new user registration.
 *
 * HttpClient simulates requests of new users registration, incoming from the internet.
 * Your task is to check if they are valid (i.e. if it is really a new user) and persist new users in the database.
 *
 * @param name the name of the new user
 * @param password the password (unused)
 */
case class NewUserRequest(name: String, password: String)

/**
 * A verification request for an existing user.
 *
 * We simulate that after a new user is registered, an answer is being sent, asking the user to verify if it's really them.
 * They confirm by "sending" us a verification request.
 * Your task is to check if a user with the given name exists and is still unverified. If yes, you should set their
 * verification flag to `true`.
 *
 * @param name the name of the user that should be verified
 */
case class VerificationRequest(name: String)

/**
 * Change password request for an existing user.
 *
 * We simulate authorisation by checking the user's password against the `oldPassword`.
 * You task is to check the authorisation and change the password in the data base.
 *
 * @param name The name of the user whose password you want to change
 * @param oldPassword The old password
 * @param newPassword A new password
 */
case class ChangePasswordRequest(name: String, oldPassword: String, newPassword: String)

/**
 * A remove user request.
 *
 * We can only remove the user who exists and has been verified.
 *
 * @param name The name of the user to remove
 */
case class RemoveUserRequest(name: String)

/**
 * After processing a new user request, you should answer with one of two options:
 * * Success, if the new user is successfully persisted
 * * Failure, with an error message of your choice, if creating/persisting the new user failed
 */
enum NewUserResult:
  case Success
  case Failure(msg: String)

/**
 * After processing a verification request, you should answer with one of two options:
 * * Success, if the `verified` flag of the user was successfully set to true
 * * Failure otherwise
 */
enum VerificationResult:
  case Success
  case Failure

/**
 * After processing a change password request, you should anwer with one of two options:
 * * `Success`, if the password has been successfully changed
 * * Failure, with an error message of your choice, if authorisation/changing of the password failed.
 */
enum ChangePasswordResult:
  case Success
  case Failure(msg: String)

/**
 * After processing a remove user request, you should answer with one of two options:
 * * `Success`, if the user has been successfully removed
 * * `Failure`, with an error message of you choice, if the user to be removed does not exist in the database or
 *   has not been verified
 */
enum RemoveUserResult:
  case Success
  case Failure(msg: String)

/**
 * A fake implementation of the http client
 *
 * In the main file, you will find two methods that you are supposed to implement: `onNewUser` and `onVerification`.
 * Those methods will be provided as parameters for the constructor of the fake http client.
 * When the http client is run, it will call those methods with new user requests and verification requests.
 */
class HttpClient( private val newUserRequest:        (NewUserRequest, HttpClient)        => NewUserResult,
                  private val verificationRequest:   (VerificationRequest, HttpClient)   => VerificationResult,
                  private val changePasswordRequest: (ChangePasswordRequest, HttpClient) => ChangePasswordResult,
                  private val removeUserRequest:     (RemoveUserRequest, HttpClient)     => RemoveUserResult
                ):
  /**
   * Implements a script that simulates a working http client. The script creates new users and checks if they are
   * ready for verification (i.e. if you successfully created and persisted the user, you should also call `setReadyForVerification`
   * to let the http client know that a verification is needed).
   * @return At the end of the scrupt you should get `Right(())` if the script completed successfully, or `Left(message)`
   *         with the error message.
   */
  def run(): Either[String, Unit] =
    for {
      _ <- successfulNewUserRegistration("user1", "password1")
      _ <- checkIfReadyForVerification("user1")
      _ <- successfulNewUserRegistration("user2", "password2")
      _ <- checkIfReadyForVerification("user2")
      _ <- failedNewUserRegistration("user1", "password1") // the user already exists
      _ <- successfulVerification("user1")
      _ <- failedVerification("user3") // no such user
      _ <- checkIfNotReadyForVerification("user3") // no such user
      _ <- failedVerification("user1") // no need for verifying twice

      // uncomment the following lines when you start working on the passwordChange and removeUser requests

//      _ <- successfulPasswordChange("user1", "password1", "newPassword1")
//      _ <- failedPasswordChange("user3", "password1", "newPassword1") // no such user
//      _ <- failedPasswordChange("user1", "password1", "newPassword1") // wrong old password
//      _ <- failedPasswordChange("user2", "password2", "newPassword2") // unverified user
//      _ <- successfulUserRemoval("user1")
//      _ <- failedUserRemoval("user3") // no such user
//      _ <- failedUserRemoval("user2") // unverified user
    } yield Right(())

  /**
   * When a new user is created and persisted in the database, you should call this method with the new user's name.
   * This is a simulation of an http answer sent to the user, asking them to confirm, if it's really them who requested
   * the registration. The next step in the script - after the new user's registration request - will be to check if
   * `setReadyForVerification` was called with the valid username, and then a verification request will come (or not).
   *
   * @param name the name of the user that is persisted and now awaits verification
   */
  def setReadyForVerification(name: String): Unit =
    readyForVerification += name

  // --- private fields and methods ---

  private val readyForVerification = mutable.HashSet[String]()

  private def checkIfReadyForVerification(name: String): Either[String, Unit] =
    if readyForVerification.contains(name) then
      Right(())
    else
      Left(s"The user $name should be ready for verification at this point in the script")

  private def checkIfNotReadyForVerification(name: String): Either[String, Unit] =
    if !readyForVerification.contains(name) then
      Right(())
    else
      Left(s"The user $name should NOT be ready for verification at this point in the script")

  private def successfulNewUserRegistration(name: String, password: String): Either[String, Unit] =
    newUserRequest(NewUserRequest(name, password), this) match
      case NewUserResult.Success      => Right(())
      case NewUserResult.Failure(msg) => Left(msg)

  private def failedNewUserRegistration(name: String, password: String): Either[String, Unit] =
    newUserRequest(NewUserRequest(name, password), this) match
      case NewUserResult.Success    => Left(s"User registration of $name should have failed")
      case NewUserResult.Failure(_) => Right(())

  private def successfulVerification(name: String): Either[String, Unit] =
    verificationRequest(VerificationRequest(name), this) match
      case VerificationResult.Success => Right(())
      case VerificationResult.Failure => Left(s"The verification of $name should have been successful")

  private def failedVerification(name: String): Either[String, Unit] =
    verificationRequest(VerificationRequest(name), this) match
      case VerificationResult.Success => Left(s"The verification of $name should have failed")
      case VerificationResult.Failure => Right(())

  private def successfulPasswordChange(name: String, oldPassword: String, newPassword: String): Either[String, Unit] =
    changePasswordRequest(ChangePasswordRequest(name, oldPassword, newPassword), this) match
      case ChangePasswordResult.Success      => Right(())
      case ChangePasswordResult.Failure(msg) => Left(msg)

  private def failedPasswordChange(name: String, oldPassword: String, newPassword: String): Either[String, Unit] =
    changePasswordRequest(ChangePasswordRequest(name, oldPassword, newPassword), this) match
      case ChangePasswordResult.Success    => Left(s"Password change of $name should have failed")
      case ChangePasswordResult.Failure(_) => Right(())

  private def successfulUserRemoval(name: String): Either[String, Unit] =
    removeUserRequest(RemoveUserRequest(name), this) match
      case RemoveUserResult.Success      => Right(())
      case RemoveUserResult.Failure(msg) => Left(msg)

  private def failedUserRemoval(name: String): Either[String, Unit] =
    removeUserRequest(RemoveUserRequest(name), this) match
      case RemoveUserResult.Success => Left(s"Removal of $name should have failed")
      case RemoveUserResult.Failure(_) => Right(())