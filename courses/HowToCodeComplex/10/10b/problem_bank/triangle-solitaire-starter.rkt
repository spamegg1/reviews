;; triangle-solitaire-starter.rkt

;
; PROBLEM:
;
; The game of trianguar peg solitaire is described at a number of web sites,
; including http://www.mathsisfun.com/games/triangle-peg-solitaire/#.
;
; We would like you to design a program to solve triangular peg solitaire
; boards. Your program should include a function called solve that consumes
; a board and produces a solution for it, or false if the board is not
; solvable. Read the rest of this problem box VERY CAREFULLY, it contains
; both hints and additional constraints on your solution.
;
; The key elements of the game are:
;
;   - there is a BOARD with 15 cells, each of which can either
;     be empty or contain a peg (empty or full).
;
;   - a potential JUMP whenever there are 3 holes in a row
;
;   - a VALID JUMP  whenever from and over positions contain
;     a peg (are full) and the to position is empty
;
;   - the game starts with a board that has a single empty
;     position
;
;   - the game ends when there is only one peg left - a single
;     full cell
;
; Here is one sample sequence of play, in which the player miraculously does
; not make a single incorrect move. (A move they have to backtrack from.) No
; one is actually that lucky!
;
;
; .    .   .   .    .
; .    .   .   .   .
; .    .   .   .
