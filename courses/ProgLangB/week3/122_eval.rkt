; Programming Languages, Dan Grossman
; Section 6: Optional: eval and quote

#lang racket

(define (make-some-code1 y) ; just returns a list
  (if y
     (list 'begin (list 'print "hi") (list '+ 4 2))
     (list '+ 5 3)))

(define (make-some-code2 y) ; same as make-some-code1
  (if y
      (quote (begin (print "hi") (+ 4 2)))
      (quote (+ 5 3))))

(define (test1) (eval (make-some-code1 #t))) ; prints "hi", result 6 
(define (test2) (eval (make-some-code1 #t))) ; prints "hi", result 6 

