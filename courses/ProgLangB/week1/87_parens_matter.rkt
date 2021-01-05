; Programming Languages, Dan Grossman
; Section 5: Parentheses Matter! (Debugging Practice)

#lang racket

(provide (all-defined-out))

; [first big difference from ML (and Java)] PARENS MATTER!!

(define (fact n) (if (= n 0) 1 (* n (fact (- n 1)))))

; base case calls the function 1 with zero arguments
(define (fact-wrong1 n) (if (= n 0) (1) (* n (fact-wrong1 (- n 1)))))

; so why does this work (hint: it's not recursive
; and there is no type system):
(define (fact-works1b n) (if (= n 0) (1) (* n (fact (- n 1)))))

; passing 5 arguments to if: =, n, 0, 1, (* ...)
; this is bad syntax
;(define (fact-wrong2 n) (if = n 0 1 (* n (fact-wrong2 (- n 1)))))

; calling n with zero arguments and also having an if
; this is not a legal definition: bad syntax
;(define fact-wrong3 (n) (if (= n 0) 1 (* n (fact-wrong3 (- n 1)))))

; calling multiply with three arguments, which would be fine
; except the second one is fact-wrong4
(define (fact-wrong4 n) (if (= n 0) 1 (* n fact-wrong4 (- n 1))))

; calling fact-wrong5 with zero arguments, calling result of that
; with n-1
(define (fact-wrong5 n) (if (= n 0) 1 (* n ((fact-wrong5) (- n 1)))))

; treating n as a function of two arguments, passing it *

(define (fact-wrong6 n) (if (= n 0) 1 (n * (fact-wrong6 (- n 1)))))

