(require 2htdp/image)
(require 2htdp/universe)

;; tracker-solution.rkt

;
; PROBLEM:
;
; Design a world program that displays the current (x, y) position
; of the mouse at that current position. So as the mouse moves the
; numbers in the (x, y) display changes and its position changes.
;




;; Display the current mouse position, at the mouse position.



;; =================
;; Constants:

(define WIDTH  400)
(define HEIGHT 400)

(define TEXT-SIZE 20)
(define TEXT-COLOR "black")

(define MTS (empty-scene WIDTH HEIGHT))




;; =================
;; Data definitions:

(define-struct position (x y))
;; Position is (make-position Integer Integer)
;; interp. position of mouse in pixels
;;
(define P1 (make-position 0 0))          ;upper left
(define P2 (make-position WIDTH HEIGHT)) ;lower right
#;
(define (fn-for-position )
  (... (position-x p)   ;Integer
       (position-y p))) ;Integer

;; Template rules used:
;;  - compound: 2 fields





;; =================
;; Functions:

;; Position -> Position
;; called to start the mouse tracker, call with (main (make-position 0 0))
;; no tests for main function
(define (main p)
  (big-bang p
            (to-draw  render)         ; Position -> Image
            (on-mouse handle-mouse))) ; Position Integer Integer MouseEvent -> Position






;; Position -> Image
;; render current position at the position itself
(check-expect (render (make-position 110 33))
              (place-image (text "(110, 33)" TEXT-SIZE TEXT-COLOR) 110 33 MTS))

;(define (render c) MTS)  ;stub
;<template from Position>
(define (render p)
  (place-image (text (string-append "("
                                    (number->string (position-x p))
                                    ", "
                                    (number->string (position-y p))
                                    ")")
                     TEXT-SIZE
                     TEXT-COLOR)
               (position-x p)
               (position-y p)
               MTS))


;; Position Integer Integer MouseEvent -> Position
;; changes current position world state to current mouse position
(check-expect (handle-mouse (make-position 20 30) 3 4 "button-down") (make-position 20 30))
(check-expect (handle-mouse (make-position 20 30) 3 4 "move")        (make-position  3  4))

;(define (handle-mouse p x y me) p) ;stub

;<template according to MouseEvent>

(define (handle-mouse p x y me)
  (cond [(mouse=? me "move") (make-position x y)]
        [else p]))
