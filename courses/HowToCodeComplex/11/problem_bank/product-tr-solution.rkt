
;; product-tr-solution.rkt

;
; PROBLEM:
;
; (A) Consider the following function that consumes a list of numbers and produces
;     the product of all the numbers in the list. Use the stepper to analyze the behavior
;     of this function as the list gets larger and larger.
;
; (B) Use an accumulator to design a tail-recursive version of product.
;


;; (listof Number) -> Number
;; produce product of all elements of lon
(check-expect (product empty) 1)
(check-expect (product (list 2 3 4)) 24)
#;
(define (product lon)
  (cond [(empty? lon) 1]
        [else
         (* (first lon)
            (product (rest lon)))]))
; <template according to (listof Number) + accumulator>
(define (product lon0)
  ;; acc: Number; the product of the elements of lon0 seen so far
  ;; (product (list 2 3 4))
  ;;
  ;; (product (list 2 3 4) 1)
  ;; (product (list   3 4) 2)
  ;; (product (list     4) 6)
  ;; (product (list      ) 24)
  (local [(define (product lon acc)
            (cond [(empty? lon) acc]
                  [else
                   (product (rest lon)
                            (* acc (first lon)))]))]
    (product lon0 1)))
