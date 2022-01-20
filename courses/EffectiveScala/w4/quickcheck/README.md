In this assignment, you will work with the [ScalaCheck](https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md) library for automated property-based testing. Your task is to implement property-based tests that distinguish between correct and incorrect implementations of a priority queue. A priority queue is a collection that gives a priority to each element in the collection, and allows elements to be retrieved in order from highest to lowest priority. Our priority queues are in turn implemented using a data structure known as a heap.

## Heaps

You're given several implementations of a purely functional data structure called a heap, which is a data structure that supports quickly finding and removing the smallest element. The main operations are **insert**, **meld**, **findMin**, and **deleteMin**. Here is the interface:

```scala
trait HeapInterface:
  /** the empty heap */
  def empty: List[Node]
  /** whether the given `heap` is empty */
  def isEmpty(heap: List[Node]): Boolean

  /** the heap resulting from inserting `x` into `heap` */
  def insert(x: Int, heap: List[Node]): List[Node]
  /** the heap resulting from merging `heap1` and `heap2` */
  def meld(heap1: List[Node], heap2: List[Node]): List[Node]

  /** a minimum of the heap `heap` */
  def findMin(heap: List[Node]): Int
  /** a heap resulting from deleting a minimum of `heap` */
  def deleteMin(heap: List[Node]): List[Node]

  case class Node(value: Int, rank: Int, children: List[Node])
end Heap
```

A heap is modeled by a **List[Node]**. The only way to construct a heap is to use the method **empty**, and then the method **insert**.

All these operations are *pure*; they never modify the given heaps, and may return new heaps. This purely functional interface is taken from Brodal & Okasaki's paper, [*Optimal Purely Functional Priority Queues*](http://www.brics.dk/RS/96/37/BRICS-RS-96-37.pdf).

## Priority Queues

A priority queue is a queue, in which each element is assigned a "priority". In classical queues elements can be retrieved in first-in, first-out order, whereas in a priority queue elements are retrieved as per the priority they are assigned. As such, classical queues are therefore just priority queues where the priority is the order in which elements are inserted.

As seen in the above interface, we can create a queue by

- instantiating an empty queue.
- inserting an element into a queue (with an attached priority), thereby creating a new queue.
- melding two queues, which results in a new queue that contains all the elements of the first queue and all the elements of the second queue.

In addition, we can test whether a queue is empty or not with **isEmpty**. If you have a non-empty queue, you can find its minimum with **findMin**. You can also get a smaller queue from a non-empty queue by deleting the minimum element with **deleteMin**. In this assignment, the heap operates on **Int** elements with their values as priorities, so **findMin** finds the least integer in the heap.

## Property-Based Tests

Download the [handout archive](https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3a/effective-quickcheck.zip).

You are given multiple implementations of **HeapInterface** in the file **src/main/scala/quickcheck/Heap.scala**. Only one of them is correct, while the other ones have bugs. Your goal is to complete the implementation of properties that will be satisfied by the correct implementation, but will fail for at least one incorrect implementation, thus revealing it's buggy.

You should complete the properties written in the body of the **HeapProperties** class in the file **src/main/scala/quickcheck/HeapProperties.scala**.

### Writing properties

The idea behind property-based testing is to verify that certain properties hold on your implementations. Instead of specifying exactly which inputs our properties should satisfy we instead generate random inputs, and run each property test on these randomly generated inputs. This way we increase the likelihood that our implementation is correct.

For example, we would like to check that adding a single element to an empty heap, and then removing this element, should yield the element in question. We would write this requirement as follows:

```scala
forAll { (x: Int) =>
  val heap = insert(x, empty)
  findMin(heap) == x
}
```

Another property we might be interested in is that, for any heap, we can find its minimal element if it is not empty:

```scala
forAll { (heap: List[Node]) =>
  if isEmpty(heap) then
    true
  else
    findMin(heap)
    true
}
```

This property succeeds as long as `findMin` effectively returns a value. If `findMin` throws an exception, the property will fail. You will learn more about exceptions later in the course.

Note that you can write properties that take several arbitrary parameters, like the following: 

```scala
forAll { (x1: Int, x2: Int) =>
  val heap = insert(x1, insert(x2, empty))
  // ...
}
```

Last, in this assignment properties are not defined by using the usual syntax of Scalacheck, but by defining a pair containing a label and the property implementation:

```scala
val someProperty: (String, Prop) =
  "blah blah" ->
  forAll { ... }

// Equivalent to the usual Scalacheck syntax:
//
// property("blah blah") = forAll { ... }
//
```

This difference is required by our grading infrastructure.

In **src/main/scala/quickcheck/HeapProperties.scala**, implement the properties that should be satisfied. Your properties should at least cover the following relevant facts:

- If you insert any two elements into an empty heap, finding the minimum of the resulting heap should get the smallest of the two elements back.
- If you insert an element into an empty heap, then delete the minimum, the resulting heap should be empty.
- Given a non-empty heap, inserting again its minimal element and then finding it should return the same minimal element.

- Given any heap, you should get a sorted sequence of elements when continually finding and deleting minima. (Hint: recursion and helper functions are your friends.)
- Given a low integer value and a high integer value, you should  be able to construct a heap containing two occurrences of the low   value, a heap containing two occurrences of the high value, and  a heap resulting from melding the first two heaps together. Then,  deleting the minimal element twice in a row from the melded heap  and finding the minimal element should return the high value.  Similarly, inserting the low value in the melded heap, and finding  the minimal element should return the low value.

- Given two arbitrary heaps, and the heap that results from melding them together, finding the minimum of the melded heap should return the minimum of one of the two source heaps. Then, continuously deleting that minimum element (from both, the melded heap and the source heap that contained it), should always give back a melded heap whose minimum element is the minimum element of the two source heaps, until the two source heaps are empty.

All tests should pass in order to get full credit.  That is you should correctly identify each buggy implementation while only writing properties that are true of heaps. Your properties should cover all of the above-stated relevant facts. You are free to write as many or as few properties as you want in order to achieve a full passing suite.

Note that this assignment asks you to write tests whose content captures all of the above relevant facts, and whose execution correctly differentiates correct from incorrect heaps among the heaps given to you. You need not worry about additional buggy heaps that someone else might write.