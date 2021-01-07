;; simple-text-editor-starter.rkt

;
;  In this problem, you will be designing a simple one-line text editor.
;
;  The constants and data definitions are provided for you, so make sure
;  to take a look through them after completing your own Domain Analysis.
;
;  Your text editor should have the following functionality:
;  - when you type, characters should be inserted on the left side of the cursor
;  - when you press the left and right arrow keys, the cursor should move accordingly
;  - when you press backspace (or delete on a mac), the last character on the left of
;    the cursors should be deleted
;



(require 2htdp/image)
(require 2htdp/universe)

;; A simple editor

;; Constants
;; =========

(define WIDTH 300)
(define HEIGHT 20)
(define MTS (empty-scene WIDTH HEIGHT))

(define CURSOR (rectangle 2 14 "solid" "red"))

(define TEXT-SIZE 14)
(define TEXT-COLOUR "black")

;; Data Definitions
;; ================

(define-struct editor (pre post))
;; Editor is (make-editor String String)
;; interp. pre is the text before the cursor, post is the text after
(define E0 (make-editor "" ""))
(define E1 (make-editor "a" ""))
(define E2 (make-editor "" "b"))

#;
(define (fn-for-editor e)
  (... (editor-pre e)
       (editor-post e)))
