package calculator

object TweetLength extends TweetLengthInterface:
  final val MaxTweetLength = 140

  def tweetRemainingCharsCount(tweetText: Signal[String]): Signal[Int] = // TODO
    Signal(MaxTweetLength - tweetLength(tweetText()))

  def colorForRemainingCharsCount(remainingCharsCount: Signal[Int])
      : Signal[String] =                                                 // TODO
    Signal(if      remainingCharsCount() >= 15 then "green"
           else if remainingCharsCount() >= 0  then "orange"
           else                                     "red")

  /** Computes the length of a tweet, given its text string.
   *  This is not equivalent to text.length, as tweet lengths count the number
   *  of Unicode *code points* in the string.
   *  Note that this is still a simplified view of the reality. Full details
   *  can be found at
   *  https://dev.twitter.com/overview/api/counting-characters
   */
  private def tweetLength(text: String): Int =
    /* This should be simply text.codePointCount(0, text.length), but it
     * is not implemented in Scala.js 0.6.2.
     */
    if   text.isEmpty
    then 0
    else text.length
       - text.init.zip(text.tail).count((Character.isSurrogatePair _).tupled)
