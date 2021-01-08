
;; merge-solution.rkt

; Problem:
;
; Design the function merge. It consumes two lists of numbers, which it assumes are
; each sorted in ascending order. It produces a single list of all the numbers,
; also sorted in ascending order.
;
; Your solution should explicitly show the cross product of type comments table,
; filled in with the values in each case. Your final function should have a cond
; with 3 cases. You can do this simplification using the cross product table by
; recognizing that there are subtly equal answers.
;
; Hint: Think carefully about the values of both lists. You might see a way to
; change a cell content so that 2 cells have the same value.
;


;; ListOfNumber ListOfNumber -> ListOfNumber
;; produce sorted list of numbers by merging two sorted lists of numbers

; CROSS PRODUCT OF TYPE COMMENTS TABLE
;
;       l2                     empty        (cons Number ListOfNumber)
;   l1
; empty                        l2           l2
;
; (cons Number ListOfNumber)   l1           (if (<= (first l1) (first l2))
;                                               (cons (first l1)
;                                                     (merge (rest l1) l2))
;                                               (cons (first l2)
;                                                     (merge l1 (rest l2))))
;
; NOTE: The l2 in the upper left corner of the table was originally
;       empty. But then the table had no simplifications. Thinking
;       about the cases carefully leads to the conclusion that the
;       empty could be replaced by l2, since l2 is in fact empty
;       in that case. Now the table supports simplification.


(check-expect (merge empty empty) empty)
(check-expect (merge empty (list 1 3 5)) (list 1 3 5))
(check-expect (merge (list 0 2 4) empty) (list 0 2 4))
(check-expect (merge (list 0 2 4) (list 1 3)) (list 0 1 2 3 4))

; template taken from cross product table
; 4 cases simplified to 3

(define (merge l1 l2)
  (cond [(empty? l1) l2]
        [(empty? l2) l1]
        [else
         (if (<= (first l1) (first l2))
             (cons (first l1)
                   (merge (rest l1) l2))
             (cons (first l2)
                   (merge l1 (rest l2))))]))
