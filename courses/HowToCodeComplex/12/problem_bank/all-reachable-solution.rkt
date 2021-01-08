
;; all-reachable-solution.rkt

;
; PROBLEM:
;
; Using the following data definition:
;
; a) Design a function that consumes a room and produces a list of the names of all the rooms
; reachable from that room.
;
; b) Revise your function from (a) so that it produces a list of the rooms not the room names
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

;; template: structural recursion, encapsulate w/ local,
;;           context-preserving accumulator what rooms traversed on this path
#;
(define (fn-for-house r0)
  ;; path is (listof String); context preserving accumulator, names of rooms
  (local [(define (fn-for-room r  path)
            (if (member (room-name r) path)
                (... path)
                (fn-for-lor (room-exits r)
                            (cons (room-name r) path))))
          (define (fn-for-lor lor path)
            (cond [(empty? lor) (...)]
                  [else
                   (... (fn-for-room (first lor) path)
                        (fn-for-lor (rest lor) path))]))]
    (fn-for-room r0 empty)))

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

;; Room -> (listof String)
;; produce names of rooms reachable from the given room

(check-expect (all-reachable H1) (list "B" "A"))
(check-expect (all-reachable H2) (list "B" "A"))
(check-expect (all-reachable H4) (list "D" "F" "E" "C" "B" "A"))

;; ordinary recursion (result order differs from tail recursion)
#;
(define (all-reachable r0)
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
    (fn-for-room r0 empty)))

(define (all-reachable r0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  (local [(define (fn-for-room r todo visited)
            (if (member (room-name r) visited)
                (fn-for-lor todo visited)
                (fn-for-lor (append (room-exits r) todo)
                            (cons (room-name r) visited))))
          (define (fn-for-lor todo visited)
            (cond [(empty? todo) visited]
                  [else
                   (fn-for-room (first todo)
                                (rest todo)
                                visited)]))]
    (fn-for-room r0 empty empty)))


;; Room -> (listof Room)
;; produce rooms reachable from the given room

(check-expect (all-reachable-rooms H1) (list (make-room "B" empty) (make-room "A" (list (make-room "B" empty)))))
(check-expect (all-reachable-rooms H2) (shared ((-B- (make-room "B" (list -A-)))
                                                (-A- (make-room "A" (list -B-))))
                                         (list -B- -A-)))
(check-expect (all-reachable-rooms H4) (shared ((-D- (make-room "D" (list -E-)))
                                                (-A- (make-room "A" (list -B- -D-)))
                                                (-B- (make-room "B" (list -C- -E-)))
                                                (-C- (make-room "C" (list -B-)))
                                                (-E- (make-room "E" (list -F- -A-)))
                                                (-F- (make-room "F" empty)))
                                         (list -D- -F- -E- -C- -B- -A-)))


;; ordinary recursion (result order differs from tail recursion)
#;
(define (all-reachable-rooms r0)
  ;; path is (listof String); context preserving accumulator, names of rooms
  (local [(define (fn-for-room r  path)
            (if (member (room-name r) path)
                empty
                (cons r
                      (fn-for-lor (room-exits r)
                            (cons (room-name r) path)))))
          (define (fn-for-lor lor path)
            (cond [(empty? lor) empty]
                  [else
                   (union (fn-for-room (first lor) path)
                          (fn-for-lor (rest lor) path))]))]
    (fn-for-room r0 empty)))

(define (all-reachable-rooms r0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof Room); context preserving accumulator, rooms already visited
  (local [(define (fn-for-room r todo visited)
            (if (member r visited)
                (fn-for-lor todo visited)
                (fn-for-lor (append (room-exits r) todo)
                            (cons r visited))))
          (define (fn-for-lor todo visited)
            (cond [(empty? todo) visited]
                  [else
                   (fn-for-room (first todo)
                                (rest todo)
                                visited)]))]
    (fn-for-room r0 empty empty)))

(define (union l1 l2)
  (cond [(empty? l1) l2]
        [else
         (cons (first l1)
               (union (rest l1)
                      (remove (first l1) l2)))]))
