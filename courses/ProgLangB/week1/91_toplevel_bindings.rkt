; Programming Languages, Dan Grossman
; Section 5: Top-Level Bindings

#lang racket

(provide (all-defined-out))

; at the top-level (*)
; same letrec-like rules: can have forward references, but
;  definitions still evaluate in order and cannot be repeated

(define (f x) (+ x (* x b))) ; forward reference okay here
(define b 3)
(define c (+ b 4)) ; backward reference okay
;(define d (+ e 4)) ; not okay (get an error)
(define e 5)
;(define f 17) ; not okay: f already defined in this module

; (*) we are not actually at top-level -- we are in a module called 91_toplevel_bindings