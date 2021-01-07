(require 2htdp/image)
(require 2htdp/universe)

;; making-rain-filtered-solution.rkt

;
; PROBLEM:
;
; Design a simple interactive animation of rain falling down a screen. Wherever we click,
; a rain drop should be created and as time goes by it should fall. Over time the drops
; will reach the bottom of the screen and "fall off". You should filter these excess
; drops out of the world state - otherwise your program is continuing to tick and
; and draw them long after they are invisible.
;
; In your design pay particular attention to the helper rules. In our solution we use
; these rules to split out helpers:
;   - function composition
;   - reference
;   - knowledge domain shift
;
;


;; Make it rain where we want it to.

;; =================
;; Constants:

(define WIDTH  300)
(define HEIGHT 300)

(define SPEED 1)

(define DROP (ellipse 4 8 "solid" "blue"))

(define MTS (rectangle WIDTH HEIGHT "solid" "light blue"))

;; =================
;; Data definitions:

(define-struct drop (x y))
;; Drop is (make-drop Integer Integer)
;; interp. A raindrop on the screen, with x and y coordinates.

(define D1 (make-drop 10 30))

#;
(define (fn-for-drop d)
  (... (drop-x d)
       (drop-y d)))

;; Template Rules used:
;; - compound: 2 fields


;; ListOfDrop is one of:
;;  - empty
;;  - (cons Drop ListOfDrop)
;; interp. a list of drops

(define LOD1 empty)
(define LOD2 (cons (make-drop 10 20) (cons (make-drop 3 6) empty)))

#;
(define (fn-for-lod lod)
  (cond [(empty? lod) (...)]
        [else
         (... (fn-for-drop (first lod))
              (fn-for-lod (rest lod)))]))

;; Template Rules used:
;; - one-of: 2 cases
;; - atomic distinct: empty
;; - compound: (cons Drop ListOfDrop)
;; - reference: (first lod) is Drop
;; - self reference: (rest lod) is ListOfDrop

;; =================
;; Functions:

;; ListOfDrop -> ListOfDrop
;; start rain program by evaluating (main empty)
(define (main lod0)
  (big-bang lod0
            (on-mouse handle-mouse)   ; ListOfDrop Integer Integer MouseEvent -> ListOfDrop
            (on-tick  next-drops)     ; ListOfDrop -> ListOfDrop
            (to-draw  render-drops))) ; ListOfDrop -> Image


;; ListOfDrop Integer Integer MouseEvent -> ListOfDrop
;; if mevt is "button-down" add a new drop at that position
(check-expect (handle-mouse empty 3 12 "button-down")
              (cons (make-drop 3 12) empty))
(check-expect (handle-mouse empty 3 12 "drag")
              empty)

;<template from MouseEvent>

(define (handle-mouse lod x y mevt)
  (cond [(mouse=? mevt "button-down") (cons (make-drop x y) lod)]
        [else lod]))


;; ListOfDrop -> ListOfDrop
;; produce filtered and ticked list of drops
(check-expect (next-drops empty) empty)
(check-expect (next-drops (cons (make-drop 3 4)
                                (cons (make-drop 90 HEIGHT)
                                      empty)))
              (cons (make-drop 3 5)
                    empty))

;<template as function composition>

(define (next-drops lod)
  (onscreen-only (tick-drops lod)))


;; ListOfDrop -> ListOfDrop
;; produce list of ticked drops
(check-expect (tick-drops empty) empty)
(check-expect (tick-drops (cons (make-drop 3 4)
                                (cons (make-drop 90 100)
                                      empty)))
              (cons (make-drop 3 (+ SPEED 4))
                    (cons (make-drop 90 (+ SPEED 100))
                          empty)))

;<template from ListOfDrop>

(define (tick-drops lod)
  (cond [(empty? lod) empty]
        [else
         (cons (tick-drop (first lod))
               (tick-drops (rest lod)))]))


;; Drop -> Drop
;; produce a new drop that is one pixel farther down the screen
(check-expect (tick-drop (make-drop 6 9)) (make-drop 6 (+ SPEED 9)))

;<template from Drop>

(define (tick-drop d)
  (make-drop (drop-x d) (+ SPEED (drop-y d))))


;; ListOfDrop -> ListOfDrop
;; produce a list containing only those drops in lod that are onscreen?
(check-expect (onscreen-only empty) empty)
(check-expect (onscreen-only (cons (make-drop 3 4)
                                   (cons (make-drop 1 (+ 1 HEIGHT))
                                         empty)))
              (cons (make-drop 3 4)
                    empty))

;<template from ListOfDrop>

(define (onscreen-only lod)
  (cond [(empty? lod) empty]
        [else
         (if (onscreen? (first lod))
             (cons (first lod) (onscreen-only (rest lod)))
             (onscreen-only (rest lod)))]))


;; Drop -> Boolean
;; produce true if d has not fallen off the bottom of MTS
(check-expect (onscreen? (make-drop 2 -1)) false)
(check-expect (onscreen? (make-drop 2  0)) true)
(check-expect (onscreen? (make-drop 2  1)) true)
(check-expect (onscreen? (make-drop 2 (- HEIGHT 1))) true)
(check-expect (onscreen? (make-drop 2    HEIGHT))    true)
(check-expect (onscreen? (make-drop 2 (+ HEIGHT 1))) false)

;<template from Drop>


(define (onscreen? d)
  (<= 0 (drop-y d) HEIGHT))


;; ListOfDrop -> Image
;; Render the drops onto MTS
(check-expect (render-drops empty) MTS)
(check-expect (render-drops (cons (make-drop 3 7) empty))
              (place-image DROP 3 7 MTS))
(check-expect (render-drops (cons (make-drop 3 7) (cons (make-drop 12 30) empty)))
              (place-image DROP 3 7 (place-image DROP 12 30 MTS)))

;<template from ListOfDrop>

(define (render-drops lod)
  (cond [(empty? lod) MTS]
        [else
         (place-drop (first lod)
                     (render-drops (rest lod)))]))


;; Drop Image -> Image
;; place drop on img as specified by d
(check-expect (place-drop (make-drop 9 5) MTS)
              (place-image DROP 9 5 MTS))

;<template from Drop w/ extra atomic parameter>

(define (place-drop d img)
  (place-image DROP (drop-x d) (drop-y d) img))
