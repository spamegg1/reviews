(require 2htdp/image)

;; stepper-starter.rkt

(+ (* 3 2) 1)



(define (max-dim img)
  (if (> (image-width img) (image-height img))
      (image-width img)
      (image-height img)))


(max-dim (rectangle 10 20 "solid" "blue"))
