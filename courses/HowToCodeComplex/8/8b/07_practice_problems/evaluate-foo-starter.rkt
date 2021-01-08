
;; evaluate-foo-starter.rkt

;
; PROBLEM:
;
; Hand step the evaluation of (foo 3) given the definition of foo below.
; We know that you can use the stepper to check your work - please go
; ahead and do that AFTER you try hand stepping it yourself.
;
; (define (foo n)
;   (local [(define x (* 3 n))]
;     (if (even? x)
;         n
;         (+ n (foo (sub1 n))))))
;
