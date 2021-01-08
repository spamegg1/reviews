
;; abstract-sum-starter.rkt

;
; PROBLEM A:
;
; Design an abstract function (including signature, purpose, and tests) to
; simplify the two sum-of functions.
;


;; (listof Number) -> Number
;; produce the sum of the squares of the numbers in lon
(check-expect (sum-of-squares empty) 0)
(check-expect (sum-of-squares (list 2 4)) (+ 4 16))

(define (sum-of-squares lon)
  (cond [(empty? lon) 0]
        [else
         (+ (sqr (first lon))
            (sum-of-squares (rest lon)))]))

;; (listof String) -> Number
;; produce the sum of the lengths of the strings in los
(check-expect (sum-of-lengths empty) 0)
(check-expect (sum-of-lengths (list "a" "bc")) 3)

(define (sum-of-lengths los)
  (cond [(empty? los) 0]
        [else
         (+ (string-length (first los))
            (sum-of-lengths (rest los)))]))


;
; PROBLEM B:
;
; Now re-define the original functions to use abstract-sum.
;
; Remember, the signature and tests should not change from the original
; functions.
;
