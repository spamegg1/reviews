
;; foo-evaluation-starter.rkt

;
; PROBLEM:
;
; Given the following function definition:
;
; (define (foo s)
;   (if (string=? (substring s 0 1) "a")
;       (string-append s "a")
;       s))
;
; Write out the step-by-step evaluation of the expression:
;
; (foo (substring "abcde" 0 3))
;
; Be sure to show every intermediate evaluation step.
;