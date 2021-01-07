(require 2htdp/image)
(require 2htdp/universe)

;; rolling-lambda-solution.rkt

;
; PROBLEM:
;
; Design a world program as follows:
;
; The world starts off with a lambda on the left hand side of the screen. As
; time passes, the lambda will roll towards the right hand side of the screen.
; Clicking the mouse changes the direction the lambda is rolling (ie from
; left -> right to right -> left). If the lambda hits the side of the window
; it should also change direction.
;
; Starting display (rolling to the right):
;
; .
;
; After a few seconds (rolling to the right):
;       .
; After a few more seconds (rolling to the right):
;                .
; A few seconds after clicking the mouse (rolling to the left):
;
;      .
;
; NOTE 1: Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
; NOTE 2: DO THIS PROBLEM IN STAGES.
;
; FIRST
;
; Just make the lambda slide back and forth across the screen without rolling.
;
; SECOND
;
; Make the lambda spin as it slides, but don't worry about making the spinning
; be just exactly right to make it look like its rolling. Just have it
; spinning and sliding back and forth across the screen.
;
; FINALLY
;
; Work out the math you need to in order to make the lambda look like it is
; actually rolling.  Remember that the circumference of a circle is 2*pi*radius,
; so that for each degree of rotation the circle needs to move:
;
;    2*pi*radius
;    -----------
;        360
;
; Also note that the rotate function requires an angle in degrees as its
; first argument. [By that it means Number[0, 360). As time goes by the lambda
; may end up spinning more than once, for example, you may get to a point
; where it has spun 362 degrees, which rotate won't accept. One solution to
; that is to  use the modulo function as follows:
;
; (rotate (modulo ... 360) LAMBDA)
;
; where ... can be an expression that produces any positive number of degrees
; and remainder will produce a number in [0, 360).
;
; Remember that you can lookup the documentation of modulo if you need to know
; more about it.
;


;; A lambda that rolls back and forth across screen.

;; =================
;; Constants:

(define LAMBDA .)

(define ALMOST-PI 3.14159)  ;we use ALMOST-PI instead of pi because if we
;                           ;use pi then we end up having to use check-within
;                           ;in all the tests

(define RADIUS (/ (image-height LAMBDA) 2))

(define CIRCUMFERENCE (* 2 ALMOST-PI RADIUS))



;; This is how far a circle moves in the x direction for each
;; degree of angle it turns.
(define X-MOVE-PER-DEGREE (/ CIRCUMFERENCE 360))

;; Change per clock tick in degrees and x position.
(define ANGULAR-SPEED -2)
(define       X-SPEED (* -1 ANGULAR-SPEED X-MOVE-PER-DEGREE))

(define WIDTH  (+ CIRCUMFERENCE (* 2 RADIUS)))
(define HEIGHT (* RADIUS 3))

(define YPOS (* RADIUS 2))

(define MTS (empty-scene WIDTH HEIGHT))

;; =================
;; Data definitions:


(define-struct ws (d x t))
;; WorldState is (make-ws Integer Number[0, WIDTH] Number[0, 360)]
;; interp. d is the direction lambda is moving, -1 means to left, +1 means to right
;;         x is screen coordinate of center of lambda
;;         t is angular rotation of lambda

(define WS1 (make-ws 1 5 100))
(define WS2 (make-ws -1 10 50))

#;
(define (fn-for-worldstate ws)
  (... (ws-d ws)     ;Integer
       (ws-x ws)     ;Number[0, WIDTH]
       (ws-t ws)))   ;Number[0, 360)

;; Template Rules
;; - compound: 3 fields

;; =================
;; Functions:

;; WorldState -> WorldState
;; starts the world - to start with LAMBDA at left hand side evaluate (main (make-ws 1 RADIUS 359))
;; <examples not needed>

(define (main ws)
  (big-bang ws
            (state true)
            (on-tick tock)
            (to-draw render)
            (on-mouse change-dir)))

;; WorldState -> WorldState
;; roll the lambda in direction ws-d
(check-expect (tock (make-ws 1 60 100))
              (make-ws 1
                       (+  60 X-SPEED)
                       (+ 100 ANGULAR-SPEED)))

(check-expect (tock (make-ws 1 (- WIDTH RADIUS) 1))
              (make-ws -1
                       (- WIDTH RADIUS)
                       1))

(check-expect (tock (make-ws -1 0 359))
              (make-ws 1
                       RADIUS
                       359))

;(define (tock ws) WS1)

; <took template from WorldState>

(define (tock ws)
  (cond [(>= (+ (ws-x ws) (* (ws-d ws) X-SPEED)) (- WIDTH RADIUS)) ;hit right edge
         (make-ws -1 (- WIDTH RADIUS) 1)]
        [(<= (+ (ws-x ws) (* (ws-d ws) X-SPEED))          RADIUS)  ;hit left edge
         (make-ws 1 RADIUS 359)]
        [else
         (make-ws (ws-d ws)
                  (+ (ws-x ws) (*    (ws-d ws) X-SPEED))
                  (modulo (+ (ws-t ws) (* (ws-d ws) ANGULAR-SPEED)) 359))]))

;; WorldState -> Image
;; place the lambda at ws-x, with angle ws-t
(check-expect (render WS1)
              (place-image (rotate (ws-t WS1) LAMBDA)
                           (ws-x WS1)
                           YPOS
                           MTS))

;(define (render ws) LAMBDA)

; <took template from WorldState>

(define (render ws)
  (place-image (rotate (ws-t ws) LAMBDA)
               (ws-x ws)
               YPOS
               MTS))


;; WorldState Number Number MouseEvent -> WorldState
;; reverse ws-d
(check-expect (change-dir WS1  0  0 "button-down") (make-ws -1  5 100))
(check-expect (change-dir WS2 10 20 "button-down") (make-ws  1 10  50))

;(define (change-dir x y ws me) WS1)

;<template according to MouseEvent>

(define (change-dir ws x y me)
  (cond [(mouse=? "button-down" me)
         (make-ws (* -1 (ws-d ws))
                  (ws-x ws)
                  (ws-t ws))]
        [else ws]))


;(main (make-ws 1 RADIUS 359))
