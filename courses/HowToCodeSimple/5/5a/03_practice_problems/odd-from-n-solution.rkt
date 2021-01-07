
;; odd-from-n-solution.rkt

;  PROBLEM:
;
;  Design a function called odd-from-n that consumes a natural number n, and produces a list of all
;  the odd numbers from n down to 1.
;
;  Note that there is a primitive function, odd?, that produces true if a natural number is odd.
;


;; Natural -> ListOfNatural
;; produce a list of the odd numbers between n and 1
(check-expect (odd-from-n 0) empty)
(check-expect (odd-from-n 7) (list 7 5 3 1))
(check-expect (odd-from-n 8) (list 7 5 3 1))

;(define (odd-from-n n) empty)

(define (odd-from-n n)
  (cond [(zero? n) empty]
        [else
         (if (odd? n)
             (cons n (odd-from-n (sub1 n)))
             (odd-from-n (sub1 n)))]))
