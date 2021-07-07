// This file only exists to make code compile without the correct dependencies
// in build.sbt. Deleting this file is one of the first things you should do.
package io.circe.parser

def decode[A](string: String): Either[Throwable, A] = ???
