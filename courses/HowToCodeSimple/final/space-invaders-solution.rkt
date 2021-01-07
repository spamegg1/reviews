(require 2htdp/universe)
(require 2htdp/image)

;; Space Invaders


;; Constants:

(define WIDTH  300)
(define HEIGHT 500)

(define INVADER-X-SPEED 1.5)  ;speeds (not velocities) in pixels per tick
(define INVADER-Y-SPEED 1.5)
(define TANK-SPEED 2)
(define MISSILE-SPEED 10)

(define HIT-RANGE 10)

(define INVADE-RATE 100)

(define BACKGROUND (empty-scene WIDTH HEIGHT))

(define INVADER
  (overlay/xy (ellipse 10 15 "outline" "blue")              ;cockpit cover
              -5 6
              (ellipse 20 10 "solid"   "blue")))            ;saucer

(define TANK
  (overlay/xy (overlay (ellipse 28 8 "solid" "black")       ;tread center
                       (ellipse 30 10 "solid" "green"))     ;tread outline
              5 -14
              (above (rectangle 5 10 "solid" "black")       ;gun
                     (rectangle 20 10 "solid" "black"))))   ;main body

(define TANK-HEIGHT/2 (/ (image-height TANK) 2))

(define MISSILE (ellipse 5 15 "solid" "red"))



;; Data Definitions:

(define-struct game (invaders missiles tank))
;; GameState is (make-game  (listof Invader) (listof Missile) Tank)
;; interp. the current state of a space invaders game
;;         with the current invaders, missiles and tank position

;; GameState constants defined below Missile data definition

#;
(define (fn-for-game gs)
  (... (fn-for-loinvader (game-invaders gs))
       (fn-for-lom (game-missiles gs))
       (fn-for-tank (game-tank gs))))



