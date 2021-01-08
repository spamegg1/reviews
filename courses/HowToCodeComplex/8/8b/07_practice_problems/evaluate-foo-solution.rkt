
;; evaluate-foo-solution.rkt

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


;  EVALUATE: (foo 3)


(local [(define x (* 3 3))]
  (if (even? x)
      3
      (+ 3 (foo (sub1 3)))))

(define x_0 (* 3 3))
(if (even? x_0)
    3
    (+ 3 (foo (sub1 3))))


#; ; commented out so DrRacket doesn't complain
(define x_0 9)
(if (even? x_0)
    3
    (+ 3 (foo (sub1 3))))

(if (even? 9)
    3
    (+ 3 (foo (sub1 3))))

(if false
    3
    (+ 3 (foo (sub1 3))))

(+ 3 (foo (sub1 3)))
(+ 3 (foo 2))
(+ 3 (local [(define x (* 3 2))]
       (if (even? x)
           2
           (+ 2 (foo (sub1 2))))))

(define x_1 (* 3 2))
(+ 3 (if (even? x_1)
         2
         (+ 2 (foo (sub1 2)))))

#; ; Again, comment out so DrRacket doesn't complain
(define x_1 6)
(+ 3 (if (even? x_1)
         2
         (+ 2 (foo (sub1 2)))))

(+ 3 (if (even? 6)
         2
         (+ 2 (foo (sub1 2)))))

(+ 3 (if true
         2
         (+ 2 (foo (sub1 2)))))

(+ 3 2)

5
