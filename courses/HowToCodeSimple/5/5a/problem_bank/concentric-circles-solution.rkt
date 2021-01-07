(require 2htdp/image)

;; concentric-circles-solution.rkt

;  PROBLEM:
;
;  Design a function that consumes a natural number n and a color c, and produces n
;  concentric circles of the given color.
;
;  So (concentric-circles 5 "black") should produce .
;


;; Natural String -> Image
;; produce n concentric circles of color c
(check-expect (concentric-circles 0 "red") empty-image)
(check-expect (concentric-circles 2 "purple") (overlay (circle 20 "outline" "purple")
                                                       (circle 10 "outline" "purple")
                                                       empty-image))
(check-expect (concentric-circles 5 "black") (overlay (circle 50 "outline" "black")
                                                      (circle 40 "outline" "black")
                                                      (circle 30 "outline" "black")
                                                      (circle 20 "outline" "black")
                                                      (circle 10 "outline" "black")
                                                      empty-image))

;(define (concentric-circles n c) empty-image)   ;stub

(define (concentric-circles n c)
  (cond [(zero? n) empty-image]
        [else
         (overlay (circle (* n 10) "outline" c)
                  (concentric-circles (sub1 n) c))]))
