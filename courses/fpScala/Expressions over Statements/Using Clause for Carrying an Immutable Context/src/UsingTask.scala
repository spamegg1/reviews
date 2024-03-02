object UsingTask:
  trait Comparator[A]:
    def compare(x: A, y: A): Int

  object Comparator:
    given Comparator[Int] with
      def compare(x: Int, y: Int): Int =
        x - y
    given Comparator[String] with
      def compare(x: String, y: String): Int =
        x.compareTo(y)
  end Comparator

  object StringLengthComparator extends Comparator[String]:
    def compare(x: String, y: String): Int =
      if x.length != y.length then x.length - y.length
      else x.compareTo(y)

  def sort[A](xs: Array[A])(using comparator: Comparator[A]): Array[A] =
    val n = xs.length
    var swapped: Boolean = true

    while swapped do
      swapped = false
      for i <- 0 until n - 1 do
        if comparator.compare(xs(i), xs(i + 1)) > 0 then
          val temp = xs(i)
          xs(i) = xs(i + 1)
          xs(i + 1) = temp
          swapped = true
    xs

  @main
  def main() =
    val array = Array(3,2,1)
    println(s"${sort(array).mkString(",")}")

    val stringArray = Array("ca", "acc", "cc", "c", "b", "ac", "bb", "ab", "aac", "bab", "aab", "bba", "bc", "a", "aa", "abc", "cab", "ba", "cb", "aba")
    println(s"${sort(stringArray).mkString("\", \"")}")
    println(s"${sort(stringArray)(using StringLengthComparator).mkString("\", \"")}")
