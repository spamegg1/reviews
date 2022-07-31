- The wording of the `careful_player` challenge problem requirements is a bit tricky. There are 4 requirements: 

1. If held-card value is more than 10 points behind the goal, you must draw. (It is guaranteed to be safe, make sure you understand why.) 
2. If your held-card value is 10 points or less behind the goal, then you have a choice on your algorithm as to whether you should draw or not. But you must ensure that you do not exceed the goal. So you can either say "No I won't draw" or you can say "let me sneak a peak into the next card, and if it is safe to draw it then I will (which is sort of cheating, but valid from the assignment's point of view)" or something in-between. These are all valid approaches. But you must make sure that the held-card value never exceeds the goal. And you also must make sure to follow 4 below. 
3. If your score is 0, you must not make any more moves. 
4. If your score is not 0, but you can reach a zero by discarding a card and then drawing a card, this must be done. In order to achieve this, your algorithm will need to look ahead and see what the next card is. 
5. Your code may choose to discard cards if it wants to, or do any other things it wants almost, as long as it satisfies conditions 1-4 above. So say your held-card value is 6 points behind the goal and the next card would be a 2. Then you have some leeway in what to do. You can choose to discard a card, you can choose to just stop, you can choose draw pretending you have looked ahead at that 2. (If the next card was a 5-6 though, note case 4 above.) 

- Watch out with nested case expressions -- you may need parentheses.  This does not arise too much because often nested patterns are a better choice, but nested case expressions do still make sense in some situations.  The problem arises in something like this:

```haskell
fun silly xs =
    case xs of
         [] => ""
       | x :: [] => case helper_function x of
                        NONE => ""
                      | SOME i => ""
       | x :: xs' => "" (* Error related to this line. It is a branch of the *inner* case *)
```

So you have an inner case expression, but then there is an extra clause at the end that corresponds to the outer case expression. SML reads that as: 

```haskell
fun silly xs =
    case xs of
         [] => ""
       | x :: [] => case helper_function x of
                        NONE => ""
                      | SOME i => ""
                      | x :: xs' => "" (* Error related to this line. It is a branch of the *inner* case *)
```

which is wrong (it's not what you intended and it doesn't type-check).  You need to wrap the inner case expression in parentheses:

```haskell
fun silly xs =
    case xs of
         [] => ""
       | x :: [] => (case helper_function x of
                        NONE => ""
                      | SOME i => "")
       | x :: xs' => "" 
```

