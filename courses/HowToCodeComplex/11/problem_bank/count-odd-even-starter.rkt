
;; count-odd-even-starter.rkt

; PROBLEM:
;
; Previously we have written functions to count the number of elements in a list. In this
; problem we want a function that produces separate counts of the number of odd and even
; numbers in a list, and we only want to traverse the list once to produce that result.
;
; Design a tail recursive function that produces the Counts for a given list of numbers.
; Your function should produce Counts, as defined by the data definition below.
;
; There are two ways to code this function, one with 2 accumulators and one with a single
; accumulator. You should provide both solutions.
;


(define-struct counts (odds evens))
;; Counts is (make-counts Natural Natural)
;; interp. describes the number of even and odd numbers in a list

(define C1 (make-counts 0 0)) ;describes an empty list
(define C2 (make-counts 3 2)) ;describes (list 1 2 3 4 5))
