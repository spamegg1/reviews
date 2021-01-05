; Programming Languages, Dan Grossman
; Section 6: Datatype Programming in Racket Without Structs

#lang racket
(provide (all-defined-out))

; we do not need a datatype to create a list holding a mix of numbers and string
; Note: arguably bad style to not have else clause but makes example more like ML code
(define (funny-sum xs)
  (cond [(null? xs) 0]
        [(number? (car xs)) (+ (car xs) (funny-sum (cdr xs)))]
        [(string? (car xs)) (+ (string-length (car xs)) (funny-sum (cdr xs)))]))

; suppose we wanted a "one-of type" using nothing but lists and symbols and such, like ML's 
; datatype exp = Const of int | Negate of exp | Add of exp * exp | Multiply of exp * exp

; just helper functions that make lists where first element is a symbol
; Note: More robust could check at run-time the type of thing being put in
(define (Const i) (list 'Const i))
(define (Negate e) (list 'Negate e))
(define (Add e1 e2) (list 'Add e1 e2))
(define (Multiply e1 e2) (list 'Multiply e1 e2))

; just helper functions that test what "kind of exp"
; Note: More robust could raise better errors for non-exp values
(define (Const? x) (eq? (car x) 'Const))
(define (Negate? x) (eq? (car x) 'Negate))
(define (Add? x) (eq? (car x) 'Add))
(define (Multiply? x) (eq? (car x) 'Multiply))

; just helper functions that get the pieces for "one kind of exp"
; Note: More robust could check "what kind of exp"
(define (Const-int e) (car (cdr e)))
(define (Negate-e e) (car (cdr e)))
(define (Add-e1 e) (car (cdr e)))
(define (Add-e2 e) (car (cdr (cdr e))))
(define (Multiply-e1 e) (car (cdr e)))
(define (Multiply-e2 e) (car (cdr (cdr e))))

; fyi: there are built-in functions for getting 2nd, 3rd list elements that would
; have made the above simpler:
;(define Const-int cadr)
;(define Negate-e cadr)
;(define Add-e1 cadr)
;(define Add-e2 caddr)
;(define Multiply-e1 cadr)
;(define Multiply-e2 caddr)

; same recursive structure as we have in ML, just without pattern-matching
; And one change from what we did before: returning an exp, in particular
; a Constant, rather than an int
(define (eval-exp e)
  (cond [(Const? e) e] ; note returning an exp, not a number
        [(Negate? e) (Const (- (Const-int (eval-exp (Negate-e e)))))]
        [(Add? e) (let ([v1 (Const-int (eval-exp (Add-e1 e)))]
                        [v2 (Const-int (eval-exp (Add-e2 e)))])
                    (Const (+ v1 v2)))]
        [(Multiply? e) (let ([v1 (Const-int (eval-exp (Multiply-e1 e)))]
                             [v2 (Const-int (eval-exp (Multiply-e2 e)))])
                         (Const (* v1 v2)))]
        [#t (error "eval-exp expected an exp")]))

(define a-test (eval-exp (Multiply (Negate (Add (Const 2) (Const 2))) (Const 7))))