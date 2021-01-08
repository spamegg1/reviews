;; ta-solver-solution.rkt
;  PROBLEM 1:
;
;  Consider a social network similar to Twitter called Chirper. Each user has a name, a note about
;  whether or not they are a verified user, and follows some number of people.
;
;  Design a data definition for Chirper, including a template that is tail recursive and avoids
;  cycles.
;
;  Then design a function called most-followers which determines which user in a Chirper Network is
;  followed by the most people.
;

(define-struct user (name verified? following))
;; User is (String Boolean (listof User))
;; interp. user's name, verified status, and list of other users they follow
(define U1 (make-user "A" true (list (make-user "B" true empty))))

(define U2
  (shared ((-A- (make-user "A" true (list -B-)))
           (-B- (make-user "B" true (list -A-))))
    -A-))

(define U3
  (shared ((-A- (make-user "A" true (list -B-)))
           (-B- (make-user "B" true (list -C-)))
           (-C- (make-user "C" true (list -A-))))
    -A-))

(define U4
  (shared ((-A- (make-user "A" true (list -B- -D-)))
           (-B- (make-user "B" true (list -C- -E-)))
           (-C- (make-user "C" true (list -B-)))
           (-D- (make-user "D" true (list -E-)))
           (-E- (make-user "E" true (list -F- -A-)))
           (-F- (make-user "F" true (list))))
    -A-))
;; template: structural recursion, encapsulate w/ local, tail-recursive w/ worklist,
;;           context-preserving accumulator what users have we already visited
#;
(define (fn-for-chirper u0)
  ;; todo is (listof User); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of chirpers already visited
  (local [(define (fn-for-user u todo visited)
            (if (member (user-name u) visited)
                (fn-for-lou todo visited)
                (fn-for-lou (append (user-following u) todo)
                            (cons (user-name u) visited)))) ; (... (user-name r))
          (define (fn-for-lou todo visited)
            (cond [(empty? todo) (...)]
                  [else
                   (fn-for-user (first todo)
                                (rest todo)
                                visited)]))]
    (fn-for-user u0 empty empty)))

;; Followers is (listof (list Room (listof String)))
;; interp. each element of the list (list User (listof String)) is
;;         a user and a list of user names that lead to it

(define F1 (list (list (make-user "B" true empty) (list "C" "A"))
                 (list (make-user "A" true empty) (list "C"))))
;; F1 says that users A and C follow B, and user C follows A


;; Functions:

;; User -> User
;; produce the user with the most followers
(check-expect (max-followers U1) (first (user-following U1)))
(check-expect (max-followers (shared ((-A- (make-user "A" true (list -B- -C-)))
                                      (-B- (make-user "B" true (list -C-)))
                                      (-C- (make-user "C" true (list -A-))))
                               -A-))
              (shared ((-A- (make-user "A" true (list -B- -C-)))
                       (-B- (make-user "B" true (list -C-)))
                       (-C- (make-user "C" true (list -A-))))
                -C-))
(check-expect (max-followers (shared ((-A- (make-user "A" true (list -B- -C-)))
                                      (-B- (make-user "B" true empty))
                                      (-C- (make-user "C" true (list -B-))))
                               -A-))
              (shared ((-A- (make-user "A" true (list -B- -C-)))
                       (-B- (make-user "B" true empty))
                       (-C- (make-user "C" true (list -B-))))
                -B-))
(check-expect (max-followers (shared ((-A- (make-user "A" true (list -B- -C-)))
                                      (-B- (make-user "B" true (list -A-)))
                                      (-C- (make-user "C" true (list -A-))))
                               -A-))
              (shared ((-A- (make-user "A" true (list -B- -C-)))
                       (-B- (make-user "B" true (list -A-)))
                       (-C- (make-user "C" true (list -A-))))
                -A-))
(check-expect (max-followers (shared ((-A- (make-user "A" true (list -B- -C- -D-)))
                                      (-B- (make-user "B" true (list -C- -D-)))
                                      (-C- (make-user "C" true (list -D-)))
                                      (-D- (make-user "C" true (list -A-))))
                               -A-))
              (shared ((-A- (make-user "A" true (list -B- -C- -D-)))
                       (-B- (make-user "B" true (list -C- -D-)))
                       (-C- (make-user "C" true (list -D-)))
                       (-D- (make-user "C" true (list -A-))))
                -D-))
