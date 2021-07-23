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
  def namedLinks(of: ArticleId): WikiResult[Set[String]] = // TODO
    val linkSet: WikiResult[Set[ArticleId]] = client.linksFrom(of)
    val linkSeq: WikiResult[Seq[ArticleId]] = linkSet.map(_.toSeq)
    val traversedSeq: WikiResult[Seq[String]] =
      linkSeq.flatMap(list => WikiResult.traverse(list)(client.nameOfArticle))
    traversedSeq.map(_.toSet)

  /**
    * Computes the distance between two articles using breadth first search.
    * 
    * @param start compute the distance from this node to `target`
    * @param target compute the distance from `start` to this node
    * @param maxDepth stop if the depth exceeds this value
    * 
    * @return an asynchronous computation that might fail. If the maximal distance
    *         is exceeded during the search, the result is None
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
  : WikiResult[Option[Int]] = // TODO
    import scala.collection.immutable.Queue
    /**
      * This recursive method iterates on the graph.
      * 
      * The algorithm is detailed in the assignment description.
      * - When the queue is empty or the maxDepth is exceeded (in the next element of the queue),
      *   the search fails with None
      * - Otherwise a node is retrieved from the queue and its neighbors fetched from the dataset.
      *   The search succeeds if `target` is in this set.
      *   Otherwise we recursively search after modifying `unknowns` and adding the unknown
      *   neighbors to the queue with the correct distance.
      * 
      * @param visited keep the nodes the are already visited, used no to iterate infinitely on
      *        graph cycles
      * @param q the next nodes to visit and their distance from `start`
      *
      * Issues 1 and 2: you want to use flatMap here, so that you can write the
      * continuation of your program in the function passed to flatMap, which
      * takes a Set[ArticleId], and returns a successful WikiResult if the
      * neighbor nodes contain the target, or it returns the result of
      * recursively calling iter, with the updated queue. (so, the recursive
      * call to iter is performed within the function passed to flatMap)
      * Issue 3: I think the problem comes from our side here. I would say that
      * the instructions could be clearer, and the specification of the methods
      * fallbackTo and orElse could be clarified further as well. What we should
      * add in the instructions (I’m going to do it as I write this message), is
      * that any system failure will stop the algorithm, which would return that
      * failure, on the other hand, domain errors should not stop the algorithm,
      * which would just skip that node and iterate on the remaining ones. Both
      * orElse and fallbackTo method recover from domain errors, so you will use
      * one of these methods, depending on which one works better for you given
      * its type signature.
      */
    def iter(visited: Set[ArticleId], q: Queue[(Int, ArticleId)])
    : WikiResult[Option[Int]] = // TODO

      if q.isEmpty then
        WikiResult.successful(None)

      else
        val ((depth, aId), queue): ((Int, ArticleId), Queue[(Int, ArticleId)]) =
          q.dequeue

        if depth >= maxDepth then
          WikiResult.successful(None)

        else
          val neighbors: WikiResult[Set[ArticleId]] = client.linksFrom(aId)

          val fun: Set[ArticleId] => WikiResult[Option[Int]] = nodes =>
            if nodes.contains(target) then
              WikiResult.successful(Some(depth))
            else
              val newVisited: Set[ArticleId] = visited + aId
              val updatedNodes: Set[(Int, ArticleId)] =
                nodes.map(aid => (depth + 1, aid))
              iter(newVisited, queue.enqueueAll(updatedNodes))

          neighbors
            .flatMap(fun)
            .fallbackTo(iter(visited + aId, queue))

    if start == target then WikiResult.successful(Some(0))
    else iter(Set(start), Queue(1->start))

  /**
    * Computes the distances between some pages provided the list of their titles.
    * Do not compute the distance from page and itself.
    * 
    * @param titles names of the articles
    * @param maxDepth stop the search this value of distance is exceeded
    * 
    * @return An asynchronous computation of the following form:
    *         Seq((distanceFromTitle, distanceToTitle, distance), ...)
    * 
    * Hint: You should use the methods that you implemented on WikiResult as well as
    *       breadthFirstSearch
    */
  def distanceMatrix(titles: List[String], maxDepth: Int = 50)
  : WikiResult[Seq[(String, String, Option[Int])]] = // TODO
    // Let's play a game of Type Inference Puzzle!
    // traverse:
    // as: Seq[A] = titles: List[String]
    // so: A = String
    // WikiResult[Seq[B]] = WikiResult[Seq[(String, String, Option[Int])]
    // so: B = (String, String, Option[Int])
    // we need: f: A => WikiResult[B]
    // so: f: String => WikiResult[(String, String, Option[Int])]
    // this can be done by zipping
    //    WikiResult[String],
    //    WikiResult[String],
    //    WikiResult[Option[Int]] <- this is returned by breadthFirstSearch
    // We can also zip to get Seq[(String, String)] and use breadthFirstSearch.
    // We look at pairs of titles from the given list. So we would need:
    // f: (String, String) => WikiResult[(String, String, Option[Int])]
    // First we can convert (String, String) to (ArticleId, ArticleId)
    val titlePairs: Seq[(String, String)] =
      for
        source <- titles
        dest <- titles
        if source != dest
      yield
        (source, dest)

    def fun(pair: (String, String)): WikiResult[(String, String, Option[Int])] =
      val (source, dest): (String, String) = pair

      val aIdPair: WikiResult[(ArticleId, ArticleId)] =
        client.searchId(source).zip(client.searchId(dest))

      def fun2(aidPair: (ArticleId, ArticleId)):
      WikiResult[(String, String, Option[Int])] =
        val (sourceId, destId) = aidPair
        val res: WikiResult[Option[Int]] =
          breadthFirstSearch(sourceId, destId, maxDepth)
        res.flatMap(distance => WikiResult.successful(source, dest, distance))

      aIdPair.flatMap(fun2)

    WikiResult.traverse(titlePairs)(fun)


end Wikigraph
