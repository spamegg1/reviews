class Polynom(nonZeroTerms: Map[Int, Double]):
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = nonZeroTerms.withDefaultValue(0.0)

  def +(other: Polynom): Polynom =
    //Polynom(terms ++ other.terms.map((exp, coeff) => (exp, coeff + terms(exp))))
    Polynom(other.terms.foldLeft(terms)(addTerms))

  def addTerms(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] =
    val (exp, coeff) = term
    terms + (exp -> (terms(exp) + coeff))

  override def toString: String =
    val termStrings =
      for
        (exp, coeff) <- terms.toList.sorted.reverse
      yield
        val exponent = if exp == 0 then "" else s"x^$exp"
        s"$coeff$exponent"
    if terms.isEmpty then "0.0"
    else termStrings.mkString(" + ").replace("+ -", "- ")

val x = Polynom(0 -> 2.0, 1 -> -3.0, 2 -> 1.0)
val z = Polynom()
val y = Polynom(3 -> 4.0)
x + x
x + y
