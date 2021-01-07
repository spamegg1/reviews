(require 2htdp/image)
(require 2htdp/universe)

;;   cowabunga-starter.rkt  problem statement
;; > cowabunga-v0.rkt       has constants
;;   cowabunga-v1.rkt       has data definition
;;   cowabunga-v2.rkt       has main function, wish list entries
;;   cowabunga-v3.rkt       has next-cow
;;   cowabunga-v4.rkt       has render-cow
;;   cowabunga-v5.rkt       has handle-key

;; A cow, meandering back and forth across the screen.




;; =================
;; Constants:

(define WIDTH  400)
(define HEIGHT 200)


(define CTR-Y (/ HEIGHT 2))


(define RCOW .)
(define LCOW .)


(define MTS (empty-scene WIDTH HEIGHT))




;; =================
;; Data definitions:




;; =================
;; Functions:
