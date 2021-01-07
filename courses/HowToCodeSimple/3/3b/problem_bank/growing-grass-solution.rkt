(require 2htdp/image)
(require 2htdp/universe)

;; growing-grass-solution.rkt

;
; PROBLEM:
;
; Design a world program as follows:
;
; The world starts off with a piece of grass waiting to grow. As time passes,
; the grass grows upwards. Pressing any key cuts the current strand of
; grass to 0, allowing a new piece to grow to the right of it.
;
; Starting display:
;
; .
;
; After a few seconds:
;
; .
;
; After a few more seconds:
;
; .
;
; Immediately after pressing any key:
;
; .
;
; A few more seconds after pressing any key:
;
; .
;
; NOTE 1: Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;


; Growing and replanting grass

;; =================
;; Constants:

(define WIDTH 600)
(define HEIGHT 400)

(define GRASS-COLOR "green")
(define GRASS-WIDTH 10)
(define GRASS-Y HEIGHT)
(define GRASS-SPACING 10)

(define SUN (overlay (circle 45 "solid" "yellow")
                     (radial-star 20 70 50 "solid" "orange")))
(define MTS (overlay/align "left"
                           "top"
                           SUN
                           (rectangle WIDTH HEIGHT "solid" "sky blue")))

(define GROW-SPEED 1)

;; =================
;; Data definitions:

(define-struct grass (x h))
;; Grass is (make-grass Number[0,WIDTH] Number)
;; interp. a grass at some x-position that is grass-h tall
(define G1 (make-grass 0 0))
(define G2 (make-grass 10 4))

#;
(define (fn-for-grass g)
  (... (grass-x g)     ;Number[0,WIDTH]
       (grass-h g)))   ;Number

;; Template Rules
;; - compound: 2 fields

;; =================
;; Functions:

;; Grass -> Grass
;; starts the world call (main (make-grass 10 0))
;; <examples not needed>

(define (main g)
  (big-bang g
            (on-tick grow-grass)
            (to-draw place-grass)
            (on-key replant-grass)))

;; Grass -> Grass
;; increase g's height by GROW-SPEED
(check-expect (grow-grass (make-grass 0 1))
              (make-grass 0 (+ 1 GROW-SPEED)))

;(define (grow-grass g) G2)    ;stub
; Template from Grass

(define (grow-grass g)
  (make-grass (grass-x g)
              (+ GROW-SPEED (grass-h g))))

;; Grass -> Image
;; place the grass at grass-x, GRASS-Y with height grass-h
(check-expect (place-grass (make-grass 1 20))
              (place-image/align (rectangle GRASS-WIDTH 20 "solid" GRASS-COLOR)
                                 1 GRASS-Y
                                 "center" "bottom"
                                 MTS))

;(define (place-grass g img) MTS)   ;stub
; Template from Grass

(define (place-grass g)
  (place-image/align (rectangle GRASS-WIDTH
                                (grass-h g)
                                "solid"
                                GRASS-COLOR)
                     (grass-x g) GRASS-Y
                     "center" "bottom"
                     MTS))

;; Grass KeyEvent -> Grass
;; replant new grass at (grass-x + GRASS-SPACING)

(check-expect (replant-grass (make-grass 30 40) " ") (make-grass 40 0))

;(define (replant-grass g ke) G1)    ;stub
; Template from Grass

(define (replant-grass g ke)
  (make-grass (+ (grass-x g) GRASS-SPACING) 0))
