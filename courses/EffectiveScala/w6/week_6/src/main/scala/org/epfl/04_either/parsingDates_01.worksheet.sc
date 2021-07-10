import java.time.{LocalDate, Period}

import scala.util.Try

type Errors = Seq[String]

type Validated[A] = Either[Errors, A]

def parseDate(str: String): Validated[LocalDate] =
  Try(LocalDate.parse(str)).toEither
    .left.map(error => Seq(error.getMessage))

def validatePeriod(str1: String, str2: String): Validated[Period] =
  parseDate(str1).flatMap { date1 =>
    parseDate(str2).map { date2 =>
      Period.between(date1, date2)
    }
  }

validatePeriod("not a date", "not a date either")
