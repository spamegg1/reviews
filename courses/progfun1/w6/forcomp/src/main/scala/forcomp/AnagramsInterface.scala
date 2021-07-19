package forcomp

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait AnagramsInterface:
  def wordOccurrences(w: String): List[(Char, Int)]
  def sentenceOccurrences(s: List[String]): List[(Char, Int)]
  def dictionaryByOccurrences: Map[List[(Char, Int)], List[String]]
  def wordAnagrams(word: String): List[String]
  def combinations(occurrences: List[(Char, Int)]): List[List[(Char, Int)]]
  def subtract(x: List[(Char, Int)], y: List[(Char, Int)]): List[(Char, Int)]
  def sentenceAnagrams(sentence: List[String]): List[List[String]]
