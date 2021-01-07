(require 2htdp/image)

;; blue-triangle-solution.rkt

; PROBLEM:
;
; Design a function that consumes a number and produces a blue solid triangle of that size.
;
; You should use The How to Design Functions (HtDF) recipe, and your complete design should include
; signature, purpose, commented out stub, examples/tests, commented out template and the completed function.


;; Natural -> Image
;; Given a number, produce a blue solid triangle of that size.
(check-expect (blue-triangle 7) (triangle 7 "solid" "blue"))
(check-expect (blue-triangle 50) (triangle 50 "solid" "blue"))
(check-expect (blue-triangle 100) (triangle 100 "solid" "blue"))

;(define (blue-triangle n) empty-image) ; stub

#;
(define (blue-triangle n)
  (... n))

(define (blue-triangle n)
  (triangle n "solid" "blue"))
