import java.time.{LocalDate, Period}

import scala.util.Try

type Errors = Seq[String]
type Validated[A] = Either[Errors, A]

def validateBoth[A, B](
  validatedA: Validated[A],
  validatedB: Validated[B]
): Validated[(A, B)] =
  (validatedA, validatedB) match {
    case (Right(a), Right(b)) => Right((a, b))
    case (Left(e),  Right(_)) => Left(e)
    case (Right(_), Left(e))  => Left(e)
    case (Left(e1), Left(e2)) => Left(e1 ++ e2)
  }

def parseDate(str: String): Validated[LocalDate] =
  Try(LocalDate.parse(str)).toEither
    .left.map(error => Seq(error.getMessage))

def validatePeriod(str1: String, str2: String): Validated[Period] =
  validateBoth(parseDate(str1), parseDate(str2))
    .map((date1, date2) => Period.between(date1, date2))

validatePeriod("not a date", "not a date either")

def parseDates(strs: List[String]): Validated[List[LocalDate]] =
  strs.foldLeft[Validated[List[LocalDate]]](Right(Nil)) {
    (validatedDates, str) =>
      val validatedDate: Validated[LocalDate] = parseDate(str)
      validateBoth(validatedDate, validatedDates)
        .map((date, dates) => date :: dates)
  }

parseDates(List("2020-01-04", "2020-08-09"))
parseDates(List("not a date", "2020-09-13", "not a date either"))
