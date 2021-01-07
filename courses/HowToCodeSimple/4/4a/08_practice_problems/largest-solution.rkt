
;; largest-solution.rkt

;; =================
;; Data definitions:

;
; Remember the data definition for a list of numbers we designed in Lecture 5f:
; (if this data definition does not look familiar, please review the lecture)
;


;; ListOfNumber is one of:
;;  - empty
;;  - (cons Number ListOfNumber)
;; interp. a list of numbers
(define LON1 empty)
(define LON2 (cons 60 (cons 42 empty)))
#;
(define (fn-for-lon lon)
  (cond [(empty? lon) (...)]
        [else
         (... (first lon)
              (fn-for-lon (rest lon)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons Number ListOfNumber)
;;  - self-reference: (rest lon) is ListOfNumber

;; =================
;; Functions:

;
; PROBLEM:
;
; Design a function that consumes a list of numbers and produces the largest number
; in the list. You may assume that all numbers in the list are greater than 0. If
; the list is empty, produce 0.
;


;; ListOfNumber -> Number
;; produce the largest number in the given list, or 0 if empty
;; ASSUMES that every element of lon is > 0
(check-expect (largest empty) 0)
(check-expect (largest LON2) 60)
(check-expect (largest (cons 10 (cons 20 (cons 50 empty)))) 50)

;(define (largest lon) 0) ;stub
;<template from ListOfNumber>

(define (largest lon)
  (cond [(empty? lon) 0]
        [else
         (if (> (first lon) (largest (rest lon)))
             (first lon)
             (largest (rest lon)))]))
