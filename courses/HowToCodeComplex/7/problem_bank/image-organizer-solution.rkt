(require 2htdp/image)

;; image-organizer-solution.rkt

;
; In this exercise you will be creating an image organizer using the
; following DDs.
;


;; =================
;; Constants:

(define BLANK (square 0 "solid" "white"))
(define LABEL-SIZE 24)
(define LABEL-COLOR "black")


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
; PART A:
;
; Annotate the type comments with reference arrows and label each one to say
; whether it is a reference, self-reference or mutual-reference.
;
; PART B:
;
; Write out the templates for Dir, ListOfDir and ListOfImage. Identify for each
; call to a template function which arrow from part A it corresponds to.
;


(define (fn-for-dir d)
  (... (dir-name d)
       (fn-for-lod (dir-sub-dirs d))
       (fn-for-loi (dir-images d))))

(define (fn-for-lod lod)
  (cond [(empty? lod) (...)]
        [else
         (... (fn-for-dir (first lod))
              (fn-for-lod (rest lod)))]))

(define (fn-for-loi loi)
  (cond [(empty? loi) (...)]
        [else
         (... (first loi)
              (fn-for-loi (rest loi)))]))


;; =================
;; Functions:

;
; PROBLEM B:
;
; Design a function to calculate the total size (width* height) of all the images
; in a directory and its sub-directories.
;


;; Dir             -> Natural
;; ListOfDir       -> Natural
;; ListOfImage     -> Natural
;; produce total area of all images in dir
(check-expect (total-area--lod empty) 0)
(check-expect (total-area--loi (list I1 I2)) (+ (* 10 10) (* 12 12)))
(check-expect (total-area--dir D6) (+ (* 10 10) (* 12 12) (* 13 14)))

;<templates taken from Dir, ListOfDir, and ListOfImage>

(define (total-area--dir d)
  (+ (total-area--lod (dir-sub-dirs d))
     (total-area--loi (dir-images d))))

(define (total-area--lod lod)
  (cond [(empty? lod) 0]
        [else
         (+ (total-area--dir (first lod))
            (total-area--lod (rest lod)))]))

(define (total-area--loi loi)
  (cond [(empty? loi) 0]
        [else
         (+ (image-area (first loi))          ;knowledge domain shift
            (total-area--loi (rest loi)))]))

;; Image -> Natural
;; produce area of image (width * height
(check-expect (image-area I3) (* 13 14))

(define (image-area img)
  (* (image-width img)
     (image-height img)))

;
; PROBLEM C:
;
; Design a function to produce rendering of a directory with its images. Keep it
; simple and be sure to spend the first 10 minutes of your work with paper and
; pencil!
;


;; Dir             -> Image
;; ListOfDir       -> Image
;; ListOfImage     -> Image
;; produce VERY simple rendering of all images in dir
(check-expect (render--loi empty) BLANK)
(check-expect (render--lod empty) BLANK)
(check-expect (render--loi (list I1 I2)) (beside I1 I2 BLANK))
(check-expect (render--dir D4)
              (beside (text "D4" LABEL-SIZE LABEL-COLOR)
                      (render--loi (list I1 I2))))   ;ok to call render--loi here since it is
;                                                    ;tested in previous test
(check-expect (render--dir D6)
              (beside (text "D6" LABEL-SIZE LABEL-COLOR)
                      (above (render--dir D4)
                             (render--dir D5)
                             BLANK)))


(define (render--dir d)
  (beside (text (dir-name d) LABEL-SIZE LABEL-COLOR)
          (render--lod (dir-sub-dirs d))
          (render--loi (dir-images d))))

(define (render--lod lod)
  (cond [(empty? lod) BLANK]
        [else
         (above (render--dir (first lod))
                (render--lod (rest lod)))]))

(define (render--loi loi)
  (cond [(empty? loi) BLANK]
        [else
         (beside (first loi)
                 (render--loi (rest loi)))]))
