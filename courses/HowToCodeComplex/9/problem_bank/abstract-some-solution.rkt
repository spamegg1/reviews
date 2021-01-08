
;; abstract-some-solution.rkt

;
; PROBLEM:
;
; Design an abstract function called some-pred? (including signature, purpose,
; and tests) to simplify the following two functions. When you are done
; rewrite the original functions to use your new some-pred? function.
;


; PART A: Design an abstract function


;; (X -> Boolean) (listof X) -> Boolean
;; produces true if some number in the list fits the predicate
(check-expect (some-pred? positive? empty) false)
(check-expect (some-pred? positive? (list 1 4 -1 3)) true)
(check-expect (some-pred? positive? (list -1 -5)) false)
(check-expect (some-pred? negative? (list 2 -1)) true)
(check-expect (some-pred? even? (list 1 5 2 7)) true)
(check-expect (some-pred? odd? (list 1 5 2 7)) true)
(check-expect (some-pred? odd? (list 2 4 6)) false)

; <template from (listof X)>
(define (some-pred? pred lon)
  (cond [(empty? lon) false]
        [else
         (or (pred (first lon))
             (some-pred? pred (rest lon)))]))

; PART B: Rewrite the original functions


;; ListOfNumber -> Boolean
;; produce true if some number in lon is positive
(check-expect (some-positive? empty) false)
(check-expect (some-positive? (list 2 -3 -4)) true)
(check-expect (some-positive? (list -2 -3 -4)) false)

(define (some-positive? lon) (some-pred? positive? lon))

;; ListOfNumber -> Boolean
;; produce true if some number in lon is negative
(check-expect (some-negative? empty) false)
(check-expect (some-negative? (list 2 3 -4)) true)
(check-expect (some-negative? (list 2 3 4)) false)

; <template as call to some-pred?>
(define (some-negative? lon) (some-pred? negative? lon))

;
; For reference, here are the function definitions for the two original
; functions.
;
; ;; ListOfNumber -> Boolean
; ;; produce true if some number in lon is positive
; (check-expect (some-positive? empty) false)
; (check-expect (some-positive? (list 2 -3 -4)) true)
; (check-expect (some-positive? (list -2 -3 -4)) false)
;
; (define (some-positive? lon)
;   (cond [(empty? lon) false]
;         [else
;          (or (positive? (first lon))
;              (some-positive? (rest lon)))]))
;
;
; ;; ListOfNumber -> Boolean
; ;; produce true if some number in lon is negative
; (check-expect (some-negative? empty) false)
; (check-expect (some-negative? (list 2 3 -4)) true)
; (check-expect (some-negative? (list 2 3 4)) false)
;
; (define (some-negative? lon)
;   (cond [(empty? lon) false]
;         [else
;          (or (negative? (first lon))
;              (some-negative? (rest lon)))]))
;
