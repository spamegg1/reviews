(require 2htdp/image)

;; tuition-graph-starter.rkt  (just the problem statements)
;; tuition-graph-v1.rkt       (includes constants)
;; tuition-graph-v2.rkt       (includes complete School and ListOfSchool type comment)

;
; PROBLEM:
;
; Eva is trying to decide where to go to university. One important factor for her is
; tuition costs. Eva is a visual thinker, and has taken Systematic Program Design,
; so she decides to design a program that will help her visualize the costs at
; different schools. She decides to start simply, knowing she can revise her design
; later.
;
; The information she has so far is the names of some schools as well as their
; international student tuition costs. She would like to be able to represent that
; information in bar charts like this one:
;
;
;         .
;
; (A) Design data definitions to represent the information Eva has.
; (B) Design a function that consumes information about schools and their
;     tuition and produces a bar chart.
; (C) Design a function that consumes information about schools and produces
;     the school with the lowest international student tuition.
;




(require 2htdp/image)

;; Constants:

(define FONT-SIZE 24)
(define FONT-COLOR "black")

(define Y-SCALE   1/200)
(define BAR-WIDTH 30)
(define BAR-COLOR "lightblue")

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




;; Functions:
