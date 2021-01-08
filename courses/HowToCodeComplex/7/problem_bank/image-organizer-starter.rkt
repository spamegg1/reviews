(require 2htdp/image)

;; image-organizer-starter.rkt

;
; PROBLEM:
;
; Complete the design of a hierarchical image organizer.  The information and data
; for this problem are similar to the file system example in the fs-starter.rkt file.
; But there are some key differences:
;   - this data is designed to keep a hierchical collection of images
;   - in this data a directory keeps its sub-directories in a separate list from
;     the images it contains
;   - as a consequence data and images are two clearly separate types
;
; Start by carefully reviewing the partial data definitions below.
;


;; =================
;; Constants:


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
(define I2 (square 10 "solid" "green"))
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



;; =================
;; Functions:

;
; PROBLEM B:
;
; Design a function to calculate the total size (width* height) of all the images
; in a directory and its sub-directories.
;


;
; PROBLEM C:
;
; Design a function to produce rendering of a directory with its images. Keep it
; simple and be sure to spend the first 10 minutes of your work with paper and
; pencil!
;
