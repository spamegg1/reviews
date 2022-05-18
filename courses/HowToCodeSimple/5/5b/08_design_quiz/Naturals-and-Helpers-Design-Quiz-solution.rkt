(require 2htdp/image)
;; SPD2-Design-Quiz-1.rkt


;; ======================================================================
;; Constants
(define COOKIES empty-image) ;; I removed the actual cookie image here.

;; ======================================================================
;; Data Definitions

;; Natural is one of:
;;  - 0
;;  - (add1 Natural)
;; interp. a natural number
(define N0 0)         ;0
(define N1 (add1 N0)) ;1
(define N2 (add1 N1)) ;2

#;
(define (fn-for-natural n)
  (cond [(zero? n) (...)]
        [else
         (... n   ; n is added because it's often useful
              (fn-for-natural (sub1 n)))]))

;; Template rules used:
;;  - one-of: two cases
;;  - atomic distinct: 0
;;  - compound: 2 fields
;;  - self-reference: (sub1 n) is Natural




; PROBLEM 1:
;
; Complete the design of a function called pyramid that takes a natural
; number n and an image, and constructs an n-tall, n-wide pyramid of
; copies of that image.
;
; For instance, a 3-wide pyramid of cookies would look like this:
;
; .


;; Natural Image -> Image
;; produce an n-wide pyramid of the given image
(check-expect (pyramid 0 COOKIES) empty-image)
(check-expect (pyramid 1 COOKIES) COOKIES)
(check-expect (pyramid 3 COOKIES)
              (above COOKIES
                     (beside COOKIES COOKIES)
                     (beside COOKIES COOKIES COOKIES)))

;(define (pyramid n img) empty-image) ; stub
;<template from Natural, with added atomic parameter>
(define (pyramid n img)
  (cond [(zero? n) empty-image]
        [else
         (above (pyramid (sub1 n) img) (one-line n img))]))


;; Natural Image -> Image
;; produce n copies of image beside each other
(check-expect (one-line 0 COOKIES) empty-image)
(check-expect (one-line 1 COOKIES) COOKIES)
(check-expect (one-line 3 COOKIES) (beside COOKIES COOKIES COOKIES))
;(define (one-line n img) empty-image) ; stub
;<template from Natural, with added atomic parameter>
(define (one-line n img)
  (cond [(zero? n) empty-image]
        [else
         (beside img (one-line (sub1 n) img))]))

; Problem 2:
; Consider a test tube filled with solid blobs and bubbles.  Over time the
; solids sink to the bottom of the test tube, and as a consequence the bubbles
; percolate to the top.  Let's capture this idea in BSL.
;
; Complete the design of a function that takes a list of blobs and sinks each
; solid blob by one. It's okay to assume that a solid blob sinks past any
; neighbor just below it.
;
; To assist you, we supply the relevant data definitions.


;; Blob is one of:
;; - "solid"
;; - "bubble"
;; interp.  a gelatinous blob, either a solid or a bubble
;; Examples are redundant for enumerations
#;
(define (fn-for-blob b)
  (cond [(string=? b "solid") (...)]
        [(string=? b "bubble") (...)]))

;; Template rules used:
;; - one-of: 2 cases
;; - atomic distinct: "solid"
;; - atomic distinct: "bubble"


;; ListOfBlob is one of:
;; - empty
;; - (cons Blob ListOfBlob)
;; interp. a sequence of blobs in a test tube, listed from top to bottom.
(define LOB0 empty) ; empty test tube
(define LOB2 (cons "solid" (cons "bubble" empty))) ; solid blob above a bubble

#;
(define (fn-for-lob lob)
  (cond [(empty? lob) (...)]
        [else
         (... (fn-for-blob (first lob))
              (fn-for-lob (rest lob)))]))

;; Template rules used
;; - one-of: 2 cases
;; - atomic distinct: empty
;; - compound: 2 fields
;; - reference: (first lob) is Blob
;; - self-reference: (rest lob) is ListOfBlob

;; ListOfBlob -> ListOfBlob
;; produce a list of blobs that sinks the given solid blobs by one

(check-expect (sink empty) empty)
(check-expect (sink (cons "bubble" empty)) (cons "bubble" empty))
(check-expect (sink (cons "solid" empty)) (cons "solid" empty))
(check-expect (sink (cons "bubble" (cons "bubble" empty)))
              (cons "bubble" (cons "bubble" empty)))
(check-expect (sink (cons "bubble" (cons "solid" empty)))
              (cons "bubble" (cons "solid" empty)))
(check-expect (sink (cons "solid" (cons "bubble" empty)))
              (cons "bubble" (cons "solid" empty)))
(check-expect (sink (cons "solid" (cons "solid" empty)))
              (cons "solid" (cons "solid" empty)))
(check-expect (sink (cons "bubble" (cons "bubble" (cons "bubble" empty))))
              (cons "bubble" (cons "bubble" (cons "bubble" empty))))
(check-expect (sink (cons "bubble" (cons "bubble" (cons "solid" empty))))
              (cons "bubble" (cons "bubble" (cons "solid" empty))))
(check-expect (sink (cons "bubble" (cons "solid" (cons "bubble" empty))))
              (cons "bubble" (cons "bubble" (cons "solid" empty))))
(check-expect (sink (cons "solid" (cons "bubble" (cons "bubble" empty))))
              (cons "bubble" (cons "solid" (cons "bubble" empty))))
(check-expect (sink (cons "bubble" (cons "solid" (cons "solid" empty))))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "solid" (cons "bubble" (cons "solid" empty))))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "solid" (cons "solid" (cons "bubble" empty))))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "solid" (cons "solid" (cons "solid" empty))))
              (cons "solid" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "solid"
                          (cons "solid"
                                (cons "bubble" (cons "bubble" empty)))))
              (cons "bubble"
                    (cons "solid"
                          (cons "solid" (cons "bubble" empty)))))
;(define (sink lob) empty) ; stub
;<template from ListOfBlob>
(define (sink lob)
  (cond [(empty? lob) empty]
        [else (helper (first lob) (sink (rest lob)))]))


;; Blob ListOfBlob -> ListOfBlob
;; helper function
;; ASSUMES that lob has at least 1 element
(check-expect (helper "bubble" (cons "solid" empty))
              (cons "bubble" (cons "solid" empty)))
(check-expect (helper "solid" (cons "bubble" empty))
              (cons "bubble" (cons "solid" empty)))
(check-expect (helper "solid" (cons "bubble" (cons "bubble" empty)))
              (cons "bubble" (cons "solid" (cons "bubble" empty))))
;(define (helper b lob) lob) ;stub
;<template from Blob, with added ListOfBlob parameter>
(define (helper b lob)
  (cond [(empty? lob) (cons b lob)]
        [(string=? "solid" b) (cons (first lob) (cons b (rest lob)))]
        [else (cons b lob)]))
