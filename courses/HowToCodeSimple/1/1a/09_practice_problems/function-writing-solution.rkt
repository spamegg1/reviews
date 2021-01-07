;; function-writing-solution.rkt

;
; PROBLEM:
;
; Write a function that consumes two numbers and produces the larger of the two.
;

(define (larger x y)
  (if (> x y)
      x
      y))

