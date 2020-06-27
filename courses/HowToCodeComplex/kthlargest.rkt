;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname kthlargest) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; (listof Number) -> Number
;; produce largest number in list
;; ASSUME: list is not empty
(check-expect (find-largest (list 5 -2 6 -3 10)) 10)
(check-expect (find-largest (list 5)) 5)
(check-expect (find-largest (list 1 2 3 4 5 6)) 6)
(check-expect (find-largest (list 1 2 2 2 2 2)) 2)

(define (find-largest lon)
  ;; max is Number; result so far accumulator, largest number found so far
  (local [(define (fn-for-lon lon max)
            (cond [(empty? lon) max]
                  [else
                   (if (< max (first lon))
                       (fn-for-lon (rest lon) (first lon))
                       (fn-for-lon (rest lon) max))]))]
    (fn-for-lon lon (first lon))))


;; (listof Number) -> (listof Number)
;; produces sorted list in descending order
(check-expect (sorted (list 1 2 2 3 3 4 5 6 6))
              (list 6 6 5 4 3 3 2 2 1))
(check-expect (sorted (list 1 2 2 3 3 4 2 6 6))
              (list 6 6 4 3 3 2 2 2 1))
(check-expect (sorted (list 6 6 4 3 3 2 2 2 1))
              (list 6 6 4 3 3 2 2 2 1))
(define (sorted lon)
  (local [(define (fn-for-lon lon top bottom)
            (cond [(empty? lon) (cons (first top) bottom)]
                  [(empty? top)
                   (fn-for-lon (rest lon)
                               (cons (first lon) top)
                               bottom)]
                  [else
                   (if (<= (first top) (first lon))
                       (fn-for-lon (rest lon)
                                   (list (first lon))
                                   (cons (first top) bottom))
                       (fn-for-lon (rest lon)
                                   top
                                   (fn-for-lon (cons (first lon) bottom)
                                               empty
                                               empty)))]))]
  (fn-for-lon lon empty empty)))


;; (listof Number) Natural -> Number
;; produce kth largest number in list
;; ASSUME: 1 <= k <= length of list, and list is not empty
(check-expect (find-k-largest (list 5 -2 6 -3 10) 1) 10)
(check-expect (find-k-largest (list 5 -2 6 -3 10) 2) 6)
(check-expect (find-k-largest (list 5 -2 6 -3 10) 3) 5)
(check-expect (find-k-largest (list 5 -2 6 -3 10) 4) -2)
(check-expect (find-k-largest (list 5 -2 6 -3 10) 5) -3)

(define (find-k-largest lon k)
  (list-ref (sorted lon) (sub1 k)))