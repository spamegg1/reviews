
;; abstract-sum-solution.rkt

;
; PROBLEM A:
;
; Design an abstract function (including signature, purpose, and tests) to
; simplify the two sum-of functions.
;


;; (X -> Number) (listof X) -> Number
;; Produce sum of calling fn on every element of lox.
(check-expect (abstract-sum string-length empty) 0)
(check-expect (abstract-sum string-length (list "a" "bc" "def")) 6)
(check-expect (abstract-sum sqr (list 2 4)) (+ 4 16))

; <template from (listof X)>
(define (abstract-sum fn lox)
  (cond [(empty? lox) 0]
        [else
         (+ (fn (first lox))
            (abstract-sum fn (rest lox)))]))

;
; PROBLEM B:
;
; Now re-define the original functions to use the new abstract function.
;
; Remember, the signature and tests should not change from the original
; functions.
;


;; (listof Number) -> Number
;; produce the sum of the squares of the numbers in lon
(check-expect (sum-of-squares empty) 0)
(check-expect (sum-of-squares (list 2 4)) (+ 4 16))

; <template as call to abstract-sum>
(define (sum-of-squares lon) (abstract-sum sqr lon))

;; (listof String) -> Number
;; produce the sum of the lengths of the strings in los
(check-expect (sum-of-lengths empty) 0)
(check-expect (sum-of-lengths (list "a" "bc")) 3)

; <template as call to abstract-sum>
(define (sum-of-lengths los) (abstract-sum string-length los))

;
; For reference, here are the function definitions for the two original
; functions.
;
; ;; (listof Number) -> Number
; ;; produce the sum of the squares of the numbers in lon
; (check-expect (sum-of-squares empty) 0)
; (check-expect (sum-of-squares (list 2 4)) (+ 4 16))
;
; (define (sum-of-squares lon)
;   (cond [(empty? lon) 0]
;         [else
;          (+ (sqr (first lon))
;             (sum-of-squares (rest lon)))]))
;
; ;; (listof String) -> Number
; ;; produce the sum of the lengths of the strings in los
; (check-expect (sum-of-lengths empty) 0)
; (check-expect (sum-of-lengths (list "a" "bc")) 3)
;
; (define (sum-of-lengths los)
;   (cond [(empty? los) 0]
;         [else
;          (+ (string-length (first los))
;             (sum-of-lengths (rest los)))]))
;
