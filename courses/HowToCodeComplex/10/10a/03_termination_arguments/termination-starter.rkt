
;; termination-starter.rkt

;
; .  ; The Collatz conjecture is a conjecture in mathematics named
; ; after Lothar Collatz, who first proposed it in 1937. ...
; ; The sequence of numbers involved is referred to as the hailstone
; ; sequence or hailstone numbers (because the values are usually
; ; subject to multiple descents and ascents like hailstones in a
; ; cloud).
; ;
; ; f(n) = /   n/2     if n is even
; ;        \   3n + 1  if n is odd
; ;
; ;
; ; The Collatz conjecture is: This process will eventually reach
; ; the number 1, regardless of which positive integer is chosen
; ; initially.
; ;
; ;
; ;
; ;
; ;
;
; [Image and part of text from: https://en.wikipedia.org/wiki/Collatz_conjecture]



;; Integer[>=1] -> (listof Integer[>=1])
;; produce hailstone sequence for n
(check-expect (hailstones 1) (list 1))
(check-expect (hailstones 2) (list 2 1))
(check-expect (hailstones 4) (list 4 2 1))
(check-expect (hailstones 5) (list 5 16 8 4 2 1))

(define (hailstones n)
  (if (= n 1)
      (list 1)
      (cons n
            (if (even? n)
                (hailstones (/ n 2))
                (hailstones (add1 (* n 3)))))))


;
; PROBLEM:
;
; The stri, scarpet and hailstones functions use generative recursion. So
; they are NOT based on a well-formed self-referential type comment. How do
; we know they are going terminate? That is, how do we know every recursion
; will definitely stop?
;
; Construct a three part termination argument for stri.
;
; Base case:
;
; Reduction step:
;
; Argument that repeated application of reduction step will eventually
; reach the base case:
;
;



;
;
; PROBLEM:
;
; Construct a three part termination argument for scarpet.
;
; Base case:
;
; Reduction step (next problem):
;
;
; Argument that repeated application of reduction step will eventually
; reach the base case:
;
;



;
;
; PROBLEM:
;
; Construct a three part termination argument for hailstones:
;
; Base case:
;
; Reduction step (next problem):
;
;
; Argument that repeated application of reduction step will eventually
; reach the base case:
;
;
