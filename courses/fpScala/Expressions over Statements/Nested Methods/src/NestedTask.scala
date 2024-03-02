object NestedTask:
  object Task1:
    def f(): Unit =
      def g(): Unit =
        h()
      def i(): Unit =
        h()
      def h(): Unit = () // un-indented this one

  object Task2:
    def f(x: Int): Unit =
      h()
      g(42)
      def g(y: Int): Unit =
        def h(z: Int): Unit = ()
        h(x+y)
    def h(): Unit = // un-indented this!
      g()
    def g(): Unit = ()

  def f(x: Int, y: Int): Int =
    def g(z: Int): Int =
      def h(): Int =
        x + y + z
      h()
//  def i(): Int = z // z is not visible outside g
    g(42)
