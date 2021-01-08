
;; zip-starter.rkt

; Problem:
;
; Given the data definition below, design a function called zip that consumes two
; lists of numbers and produces a list of Entry, formed from the corresponding
; elements of the two lists.
;
; (zip (list 1 2 ...) (list 11 12 ...)) should produce:
;
; (list (make-entry 1 11) (make-entry 2 12) ...)
;
; Your design should assume that the two lists have the same length.
;


;; =================
;; Data Definitions:

(define-struct entry (k v))
;; Entry is (make-entry Number Number)
;; Interp. an entry maps a key to a value
(define E1 (make-entry 3 12))

;; ListOfEntry is one of:
;;  - empty
;;  - (cons Entry ListOfEntry)
;; interp. a list of key value entries
(define LOE1 (list E1 (make-entry 1 11)))

;; ==========
;; Functions:
