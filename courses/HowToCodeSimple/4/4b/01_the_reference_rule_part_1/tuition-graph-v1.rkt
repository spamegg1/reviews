(require 2htdp/image)

;; tuition-graph-starter.rkt  (just the problem statements)
;; tuition-graph-v1.rkt       (includes constants)

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



;; Constants:

(define FONT-SIZE 24)
(define FONT-COLOR "black")

(define Y-SCALE   1/200)
(define BAR-WIDTH 30)
(define BAR-COLOR "lightblue")


;; Data definitions:


;; Functions:
