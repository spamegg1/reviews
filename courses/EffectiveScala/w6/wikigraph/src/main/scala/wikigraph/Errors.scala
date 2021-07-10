package wikigraph.errors
import wikigraph.Articles.*

sealed trait WikiError
object WikiError:
  case class ArticleNotFound(id: ArticleId) extends WikiError
  case class TitleNotFound(id: ArticleId) extends WikiError
  case class NoResult(query: String) extends WikiError

sealed trait WikiException extends Exception
object WikiException:
  case object Timeout extends WikiException
  case object TooManyRequests extends WikiException
  case class ResourceNotFound(res: String) extends WikiException
