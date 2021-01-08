(require 2htdp/image)

;; van-koch-starter.rkt

;
; PROBLEM:
;
; First review the discussion of the Van Koch Line fractal at:
; http://pages.infinit.net/garrick/fractals/.
;
; Now design a function to draw a SIMPLIFIED version of the fractal.
;
; For this problem you will draw a simplified version as follows:
;
;
;        .
;
;
; Notice that the difference here is that the vertical parts of the
; curve, or segments BC and DE in this figure .
; are just ordinary lines they are not themselves recursive Koch curves.
; That ends up making things much simpler in terms of the math required
; to draw this curve.
;
; We want you to make the function consume positions using
; DrRacket's posn structure. A reasonable data definition for these
; is included below.
;
; The signature and purpose of your function should be:
;
; ;; Posn Posn Image -> Image
; ;; Add a simplified Koch fractal to image of length ln, going from p1 to p2
; ;; length ln is calculated by (distance p1 p2)
; ;; Assume p1 and p2 have same y-coordinate.
;
; (define (vkline p1 p2 img) img) ;stub
;
; Include a termination argument for your function.
;
; We've also given you some constants and two other functions
; below that should be useful.


;; Create a simplified Van Koch Line fractal.

;; =================
;; Constants:

(define LINE-CUTOFF 5)

(define WIDTH 300)
(define HEIGHT 200)
(define MTS (empty-scene WIDTH HEIGHT))

;; =================
;; Data definitions:

;(define-struct posn (x y))   ;struct is already part of racket
;; Posn is (make-posn Number Number)
;; interp. A cartesian position, x and y are screen coordinates.
(define P1 (make-posn 20 30))
(define P2 (make-posn 100 10))

;; =================
;; Functions:

;; Posn Posn -> Number
;; produce the distance between two points
(check-expect (distance P1 P1) 0)
(check-within (distance P1 P2) 82.4621125 0.0000001)

(define (distance p1 p2)
  (sqrt (+ (sqr (- (posn-x p2) (posn-x p1)))
           (sqr (- (posn-y p2) (posn-y p1))))))


;; Posn Posn Image -> Image
;; add a black line from p1 to p2 on image
(check-expect (simple-line P1 P2 MTS) (add-line MTS 20 30 100 10 "black"))

(define (simple-line p1 p2 img)
  (add-line img (posn-x p1) (posn-y p1) (posn-x p2) (posn-y p2) "black"))
