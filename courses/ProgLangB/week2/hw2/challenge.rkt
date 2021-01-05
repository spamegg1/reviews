#lang racket
(provide (all-defined-out))

(define (compute-free-vars e)
  (struct res (e fvs)) ; result type of f (could also use a pair)
    (define (f e)
      (cond [(var? e) (res e (set (var-string e)))]
            [(int? e) (res e (set))]
            [(add? e) (let ([r1 (f (add-e1 e))]
                            [r2 (f (add-e2 e))])
                        (res (add (res-e r1) (res-e r2))
                             (set-union (res-fvs r1) (res-fvs r2))))]
            [(ifgreater? e) (let ([r1 (f (ifgreater-e1 e))]
                                  [r2 (f (ifgreater-e2 e))]
                                  [r3 (f (ifgreater-e3 e))]
                                  [r4 (f (ifgreater-e4 e))])
                              (res (ifgreater (res-e r1) (res-e r2) (res-e r3)    (res-e r4))
                                  (set-union (res-fvs r1) (res-fvs r2) (res-fvs   r3) (res-fvs r4))))]
            [(fun? e) (let* ([r (f (fun-body e))]
                             [fvs (set-remove (res-fvs r) (fun-formal e))]
                             [fvs (if (fun-nameopt e)
                                      (set-remove fvs (fun-nameopt e))
                                      fvs)])
                        (res (fun-challenge (fun-nameopt e) (fun-formal e)
                                            (res-e r) fvs)
                            fvs))]
            [(call? e) (let ([r1 (f (call-funexp e))]
                             [r2 (f (call-actual e))])
                        (res (call (res-e r1) (res-e r2))
                             (set-union (res-fvs r1) (res-fvs r2))))]
            [(mlet? e) (let* ([r1 (f (mlet-e e))]
                              [r2 (f (mlet-body e))])
                         (res (mlet (mlet-var e) (res-e r1) (res-e r2))
                              (set-union (res-fvs r1) (set-remove (res-fvs r2)   (mlet-var e)))))]
            [(apair? e) (let ([r1 (f (apair-e1 e))]
                              [r2 (f (apair-e2 e))])
                          (res (apair (res-e r1) (res-e r2))
                             (set-union (res-fvs r1) (res-fvs r2))))]
            [(fst? e) (let ([r (f (fst-e e))])
                        (res (fst (res-e r))
                             (res-fvs r)))]
            [(snd? e) (let ([r (f (snd-e e))])
                        (res (snd (res-e r))
                             (res-fvs r)))]
            [(aunit? e) (res e (set))]
            [(isaunit? e) (let ([r (f (isaunit-e e))])
                            (res (isaunit (res-e r))
                                 (res-fvs r)))]))
    (res-e (f e)))

(define (eval-under-env-c e env)
  (cond
        [(fun-challenge? e)
         (closure (set-map (fun-challenge-freevars e)
                           (lambda (s) (cons s (envlookup env s))))
                  e)]
         ; call case uses fun-challenge as appropriate
         ; all other cases the same
        ...))

(define (eval-exp-c e)
  (eval-under-env-c (compute-free-vars e) null))
