package org.epfl.errorhandling.part8

import java.time.LocalDate

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import scala.util.control.NonFatal

case class User(name: String, passwordHash: Seq[Byte])

def bcrypt(saltRound: Int, password: String): Future[Seq[Byte]] =
  Future.successful(password.getBytes().toSeq)
def insertUser(login: String, passwordHash: Seq[Byte]): Future[User] =
  Future.successful(User(login, passwordHash))

def hashPasswordAndInsert(name: String, password: String): Future[User] =
  bcrypt(10, password)
    .flatMap(passwordHash => insertUser(name, passwordHash))

val userData: Seq[(String, String)] = Seq(
  "Alice" -> "averycomplicatedpassword",
  "Bob" -> "anothercomplicatedpassword"
)

@main def run(): Unit =
  Future.traverse(userData)(hashPasswordAndInsert)
    .foreach(println)
