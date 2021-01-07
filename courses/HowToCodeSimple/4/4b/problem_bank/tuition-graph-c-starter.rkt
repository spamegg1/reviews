(require 2htdp/image)

;; tuition-graph-c-starter.rkt

;
; Remember the constants and data definitions we created in lectures 5h-j
; to help Eva decide where to go to university:
;


;; =================
;; Constants:

(define FONT-SIZE 24)
(define FONT-COLOR "black")

(define Y-SCALE   1/200)
(define BAR-WIDTH 30)
(define BAR-COLOR "lightblue")

;; =================
;; Data definitions:

(define-struct school (name tuition))
;; School is (make-school String Natural)
;; interp. name is the school's name, tuition is international-students tuition in USD

(define S1 (make-school "School1" 27797)) ;We encourage you to look up real schools
(define S2 (make-school "School2" 23300)) ;of interest to you -- or any similar data.
(define S3 (make-school "School3" 28500)) ;

(define (fn-for-school s)
  (... (school-name s)
       (school-tuition s)))

;; Template rules used:
;;  - compound: (make-school String Natural)


;; ListOfSchool is one of:
;;  - empty
;;  - (cons School ListOfSchool)
;; interp. a list of schools
(define LOS1 empty)
(define LOS2 (cons S1 (cons S2 (cons S3 empty))))

(define (fn-for-los los)
  (cond [(empty? los) (...)]
        [else
         (... (fn-for-school (first los))
              (fn-for-los (rest los)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons School ListOfSchool)
;;  - reference: (first los) is School
;;  - self-reference: (rest los) is ListOfSchool


;; ListOfNumber is one of:
;;  - empty
;;  - (cons Number ListOfNumber)
;; interp. a list of numbers
(define LON1 empty)
(define LON2 (cons 60 (cons 42 empty)))
#;
(define (fn-for-lon lon)
  (cond [(empty? lon) (...)]
        [else
         (... (first lon)
              (fn-for-lon (rest lon)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons Number ListOfNumber)
;;  - self-reference: (rest lon) is ListOfNumber

;; =================
;; Functions:

;
; PROBLEM A:
;
; Complete problem (C) from the reference rule videos.
;
; "Design a function that consumes information about schools and produces
; the school with the lowest international student tuition."
;
; The function should consume a ListOfSchool. Call it cheapest.
;
; ;; ASSUME the list includes at least one school or else
; ;;        the notion of cheapest doesn't make sense
;
; Also note that the template for a function that consumes a non-empty
; list is:
;
; (define (fn-for-nelox nelox)
;   (cond [(empty? (rest nelox)) (...  (first nelox))]
;         [else
;           (... (first nelox)
;                (fn-for-nelox (rest nelox)))]))
;
; And the template for a function that consumes two schools is:
;
; (define (fn... s1 s2)
; (... (school-name s1)
;      (school-tuition s1)
;      (school-name s2)
;      (school-tuition s2)))
;



;
; PROBLEM B:
;
; Design a function that consumes a ListOfSchool and produces a list of the
; school names. Call it get-names.
;
;  Do you need to define a new helper function?
;
