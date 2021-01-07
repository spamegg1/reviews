(require 2htdp/image)

;; image-list-solution.rkt

;; =================
;; Data definitions:

;
; PROBLEM A:
;
; Design a data definition to represent a list of images. Call it ListOfImage.
;


;; ListOfImage is one of:
;;  - empty
;;  - (cons Image ListOfImage)
;; interp. a list of images
(define LI0 empty)
(define LI1 (cons (circle 20 "solid" "red") empty))
(define LI2 (cons (circle 40 "solid" "blue") (cons (circle 20 "solid" "red") empty)))

#;
(define (fn-for-loi loi)
  (cond [(empty? loi) (...)]
        [else
         (... (first loi)
              (fn-for-loi (rest loi)))]))

;; Template rules used:
;; - one of: 2 cases
;; - atomic distinct: empty
;; - compound: (cons Image ListOfImage)
;; - atomic non-distinct: (first loi) is Image
;; - self-reference: (rest loi) is ListOfImage

;; =================
;; Functions:

;
; PROBLEM B:
;
; Design a function that consumes a list of images and produces a number
; that is the sum of the areas of each image. For area, just use the image's
; width times its height.
;


;; ListOfImage -> Number
;; produce total area of all images
(check-expect (total-area empty) 0)
(check-expect (total-area (cons (rectangle 2 4 "solid" "red")
                                (cons (square 3 "solid" "blue")
                                      empty)))
              17)

;(define (total-area loi) 0)  ;stub
;<template from ListOfImage>

(define (total-area loi)
  (cond [(empty? loi) 0]
        [else
         (+ (* (image-width (first loi))
               (image-height (first loi)))
            (total-area (rest loi)))]))
