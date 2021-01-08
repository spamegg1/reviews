
;; photos-starter.rkt

;; =================
;; Data definitions:

(define-struct photo (location album favourite))
;; Photo is (make-photo String String Boolean)
;; interp. a photo having a location, belonging to an album and having a
;; favourite status (true if photo is a favourite, false otherwise)
(define PHT1 (make-photo "photos/2012/june" "Victoria" true))
(define PHT2 (make-photo "photos/2013/birthday" "Birthday" true))
(define PHT3 (make-photo "photos/2012/august" "Seattle" true))

;; =================
;; Functions:

;
; PROBLEM:
;
; Design a function called to-frame that consumes an album name and a list of photos
; and produces a list of only those photos that are favourites and that belong to
; the given album. You must use built-in abstract functions wherever possible.
;
