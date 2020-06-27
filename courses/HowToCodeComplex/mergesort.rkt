;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname mergesort) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; template according to generative recursion
#;
(define (genrec lon)
 (cond [(trivial? lon) (trivial-solution lon)]
       [else
        (... lon
        (genrec (next-problem lon)))]))


;; (listof Number) -> (listof Number)
;; produces sorted list of numbers in ascending order
(check-expect (merge-sort empty) empty)
(check-expect (merge-sort (list 2)) (list 2))
(check-expect (merge-sort (list 1 2)) (list 1 2))
(check-expect (merge-sort (list 2 1)) (list 1 2))
(check-expect (merge-sort (list 1 1)) (list 1 1))
(check-expect (merge-sort (list 1 2 3)) (list 1 2 3))
(check-expect (merge-sort (list 3 2 1)) (list 1 2 3))
(check-expect (merge-sort (list 2 1 3)) (list 1 2 3))
(check-expect (merge-sort (list 1 2 2)) (list 1 2 2))
(check-expect (merge-sort (list 2 2 1)) (list 1 2 2))
(check-expect (merge-sort (list 2 1 2)) (list 1 2 2))
(check-expect (merge-sort (list 3 3 3)) (list 3 3 3))
(check-expect (merge-sort (list 1 2 2)) (list 1 2 2))
(check-expect (merge-sort (list 1 2 2)) (list 1 2 2))
(check-expect (merge-sort (list 6 5 3 1 8 7 2 4)) (list 1 2 3 4 5 6 7 8))
(check-expect (merge-sort (list 8 7 6 5 4 3 2 1)) (list 1 2 3 4 5 6 7 8))
(check-expect (merge-sort (build-list 100 identity))(build-list 100 identity))

;(define (merge-sort lon) lon) ;stub

(define (merge-sort lon)
  (cond [(empty? lon) lon]
        [(empty? (rest lon)) lon]
        [else
         (merge (merge-sort (take lon (quotient (length lon) 2)))
                (merge-sort (drop lon (quotient (length lon) 2))))]))


;; (listof Number) (listof Number) -> (listof Number)
;; merges two already sorted lists of numbers into one sorted list in ascending order
;; ASSUME: both given lists are sorted in ascending order
(check-expect (merge empty empty) empty)
(check-expect (merge (list 1) empty) (list 1))
(check-expect (merge empty (list 2)) (list 2))
(check-expect (merge (list 2) (list 1)) (list 1 2))
(check-expect (merge (list 2) (list 1 3)) (list 1 2 3))
(check-expect (merge (list 2 4) (list 3)) (list 2 3 4))
(check-expect (merge (list 2 4) (list 1 3)) (list 1 2 3 4))
(check-expect (merge (list 1 4 5 8 11 12) (list 2 3 6 7 9 10))
              (list 1 2 3 4 5 6 7 8 9 10 11 12))
(check-expect (merge (list 1 4 5 7 11) (list 2 3 6 8 9 10))
              (list 1 2 3 4 5 6 7 8 9 10 11))

;(define (merge lon1 lon2) empty);stub

(define (merge lon1 lon2)
  (cond [(empty? lon1) lon2]
        [(empty? lon2) lon1]
        [else         
         (if (<= (first lon1) (first lon2))
             (cons (first lon1)
                   (merge (rest lon1) lon2))
             (cons (first lon2)
                   (merge (rest lon2) lon1)))]))


;; (listof X) Natural -> (listof X)
;; produces first n elements of given list
;; ASSUME: list has at least n elements
(check-expect (take empty 0) empty)
(check-expect (take (list 1) 0) empty)
(check-expect (take (list 3 2) 1) (list 3))
(check-expect (take (list 5 2 3) 1) (list 5))
(check-expect (take (list 5 9 7 2) 2) (list 5 9))
(check-expect (take (list 5 9 7 2 3) 2) (list 5 9))
(check-expect (take (list 5 9 7 2 3 6) 1) (list 5 ))
(check-expect (take (list 5 9 7 2 3 6) 2) (list 5 9))
(check-expect (take (list 5 9 7 2 3 6) 3) (list 5 9 7))
(check-expect (take (list 5 9 7 2 3 6) 4) (list 5 9 7 2))
(check-expect (take (list 5 9 7 2 3 6) 5) (list 5 9 7 2 3))
;(define (take lst n) lst);stub
(define (take lst n)
  (cond [(zero? n) empty]
        [else
         (cons (first lst) (take (rest lst) (sub1 n)))]))


;; (listof X) Natural -> (listof X)
;; drops first n elements of given list
;; ASSUME: list has at most n elements
(check-expect (drop empty 0) empty)
(check-expect (drop (list 1) 0) (list 1))
(check-expect (drop (list 3 2) 1) (list 2))
(check-expect (drop (list 5 2 3) 2) (list 3))
(check-expect (drop (list 5 9 7 2) 2) (list 7 2))
(check-expect (drop (list 5 9 7 2 3) 3) (list 2 3))
(check-expect (drop (list 5 9 7 2 3 6) 1) (list 9 7 2 3 6))
(check-expect (drop (list 5 9 7 2 3 6) 2) (list 7 2 3 6))
(check-expect (drop (list 5 9 7 2 3 6) 3) (list 2 3 6))
(check-expect (drop (list 5 9 7 2 3 6) 4) (list 3 6))
(check-expect (drop (list 5 9 7 2 3 6) 5) (list 6))
(check-expect (drop (list 5 9 7 2 3 6) 6) empty)
;(define (drop lst n) lst);stub

(define (drop lst n)
  (cond [(zero? n) lst]
        [else
         (drop (rest lst) (sub1 n))]))