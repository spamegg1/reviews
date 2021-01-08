;  PROBLEM 1:
;
;  Assuming the use of at least one accumulator, design a function that consumes a list of strings,
;  and produces the length of the longest string in the list.
;

;; (listof String) -> Natural
;; produces length of longest string in list
(check-expect (longest empty) 0)
(check-expect (longest (list "a" "b" "c")) 1)
(check-expect (longest (list "a" "bc")) 2)
(check-expect (longest (list "a" "bc" "de")) 2)
(check-expect (longest (list "a" "bc" "def")) 3)
(check-expect (longest (list "abc" "de" "f")) 3)
(check-expect (longest (list "abc" "def" "nopr" "ghi" "jklm")) 4)

;(define (longest los) 0);stub

(define (longest los)
  ;; acc: Natural; length of longest string in list SO FAR, result so far accumulator
  (local [(define (fn-for-los los acc)
            (cond [(empty? los) acc]
                  [else
                   (local [(define try (string-length (first los)))]
                     (fn-for-los (rest los)
                                 (if (< acc try)
                                     try
                                     acc)))]))]
    (fn-for-los los 0)))


;  PROBLEM 2:
;
;  The Fibbonacci Sequence https://en.wikipedia.org/wiki/Fibonacci_number is
;  the sequence 0, 1, 1, 2, 3, 5, 8, 13,... where the nth element is equal to
;  n-2 + n-1.
;
;  Design a function that given a list of numbers at least two elements long,
;  determines if the list obeys the fibonacci rule, n-2 + n-1 = n, for every
;  element in the list. The sequence does not have to start at zero, so for
;  example, the sequence 4, 5, 9, 14, 23 would follow the rule.
;

;; (listof Number) -> Boolean
;; produce true if list obeys fibonacci rule
;; ASSUME: list has at least two numbers in it
(check-expect (fib? (list 0 0)) true)
(check-expect (fib? (list 5 110)) true)
(check-expect (fib? (list 5 110 115)) true)
(check-expect (fib? (list 0 1 1 2 3 5 8 13)) true)
(check-expect (fib? (list 1 1 2 3 5 8 13)) true)
(check-expect (fib? (list 0 1 1 2 3 8 13)) false)
(check-expect (fib? (list 4 5 9 14 23)) true)
(check-expect (fib? (list 4 5 9 13 24)) false)
(check-expect (fib? (list 5 7 12 19 32)) false)

;(define (fib? lon) false);stub
(define (fib? lon)
  (local [(define (fn-for-lon lon)
          (cond [(empty? (rest (rest lon))) true]
                [else
                 (if (= (first (rest (rest lon)))
                        (+ (first lon) (first (rest lon))))
                     (fn-for-lon (rest lon))
                     false)]))]
    (fn-for-lon lon)))

;  PROBLEM 3:
;
;  Refactor the function below to make it tail recursive.
;


;; Natural -> Natural
;; produces the factorial of the given number
(check-expect (fact 0) 1)
(check-expect (fact 3) 6)
(check-expect (fact 5) 120)


(define (fact n)
  ;; acc: Natural; the current product so far, result so far accumulator
  ;; (fact 5)
  ;; (fn-for-n 5 1)
  ;; (fn-for-n 4 (* 1 5))
  ;; (fn-for-n 3 (* 1 5 4))
  ;; (fn-for-n 2 (* 1 5 4 3))
  ;; (fn-for-n 1 (* 1 5 4 3 2))
  ;; (fn-for-n 0 (* 1 5 4 3 2 1))
  ;; 120
  (local [(define (fn-for-n n acc)
            (cond [(zero? n) acc]
                  [else
                   (fn-for-n (sub1 n) (* acc n))]))]
    (fn-for-n n 1)))



;  PROBLEM 4:
;
;  Recall the data definition for Region from the Abstraction Quiz. Use a worklist
;  accumulator to design a tail recursive function that counts the number of regions
;  within and including a given region.
;  So (count-regions CANADA) should produce 7



(define-struct region (name type subregions))
;; Region is (make-region String Type (listof Region))
;; interp. a geographical region

;; Type is one of:
;; - "Continent"
;; - "Country"
;; - "Province"
;; - "State"
;; - "City"
;; interp. categories of geographical regions

(define VANCOUVER (make-region "Vancouver" "City" empty))
(define VICTORIA (make-region "Victoria" "City" empty))
(define BC (make-region "British Columbia" "Province" (list VANCOUVER VICTORIA)))
(define CALGARY (make-region "Calgary" "City" empty))
(define EDMONTON (make-region "Edmonton" "City" empty))
(define ALBERTA (make-region "Alberta" "Province" (list CALGARY EDMONTON)))
(define CANADA (make-region "Canada" "Country" (list BC ALBERTA)))
; (Canada (BC (Vancouver Victoria)) (Alberta (Calgary Edmonton)))
#;
;; Region -> Natural
;; produce number of regions within and including a given region
(check-expect (count-regions VANCOUVER) 1)
(check-expect (count-regions VICTORIA) 1)
(check-expect (count-regions BC) 3)
(check-expect (count-regions CALGARY) 1)
(check-expect (count-regions EDMONTON) 1)
(check-expect (count-regions ALBERTA) 3)
(check-expect (count-regions CANADA) 7)

;(define (count-regions r) 0);stub

(define (count-regions r)
  ;; count: Natural; result so far accumulator
  ;; keeps track of how many regions have been counted so far
  ;; todo: (listof Region); worklist accumulator
  ;; keeps track of what regions still need to be visited
  (local [(define (fn-for-region r todo count)
            (fn-for-lor
             (append todo (region-subregions r))
             (add1 count)))

          (define (fn-for-lor todo count)
            (cond [(empty? todo) count]
                  [else
                   (fn-for-region (first todo) (rest todo) count)]))]
    (fn-for-region r empty 0)))
