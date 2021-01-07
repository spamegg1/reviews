
;; student-solution.rkt

;; =================
;; Data definitions:

;
; PROBLEM A:
;
; Design a data definition to help a teacher organize their next field trip.
; On the trip, lunch must be provided for all students. For each student, track
; their name, their grade (from 1 to 12), and whether or not they have allergies.
;


(define-struct student (name grade allergies?))
;; Student is (make-student String Natural[1, 12] Boolean)
;; interp. a student with a name, in grade 1-12, and true if they have allergies

(define S1 (make-student "Bob" 2 true))
(define S2 (make-student "Hannah" 5 false))

#;
(define (fn-for-student s)
  (... (student-name s)         ;String
       (student-grade s)        ;Natural[1, 12]
       (student-allergies? s))) ;Boolean

;; Template rules used:
;; - compound: 3 fields

;; =================
;; Functions:

;
; PROBLEM B:
;
; To plan for the field trip, if students are in grade 6 or below, the teacher
; is responsible for keeping track of their allergies. If a student has allergies,
; and is in a qualifying grade, their name should be added to a special list.
; Design a function to produce true if a student name should be added to this list.
;


;; Student -> Boolean
;; produce true if the given student is at or below grade 6 and has allergies
(check-expect (add-name? S1) true)
(check-expect (add-name? S2) false)
(check-expect (add-name? (make-student "Joanne" 10 true)) false)
(check-expect (add-name? (make-student "John" 11 false)) false)

;(define (add-name? s) true)   ; stub
; Template from Student

(define (add-name? s)
  (and (<= (student-grade s) 6)
       (student-allergies? s)))
