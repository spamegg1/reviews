#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; Int Int Int -> List
;; produces list of numbers from low to high (including low and possibly high)
;; separated by stride and in sorted order.
;; assumes: stride is positive
(define (sequence low high stride)
  (cond [(< high low) empty]
        [else (cons low (sequence (+ low stride) high stride))]))

;; (String List) String -> (String List)
;; appends each string in list with given suffix
(define (string-append-map xs suffix)
  (map (lambda (s) (string-append s suffix)) xs))

;; List Int -> Int or Error
;; produces ith elt of list where i = n % length of list,
;; error if n < 0 or list empty
(define (list-nth-mod xs n)
  (cond [(empty? xs) (error "list-nth-mod: empty list")]
        [(< n 0) (error "list-nth-mod: negative number")]
        [else (car (list-tail xs (remainder n (length xs))))]))

;; Stream Integer -> (Integer List)
;; produces list holding first n values produced by stream in order
;; assumes: n is non-negative
(define (stream-for-n-steps s n)
  (cond [(= 0 n) empty]
        ;[else (local [(define thunk (s))]
        [else (letrec ([thunk (s)])
                (cons (car thunk)
                      (stream-for-n-steps (cdr thunk) (- n 1))))]))

;; -> Stream
;; produces stream of natural numbers except numbers divisible by 5 are negated:
;; 1, 2, 3, 4, -5, 6, 7, 8, 9, -10, 11, ... etc.
(define funny-number-stream
  (letrec ([f (lambda (x) (cons (if (= 0 (remainder x 5)) (- x) x)
                                (lambda () (f (+ x 1)))))])
    (lambda () (f 1))))

;; -> Stream
;; produces stream that alternates between the strings "dan.jpg" and "dog.jpg"
(define dan-then-dog
  (local [(define (aux flag)
            (lambda () (cons (cond [flag "dan.jpg"]
                                   [else "dog.jpg"])
                             (aux (not flag)))))]
    (aux true)))

;; Stream -> Stream
;; produces pairs from stream, with zero added
(define (stream-add-zero s)
  (lambda () (local [(define next (s))]
               (cons (cons 0 (car next))
                     (stream-add-zero (cdr next))))))

;; List List -> Stream
;; produces stream of pairs of elements from two lists,
;; which cycles forever through the two lists
;; assumes: both lists are non-empty
(define (cycle-lists xs ys)
  (local [(define (thunk n)
            (lambda () (cons (cons (list-nth-mod xs n)
                                   (list-nth-mod ys n))
                             (thunk (+ n 1)))))]
    (thunk 0)))

;; Value Vector -> Pair or False
;; acts similarly to Racket's assoc function, but accepts vectors instead of lists
;; produces first pair with a first field equal to given value, false otherwise
;; allows vector elements not to be pairs, in which case it skips them
(define (vector-assoc v vec)
  (if (= 0 (vector-length vec)) #f
  (local [(define vlen (vector-length vec))
          (define (aux i)
            (local [(define vi (vector-ref vec i))]
              (cond [(and (pair? vi) (equal? (car vi) v)) vi]
                    [(< (+ i 1) vlen) (aux (+ i 1))]
                    [else false])))]
    (aux 0))))

;; List Integer -> Function
;; produces function of one argument that acts like Racket's assoc,
;; which has an n-element vector-cache. Assumes: n is positive
(define (cached-assoc lst n)
  (let ([cache (make-vector n #f)]
        [next-to-replace 0])
    (lambda (v)
      (or (vector-assoc v cache)
          (let ([ans (assoc v lst)])
            (and ans
                 (begin (vector-set! cache next-to-replace ans)
                        (set! next-to-replace
                              (remainder (+ next-to-replace 1) n))
                        ans)))))))
