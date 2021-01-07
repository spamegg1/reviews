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
