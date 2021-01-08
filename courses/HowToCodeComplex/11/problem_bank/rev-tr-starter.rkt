
;; rev-tr-starter.rkt

;
; PROBLEM:
;
; Design a function that consumes (listof X) and produces a list of the same
; elements in the opposite order. Use an accumulator. Call the function rev.
; (DrRacket's version is called reverse.) Your function should be tail recursive.
;
; In this problem only the first step of templating is provided.


;; (listof X) -> (listof X)
;; produce list with elements of lox in reverse order
(check-expect (rev empty) empty)
(check-expect (rev (list 1)) (list 1))
(check-expect (rev (list "a" "b" "c")) (list "c" "b" "a"))

(define (rev lox) empty)
#;
(define (rev lox)                    ;first step of templating
  (cond [(empty? lox) (...)]
        [else
         (... (first lox)
              (rev (rest lox)))]))
