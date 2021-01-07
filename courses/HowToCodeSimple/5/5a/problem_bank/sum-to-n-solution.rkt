;; sum-to-n-solution.rkt

;   PROBLEM:
;
;   Design a function that produces the sum of all the naturals from 0 to a given n.
;


;; Natrual -> Natural
;; produce the sum of all the naturals from 0 to n
(check-expect (sum-to-n 0) 0)
(check-expect (sum-to-n 1) 1)
(check-expect (sum-to-n 4) 10)

;(define (sum-to-n n) 0)   ;stub

(define (sum-to-n n)
  (cond [(zero? n) 0]
        [else
         (+ n
            (sum-to-n (sub1 n)))]))
