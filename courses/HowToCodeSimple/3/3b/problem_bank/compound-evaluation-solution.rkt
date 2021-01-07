
;; compound-evaluation-solution.rkt

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


; (count-newborn (make-census-data "Vancouver" 603502))
;
; (make-census-data
;  (census-data-city (make-census-data "Vancouver" 603502))
;  (add1 (census-data-population (make-census-data "Vancouver" 603502))))
;
; (make-census-data
;  "Vancouver"
;  (add1 (census-data-population (make-census-data "Vancouver" 603502))))
;
; (make-census-data
;  "Vancouver"
;  (add1 603502))
;
; (make-census-data "Vancouver" 603503)
;
