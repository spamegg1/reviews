(require 2htdp/image)
(require 2htdp/universe)

;; spinning-solution.rkt

;
; PROBLEM:
;
; Design a world program as follows:
;
; The world starts off with a small square at the center of the screen. As time
; passes, the square stays fixed at the center, but increases in size and rotates
; at a constant speed.Pressing the spacebar resets the world so that the square
; is small and unrotated.
;
; Starting display:
; .
; After a few seconds:
; .
; After a few more seconds:
; .
; Immediately after pressing the spacebar:
; .
; NOTE 1: Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
; NOTE 2: The rotate function requires an angle in degrees as its first
; argument. By that it means Number[0, 360). As time goes by the box may end up
; spinning more than once, for example, you may get to a point where it has spun
; 362 degrees, which rotate won't accept. One solution to that is to use the
; remainder function as follows:
;
; (rotate (remainder ... 360) (text "hello" 30 "black"))
;
; where ... can be an expression that produces any positive number of degrees
; and remainder will produce a number in [0, 360).
;
; Remember that you can lookup the documentation of rotate if you need to know
; more about it.
;
; NOTE 3: There is a way to do this without using compound data. But you should
; design the compound data based solution.
;


; a growing and rotating box

;; =================
;; Constants:

(define WIDTH 700)
(define HEIGHT WIDTH)

(define CTR-X (/ WIDTH 2))
(define CTR-Y (/ HEIGHT 2))


(define ROTATIONAL-SPEED 15)
(define GROWTH-SPEED 1)


(define SQUARE-COLOR "red")
(define MTS (empty-scene WIDTH HEIGHT))


;; =================
;; Data definitions:

(define-struct box (a s))
;; Box is (make-box Natural Natural)
;; interp. a box with side length box-s and rotated (remainder box-a 360) degrees

(define B0 (make-box 0 10))
(define B1 (make-box 30 20))
(define B2 (make-box 60 40))

#;
(define (fn-for-box b)
  (... (box-a b)    ;Natural
       (box-s b)))  ;Natural

;; Template Rules Used:
;; - compound: 2 fields

;; =================
;; Functions:

;; Box -> Box
;; called to initiate a growing and rotating square. Start with (main (make-box 0 0))
;; no tests for main function
(define (main b)
  (big-bang b
            (on-tick next-box)
            (to-draw render-box)
            (on-key handle-key)))

;; Box -> Box
;; produces next box grown by GROWTH-SPEED and rotated ROTATIONAL-SPEED
(check-expect (next-box (make-box 20 50))
              (make-box (+ 20 ROTATIONAL-SPEED) (+ 50 GROWTH-SPEED)))

;(define (next-box b) b)
; Template from Box

(define (next-box b)
  (make-box (+ (box-a b) ROTATIONAL-SPEED)
            (+ (box-s b) GROWTH-SPEED)))

;; Box -> Image
;; draws a box at the middle of the screen with side box-s and rotated (remainder box-a 360) degrees
(check-expect (render-box (make-box 0 0))
              (place-image (rotate (remainder 0 360) (square 0 "solid" SQUARE-COLOR))
                           CTR-X CTR-Y
                           MTS))
(check-expect (render-box (make-box 120 70))
              (place-image (rotate (remainder 120 360) (square 70 "solid" SQUARE-COLOR))
                           CTR-X CTR-Y
                           MTS))

;(define (render-box b) MTS)
; Template from Box

(define (render-box b)
  (place-image (rotate (remainder (box-a b) 360) (square (box-s b) "solid" SQUARE-COLOR))
               CTR-X
               CTR-Y
               MTS))

;; Box KeyEvent -> Box
;; resets the world so the box is small and unrotated when space bar is pressed
(check-expect (handle-key (make-box 40 20) " ") (make-box 0 0))
(check-expect (handle-key (make-box 40 20) "a") (make-box 40 20))

;(define (handle-key b key) b)
; Template according to KeyEvent

(define (handle-key b key)
  (cond [(key=? key " ") (make-box 0 0)]
        [else b]))


(main (make-box 0 0))
