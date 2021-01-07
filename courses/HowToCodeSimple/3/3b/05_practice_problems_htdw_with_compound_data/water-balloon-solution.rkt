
;; water-balloon-solution.rkt

; PROBLEM:
;
; In this problem, we will design an animation of throwing a water balloon.
; When the program starts the water balloon should appear on the left side
; of the screen, half-way up.  Since the balloon was thrown, it should
; fly across the screen, rotating in a clockwise fashion. Pressing the
; space key should cause the program to start over with the water balloon
; back at the left side of the screen.
;
; NOTE: Please include your domain analysis at the top in a comment box.
;
; Use the following images to assist you with your domain analysis:
;
;
; 1)
; 2).
; .
; 3)
; .
; 4)
;
; .
;
;
; Here is an image of the water balloon:
; (define WATER-BALLOON.)
;
;
;
; NOTE: The rotate function wants an angle in degrees as its first
; argument. By that it means Number[0, 360). As time goes by your balloon
; may end up spinning more than once, for example, you may get to a point
; where it has spun 362 degrees, which rotate won't accept.
;
; The solution to that is to use the modulo function as follows:
;
; (rotate (modulo ... 360) (text "hello" 30 "black"))
;
; where ... should be replaced by the number of degrees to rotate.
;
; NOTE: It is possible to design this program with simple atomic data,
; but we would like you to use compound data.


(require 2htdp/image)
(require 2htdp/universe)

;; Spinning water balloon


;; ===========

;; Constants

(define WIDTH  600)
(define HEIGHT 300)

(define CTR-Y (/ HEIGHT 2))

(define LINEAR-SPEED 2)
(define ANGULAR-SPEED 3) ;optional

(define MTS (rectangle WIDTH HEIGHT "solid" "white"))

(define WATER-BALLOON.)

;; ============
;; Data Definitions

(define-struct bs (x a))
;; BalloonState is (make-bs Number Number)
;; interp. The state of a tossed balloon.
;;         x is the x-coordinate in pixels
;;         a is the angle of rotation in degrees
;;
(define BS1 (make-bs 10 0))
(define BS2 (make-bs 30 15))
#;
(define (fn-for-balloon-state bs)
  (... (bs-x bs)
       (bs-a bs)))

;; Template rules used:
;; - compound: 2 fields



;; ===========
;; Functions

;; BalloonState -> BalloonState
;; run the animation, starting with initial balloon state bs.
;; Start with (main (make-bs 0 0))
;; <no tests for main functions>

(define (main bs)
  (big-bang bs
            (on-tick next-bs)
            (to-draw render-bs)
            (on-key  reset-bs)))

;; BalloonState -> BalloonState
;; advance bs by LINEAR-SPEED and ANGULAR-SPEED
(check-expect (next-bs (make-bs 1 12))
              (make-bs (+ 1 LINEAR-SPEED) (- 12 ANGULAR-SPEED)))

;(define (next-bs bs) bs)  ;stub
; Template from BalloonState

(define (next-bs bs)
  (make-bs (+ (bs-x bs) LINEAR-SPEED)
           (- (bs-a bs) ANGULAR-SPEED)))


;; BalloonState -> Image
;; Produces the bs at height bs-x rotated (remainder bs-a 360) on the MTS
(check-expect (render-bs (make-bs 1 12))
              (place-image (rotate 12 WATER-BALLOON)
                           1
                           CTR-Y
                           MTS))
(check-expect (render-bs (make-bs 10 361))
              (place-image (rotate 1 WATER-BALLOON)
                           10
                           CTR-Y
                           MTS))

; (define (render-bs bs) MTS)

; Template from BalloonState
(define (render-bs bs)
  (place-image (rotate (modulo (bs-a bs) 360) WATER-BALLOON)
               (bs-x bs)
               CTR-Y
               MTS))


;; BalloonState KeyEvent -> BalloonState
;; Resets the program so the balloon is back at the top, unrotated, when space bar is pressed
(check-expect (reset-bs (make-bs 1 12) " ")
              (make-bs 0 0))
(check-expect (reset-bs (make-bs 1 12) "left")
              (make-bs 1 12))

; (define (reset-bs bs key) bs)

; Template from KeyEvent
(define (reset-bs bs key)
  (cond [(key=? " " key) (make-bs 0 0)]
        [else bs]))
