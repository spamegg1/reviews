; Programming Languages, Dan Grossman
; Section 7: Optional: Dynamic Dispatch Manually in Racket

#lang racket

;; We can "use" dynamic dispatch in a language without it manually

;; Our "objects" will have:
;;  * an immutable list of mutable "fields" (symbols and contents)
;;  * an immutable list of immutable "methods" (symbols and functions taking self)
(struct obj (fields methods))

; like assoc but for an immutable list of mutable pairs
(define (assoc-m v xs)
  (cond [(null? xs) #f]
        [(equal? v (mcar (car xs))) (car xs)]
        [#t (assoc-m v (cdr xs))]))

(define (get obj fld)
  (let ([pr (assoc-m fld (obj-fields obj))])
    (if pr
        (mcdr pr)
        (error "field not found"))))

(define (set obj fld v)
  (let ([pr (assoc-m fld (obj-fields obj))])
    (if pr
        (set-mcdr! pr v)
        (error "field not found"))))
 
(define (send obj msg . args) ; convenience: multi-argument functions (2+ arguments)
  (let ([pr (assoc msg (obj-methods obj))])
    (if pr
        ((cdr pr) obj args) ; do the call
        (error "method not found" msg))))

(define (make-point _x _y)
  (obj
   (list (mcons 'x _x)
         (mcons 'y _y))
   (list (cons 'get-x (lambda (self args) (get self 'x)))
         (cons 'get-y (lambda (self args) (get self 'y)))
         (cons 'set-x (lambda (self args) (set self 'x (car args))))
         (cons 'set-y (lambda (self args) (set self 'y (car args))))
         (cons 'distToOrigin 
               (lambda (self args)
                 (let ([a (send self 'get-x)]
                       [b (send self 'get-y)])
                   (sqrt (+ (* a a) (* b b)))))))))

(define (make-color-point _x _y _c)
  (let ([pt (make-point _x _y)])
    (obj
     (cons (mcons 'color _c) 
           (obj-fields pt))
     (append (list
              (cons 'get-color (lambda (self args) (get self 'color)))
              (cons 'set-color (lambda (self args) (set self 'color (car args)))))
           (obj-methods pt)))))
      
(define (make-polar-point _r _th)
  (let ([pt (make-point #f #f)])
    (obj
     (append (list (mcons 'r _r)
                   (mcons 'theta _th))
             (obj-fields pt)) ; Java-style field extension
     (append ; overriding by being earlier in the list (see send function)
      (list 
       (cons 'set-r-theta 
             (lambda (self args)
               (begin 
                 (set self 'r (car args))
                 (set self 'theta (cadr args)))))
       (cons 'get-x (lambda (self args)
                      (let ([r (get self 'r)]
                            [theta (get self 'theta)])
                        (* r (cos theta)))))
       (cons 'get-y (lambda (self args)
                      (let ([r (get self 'r)]
                            [theta (get self 'theta)])
                        (* r (sin theta)))))
       (cons 'set-x (lambda (self args) 
                      (let* ([a     (car args)]
                             [b     (send self 'get-y)]
                             [theta (atan b a)]
                             [r     (sqrt (+ (* a a) (* b b)))])
                        (send self 'set-r-theta r theta))))
       (cons 'set-y (lambda (self args) 
                      (let* ([b     (car args)]
                             [a     (send self 'get-x)]
                             [theta (atan b a)]
                             [r     (sqrt (+ (* a a) (* b b)))])
                        (send self 'set-r-theta r theta)))))
      (obj-methods pt)))))

(define p1 (make-point -4 0))
p1
(send p1 'get-x)
(send p1 'get-y)
(send p1 'distToOrigin)
(send p1 'set-y 3)
(send p1 'distToOrigin)

(define p2 (make-color-point -4 0 "red"))
p2
(send p2 'get-x)
(send p2 'get-y)
(send p2 'distToOrigin)
(send p2 'set-y 3)
(send p2 'distToOrigin)

(define p3 (make-polar-point 4 3.1415926535))
p3
(send p3 'get-x)
(send p3 'get-y)
(send p3 'distToOrigin)
(send p3 'set-y 3)
(send p3 'distToOrigin)
