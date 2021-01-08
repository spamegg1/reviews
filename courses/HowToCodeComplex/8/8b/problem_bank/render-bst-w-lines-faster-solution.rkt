
;; render-bst-w-lines-faster-solution.rkt

(require 2htdp/image)

;
; PROBLEM STATEMENT IS AT THE END OF THIS FILE.
;


;; Constants

(define TEXT-SIZE  14)
(define TEXT-COLOR "BLACK")

(define KEY-VAL-SEPARATOR ":")

(define MTTREE (rectangle 20 1 "solid" "white"))



;; Data definitions:

(define-struct node (key val l r))
;; A BST (Binary Search Tree) is one of:
;;  - false
;;  - (make-node Integer String BST BST)
;; interp. false means no BST, or empty BST
;;         key is the node key
;;         val is the node val
;;         l and r are left and right subtrees
;; INVARIANT: for a given node:
;;     key is > all keys in its l(eft)  child
;;     key is < all keys in its r(ight) child
;;     the same key never appears twice in the tree
; .

(define BST0 false)
(define BST1 (make-node 1 "abc" false false))
(define BST7 (make-node 7 "ruf" false false))
(define BST4 (make-node 4 "dcj" false (make-node 7 "ruf" false false)))
(define BST3 (make-node 3 "ilk" BST1 BST4))
(define BST42
  (make-node 42 "ily"
             (make-node 27 "wit" (make-node 14 "olp" false false) false)
             (make-node 50 "dug" false false)))
(define BST10
  (make-node 10 "why" BST3 BST42))
(define BST100
  (make-node 100 "large" BST10 false))
#;
(define (fn-for-bst t)
  (cond [(false? t) (...)]
        [else
         (... (node-key t)    ;Integer
              (node-val t)    ;String
              (fn-for-bst (node-l t))
              (fn-for-bst (node-r t)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic-distinct: false
;;  - compound: (make-node Integer String BST BST)
;;  - self reference: (node-l t) has type BST
;;  - self reference: (node-r t) has type BST

;; Functions:


; PROBLEM:
;
; Design a function that consumes a bst and produces a SIMPLE
; rendering of that bst including lines between nodes and their
; subnodes.
;


;; BST -> Image
;; produce SIMPLE rendering of bst
;; ASSUME BST is relatively well balanced
(check-expect (render-bst false) MTTREE)
(check-expect (render-bst BST1)
              (above (render-key-val 1 "abc")
                     (lines (image-width (render-bst false))
                            (image-width (render-bst false)))
                     (beside (render-bst false)
                             (render-bst false))))
; <template from BST>

(define (render-bst bst)
  (cond [(false? bst) MTTREE]
        [else
         (local [(define li (render-bst (node-l bst)))
                 (define ri (render-bst (node-r bst)))
                 (define lw (image-width li))
                 (define rw (image-width ri))]
           (above (render-key-val (node-key bst) (node-val bst))
                  (lines lw rw)
                  (beside li ri)))]))

;; Integer String -> Image
;; render key and value to form the body of a node
(check-expect (render-key-val 99 "foo")
              (text (string-append "99" KEY-VAL-SEPARATOR "foo") TEXT-SIZE TEXT-COLOR))

(define (render-key-val k v)
  (text (string-append (number->string k)
                       KEY-VAL-SEPARATOR
                       v)
        TEXT-SIZE
        TEXT-COLOR))


;; Natural Natural -> Image
;; produce lines to l/r subtrees based on width of those subtrees
(check-expect (lines 60 130)
              (add-line (add-line (rectangle (+ 60 130) (/ 190 4) "solid" "white")
                                  (/ (+ 60 130) 2) 0
                                  (/ 60 2)         (/ 190 4)
                                  "black")
                        (/ (+ 60 130) 2) 0
                        (+ 60 (/ 130 2)) (/ 190 4)
                        "black"))

(define (lines lw rw)
    ; .

  (local [(define tw (+ lw rw))        ;total width
          (define th (/ (+ lw rw) 4))  ;total height
          (define cx (/ tw 2))         ;center x
          (define lx (/ lw 2))         ;left line, lower x
          (define rx (+ lw (/ rw 2)))] ;right line, lower x

  (add-line (add-line (rectangle tw th "solid" "white")
                      cx  0 lx th "black")
            cx 0 rx th "black")))

;
; ;
; ; PROBLEM:
; ;
; ; Uncomment out the definitions and expressions below, and then
; ; construct a simple graph with the size of the tree on the
; ; x-axis and the time it takes to render it on the y-axis. How
; ; does the time it takes to render increase as a function of
; ; the size of the tree? If you can, improve the performance of
; ; render-bst.
; ;
; ; There is also at least one other good way to use local in this
; ; program. Try it.
; ;
;
;
;
; ;; These trees are NOT legal binary SEARCH trees.
; ;; But for tests on rendering speed that won't matter.
; ;; Just don't try searching in them!
;
; (define BSTA (make-node 100 "A" BST10 BST10))
; (define BSTB (make-node 101 "B" BSTA BSTA))
; (define BSTC (make-node 102 "C" BSTB BSTB))
; (define BSTD (make-node 103 "D" BSTC BSTC))
; (define BSTE (make-node 104 "E" BSTD BSTD))
; (define BSTF (make-node 104 "E" BSTE BSTE))
;
; (time (rest (list (render-bst BSTA))))
; (time (rest (list (render-bst BSTB))))
; (time (rest (list (render-bst BSTC))))
; (time (rest (list (render-bst BSTD))))
; (time (rest (list (render-bst BSTE))))
;
