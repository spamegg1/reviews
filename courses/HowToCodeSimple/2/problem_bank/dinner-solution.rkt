
;; dinner-solution.rkt

;; =================
;; Data definitions:

;
; PROBLEM A:
;
; You are working on a system that will automate delivery for
; YesItCanFly! airlines catering service.
; There are three dinner options for each passenger, chicken, pasta
; or no dinner at all.
;
; Design a data definition to represent a dinner order. Call the type
; DinnerOrder.
;


;; DinnerOrder is one of:
;;  - "No dinner"
;;  - "Chicken"
;;  - "Pasta"
;; interp. "No dinner" means the passenger does not want dinner,
;;         the other values are dinner options
;; <examples are redundant for enumerations>
#;
(define (fn-for-dinner-order d)
  (cond [(string=? d "No dinner") (...)]
        [(string=? d "Chicken") (...)]
        [(string=? d "Pasta") (...)]))

;; Template rules used:
;; - one-of: 3 cases
;; - atomic distinct: "No dinner"
;; - atomic distinct: "Chicken"
;; - atomic distinct: "Pasta"

;; =================
;; Functions:

;
; PROBLEM B:
;
; Design the function dinner-order-to-msg that consumes a dinner order
; and produces a message for the flight attendants saying what the
; passenger ordered.
;
; For example, calling dinner-order-to-msg for a chicken dinner would
; produce "The passenger ordered chicken."
;


;; DinnerOrder -> String
;; produce message to describe what passenger ordered
(check-expect (dinner-order-to-msg "No dinner") "The passenger did not order dinner.")
(check-expect (dinner-order-to-msg "Chicken") "The passenger ordered chicken.")
(check-expect (dinner-order-to-msg "Pasta") "The passenger ordered pasta.")

;(define (dinner-order-to-msg d) "d")  ;stub

; <template from DinnerOrder>

(define (dinner-order-to-msg d)
  (cond [(string=? d "No dinner") "The passenger did not order dinner."]
        [(string=? d "Chicken") "The passenger ordered chicken."]
        [(string=? d "Pasta") "The passenger ordered pasta."]))
