package berlinerPattern

object BerlinerPatternTask:

  /**
   * This method should handle an incoming request of a new user registration.
   *
   * Implement it according to the following rules:
   * 1. You can't register again an already existing user (identified by their name).
   * 2. If you successfully register a new user, you should call `setReadyForVerification` with their name on the http client.
   * 3. You should return either `NewUserResult.Success` or `NewUserResult.Failure(message)` with a meaningful message.
   * 4. Don't use variables or mutable data structures.
   * 5. Bonus points for implementing the method as one expression.
   *
   * @param request a new user request coming from the http client
   * @param db a reference to the database you can use to persist and get the user
   * @param httpClient a reference to the http client which you can use to call `setReadyForVerification`
   * @return either `NewUserResult.Success` or `NewUserResult.Failure(message)`
   */
  def onNewUser(
    request: NewUserRequest,
    db: Database,
    httpClient: HttpClient
  ): NewUserResult =
    /* TODO */
    db.get(request.name) match
      case Some(_) => NewUserResult.Failure("user already exists")
      case None =>
        db.add(User(request.name, request.password))
        httpClient.setReadyForVerification(request.name)
        NewUserResult.Success

  /**
   * This method should handle an incoming verification request
   *
   * Implement it according to the following rules:
   * 1. You can't verify a user who is not yet persisted
   * 2. You can't verify an already verified user
   * 3. You should return either `VerificationResult.Success` or `VerificationResult.Failure`.
   * 4. Don't use variables or mutable data structures.
   * 5. Bonus points for implementing the method as one expression.
   *
   * @param request a new verification request coming from the http client
   * @param db a reference to the database you can use to get the user
   * @param httpClient a reference to the http client
   * @return either `VerificationResult.Success` or `VerificationResult.Failure`
   */
  def onVerification(
    request: VerificationRequest,
    db: Database,
    httpClient: HttpClient
  ): VerificationResult =
    /* TODO */
    if db.verify(request.name) then VerificationResult.Success
    else VerificationResult.Failure


  /**
   *
   * This method should handle an incoming change password request
   *
   * Implement it according to the following rules:
   * 1. You can't change a password of a user who does not exist in the database
   * 2. You can't change a password of an unverified user
   * 3. You can't change a password of a user if they provided the wrong old password
   * 4. You should return either `ChangePasswordResult.Success` or `ChangePasswordResult.Failure`.
   * 5. Don't use variables or mutable data structures.
   * 6. Bonus points for implementing the method as one expression.
   *
   * @param request a new change password request coming from the http client
   * @param db a reference to the database you can use to get the user
   * @param httpClient a reference to the http client
   * @return either `ChangePasswordResult.Success` or `ChangePasswordResult.Failure`
   */
  def onChangePassword(
    request: ChangePasswordRequest,
    db: Database,
    httpClient: HttpClient
  ): ChangePasswordResult =
    /* TODO */
    if db.changePassword(request.name, request.oldPassword, request.newPassword) then
      ChangePasswordResult.Success
    else ChangePasswordResult.Failure("cannot change password")

  /**
   * This method should handle an incoming remove user request
   *
   * Implement it according to the following rules:
   * 1. You can't remove a user who does not exist in the database
   * 2. You can't remove an unverified user
   * 3. You should return either `ChangePasswordResult.Success` or `ChangePasswordResult.Failure`.
   * 4. Don't use variables or mutable data structures.
   * 5. Bonus points for implementing the method as one expression.
   *
   * @param request
   * @param db
   * @param httpClient
   * @return
   */
  def onRemoveUser(
    request: RemoveUserRequest,
    db: Database,
    httpClient: HttpClient
  ): RemoveUserResult =
    /* TODO */
    if db.removeUser(request.name) then RemoveUserResult.Success
    else RemoveUserResult.Failure("cannot remove user")

  def runScript(): Either[String, Unit] =
    val db = Database()
    val httpClient = HttpClient(onNewUser(_, db, _),
      onVerification(_, db, _),
      onChangePassword(_, db, _),
      onRemoveUser(_, db, _))
    httpClient.run()

  /**
   * When you finish implementing `onNewUser` and `onVerification`, run the program.
   * It will create instances of the database and the http client, and run the script which will check if your
   * implementation is valid.
   */
  @main
  def main(): Unit =
    runScript() match
      case Right(_)    => println(s"Success!")
      case Left(error) => println(s"Error: $error")
