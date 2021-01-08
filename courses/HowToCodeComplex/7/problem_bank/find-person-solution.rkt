
;; find-person-solution.rkt

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


;; String Person -> Natural or false
;; String ListOfPerson -> Natural or false
;; search the given tree for a person with the given name, produce age if found; false otherwise
(check-expect (find--lop "N1" empty) false)
(check-expect (find--person "N2" P1) false)
(check-expect (find--person "N1" P1) 5)
(check-expect (find--lop "N3" (cons P1 (cons P2 (cons P3 empty)))) 15)
(check-expect (find--lop "N4" (cons P1 (cons P2 (cons P3 empty)))) false)
(check-expect (find--person "N1" P2) 5)
(check-expect (find--person "N3" P2) false)
(check-expect (find--person "N2" P4) 25)
(check-expect (find--person "N1" P4) 5)

;<templates taken from Person and ListOfPerson>

(define (find--person n p)
  (if (string=? (person-name p) n)
      (person-age p)
      (find--lop n (person-kids p))))

(define (find--lop n lop)
  (cond [(empty? lop) false]
        [else
         (if (not (false? (find--person n (first lop))))
             (find--person n (first lop))
             (find--lop n (rest lop)))]))
