package wikigraph

import scala.util.Try

/**
  * This object contains the definition of ArticleId.
  * 
  * This type is represented by an integer, this primitive
  * type is hidden to avoid operations that would not make
  * sense (such as + or /) by the `opaque` modifier.
  * 
  * Create an ArticleId from an Int x: ArticleId(x)
  * Retrieve the int stored inside an ArticleId id: id.raw
  */
object Articles:
  opaque type ArticleId = Int
  extension (articleId: ArticleId) def raw: Int = articleId

  object ArticleId:
    def apply(id: Int): ArticleId = id

