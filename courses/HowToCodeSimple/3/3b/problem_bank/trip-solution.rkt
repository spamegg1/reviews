
;; trip-solution.rkt

;; =================
;; Data definitions:

;
; PROBLEM A:
;
; Design a data definition to help travellers plan their next trip.
; A trip should specify an origin, destination, mode of transport and
; duration (in days).
;


(define-struct trip (origin destination mot duration))
;; Trip is (make-trip String String String Natural)
;; interp. a trip with origin, destination, mode of transport, and duration in days

(define T1 (make-trip "Vancouver" "Cancun" "Flight" 10))
(define T2 (make-trip "Calgary" "Ottawa" "Car" 14))
(define T3 (make-trip "Montreal" "New York" "Flight" 5))

#;
(define (fn-for-trip t)
  (... (trip-origin t)       ;String
       (trip-destination t)  ;String
       (trip-mot t)          ;String
       (trip-duration t)))   ;Natural


;; Template rules used:
;; - compound: 4 fields

;; =================
;; Functions:

;
; PROBLEM B:
;
; You have just found out that you have to use all your days off work
; on your next vacation before they expire at the end of the year.
; Comparing two options for a trip, you want to take the one that
; lasts the longest. Design a function that compares two trips and
; returns the trip with the longest duration.
;
; Note that the rule for templating a function that consumes two
; compound data parameters is for the template to include all
; the selectors for both parameters.
;


;; Trip Trip -> Trip
;; produce the trip with the longer duration, if durations are equal produce the second trip
(check-expect (longer-trip T2 T3) T2)
(check-expect (longer-trip T3 T1) T1)
(check-expect (longer-trip T3 (make-trip "Houston" "Dallas" "Car" 5))
              (make-trip "Houston" "Dallas" "Car" 5))

;(define (longer-trip t1 t2) T1)  ; stub

#;
(define (fn-for-trip t1 t2)
  (... (trip-origin t1)
       (trip-destination t1)
       (trip-mot t1)
       (trip-duration t1)
       (trip-origin t2)
       (trip-destination t2)
       (trip-mot t2)
       (trip-duration t2)))

(define (longer-trip t1 t2)
  (if (> (trip-duration t1) (trip-duration t2))
      t1
      t2))