(define (max-followers u0)
  (local [;; UserNFollowers is (make-unf User Natural)
          ;; interp. the number of followers recorded of a given User
          (define-struct unf (u n))

          ;; todo is (listof User); a worklist accumulator
          ;; visited is (listof String); context preserving accumulator, names of users already visited
          ;; rsf is (listof UserNFollowers); the number of followers of users so far
          (define (fn-for-user u todo visited rsf)
            (if (member (user-name u) visited)
                (fn-for-lou todo visited rsf)
                (fn-for-lou (append (user-following u) todo)
                            (cons (user-name u) visited)
                            (merge-user u rsf))))
          (define (fn-for-lou todo visited rsf)
            (cond [(empty? todo) rsf]
                  [else
                   (fn-for-user (first todo)
                                (rest todo)
                                visited
                                rsf)]))
          ;; add one new user to rsf
          (define (merge-user u rsf)
            (foldr merge-follower rsf (user-following u)))
          ;; add one new follower to rsf
          (define (merge-follower u lounf)
            (cond [(empty? lounf) (list (make-unf u 1))]
                  [else
                   (if (string=? (user-name u) (user-name (unf-u (first lounf))))
                       (cons (make-unf u
                                       (add1 (unf-n (first lounf))))
                             (rest lounf))
                       (cons (first lounf)
                             (merge-follower u (rest lounf))))]))
          ;; pick the user from rsf that has the most followers to it
          (define (pick-max rsf)
            (unf-u
             (foldr (λ (u1 u2)
                      (if (> (unf-n u1) (unf-n u2))
                          u1
                          u2))
                    (first rsf)
                    (rest rsf))))]
    ;; function composition
    (pick-max (fn-for-user u0 empty empty empty))))

;  PROBLEM 2:
;
;  In UBC's version of How to Code, there are often more than 800 students taking
;  the course in any given semester, meaning there are often over 40 Teaching Assistants.
;
;  Designing a schedule for them by hand is hard work - luckily we've learned enough now to write
;  a program to do it for us!
;
;  Below are some data definitions for a simplified version of a TA schedule. There are some
;  number of slots that must be filled, each represented by a natural number. Each TA is
;  available for some of these slots, and has a maximum number of shifts they can work.
;
;  Design a search program that consumes a list of TAs and a list of Slots, and produces one
;  valid schedule where each Slot is assigned to a TA, and no TA is working more than their
;  maximum shifts. If no such schedules exist, produce false.
;
;  You should supplement the given check-expects and remember to follow the recipe!

;; Slot is Natural
;; interp. each TA slot has a number, is the same length, and none overlap

(define-struct ta (name max avail))
;; TA is (make-ta String Natural (listof Slot))
;; interp. the TA's name, number of slots they can work, and slots they're available for
(define SOBA (make-ta "Soba" 2 (list 1 3)))
(define UDON (make-ta "Udon" 1 (list 3 4)))
(define RAMEN (make-ta "Ramen" 1 (list 2)))
(define NOODLE-TAs (list SOBA UDON RAMEN))
(define ERIKA (make-ta "Erika" 1 (list 1 3 7 9)))
(define RYAN (make-ta "Ryan" 1 (list 1 8 10)))
(define REECE (make-ta "Reece" 1 (list 5 6)))
(define GORDON (make-ta "Gordon" 2 (list 2 3 9)))
(define DAVID (make-ta "David" 2 (list 2 8 9)))
(define KATIE (make-ta "Katie" 1 (list 4 6)))
(define AASHISH (make-ta "Aashish" 2 (list 1 10)))
(define GRANT (make-ta "Grant" 2 (list 1 11)))
(define RAEANNE (make-ta "Raeanne" 2 (list 1 11 12)))
(define ALEX (make-ta "Alex" 1 (list 7)))
(define ERIN (make-ta "Erin" 1 (list 4)))
(define QUIZ-TAs-1 (list ERIKA RYAN REECE GORDON DAVID KATIE AASHISH GRANT RAEANNE))
(define QUIZ-TAs-2 (list ERIKA RYAN REECE GORDON DAVID KATIE AASHISH GRANT RAEANNE ALEX))
(define QUIZ-TAs-3 (list ERIKA RYAN REECE GORDON DAVID KATIE AASHISH GRANT RAEANNE ERIN))

