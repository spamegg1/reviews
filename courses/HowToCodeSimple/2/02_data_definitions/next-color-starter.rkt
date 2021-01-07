
;; next-color-starter.rkt

;
;
; Suppose you are working on a program someone else wrote
; that simulates traffic. In the program there are traffic
; lights and cars and streets and things like that. While
; reading the program you come across this function:
;
; (define (next-color c)
;   (cond [(= c 0) 2]
;         [(= c 1) 0]
;         [(= c 2) 1]))
;
; What does it do? The name is a hint, it seems to produce
; the "next color". But its hard to be sure.
;


;
; Surely if the programmer had followed the HtDF recipe
; this would be better wouldn't it? Suppose instead the
; code looked like this.
;
; ;; Natural -> Natural
; ;; produce next color of traffic light
; (check-expect (next-color 0) 2)
; (check-expect (next-color 1) 0)
; (check-expect (next-color 2) 1)
;
; ;(define (next-color c) 0)  ;stub
;
; ;(define (next-color c)     ;template
; ;  (... c))
;
; (define (next-color c)
;   (cond [(= c 0) 2]
;         [(= c 1) 0]
;         [(= c 2) 1]))
;
; That's a little better. At least it is now clear that
; the function does produce the next color. And the tests
; make it clear that the function is really supposed to
; produce 2 when it is called with 0. But what are the
; 0, 1 and 2 about? And what about calling the function
; with 3? The signature says that is OK, but the cond
; in the body will signal an error in that case.
;


;
; ;; Data definitions:
;
; ;; TLColor is one of:
; ;;  - 0
; ;;  - 1
; ;;  - 2
; ;; interp. 0 means red, 1 yellow, 2 green
; #;
; (define (fn-for-tlcolor c)
;   (cond [(= c 0) (...)]
;         [(= c 1) (...)]
;         [(= c 2) (...)]))
;
;
;
; ;; Functions
;
; ;; TLColor -> TLColor
; ;; produce next color of traffic light
; (check-expect (next-color 0) 2)
; (check-expect (next-color 1) 0)
; (check-expect (next-color 2) 1)
;
; ;(define (next-color c) 0)  ;stub
;
; ; Template from TLColor
;
; (define (next-color c)
;   (cond [(= c 0) 2]
;         [(= c 1) 0]
;         [(= c 2) 1]))



;; A small part of a traffic simulation.

;; Data definitions:

;; TLColor is one of:
;;  - "red"
;;  - "yellow"
;;  - "green"
;; interp. "red" means red, "yellow" yellow, "green" green
#;
(define (fn-for-tlcolor c)
  (cond [(string=? c "red") (...)]
        [(string=? c "yellow") (...)]
        [(string=? c "green") (...)]))



;; Functions

;; TLColor -> TLColor
;; produce next color of traffic light
(check-expect (next-color "red") "green")
(check-expect (next-color "yellow") "red")
(check-expect (next-color "green") "yellow")

;(define (next-color c) "red")  ;stub

; Template from TLColor

(define (next-color c)
  (cond [(string=? c "red")    "green"]
        [(string=? c "yellow") "red"]
        [(string=? c "green")  "yellow"]))
