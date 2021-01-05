; Programming Languages, Dan Grossman
; Section 5: mcons

#lang racket

(provide (all-defined-out))

; cons cells are immutable -- this does not change a cell's contents
(define x (cons 14 null))
(define y x)
(set! x (cons 42 null))
(define fourteen (car y))

; but since mutable pairs are useful, Racket has them too:
;  mcons, mcar, mcdr, set-mcar!, set-mcdr!
(define mpr (mcons 1 (mcons #t "hi")))
(set-mcdr! (mcdr mpr) "bye")
(define bye (mcdr (mcdr mpr)))

; Note: run-time error to use mcar on a cons or car on an mcons