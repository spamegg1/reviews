(require 2htdp/image)
(require 2htdp/universe)

;; cantor-starter.rkt

;
; PROBLEM:
;
; A Cantor Set is another fractal with a nice simple geometry.
; The idea of a Cantor set is to have a bar (or rectangle) of
; a certain width w, then below that are two recursive calls each
; of 1/3 the width, separated by a whitespace of 1/3 the width.
;
; So this means that the
;   width of the whitespace   wc  is  (/ w 3)
;   width of recursive calls  wr  is  (/ (- w wc) 2)
;
; To make it look better a little extra whitespace is put between
; the bars.
;
;
; Here are a couple of examples (assuming a reasonable CUTOFF)
;
; (cantor CUTOFF) produces:
;
; .
;
; (cantor (* CUTOFF 3)) produces:
;
; .
;
; And that keeps building up to something like the following. So
; as it goes it gets wider and taller of course.
;
; .
;
;
; PROBLEM A:
;
; Design a function that consumes a width and produces a cantor set of
; the given width.
;
;
; PROBLEM B:
;
; Add a second parameter to your function that controls the percentage
; of the recursive call that is white each time. Calling your new function
; with a second argument of 1/3 would produce the same images as the old
; function.
;
; PROBLEM C:
;
; Now you can make a fun world program that works this way:
;   The world state should simply be the most recent x coordinate of the mouse.
;
;   The to-draw handler should just call your new cantor function with the
;   width of your MTS as its first argument and the last x coordinate of
;   the mouse divided by that width as its second argument.
;
;
;
