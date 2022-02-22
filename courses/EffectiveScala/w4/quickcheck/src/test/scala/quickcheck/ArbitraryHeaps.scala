package quickcheck

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen.{const, oneOf}

trait ArbitraryHeaps extends HeapProperties:

  import heapInterface.*

  // Generator of arbitrary heap values (used by Scalacheck)
  given generatedHeap: Gen[List[Node]] =
    oneOf(
      const(empty),
      for
        v <- arbitrary[Int]
        h <- oneOf(const(empty), generatedHeap)
      yield insert(v, h)
    )

end ArbitraryHeaps
