
;; house-path-starter.rkt

; Consider the following house diagram:
;
; .
;
; Starting from the porch, there are many paths through the house that you can
; follow without retracing your steps.  If we represent these paths as lists:
; (list
;  (list "Porch")
;  (list "Porch" "Living Room")
;  (list "Porch" "Living Room" "Hall")
;  ...)
;
; you can see that a lot of these paths start with the same sequence of rooms.
; We can represent these paths, and capture their shared initial parts, by using
; a tree:
; .
;
; The following data definition does exactly this.


(define-struct path (room nexts))
;; Path is (make-path String (listof Path))
;; interp. An arbitrary-arity tree of paths.
;;  - (make-path room nexts) represents all the paths downward from room
(define P0 (make-path "A" empty)) ; a room from which there are no paths

(define PH
  (make-path "Porch"
   (list
    (make-path "Living Room"
      (list (make-path "Dining Room"
              (list (make-path"Kitchen"
                      (list (make-path "Hall"
                              (list (make-path "Study" (list))
                                    (make-path "Bedroom" (list))
                                    (make-path "Bathroom" (list))))))))
            (make-path "Hall"
              (list (make-path "Kitchen"
                      (list (make-path "Dining Room" (list))))
                    (make-path "Study" (list))
                    (make-path "Bedroom" (list))
                    (make-path "Bathroom" (list)))))))))

#;
(define (fn-for-path p)
  (local [(define (fn-for-path p)
            (... (path-room p)
                 (fn-for-lop (path-nexts p))))
          (define (fn-for-lop lop)
            (cond [(empty? lop) (...)]
                  [else
                   (... (fn-for-path (first lop))
                        (fn-for-lop (rest lop)))]))]
    (fn-for-path p)))



; The problems below also make use of the following data definition and function:


;; Result is one of:
;; - Boolean
;; - "never"
;; interp. three possible answers to a question
(define R0 true)
(define R1 false)
(define R2 "never")

#;
(define (fn-for-result r)
  (cond
    [(boolean? r) (... r)]
    [else (...)]))

;; Result Result -> Result
;; produce the logical combination of two results
; Cross Product of Types Table:
;
;  ╔════════════════╦═══════════════╦══════════════╗
;  ║                ║               ║              ║
;  ║            r0  ║   Boolean     ║   "never"    ║
;  ║                ║               ║              ║
;  ║    r1          ║               ║              ║
;  ╠════════════════╬═══════════════╬══════════════╣
;  ║                ║               ║              ║
;  ║   Boolean      ║ (and r0 r1)   ║              ║
;  ║                ║               ║              ║
;  ╠════════════════╬═══════════════╣  r1          ║
;  ║                ║               ║              ║
;  ║   "never"      ║  r0           ║              ║
;  ║                ║               ║              ║
;  ╚════════════════╩═══════════════╩══════════════╝



(check-expect (and-result false false) false)
(check-expect (and-result false true) false)
(check-expect (and-result false "never") false)
(check-expect (and-result true false) false)
(check-expect (and-result true true) true)
(check-expect (and-result true "never") true)
(check-expect (and-result "never" true) true)
(check-expect (and-result "never" false) false)
(check-expect (and-result "never" "never") "never")

(define (and-result r0 r1)
  (cond [(and (boolean? r0) (boolean? r1)) (and r0 r1)]
        [(string? r0) r1]
        [else r0]))



; PROBLEM 1:
;
; Design a function called always-before that takes a path tree p and two room
; names b and c, and determines whether starting from p:
; 1) you must pass through room b to get to room c (produce true),
; 2) you can get to room c without passing through room b (produce false), or
; 3) you just can't get to room c (produce "never").
;
; Note that if b and c are the same room, you should produce false since you don't
; need to pass through the room to get there.
;




; OPTIONAL EXTRA PRACTICE PROBLEM:
;
; Once you have always-before working, make a copy of it, rename the copy to
; always-before-tr, and then modify the function to be tail recursive.
;
