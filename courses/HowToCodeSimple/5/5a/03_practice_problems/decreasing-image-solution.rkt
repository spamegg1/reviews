(require 2htdp/image)

;; decreasing-image-solution.rkt

;  PROBLEM:
;
;  Design a function called decreasing-image that consumes a natural n and produces an image of all the numbers
;  from n to 0 side by side.
;
;  So (decreasing-image 3) should produce .


(define TEXT-SIZE 20)
(define TEXT-COLOR "black")
(define SPACING (text " " TEXT-SIZE TEXT-COLOR))

;; Natural -> Image
;; produce an image of n, n-1, ... 0 side by side
(check-expect (decreasing-image 0) (text "0" 20 "black"))
(check-expect (decreasing-image 3) (beside (text "3" 20 "black") SPACING
                                           (text "2" 20 "black") SPACING
                                           (text "1" 20 "black") SPACING
                                           (text "0" 20 "black")))

;(define (decreasing-image n) empty-image)   ; stub

(define (decreasing-image n)
  (cond [(zero? n) (text "0" TEXT-SIZE TEXT-COLOR)]
        [else
         (beside (text (number->string n) TEXT-SIZE TEXT-COLOR)
                 SPACING
                 (decreasing-image (sub1 n)))]))
