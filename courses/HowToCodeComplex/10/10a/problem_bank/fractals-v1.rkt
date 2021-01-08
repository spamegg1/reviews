
(require 2htdp/image)

;; fractals-v1.rkt

;
; PROBLEM:
;
; Design a function that consumes a number and produces a Sierpinski
; triangle of that size. Your function should use generative recursion.
;
; One way to draw a Sierpinski triangle is to:
;
;  - start with an equilateral triangle with side length s
;
;      .
;
;  - inside that triangle are three more Sierpinski triangles
;      .
;
;  - and inside each of those... and so on
;
; So that you end up with something that looks like this:
;
;
;
;
; .
;
; Note that in the 2nd picture above the inner triangles are drawn in
; black and slightly smaller just to make them clear. In the real
; Sierpinski triangle they should be in the same color and of side
; length s/2. Also note that the center upside down triangle is not
; an explicit triangle, it is simply formed from the other triangles.
;
;


(define CUTOFF 2)


;; Number -> Image
;; produce a Sierpinski Triangle of the given size
(check-expect (stri (/ CUTOFF 2))
              (triangle (/ CUTOFF 2) "outline" "red"))
(check-expect (stri CUTOFF) (triangle CUTOFF "outline" "red"))
(check-expect (stri (* CUTOFF 2))
              (overlay (triangle (* 2 CUTOFF) "outline" "red")
                       (local [(define sub (triangle CUTOFF "outline" "red"))]
                         (above           sub
                                (beside sub sub)))))
(check-expect (stri (* CUTOFF 4))
              (overlay (triangle (* 4 CUTOFF) "outline" "red")
                       (local [(define sub (stri (* CUTOFF 2)))]
                         (above           sub
                                (beside sub sub)))))


;(define (stri s)              ;stub
;  (square 0 "solid" "white"))

#;
(define (genrec-fn d)          ;template
  (if (trivial? d)
      (trivial-answer d)
      (... d
           (genrec-fn (next-problem d)))))


(define (stri s)
  (if (<= s CUTOFF)
      (triangle s "outline" "red")
      (overlay (triangle s "outline" "red")
               (local [(define sub (stri (/ s 2)))]
                 (above sub
                        (beside sub sub))))))






;
; PROBLEM:
;
; Design a function to produce a Sierpinski carpet of size s.
;
; Here is an example of a larger Sierpinski carpet.
;
; .
;


;; Number -> Image
;; produce Sierpinski carpet of given size
(check-expect (scarpet (/ CUTOFF 3)) (square (/ CUTOFF 3) "outline" "red"))
(check-expect (scarpet CUTOFF) (square CUTOFF "outline" "red"))
(check-expect (scarpet (* CUTOFF 3))
              (overlay (square (* CUTOFF 3) "outline" "red")
                       (local [(define sub (square CUTOFF "outline" "red"))
                               (define blk (square CUTOFF "solid" "white"))]
                         (above (beside sub sub sub)
                                (beside sub blk sub)
                                (beside sub sub sub)))))
(check-expect (scarpet (* CUTOFF 9))
              (overlay (square (* CUTOFF 9) "outline" "red")
                       (local [(define sub (scarpet (* CUTOFF 3)))
                               (define blk
                                 (square (* CUTOFF 3) "solid" "white"))]
                         (above (beside sub sub sub)
                                (beside sub blk sub)
                                (beside sub sub sub)))))

;(define (scarpet s) (square 0 "solid" "white"))

(define (scarpet s)
  (if (<= s CUTOFF)
      (square s "outline" "red")
      (overlay (square s "outline" "red")
               (local [(define sub (scarpet (/ s 3)))
                       (define blk (square (/ s 3) "solid" "white"))]
                 (above (beside sub sub sub)
                        (beside sub blk sub)
                        (beside sub sub sub))))))
