#lang racket
;; Programming Languages Homework 5 Simple Test
;; Save this file to the same directory as your homework file
;; These are basic tests. Passing these tests does not
;; guarantee that your code will pass the actual homework grader

;; Be sure to put your homework file in the same folder as this test file.
;; Uncomment the line below and, if necessary, change the filename
(require "hw5.rkt")

(require rackunit)

(define tests
  (test-suite
   "Sample tests for Assignment 5"

   ;; check racketlist to mupllist with normal list
   (check-equal? (racketlist->mupllist (list (int 3) (int 4)))
                 (apair (int 3) (apair (int 4) (aunit))) "racketlist->mupllist test")

   ;; check mupllist to racketlist with normal list
   (check-equal? (mupllist->racketlist (apair (int 3) (apair (int 4) (aunit))))
                 (list (int 3) (int 4)) "mupllist->racketlist test")

   ;; tests if ifgreater returns (int 2)
   (check-equal? (eval-exp (ifgreater (int 3) (int 4) (int 3) (int 2)))
                 (int 2) "ifgreater test")

   ;; mlet test
   (check-equal? (eval-exp (mlet "x" (int 1) (add (int 5) (var "x"))))
                 (int 6) "mlet test")

   ;; call test
   (check-equal? (eval-exp
                  (call (closure '() (fun #f "x" (add (var "x") (int 7)))) (int 1)))
                 (int 8) "call test")

   ;; snd test
   (check-equal? (eval-exp (snd (apair (int 1) (int 2))))
                 (int 2) "snd test")

   ;; isaunit test
   (check-equal? (eval-exp (isaunit (closure '() (fun #f "x" (aunit)))))
                 (int 0) "isaunit test")

   ;; ifaunit test
   (check-equal? (eval-exp (ifaunit (int 1) (int 2) (int 3)))
                 (int 3) "ifaunit test")

   ;; mlet* test
   (check-equal? (eval-exp (mlet* (list (cons "x" (int 10))) (var "x")))
                 (int 10) "mlet* test")

   ;; ifeq test
   (check-equal? (eval-exp (ifeq (int 1) (int 2) (int 3) (int 4)))
                 (int 4) "ifeq test")

   ;; mupl-map test
   (check-equal? (eval-exp (call
                            (call mupl-map
                                  (fun #f "x" (add (var "x") (int 7))))
                            (apair (int 1) (aunit))))
                 (apair (int 8) (aunit)) "mupl-map test")

   ;; problems 1, 2, and 4 combined test
   (check-equal? (mupllist->racketlist
                  (eval-exp
                   (call (call mupl-mapAddN (int 7))
                         (racketlist->mupllist
                          (list (int 3) (int 4) (int 9))))))
                 (list (int 10) (int 11) (int 16)) "combined test")

   ))

(require rackunit/text-ui)
;; runs the test
(run-tests tests)
