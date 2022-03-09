package wikigraph

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import Articles.ArticleId


/**
  * Analyze the graph of Wikipedia Articles
  *
  * @param client the wikipedia client providing access to the data.
  */
final class Wikigraph(client: Wikipedia):

  /**
    * Retrieves the names of the articles linked in a page.
    * 
    * @param of the id of the page from which the links are retrieved
    * 
    * Hint: Use the methods that you implemented in WikiResult.
    */
  def namedLinks(of: ArticleId): WikiResult[Set[String]] =               // TODO
    for
      linkSet <- client.linksFrom(of)                   // Set[ArticleId] <- ...
      linkSeq = linkSet.toSeq                                  // Seq[ArticleId]
      fun = client.nameOfArticle                      // ArticleId => WR[String]
      traversedSeq <- WikiResult.traverse(linkSeq)(fun)    // Seq[String] <- ...
    yield
      traversedSeq.toSet                                          // Set[String]

  /**
    * Computes the distance between two articles using breadth first search.
    * 
    * @param start compute the distance from this node to `target`
    * @param target compute the distance from `start` to this node
    * @param maxDepth stop if the depth exceeds this value
    * 
    * @return the result of an asynchronous computation that might fail. If the
    *        maximal distance is exceeded during the search, the result is None.
    * 
    * Note: if a domain error occurs when jumping from node to node,
    *       fallback by ignoring the problematic node. On the other hand,
    *       any system failure just ends the algorithm by returning that
    *       system failure.
    * 
    * Hint: More information is provided in the description of the assignment
    *       Use the `enqueue` and `dequeue` methods of `Queue`.
    */
  def breadthFirstSearch(start: ArticleId, target: ArticleId, maxDepth: Int)
                        : WikiResult[Option[Int]] =
    import scala.collection.immutable.Queue
    /**
      * This recursive method iterates on the graph.
      * 
      * The algorithm is detailed in the assignment description.
      * - When the queue is empty or the maxDepth is exceeded
      *   (in the next element of the queue),
      *   the search fails with None
      * - Otherwise a node is retrieved from the queue and its neighbors
      *   fetched from the dataset. 
      *   The search succeeds if `target` is in this set of neighbors.
      *   Otherwise we recursively search after modifying `visited` and adding
      *   the unknown neighbors to the queue with the correct distance.
      * 
      * @param visited keep the nodes the are already visited, used no to
      *        iterate infinitely on graph cycles
      * @param q the next nodes to visit and their distance from `start`
      * 
      * HINT: Have a look at the implementation of [[wikigraph.WikiResult#zip]] 
      *       to see how to use [[wikigraph.WikiResult#flatMap]] to work with 
      *       the content of [[wikigraph.WikiResult]].
      *       This is useful to chain successive calls to iter.
      * 
      * HINT: Do not forget, if a domain error occurs while retrieving the links
      *       from an article, to fallback by continuing iterating on the other
      *       articles of the queue.
      *       Refer to the documentation of [[wikigraph.WikiResult#fallbackTo]].
      */
    def iter(visited: Set[ArticleId], q: Queue[(Int, ArticleId)])
            : WikiResult[Option[Int]] =                                  // TODO
      if   q.isEmpty
      then WikiResult.successful(None)
      else
        val ((depth, aId), queue) = q.dequeue
        if   depth >= maxDepth
        then WikiResult.successful(None)
        else
        if   visited contains aId
        then iter(visited, queue)
        else
          val neighbors = client.linksFrom(aId)            // WR[Set[ArticleId]]
          def fun(nodes: Set[ArticleId]): WikiResult[Option[Int]] =
            if   nodes contains target
            then WikiResult.successful(Some(depth))
            else
              val updatedNodes = nodes map ((depth + 1, _))
              iter(visited + aId, queue enqueueAll updatedNodes)

          neighbors
            .flatMap(fun)
            .fallbackTo(iter(visited, queue))

    if   start == target
    then WikiResult.successful(Some(0))
    else iter(Set(), Queue(1 -> start))

  /**
    * Computes the distances between articles whose list of titles is provided.
    *
    * For each article in the list, compute the distance to all the other
    * articles in the list.
    *
    * Do not compute the distance from an article and itself.
    * 
    * @param titles   titles of the articles
    * @param maxDepth stop the search when this value of distance is exceeded
    * 
    * @return An asynchronous result containing a list of tuples with
    *         the following elements:
    *           - the title of the article from which the distance is computed
    *           - the title of the article to which the distance is computed
    *           - the distance (as returned by `breadFirstSearch`).
    *          For instance:
    *          {{{
    *            Seq(
    *              ("article-1", "article-2", Some(1)),
    *              ("article-2", "article-1", None),
    *              ...
    *            )
    *          }}}
    *
    * Hint: You should use the methods that you implemented on `WikiResult`
    *       as well as `breadthFirstSearch`
    */
  def distanceMatrix(titles: List[String], maxDepth: Int = 50)
                    : WikiResult[Seq[(String, String, Option[Int])]] =   // TODO
    val titlePairs: Seq[(String, String)] =
      for
        source <- titles                               // String <- List[String]
        dest <- titles                                 // String <- List[String]
        if source != dest
      yield
        (source, dest)                                       // (String, String)

    def fun(titlePair: (String, String))
           : WikiResult[(String, String, Option[Int])] =
      val (source, dest) = titlePair
      for
        idPair <- client.searchId(source) zip client.searchId(dest)
        (sourceId, destId) = idPair
        distance <- breadthFirstSearch(sourceId, destId, maxDepth)
        result <- WikiResult.successful(source, dest, distance)
      yield result

    WikiResult.traverse(titlePairs)(fun)

end Wikigraph
