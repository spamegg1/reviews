object EnumTask:
  enum Tree[+A]:
    case Branch(left: Tree[A], value: A, right: Tree[A])
    case Leaf(value: A)
    case Stump

  import Tree.*

  // Exercise: implement a function which checks if the tree is balanced.
  // A balanced binary tree meets the following conditions:
  // * The absolute difference of heights of left and right subtrees at any node is no more than 1.
  // * For each node its left subtree is a balanced binary tree
  // * For each node its right subtree is a balanced binary tree
  def isTreeBalanced[A](tree: Tree[A]): Boolean =
    /* Put your implementation here */

  /*
      3
     / \
    2   5
   /   / \
  1   4   6
  */
  val balancedTree: Tree[Int] =
    Branch(
      Branch(Leaf(1), 2, Stump),
      3,
      Branch(Leaf(4), 5, Leaf(6)))

  /*
      3
     / \
    2   5
   /|  | \
  1 7  4  6
  */
  val fullTree: Tree[Int] =
    Branch(
      Branch(Leaf(1), 2, Leaf(7)),
      3,
      Branch(Leaf(4), 5, Leaf(6)))

  /*
      3
       \
        5
       / \
      4   6
  */
  val unbalancedTree: Tree[Int] =
    Branch(
      Stump,
      3,
      Branch(Leaf(4), 5, Leaf(6)))

  /*
      3
     /
    2
   /
  1
  */
  val degenerateTree: Tree[Int] =
    Branch(
      Branch(Leaf(1), 2, Stump),
      3,
      Stump)

  @main
  def main() =
    println(isTreeBalanced(unbalancedTree))
    println(isTreeBalanced(balancedTree))
    println(isTreeBalanced(fullTree))
    println(isTreeBalanced(degenerateTree))



