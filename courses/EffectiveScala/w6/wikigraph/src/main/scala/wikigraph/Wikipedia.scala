package wikigraph

import Articles.ArticleId
import wikigraph.errors.WikiError.{ ArticleNotFound, TitleNotFound, NoResult }
import scala.concurrent.{ExecutionContext, Future}

/**
  * The interface of objects providing access to Wikipedia data.
  * 
  * All the methods of this class can fail with a system failure
  * of type [[wikigraph.errors.WikiException]]
  */
trait Wikipedia:
  /**
    * Retrieves the ids of the pages linked on a page.
    * 
    * If the provided id does not identify an article in the data,
    * the computation fails with a [[wikigraph.errors.WikiError.ArticleNotFound]]
    * 
    * @param the id of the page from which we want to retrieve the links
    */
  def linksFrom(art: ArticleId)(using ExecutionContext): WikiResult[Set[ArticleId]]

  /**
    * Retrieves the title of the page corresponding to the provided id.
    * 
    * If the provided id does not identify an article in the data or if 
    * page does not have a title, the computation fails with a 
    * [[wikigraph.errors.WikiError.TitleNotFound]]
    * 
    * @param the id of the page from which we want to retrieve the links
    */
  def nameOfArticle(art: ArticleId)(using ExecutionContext): WikiResult[String]

  /**
    * Retrieves the article id of the page which has the provided title.
    * 
    * If the page is not found, the computation fails with a
    * [[wikigraph.errors.WikiError.NoResult]]
    * 
    * @param title the title of the page whose id we want to retrieve
    */
  def searchId(title: String)(using ExecutionContext): WikiResult[ArticleId]
