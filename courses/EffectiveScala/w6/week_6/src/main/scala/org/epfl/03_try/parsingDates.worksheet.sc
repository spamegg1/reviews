import java.time.LocalDate
import java.time.Period
import scala.io.Source
import scala.util.{Success, Try, Using}

def parseDate(str: String): Try[LocalDate] =
  Try(LocalDate.parse(str))

def tryPeriod(str1: String, str2: String): Try[Period] =
  parseDate(str1).flatMap { date1 =>
    parseDate(str2).map { date2 =>
      Period.between(date1, date2)
    }
  }

tryPeriod("2020-07-27", "2020-12-25")
tryPeriod("2020-19-27", "2020-12-25")
tryPeriod("2020-07-27", "2020-22-25")
tryPeriod("2020-19-27", "2020-22-25")

def readDateStrings(fileName: String): Try[Seq[String]] =
  Using(Source.fromFile(fileName)) { source =>
    source.getLines().toSeq
  }

def parseDates(fileName: String): Try[Seq[LocalDate]] =
  readDateStrings(fileName).flatMap { dateStrings =>
    dateStrings.foldLeft[Try[Seq[LocalDate]]](Success(Vector.empty[LocalDate])) {
      case (tryDates, dateString) =>
        tryDates.flatMap { dates =>
          parseDate(dateString).map { date =>
            dates :+ date
          }
        }
    }
  }

parseDates("./src/main/scala/org/epfl/03_try/dates-file.txt")

