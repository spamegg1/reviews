
;; lookup-room-solution.rkt

;
; PROBLEM:
;
; Using the following data definition, design a function that consumes a room and a room
; name and tries to find a room with the given name starting at the given room.
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

;; Room String -> Room or false
;; produce the room with the given name, false if it doesn't exist

(check-expect (lookup-room H1 "A") H1)
(check-expect (lookup-room H3 "C") (shared ((-C- (make-room "C"
                                                            (list (make-room "A"
                                                                             (list (make-room "B"
                                                                                              (list -C-))))))))
                                     -C-))
(check-expect (lookup-room H4 "F") (make-room "F" empty))
(check-expect (lookup-room H4 "X") false)


;structural recursion only (not tail recursion)
(define (lookup-room r0 rn)
  ;; path is (listof String); context preserving accumulator, names of rooms
  (local [(define (fn-for-room r  path)
            (cond [(member (room-name r) path) false]
                  [(string=? (room-name r) rn) r]
                  [else
                   (fn-for-lor (room-exits r)
                               (cons (room-name r) path))]))
          (define (fn-for-lor lor path)
            (cond [(empty? lor) false]
                  [else
                   (local [(define try (fn-for-room (first lor) path))]
                     (if (not (false? try))
                         try
                         (fn-for-lor (rest lor) path)))]))]
    (fn-for-room r0 empty)))

#;
(define (lookup-room r0 rn)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  (local [(define (fn-for-room r todo visited)
            (cond [(string=? (room-name r) rn) r]
                  [(member (room-name r) visited)
                   (fn-for-lor todo visited)]
                  [else
                   (fn-for-lor (append (room-exits r) todo)
                               (cons (room-name r) visited))]))
          (define (fn-for-lor todo visited)
            (cond [(empty? todo) false]
                  [else
                   (fn-for-room (first todo)
                                (rest todo)
                                visited)]))]
    (fn-for-room r0 empty empty)))
