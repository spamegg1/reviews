(require 2htdp/image)
(require 2htdp/universe)

;; traffic-light-solution.rkt

;
; PROBLEM:
;
; Design an animation of a traffic light.
;
; Your program should show a traffic light that is red, then green,
; then yellow, then red etc. For this program, your changing world
; state data definition should be an enumeration.
;
; Here is what your program might look like if the initial world
; state was the red traffic light:
; .
; Next:
; .
; Next:
; .
; Next is red, and so on.
;
; To make your lights change at a reasonable speed, you can use the
; rate option to on-tick. If you say, for example, (on-tick next-color 1)
; then big-bang will wait 1 second between calls to next-color.
;
; Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
; Note: If you want to design a slightly simpler version of the program,
; you can modify it to display a single circle that changes color, rather
; than three stacked circles.
;


;; A simple animated traffic light.

;; =================
;; Constants:

(define RADIUS 20) ; of each light
(define SPACING 6) ; space between and beside lights

(define BACKGROUND (rectangle (+ (* 2 SPACING) (* 2 RADIUS))
                              (+ (* 4 SPACING) (* 6 RADIUS))
                              "solid"
                              "black"))

(define SPACE (square SPACING "solid" "black"))

(define RON
  (overlay (above SPACE
                  (circle RADIUS "solid"   "red")
                  SPACE
                  (circle RADIUS "outline" "yellow")
                  SPACE
                  (circle RADIUS "outline" "green")
                  SPACE)
           BACKGROUND))

(define YON
  (overlay (above SPACE
                  (circle RADIUS "outline" "red")
                  SPACE
                  (circle RADIUS "solid"   "yellow")
                  SPACE
                  (circle RADIUS "outline" "green")
                  SPACE)
           BACKGROUND))

(define GON
  (overlay (above SPACE
                  (circle RADIUS "outline" "red")
                  SPACE
                  (circle RADIUS "outline" "yellow")
                  SPACE
                  (circle RADIUS "solid"   "green")
                  SPACE)
           BACKGROUND))



;; =================
;; Data definitions:

;; Light is one of:
;;  - "red"
;;  - "yellow"
;;  - "green"
;; interp. the current color of the light
;; <examples are redundant for enumerations>

#;
(define (fn-for-light l)
  (cond [(string=? l "red")    (...)]
        [(string=? l "yellow") (...)]
        [(string=? l "green")  (...)]))

;; Template rules used:
;;   one of: 3 cases
;;   atomic distinct: "red"
;;   atomic distinct: "yellow"
;;   atomic distinct: "green"



;; =================
;; Functions:

;; Light -> Light
;; called to run the animation; start with (main "red")
;; no tests for main function
(define (main l)
  (big-bang l
            (on-tick next-color 1)   ; Light -> Light
            (to-draw render-light))) ; Light -> Image




;; Light -> Light
;; produce next color of light
(check-expect (next-color "red")    "green")
(check-expect (next-color "yellow") "red")
(check-expect (next-color "green")  "yellow")

#;
(define (next-color l)      ; stub
  "red")
;<template from Light>

(define (next-color l)
  (cond [(string=? l "red")    "green"]
        [(string=? l "yellow") "red"]
        [(string=? l "green")  "yellow"]))



;; Light -> Image
;; produce appropriate image for light color
(check-expect (render-light "red")    RON)
(check-expect (render-light "yellow") YON)
(check-expect (render-light "green")  GON)

#;
(define (render-light l)
  BACKGROUND)
;<template from Light>

(define (render-light l)
  (cond [(string=? l "red")    RON]
        [(string=? l "yellow") YON]
        [(string=? l "green")  GON]))
