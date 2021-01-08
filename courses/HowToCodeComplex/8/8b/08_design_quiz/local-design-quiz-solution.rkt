;; local-design-quiz-solution.rkt


; Problem 1:
;
; Suppose you have rosters for players on two opposing tennis team, and each
; roster is ordered by team rank, with the best player listed first. When both
; teams play, the best players of each team play one another,
; and the second-best players play one another, and so on down the line. When
; one team has more players than the other, the lowest ranking players on
; the larger team do not play.
;
; Design a function that consumes two rosters, and produces true if all players
; on both teams will play if the teams play each other.
; No marks will be given to solution that do not use a cross product table.
;


;; Player is String
;; interp.  the name of a tennis player.
(define P0 "Maria")
(define P2 "Serena")

#;
(define (fn-for-player p)
  (... p))



;; Roster is one of:
;; - empty
;; - (cons Player Roster)
;; interp.  a team roster, ordered from best player to worst.
(define R0 empty)
(define R1 (list "Eugenie" "Gabriela" "Sharon" "Aleksandra"))
(define R2 (list "Maria" "Nadia" "Elena" "Anastasia" "Svetlana"))
(define R3 (list "Eugenie" "Gabriela" "Sharon" "Aleksandra" "Bla"))
#;
(define (fn-for-roster r)
  (cond [(empty? r) (...)]
        [else
         (... (fn-for-player (first r))
              (fn-for-roster (rest r)))]))



(define-struct match (p1 p2))
;; Match is (make-match Player Player)
;; interp.  a match between player p1 and player p2, with same team rank
(define M0 (make-match "Eugenie" "Maria"))
(define M1 (make-match "Gabriela" "Nadia"))

#;
(define (fn-for-match m)
  (... (match-p1 m) (match-p2 m)))



;; ListOfMatch is one of:
;; - empty
;; - (cons Match ListOfMatch)
;; interp. a list of matches between one team and another.
(define LOM0 empty)
(define LOM1 (list (make-match "Eugenie" "Maria")
                   (make-match "Gabriela" "Nadia")))

#;
(define (fn-for-lom lom)
  (cond [(empty? lom) (...)]
        [else
         (... (fn-for-match (first lom))
              (fn-for-lom (rest lom)))]))

;; Roster Roster -> Boolean
;; produce true if all players on both rosters will play in a match
(check-expect (roster-match R0 R0) true)
(check-expect (roster-match R1 R1) true)
(check-expect (roster-match R2 R2) true)
(check-expect (roster-match R0 R1) false)
(check-expect (roster-match R1 R0) false)
(check-expect (roster-match R0 R2) false)
(check-expect (roster-match R2 R0) false)
(check-expect (roster-match R1 R2) false)
(check-expect (roster-match R2 R1) false)

;(define (roster-match r1 r2) false) ;stub
(define (roster-match r1 r2)
  (cond [(and (empty? r1) (empty? r2)) true]
        [(and (empty? r1) (not (empty? r2))) false]
        [(and (not (empty? r1)) (empty? r2)) false]
        [else (roster-match (rest r1) (rest r2))]))
;; Cross Product of Type Comments Table
;;-------------------------------------------
;;         r2  |   empty      |   non-empty
;; r1          |              |
;;-------------------------------------------
;;  empty      |   true       |   false
;;             |              |
;;-------------------------------------------
;;  non-empty  |   false      |   ???
;;             |              |
;;-------------------------------------------

; Problem 2:
;
; Now write a function that, given two teams, produces the list of tennis matches
; that will be played.
;
; Assume that this function will only be called if the function you designed above
; produces true. In other words, you can assume the two teams have the same number
; of players.
;

;; Roster Roster -> ListOfMatch
;; produces list of matches that will be played
;; ASSUMES both rosters have equal number of players
(check-expect (matches R0 R0) empty)
(check-expect (matches R1 R1) (list
                               (make-match "Eugenie" "Eugenie")
                               (make-match "Gabriela" "Gabriela")
                               (make-match "Sharon" "Sharon")
                               (make-match "Aleksandra" "Aleksandra")))
(check-expect (matches R2 R2) (list
                               (make-match "Maria" "Maria")
                               (make-match "Nadia" "Nadia")
                               (make-match "Elena" "Elena")
                               (make-match "Anastasia" "Anastasia")
                               (make-match "Svetlana" "Svetlana")))
(check-expect (matches R2 R3) (list
                               (make-match "Maria" "Eugenie")
                               (make-match "Nadia" "Gabriela")
                               (make-match "Elena" "Sharon")
                               (make-match "Anastasia" "Aleksandra")
                               (make-match "Svetlana" "Bla")))
(check-expect (matches R3 R2) (list
                               (make-match "Eugenie" "Maria")
                               (make-match "Gabriela" "Nadia")
                               (make-match "Sharon" "Elena")
                               (make-match "Aleksandra" "Anastasia")
                               (make-match "Bla" "Svetlana")))
;(define (matches r1 r2) empty) ;stub
(define (matches r1 r2)
  (cond [(and (empty? r1) (empty? r2)) empty]
        [else
         (cons (make-match (first r1) (first r2)) (matches (rest r1) (rest r2)))]))