(define-struct assignment (ta slot))
;; Assignment is (make-assignment TA Slot)
;; interp. the TA is assigned to work the slot
(define EXAMPLE-ASSIGNMENT (make-assignment UDON 4))
;; Schedule is (listof Assignment)


;; ============================= FUNCTIONS
;; (listof TA) (listof Slot) -> Schedule or false
;; produce valid schedule given TAs and Slots; false if impossible
(check-expect (solve empty empty) empty)
(check-expect (solve empty (list 1 2)) false)
(check-expect (solve (list SOBA) empty) empty)
(check-expect (solve (list SOBA) (list 1))
              (list (make-assignment SOBA 1)))
(check-expect (solve (list SOBA) (list 2)) false)

(check-expect (solve (list SOBA) (list 1 3))
              (list (make-assignment SOBA 1)
                    (make-assignment SOBA 3)))

(check-expect (solve NOODLE-TAs (list 1 2 3 4))
              (list
               (make-assignment SOBA 1)
               (make-assignment SOBA 3)
               (make-assignment UDON 4)
               (make-assignment RAMEN 2)))
(check-expect (solve NOODLE-TAs (list 1 2 3 4 5)) false)
#;
(check-expect (solve QUIZ-TAs-1
                     (list 1 2 3 4 5 6 7 8 9 10 11 12))
              false)
#;
(check-expect (solve QUIZ-TAs-2
                     (list 1 2 3 4 5 6 7 8 9 10 11 12))
              false)
#;
(check-expect (solve QUIZ-TAs-3
                     (list 1 2 3 4 5 6 7 8 9 10 11 12))
              true)
;(define (solve tas slots) empty) ;stub

(define (solve tas slots)
  (local [(define (solve-schedule tas slots schedule)
            (if (filled? schedule slots)
                schedule
                (solve-los tas slots (next-schedules schedule tas))))

          (define (solve-los tas slots los)
            (cond [(empty? los) false]
                  [else
                   (local [(define try (solve-schedule tas slots (first los)))]
                     (if (not (false? try))
                         try
                         (solve-los tas slots (rest los))))]))]
    (solve-schedule tas slots empty)))


;; Schedule (listof TA) -> (listof Schedule)
;; produce list of next possible valid schedules
(check-expect (next-schedules empty empty) empty)
(check-expect (next-schedules (list (make-assignment SOBA 1)) empty) empty)
(check-expect (next-schedules empty (list SOBA))
              (list
               (list (make-assignment SOBA 1))
               (list (make-assignment SOBA 3))))
(check-expect (next-schedules empty (list SOBA UDON))
              (list
               (list (make-assignment SOBA 1))
               (list (make-assignment SOBA 3))
               (list (make-assignment UDON 3))
               (list (make-assignment UDON 4))))
(check-expect (next-schedules (list (make-assignment SOBA 3)) (list RAMEN UDON SOBA))
              (list
               (list (make-assignment SOBA 3) (make-assignment RAMEN 2))
               (list (make-assignment SOBA 3) (make-assignment UDON 4))
               (list (make-assignment SOBA 3) (make-assignment SOBA 1))))
(check-expect (next-schedules (list (make-assignment UDON 3)) (list RAMEN UDON SOBA))
              (list
               (list (make-assignment UDON 3) (make-assignment RAMEN 2))
               (list (make-assignment UDON 3) (make-assignment SOBA 1))))
;(define (next-schedules schedule tas) empty);stub
(define (next-schedules schedule tas)
  (cond [(empty? tas) empty]
        [else
         (local [(define try (add-ta-possible? schedule (first tas)))
                 (define remaining (next-schedules schedule (rest tas)))]
           (if try
               (append
                (add-ta-to-schedule schedule (first tas))
                remaining)
               remaining))]))


;; Schedule TA -> (listof Schedule) or false
;; produce list of extended schedules with assignments available from TA, false otherwise
;; ASSUME: it is possible to add at least one assignment from TA to schedule
;;         i.e. (add-ta-possible? schedule ta) is true
(check-expect (add-ta-to-schedule
               empty
               SOBA)
              (list
               (list (make-assignment SOBA 1))
               (list (make-assignment SOBA 3))))
