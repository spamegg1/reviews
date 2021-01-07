;; HtDD Design Quiz

;; Age is Natural
;; interp. the age of a person in years
(define A0 18)
(define A1 25)

#;
(define (fn-for-age a)
  (... a))

;; Template rules used:
;; - atomic non-distinct: Natural


; Problem 1:
;
; Consider the above data definition for the age of a person.
;
; Design a function called teenager? that determines whether a person
; of a particular age is a teenager (i.e., between the ages of 13 and 19).

;; Age -> Boolean
;; produces true if given age is between 13 and 19 (inclusive), false otherwise
(check-expect (teenager? 12) false)
(check-expect (teenager? 13) true)
(check-expect (teenager? 16) true)
(check-expect (teenager? 19) true)
(check-expect (teenager? 20) false)

;(define (teenager? a) false) ;stub

#; ;<used template from Age>
(define (teenager? a)       ;template
  (... a))

(define (teenager? a)
  (<= 13 a 19))


; Problem 2:
;
; Design a data definition called MonthAge to represent a person's age
; in months.


;; MonthAge is Natural
;; interp. the age of a person in months
(define MA0 120)
(define MA1 300)

#;
(define (fn-for-age ma)
  (... ma))

;; Template rules used:
;; - atomic non-distinct: Natural


; Problem 3:
;
; Design a function called months-old that takes a person's age in years
; and yields that person's age in months.
;


;; Age -> MonthAge
;; takes a person's age in years and yields that person's age in months (accurate within 12 months)
(check-expect(months-old 0) 0)
(check-expect(months-old 12) 144)
(check-expect(months-old 25) 300)
(check-expect(months-old 40) 480)
(check-expect(months-old 80) 960)

;(define (months-old a) 0) ;stub
#; ;<used template from Age>
(define (months-old a)
  (... a))

(define (months-old a)
  (* 12 a))



; Problem 4:
;
; Consider a video game where you need to represent the health of your
; character. The only thing that matters about their health is:
;
;   - if they are dead (which is shockingly poor health)
;   - if they are alive then they can have 0 or more extra lives
;
; Design a data definition called Health to represent the health of your
; character.
;
; Design a function called increase-health that allows you to increase the
; lives of a character.  The function should only increase the lives
; of the character if the character is not dead, otherwise the character
; remains dead.


;; Health is one of:
;;  - false
;;  - Natural
;; interp. false means dead, Natural means number of extra lives

(define H1 false)
(define H2 0)
(define H3 5)

#;
(define (fn-for-health h)
  (cond [(false? h) (...)]
        [(and (number? h) (<= 0 h)) (... h)]))
;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: false
;;  - atomic non-distinct: Number

;; ===============
;; Functions

;; Health -> Health
;; increases given health by 1 only if health is not false (otherwise leaves health unchanged)
(check-expect (increase-health false) false)
(check-expect (increase-health 0) 1)
(check-expect (increase-health 5) 6)

;(define (increase-health h) false) ;stub

#; ;<used template from Health>
(define (increase-health h)        ;template
  (cond [(false? h) (...)]
        [(and (number? h) (<= 0 h)) (... h)]))

(define (increase-health h)
  (cond [(false? h) false]
        [(and (number? h) (<= 0 h)) (+ 1 h)]))
