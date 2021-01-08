
;; to-list-tr-starter.rkt

;
; PROBLEM:
;
; Consider the following function that consumes Natural number n and produces a list of all
; the naturals of the form (list 1 2 ... n-1 n) not including 0.
;
; Use an accumulator to design a tail-recursive version of to-list.
;


;; Natural -> (listof Natural)
;; produce (cons n (cons n-1 ... empty)), not including 0
(check-expect (to-list 0) empty)
(check-expect (to-list 1) (list 1))
(check-expect (to-list 3) (list 1 2 3))

;(define (to-list n) empty) ;stub

(define (to-list n)
  (cond [(zero? n) empty]
        [else
         (append (to-list (sub1 n))
                 (list n))]))
