; USE INTERMEDIATE STUDENT LANGUAGE (or Advanced)

; Data definitions:
; A "pair" is a 2-element list: a string and a non-negative integer.
; the string can be accessed with (first pair),
; the integer can be accessed with (second pair).
; For example, this is a pair:
(define empty-pair (list "" 0))

; A "dictionary" is a (possibly empty) list of pairs.
; For example, this is a dictionary:
(define testdict (list (list "A" 2) (list "B" 3) (list "C" 1)))

; Test data:
(define strlist1 empty)
(define dict1 empty)
(define strlist2 (list "A"))
(define dict2 (list (list "A" 1)))
(define strlist3 (list "A" "A"))
(define dict3 (list (list "A" 2)))
(define strlist4 (list "A" "B" "A" "C" "A" "D" "B"))
(define dict4 (list (list "A" 3) (list "B" 2) (list "C" 1) (list "D" 1)))
(define strlist5 (list "A" "B" "C" "D" "D" "B"))
(define dict5 (list (list "A" 1) (list "B" 2) (list "C" 1) (list "D" 2)))

;========================================================================
; looks up the integer associated with the string in the dictionary.
; if string is not in the dictionary, returns false.
; keep in mind that 0 is not the same as false: (false? 0) = #false

(check-expect (lookup "A" empty) false)
(check-expect (lookup "A" testdict) 2)
(check-expect (lookup "B" testdict) 3)
(check-expect (lookup "C" testdict) 1)
(check-expect (lookup "D" testdict) false)

; String Dict -> (Integer or False)
(define (lookup str dict)
  (cond [(empty? dict) false]
        [(string=? (first (first dict)) str)
         (second (first dict))]
        [else (lookup str (rest dict))]))

;=====================================================================
; Updates value of string in dictionary with given integer.
; Assumes that the string occurs in the dictionary.

(check-expect (update-dict "A" 99 empty) empty)
(check-expect (update-dict "A" 99 testdict)
              (list (list "A" 99) (list "B" 3) (list "C" 1)))
(check-expect (update-dict "B" 99 testdict)
              (list (list "A" 2) (list "B" 99) (list "C" 1)))
(check-expect (update-dict "C" 99 testdict)
              (list (list "A" 2) (list "B" 3) (list "C" 99)))

; Dictionary String Integer -> Dictionary
(define (update-dict str int dict)
  (cond [(empty? dict) dict]
        [(string=? (first (first dict)) str)
         (append (list (list str int)) (rest dict))]
        [else
         (cons (first dict) (update-dict str int (rest dict)))]))

;=======================================================================
; converts a list of strings to a dictionary, by counting
; the number of occurences of each string in the list.
; for convenience I added the accumulator as one of the input parameters.
; so it should be called with the string list and the empty dictionary.
(check-expect (list-to-dict strlist1 empty) dict1)
(check-expect (list-to-dict strlist2 empty) dict2)
(check-expect (list-to-dict strlist3 empty) dict3)
(check-expect (list-to-dict strlist4 empty) dict4)
(check-expect (list-to-dict strlist5 empty) dict5)

; (List of String) Dictionary -> Dictionary
(define (list-to-dict strlist dict)
  (cond [(empty? strlist) dict]
        [else
         (local [(define lookup-val (lookup (first strlist) dict))]
         (cond [(false? lookup-val)                 ; str is not in dict
                (list-to-dict (rest strlist)          ; so add it with 1
                              (append dict
                                      (list (list (first strlist) 1))))]
               [else                                    ; str is in dict
                (list-to-dict (rest strlist)        ; increment val by 1
                              (update-dict (first strlist)
                                           (+ 1 lookup-val)
                                           dict))]))]))

;=======================================================================
; returns the pair with the highest integer value in the dictionary.
; for convenience I added the accumulator as one of the input parameters.
; so it should be called with the dictionary and the pair (list "" 0).
(check-expect (find-max dict1 empty-pair) empty-pair)
(check-expect (find-max dict2 empty-pair) (list "A" 1))
(check-expect (find-max dict3 empty-pair) (list "A" 2))
(check-expect (find-max dict4 empty-pair) (list "A" 3))
(check-expect (find-max dict5 empty-pair) (list "B" 2))

; Dictionary Pair -> Pair
(define (find-max dict max-pair-so-far)
  (cond [(empty? dict) max-pair-so-far]
        [(< (second max-pair-so-far) (second (first dict)))
         (find-max (rest dict) (first dict))]
        [else (find-max (rest dict) max-pair-so-far)]))

;=======================================================================
; returns the string that occurs the highest number of times in the list.
; assumes the list is not empty.
(check-expect (max-sub strlist2) "A")
(check-expect (max-sub strlist3) "A")
(check-expect (max-sub strlist4) "A")
(check-expect (max-sub strlist5) "B")

; List of String -> String
(define (max-sub strlist)
  (local [(define dict (list-to-dict strlist empty))]
    (first (find-max dict empty-pair))))
