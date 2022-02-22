package quickcheck

// This file contains the definition of the interface
// for the heap data structure, and several implementations.
// You should NOT modify this file.

/**
 * Interface for the heap data structure
 *
 * This interface defines the operations for constructing
 * and manipulating a heap. A heap is modeled by the
 * type `List[Node]`, and the only way to construct
 * a heap is to use the constructor `empty`, and then
 * to use the operation `insert` to add more elements
 * to it.
 *
 * @see http://www.brics.dk/RS/96/37/BRICS-RS-96-37.pdf
 */
trait HeapInterface:

  /** @return an empty binomial heap */
  def empty: List[Node]
  /** @return whether the given `heap` is empty or not */
  def isEmpty(heap: List[Node]): Boolean

  /** Insert the given `value` in the `heap` */
  def insert(value: Int, heap: List[Node]): List[Node]
  /** Merge two heaps */
  def meld(heap1: List[Node], heap2: List[Node]): List[Node]

  /** Find the minimum value of a heap, or raise an error if heap is empty */
  def findMin(heap: List[Node]): Int
  /** Delete the minimum value of a heap, or raise an error if heap is empty */
  def deleteMin(heap: List[Node]): List[Node]

  case class Node(value: Int, rank: Int, children: List[Node])

end HeapInterface

// The remaining of the file contains correct and bogus implementations
// of the heap interface.
// You don't need to go through these implementations.

class BinomialHeap extends HeapInterface:

  protected def root(node: Node) = node.value
  protected def rank(node: Node) = node.rank
  protected def link(node1: Node, node2: Node): Node =
    // node1.rank == node2.rank
    if   node1.value <= node2.value
    then Node(node1.value, node1.rank + 1, node2 :: node1.children)
    else Node(node2.value, node2.rank + 1, node1 :: node2.children)

  protected def ins(node: Node, heap: List[Node]): List[Node] = heap match
    case Nil => List(node)
    case node2 :: heap2 =>                            // node.rank <= node2.rank
      if   node.rank < node2.rank
      then node :: node2 :: heap2
      else ins(link(node, node2), heap2)

  def empty: List[Node] = Nil

  def isEmpty(heap: List[Node]): Boolean = heap.isEmpty

  def insert(value: Int, heap: List[Node]): List[Node] =
    ins(Node(value, 0, Nil), heap)

  def meld(heap1: List[Node], heap2: List[Node]): List[Node] =
    (heap1, heap2) match
      case (Nil, heap) => heap
      case (heap, Nil) => heap
      case (node1 :: heap1, node2 :: heap2) =>
        if      node1.rank < node2.rank
        then    node1 :: meld(heap1, node2 :: heap2)
        else if node2.rank < node1.rank
        then    node2 :: meld(node1 :: heap1, heap2)
        else    ins(link(node1, node2), meld(heap1, heap2))

  def findMin(heap: List[Node]): Int = heap match
    case Nil => throw new NoSuchElementException("min of empty heap")
    case node :: Nil => root(node)
    case node :: heap =>
      val x = findMin(heap)
      if root(node) <= x then root(node) else x

  def deleteMin(heap: List[Node]): List[Node] = heap match
    case Nil => throw new NoSuchElementException("delete min of empty heap")
    case node :: heap =>
      def getMin(node: Node, heap: List[Node]): (Node, List[Node]) =
        heap match
          case Nil => (node, Nil)
          case node2 :: heap2 =>
            val (node3, heap3) = getMin(node2, heap2)
            if   root(node) <= root(node3)
            then (node, heap)
            else (node3, node :: heap3)

      val (Node(_, _, children), heap3) = getMin(node, heap)
      meld(children.reverse, heap3)

end BinomialHeap

class Bogus1BinomialHeap extends BinomialHeap:
  override def findMin(heap: List[Node]) = heap match
    case Nil => throw new NoSuchElementException("min of empty heap")
    case node :: heap => root(node)

class Bogus2BinomialHeap extends BinomialHeap:
  override protected def link(node1: Node, node2: Node): Node =
     // node1.rank == node2.rank
    if   node1.value <= node2.value
    then Node(node1.value, node1.rank + 1, node2 :: node2.children)
    else Node(node2.value, node2.rank + 1, node1 :: node2.children)

class Bogus3BinomialHeap extends BinomialHeap:
  override protected def link(node1: Node, node2: Node): Node =
     // node1.rank == node2.rank
    if   node1.value <= node2.value
    then Node(node1.value, node1.rank + 1, node1 :: node1.children)
    else Node(node2.value, node2.rank + 1, node2 :: node2.children)

class Bogus4BinomialHeap extends BinomialHeap:
  override def deleteMin(heap: List[Node]) = heap match
    case Nil => throw new NoSuchElementException("delete min of empty heap")
    case node :: heap => meld(node.children.reverse, heap)

class Bogus5BinomialHeap extends BinomialHeap:
  override def meld(heap1: List[Node], heap2: List[Node]) = heap1 match
    case Nil => heap2
    case node1 :: heap1 => List(Node(node1.value, node1.rank, heap1 ++ heap2))
