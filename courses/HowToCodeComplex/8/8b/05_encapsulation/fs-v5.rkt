(require 2htdp/image)

;; fs-starter.rkt (type comments and examples)
;; fs-v1.rkt (complete data-definition plus function problems)
;; fs-v2.rkt (complete data-definition and sum-data function)
;; fs-v3.rkt (complete data-definition, sum-data function and all-names function)
;; fs-v4.rkt (complete data-definition, sum-data, all-names and find functions)
;; fs-v5.rkt (data-definition, encapsulated template, encapsulated functions)

;; Data definitions:

(define-struct elt (name data subs))
;; Element is (make-elt String Integer ListOfElement)
;; interp. An element in the file system, with name, and EITHER data or subs.
;;         If data is 0, then subs is considered to be list of sub elements.
;;         If data is not 0, then subs is ignored.

;; ListOfElement is one of:
;;  - empty
;;  - (cons Element ListOfElement)
;; interp. A list of file system Elements

; .

(define F1 (make-elt "F1" 1 empty))
(define F2 (make-elt "F2" 2 empty))
(define F3 (make-elt "F3" 3 empty))
(define D4 (make-elt "D4" 0 (list F1 F2)))
(define D5 (make-elt "D5" 0 (list F3)))
(define D6 (make-elt "D6" 0 (list D4 D5)))
#;
(define (fn-for-element e)
  (local [(define (fn-for-element e)
            (... (elt-name e)    ;String
                 (elt-data e)    ;Integer
                 (fn-for-loe (elt-subs e))))

          (define (fn-for-loe loe)
            (cond [(empty? loe) (...)]
                  [else
                   (... (fn-for-element (first loe))
                        (fn-for-loe (rest loe)))]))]

    (fn-for-element e)))


;; Functions:

;
; PROBLEM
;
; Design a function that consumes Element and produces the sum of all the file data in
; the tree.
;

;; Element -> Integer
;; produce the sum of all the data in element (and its subs)
(check-expect (sum-data F1) 1)
(check-expect (sum-data D5) 3)
(check-expect (sum-data D4) (+ 1 2))
(check-expect (sum-data D6) (+ 1 2 3))

; re-developed with encapsulated templates
(define (sum-data e)
  (local [(define (fn-for-element e)
            (+ (elt-data e)
               (fn-for-loe (elt-subs e))))

          (define (fn-for-loe loe)
            (cond [(empty? loe) 0]
                  [else
                   (+ (fn-for-element (first loe))
                      (fn-for-loe (rest loe)))]))]

    (fn-for-element e)))

#;
(define (sum-data e)
  (local [(define (sum-data--element e)
            (if (zero? (elt-data e))
                (sum-data--loe (elt-subs e))
                (elt-data e)))

          (define (sum-data--loe loe)
            (cond [(empty? loe) 0]
                  [else
                   (+ (sum-data--element (first loe))
                      (sum-data--loe (rest loe)))]))]

    (sum-data--element e)))

;
; PROBLEM
;
; Design a function that consumes Element and produces a list of the names of all the elements in
; the tree.
;

;; Element       -> ListOfString
;; produce list of the names of all the elements in the tree
(check-expect (all-names F1) (list "F1"))
(check-expect (all-names D5) (list "D5" "F3"))
(check-expect (all-names D4) (list "D4" "F1" "F2"))
(check-expect (all-names D6) (list "D6" "D4" "F1" "F2" "D5" "F3"))
(check-expect (all-names D6) (cons "D6"  (append (list "D4" "F1" "F2") (list "D5" "F3"))))

;(define (all-names e) empty) ;stub

(define (all-names e)
  (local [(define (all-names--element e)
            (cons (elt-name e)
                  (all-names--loe (elt-subs e))))

          (define (all-names--loe loe)
            (cond [(empty? loe) empty]
                  [else
                   (append (all-names--element (first loe))
                           (all-names--loe (rest loe)))]))]

    (all-names--element e)))

;
; PROBLEM
;
; Design a function that consumes String and Element and looks for a data element with the given
; name. If it finds that element it produces the data, otherwise it produces false.
;


;; String Element -> Integer or false
;; search the given tree for an element with the given name, produce data if found; false otherwise
(check-expect (find "F3" F1) false)
(check-expect (find "F3" F3) 3)
(check-expect (find "D4" D4) 0)
(check-expect (find "D6" D6) 0)
(check-expect (find "F3" D4) false)
(check-expect (find "F1" D4) 1)
(check-expect (find "F2" D4) 2)
(check-expect (find "F1" D6) 1)
(check-expect (find "F3" D6) 3)

;(define (find n e) false) ;stubs

(define (find n e)
  (local [(define (find--element n e)
            (if (string=? (elt-name e) n)
                (elt-data e)
                (find--loe n (elt-subs e))))

          (define (find--loe n loe)
            (cond [(empty? loe) false]
                  [else
                   (if (not (false? (find--element n (first loe))))
                       (find--element n (first loe))
                       (find--loe n (rest loe)))]))]

    (find--element n e)))


;
; PROBLEM
;
; Design a function that consumes Element and produces a rendering of the tree. For example:
;
; (render-tree D6) should produce something like the following.
; .
;
; HINTS:
;   - This function is not very different than the first two functions above.
;   - Keep it simple! Start with a not very fancy rendering like the one above.
;     Once that works you can make it more elaborate if you want to.
;   - And... be sure to USE the recipe. Not just follow it, but let it help you.
;     For example, work out a number of examples BEFORE you try to code the function.
;
