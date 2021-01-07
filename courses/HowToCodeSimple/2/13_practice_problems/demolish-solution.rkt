
;; demolish-solution.rkt

;; =================
;; Data definitions:

;
; PROBLEM A:
;
; You are assigned to develop a system that will classify
; buildings in downtown Vancouver based on how old they are.
; According to city guidelines, there are three different classification levels:
; new, old, and heritage.
;
; Design a data definition to represent these classification levels.
; Call it BuildingStatus.
;


;; BuildingStatus is one of:
;; - "new"
;; - "old"
;; - "heritage"
;; interp. classification of a building based on age
;; examples redunant in enumeration

#;
(define (fn-for-building-status bs)
  (cond [(string=? "new" bs) (...)]
        [(string=? "old" bs) (...)]
        [(string=? "heritage" bs) (...)]))

;; Template Rules Used:
;; - one of: 3 cases
;; - atomic distinct: "new"
;; - atomic distinct: "old"
;; - atomic distinct: "heritage"

;; =================
;; Functions:

;
; PROBLEM B:
;
; The city wants to demolish all buildings classified as "old".
; You are hired to design a function called demolish?
; that determines whether a building should be torn down or not.
;


;; BuildingStatus -> Boolean
;; produces true if building is "old" and should be demolished
(check-expect (demolish? "new") false)
(check-expect (demolish? "old") true)
(check-expect (demolish? "heritage") false)

; (define (demolish? bs) true)

; <template from BuildingStatus>

(define (demolish? bs)
  (cond [(string=? "new" bs) false]
        [(string=? "old" bs) true]
        [(string=? "heritage" bs) false]))
