;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname 01-HtDfDesignQuiz) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)

;; Image Image -> Boolean
;; produce true if first image's both height and width are larger than second's, false otherwise
(check-expect (larger-img? (rectangle 10 10 "solid" "red") (rectangle 9 9 "solid" "red")) true)
(check-expect (larger-img? (rectangle 10 10 "solid" "red") (rectangle 10 10 "solid" "red")) false)
(check-expect (larger-img? (rectangle 9 9 "solid" "red") (rectangle 10 10 "solid" "red")) false)
(check-expect (larger-img? (rectangle 10 10 "solid" "red") (rectangle 10 9 "solid" "red")) false)
(check-expect (larger-img? (rectangle 10 9 "solid" "red") (rectangle 10 10 "solid" "red")) false)
(check-expect (larger-img? (rectangle 10 10 "solid" "red") (rectangle 9 10 "solid" "red")) false)
(check-expect (larger-img? (rectangle 9 10 "solid" "red") (rectangle 10 10 "solid" "red")) false)
(check-expect (larger-img? (rectangle 10 9 "solid" "red") (rectangle 9 10 "solid" "red")) false)
(check-expect (larger-img? (rectangle 9 10 "solid" "red") (rectangle 10 9 "solid" "red")) false)

;(define (larger-img? img1 img2) false) ;stub

;(define (larger-img? img1 img2)        ;template
;  (... img1 img2))

(define (larger-img? img1 img2)
  (and (> (image-height img1) (image-height img2))
       (> (image-width img1) (image-width img2))))