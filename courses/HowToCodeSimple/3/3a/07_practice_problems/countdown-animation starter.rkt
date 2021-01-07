(require 2htdp/image)
(require 2htdp/universe)

;; countdown-animation starter.rkt

;
; PROBLEM:
;
; Design an animation of a simple countdown.
;
; Your program should display a simple countdown, that starts at ten, and
; decreases by one each clock tick until it reaches zero, and stays there.
;
; To make your countdown progress at a reasonable speed, you can use the
; rate option to on-tick. If you say, for example,
; (on-tick advance-countdown 1) then big-bang will wait 1 second between
; calls to advance-countdown.
;
; Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
; Once you are finished the simple version of the program, you can improve
; it by reseting the countdown to ten when you press the spacebar.
;
