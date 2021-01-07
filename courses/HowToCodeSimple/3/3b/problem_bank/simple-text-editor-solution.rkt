;; simple-text-editor-solution.rkt

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

;; Functions
;; =========

;; Editor -> Editor
;; a simple editor, start with (main (make-editor "" ""))

(define (main e)
  (big-bang e
            (to-draw render)
            (on-key handle-key)))


;; Editor -> Image
;; renders the text with the curson between pre and post
(check-expect (render (make-editor "" "")) (overlay/align "left" "middle" CURSOR MTS))
(check-expect (render (make-editor "a" "")) (overlay/align "left" "middle" (beside (text "a" TEXT-SIZE TEXT-COLOUR) CURSOR) MTS))
(check-expect (render (make-editor "" "b")) (overlay/align "left" "middle" (beside CURSOR (text "b" TEXT-SIZE TEXT-COLOUR)) MTS))
(check-expect (render (make-editor "a" "b")) (overlay/align "left" "middle" (beside (text "a" TEXT-SIZE TEXT-COLOUR) CURSOR (text "b" TEXT-SIZE TEXT-COLOUR)) MTS))

;(define (render e) MTS)
(define (render e)
  (overlay/align "left" "middle"
                 (beside (text (editor-pre e) TEXT-SIZE TEXT-COLOUR)
                         CURSOR
                         (text (editor-post e) TEXT-SIZE TEXT-COLOUR))
                 MTS))



;; Editor KeyEvent -> Editor
;; key handler for simple editor
(check-expect (handle-key (make-editor "" "") "a")(make-editor "a" ""))
(check-expect (handle-key (make-editor "a" "c") "b")(make-editor "ab" "c"))
(check-expect (handle-key (make-editor "aa" "bb") "\b")(make-editor "a" "bb"))
(check-expect (handle-key (make-editor "aa" "bb") "left")(make-editor "a" "abb"))
(check-expect (handle-key (make-editor "aa" "bb") "right")(make-editor "aab" "b"))

;(define (handle-key e ke) e)

(define (handle-key e ke)
  (cond [(key=? ke "\b")(make-editor (string-butlast (editor-pre e))(editor-post e))]
        [(key=? ke "left")(make-editor (string-butlast (editor-pre e))(string-append (string-last (editor-pre e))(editor-post e)))]
        [(key=? ke "right")(make-editor (string-append (editor-pre e)(string-first (editor-post e)))(string-butfirst (editor-post e)))]
        [(= 1(string-length ke))(make-editor (string-append (editor-pre e) ke)(editor-post e))]))

;; String -> String
;; returns the first character of a string
(check-expect (string-first "") "")
(check-expect (string-first "apple") "a")

;(define (string-first s) s)

#;
(define (string-first s)
  (... s))

(define (string-first s)
  (if (string=? s "")
      ""
      (string-ith s 0)))

;; String -> String
;; returns the last character of a string
(check-expect (string-last "") "")
(check-expect (string-last "apple") "e")

;(define (string-last s) s)

#;
(define (string-last s)
  (... s))

(define (string-last s)
  (if (string=? s "")
      ""
      (string-ith s (sub1 (string-length s)))))

;; String -> String
;; returns everything but the first character of a string
(check-expect (string-butfirst "") "")
(check-expect (string-butfirst "apple") "pple")

;(define (string-butfirst s) s)

#;
(define (string-butfirst s)
  (... s))

(define (string-butfirst s)
  (if (string=? s "")
      ""
      (substring s 1)))

;; String -> String
;; returns everything but the last character of a string
(check-expect (string-butlast "") "")
(check-expect (string-butlast "apple") "appl")

;(define (string-butlast s) s)

#;
(define (string-butlast s)
  (... s))

(define (string-butlast s)
  (if (string=? s "")
      ""
      (substring s 0 (sub1 (string-length s)))))
