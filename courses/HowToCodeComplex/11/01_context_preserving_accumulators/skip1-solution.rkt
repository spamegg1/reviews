
;; skip1-solution.rkt

;
; PROBLEM:
;
; Design a function that consumes a list of elements and produces the list
; consisting of only the 1st, 3rd, 5th and so on elements of its input.
;
;    (skip1 (list "a" "b" "c" "d")) should produce (list "a" "c")
;


;; (listof X) -> (listof X)
;; produce list consisting of only the 1st, 3rd, 5th... elements of lox
(check-expect (skip1 empty) empty)
(check-expect (skip1 (list "a" "b" "c" "d")) (list "a" "c"))
(check-expect (skip1 (list 1 2 3 4 5 6)) (list 1 3 5))

;(define (skip1 lox) empty);stub

(define (skip1 lox0)
  ;; acc: Natural; 1-based position of (first lox) in lox0
  ;; (skip1 (list "a" "b" "c") 1)
  ;; (skip1 (list     "b" "c") 2)
  ;; (skip1 (list         "c") 3)
  (local [(define (skip1 lox acc)
            (cond [(empty? lox) empty]
                  [else
                   (if (odd? acc)
                       (cons (first lox)
                             (skip1 (rest lox)
                                    (add1 acc)))
                       (skip1 (rest lox)
                              (add1 acc)))]))]

    (skip1 lox0 1)))
