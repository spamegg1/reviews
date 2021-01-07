;; spinning-bears-solution.rkt

(require 2htdp/image)
(require 2htdp/universe)

; PROBLEM:
;
; In this problem you will design another world program. In this program the changing
; information will be more complex - your type definitions will involve arbitrary
; sized data as well as the reference rule and compound data. But by doing your
; design in two phases you will be able to manage this complexity. As a whole, this problem
; will represent an excellent summary of the material covered so far in the course, and world
; programs in particular.
;
; This world is about spinning bears. The world will start with an empty screen. Clicking
; anywhere on the screen will cause a bear to appear at that spot. The bear starts out upright,
; but then rotates counterclockwise at a constant speed. Each time the mouse is clicked on the
; screen, a new upright bear appears and starts spinning.
;
; So each bear has its own x and y position, as well as its angle of rotation. And there are an
; arbitrary amount of bears.
;
; To start, design a world that has only one spinning bear. Initially, the world will start
; with one bear spinning in the center at the screen. Clicking the mouse at a spot on the
; world will replace the old bear with a new bear at the new spot. You can do this part
; with only material up through compound.
;
; Once this is working you should expand the program to include an arbitrary number of bears.
;
; Here is an image of a bear for you to use: .


;; Spinning Bears

;; =================
;; Constants:

(define WIDTH 600) ; width of the scene
(define HEIGHT 700) ; height of the scene

(define SPEED 3)  ; speed of rotation

(define MTS (empty-scene WIDTH HEIGHT)) ; the empty scene

(define BEAR-IMG .)

;; =================
;; Data definitions:

(define-struct bear (x y r))
;; Bear is (make-bear Number[0,WIDTH] Number[0,HEIGHT] Integer)
;; interp.  (make-bear x y r) is the state of a bear, where
;;  x is the x coordinate in pixels,
;;  y is the y coordinate in pixels, and
;;  r is the angle of rotation in degrees

(define B1 (make-bear 0 0 0)) ; bear in the upper left corner
(define B2 (make-bear (/ WIDTH 2) (/ HEIGHT 2) 90)) ; sideways bear in the middle

#;
(define (fn-for-bear b)
  (... (bear-x b)     ; Number[0,WIDTH]
       (bear-y b)     ; Number[0,HEIGHT]
       (bear-r b)))   ; Integer

;; Template Rules Used:
;; - compound: 3 fields


;; ListOfBear is one of:
;; - empty
;; - (cons Bear ListOfBear)
(define LB0 empty)
(define LB1 (cons B1 empty))
(define LB2 (cons B1 (cons B2 empty)))

#;
(define (fn-for-lob lob)
  (cond [(empty? lob) (...)]
        [else
         (... (fn-for-bear (first lob))
              (fn-for-lob (rest lob)))]))

;; Template Rules Used:
;; - one of: 2 cases
;; - atomic distinct: empty
;; - compound: 2 fields
;; - reference: (first lob) is Bear
;; - self-reference: (rest lob) is ListOfBear

;; =================
;; Functions:

;; ListOfBear -> ListOfBear
;; start the world with (main empty)
;;
(define (main lob)
  (big-bang lob                         ; ListOfBear
            (on-tick   spin-bears)      ; ListOfBear -> ListOfBear
            (to-draw   render-bears)    ; ListOfBear -> Image
            (on-mouse  handle-mouse)))  ; ListOfBear Integer Integer MouseEvent -> ListOfBear

;; ListOfBear -> ListOfBear
;; spin all of the bears forward by SPEED degrees

(check-expect (spin-bears empty) empty)
(check-expect (spin-bears
               (cons (make-bear 0 0 0) empty))
              (cons (make-bear 0 0 (+ 0 SPEED)) empty))
(check-expect (spin-bears
               (cons (make-bear 0 0 0)
                     (cons (make-bear (/ WIDTH 2) (/ HEIGHT 2) 90)
                           empty)))
              (cons (make-bear 0 0 (+ 0 SPEED))
                    (cons (make-bear (/ WIDTH 2) (/ HEIGHT 2) (+ 90 SPEED)) empty)))

;(define (spin-bears lob) empty)

;; Took template from ListOfBear

(define (spin-bears lob)
  (cond [(empty? lob) empty]
        [else
         (cons (spin-bear (first lob))
               (spin-bears (rest lob)))]))


;; Bear -> Bear
;; spin a bear forward by SPEED degrees

(check-expect (spin-bear (make-bear 0 0 0)) (make-bear 0 0 (+ 0 SPEED)))
(check-expect (spin-bear (make-bear (/ WIDTH 2) (/ HEIGHT 2) 90))
              (make-bear (/ WIDTH 2) (/ HEIGHT 2) (+ 90 SPEED)))

;(define (spin-bear b) b)

;; Took template from Bear
(define (spin-bear b)
  (make-bear (bear-x b)
             (bear-y b)
             (+ (bear-r b) SPEED)))


;; ListOfBear -> Image
;; render the bears onto the empty scene

(check-expect (render-bears empty) MTS)
(check-expect (render-bears (cons (make-bear 0 0 0) empty))
              (place-image (rotate 0 BEAR-IMG) 0 0 MTS))
(check-expect (render-bears
               (cons (make-bear 0 0 0)
                     (cons (make-bear (/ WIDTH 2) (/ HEIGHT 2) 90)
                           empty)))
              (place-image (rotate 0 BEAR-IMG) 0 0
                           (place-image (rotate 90 BEAR-IMG) (/ WIDTH 2) (/ HEIGHT 2)
                                        MTS)))


;(define (render-bears lob) MTS)

;; Took Template from ListOfBear

(define (render-bears lob)
  (cond [(empty? lob) MTS]
        [else
         (render-bear-on (first lob) (render-bears (rest lob)))]))


;; Bear Image -> Image
;; render an image of the bear on the given image

(check-expect (render-bear-on (make-bear 0 0 0) MTS) (place-image (rotate 0 BEAR-IMG) 0 0 MTS))
(check-expect (render-bear-on (make-bear (/ WIDTH 2) (/ HEIGHT 2) 90) MTS)
              (place-image (rotate 90 BEAR-IMG) (/ WIDTH 2) (/ HEIGHT 2) MTS))

;(define (render-bear-on b img) MTS)

;; Took Template from Bear w/ added atomic parameter
(define (render-bear-on b img)
  (place-image (rotate (modulo (bear-r b) 360) BEAR-IMG) (bear-x b) (bear-y b) img))



;; ListOfBear Integer Integer MouseEvent -> ListOfBear
;; On mouse-click, adds a bear with 0 rotation to the list at the x, y location
(check-expect (handle-mouse empty 5 4 "button-down") (cons (make-bear 5 4 0) empty))
(check-expect (handle-mouse empty 5 4 "move") empty)

;(define (handle-mouse lob x y mev) empty)


;; Templated according to MouseEvent large enumeration.
(define (handle-mouse lob x y mev)
  (cond [(mouse=? mev "button-down") (cons (make-bear x y 0) lob)]
        [else lob]))
