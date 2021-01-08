
;; max-exits-from-solution.rkt

;
; PROBLEM:
;
; Using the following data definition, design a function that produces the room with the most exits
; (in the case of a tie you can produce any of the rooms in the tie).
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

;; Room -> Room
;; produce the room with the most exits

(check-expect (max-exits-from H1) H1)
(check-expect (max-exits-from H2) H2)
(check-expect (max-exits-from (shared ((-A- (make-room "A" (list -B-)))
                                       (-B- (make-room "B" (list -C- -A-)))
                                       (-C- (make-room "C" (list -A-))))
                                -A-))
              (shared ((-A- (make-room "A" (list -B-)))
                       (-B- (make-room "B" (list -C- -A-)))
                       (-C- (make-room "C" (list -A-))))
                -B-))


;structural recursion only (not tail recursion)
(define (max-exits-from r0)
  ;; path is (listof String); context preserving accumulator, names of rooms
  (local [(define (fn-for-room r  path)
            (cond [(member (room-name r) path) false]
                  [else
                   (max-exits r
                              (fn-for-lor (room-exits r)
                                          (cons (room-name r) path)))]))
          (define (fn-for-lor lor path)
            (cond [(empty? lor) false]
                  [else
                   (max-exits (fn-for-room (first lor) path)
                              (fn-for-lor (rest lor) path))]))

          (define (max-exits r1 r2)
            (cond [(false? r1) r2]
                  [(false? r2) r1]
                  [(>= (length (room-exits r1))
                       (length (room-exits r2)))
                   r1]
                  [else r2]))]

    (fn-for-room r0 empty)))

#;
(define (max-exits-from r0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  ;; rsf is Room; the room with the most exits of rooms seen so far
  (local [(define (fn-for-room r todo visited rsf)
            (if (member (room-name r) visited)
                (fn-for-lor todo visited rsf)
                (fn-for-lor (append (room-exits r) todo)
                            (cons (room-name r) visited)
                            (max-exits r rsf))))
          (define (fn-for-lor todo visited rsf)
            (cond [(empty? todo) rsf]
                  [else
                   (fn-for-room (first todo)
                                (rest todo)
                                visited rsf)]))
          ; helper
          (define (max-exits r rsf)
            (if (>= (length (room-exits rsf)) (length (room-exits r)))
                rsf
                r))]
    (fn-for-room r0 empty empty r0)))
