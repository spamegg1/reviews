;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname image) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(circle 10 "solid" "red")
(rectangle 30 60 "outline" "blue")
(text "hello" 24 "orange")
(above (circle 10 "solid" "red") (circle 20 "solid" "orange") (circle 30 "solid" "green"))
(beside (circle 10 "solid" "red") (circle 20 "solid" "orange") (circle 30 "solid" "green"))
(overlay (circle 10 "solid" "red") (circle 20 "solid" "orange") (circle 30 "solid" "green"))