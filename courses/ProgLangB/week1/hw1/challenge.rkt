#lang racket
(define-syntax while-less
  (syntax-rules (do)
    ((while-less x do y)
     (let ([z x])
        (letrec ([loop (lambda ()
                         (let ([w y])
                           (if (or (not (number? w)) (>= w z))
                               #t
                               (loop))))])
          (loop))))))
