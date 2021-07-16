package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:
  /* Example Generator: */
//  lazy val genMap: Gen[Map[Int,Int]] = oneOf(
//    const(Map.empty[Int,Int]),
//    for
//      k <- arbitrary[Int]
//      v <- arbitrary[Int]
//      m <- oneOf(const(Map.empty[Int,Int]), genMap)
//    yield m.updated(k, v)
//  )

  lazy val genHeap: Gen[H] =
    for
      node <- arbitrary[A]                     // gives random element of type A

      // base heap to insert the node into: either empty, or a random heap from
      heap <- oneOf(const(empty), genHeap)  // a recursive call to this function

    yield insert(node, heap)        // creates new heap by inserting random node

  given Arbitrary[H] = Arbitrary(genHeap)

  // for any heap, adding the minimal element, and then finding it,
  // should return the element in question
  property("gen1") = forAll { (h: H) =>
    val m = if isEmpty(h) then 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  // adding a single element to an empty heap, and then removing
  // this element, should yield the element in question.
  property("min1") = forAll { (a: Int) =>
    val h: H = insert(a, empty)
    findMin(h) == a
  }

  // If you insert any two elements into an empty heap, finding the minimum
  // of the resulting heap should get the smallest of the two elements back.
  property("gen2") = forAll { (a: A, b: A) =>
    val h1: H = insert(a, empty)
    val h2: H = insert(b, h1)
    val minab: A = if (a < b) a else b
    findMin(h2) == minab
  }

  // If you insert an element into an empty heap, then delete the minimum,
  // the resulting heap should be empty.
  property("gen3") = forAll { (a: A) =>
    val h: H = insert(a, empty)
    deleteMin(h) == empty
  }

  // Given any heap, you should get a sorted sequence of elements
  // when continually finding and deleting minima.
  // (Hint: recursion and helper functions are your friends.)
  property("gen4") = forAll { (h: H) =>
    @scala.annotation.tailrec
    def helper(acc: List[A], h: H): Boolean = {
      // we kept removing mins and eventually hit an empty heap.
      // We accumulated the list of mins in order along the way.
      // So this list should already be sorted.
      if isEmpty(h) then acc == acc.sorted.reverse

      // Else, we remove the min and add it to the left of our accumulator.
      else helper(findMin(h) :: acc, deleteMin(h))
    }

    helper(List(), h)                 // true even if we start with empty heap h
  }

  // Finding min of melding of any two heaps should return min of one or the other
  property("gen5") = forAll { (h1: H, h2: H) =>
    if !isEmpty(h1) && !isEmpty(h2) then         // avoid NoSuchElementException
      val minh1: A = findMin(h1)
      val minh2: A = findMin(h2)
      val min12: A = if (minh1 < minh2) minh1 else minh2
      findMin(meld(h1, h2)) == min12

    else if isEmpty(h1) then
      findMin(meld(h1, h2)) == findMin(h2)
    else if isEmpty(h2) then
      findMin(meld(h1, h2)) == findMin(h1)
    else true
  }

  // Two heaps are equal iff, recursively removing mins from both, they have same
  // mins, and remaining heaps are equal, at each stage until both heaps are empty.
  property("gen6") = forAll { (h1: H, h2: H) =>
    @scala.annotation.tailrec
    def helper(h1: H, h2: H): Boolean =
      // we recursively kept removing mins from both, eventually hit empty heaps
      if isEmpty(h1) && isEmpty(h2) then true

      // else they should have equal mins and equal remaining heaps.
      else findMin(h1) == findMin(h2) && helper(deleteMin(h1), deleteMin(h2))

    // we create two equal random starting heaps from two unequal random heaps:
    // (1) melding the original h1 and h2,
    // (2) removing one's min, inserting it into the other, then melding.
    helper(meld(h1, h2),meld(deleteMin(h2), insert(findMin(h2), h1)))
  }
