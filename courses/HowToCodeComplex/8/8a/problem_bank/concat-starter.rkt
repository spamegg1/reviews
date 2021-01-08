
;; concat-starter.rkt

; Problem:
;
; Given the data definition below, design a function called concat that
; consumes two ListOfString and produces a single list with all the elements
; of lsta preceding lstb.
;
; (concat (list "a" "b" ...) (list "x" "y" ...)) should produce:
;
; (list "a" "b" ... "x" "y" ...)
;
; You are basically going to design the function append using a cross product
; of type comments table. Be sure to simplify your design as much as possible.
;
; Hint: Think carefully about the values of both lists. You might see a way to
; change a cell's content so that 2 cells have the same value.
;


;; =================
;; Data Definitions:

;; ListOfString is one of:
;;  - empty
;;  - (cons String ListOfString)
;; interp. a list of strings
(define LOS1 empty)
(define LOS2 (cons "a" (cons "b" empty)))

;; ==========
;; Functions:
