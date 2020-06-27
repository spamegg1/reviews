;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname local-expressions) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(local
  [(define a 1)
   (define b 2)]
  (+ a b))
(define p "incendio ")

(local [(define p "accio ")
        (define (fetch n) (string-append p n))]
  (fetch "portkey"))

(define c 1)
(define d 2)
(+ c
   (local [(define d 3)]
     (+ c d))
  d)

(define (foo x)
  (local [(define (bar y) (+ x y))]
    (+ x (bar (* 2 x)))))

(list (foo 2) (foo 3))

(list (local [(define (bar y) (+ 2 y))]
        (+ 2 (bar (* 2 2))))
      (foo 3))

(define (bar_0 y) (+ 2 y))
(list (+ 2 (bar_0 (* 2 2)))
      (foo 3))

(define (bar_0 y) (+ 2 y))
(list (+ 2 (bar_0 4))
      (foo 3))

(define (bar_0 y) (+ 2 y))
(list (+ 2 (+ 2 4))
      (foo 3))

(define (bar_0 y) (+ 2 y))
(list (+ 2 6)
      (foo 3))

(define (bar_0 y) (+ 2 y))
(list 8 (foo 3))

(define (bar_0 y) (+ 2 y))
(list 8 (local [(define (bar y) (+ 3 y))]
          (+ 3 (bar (* 2 3)))))

(define (bar_0 y) (+ 2 y))
(define (bar_1 y) (+ 3 y))
(list 8 (+ 3 (bar_1 (* 2 3))))

(define (bar_0 y) (+ 2 y))
(define (bar_1 y) (+ 3 y))
(list 8 (+ 3 (bar_1 6)))

(define (bar_0 y) (+ 2 y))
(define (bar_1 y) (+ 3 y))
(list 8 (+ 3 (+ 3 6)))

(define (bar_0 y) (+ 2 y))
(define (bar_1 y) (+ 3 y))
(list 8 (+ 3 9))

(define (bar_0 y) (+ 2 y))
(define (bar_1 y) (+ 3 y))
(list 8 12)











