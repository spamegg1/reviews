package quickcheck

trait IntHeap extends Heap:
  override type A = Int
  override def ord = scala.math.Ordering.Int

// http://www.brics.dk/RS/96/37/BRICS-RS-96-37.pdf

// Figure 1, page 3
trait Heap:
  type H // type of a heap
  type A // type of an element
  def ord: Ordering[A] // ordering on elements

  def empty: H // the empty heap
  def isEmpty(h: H): Boolean // whether the given heap h is empty

  def insert(x: A, h: H): H // the heap resulting from inserting x into h
  def meld(h1: H, h2: H): H // the heap resulting from merging h1 and h2

  def findMin(h: H): A // a minimum of the heap h
  def deleteMin(h: H): H // a heap resulting from deleting a minimum of h

// Figure 3, page 7
trait BinomialHeap extends Heap:

  type Rank = Int
  case class Node(x: A, r: Rank, c: List[Node])
  override type H = List[Node]

  protected def root(t: Node) = t.x
  protected def rank(t: Node) = t.r
  protected def link(t1: Node, t2: Node): Node = // t1.r == t2.r
    if ord.lteq(t1.x, t2.x) then Node(t1.x, t1.r + 1, t2 :: t1.c) else Node(t2.x, t2.r + 1, t1 :: t2.c)
  protected def ins(t: Node, ts: H): H = ts match
    case Nil => List(t)
    case tp :: ts => // t.r <= tp.r
      if t.r < tp.r then t :: tp :: ts else ins(link(t, tp), ts)

  override def empty = Nil
  override def isEmpty(ts: H) = ts.isEmpty

  override def insert(x: A, ts: H) = ins(Node(x, 0, Nil), ts)
  override def meld(ts1: H, ts2: H) = (ts1, ts2) match
    case (Nil, ts) => ts
    case (ts, Nil) => ts
    case (t1 :: ts1, t2 :: ts2) =>
      if t1.r < t2.r then t1 :: meld(ts1, t2 :: ts2)
      else if t2.r < t1.r then t2 :: meld(t1 :: ts1, ts2)
      else ins(link(t1, t2), meld(ts1, ts2))

  override def findMin(ts: H) = ts match
    case Nil => throw new NoSuchElementException("min of empty heap")
    case t :: Nil => root(t)
    case t :: ts =>
      val x = findMin(ts)
      if ord.lteq(root(t), x) then root(t) else x
  override def deleteMin(ts: H) = ts match
    case Nil => throw new NoSuchElementException("delete min of empty heap")
    case t :: ts =>
      def getMin(t: Node, ts: H): (Node, H) = ts match
        case Nil => (t, Nil)
        case tp :: tsp =>
          val (tq, tsq) = getMin(tp, tsp)
          if ord.lteq(root(t), root(tq)) then (t, ts) else (tq, t :: tsq)
      val (Node(_, _, c), tsq) = getMin(t, ts)
      meld(c.reverse, tsq)

trait Bogus1BinomialHeap extends BinomialHeap:
  override def findMin(ts: H) = ts match
    case Nil => throw new NoSuchElementException("min of empty heap")
    case t :: ts => root(t)

trait Bogus2BinomialHeap extends BinomialHeap:
  override protected def link(t1: Node, t2: Node): Node = // t1.r == t2.r
    if !ord.lteq(t1.x, t2.x) then Node(t1.x, t1.r + 1, t2 :: t1.c) else Node(t2.x, t2.r + 1, t1 :: t2.c)

trait Bogus3BinomialHeap extends BinomialHeap:
  override protected def link(t1: Node, t2: Node): Node = // t1.r == t2.r
    if ord.lteq(t1.x, t2.x) then Node(t1.x, t1.r + 1, t1 :: t1.c) else Node(t2.x, t2.r + 1, t2 :: t2.c)

trait Bogus4BinomialHeap extends BinomialHeap:
  override def deleteMin(ts: H) = ts match
    case Nil => throw new NoSuchElementException("delete min of empty heap")
    case t :: ts => meld(t.c.reverse, ts)

trait Bogus5BinomialHeap extends BinomialHeap:
  override def meld(ts1: H, ts2: H) = ts1 match
    case Nil => ts2
    case t1 :: ts1 => List(Node(t1.x, t1.r, ts1 ++ ts2))
