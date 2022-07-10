package wikipedia

import scala.io.Source
import scala.io.Codec

object WikipediaData:

  private[wikipedia] def lines: List[String] =
    Option(getClass.getResourceAsStream("/wikipedia/wikipedia-grading.dat")) match
      case None => sys.error(
        "Please download the dataset as explained in the assignment instructions")
      case Some(resource) =>
        Source
          .fromInputStream(resource)(Codec.UTF8)
          .getLines()
          .toList

  private[wikipedia] def parse(line: String): WikipediaArticle =
    val subs = "</title><text>"
    val i = line.indexOf(subs)
    val title = line.substring(14, i)
    val text  = line.substring(i + subs.length, line.length-16)
    WikipediaArticle(title, text)