(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, HEIGHT - TANK-HEIGHT/2 in screen coordinates
;;         the tank moves TANK-SPEED pixels per clock tick left if dir -1, right if dir 1

(define T0 (make-tank (/ WIDTH 2) 1))   ;center going right
(define T1 (make-tank 50 1))            ;going right
(define T2 (make-tank 50 -1))           ;going left

#;
(define (fn-for-tank t)
  (... (tank-x t) (tank-dir t)))



(define-struct invader (x y dx))
;; Invader is (make-invader Number Number Number)
;; interp. the invader is at (x, y) in screen coordinates
;;         the invader along x by dx pixels per clock tick

(define I1 (make-invader 150 100 1))           ;not landed, moving right
(define I2 (make-invader 150 HEIGHT -1))       ;exactly landed, moving left
(define I3 (make-invader 150 (+ HEIGHT 10) 1)) ;> landed, moving right


#;
(define (fn-for-invader invader)
  (... (invader-x invader) (invader-y invader) (invader-dx invader)))


(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. the missile's location is x y in screen coordinates

(define M1 (make-missile 150 300))                       ;not hit I1
(define M2 (make-missile (invader-x I1) (+ (invader-y I1) 10)))  ;exactly hit I1
(define M3 (make-missile (invader-x I1) (+ (invader-y I1)  5)))  ;> hit I1

#;
(define (fn-for-missile m)
  (... (missile-x m) (missile-y m)))


;; GameState constants
(define GS0 (make-game empty empty T0))
(define GS1 (make-game empty empty T1))
(define GS2 (make-game (list I1) (list M1) T1))
(define GS3 (make-game (list I1 I2) (list M1 M2) T1))




;; =================
;; Functions:

;; MAIN
;; GameState -> GameState
;; runs the Space Invaders game
;; start the world with (main GS0)
;; <no tests for main functions>
(define (main gs)
  (big-bang gs                  ; GameState
    (on-tick   next-state)      ; GameState -> GameState
    (to-draw   render-state)    ; GameState -> Image
    (on-key    handle-key)      ; GameState KeyEvent -> GameState
    (stop-when game-over?)))    ; GameState -> Boolean




;; NEXT-STATE
;; GameState -> GameState
;; update tank, invaders, missiles
;; <no tests for random functions>
;(define (next-state gs) GS0) ;stub
;<template from GameState>
(define (next-state gs)
  (cond [(< (random INVADE-RATE) 2)
         (make-game
          (append (list (make-invader (random WIDTH) 0 INVADER-X-SPEED))
                  (next-invaders (game-invaders gs) (game-missiles gs)))
          (next-missiles (game-invaders gs) (game-missiles gs))
          (next-tank (game-tank gs)))]
        [else
         (make-game (next-invaders (game-invaders gs) (game-missiles gs))
                    (next-missiles (game-invaders gs) (game-missiles gs))
                    (next-tank (game-tank gs)))]))




;; COLLISION-INVADER
;; Invader (listof Missile) -> Boolean
;; produces true if given invader is within hit range
;; of any one of given missiles, false otherwise
(check-expect (collision-invader? I1 (list M1)) false)
(check-expect (collision-invader? I1 (list M1 M2)) true)
(check-expect (collision-invader? I1 (list M1 M2 M3)) true)
;(define (collision-invader? i lom) false) ;stub
(define (collision-invader? i lom)
  (cond [(empty? lom) false]
        [(and
          (<= (- (invader-x i) HIT-RANGE)
              (missile-x (first lom))
              (+ (invader-x i) HIT-RANGE))
          (<= (- (invader-y i) HIT-RANGE)
              (missile-y (first lom))
              (+ (invader-y i) HIT-RANGE)))
         true]
        [else (collision-invader? i (rest lom))]))




;; NEXT-INVADERS
;; (listof Invader) (listof Missile) -> (listof Invader)
;; produces updated list of invaders with new positions
(check-expect (next-invaders (list I1) (list M1))
              (cond [(empty? (list I1)) empty]
                    [(collision-invader? (first (list I1)) (list M1))
                     (next-invaders (rest (list I1)) (list M1))]
                    [else
                     (append (list (next-invader (first (list I1))))
                             (next-invaders (rest (list I1)) (list M1)))]))
(check-expect (next-invaders (list I1 I2) (list M1 M2))
              (cond [(empty? (list I1 I2)) empty]
                    [(collision-invader? (first (list I1 I2)) (list M1 M2))
                     (next-invaders (rest (list I1 I2)) (list M1 M2))]
                    [else
                     (append (list (next-invader (first (list I1 I2))))
                             (next-invaders (rest (list I1 I2)) (list M1 M2)))]))
(check-expect (next-invaders (list I1 I2 I3) (list M1 M2 M3))
              (cond [(empty? (list I1 I2 I3)) empty]
                    [(collision-invader? (first (list I1 I2 I3)) (list M1 M2 M3))
                     (next-invaders (rest (list I1 I2 I3)) (list M1 M2 M3))]
                    [else
                     (append (list (next-invader (first (list I1 I2 I3))))
                             (next-invaders (rest (list I1 I2 I3)) (list M1 M2 M3)))]))
;(define (next-invaders loi lom) loi) ;stub
;<template from Invader>
(define (next-invaders loi lom)
  (cond [(empty? loi) empty]
        [(collision-invader? (first loi) lom) (next-invaders (rest loi) lom)]
        [else
         (append (list (next-invader (first loi)))
                 (next-invaders (rest loi) lom))]))




;; NEXT-INVADER
;; Invader -> Invader
;; produces updated invader
(check-expect (next-invader I1) (make-invader 151 101 1))
(check-expect (next-invader (make-invader 1 100 -2)) (make-invader 0 100 2))
(check-expect (next-invader (make-invader (- WIDTH 1) 100 2))
              (make-invader WIDTH 100 -2))
;(define (next-invader i) i) ;stub
(define (next-invader i)
  (cond [(< 0 (+ (invader-x i) (invader-dx i)) WIDTH)
         (make-invader
          (+ (invader-x i) (invader-dx i))
          (+ (invader-y i) (abs (invader-dx i)))
          (invader-dx i))]
        [(<= (+ (invader-x i) (invader-dx i)) 0)
         (make-invader 0 (invader-y i) (* -1 (invader-dx i)))]
        [(<= WIDTH (+ (invader-x i) (invader-dx i)))
         (make-invader WIDTH (invader-y i) (* -1 (invader-dx i)))]))




;; COLLISION-MISSILE
;; Missile (listof Invader) -> Boolean
;; produces true if given missile is within hit range
;; of any one of given invaders, false otherwise
(check-expect (collision-missile? M1 (list I1)) false)
(check-expect (collision-missile? M2 (list I1 I2)) true)
(check-expect (collision-missile? M3 (list I1 I2 I3)) true)
;(define (collision-missile? m loi) false) ;stub
(define (collision-missile? m loi)
  (cond [(empty? loi) false]
        [(and
          (<= (- (missile-x m) HIT-RANGE)
              (invader-x (first loi))
              (+ (missile-x m) HIT-RANGE))
          (<= (- (missile-y m) HIT-RANGE)
              (invader-y (first loi))
              (+ (missile-y m) HIT-RANGE)))
         true]
        [else (collision-missile? m (rest loi))]))




;; NEXT-MISSILES
;; (listof Invader) (listof Missile) -> (listof Missile)
;; produces updated list of Missiles with new positions
;; !!!
;(define (next-missiles loi lom) lom) ;stub
;<template from Missile>
(define (next-missiles loi lom)
  (cond [(empty? lom) empty]
        [(<= (- (missile-y (first lom)) MISSILE-SPEED) 0)
         (next-missiles loi (rest lom))]
        [(collision-missile? (first lom) loi)
         (next-missiles loi (rest lom))]
        [else
         (append (list (make-missile
                        (missile-x (first lom))
                        (- (missile-y (first lom)) MISSILE-SPEED)))
                 (next-missiles loi (rest lom)))]))




;; NEXT-TANK
;; Tank -> Tank
;; produces updated Tank with new position
(check-expect (next-tank T0)
              (cond [(< 0 (+ (* TANK-SPEED (tank-dir T0)) (tank-x T0)) WIDTH)
                     (make-tank (+ (* TANK-SPEED (tank-dir T0)) (tank-x T0))
                                (tank-dir T0))]
                    [(<= (+ (* TANK-SPEED (tank-dir T0)) (tank-x T0)) 0)
                     (make-tank 0 (tank-dir T0))]
                    [(<= WIDTH (+ (* TANK-SPEED (tank-dir T0)) (tank-x T0)))
                     (make-tank WIDTH (tank-dir T0))]))
(check-expect (next-tank T1)
              (cond [(< 0 (+ (* TANK-SPEED (tank-dir T1)) (tank-x T1)) WIDTH)
                     (make-tank (+ (* TANK-SPEED (tank-dir T1)) (tank-x T1))
                                (tank-dir T1))]
                    [(<= (+ (* TANK-SPEED (tank-dir T1)) (tank-x T1)) 0)
                     (make-tank 0 (tank-dir T1))]
                    [(<= WIDTH (+ (* TANK-SPEED (tank-dir T1)) (tank-x T1)))
                     (make-tank WIDTH (tank-dir T1))]))
(check-expect (next-tank T2)
              (cond [(< 0 (+ (* TANK-SPEED (tank-dir T2)) (tank-x T2)) WIDTH)
                     (make-tank (+ (* TANK-SPEED (tank-dir T2)) (tank-x T2))
                                (tank-dir T2))]
                    [(<= (+ (* TANK-SPEED (tank-dir T2)) (tank-x T2)) 0)
                     (make-tank 0 (tank-dir T2))]
                    [(<= WIDTH (+ (* TANK-SPEED (tank-dir T2)) (tank-x T2)))
                     (make-tank WIDTH (tank-dir T2))]))
;(define (next-tank t) t) ;stub
;<template from Tank>
(define (next-tank t)
  (cond [(< 0 (+ (* TANK-SPEED (tank-dir t)) (tank-x t)) WIDTH)
         (make-tank (+ (* TANK-SPEED (tank-dir t)) (tank-x t))
                    (tank-dir t))]
        [(<= (+ (* TANK-SPEED (tank-dir t)) (tank-x t)) 0)
         (make-tank 0 (tank-dir t))]
        [(<= WIDTH (+ (* TANK-SPEED (tank-dir t)) (tank-x t)))
         (make-tank WIDTH (tank-dir t))]))




;; RENDER-STATE
;; GameState -> Image
;; produces image, rendering given game state
(check-expect (render-state GS0)
              (render-invaders (game-invaders GS0)
                               (render-missiles (game-missiles GS0)
                                                (render-tank (game-tank GS0) BACKGROUND))))
(check-expect (render-state GS1)
              (render-invaders (game-invaders GS1)
                               (render-missiles (game-missiles GS1)
                                                (render-tank (game-tank GS1) BACKGROUND))))
(check-expect (render-state GS2)
              (render-invaders (game-invaders GS2)
                               (render-missiles (game-missiles GS2)
                                                (render-tank (game-tank GS2) BACKGROUND))))
(check-expect (render-state GS3)
              (render-invaders (game-invaders GS3)
                               (render-missiles (game-missiles GS3)
                                                (render-tank (game-tank GS3) BACKGROUND))))
;(define (render-state gs) gs) ;stub
;<template from GameState>
(define (render-state gs)
  (render-invaders (game-invaders gs)
                   (render-missiles (game-missiles gs)
                                    (render-tank (game-tank gs) BACKGROUND))))




;; RENDER-INVADERS
;; (listof Invader) Image -> Image
;; produces image of given list of invaders
(check-expect (render-invaders (game-invaders GS0) BACKGROUND) BACKGROUND)
(check-expect (render-invaders (game-invaders GS1) BACKGROUND) BACKGROUND)
(check-expect (render-invaders (game-invaders GS2) BACKGROUND)
              (place-image INVADER
                           (invader-x (first (game-invaders GS2)))
                           (invader-y (first (game-invaders GS2)))
                           (render-invaders (rest (game-invaders GS2))
                                            BACKGROUND)))
(check-expect (render-invaders (game-invaders GS3) BACKGROUND)
              (place-image INVADER
                           (invader-x (first (game-invaders GS3)))
                           (invader-y (first (game-invaders GS3)))
                           (render-invaders (rest (game-invaders GS3))
                                            BACKGROUND)))
;(define (render-invaders lst BACKGROUND) BACKGROUND) ;stub
;<template from Invader with additional atomic parameter>
(define (render-invaders lst img)
  (cond [(empty? lst) img]
        [else
         (place-image INVADER
                      (invader-x (first lst))
                      (invader-y (first lst))
                      (render-invaders (rest lst)
                                       img))]))




;; RENDER-MISSILES
;; (listof Missile) Image -> Image
;; produces image of given list of missiles
(check-expect (render-missiles (game-missiles GS0) BACKGROUND) BACKGROUND)
(check-expect (render-missiles (game-missiles GS1) BACKGROUND) BACKGROUND)
(check-expect (render-missiles (game-missiles GS2) BACKGROUND)
              (place-image MISSILE
                           (missile-x (first (game-missiles GS2)))
                           (missile-y (first (game-missiles GS2)))
                           (render-missiles (rest (game-missiles GS2))
                                            BACKGROUND)))
(check-expect (render-missiles (game-missiles GS3) BACKGROUND)
              (place-image MISSILE
                           (missile-x (first (game-missiles GS3)))
                           (missile-y (first (game-missiles GS3)))
                           (render-missiles (rest (game-missiles GS3))
                                            BACKGROUND)))
;(define (render-missiles lst img) img) ;stub
;<template from Missile with additional atomic parameter>
(define (render-missiles lst img)
  (cond [(empty? lst) img]
        [else
         (place-image MISSILE
                      (missile-x (first lst))
                      (missile-y (first lst))
                      (render-missiles (rest lst)
                                       img))]))




;; RENDER-TANK
;; Tank Image -> Image
;; produces tank image on top of given image, given tank
(check-expect (render-tank T0 BACKGROUND)
              (place-image TANK
                           (tank-x T0)
                           (- HEIGHT TANK-HEIGHT/2)
                           BACKGROUND))
(check-expect (render-tank T1 BACKGROUND)
              (place-image TANK
                           (tank-x T1)
                           (- HEIGHT TANK-HEIGHT/2)
                           BACKGROUND))
(check-expect (render-tank T2 BACKGROUND)
              (place-image TANK
                           (tank-x T2)
                           (- HEIGHT TANK-HEIGHT/2)
                           BACKGROUND))
;(define (render-tank t img) BACKGROUND) ;stub
;<template from Tank with additional atomic parameter>
(define (render-tank t img)
  (place-image TANK
               (tank-x t)
               (- HEIGHT TANK-HEIGHT/2)
               img))




;; HANDLE-KEY
;; GameState KeyEvent -> GameState
;; move tank left/right on left/right arrow key press
;; shoot missiles on space key press
(check-expect (handle-key GS0 "u") GS0)
(check-expect (handle-key GS1 "left") (handle-key-tank-left GS1))
(check-expect (handle-key GS2 "right") (handle-key-tank-right GS2))
(check-expect (handle-key GS3 " ") (handle-key-missile GS3))
;(define (handle-key gs ke) GS0)   ;stub
;<template from KeyEvent>
(define (handle-key gs ke)
  (cond [(key=? ke " ")     (handle-key-missile gs)]
        [(key=? ke "left")  (handle-key-tank-left gs)]
        [(key=? ke "right") (handle-key-tank-right gs)]
        [else gs]))




;; HANDLE-KEY-MISSILE
;; GameState -> GameState
;; produces new game state with additional missile at tank location
(check-expect (handle-key-missile GS0)
              (make-game (game-invaders GS0)
                         (append (game-missiles GS0)
                                 (list (make-missile
                                        (tank-x (game-tank GS0))
                                        (- HEIGHT TANK-HEIGHT/2))))
                         (game-tank GS0)))
(check-expect (handle-key-missile GS1)
              (make-game (game-invaders GS1)
                         (append (game-missiles GS1)
                                 (list (make-missile
                                        (tank-x (game-tank GS1))
                                        (- HEIGHT TANK-HEIGHT/2))))
                         (game-tank GS1)))
(check-expect (handle-key-missile GS2)
              (make-game (game-invaders GS2)
                         (append (game-missiles GS2)
                                 (list (make-missile
                                        (tank-x (game-tank GS2))
                                        (- HEIGHT TANK-HEIGHT/2))))
                         (game-tank GS2)))
(check-expect (handle-key-missile GS3)
              (make-game (game-invaders GS3)
                         (append (game-missiles GS3)
                                 (list (make-missile
                                        (tank-x (game-tank GS3))
                                        (- HEIGHT TANK-HEIGHT/2))))
                         (game-tank GS3)))
;(define (handle-key-missile gs) gs) ;stub
;<template from GameState>
(define (handle-key-missile gs)
  (make-game (game-invaders gs)
             (append (game-missiles gs)
                     (list (make-missile
                            (tank-x (game-tank gs))
                            (- HEIGHT TANK-HEIGHT/2))))
             (game-tank gs)))




;; HANDLE-KEY-TANK-LEFT
;; GameState -> GameState
;; produce new game state with tank-dx changed to -1
(check-expect (handle-key-tank-left GS0)
              (make-game (game-invaders GS0)
                         (game-missiles GS0)
                         (make-tank (tank-x (game-tank GS0)) -1)))
(check-expect (handle-key-tank-left GS1)
              (make-game (game-invaders GS1)
                         (game-missiles GS1)
                         (make-tank (tank-x (game-tank GS1)) -1)))
(check-expect (handle-key-tank-left GS2)
              (make-game (game-invaders GS2)
                         (game-missiles GS2)
                         (make-tank (tank-x (game-tank GS2)) -1)))
(check-expect (handle-key-tank-left GS3)
              (make-game (game-invaders GS3)
                         (game-missiles GS3)
                         (make-tank (tank-x (game-tank GS3)) -1)))
;(define (handle-key-tank-left gs) gs) ;stub
;<template from GameState>
(define (handle-key-tank-left gs)
  (make-game (game-invaders gs)
             (game-missiles gs)
             (make-tank (tank-x (game-tank gs)) -1)))




;; HANDLE-KEY-TANK-RIGHT
;; GameState -> GameState
;; produce new game state with tank-dx changed to 1
(check-expect (handle-key-tank-right GS0)
              (make-game (game-invaders GS0)
                         (game-missiles GS0)
                         (make-tank (tank-x (game-tank GS0)) 1)))
(check-expect (handle-key-tank-right GS1)
              (make-game (game-invaders GS1)
                         (game-missiles GS1)
                         (make-tank (tank-x (game-tank GS1)) 1)))
(check-expect (handle-key-tank-right GS2)
              (make-game (game-invaders GS2)
                         (game-missiles GS2)
                         (make-tank (tank-x (game-tank GS2)) 1)))
(check-expect (handle-key-tank-right GS3)
              (make-game (game-invaders GS3)
                         (game-missiles GS3)
                         (make-tank (tank-x (game-tank GS3)) 1)))
;(define (handle-key-tank-right gs) gs) ;stub
;<template from GameState>
(define (handle-key-tank-right gs)
  (make-game (game-invaders gs)
             (game-missiles gs)
             (make-tank (tank-x (game-tank gs)) 1)))




;; GAME-OVER
;; GameState -> Boolean
;; produces true when an invader reaches bottom of window, false otherwise
(check-expect (game-over? GS0)
              (cond[(empty? (game-invaders GS0)) false]
                   [else
                    (cond [(<= HEIGHT (invader-y (first (game-invaders GS0)))) true]
                          [else (game-over? (make-game
                                             (rest (game-invaders GS0))
                                             (game-missiles GS0)
                                             (game-tank GS0)))])]))
(check-expect (game-over? GS1)
              (cond[(empty? (game-invaders GS1)) false]
                   [else
                    (cond [(<= HEIGHT (invader-y (first (game-invaders GS1)))) true]
                          [else (game-over? (make-game
                                             (rest (game-invaders GS1))
                                             (game-missiles GS1)
                                             (game-tank GS1)))])]))
(check-expect (game-over? GS2)
              (cond[(empty? (game-invaders GS2)) false]
                   [else
                    (cond [(<= HEIGHT (invader-y (first (game-invaders GS2)))) true]
                          [else (game-over? (make-game
                                             (rest (game-invaders GS2))
                                             (game-missiles GS2)
                                             (game-tank GS2)))])]))
(check-expect (game-over? GS3)
              (cond[(empty? (game-invaders GS3)) false]
                   [else
                    (cond [(<= HEIGHT (invader-y (first (game-invaders GS3)))) true]
                          [else (game-over? (make-game
                                             (rest (game-invaders GS3))
                                             (game-missiles GS3)
                                             (game-tank GS3)))])]))
;(define (game-over? gs) false) ;stub
;<template from GameState>
(define (game-over? gs)
  (cond[(empty? (game-invaders gs)) false]
       [else
        (cond [(<= HEIGHT (invader-y (first (game-invaders gs)))) true]
              [else (game-over? (make-game
                                 (rest (game-invaders gs))
                                 (game-missiles gs)
                                 (game-tank gs)))])]))
