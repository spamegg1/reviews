
;; evaluate-boo-starter.rkt

;
; Given the following function definition:
;
; (define (boo x lon)
;   (local [(define (addx n) (+ n x))]
;     (if (zero? x)
;         empty
;         (cons (addx (first lon))
;               (boo (sub1 x) (rest lon))))))
;



;
; PROBLEM A:
;
; What is the value of the following expression:
;
; (boo 2 (list 10 20))
;
; NOTE: We are not asking you to show the full step-by-step evaluation for
; this problem, but you may want to sketch it out to help you get these
; questions right.
;



;
; PROBLEM B:
;
; How many function definitions are lifted during the evaluation of the
; expression in part A.
;



;
; PROBLEM C:
;
; Write out the lifted function definition(s). Just the actual lifted function
; definitions.
;
