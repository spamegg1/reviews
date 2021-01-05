; Programming Languages, Dan Grossman
; Section 5: Delayed Evaluation and Thunks

#lang racket

(provide (all-defined-out))

(define (factorial-normal x)
  (if (= x 0)
      1
      (* x (factorial-normal (- x 1)))))

(define (my-if-bad e1 e2 e3)
  (if e1 e2 e3))

(define (factorial-bad x)
  (my-if-bad (= x 0)
             1
             (* x 
                (factorial-bad (- x 1)))))

(define (my-if-strange-but-works e1 e2 e3)
  (if e1 (e2) (e3)))

(define (factorial-okay x)
  (my-if-strange-but-works (= x 0)
			   (lambda () 1)
			   (lambda () (* x (factorial-okay (- x 1))))))
