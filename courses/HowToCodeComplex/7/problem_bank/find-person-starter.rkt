
;; find-person-starter.rkt

;
; The following program implements an arbitrary-arity descendant family
; tree in which each person can have any number of children.
;
; PROBLEM A:
;
; Decorate the type comments with reference arrows and establish a clear
; correspondence between template function calls in the templates and
; arrows in the type comments.
;


;; Data definitions:

(define-struct person (name age kids))

;; Person is (make-person String Natural ListOfPerson)
;; interp. A person, with first name, age and their children
(define P1 (make-person "N1" 5 empty))
(define P2 (make-person "N2" 25 (list P1)))
(define P3 (make-person "N3" 15 empty))
(define P4 (make-person "N4" 45 (list P3 P2)))

(define (fn-for-person p)
  (... (person-name p)			;String
       (person-age p)			;Natural
       (fn-for-lop (person-kids p))))


;; ListOfPerson is one of:
;;  - empty
;;  - (cons Person ListOfPerson)
;; interp. a list of persons
#;
(define (fn-for-lop lop)
  (cond [(empty? lop) (...)]
        [else
         (... (fn-for-person (first lop))
              (fn-for-lop (rest lop)))]))


;; Functions:

; PROBLEM B:
;
; Design a function that consumes a Person and a String. The function
; should search the entire tree looking for a person with the given
; name. If found the function should produce the person's age. If not
; found the function should produce false.
