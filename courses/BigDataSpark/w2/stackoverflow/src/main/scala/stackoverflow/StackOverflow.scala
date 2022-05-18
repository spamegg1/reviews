package stackoverflow

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext.*
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Logger, Level}

import annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Properties.isWin
import scala.io.Source
import scala.io.Codec

object Aliases:
  type Question = Posting
  type Answer = Posting
  type QID = Int
  type HighScore = Int
  type LangIndex = Int
import Aliases.*

/** A raw stackoverflow posting, either a question or an answer */
case class Posting(
  postingType: Int,
  id: Int,
  acceptedAnswer: Option[Int],
  parentId: Option[QID],
  score: Int,
  tags: Option[String]
) extends Serializable

/** The main class */
object StackOverflow extends StackOverflow:

  // Reduce Spark logging verbosity
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)

  if isWin then
    System.setProperty("hadoop.home.dir",
      System.getProperty("user.dir") + "\\winutils\\hadoop-3.3.1")

  @transient lazy val conf: SparkConf =
    new SparkConf().setMaster("local[2]").setAppName("StackOverflow")

  @transient lazy val sc: SparkContext = new SparkContext(conf)

  /** Main function */
  def main(args: Array[String]): Unit =
    val inputFileLocation: String = "/stackoverflow/stackoverflow-grading.csv"
    val resource = getClass.getResourceAsStream(inputFileLocation)
    val inputFile = Source.fromInputStream(resource)(Codec.UTF8)

    val lines   = sc.parallelize(inputFile.getLines().toList)
    val raw     = rawPostings(lines)
    val grouped = groupedPostings(raw)
    val scored  = scoredPostings(grouped)
    val vectors = vectorPostings(scored)
//    assert(vectors.count() == 1042132,
//      "Incorrect number of vectors: " + vectors.count())

    val means   = kmeans(sampleVectors(vectors), vectors, debug = true)
    val results = clusterResults(means, vectors)
    printResults(results)

