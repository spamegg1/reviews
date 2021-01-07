
;; ensure-question-solution.rkt

;
; PROBLEM:
;
; Use the How to Design Functions (HtDF) recipe to design a function that consumes a string, and adds "?"
; to the end unless the string already ends in "?".
;
; For this question, assume the string has length > 0. Follow the HtDF recipe and leave behind commented
; out versions of the stub and template.
;


;; String -> String
;; add ? to end of str unless it already ends in ?
(check-expect (ensure-question "Hello") "Hello?")
(check-expect (ensure-question "OK?") "OK?")

;(define (ensure-question str) ;stub
;  "str")

;(define (ensure-question str) ; template
;  (... str))

(define (ensure-question str)
  (if (string=? (substring str (- (string-length str) 1)) "?")
      str
      (string-append str "?")))
