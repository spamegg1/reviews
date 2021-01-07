
;; less-than-five-solution.rkt

;
; PROBLEM:
;
; DESIGN function that consumes a string and determines whether its length is
; less than 5.  Follow the HtDF recipe and leave behind commented out versions
; of the stub and template.
;


;; String -> Boolean
;; produce true if length of s is less than 5
(check-expect (less-than-5? "") true)
(check-expect (less-than-5? "five") true)
(check-expect (less-than-5? "12345") false)
(check-expect (less-than-5? "eighty") false)

;(define (less-than-5? s)  ;stub
;  true)

;(define (less-than-5? s)  ;template
;  (... s))

(define (less-than-5? s)
  (< (string-length s) 5))
