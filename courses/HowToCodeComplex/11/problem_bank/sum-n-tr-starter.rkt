
;; sum-n-tr-starter.rkt

;
; PROBLEM:
;
; Consider the following function that consumes Natural number n and produces the sum
; of all the naturals in [0, n].
;
; Use an accumulator to design a tail-recursive version of sum-n.
;


;; Natural -> Natural
;; produce sum of Natural[0, n]

(check-expect (sum-n 0) 0)
(check-expect (sum-n 1) 1)
(check-expect (sum-n 3) (+ 3 2 1 0))

;(define (sum-n n) 0) ;0

(define (sum-n n)
  (cond [(zero? n) 0]
        [else
         (+ n
            (sum-n (sub1 n)))]))
