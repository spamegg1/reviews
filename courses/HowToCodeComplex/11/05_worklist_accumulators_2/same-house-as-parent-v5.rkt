
;; same-house-as-parent-v5.rkt

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


;; Wizard -> (listof String)
;; Produce the names of every descendant in the same house as their parent.
(check-expect (same-house-as-parent Wa) empty)
(check-expect (same-house-as-parent Wh) empty)
(check-expect (same-house-as-parent Wg) (list "A"))
(check-expect (same-house-as-parent Wk) (list "E" "F" "A"))

; template from Wizard plus lost context accumulator
(define (same-house-as-parent w)
  ;; parent-house is String; the house of this wizard's immediate parent ("" for root of tree)
  ;; (same-house-as-parent Wk)
  ;; (fn-for-wiz Wk "")
  ;; (fn-for-wiz Wh "G")
  ;; (fn-for-wiz Wc "S")
  ;; (fn-for-wiz Wd "S")
  ;; (fn-for-wiz Wi "G")
  (local [(define (fn-for-wiz w parent-house)
            (if (string=? (wiz-house w) parent-house)
                (cons (wiz-name w)
                      (fn-for-low (wiz-kids w)
                                  (wiz-house w)))
                (fn-for-low (wiz-kids w)
                            (wiz-house w))))
          (define (fn-for-low low parent-house)
            (cond [(empty? low) empty]
                  [else
                   (append (fn-for-wiz (first low) parent-house)
                           (fn-for-low (rest low)  parent-house))]))]
    (fn-for-wiz w "")))



;
; PROBLEM:
;
; Design a function that consumes a wizard and produces the number of wizards
; in that tree (including the root). Your function should be tail recursive.
;


;; Wizard -> Natural
;; produces the number of wizards in that tree (including the root)
(check-expect (count Wa) 1)
(check-expect (count Wk) 11)

;(define (count w) 0)


;template: from Wizard (arb-arity + local)
;          add result so far accumulator for tail recursion

(define (count w)
  ;; rsf is Natural; the number of wizards seen so far
  ;; (count Wk)
  ;; (fn-for-wiz Wk 0)
  ;; (fn-for-wiz Wh 1)
  ;; (fn-for-wiz Wc 2)
  (local [(define (fn-for-wiz w rsf)
            (fn-for-low (wiz-kids w)
                        (add1 rsf)))
          (define (fn-for-low low rsf)
            (cond [(empty? low) rsf]
                  [else
                   (+ (fn-for-wiz (first low) rsf)
                      (fn-for-low (rest low)  rsf))]))]
    (fn-for-wiz w 0)))


;
; PROBLEM:
;
; Design a new function definition for same-house-as-parent that is tail
; recursive. You will need a worklist accumulator.
;
;
