
;
; In this problem set you will design a program to check whether a given simple maze is
; solvable.  Note that you are operating on VERY SIMPLE mazes, specifically:
;
;    - all of your mazes will be square
;    - the maze always starts in the upper left corner and ends in the lower right corner
;    - at each move, you can only move down or right
;
; Design a representation for mazes, and then design a function that consumes a maze and
; produces true if the maze is solvable, false otherwise.
;
; Solvable means that it is possible to start at the upper left, and make it all the way to
; the lower right.  Your final path can only move down or right one square at a time. BUT, it
; is permissible to backtrack if you reach a dead end.
;
; For example, the first three mazes below are solvable.  Note that the fourth is not solvable
; because it would require moving left. In this solver you only need to support moving down
; and right! Moving in all four directions introduces complications we are not yet ready for.
;
;     .    .    .    .
;
;
; Your function will of course have a number of helpers. Use everything you have learned so far
; this term to design this program.
;
; One big hint. Remember that we avoid using an image based representation of information unless
; we have to. So the above are RENDERINGs of mazes. You should design a data definition that
; represents such mazes, but don't use images as your representation.
;
; For extra fun, once you are done, design a function that consumes a maze and produces a
; rendering of it, similar to the above images.
;



;; Solve simple square mazes

;; Constants:


;; Data definitions:


;; Functions:
