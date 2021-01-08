
;; same-house-as-parent-v2.rkt

;
; PROBLEM:
;
; In the Harry Potter movies, it is very important which of the four houses a
; wizard is placed in when they are at Hogwarts. This is so important that in
; most families multiple generations of wizards are all placed in the same family.
;
; Design a representation of wizard family trees that includes, for each wizard,
; their name, the house they were placed in at Hogwarts and their children. We
; encourage you to get real information for wizard families from:
;    http://harrypotter.wikia.com/wiki/Main_Page
;
; The reason we do this is that designing programs often involves collection
; domain information from a variety of sources and representing it in the program
; as constants of some form. So this problem illustrates a fairly common scenario.
;
; That said, for reasons having to do entirely with making things fit on the
; screen in later videos, we are going to use the following wizard family tree,
; in which wizards and houses both have 1 letter names. (Sigh)
;
;


;; Data definitions:

(define-struct wiz (name house kids))
;; Wizard is (make-wiz String String (listof Wizard))
;; interp. A wizard, with name, house and list of children.

(define Wa (make-wiz "A" "S" empty))
(define Wb (make-wiz "B" "G" empty))
(define Wc (make-wiz "C" "R" empty))
(define Wd (make-wiz "D" "H" empty))
(define We (make-wiz "E" "R" empty))
(define Wf (make-wiz "F" "R" (list Wb)))
(define Wg (make-wiz "G" "S" (list Wa)))
(define Wh (make-wiz "H" "S" (list Wc Wd)))
(define Wi (make-wiz "I" "H" empty))
(define Wj (make-wiz "J" "R" (list We Wf Wg)))
(define Wk (make-wiz "K" "G" (list Wh Wi Wj)))


#; ;template, arb-arity-tree, encapsulated w/ local
(define (fn-for-wiz w)
  (local [(define (fn-for-wiz w)
            (... (wiz-name w)
                 (wiz-house w)
                 (fn-for-low (wiz-kids w))))
          (define (fn-for-low low)
            (cond [(empty? low) (...)]
                  [else
                   (... (fn-for-wiz (first low))
                        (fn-for-low (rest low)))]))]
    (fn-for-wiz w)))


;; Functions:

;
; PROBLEM:
;
; Design a function that consumes a wizard and produces the names of every
; wizard in the tree that was placed in the same house as their immediate
; parent.
;


;
; PROBLEM:
;
; Design a function that consumes a wizard and produces the number of wizards
; in that tree (including the root). Your function should be tail recursive.
;



;
; PROBLEM:
;
; Design a new function definition for same-house-as-parent that is tail
; recursive. You will need a worklist accumulator.
;
;
