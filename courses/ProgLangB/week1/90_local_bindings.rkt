; Programming Languages, Dan Grossman
; Section 5: Local Bindings

#lang racket

(provide (all-defined-out))

; notice the parentheses in the let-expressions
(define (max-of-list xs)
  (cond [(null? xs) (error "max-of-list given empty list")]
        [(null? (cdr xs)) (car xs)]
        [#t (let ([tlans (max-of-list (cdr xs))])
              (if (> tlans (car xs))
                  tlans
                  (car xs)))]))

; 4 forms of local bindings

; let evaluates all expressions using outer environment, 
; *not* earlier bindings
(define (double1 x)
  (let ([x (+ x 3)]
        [y (+ x 2)])
    (+ x y -5)))

; let* is like ML's let: environment includes previous bindings
(define (double2 x)
  (let* ([x (+ x 3)]
         [y (+ x 2)])
    (+ x y -8)))
  
; letrec uses an environment where all bindings in scope
; * like ML's use of and for mutual recursion
; * you get an error if you use a variable before it's defined
;   where as always function bodies not used until called
;   (bindings still evaluated in order)
; (In versions of Racket before 6.1, you got an #<undefined> value instead,
;  which would typically cause an error as soon as you used it.)
(define (triple x)
  (letrec ([y (+ x 2)]
           [f (lambda (z) (+ z y w x))]
           [w (+ x 7)])
    (f -9)))

(define (mod2 x)
  (letrec 
      ([even?(lambda (x) (if (zero? x) #t (odd? (- x 1))))]
       [odd? (lambda (x) (if (zero? x) #f (even? (- x 1))))])
    (if (even? x) 0 1)))

(define (bad-letrec-example x)
  (letrec ([y z] ; okay to be a lambda that uses z, but here y undefined
           [z 13])
    (if x y z)))

; and you can use define locally (in some positions)
; the same as letrec when binding local variables
(define (mod2_b x)
  (define even? (lambda(x)(if (zero? x) #t (odd? (- x 1)))))
  (define odd?  (lambda(x)(if (zero? x) #f (even? (- x 1)))))
  (if (even? x) 0 1))

