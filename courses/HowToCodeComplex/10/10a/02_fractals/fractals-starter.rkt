
(require 2htdp/image)

;; fractals-starter.rkt

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



;
; PROBLEM:
;
; Design a function to produce a Sierpinski carpet of size s.
;
; Here is an example of a larger Sierpinski carpet.
;
; .
;
