; Programming Languages, Dan Grossman
; Section 5: Avoiding Unnecessary Computations: Delay and Force

; [this is the code for two segments related to thunking and delay/force] 

#lang racket

(provide (all-defined-out))

; this is a silly addition function that purposely runs slows for 
; demonstration purposes
(define (slow-add x y)
  (letrec ([slow-id (lambda (y z)
                      (if (= 0 z)
                          y
                          (slow-id y (- z 1))))])
    (+ (slow-id x 50000000) y)))

; multiplies x and result of y-thunk, calling y-thunk x times
(define (my-mult x y-thunk) ;; assumes x is >= 0
  (cond [(= x 0) 0]
        [(= x 1) (y-thunk)]
        [#t (+ (y-thunk) (my-mult (- x 1) y-thunk))]))
        
;(my-mult 0 (lambda () (slow-add 3 4)))
;(my-mult 1 (lambda () (slow-add 3 4)))
;(my-mult 2 (lambda () (slow-add 3 4)))
;(my-mult 0 (let ([x (slow-add 3 4)]) (lambda () x)))
;(my-mult 1 (let ([x (slow-add 3 4)]) (lambda () x)))
;(my-mult 2 (let ([x (slow-add 3 4)]) (lambda () x)))

(define (my-delay th) 
  (mcons #f th)) ;; a one-of "type" we will update /in place/

(define (my-force p)
  (if (mcar p)
      (mcdr p)
      (begin (set-mcar! p #t)
             (set-mcdr! p ((mcdr p)))
             (mcdr p))))

;(my-mult 0 (let ([x (my-delay (lambda () (slow-add 3 4)))]) (lambda () (my-force x))))
;(my-mult 1 (let ([x (my-delay (lambda () (slow-add 3 4)))]) (lambda () (my-force x))))
;(my-mult 2 (let ([x (my-delay (lambda () (slow-add 3 4)))]) (lambda () (my-force x))))
