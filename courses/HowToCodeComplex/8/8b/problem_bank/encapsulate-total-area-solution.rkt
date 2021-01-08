(require 2htdp/image)

;; encapsulate-total-area-solution.rkt

;
; In this exercise you will be need to remember the following DDs
; for an image organizer.
;


;; =================
;; Data definitions:

(define-struct dir (name sub-dirs images))
;; Dir is (make-dir String ListOfDir ListOfImage)
;; interp. An directory in the organizer, with a name, a list
;;         of sub-dirs and a list of images.

;; ListOfDir is one of:
;;  - empty
;;  - (cons Dir ListOfDir)
;; interp. A list of directories, this represents the sub-directories of
;;         a directory.

;; ListOfImage is one of:
;;  - empty
;;  - (cons Image ListOfImage)
;; interp. a list of images, this represents the sub-images of a directory.
;; NOTE: Image is a primitive type, but ListOfImage is not.

(define I1 (square 10 "solid" "red"))
(define I2 (square 12 "solid" "green"))
(define I3 (rectangle 13 14 "solid" "blue"))
(define D4 (make-dir "D4" empty (list I1 I2)))
(define D5 (make-dir "D5" empty (list I3)))
(define D6 (make-dir "D6" (list D4 D5) empty))

;
; PROBLEM:
;
; Remember the functions we wrote last week to calculate the total area
; of all images in an image organizer.
;
; Use local to encapsulate the functions so that total-area--dir, total-area--lod,
; total-area--loi and image-area are private to a new functionc called total-area.
; Be sure to revise the signature, purpose, tests etc.
;


;; Dir -> Natural
;; produce total area of all images in dir
(check-expect (total-area (make-dir "Base" empty empty)) 0)
(check-expect (total-area D6) (+ (* 10 10) (* 12 12) (* 13 14)))

; <template from Dir, ListOfDir and ListOfImage, encapsulated with local>

(define (total-area dir)
  (local [(define (total-area--dir dir)
            (+ (total-area--lod (dir-sub-dirs dir))
               (total-area--loi (dir-images dir))))

          (define (total-area--lod lod)
            (cond [(empty? lod) 0]
                  [else
                   (+ (total-area--dir (first lod))
                      (total-area--lod (rest lod)))]))

          (define (total-area--loi loi)
            (cond [(empty? loi) 0]
                  [else
                   (+ (image-area (first loi))
                      (total-area--loi (rest loi)))]))

          (define (image-area img)
            (* (image-width img)
               (image-height img)))]

    (total-area--dir dir)))