/** The parsing and kmeans methods */
class StackOverflow extends StackOverflowInterface with Serializable:

  /** Languages */
  val langs =
    List(
      "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
      "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

  /** K-means parameter: How "far apart" languages should be 
    * for the kmeans algorithm? */
  def langSpread = 50000
  assert(langSpread > 0,
    "If langSpread is zero we can't recover the language from the input data!")

  /** K-means parameter: Number of clusters */
  def kmeansKernels = 45

  /** K-means parameter: Convergence criteria */
  def kmeansEta: Double = 20.0D

  /** K-means parameter: Maximum iterations */
  def kmeansMaxIterations = 120

  //
  //
  // Parsing utilities:
  //
  //

  /** Load postings from the given file */
  def rawPostings(lines: RDD[String]): RDD[Posting] =
    lines map (line =>
      val arr = line.split(",")
      Posting(
        postingType =    arr(0).toInt,
        id =             arr(1).toInt,
        acceptedAnswer = if arr(2) == "" then None else Some(arr(2).toInt),
        parentId =       if arr(3) == "" then None else Some(arr(3).toInt),
        score =          arr(4).toInt,
        tags =           if arr.length >= 6 then Some(arr(5).intern()) else None
    ))


  /** Group the questions and answers together */
  def groupedPostings(postings: RDD[Posting])                            // TODO
                              : RDD[(QID, Iterable[(Question, Answer)])] =
    val questions = postings filter (_.postingType == 1)
    val answers   = postings filter (_.postingType == 2)

    val qPrep = questions map (posting => (posting.id, posting))
    val aPrep = answers   map (posting => (posting.parentId.get, posting))

    qPrep
      .join(aPrep)
      .groupByKey()                                   // no shuffling up to here

  /** Compute the maximum score for each posting */
  def scoredPostings(grouped: RDD[(QID, Iterable[(Question, Answer)])])
                            : RDD[(Question, HighScore)] =

    def answerHighScore(as: Array[Answer]): HighScore =
      var highScore = 0
      var i = 0
      while i < as.length do
        val score = as(i).score
        if score > highScore then
          highScore = score
        i += 1
      highScore

    // TODO
    val fun = (iterable: Iterable[(Question, Answer)]) =>
      val question  = iterable.head._1
      val answers   = iterable.map(_._2).toArray
      val highScore = answerHighScore(answers)
      (question, highScore)

    grouped           // no shuffling
      .mapValues(fun)
      .map(_._2)

  /** Compute the vectors for the kmeans */
  def vectorPostings(scored: RDD[(Question, HighScore)])
                           : RDD[(LangIndex, HighScore)] =
    /** Return optional index of first language that occurs in `tags`. */
    def firstLangInTag(tag: Option[String], ls: List[String]): Option[Int] =
      tag match
        case None => None
        case Some(lang) =>
          val index = ls.indexOf(lang)
          if index >= 0 then Some(index) else None

    // TODO
    val optIndex: RDD[(Int, HighScore)] =                          // shuffling!
      scored map { case (question, highScore) =>
        (firstLangInTag(question.tags, langs).getOrElse(-1), highScore) }

    // this changes all keys by same constant factor, should not shuffle
    val vectors: RDD[(Int, HighScore)] =
      optIndex map { case (int, hs) => (int * langSpread, hs) }

    // no shuffling, narrow dependency
    val vectorsFiltered: RDD[(Int, HighScore)] = vectors filter (_._1 >= 0)

    vectorsFiltered.persist()  // IMPORTANT! WITHOUT THIS, THE GRADER TIMES OUT!

  /** Sample the vectors */
  def sampleVectors(vectors: RDD[(LangIndex, HighScore)]): Array[(Int, Int)] =

    assert(kmeansKernels % langs.length == 0,
      "kmeansKernels should be a multiple of the number of languages studied.")
    val perLang = kmeansKernels / langs.length

    // http://en.wikipedia.org/wiki/Reservoir_sampling
    def reservoirSampling(lang: Int,
                          iter: Iterator[Int],
                          size: Int)
                              : Array[Int] =
      val res = new Array[Int](size)
      val rnd = new util.Random(lang)

      for i <- 0 until size do
        assert(iter.hasNext, s"iterator must have at least $size elements")
        res(i) = iter.next()

      var i = size.toLong
      while iter.hasNext do
        val elt = iter.next()
        val j = math.abs(rnd.nextLong()) % i
        if j < size then
          res(j.toInt) = elt
        i += 1

      res

    val res =
      if langSpread < 500 then
        // sample the space regardless of the language
        vectors.distinct.takeSample(false, kmeansKernels, 42)
      else
        // sample the space uniformly from each language partition
        vectors
          .groupByKey()
          .flatMap((lang, vectors) =>
            reservoirSampling(lang, vectors.iterator.distinct, perLang)
              .map((lang, _)))
          .collect()

    assert(res.length == kmeansKernels, res.length)
    res

  //
  //
  //  Kmeans method:
  //
  //

  /** Main kmeans computation */
  @tailrec
  final def kmeans(means: Array[(Int, Int)],
                 vectors: RDD[(Int, Int)],
                    iter: Int = 1,
                   debug: Boolean = false)
                        : Array[(Int, Int)] =
    // TODO: Compute the groups of points that are the closest to each mean,
    // and then compute the new means of each group of points. Finally, compute
    // a Map that associate the old `means` values to their new values
    val newMeansMap: scala.collection.Map[(Int, Int), (Int, Int)] =      // TODO
      vectors
        .map(v => (findClosest(v, means), v))                       // shuffling
        .groupByKey()                                               // shuffling
        .mapValues(averageVectors)                               // no shuffling
        .collect()                                            // LAZY up to here
        .toMap

    val newMeans: Array[(Int, Int)] = means.map(oldMean => newMeansMap(oldMean))

    val distance = euclideanDistance(means, newMeans)

    if debug then
      println(s"""Iteration: $iter
                 |  * current distance: $distance
                 |  * desired distance: $kmeansEta
                 |  * means:""".stripMargin)
      for idx <- 0 until kmeansKernels do println(
        f"   ${means(idx).toString}%20s ==> ${newMeans(idx).toString}%20s  " +
        f"  distance: ${euclideanDistance(means(idx), newMeans(idx))}%8.0f")

    if converged(distance) then
      newMeans
    else if iter < kmeansMaxIterations then
      kmeans(newMeans, vectors, iter + 1, debug)
    else
      if debug then
        println("Reached max iterations!")
      newMeans

  //
  //
  //  Kmeans utilities:
  //
  //

  /** Decide whether the kmeans clustering converged */
  def converged(distance: Double) =
    distance < kmeansEta


  /** Return the euclidean distance between two points */
  def euclideanDistance(v1: (Int, Int), v2: (Int, Int)): Double =
    val part1 = (v1._1 - v2._1).toDouble * (v1._1 - v2._1)
    val part2 = (v1._2 - v2._2).toDouble * (v1._2 - v2._2)
    part1 + part2

  /** Return the euclidean distance between two points */
  def euclideanDistance(a1: Array[(Int, Int)], a2: Array[(Int, Int)]): Double =
    assert(a1.length == a2.length)
    var sum = 0d
    var idx = 0
    while idx < a1.length do
      sum += euclideanDistance(a1(idx), a2(idx))
      idx += 1
    sum

  /** Return the center that is the closest to `p` */
  def findClosest(p: (Int, Int), centers: Array[(Int, Int)]): (Int, Int) =
    var bestCenter: (Int, Int) = null
    var closest = Double.PositiveInfinity
    for center <- centers do
      val tempDist = euclideanDistance(p, center)
      if tempDist < closest then
        closest = tempDist
        bestCenter = center
    bestCenter


  /** Average the vectors */
  def averageVectors(ps: Iterable[(Int, Int)]): (Int, Int) =
    val iter = ps.iterator
    var count = 0
    var comp1: Long = 0
    var comp2: Long = 0
    while iter.hasNext do
      val item = iter.next()
      comp1 += item._1
      comp2 += item._2
      count += 1
    ((comp1 / count).toInt, (comp2 / count).toInt)

  //
  //
  //  Displaying results:
  //
  //
  def clusterResults(means: Array[(Int, Int)],
                   vectors: RDD[(LangIndex, HighScore)])
                          : Array[(String, Double, Int, Int)] =
    val closest = vectors.map(p => (findClosest(p, means), p))
    val closestGrouped = closest.groupByKey()

    val median = closestGrouped.mapValues { vs =>
      val mostCommon = vs                                                // TODO
        .groupBy(_._1)                                // group by language index
        .view
        .mapValues(_.size)             // which language occurred how many times
        .maxBy(_._2)                      // most common language in the cluster

      val langLabel  : String = langs(mostCommon._1 / langSpread)        // TODO
      val clusterSize: Int    = vs.size                                  // TODO
      val langPercent: Double =                                          // TODO
        (mostCommon._2.toDouble / clusterSize.toDouble) * 100.0

      val medianScore: Int =                                             // TODO
        val vsScoresSorted = vs
          .map(_._2)
          .toList
          .sorted
        val mid: Int = clusterSize / 2

        if   clusterSize % 2 == 0
        then (vsScoresSorted(mid - 1) + vsScoresSorted(mid)) / 2
        else vsScoresSorted(mid)

      (langLabel, langPercent, clusterSize, medianScore)
    }

    median
      .collect()
      .map(_._2)
      .sortBy(_._4)

  def printResults(results: Array[(String, Double, Int, Int)]): Unit =
    println("Resulting clusters:")
    println("  Score  Dominant language (%percent)  Questions")
    println("================================================")
    for (lang, percent, size, score) <- results do
      println(f"${score}%7d  ${lang}%-17s (${percent}%-5.1f%%)      ${size}%7d")