(check-expect (add-ta-to-schedule
               (list (make-assignment SOBA 1))
               SOBA)
              (list
               (list (make-assignment SOBA 1)
                     (make-assignment SOBA 3))))
(check-expect (add-ta-to-schedule
               (list (make-assignment SOBA 3))
               SOBA)
              (list
               (list (make-assignment SOBA 3)
                     (make-assignment SOBA 1))))
(check-expect (add-ta-to-schedule
               (list (make-assignment SOBA 3))
               UDON)
              (list
               (list (make-assignment SOBA 3)
                     (make-assignment UDON 4))))
;(define (add-ta-to-schedule schedule ta) empty);stub
(define (add-ta-to-schedule schedule ta)
  (local [(define (fn-for-avail schedule avail)
            (cond [(empty? avail) empty]
                  [else
                   (local [(define try (add-one-slot schedule ta (first avail)))
                           (define remaining (fn-for-avail schedule (rest avail)))]
                     (if (not (false? try))
                         (cons
                          try
                          remaining)
                         remaining))]))]
    (fn-for-avail schedule (ta-avail ta))))


;; Schedule TA -> Boolean
;; produce true if it is possible to add an assignment of given TA to given schedule
(check-expect (add-ta-possible?
               (list (make-assignment SOBA 1)
                     (make-assignment SOBA 3))
               SOBA) false)
(check-expect (add-ta-possible?
               (list (make-assignment SOBA 1))
               SOBA) true)
;(define (add-ta-possible? schedule ta) false);stub
(define (add-ta-possible? schedule ta)
  (ormap (λ (slot) (add-one-slot? schedule ta slot)) (ta-avail ta)))


;; Schedule TA Slot -> Boolean
;; produce false if add-one-slot produces false, true otherwise
(check-expect (add-one-slot? empty SOBA 1)
              true)
(check-expect (add-one-slot?
               (list (make-assignment SOBA 1))
               SOBA 1)
              false)
(check-expect (add-one-slot?
               (list (make-assignment SOBA 1))
               SOBA 3)
              true)
;(define (add-one-slot? schedule ta slot) false);stub
(define (add-one-slot? schedule ta slot)
  (if (false? (add-one-slot schedule ta slot))
      false
      true))


;; Schedule TA Slot -> Schedule
;; produce valid schedule by adding assignment of TA in Slot, false otherwise
;; ASSUME: Slot is among TA's avail
(check-expect (add-one-slot empty SOBA 1)
              (list (make-assignment SOBA 1)))
(check-expect (add-one-slot
               (list (make-assignment SOBA 1))
               SOBA 1)
              false)
(check-expect (add-one-slot
               (list (make-assignment SOBA 1))
               SOBA 3)
              (list
               (make-assignment SOBA 1)
               (make-assignment SOBA 3)))
;(define (add-one-slot schedule ta slot) empty);stub
(define (add-one-slot schedule ta slot)
  (if (and
       (no-conflict? (make-assignment ta slot) schedule)
       (not-maxed-out? schedule ta))
      (append
       schedule
       (list (make-assignment ta slot)))
      false))


;; Schedule (listof Slot) -> Boolean
;; produce true if given schedule fills all given slots
;; ASSUME: schedule is valid
(check-expect (filled? empty empty) true)
(check-expect (filled? empty (list 1 2)) false)
(check-expect (filled? (list (make-assignment SOBA 1)) empty) true)
(check-expect (filled? (list (make-assignment SOBA 1)) (list 1)) true)
(check-expect (filled? (list (make-assignment SOBA 1)) (list 2)) false)
(check-expect (filled? (list (make-assignment SOBA 1)
                             (make-assignment SOBA 3)) (list 1 3)) true)
(check-expect (filled? (list (make-assignment UDON 3)) (list 3)) true)
(check-expect (filled? (list (make-assignment UDON 4)) (list 4)) true)
(check-expect (filled? (list (make-assignment UDON 3)) (list 3 4)) false)
(check-expect (filled? (list (make-assignment UDON 3)) (list 1)) false)
(check-expect (filled?  (list
                         (make-assignment UDON 4)
                         (make-assignment SOBA 3)
                         (make-assignment RAMEN 2)
                         (make-assignment SOBA 1))
                        (list 1 2 3 4)) true)
