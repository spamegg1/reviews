
;; yell-solution.rkt

;
; PROBLEM:
;
; DESIGN a function called yell that consumes strings like "hello"
; and adds "!" to produce strings like "hello!".
;
; Remember, when we say DESIGN, we mean follow the recipe.
;
; Leave behind commented out versions of the stub and template.
;


;; String -> String
;; add "!" to the end of s
(check-expect (yell "hello") "hello!")
(check-expect (yell "bye") (string-append "bye" "!"))

;(define (yell s)  ;stub
;  "a")

;(define (yell s)  ;template
;  (... s))

(define (yell s)
  (string-append s "!"))
