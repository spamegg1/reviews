(require 2htdp/image)
(require 2htdp/universe)

;; traffic-light-starter.rkt

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
