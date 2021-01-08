
;; dropn-solution.rkt

;
; PROBLEM:
;
; Design a function that consumes a list of elements lox and a natural number
; n and produces the list formed by dropping every nth element from lox.
;
; (dropn (list 1 2 3 4 5 6 7) 2) should produce (list 1 2 4 5 7)
;


;; (listof X) Natural -> (listof X)
;; produce list formed by dropping every nth element from lox
(check-expect (dropn empty 0) empty)
(check-expect (dropn (list "a" "b" "c" "d" "e" "f") 0) empty)
(check-expect (dropn (list "a" "b" "c" "d" "e" "f") 1) (list "a" "c" "e"))
(check-expect (dropn (list "a" "b" "c" "d" "e" "f") 2) (list "a" "b" "d" "e"))

;(define (dropn lox n) empty) ;stub

;; templated according to (listof X) and accumulator

(define (dropn lox0 n)
  ;; acc: Natural; the number of elements to keep before dropping the next one
  ;; (dropn (list "a" "b" "c" "d") 2)  ;outer call
  ;;
  ;; (dropn (list "a" "b" "c" "d") 2)  ; keep
  ;; (dropn (list     "b" "c" "d") 1)  ; keep
  ;; (dropn (list         "c" "d") 0)  ; drop
  ;; (dropn (list             "d") 2)  ; keep
  ;; (dropn (list                ) 1)  ; keep
  (local [(define (dropn lox acc)
            (cond [(empty? lox) empty]
                  [else
                   (if (zero? acc)
                       (dropn (rest lox) n)
                       (cons (first lox)
                             (dropn (rest lox)
                                    (sub1 acc))))]))]
    (dropn lox0 n)))
