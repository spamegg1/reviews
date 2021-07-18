package objsets

object TweetReader:

  object ParseTweets:
    def regexParser(s: String): List[Map[String, Any]] =
      // In real life. you would use an actual JSON library...
      val tweetRegex = """^\{ .*"user": "([^"]+)", "text": "([^"]+)", "retweets": ([\\.0-9]+) \},?$""".r
      s.split("\r?\n").toList.tail.init.map {
        case tweetRegex(user, text, retweets) => Map("user" -> user, "text" -> text, "retweets" -> retweets.toDouble)
      }

    def getTweets(user: String, json: String): List[Tweet] =
      for map <- regexParser(json) yield
        val text = map("text")
        val retweets = map("retweet_count")
        Tweet(user, text.toString, retweets.toString.toDouble.toInt)

    def getTweetData(user: String, json: String): List[Tweet] =
      // is list
      val l = regexParser(json)
      for map <- l yield
        val text = map("text")
        val retweets = map("retweets")
        Tweet(user, text.toString, retweets.toString.toDouble.toInt)

  def toTweetSet(l: List[Tweet]): TweetSet =
    l.foldLeft(Empty(): TweetSet)(_.incl(_))

  def unparseToData(tws: List[Tweet]): String =
    val buf = StringBuffer()
    for tw <- tws do
      val json = "{ \"user\": \"" + tw.user + "\", \"text\": \"" +
                                    tw.text.replaceAll(""""""", "\\\\\\\"") + "\", \"retweets\": " +
                                    tw.retweets + ".0 }"
      buf.append(json + ",\n")
    buf.toString

  val sites = List("gizmodo", "TechCrunch", "engadget", "amazondeals", "CNET", "gadgetlab", "mashable")

  private val gizmodoTweets = TweetReader.ParseTweets.getTweetData("gizmodo", TweetData.gizmodo)
  private val techCrunchTweets = TweetReader.ParseTweets.getTweetData("TechCrunch", TweetData.TechCrunch)
  private val engadgetTweets = TweetReader.ParseTweets.getTweetData("engadget", TweetData.engadget)
  private val amazondealsTweets = TweetReader.ParseTweets.getTweetData("amazondeals", TweetData.amazondeals)
  private val cnetTweets = TweetReader.ParseTweets.getTweetData("CNET", TweetData.CNET)
  private val gadgetlabTweets = TweetReader.ParseTweets.getTweetData("gadgetlab", TweetData.gadgetlab)
  private val mashableTweets = TweetReader.ParseTweets.getTweetData("mashable", TweetData.mashable)

  private val sources = List(gizmodoTweets, techCrunchTweets, engadgetTweets, amazondealsTweets, cnetTweets, gadgetlabTweets, mashableTweets)

  val tweetMap: Map[String, List[Tweet]] =
    Map() ++ Seq((sites(0) -> gizmodoTweets),
                 (sites(1) -> techCrunchTweets),
                 (sites(2) -> engadgetTweets),
                 (sites(3) -> amazondealsTweets),
                 (sites(4) -> cnetTweets),
                 (sites(5) -> gadgetlabTweets),
                 (sites(6) -> mashableTweets))

  val tweetSets: List[TweetSet] = sources.map(tweets => toTweetSet(tweets))

  private val siteTweetSetMap: Map[String, TweetSet] =
    Map() ++ (sites zip tweetSets)

  private def unionOfAllTweetSets(curSets: List[TweetSet], acc: TweetSet): TweetSet =
    if curSets.isEmpty then
      acc
    else
      unionOfAllTweetSets(curSets.tail, acc.union(curSets.head))

  val allTweets: TweetSet = unionOfAllTweetSets(tweetSets, Empty())
