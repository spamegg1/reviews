
;; sum-tr-starter.rkt

;
; PROBLEM:
;
; (A) Consider the following function that consumes a list of numbers and produces
;     the sum of all the numbers in the list. Use the stepper to analyze the behavior
;     of this function as the list gets larger and larger.
;
; (B) Use an accumulator to design a tail-recursive version of sum.
;


;; (listof Number) -> Number
;; produce sum of all elements of lon
(check-expect (sum empty) 0)
(check-expect (sum (list 2 4 5)) 11)

(define (sum lon)
  (cond [(empty? lon) 0]
        [else
         (+ (first lon)
            (sum (rest lon)))]))
