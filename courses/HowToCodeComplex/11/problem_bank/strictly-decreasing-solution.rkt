
;; strictly-decreasing-solution.rkt

;
; PROBLEM:
;
; Design a function that consumes a list of numbers and produces true if the
; numbers in lon are strictly decreasing. You may assume that the list has at
; least two elements.
;


;; (listof Number) -> Boolean
;; Produce true if the numbers in lon are strictly decreasing
;; ASSUME: the list has at least two elements
(check-expect (strictly-decreasing? (list 1 1))   false)
(check-expect (strictly-decreasing? (list 1 2))   false)
(check-expect (strictly-decreasing? (list 2 1))   true)
(check-expect (strictly-decreasing? (list 1 2 3)) false)
(check-expect (strictly-decreasing? (list 3 2 1)) true)

; <template from (listof Number) + accumulator>
(define (strictly-decreasing? lon0)
  ;; prev: Number; the previous number in lon0
  ;; (strictly-decreasing? (list 3 2 1))
  ;;
  ;; (strictly-decreasing? (list 2 1)  true 3)
  ;; (strictly-decreasing? (list   1)  true 2)
  ;; (strictly-decreasing? (list    )  true 1)
  (local [(define (strictly-decreasing? lon prev)
            (cond [(empty? lon) true]
                  [else
                   (if (< (first lon) prev)
                       (strictly-decreasing? (rest lon) (first lon))
                       false)]))]
    (strictly-decreasing? (rest lon0) (first lon0))))
