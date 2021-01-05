; Programming Languages, Dan Grossman
; Section 5: Mutation With set!

#lang racket

(provide (all-defined-out))

(define b 3) 
(define f (lambda (x) (* 1 (+ x b)))) 
(define c (+ b 4)) 
(set! b 5)
(define z (f 4))   
(define w c)       

; a safe version of f would make a local copy of b
; no need to call the local variable b also, but no reason not to either
(define g 
  (let ([b b])
    (lambda (x) (* 1 (+ x b)))))

; but maybe that is not good enough since + and * are also procedures
(define h 
  (let ([b b]
        [+ +]
        [* +])
    (lambda (x) (* 1 (+ x b)))))

; it turns out:
;  1. this technique doesn't work if f calls a function that itself calls
;     things that might get mutated
;  2. we don't have to worry about this in Racket because +, * are immutable:
;     the general rule is a top-level binding is mutable only if /that/ module
;     contains a set! of it