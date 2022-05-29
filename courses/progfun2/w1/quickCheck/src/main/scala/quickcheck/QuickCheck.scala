package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll
import scala.annotation.tailrec                                          // TODO

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:
  /* Example Generator (does not do anything in the solution): */
  lazy val genMap: Gen[Map[Int,Int]] = oneOf(                           // GIVEN
    const(Map.empty[Int,Int]),
    for
      k <- arbitrary[Int]
      v <- arbitrary[Int]
      m <- oneOf(const(Map.empty[Int,Int]), genMap)
    yield m.updated(k, v)
  )

  lazy val genHeap: Gen[H] =                                             // TODO
    for
      node <- arbitrary[A]                                            // A = Int
      heap <- oneOf(const(empty), genHeap)
    yield insert(node, heap)

  given Arbitrary[H] = Arbitrary(genHeap)

  // for any heap, adding the minimal element, and then finding it,
  // should return the element in question
  property("gen1") = forAll { (h: H) =>                                 // GIVEN
    val m = if isEmpty(h) then 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  // adding a single element to an empty heap, and then removing
  // this element, should yield the element in question.
  property("min1") = forAll { (a: Int) =>                                // TODO
    val h: H = insert(a, empty)
    findMin(h) == a
  }

  // If you insert any two elements into an empty heap, finding the minimum
  // of the resulting heap should get the smallest of the two elements back.
  property("gen2") = forAll { (a: A, b: A) =>                            // TODO
    val h1: H = insert(a, empty)
    val h2: H = insert(b, h1)
    val minab: A = if a < b then a else b
    findMin(h2) == minab
  }

  // If you insert an element into an empty heap, then delete the minimum,
  // the resulting heap should be empty.
  property("gen3") = forAll { (a: A) =>                                  // TODO
    val h: H = insert(a, empty)
    deleteMin(h) == empty
  }

  // Given any heap, you should get a sorted sequence of elements
  // when continually finding and deleting minima.
  // (Hint: recursion and helper functions are your friends.)
  property("gen4") = forAll { (h: H) =>                                  // TODO
    @tailrec
    def helper(acc: List[A], h: H): Boolean =
      if isEmpty(h) then acc == acc.sorted.reverse
      else helper(findMin(h) :: acc, deleteMin(h))

    helper(List(), h)                 // true even if we start with empty heap h
  }

  // Finding min of melding of any two heaps should return min of one or other
  property("gen5") = forAll { (h1: H, h2: H) =>                          // TODO
    if !isEmpty(h1) && !isEmpty(h2) then         // avoid NoSuchElementException
      val minh1: A = findMin(h1)
      val minh2: A = findMin(h2)
      val min12: A = if minh1 < minh2 then minh1 else minh2
      findMin(meld(h1, h2)) == min12

    else if isEmpty(h1) then findMin(meld(h1, h2)) == findMin(h2)
    else if isEmpty(h2) then findMin(meld(h1, h2)) == findMin(h1)
    else true
  }

  // Two heaps are equal iff, recursively removing mins from both, they have same
  // mins, and remaining heaps are equal, at each stage until both heaps are empty.
  // THIS PROPERTY IS ENOUGH ALL BY ITSELF TO GET 10/10 FROM THE GRADER.
  property("gen6") = forAll { (h1: H, h2: H) =>                          // TODO
    @tailrec
    def helper(h1: H, h2: H): Boolean =
      if   isEmpty(h1) && isEmpty(h2)
      then true
      else findMin(h1) == findMin(h2) && helper(deleteMin(h1), deleteMin(h2))

    // we create two equal random starting heaps from two unequal random heaps:
    // (1) removing h1's min, inserting it into h2, then melding.
    // (2) removing h2's min, inserting it into h1, then melding.
    val (m1, m2) = (findMin(h1), findMin(h2))
    helper(meld(insert(m2, h1), deleteMin(h2)),
           meld(deleteMin(h1), insert(m1, h2)))
  }
