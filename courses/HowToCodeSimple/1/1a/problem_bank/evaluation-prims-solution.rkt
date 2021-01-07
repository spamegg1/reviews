
;; evaluation-prims-solution.rkt

;
; PROBLEM:
;
; Write out the step-by-step evaluation for the following expression:
;
; (+ (* 2 3) (/ 8 2))
;

(+ (* 2 3) (/ 8 2))
(+ 6 (/ 8 2))
(+ 6 4)
10

;
; PROBLEM:
;
; Write out the step-by-step evaluation for the following expression:
;
; (* (string-length "foo") 2)
;

(* (string-length "foo") 2)
(* 3 2)
6
