;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname list) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct concert (artist venue))
;; Concert is (make-concert String String)
;; interp. a concert with the band playing, and the venue they're playing at
(define C1 (make-concert "Shakey Graves" "Commodore Ballroom"))
(define C2 (make-concert "Tallest Man On Earth" "Orpheum Theatre")) 
#;
(define (fn-for-concert c)
  (... (concert-artist c)
       (concert-venue c)))
    
;; ListOfConcert is one of:
;; - empty
;; - (cons Concert ListOfConcert)
;; interp. a list of concerts
(define LOC1 empty)
(define LOC2 (cons C1 (cons C2 empty)))
#;
(define (fn-for-loc loc)
  (cond [(empty? loc)(...)] 
        [else
         (... (fn-for-concert (first loc))
              (fn-for-loc (rest loc)))]))
     
(define-struct festival (name headliner shows))
;; Festival is (make-festival String Concert ListOfConcert)
;; interp. a festival with name, and list of shows that are part of the festival
(define CANCELLED-FESTIVAL (make-festival "Cancelled" (make-concert "none" "none") empty))
(define VFMF (make-festival "Vancouver Folk Music Festival"
                            (make-concert "Hawksley Workman" "Main Stage")
                            (cons (make-concert "Hawksley Workman" "Main Stage")
                                  (cons (make-concert "Grace Petrie" "Stage 1")
                                        (cons (make-concert "Mary Gauthier" "Stage 5") empty)))))
#;
(define (fn-for-festival f)
  (... (festival-name f)
       (fn-for-concert (festival-headliner f))
       (fn-for-loc (festival-shows f))))