(check-expect (filled?  (list
                         (make-assignment UDON 4)
                         (make-assignment SOBA 3)
                         (make-assignment RAMEN 2)
                         (make-assignment SOBA 1))
                        (list 1 2 3)) true)
(check-expect (filled?  (list
                         (make-assignment UDON 4)
                         (make-assignment SOBA 3)
                         (make-assignment RAMEN 2)
                         (make-assignment SOBA 1))
                        (list 1 2)) true)
(check-expect (filled?  (list
                         (make-assignment UDON 4)
                         (make-assignment SOBA 3)
                         (make-assignment RAMEN 2)
                         (make-assignment SOBA 1))
                        (list 1 2 3 4 5)) false)
;(define (filled? schedule slots) false);stub
(define (filled? schedule slots)
  (cond [(empty? slots) true]
        [else
         (andmap
          (λ (slot) (have-slot? slot schedule))
          slots)]))


;; Schedule -> Boolean
;; produce true if given schedule is valid: no TA works more hours than max, and no conflicts
;; ASSUME: each assignment in schedule is valid: no TA is assigned a slot they are not available for
(check-expect (valid? empty) true)
(check-expect (valid? (list (make-assignment SOBA 1))) true)
(check-expect (valid? (list (make-assignment SOBA 1) (make-assignment SOBA 3))) true)
(check-expect (valid? (list (make-assignment SOBA 3) (make-assignment UDON 3))) false)
(check-expect (valid? (list (make-assignment UDON 3) (make-assignment UDON 4))) false)
(check-expect (valid? (list (make-assignment SOBA 3) (make-assignment RAMEN 2))) true)
;(define (valid? schedule) false);stub
(define (valid? schedule)
  (cond [(empty? schedule) true]
        [else
         (and
          (not-exceeded? schedule (assignment-ta (first schedule)))
          (no-conflict? (first schedule) (rest schedule))
          (valid? (rest schedule)))]))


;; Schedule -> (listof Slot)
;; produce list of slots that appear in a schedule
;; ASSUME: schedule is valid
(check-expect (list-of-slots empty) empty)
(check-expect (list-of-slots
               (list (make-assignment SOBA 3)
                     (make-assignment UDON 4))) (list 4 3))
(check-expect (list-of-slots
               (list
                (make-assignment UDON 4)
                (make-assignment SOBA 3)
                (make-assignment RAMEN 2)
                (make-assignment SOBA 1))) (list 1 2 3 4))
;(define (list-of-slots schedule) empty);stub
(define (list-of-slots schedule)
  ;; acc is (listof Slot); result so far accumulator
  ;; interp. the list of slots seen in schedule so far
  (local [(define (fn-for-schedule schedule acc)
            (cond [(empty? schedule) acc]
                  [else
                   (fn-for-schedule
                    (rest schedule)
                    (cons
                     (assignment-slot (first schedule))
                     acc))]))]
    (fn-for-schedule schedule empty)))


;; Slot Schedule -> Boolean
;; produce true if slot appears in schedule
;; ASSUME: schedule is valid
(check-expect (have-slot? 3 empty) false)
(check-expect (have-slot?
               1
               (list (make-assignment SOBA 1))) true)
(check-expect (have-slot?
               2
               (list (make-assignment SOBA 3)
                     (make-assignment RAMEN 2))) true)
(check-expect (have-slot?
               5
               (list
                (make-assignment UDON 4)
                (make-assignment SOBA 3)
                (make-assignment RAMEN 2)
                (make-assignment SOBA 1))) false)
;(define (have-slot? slot schedule) false);stub
(define (have-slot? slot schedule)
  ;; acc is Boolean; result so far accumulator
  ;; interp. true if slot has been seen in schedule so far, false otherwise
  (local [(define (fn-for-schedule slot schedule acc)
            (cond [(empty? schedule) acc]
                  [else
                   (fn-for-schedule
                    slot
                    (rest schedule)
                    (if (= slot (assignment-slot (first schedule)))
                        (or acc true)
                        acc))]))]
    (fn-for-schedule slot schedule false)))


