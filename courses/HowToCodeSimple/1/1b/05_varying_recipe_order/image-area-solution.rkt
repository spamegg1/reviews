(require 2htdp/image)

;; image-area-solution.rkt

;
; PROBLEM:
;
; DESIGN a function called image-area that consumes an image and produces the
; area of that image. For the area it is sufficient to just multiple the image's
; width by its height.  Follow the HtDF recipe and leave behind commented
; out versions of the stub and template.
;


;; Image -> Natural
;; produce the area of the consumed image (width * height)
(check-expect (image-area (rectangle 3 4 "solid" "blue")) (* 3 4))

;(define (image-area img) 0) ;stub

;(define (image-area img)    ;template
;  (... img))

(define (image-area img)
  (* (image-width img)
     (image-height img)))
