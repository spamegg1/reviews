; Programming Languages, Dan Grossman
; Section 5: The Truth About Cons

#lang racket

(provide (all-defined-out))

(define pr (cons 1 (cons #t "hi"))) 
(define lst (cons 1 (cons #t (cons "hi" null))))
(define hi (cdr (cdr pr)))
(define hi-again (car (cdr (cdr lst))))
(define hi-again-shorter (caddr lst))
(define no (list? pr))
(define yes (pair? pr))
(define of-course (and (list? lst) (pair? lst)))
; (define do-not-do-this (length pr))

