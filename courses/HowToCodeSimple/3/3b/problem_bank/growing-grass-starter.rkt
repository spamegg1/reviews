(require 2htdp/image)
(require 2htdp/universe)

;; growing-grass-starter.rkt

;
; PROBLEM:
;
; Design a world program as follows:
;
; The world starts off with a piece of grass waiting to grow. As time passes,
; the grass grows upwards. Pressing any key cuts the current strand of
; grass to 0, allowing a new piece to grow to the right of it.
;
; Starting display:
;
; .
;
; After a few seconds:
;
; .
;
; After a few more seconds:
;
; .
;
; Immediately after pressing any key:
;
; .
;
; A few more seconds after pressing any key:
;
; .
;
; NOTE 1: Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
