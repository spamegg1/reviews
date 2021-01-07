(require 2htdp/image)

;; compare-images-solution.rkt

;
; PROBLEM:
;
; Based on the two constants provided, write three expressions to determine whether:
;
; 1) IMAGE1 is taller than IMAGE2
; 2) IMAGE1 is narrower than IMAGE2
; 3) IMAGE1 has both the same width AND height as IMAGE2
;

(define IMAGE1 (rectangle 10 15 "solid" "red"))
(define IMAGE2 (rectangle 15 10 "solid" "red"))


(> (image-height IMAGE1)
   (image-height IMAGE2))

(< (image-width IMAGE1)
   (image-width IMAGE2))

(and (= (image-width IMAGE1)
        (image-width IMAGE2))
     (= (image-height IMAGE1)
        (image-height IMAGE2)))
