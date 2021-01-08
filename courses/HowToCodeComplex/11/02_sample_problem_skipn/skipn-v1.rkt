
;; skipn-v1.rkt

;
; PROBLEM:
;
; Design a function that consumes a list of elements lox and a natural number
; n and produces the list formed by including the first element of lox, then
; skipping the next n elements, including an element, skipping the next n
; and so on.
;
;  (skipn (list "a" "b" "c" "d" "e" "f") 2) should produce (list "a" "d")
;


;; (listof X) Natural -> (listof X)
;; produce list containing 1st element of lox, then skip next n, then include...
(check-expect (skipn empty 0) empty)
(check-expect (skipn (list "a" "b" "c" "d" "e" "f") 0) (list "a" "b" "c" "d" "e" "f"))
(check-expect (skipn (list "a" "b" "c" "d" "e" "f") 1) (list "a" "c" "e"))
(check-expect (skipn (list "a" "b" "c" "d" "e" "f") 2) (list "a" "d"))

;(define (skipn lox n) empty) ;stub
;; templated according to (listof X) and accumulator
(define (skipn lox0 n)
  (local [(define (skipn lox acc)
            (cond [(empty? lox) (... acc)]
                  [else
                   (... acc
                        (first lox)
                        (skipn (rest lox)
                               (... acc)))]))]
    (skipn lox0 ...)))
