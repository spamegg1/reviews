(require 2htdp/image)

;; tall-solution.rkt

;
; PROBLEM:
;
; DESIGN a function that consumes an image and determines whether the
; image is tall.
;
; Remember, when we say DESIGN, we mean follow the recipe.
;
; Leave behind commented out versions of the stub and template.
;


;; Image -> Boolean
;; produce true if img is tall (height is > width)
(check-expect (tall? (rectangle 20 40 "solid" "red")) true)
(check-expect (tall? (rectangle 40 20 "solid" "red")) false)
(check-expect (tall? (square 40 "solid" "red")) false)

;(define (tall? img) ; stub
;  false)

;(define (tall? img) ; template
;  (... img))

(define (tall? img)
  (> (image-height img) (image-width img)))
