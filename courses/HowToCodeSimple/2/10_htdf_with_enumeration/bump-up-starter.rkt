
;; bump-up-starter.rkt

;
; PROBLEM:
;
; Using the LetterGrade data definition below design a function that
; consumes a letter grade and produces the next highest letter grade.
; Call your function bump-up.
;



;; Data definitions:

;; LetterGrade is one of:
;;  - "A"
;;  - "B"
;;  - "C"
;; interp. the letter grade in a course
;; <examples are redundant for enumerations>
#;
(define (fn-for-letter-grade lg)
  (cond [(string=? lg "A") (...)]
        [(string=? lg "B") (...)]
        [(string=? lg "C") (...)]))

;; Template rules used:
;;  one-of: 3 cases
;;  atomic distinct: "A"
;;  atomic distinct: "B"
;;  atomic distinct: "C"


;; Functions:
