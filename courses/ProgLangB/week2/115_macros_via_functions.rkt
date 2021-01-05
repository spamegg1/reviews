; Programming Languages, Dan Grossman
; Section 6: Using Racket Functions to Add "Macros" to a Language

#lang racket

(provide (all-defined-out))

; we start with the same language and interpreter from 
; "What Your Interpreter Can and Cannot Assume"

; the new stuff are the "macros" as the bottom

; a larger language with two kinds of values, booleans and numbers
; an expression is any of these:
(struct const (int) #:transparent) ; int should hold a number
(struct negate (e1) #:transparent)  ; e1 should hold an expression
(struct add (e1 e2) #:transparent) ; e1, e2 should hold expressions
(struct multiply (e1 e2) #:transparent) ; e1, e2 should hold expressions
(struct bool (b) #:transparent) ; b should hold #t or #f
(struct eq-num (e1 e2) #:transparent) ; e1, e2 should hold expressions
(struct if-then-else (e1 e2 e3) #:transparent) ; e1, e2, e3 should hold expressions
; a value in this language is a legal const or bool

(define (eval-exp e)
  (cond [(const? e) 
         e] 
        [(negate? e) 
         (let ([v (eval-exp (negate-e1 e))])
           (if (const? v)
               (const (- (const-int v)))
               (error "negate applied to non-number")))]
        [(add? e) 
         (let ([v1 (eval-exp (add-e1 e))]
               [v2 (eval-exp (add-e2 e))])
           (if (and (const? v1) (const? v2))
               (const (+ (const-int v1) (const-int v2)))
               (error "add applied to non-number")))]
        [(multiply? e) 
         (let ([v1 (eval-exp (multiply-e1 e))]
               [v2 (eval-exp (multiply-e2 e))])
           (if (and (const? v1) (const? v2))
               (const (* (const-int v1) (const-int v2)))
               (error "multiply applied to non-number")))]
        [(bool? e) 
         e]
        [(eq-num? e) 
         (let ([v1 (eval-exp (eq-num-e1 e))]
               [v2 (eval-exp (eq-num-e2 e))])
           (if (and (const? v1) (const? v2))
               (bool (= (const-int v1) (const-int v2))) ; creates (bool #t) or (bool #f)
               (error "eq-num applied to non-number")))]
        [(if-then-else? e) 
         (let ([v-test (eval-exp (if-then-else-e1 e))])
           (if (bool? v-test)
               (if (bool-b v-test)
                   (eval-exp (if-then-else-e2 e))
                   (eval-exp (if-then-else-e3 e)))
               (error "if-then-else applied to non-boolean")))]
        [#t (error "eval-exp expected an exp")] ; not strictly necessary but helps debugging
        ))

; Here are two Racket functions that given language-being-implemented syntax,
; produce language-being-implemented syntax
(define (andalso e1 e2)
  (if-then-else e1 e2 (bool #f)))

(define (double e)
  (multiply e (const 2)))

; this one takes a Racket list of language-being-implemented syntax 
; and makes language-being-implemented syntax
(define (list-product es)
  (if (null? es)
      (const 1)
      (multiply (car es) (list-product (cdr es)))))

(define test (andalso (eq-num (double (const 4))
                              (list-product (list (const 2) (const 2) (const 1) (const 2))))
                      (bool #t)))

; notice we have not changed our interpreter at all
(define result (eval-exp test))