;; Assignment Schedule -> Boolean
;; produce true if assignment does not have a conflicting slot with schedule
;; ASSUME: Schedule is valid
(check-expect (no-conflict? (make-assignment SOBA 1) empty) true)
(check-expect (no-conflict?
               (make-assignment SOBA 1)
               (list (make-assignment SOBA 1))) false)
(check-expect (no-conflict?
               (make-assignment SOBA 3)
               (list (make-assignment UDON 3))) false)
(check-expect (no-conflict?
               (make-assignment SOBA 1)
               (list (make-assignment SOBA 3)
                     (make-assignment UDON 4))) true)
;(define (no-conflict? assignment schedule) false);stub
(define (no-conflict? a s)
  (cond [(empty? s) true]
        [else
         (and
          (not (= (assignment-slot a) (assignment-slot (first s))))
          (no-conflict? a (rest s)))]))


;; Schedule TA -> Boolean
;; produce true if TA appears in schedule less than their max hours
(check-expect (not-maxed-out?
               empty UDON) true)
(check-expect (not-maxed-out?
               (list (make-assignment UDON 3)) UDON) false)
(check-expect (not-maxed-out?
               (list (make-assignment UDON 3) (make-assignment UDON 4)) UDON) false)
(check-expect (not-maxed-out?
               (list (make-assignment SOBA 3)) SOBA) true)
(check-expect (not-maxed-out?
               (list (make-assignment SOBA 3) (make-assignment SOBA 1)) SOBA) false)
;(define (not-maxed-out? schedule ta) false);stub
(define (not-maxed-out? schedule ta)
  (< (count-ta schedule ta)
     (ta-max ta)))


;; Schedule TA -> Boolean
;; produce true if TA appears in schedule less than their max hours
(check-expect (not-exceeded?
               empty UDON) true)
(check-expect (not-exceeded?
               (list (make-assignment UDON 3)) UDON) true)
(check-expect (not-exceeded?
               (list (make-assignment UDON 3) (make-assignment UDON 4)) UDON) false)
(check-expect (not-exceeded?
               (list (make-assignment SOBA 3)) SOBA) true)
(check-expect (not-exceeded?
               (list (make-assignment SOBA 3) (make-assignment SOBA 1)) SOBA) true)
;(define (not-exceeded? schedule ta) false);stub
(define (not-exceeded? schedule ta)
  (<= (count-ta schedule ta)
      (ta-max ta)))


;; Schedule TA -> Natural
;; produces number of occurences of TA in a schedule
(check-expect (count-ta empty SOBA) 0)
(check-expect (count-ta (list (make-assignment SOBA 1)) SOBA) 1)
(check-expect (count-ta (list (make-assignment SOBA 1)) UDON) 0)
(check-expect (count-ta (list (make-assignment SOBA 1) (make-assignment SOBA 3)) SOBA) 2)
(check-expect (count-ta (list (make-assignment SOBA 3) (make-assignment UDON 3)) UDON) 1)
(check-expect (count-ta (list (make-assignment UDON 3) (make-assignment UDON 4)) UDON) 2)
(check-expect (count-ta (list (make-assignment SOBA 3) (make-assignment RAMEN 2)) RAMEN) 1)
;(define (count-ta schedule ta) 0);stub
(define (count-ta schedule ta)
  ;; todo is (listof Assignment); worklist accumulator
  ;; interp. remaining assignments that need to be gone through
  ;; count is Natural; result so far accumulator
  ;; interp. the number of occurences of TA in schedule so far
  (local [(define (fn-for-schedule todo ta count)
            (cond [(empty? todo) count]
                  [else
                   (fn-for-schedule
                    (rest todo)
                    ta
                    (if (equal? (assignment-ta (first todo)) ta)
                        (add1 count)
                        count))]))]
    (fn-for-schedule schedule ta 0)))


;; Assignment -> Boolean
;; produce true if given assignment is valid: TA not assigned a slot they are not available for
;(check-expect (valid-assignment? (make-assignment SOBA 1)) true)
;(check-expect (valid-assignment? (make-assignment SOBA 2)) false)
;(define (valid-assignment? a) false);stub
#;
(define (valid-assignment? a)
  (member? (assignment-slot) (ta-avail (assignment-ta a))))


;; (listof TA) (listof Slot) -> Schedule
;; crate schedule from given list of TAs to fill given slots
;; ASSUME: (solved? tas slots) is true
