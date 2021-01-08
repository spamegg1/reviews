
;; sum-odds-tr-starter.rkt

;
; PROBLEM:
;
; Consider the following function that consumes a list of numbers and produces the sum of
; all the odd numbers in the list.
;
; Use an accumulator to design a tail-recursive version of sum-odds.
;


;; (listof Number) -> Number
;; produce sum of all odd numbers of lon
(check-expect (sum-odds empty) 0)
(check-expect (sum-odds (list 1 2 5 6 11)) 17)

(define (sum-odds lon)
  (cond [(empty? lon) 0]
        [else
         (if (odd? (first lon))
             (+ (first lon)
                (sum-odds (rest lon)))
             (sum-odds (rest lon)))]))
