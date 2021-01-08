
;; count-rooms-solution.rkt

;
; PROBLEM:
;
; Using the following data definition, design a function that consumes a room and produces
; the total number of rooms reachable from the given room. Include the starting room itself.
; Your function should be tail recursive, but you should not use the primitive length function.
;


;; Data Definitions:

(define-struct room (name exits))
;; Room is (make-room String (listof Room))
;; interp. the room's name, and list of rooms that the exits lead to

; .

(define H1 (make-room "A" (list (make-room "B" empty))))

; .

(define H2
  (shared ((-0- (make-room "A" (list (make-room "B" (list -0-))))))
    -0-))


; .

(define H3
  (shared ((-A- (make-room "A" (list -B-)))
           (-B- (make-room "B" (list -C-)))
           (-C- (make-room "C" (list -A-))))
    -A-))



; .

(define H4
  (shared ((-A- (make-room "A" (list -B- -D-)))
           (-B- (make-room "B" (list -C- -E-)))
           (-C- (make-room "C" (list -B-)))
           (-D- (make-room "D" (list -E-)))
           (-E- (make-room "E" (list -F- -A-)))
           (-F- (make-room "F" (list))))
    -A-))

;; template: structural recursion, encapsulate w/ local, tail-recursive w/ worklist,
;;           context-preserving accumulator what rooms have we already visited
#;
(define (fn-for-house r0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  (local [(define (fn-for-room r todo visited)
            (if (member (room-name r) visited)
                (fn-for-lor todo visited)
                (fn-for-lor (append (room-exits r) todo)
                            (cons (room-name r) visited)))) ; (... (room-name r))
          (define (fn-for-lor todo visited)
            (cond [(empty? todo) (...)]
                  [else
                   (fn-for-room (first todo)
                                (rest todo)
                                visited)]))]
    (fn-for-room r0 empty empty)))

;; Functions:

;; Room -> Natural
;; produces the number of rooms reachable from the given room

(check-expect (count-rooms H1) 2)
(check-expect (count-rooms H3) 3)
(check-expect (count-rooms H4) 6)

;structural recursion only (not tail recursion)
(define (count-rooms r0)
  ;; path is (listof String); context preserving accumulator, names of rooms
  (local [(define (fn-for-room r  path)
            (if (member (room-name r) path)
                empty
                (cons (room-name r)
                      (fn-for-lor (room-exits r)
                            (cons (room-name r) path)))))
          (define (fn-for-lor lor path)
            (cond [(empty? lor) empty]
                  [else
                   (union (fn-for-room (first lor) path)
                          (fn-for-lor (rest lor) path))]))]
    (length (fn-for-room r0 empty))))

#;
(define (count-rooms r0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  ;; rsf is Natural; the number of rooms seen so far
  (local [(define (fn-for-room r todo visited rsf)
            (if (member (room-name r) visited)
                (fn-for-lor todo visited rsf)
                (fn-for-lor (append (room-exits r) todo)
                            (cons (room-name r) visited) (add1 rsf)))) ; (... (room-name r))
          (define (fn-for-lor todo visited rsf)
            (cond [(empty? todo) rsf]
                  [else
                   (fn-for-room (first todo)
                                (rest todo)
                                visited rsf)]))]
    (fn-for-room r0 empty empty 0)))


(define (union l1 l2)
  (cond [(empty? l1) l2]
        [else
         (cons (first l1)
               (union (rest l1)
                      (remove (first l1) l2)))]))
