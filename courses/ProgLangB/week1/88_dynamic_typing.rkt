; Programming Languages, Dan Grossman
; Section 5: Dynamic Typing

#lang racket

(provide (all-defined-out))

; [second big difference from ML (and Java)] Dynamic Typing!!

; dynamic typing: can use values of any type anywhere
;  e.g., a list that holds numbers or other lists

; this function sums lists of (numbers or lists of (numbers or ...)),
; but it does assume it only encounters lists or numbers (else run-time error)
(define (sum1 xs)
  (if (null? xs)
      0
      (if (number? (car xs))
          (+ (car xs) (sum1 (cdr xs)))
          (+ (sum1 (car xs)) (sum1 (cdr xs))))))

; this version does not fail on non-lists -- it treats them as 0
(define (sum2 xs)
  (if (null? xs)
      0
      (if (number? (car xs))
          (+ (car xs) (sum2 (cdr xs)))
          (if (list? (car xs))
              (+ (sum2 (car xs)) (sum2 (cdr xs)))
              (sum2 (cdr xs))))))
