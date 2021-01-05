;; Programming Languages, Homework 5

#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; definition of structures for MUPL programs - Do NOT change
(struct var  (string) #:transparent)  ;; a variable, e.g., (var "foo")
(struct int  (num)    #:transparent)  ;; a constant number, e.g., (int 17)
(struct add  (e1 e2)  #:transparent)  ;; add two expressions
(struct ifgreater (e1 e2 e3 e4)    #:transparent) ;; if e1 > e2 then e3 else e4
(struct fun  (nameopt formal body) #:transparent) ;; a recursive(?) 1-argument function
(struct call (funexp actual)       #:transparent) ;; function call
(struct mlet (var e body) #:transparent) ;; a local binding (let var = e in body)
(struct apair (e1 e2)     #:transparent) ;; make a new pair
(struct fst  (e)    #:transparent) ;; get first part of a pair
(struct snd  (e)    #:transparent) ;; get second part of a pair
(struct aunit ()    #:transparent) ;; unit value -- good for ending a list
(struct isaunit (e) #:transparent) ;; evaluate to 1 if e is unit else 0

;; a closure is not in "source" programs but /is/ a MUPL value; it is what functions evaluate to
(struct closure (env fun) #:transparent)

;; Problem 1

;; Racket list -> MUPL list
;; produces analogous MUPL list with same elts in same order
(define (racketlist->mupllist rlst)
  (cond [(empty? rlst) (aunit)]
        [else (apair (car rlst)
                     (racketlist->mupllist (cdr rlst)))]))

;; MUPL list -> Racket list
;; produces analogous Racket list with same elts in same order
(define (mupllist->racketlist mlst)
  (cond [(aunit? mlst) empty]
        [else (cons (apair-e1 mlst)
                    (mupllist->racketlist (apair-e2 mlst)))]))

;; Problem 2

;; lookup a variable in an environment
;; Do NOT change this function
(define (envlookup env str)
  (cond [(null? env) (error "unbound variable during evaluation" str)]
        [(equal? (car (car env)) str) (cdr (car env))]
        [#t (envlookup (cdr env) str)]))

;; Do NOT change the two cases given to you.
;; DO add more cases for other kinds of MUPL expressions.
;; We will test eval-under-env by calling it directly even though
;; "in real life" it would be a helper function of eval-exp.
(define (eval-under-env e env)
  (cond [(aunit? e) e]
        [(int? e) e]
        [(closure? e) e]
        [(fun? e) (closure env e)]
        [(var? e) (envlookup env (var-string e))]
        [(add? e)
         (let ([v1 (eval-under-env (add-e1 e) env)]
               [v2 (eval-under-env (add-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (int (+ (int-num v1)
                       (int-num v2)))
               (error "MUPL addition applied to non-number")))]
        [(ifgreater? e)
         (let ([v1 (eval-under-env (ifgreater-e1 e) env)]
               [v2 (eval-under-env (ifgreater-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (if (> (int-num v1) (int-num v2))
                   (eval-under-env (ifgreater-e3 e) env)
                   (eval-under-env (ifgreater-e4 e) env))
               (error "MUPL ifgreater applied to non-number")))]
        [(mlet? e)
         (let* ([v (eval-under-env (mlet-e e) env)]
                [newenv (cons (cons (mlet-var e) v) env)])
           (eval-under-env (mlet-body e) newenv))]
        [(apair? e)
         (apair (eval-under-env (apair-e1 e) env)
                (eval-under-env (apair-e2 e) env))]
        [(fst? e)
         (let ([v (eval-under-env (fst-e e) env)])
           (if (apair? v)
               (apair-e1 v)
               (error "MUPL fst applied to non-pair")))]
        [(snd? e)
         (let ([v (eval-under-env (snd-e e) env)])
           (if (apair? v)
               (apair-e2 v)
               (error "MUPL snd applied to non-pair")))]
        [(isaunit? e)
         (let ([v (eval-under-env (isaunit-e e) env)])
           (if (aunit? v) (int 1) (int 0)))]
        [(call? e)
         (let ([cl (eval-under-env (call-funexp e) env)]
               [arg (eval-under-env (call-actual e) env)])
           (if (closure? cl)
               (let ([myfun (closure-fun cl)])
               (eval-under-env (fun-body myfun)
                               (let ([newenv (cons
                                              (cons (fun-formal myfun) arg)
                                              (closure-env cl))])
                                 (if (fun-nameopt myfun)
                                     (cons
                                      (cons (fun-nameopt myfun) cl)
                                      newenv)
                                     newenv))))
               (error "MUPL call's funexp is not a closure")))]
        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))

;; Problem 3

(define (ifaunit e1 e2 e3)
  (ifgreater (isaunit e1) (int 0) e2 e3))

(define (mlet* lstlst e2)
  (cond [(empty? lstlst) e2]
        [else (mlet (car (car lstlst))
                    (cdr (car lstlst))
                    (mlet* (cdr lstlst) e2))]))

(define (ifeq e1 e2 e3 e4)
  (mlet "_x" e1
        (mlet "_y" e2
              (ifgreater (var "_y") (var "_x")
                         e4
                         (ifgreater (var "_x") (var "_y")
                                    e4
                                    e3)))))

;; Problem 4

(define mupl-map
  (fun "mupl-map" "mupl-fun"
       (fun "aux" "mupl-list"
            (ifaunit (var "mupl-list")
                     (aunit)
                     (apair (call (var "mupl-fun") (fst (var "mupl-list")))
                            (call (var "aux") (snd (var "mupl-list"))))))))

(define mupl-mapAddN
  (mlet "map" mupl-map
        (fun "mupl-mapAddN" "i"
             (fun #f "mupl-list"
                  (call (call (var "map")
                        (fun #f "x" (add (var "x") (var "i"))))
                        (var "mupl-list"))))))

;; Challenge Problem
;; a recursive(?) 1-argument function
(struct fun-challenge (nameopt formal body freevars) #:transparent)

;; We will test this function directly, so it must do
;; as described in the assignment
(define (compute-free-vars e) "CHANGE")

;; Do NOT share code with eval-under-env because that will make
;; auto-grading and peer assessment more difficult, so
;; copy most of your interpreter here and make minor changes
(define (eval-under-env-c e env) "CHANGE")

;; Do NOT change this
(define (eval-exp-c e)
  (eval-under-env-c (compute-free-vars e) null))
