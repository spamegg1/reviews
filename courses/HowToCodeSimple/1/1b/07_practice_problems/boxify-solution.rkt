(require 2htdp/image)

;; boxify-solution.rkt

;
; PROBLEM:
;
; Use the How to Design Functions (HtDF) recipe to design a function that consumes an image,
; and appears to put a box around it. Note that you can do this by creating an "outline"
; rectangle that is bigger than the image, and then using overlay to put it on top of the image.
; For example:
;
; (boxify (ellipse 60 30 "solid" "red")) should produce .
;
; Remember, when we say DESIGN, we mean follow the recipe.
;
; Leave behind commented out versions of the stub and template.
;


;; Image -> Image
;; Puts a box around given image. Box is 2 pixels wider and taller than given image.
;; NOTE: A solution that follows the recipe but makes the box the same width and height
;;       is also good. It just doesn't look quite as nice.
(check-expect (boxify (circle 10 "solid" "red"))
              (overlay (rectangle 22 22 "outline" "black")
                       (circle 10 "solid" "red")))
(check-expect (boxify (star 40 "solid" "gray"))
              (overlay (rectangle 67 64 "outline" "black")
                       (star 40 "solid" "gray")))

;(define (boxify i) (circle 2 "solid" "green"))

#;
(define (boxify i)
  (... i))

(define (boxify i)
  (overlay (rectangle (+ (image-width  i) 2)
                      (+ (image-height i) 2)
                      "outline"
                      "black")
           i))
