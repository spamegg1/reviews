; Programming Languages, Dan Grossman
; Section 5: Racket Definitions, Functions, Conditionals

; always make this the first (non-comment, non-blank) line of your file
#lang racket

; not needed here, but a workaround so we could write tests in a second file
; see getting-started-with-Racket instructions for more explanation
(provide (all-defined-out))

; basic definitions
(define x 3)
(define y (+ x 2)) ; function call is (e1 e2 ... en): parens matter!

; basic function
(define cube1 
  (lambda (x)
    (* x (* x x))))

; many functions, such as *, take a variable number of arguments
(define cube2
  (lambda (x)
    (* x x x)))

; syntactic sugar for function definitions
(define (cube3 x)
  (* x x x))

; conditional
(define (pow1 x y)
  (if (= y 0)
      1
      (* x (pow1 x (- y 1)))))

; currying
(define pow2 
  (lambda (x)
    (lambda (y)
      (pow1 x y))))

; sugar for currying (fairly new to Racket)
(define ((pow2b x) y) (pow1 x y))

(define three-to-the (pow2 3))
(define eightyone (three-to-the 4))
(define sixteen ((pow2 2) 4)) ; need exactly these parens
