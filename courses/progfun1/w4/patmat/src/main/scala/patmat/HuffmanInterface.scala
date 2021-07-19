package patmat

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait HuffmanInterface:
  def weight(tree: CodeTree): Int
  def chars(tree: CodeTree): List[Char]
  def times(chars: List[Char]): List[(Char, Int)]
  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf]
  def singleton(trees: List[CodeTree]): Boolean
  def combine(trees: List[CodeTree]): List[CodeTree]
  def until(done: List[CodeTree] => Boolean, merge: List[CodeTree] => List[CodeTree])(trees: List[CodeTree]): List[CodeTree]
  def createCodeTree(chars: List[Char]): CodeTree
  def decode(tree: CodeTree, bits: List[Int]): List[Char]
  def decodedSecret: List[Char]
  def encode(tree: CodeTree)(text: List[Char]): List[Int]
  def convert(tree: CodeTree): List[(Char, List[Int])]
  def quickEncode(tree: CodeTree)(text: List[Char]): List[Int]
  def frenchCode: CodeTree
  def secret: List[Int]
