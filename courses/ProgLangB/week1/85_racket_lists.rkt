; Programming Languages, Dan Grossman
; Section 5: Racket Lists

; always make this the first (non-comment, non-blank) line of your file
#lang racket

; not needed here, but a workaround so we could write tests in a second file
; see getting-started-with-Racket instructions for more explanation
(provide (all-defined-out))

; list processing: null, cons, null?, car, cdr
; we won't use pattern-matching in Racket
(define (sum xs)
  (if (empty? xs)
      0
      (+ (first xs) (sum (rest xs)))))

(define (my-append xs ys) ; same as append already provided
  (if (empty? xs)
      ys
      (cons (first xs) (my-append (rest xs) ys))))

(define (my-map f xs) ; same as map already provided
  (if (null? xs)
      null
      (cons (f (car xs)) (my-map f (cdr xs)))))

(define foo (my-map (lambda (x) (+ x 1)) (cons 3 (cons 4 (cons 5 null)))))

