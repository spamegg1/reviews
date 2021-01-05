; Programming Languages, Dan Grossman
; Section 5: Cond

#lang racket

(provide (all-defined-out))

; sum3 is equivalent to sum1 from previous segment but better style
(define (sum3 xs)
  (cond [(null? xs) 0]
        [(number? (car xs)) (+ (car xs)(sum3 (cdr xs)))]
        [else (+ (sum3 (car xs)) (sum3 (cdr xs)))]))

; sum4 is equivalent to sum2 from previous segment but better style
(define (sum4 xs)
  (cond [(null? xs) 0]
        [(number? xs) xs]
        [(list? (car xs)) (+ (sum4 (car xs)) (sum4 (cdr xs)))]
        [#t (sum4 (cdr xs))]))

; this function counts how many #f are in a (non-nested) list
; it uses the "controversial" idiom of anything not #f is true
(define (count-falses xs)
  (cond [(null? xs) 0]
        [(car xs) (count-falses (cdr xs))] ; (car xs) can have any type
        [#t (+ 1 (count-falses (cdr xs)))]))
