
;; summon-solution.rkt

;
; PROBLEM:
;
; Design a function that generates a summoning charm. For example:
;
;    (summon "Firebolt") should produce "accio Firebolt"
;    (summon "portkey")  should produce "accio portkey"
;    (summon "broom")    should produce "accio broom"
;
; See http://harrypotter.wikia.com/wiki/Summoning_Charm for background on
; summoning charms.
;
; Remember, when we say DESIGN, we mean follow the recipe.
;
; Follow the HtDF recipe and leave behind commented out versions of the stub and template.
;


;; String -> String
;; prepend "accio " to the start of s
(check-expect (summon "Firebolt") "accio Firebolt")
(check-expect (summon "portkey") (string-append "accio " "portkey"))

;(define (summon s)  ;stub
;  "")

;(define (summon s)  ;template
;  (... s))

(define (summon s)
  (string-append "accio " s))