(require 2htdp/image)
(require 2htdp/universe)
;; Growing Flowers

;; ===========
;; Constants
(define WIDTH  800)
(define HEIGHT 600)
(define MAXSIZE 15)
(define CTR-X (/ WIDTH 2))
(define CTR-Y (/ HEIGHT 2))
(define MTS (rectangle WIDTH HEIGHT "solid" "white"))

(define CRAD 2)
(define CMODE "solid")
(define CCOLOR "yellow")
(define PRAD1 15)
(define PRAD2 4)
(define PMODE "solid")
(define PCOLOR "purple")
(define PANGLE 60)

(define PETAL (ellipse PRAD1 PRAD2 PMODE PCOLOR))
(define FLOWER (overlay (circle CRAD CMODE CCOLOR)
                        PETAL
                        (rotate PANGLE PETAL)
                        (rotate (* 2 PANGLE) PETAL)))

;; ============
;; Data Definitions

(define-struct fl (x y s))
;; Flower is (make-fl Natural[0-WIDTH] Natural[0-HEIGHT] Natural[1-MAXSIZE])
;; interp. The position of a growing flower
;;         x is the x-coordinate in pixels
;;         y is the y-coordinate in pixels
;;         s is the size multiplier as flower grows

(define F1 (make-fl CTR-X CTR-Y 1))
(define F2 (make-fl 100 50 10))
(define F3 (make-fl 300 150 3))
#;
(define (fn-for-flower f)
  (... (fl-x f)
       (fl-y f)
       (fl-s f)))

;; Template rules used:
;; - compound: 3 fields

;; ===========
;; Functions

;; Flower -> Flower
;; run the animation, starting with initial flower f.
;; Start with (main F1)
;; <no tests for main functions>

(define (main f)
  (big-bang f
            (on-tick next-fl)      ; Flower -> Flower
            (to-draw render-fl)      ; Flower -> Image
            (on-mouse handle-mouse))); Flower MouseEvent -> Flower

;; Flower -> Flower
;; grow flower by increasing s by 1 (unless s is already MAXSIZE), enlarging the image
(check-expect (next-fl F1)
              (make-fl (fl-x F1) (fl-y F1)
                       (cond [(= (fl-s F1) MAXSIZE) MAXSIZE]
                             [else (+ 1 (fl-s F1))])))
(check-expect (next-fl F2)
              (make-fl (fl-x F2) (fl-y F2)
                       (cond [(= (fl-s F2) MAXSIZE) MAXSIZE]
                             [else (+ 1 (fl-s F2))])))
(check-expect (next-fl F3)
              (make-fl (fl-x F3) (fl-y F3)
                       (cond [(= (fl-s F3) MAXSIZE) MAXSIZE]
                             [else (+ 1 (fl-s F3))])))

;(define (next-fl f) f)  ;stub
; Template from Flower

(define (next-fl f)
  (make-fl (fl-x f) (fl-y f)
           (cond [(= (fl-s f) MAXSIZE) MAXSIZE]
                 [else (+ 1 (fl-s f))])))

;; Flower -> Image
;; Produces the flower at pos fl-x and fl-y,
;; with size multiplier s, on the MTS
(check-expect (render-fl F1)
              (place-image
                   (create-flower (fl-s F1))
                   (fl-x F1)
                   (fl-y F1)
                   MTS))
(check-expect (render-fl F2)
              (place-image
                   (create-flower (fl-s F2))
                   (fl-x F2)
                   (fl-y F2)
                   MTS))
(check-expect (render-fl F3)
              (place-image
                   (create-flower (fl-s F3))
                   (fl-x F3)
                   (fl-y F3)
                   MTS))

; (define (render-fl f) MTS) ;stub

; Template from Flower

(define (render-fl f)
  (place-image
        (create-flower (fl-s f))
        (fl-x f)
        (fl-y f)
        MTS))

;; Natural[1-MAXSIZE] -> Image
;; helper function to create flower image
(check-expect (create-flower (fl-s F1))
  (overlay (circle (* (fl-s F1) CRAD) CMODE CCOLOR)
           (ellipse (* (fl-s F1) PRAD1) (* (fl-s F1) PRAD2) PMODE PCOLOR)
           (rotate PANGLE (ellipse (* (fl-s F1) PRAD1) (* (fl-s F1) PRAD2) PMODE PCOLOR))
           (rotate (* 2 PANGLE) (ellipse (* (fl-s F1) PRAD1) (* (fl-s F1) PRAD2) PMODE PCOLOR))))
(check-expect (create-flower (fl-s F2))
  (overlay (circle (* (fl-s F2) CRAD) CMODE CCOLOR)
           (ellipse (* (fl-s F2) PRAD1) (* (fl-s F2) PRAD2) PMODE PCOLOR)
           (rotate PANGLE (ellipse (* (fl-s F2) PRAD1) (* (fl-s F2) PRAD2) PMODE PCOLOR))
           (rotate (* 2 PANGLE) (ellipse (* (fl-s F2) PRAD1) (* (fl-s F2) PRAD2) PMODE PCOLOR))))
(check-expect (create-flower (fl-s F3))
  (overlay (circle (* (fl-s F3) CRAD) CMODE CCOLOR)
           (ellipse (* (fl-s F3) PRAD1) (* (fl-s F3) PRAD2) PMODE PCOLOR)
           (rotate PANGLE (ellipse (* (fl-s F3) PRAD1) (* (fl-s F3) PRAD2) PMODE PCOLOR))
           (rotate (* 2 PANGLE) (ellipse (* (fl-s F3) PRAD1) (* (fl-s F3) PRAD2) PMODE PCOLOR))))

;(define (create-flower s) FLOWER) ;stub
; <no template for helper function>
(define (create-flower s)
  (overlay (circle (* s CRAD) CMODE CCOLOR)
           (ellipse (* s PRAD1) (* s PRAD2) PMODE PCOLOR)
           (rotate PANGLE (ellipse (* s PRAD1) (* s PRAD2) PMODE PCOLOR))
           (rotate (* 2 PANGLE) (ellipse (* s PRAD1) (* s PRAD2) PMODE PCOLOR))))


;; Flower Integer Integer MouseEvent -> Flower
;; produce flower with s=1 to be rendered at x,y location
;; if me is button-down, otherwise produce f
(check-expect (handle-mouse F1 100 30 "button-down") (make-fl 100 30 1))
(check-expect (handle-mouse F2 200 100 "button-up") F2)
(check-expect (handle-mouse F3 250 30 "button-down") (make-fl 250 30 1))
(check-expect (handle-mouse F3 9 2 "drag") F3)

#;
(define (handle-mouse f x y me) f)  ;stub

;<template from MouseEvent>
(define (handle-mouse f x y me)
  (cond [(mouse=? me "button-down") (make-fl x y 1)]
        [else f]))
