case class A(n: Int)
case class B(n: Int)
case class C(n: Int)
case class D(n: Int)

given a: A = A(12)
// given aToB(using a: A): B = B(a.n * 2)
// given bToC(using b: B): C = C(b.n + 1)
// given cToD(using c: C): D = D(c.n * 3)

// Anonymous version
given (using a: A): B = B(a.n * 2)
given (using b: B): C = C(b.n + 1)
given (using c: C): D = D(c.n * 3)

summon[D]
