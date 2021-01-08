
;; sum-odds-tr-solution.rkt

;
; PROBLEM:
;
; Consider the following function that consumes a list of numbers and produces the sum of
; all the odd numbers in the list.
;
; Use an accumulator to design a tail-recursive version of sum-odds.
;


;; (listof Number) -> Number
;; produce sum of all odd numbers of lon
(check-expect (sum-odds empty) 0)
(check-expect (sum-odds (list 1 2 5 6 11)) 17)
#;
(define (sum-odds lon)
  (cond [(empty? lon) 0]
        [else
         (if (odd? (first lon))
             (+ (first lon)
                (sum-odds (rest lon)))
             (sum-odds (rest lon)))]))

(define (sum-odds lon) (foldl + 0 (filter odd? lon)))
#;
; <template according to (listof Number) + accumulator>
(define (sum-odds lon0)
  ;; acc: Number; the sum of odd numbers of lon0 seen so far
  ;; (sum-odds (list 1 2 3))
  ;;
  ;; (sum-odds (list 1 2 3) 0)
  ;; (sum-odds (list   2 3) 1)
  ;; (sum-odds (list     3) 1)
  ;; (sum-odds (list      ) 4)
  (local [(define (sum-odds lon acc)
            (cond [(empty? lon) acc]
                  [else
                   (if (odd? (first lon))
                       (sum-odds (rest lon)
                                 (+ (first lon) acc))
                       (sum-odds (rest lon) acc))]))]
    (sum-odds lon0 0)))
