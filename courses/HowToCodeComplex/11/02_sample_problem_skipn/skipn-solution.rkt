
;; skipn-solution.rkt

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
  ;; acc: Natural; the number of elements to skip before including the next one
  ;; (skipn (list "a" "b" "c" "d") 2)  ;outer call
  ;;
  ;; (skipn (list "a" "b" "c" "d") 0)  ; include
  ;; (skipn (list     "b" "c" "d") 2)  ; don't include
  ;; (skipn (list         "c" "d") 1)  ; don't include
  ;; (skipn (list             "d") 0)  ; include
  ;; (skipn (list                ) 2)
  (local [(define (skipn lox acc)
            (cond [(empty? lox) empty]
                  [else
                   (if (zero? acc)
                       (cons (first lox)
                             (skipn (rest lox)
                                    n))
                       (skipn (rest lox)
                              (sub1 acc)))]))]
    (skipn lox0 0)))
