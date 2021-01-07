(require 2htdp/image)

;; make-box-solution.rkt

;
; PROBLEM:
;
; You might want to create boxes of different colours.
;
; Use the How to Design Functions (HtDF) recipe to design a function that consumes a color, and creates a
; solid 10x10 square of that colour.  Follow the HtDF recipe and leave behind commented out versions of
; the stub and template.
;


;; Color -> Image
;; Create a box of the given color
(check-expect (make-box "red") (square 10 "solid" "red"))
(check-expect (make-box "gray") (square 10 "solid" "gray"))

;(define (make-box c) (square 0 "solid" "white"))

#;
(define (make-box c)
  (... c))

(define (make-box c)
  (square 10 "solid" c))