import scala.annotation.tailrec

object RecursionTask:
  enum Operation:
    case Plus
    case Mult

  enum Tree:
    case Node(val operation: Operation, val left: Tree, val right: Tree)
    case Leaf(val num: Int)

  import Tree.*
  import Operation.*

  def eval(ast: Tree): Int =
    /* TODO */

  def prefixPrinter(ast: Tree): String =
    /* TODO */

  @main
  def main(): Unit =
    /**
     *     *
     *    / \
     *   +  5
     *  / \
     * 1  3
     */
    val tree = Node(Mult, Node(Plus, Leaf(1), Leaf(3)), Leaf(5))
    println(eval(tree)) // 20
    println(prefixPrinter(tree)) // * + 1 3 5

