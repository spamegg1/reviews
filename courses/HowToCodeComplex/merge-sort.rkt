;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname merge-sort) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; merge sort

;; (listof Number) -> (list of Number)
;; produces sorted list in ascending order using merge sort
(check-expect (merge-sort empty) empty)
(check-expect (merge-sort (list 2)) (list 2))
(check-expect (merge-sort (list 1 2)) (list 1 2))
(check-expect (merge-sort (list 4 3)) (list 3 4))
(check-expect (merge-sort (list 6 5 3 1 8 7 2 4)) (list 1 2 3 4 5 6 7 8)) 

;; template according to generative recursion

(define (merge-sort lon)
  (cond [(empty? lon)  empty]
        [(empty? (rest lon)) lon]
        [else
         (merge (merge-sort (take lon (quotient (length lon) 2)))
                (merge-sort (drop lon (quotient (length lon) 2))))]))

;; (listof Number) (listof Number) -> (listof Number)
;; merge two lists into a single in ascending order
;; ASSUME: lon1 and lon2 are both already sorted
(check-expect (merge empty empty) empty)
(check-expect (merge (list 1) empty) (list 1))
(check-expect (merge empty (list 2)) (list 2))
(check-expect (merge (list 1 3 5 6) (list 2 4 7 8)) (list 1 2 3 4 5 6 7 8))

;; 2 one of table:
;;            lon2       empty        (cons N LON)
;;  lon1
;;
;;    empty              lon2  (1)    lon2  (1)
;;
;;    (cons N LON)       lon1  (2)    (cons <smaller of the firsts>  (3)
;;                                          <natural recursion>)

(define (merge lon1 lon2)
  (cond [(empty? lon1) lon2]  ;(1)
        [(empty? lon2) lon1]  ;(2)
        [else                 ;(3)
         (if (< (first lon1) (first lon2))
             (cons (first lon1) (merge (rest lon1) lon2))
             (cons (first lon2) (merge lon1 (rest lon2))))]))

;; (listof Number) Natural -> (listof Number)
;; produce list of first n elements of lon / list after dropping first n elements
;; ASSUME lon has at least n elements (n >= length of lon)
(check-expect (take empty 0) empty)
(check-expect (take (list 1 2 3 4) 0) empty)
(check-expect (take (list 1 2 3 4) 2) (list 1 2))
(check-expect (drop empty 0) empty)
(check-expect (drop (list 1 2 3 4) 0) (list 1 2 3 4))
(check-expect (drop (list 1 2 3 4) 2) (list 3 4))

;; 2 one of table:
;;            n             0            (add1 Natural)
;;  lon
;;
;;    <<<empty              empty        CAN'T HAPPEN>>>
;;
;;    (cons N LON)   take   empty (1)    (cons (first lon) (take (rest lon) (sub1 n))) (2)
;;                   drop   lon   (1)                      (drop (rest lon) (sub1 n))  (2)       

(define (take lon n)
  (cond [(zero? n) empty] ;(1)
        [else             ;(2)
         (cons (first lon) (take (rest lon) (sub1 n)))]))

(define (drop lon n)
  (cond [(zero? n) lon]   ;(1)
        [else             ;(2)
         (drop (rest lon) (sub1 n))]))









