
;; style-rules-starter.rkt

; PROBLEM:
;
; You're redesigning the SeatNum data definition from lecture, and you're not
; sure if you've done it correctly. When you ask a TA for feedback, she tells
; you that you haven't followed our style rules and she asks you to re-format
; your data definition before she gives you feedback.
;
; a) Why is it important to follow style rules?
;
; b) Fix the data definition below so that it follows our style rules. Be sure to
; consult the style rules page so that you make ALL the required changes, of which
; there are quite a number.


;seatnum is a natural[1,32]
;interp. The number of a seat in a row.
;Seats 1 and 32 are aisle seats.
(define sn1 1)
(define sn2 32)
#;
(define (fn-sn x)
  (... x))
;; template rules used: natural[1,32]
