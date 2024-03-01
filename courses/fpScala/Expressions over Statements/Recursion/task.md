## Recursion 

*To understand recursion, one must first understand recursion* 

This topic should be familiar to anyone who delved into functional programming, but we would like to review it once again. 
The essence of recursion is that a function calls itself. 
At first encounter, this approach may seem unconventional, but with more practice, you'll find it increasingly 
natural. 
Consider the problem of finding a key in a box. But, the box may contain other boxes, which may also contain further boxes, and 
so forth, and the key is located in one of these boxes, but you have no idea which one. 
To solve such a problem without recursion, you would generally use a loop: 

```scala 3
// We model the contents of a box as a set of Items, which can contain other boxes or keys 
sealed trait Item
case class Box(content: Set[Item]) extends Item
case class Key(id: String) extends Item

def lookForKey(box: Box): Option[Key] =
  // create a mutable variable for a pile of items to look through
  var pile = box.content
  while pile.nonEmpty do
    // pick one item from the pile
    val item = pile.head
    item match
      case key: Key => 
        // found the key
        return Some(key)
      case box: Box => 
        // remove the current item from the pile and add its contents for further inspection
        pile = pile - box ++ box.content
  // no key was found
  None 

@main
def main() =
  val box = Box(Set(Box(Set(Box(Set.empty), Box(Set.empty))), Box(Set(Key(), Box(Set.empty)))))
  println(lookForKey(box))
```

This solution is valid, but it feels cumbersome. 
We need to create a mutable variable to hold the pile of items inside the box. 
Additionally, we must remember to remove the box under inspection before adding its contents.
We also have to remember to return `None` at the end, after the `while` loop: luckily, the compiler will 
complain if we forget this. 
In general, it is just too easy to make a mistake when writing this function.

Alternatively, we can consider what it means to look for a key in a box filled with other boxes and keys. 
We look inside the box, pick one of the items inside, and if it is a box, we just need to proceed and look for a key 
within it — just as we are currently doing. 
It is precisely the situation where a function calls itself. 
Compare the recursive implementation to the non-recursive one: 

```scala 3
def lookForKey(box: Box): Option[Key] =
  def go(item: Any): Option[Key] =
    item match
      case key: Key => Some(key)
      case box: Box =>
        // process every item in the box recursively and pick the first key in the result, if it exists
        box.content.flatMap(go).headOption
  go(box)
```

In this case, we simply go through evey item in the box, apply the recursive function `go` to it, and then select the first key 
in the resulting collection. 
Notice that we no longer have any mutable variables, our program is much more readable, and there are fewer ways it can go
wrong. 
One may protest that this code is not optimal because `flatMap` will go through the entire box even if we find the key
early, and that is a valid concern. 
There are many ways to deal with this kind of inefficiency. 
One method would be to use lazy collections or views, and we'll explore another in one of the following modules when we'll
discuss early returns. 

If you find it hard to think recursively, consider the following two-step approach. 
1. If the given instance of the problem can be solved directly, solve it directly. 
2. Othewise, reduce it to one or more *simpler instances of the same problem*. 

In our example with boxes, if the item at hands is a key, then we've solved the problem and all we need to do is  
return the key. 
This is the base case of our problem, the smallest instance of it. 
Otherwise, we have *smaller* instances of the same problem: other boxes in which we can try to look for a key.
Once we've looked through the boxes recursively, we can be sure that if a key exists, it has been found.
The only thing left after that is some kind of post-processing of the recursive calls' results — in our case,
getting the first found key with `headOption`. 

Recursion shines when dealing with recursive data structures, which are very common in functional programming. 
Lists, trees, and other algebraic data structures are areas where recursion applies very naturally. 
Consider the recursive function that computes the sum of the values in binary tree nodes: 

```scala 3
enum Tree:
  case Node(val payload: Int, val left: Tree, val right: Tree)
  case Leaf(val payload: Int)

import Tree.*

def sumTree(tree: Tree): Int =
  tree match
    case Leaf(payload) => payload
    case Node(payload, left, right) =>
      payload + sumTree(left) + sumTree(right)

@main
def main() =
  /**
   *     4
   *    / \
   *   2  5
   *  / \
   * 1  3
   */
  val tree = Node(4, Node(2, Leaf(1), Leaf(3)), Leaf(5))
  println(sumTree(tree))
```

You may notice that we call `sumTree` recursively every time there is a `Tree` within a `Tree` node.
The recursion in the data type points us to a *smaller* instance of the problem to solve.
We then sum the values returned from the recursive calls, producing the final result. 
There are no `Tree`s in a `Leaf`, therefore, we know it is the base case and the problem can be solved right away.
 
### Exercise 

Implement a tiny calculator `eval` and a printer `prefixPrinter` for arithmetic expressions. 
An expression is presented as its abstract syntax tree, where leaves contain numbers, while nodes correspond to the 
binary operators applied to subexpressions. 
For example, the tree `Node(Mult, Node(Plus, Leaf(1), Leaf(3)), Leaf(5))` corresponds to the expression `(1+3)*5`. 
The printer should convert an expression into the prefix form, in which the operator comes first, followed by the left 
operand, then the right operand. 
Our tree should be printed as `* + 1 3 5`. 
