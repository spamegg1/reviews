
;; area-solution.rkt

;
; PROBLEM:
;
; DESIGN a function called area that consumes the length of one
; side of a square and produces the area of the square.
;
; Remember, when we say DESIGN, we mean follow the recipe.
;
; Leave behind commented out versions of the stub and template.
;


;; Natural -> Natural
;; produce area of square with side length s
(check-expect (area 2) (* 2 2))
(check-expect (area 4) (* 4 4))

;(define (area s) ; stub
;  2)

;(define (area s) ; template
;  (... s))

(define (area s)
  (* s s))
