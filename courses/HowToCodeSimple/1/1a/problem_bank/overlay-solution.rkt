(require 2htdp/image)

;; overlay-solution.rkt

;
; PROBLEM:
;
; Write an expression that uses star and overlay to produce an image similar to this:
;
;                                   .
; You can consult the DrRacket help desk for information on how to use star and overlay.
; Don't worry about the exact size of the stars.
;


(overlay (star 15 "solid" "blue")
         (star 30 "solid" "yellow")
         (star 45 "solid" "blue"))
