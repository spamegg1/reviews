(require 2htdp/image)
(require 2htdp/universe)

;; countdown-animation-solution.rkt

;
; PROBLEM:
;
; Design a world program that represents a countdown. The program should
; display the number of seconds remaining and should decrease at each
; clock tick. Upon reaching zero, it should stay there and not change.
;
; To make your countdown progress at a reasonable speed, you can use the
; rate option to on-tick. If you say, for example,
; (on-tick advance-countdown 1) then big-bang will wait 1 second between
; calls to advance-countdown.
;
; Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
;
; Once you are finished the simple version of the program, you can improve
; it by reseting the countdown to ten when you press the spacebar.
;


;; A simple countdown animation.

;; =================
;; Constants:

(define WIDTH 50)
(define HEIGHT 50)

(define CTR-X (/ WIDTH 2))
(define CTR-Y (/ HEIGHT 2))

(define MTS (empty-scene WIDTH HEIGHT))

(define TEXT-SIZE 24)
(define TEXT-COLOR "black")

;; =================
;; Data definitions:

;; Countdown is Natural[0, 10]
;; interp. the current seconds remaining in the countdown
(define CD1 10)  ;countdown hasn't started
(define CD2 5)   ;countdown in progress
(define CD3 0)   ;countdown finished

#;
(define (fn-for-countdown cd)
  (... cd))

;; Template rules used:
;; - atomic non-distinct: Natural[0, 10]



;; =================
;; Functions:

;; Countdown -> Countdown
;; called to run the animation; start with (main 10)
;; no tests for main function
(define (main cd)
  (big-bang cd
            (on-tick advance-countdown 1)   ; Countdown -> Countdown
            (to-draw render-countdown)      ; Countdown -> Image
            (on-key handle-key)))           ; Countdown KeyEvent -> Countdown



;; Countdown -> Countdown
;; if cd is zero, produce zero, otherwise subtract 1
(check-expect (advance-countdown 10) 9)
(check-expect (advance-countdown 1) 0)
(check-expect (advance-countdown 0) 0)

#;
(define (advance-countdown cd) 0) ; stub
;<template from Countdown>

(define (advance-countdown cd)
  (if (= 0 cd)
      0
      (- cd 1)))



;; Countdown -> Image
;; produce an appropriate image for the countdown
(check-expect (render-countdown 10) (place-image (text "10" TEXT-SIZE TEXT-COLOR)
                                                 CTR-X CTR-Y
                                                 MTS))
(check-expect (render-countdown 0) (place-image (text "0" TEXT-SIZE TEXT-COLOR)
                                                CTR-X CTR-Y
                                                MTS))

#;
(define (render-countdown cd) MTS) ;stub
;<template from Countdown>

(define (render-countdown cd)
  (place-image (text (number->string cd) TEXT-SIZE TEXT-COLOR)
               CTR-X CTR-Y
               MTS))


;; Countdown KeyEvent -> Countdown
;; reset the countdown to 10 when the spacebar is pressed
(check-expect (handle-key 0 " ") 10)
(check-expect (handle-key 5 " ") 10)
(check-expect (handle-key 5 "left") 5)

#;
(define (handle-key cd ke)    ;stub
  0)

;<template from KeyEvent>

(define (handle-key cd ke)
  (cond [(key=? ke " ") 10]
        [else cd]))
