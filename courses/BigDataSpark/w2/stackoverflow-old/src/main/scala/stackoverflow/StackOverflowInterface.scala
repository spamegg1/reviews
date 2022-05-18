package stackoverflow

import org.apache.spark.rdd.RDD

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait StackOverflowInterface {
  def clusterResults(means: Array[(Int, Int)], vectors: RDD[(LangIndex, HighScore)]): Array[(String, Double, Int, Int)]
  def groupedPostings(postings: RDD[Posting]): RDD[(QID, Iterable[(Question, Answer)])]
  def kmeans(means: Array[(Int, Int)], vectors: RDD[(Int, Int)], iter: Int = 1, debug: Boolean = false): Array[(Int, Int)]
  def rawPostings(lines: RDD[String]): RDD[Posting]
  def sampleVectors(vectors: RDD[(LangIndex, HighScore)]): Array[(Int, Int)]
  def scoredPostings(grouped: RDD[(QID, Iterable[(Question, Answer)])]): RDD[(Question, HighScore)]
  def vectorPostings(scored: RDD[(Question, HighScore)]): RDD[(LangIndex, HighScore)]
  def langSpread: Int
  val langs: List[String]
}
