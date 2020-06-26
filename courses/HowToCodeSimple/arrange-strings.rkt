;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname arrange-strings) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
;; Constants:
(define TEXT-SIZE 24)
(define TEXT-COLOR "black")
(define BLANK (square 0 "solid" "white"))

;; ListOfString is one of:
;; - empty
;; - (cons String ListOfString)
;; interp. a list of strings

(define LOS1 empty)
(define LOS2 (cons "a" empty))
(define LOS3 (cons "a" (cons "b" empty)))

#;
(define (fn-for-los los)
  (cond [(empty? los)(...)]
        [else
         (... (first los)
              (fn-for-los (rest los)))]))


;; ListOfString -> Image
;; layout strings vertically in alphabetical order
(check-expect (arrange-strings (cons "Sally" (cons "Apple" empty)))
              (above/align "left"
                           (text "Apple" TEXT-SIZE TEXT-COLOR)
                           (text "Sally" TEXT-SIZE TEXT-COLOR)
                           BLANK))

(define (arrange-strings los)
  (layout-strings (sort-strings los)))


;; ListofString -> Image
;; place images above each other in order of list
(check-expect (layout-strings empty) BLANK)
(check-expect (layout-strings (cons S1 (cons S2 empty)))
              (above/align "left"
                          (text S1 TEXT-SIZE TEXT-COLOR)
                          (text S2 TEXT-SIZE TEXT-COLOR)
                          BLANK))
                          
;(define (layout-strings los) BLANK)  ;stub

(define (layout-strings los)
  (cond [(empty? los) BLANK]
        [else 
         (above/align "left"
                      (text (first los) TEXT-SIZE TEXT-COLOR)
                      (layout-strings (rest los)))]))


(define S1 "Apple")
(define S2 "Sally")
(define S3 "Systematic Program Design")

;; ListOfString -> ListOfString
;; sort strings into alphabetical order
(check-expect (sort-strings empty) empty)
(check-expect (sort-strings (cons S1 (cons S2 empty))) (cons S1 (cons S2 empty)))
(check-expect (sort-strings (cons S3 (cons S1 empty))) (cons S1 (cons S3 empty)))

;(define (sort-strings los) los) ;stub
(define (sort-strings los)
  (cond [(empty? los) empty]
        [else
         (insert-string (first los)
                        (sort-strings (rest los)))]))


;; String ListOfString -> ListOfString
;; insert s in the correct place in the sorted list los
(check-expect (insert-string "Apple" empty) (cons "Apple" empty))
(check-expect (insert-string "Sally" (cons "Apple" (cons "Systematic" empty)))
              (cons "Apple" (cons "Sally" (cons "Systematic" empty))))

;(define (insert-string s los) los);stub

(define (insert-string str los)
  (cond [(empty? los)(cons str empty)]
        [else 
         (if (string>=? str (first los))
             (cons (first los) (insert-string str (rest los)))
             (cons str los))]))