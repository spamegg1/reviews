package objsets

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait TweetSetInterface:
  def incl(tweet: Tweet): TweetSet
  def remove(tweet: Tweet): TweetSet
  def contains(tweet: Tweet): Boolean
  def foreach(f: Tweet => Unit): Unit
  def union(that: TweetSet): TweetSet
  def mostRetweeted: Tweet
  def descendingByRetweet: TweetList
