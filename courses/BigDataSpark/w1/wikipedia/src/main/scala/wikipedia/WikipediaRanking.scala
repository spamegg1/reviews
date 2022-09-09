package wikipedia

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext.*
import org.apache.log4j.{Logger, Level}

import org.apache.spark.rdd.RDD
import scala.util.Properties.isWin

case class WikipediaArticle(title: String, text: String):
  /**
    * @return Whether the text of this article mentions `lang` or not
    * @param lang Language to look for (e.g. "Scala")
    */
  def mentionsLanguage(lang: String): Boolean = text.split(' ').contains(lang)

object WikipediaRanking extends WikipediaRankingInterface:
  // Reduce Spark logging verbosity
  Logger
    .getLogger("org.apache.spark")
    .setLevel(Level.ERROR)

  if isWin then
    System.setProperty("hadoop.home.dir",
      System.getProperty("user.dir") + "\\winutils\\hadoop-3.3.1")

  val langs = List(
    "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
    "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

  val conf: SparkConf =                                                  // TODO
    new SparkConf()
      .setMaster("local[4]")
      .setAppName("MyApp")

  val sc: SparkContext = new SparkContext(conf)                          // TODO

  // Hint: use a combination of `sc.parallelize`,
  // `WikipediaData.lines` and `WikipediaData.parse`
  val wikiRdd: RDD[WikipediaArticle] =                                   // TODO
    sc.parallelize(WikipediaData.lines map WikipediaData.parse, 4)

  /** Returns the number of articles on which the language `lang` occurs.
   *  Hint1: consider using method `aggregate` on RDD[T].
   *  Hint2: consider using method `mentionsLanguage` on `WikipediaArticle`
   */
  def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int = // TODO
    val fun = (count: Int, article: WikipediaArticle) =>
      count + (if article.mentionsLanguage(lang) then 1 else 0)
    rdd.aggregate(0)(fun, _ + _)                          // aggregate is EAGER!

  /* (1) Use `occurrencesOfLang` to compute the ranking of the languages
   *     (`val langs`) by determining the number of Wikipedia articles that
   *     mention each language at least once. Don't forget to sort the
   *     languages by their occurrence, in decreasing order!
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangs(langs: List[String],
                rdd  : RDD[WikipediaArticle])
                     : List[(String, Int)] =                             // TODO
    val langRanks =
      for lang <- langs
      yield (lang, occurrencesOfLang(lang, rdd))

    langRanks.sortBy(-_._2)

  /* Compute an inverted index of the set of articles, mapping each language
   * to the Wikipedia pages in which it occurs.
   */
  def makeIndex(langs: List[String],
                rdd  : RDD[WikipediaArticle])
                     : RDD[(String, Iterable[WikipediaArticle])] =       // TODO
    val fun = (article: WikipediaArticle) =>
      for lang <- langs filter article.mentionsLanguage
      yield (lang, article)

    rdd                       // flatMap, for/yield, filter, groupByKey are LAZY
      .flatMap(fun)                           // RDD[(String, WikipediaArticle)]
      .groupByKey                   // RDD[(String, Iterable[WikipediaArticle])]

  /* (2) Compute the language ranking again, but now using the inverted index.
   *     Can you notice a performance improvement?
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangsUsingIndex(index: RDD[(String, Iterable[WikipediaArticle])])
                               : List[(String, Int)] =                   // TODO
    index
      .mapValues(_.size)
      .collect                                               // LAZY up to here!
      .toList
      .sortBy(-_._2)

  /* (3) Use `reduceByKey` so that the computation of the index
   *     and the ranking are combined.
   *     Can you notice an improvement in performance compared to measuring
   *     *both* the computation of the index
   *     and the computation of the ranking? If so, can you think of a reason?
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangsReduceByKey(langs: List[String],
                             rdd: RDD[WikipediaArticle])
                                : List[(String, Int)] =                  // TODO
    val fun = (article: WikipediaArticle) =>
      for lang <- langs filter article.mentionsLanguage
      yield (lang, 1)

    rdd
      .flatMap(fun)
      .reduceByKey(_+_)
      .sortBy(-_._2)
      .collect                                               // LAZY up to here!
      .toList

  def main(args: Array[String]): Unit =

    /* Languages ranked according to (1) */
    val langsRanked: List[(String, Int)] =
      timed("Part 1: naive ranking", rankLangs(langs, wikiRdd))

    /* An inverted index mapping languages to wikipedia pages
     * on which they appear */
    def index: RDD[(String, Iterable[WikipediaArticle])] =
      makeIndex(langs, wikiRdd)

    /* Languages ranked according to (2), using the inverted index */
    val langsRanked2: List[(String, Int)] =
      timed("Part 2: ranking using inverted index", rankLangsUsingIndex(index))

    /* Languages ranked according to (3) */
    val langsRanked3: List[(String, Int)] =
      timed("Part 3: ranking using reduceByKey",
            rankLangsReduceByKey(langs, wikiRdd))

    /* Output the speed of each ranking */
    println(timing)
    sc.stop()

  val timing = new StringBuffer
  def timed[T](label: String, code: => T): T =
    val start = System.currentTimeMillis()
    val result = code
    val stop = System.currentTimeMillis()
    timing.append(s"Processing $label took ${stop - start} ms.\n")
    result
