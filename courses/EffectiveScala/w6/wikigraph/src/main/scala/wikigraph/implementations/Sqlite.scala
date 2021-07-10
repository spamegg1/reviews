package wikigraph.implementations

import scala.concurrent.{ExecutionContext, Future}

import wikigraph.Articles.ArticleId
import wikigraph.Wikipedia
import wikigraph.WikiResult
import wikigraph.errors.WikiError
import wikigraph.errors.WikiError.{ArticleNotFound, TitleNotFound, NoResult}

import slick.jdbc.SQLiteProfile.api.*

import scala.language.implicitConversions

case class Edge(toNode: Int, fromNode: Int)
case class Title(articleId: Int, title: String)

class Edges(tag: Tag) extends Table[Edge](tag, "edges"):
  val from_node = column[Int]("from_node")
  val to_node = column[Int]("to_node")
  val * = (to_node, from_node) <> ((to, from) => Edge(to, from), edge => Some((edge.fromNode, edge.toNode)))

class Titles(tag: Tag) extends Table[Title](tag, "titles"):
  val articleId = column[Int]("articleid")
  val title = column[String]("title")
  val * = (articleId, title) <> ((id, title) => Title(id, title), title => Some((title.articleId, title.title)))

class Sqlite extends Wikipedia:
  private val db = Database.forConfig("sqlite")
  private val edges = TableQuery(Edges(_))
  private val titles = TableQuery(Titles(_))

  def linksFrom(art: ArticleId)(using ExecutionContext): WikiResult[Set[ArticleId]] =
    val outsQuery = edges.filter(_.from_node === art.raw).map(_.to_node).result
    val f = db.run(outsQuery).map {
      case Nil => Left(Seq(ArticleNotFound(art)))
      case ls => Right(ls.map(ArticleId(_)).toSet)
    }
    WikiResult(f)

  def nameOfArticle(art: ArticleId)(using ExecutionContext): WikiResult[String] =
    val query = titles.filter(_.articleId === art.raw).take(1).result
    WikiResult(db.run(query).map(_.headOption).map {
      case None => Left(Seq(TitleNotFound(art)))
      case Some(record) => Right(record.title)
    })

  override def searchId(title: String)(using ExecutionContext): WikiResult[ArticleId] =
    val query = titles.filter(_.title === title).take(1).result
    WikiResult(db.run(query).map(_.headOption).map {
      case None => Left(Seq(NoResult(title)))
      case Some(head) => Right(ArticleId(head.articleId))
    })

