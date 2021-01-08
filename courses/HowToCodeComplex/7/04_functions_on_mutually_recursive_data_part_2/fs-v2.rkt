(require 2htdp/image)

;; fs-starter.rkt (type comments and examples)
;; fs-v1.rkt (complete data-definition plus function problems)
;; fs-v2.rkt (complete data-definition and sum-data function)

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
  (... (elt-name e)    ;String
       (elt-data e)    ;Integer
       (fn-for-loe (elt-subs e))))
#;
(define (fn-for-loe loe)
  (cond [(empty? loe) (...)]
        [else
         (... (fn-for-element (first loe))
              (fn-for-loe (rest loe)))]))


;; Functions:

;
; PROBLEM
;
; Design a function that consumes Element and produces the sum of all the file data in
; the tree.
;

;; Element -> Integer
;; ListOfElement -> Integer
;; produce the sum of all the data in element (and its subs)
(check-expect (sum-data--element F1) 1)
(check-expect (sum-data--loe empty) 0)
(check-expect (sum-data--element D5) 3)
(check-expect (sum-data--element D4) (+ 1 2))
(check-expect (sum-data--element D6) (+ 1 2 3))

;(define (sum-data--element e) 0)  ;stubs
;(define (sum-data--loe loe) 0)

(define (sum-data--element e)
  (if (zero? (elt-data e))
      (sum-data--loe (elt-subs e))
      (elt-data e)))

(define (sum-data--loe loe)
  (cond [(empty? loe) 0]
        [else
         (+ (sum-data--element (first loe))
            (sum-data--loe (rest loe)))]))

;
; PROBLEM
;
; Design a function that consumes Element and produces a list of the names of all the elements in
; the tree.
;


;
; PROBLEM
;
; Design a function that consumes String and Element and looks for a data element with the given
; name. If it finds that element it produces the data, otherwise it produces false.
;



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
