
;; compound-evaluation-starter.rkt

; PROBLEM:
;
;
; Given the following definitions:
;
; (define-struct census-data (city population))
;
; (define (count-newborn cd)
;   (make-census-data
;    (census-data-city cd)
;    (add1 (census-data-population cd))))
;
;
; Write down the evaluation steps for the following expression.
;
; (count-newborn (make-census-data "Vancouver" 603502))
;
