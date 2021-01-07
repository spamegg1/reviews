
;; yell-all-solution.rkt

;; =================
;; Data definitions:

;
; Remember the data definition for a list of strings we designed in Lecture 5c:
; (if this data definition does not look familiar, please review the lecture)
;


;; ListOfString is one of:
;;  - empty
;;  - (cons String ListOfString)
;; interp. a list of strings

(define LS0 empty)
(define LS1 (cons "a" empty))
(define LS2 (cons "a" (cons "b" empty)))
(define LS3 (cons "c" (cons "b" (cons "a" empty))))

#;
(define (fn-for-los los)
  (cond [(empty? los) (...)]
        [else
         (... (first los)
              (fn-for-los (rest los)))]))

;; Template rules used:
;; - one of: 2 cases
;; - atomic distinct: empty
;; - compound: (cons String ListOfString)
;; - self-reference: (rest los) is ListOfString

;; =================
;; Functions:

;
; PROBLEM:
;
; Design a function that consumes a list of strings and "yells" each word by
; adding "!" to the end of each string.
;


;; ListOfString -> ListOfString
;; produces a list of strings with "!" added to the end of each word
(check-expect (yell-all empty) empty)
(check-expect (yell-all LS3) (cons "c!" (cons "b!" (cons "a!" empty))))
(check-expect (yell-all (cons "hi" (cons "wait" (cons "what" empty))))
              (cons "hi!" (cons "wait!" (cons "what!" empty))))

;(define (yell-all los) empty)  ;stub
;<template from ListOfString>

(define (yell-all los)
  (cond [(empty? los) empty]
        [else
         (cons (string-append (first los) "!")
               (yell-all (rest los)))]))
