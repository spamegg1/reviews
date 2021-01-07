
;; even-more-foo-evaluation-solution.rkt

; PROBLEM:
;
; Given the following function definition:
;
; (define (farfle s)
;   (string-append s s))
;
; Write out the step-by-step evaluation of the expression:
;
; (farfle (substring "abcdef" 0 2))
;
; Be sure to show every intermediate evaluation step.
;



(farfle (substring "abcdef" 0 2))

(farfle "ab")

(string-append "ab" "ab")

"abab"