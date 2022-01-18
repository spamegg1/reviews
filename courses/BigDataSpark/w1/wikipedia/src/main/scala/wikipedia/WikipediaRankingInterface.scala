package wikipedia

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait WikipediaRankingInterface {
  def makeIndex(langs: List[String], rdd: RDD[WikipediaArticle]): RDD[(String, Iterable[WikipediaArticle])]
  def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int
  def rankLangs(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)]
  def rankLangsReduceByKey(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)]
  def rankLangsUsingIndex(index: RDD[(String, Iterable[WikipediaArticle])]): List[(String, Int)]
  def langs: List[String]
  def sc: SparkContext
  def wikiRdd: RDD[WikipediaArticle]
}
