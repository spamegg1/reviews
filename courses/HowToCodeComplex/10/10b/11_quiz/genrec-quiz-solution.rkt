(require 2htdp/image)

;  PROBLEM 1:
;
;  In the lecture videos we designed a function to make a Sierpinski triangle fractal.
;
;  Here is another geometric fractal that is made of circles rather than triangles:
;
;  .
;
;  Design a function to create this circle fractal of size n and colour c.
;

(define CUT-OFF 5)
;; Natural String -> Image
;; produce a circle fractal of size n and colour c
(define (circle-fractal n c)
  (if (<= n CUT-OFF)
      (circle n "outline" c)
      (local [(define sub (circle-fractal (/ n 2) c))]
        (overlay (circle n "outline" c)
                 (beside sub sub)))))
;  PROBLEM 2:
;
;  Below you will find some data definitions for a tic-tac-toe solver.
;
;  In this problem we want you to design a function that produces all
;  possible filled boards that are reachable from the current board.
;
;  In actual tic-tac-toe, O and X alternate playing. For this problem
;  you can disregard that. You can also assume that the players keep
;  placing Xs and Os after someone has won. This means that boards that
;  are completely filled with X, for example, are valid.
;
;  Note: As we are looking for all possible boards, rather than a winning
;  board, your function will look slightly different than the solve function
;  you saw for Sudoku in the videos, or the one for tic-tac-toe in the
;  lecture questions.
;


;; Value is one of:
;; - false
;; - "X"
;; - "O"
;; interp. a square is either empty (represented by false) or has and "X" or an "O"

(define (fn-for-value v)
  (cond [(false? v) (...)]
        [(string=? v "X") (...)]
        [(string=? v "O") (...)]))

;; Board is (listof Value)
;; a board is a list of 9 Values
(define B0 (list false false false
                 false false false
                 false false false))

(define B1 (list false "X"   "O"   ; a partly finished board
                 "O"   "X"   "O"
                 false false "X"))

(define B2 (list "X"  "X"  "O"     ; a board where X will win
                 "O"  "X"  "O"
                 "X" false "X"))

(define B3 (list "X" "O" "X"       ; a board where Y will win
                 "O" "O" false
                 "X" "X" false))

(define (fn-for-board b)
  (cond [(empty? b) (...)]
        [else
         (... (fn-for-value (first b))
              (fn-for-board (rest b)))]))


;; Board -> (listof Board)
;; produce list of all possible filled boards reachable from given board
(check-expect (all-boards B2)
              (list (list "X"  "X"  "O"
                          "O"  "X"  "O"
                          "X"  "X"  "X")
                    (list "X"  "X"  "O"
                          "O"  "X"  "O"
                          "X"  "O"  "X")))
(check-expect (all-boards B3)
              (list (list "X" "O" "X"
                          "O" "O" "X"
                          "X" "X" "X")
                    (list "X" "O" "X"
                          "O" "O" "X"
                          "X" "X" "O")
                    (list "X" "O" "X"
                          "O" "O" "O"
                          "X" "X" "X")
                    (list "X" "O" "X"
                          "O" "O" "O"
                          "X" "X" "O")))

(define (all-boards b)
  (cond [(empty? b) (list empty)]
        [else
         (local [(define try
                   (all-boards (rest b)))]
           (if (false? (first b))
               (append
                (map (λ (bd) (cons "X" bd)) try)
                (map (λ (bd) (cons "O" bd)) try))
               (map (λ (bd) (cons (first b) bd)) try)))]))

;  PROBLEM 3:
;
;  Now adapt your solution to filter out the boards that are impossible if
;  X and O are alternating turns. You can continue to assume that they keep
;  filling the board after someone has won though.
;
;  You can assume X plays first, so all valid boards will have 5 Xs and 4 Os.
;
;  NOTE: make sure you keep a copy of your solution from problem 2 to answer
;  the questions on edX.
;

(check-expect (filter-boards (all-boards B2))
              (list (list "X"  "X"  "O"
                          "O"  "X"  "O"
                          "X"  "O"  "X")))
(check-expect (filter-boards (all-boards B3))
              (list (list "X" "O" "X"
                          "O" "O" "X"
                          "X" "X" "O")
                    (list "X" "O" "X"
                          "O" "O" "O"
                          "X" "X" "X")))
(define (filter-boards lob)
  (filter valid-board? lob))

(define (valid-board? b)
  (and (= 5 (count b "X")) (= 4 (count b "O"))))

(define (count b str)
  (cond [(empty? b) 0]
        [else
         (local [(define try (count (rest b) str))]
           (if (string=? str (first b))
             (+ 1 try)
             try))]))

(define (listsize lst)
  (cond [(empty? lst) 0]
        [else (+ 1 (listsize (rest lst)))]))
