(require 2htdp/image)

;; render-bst-solution.rkt

;
; Consider the following data definition for a binary search tree:
;


;; Constants

(define TEXT-SIZE  14)
(define TEXT-COLOR "BLACK")

(define KEY-VAL-SEPARATOR ":")

(define MTTREE (rectangle 20 1 "solid" "white"))

(define VSPACE (rectangle  1 10 "solid" "white"))
(define HSPACE (rectangle 10  1 "solid" "white"))




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
(define BST7 (make-node 7 "ruf" false false)) ;!!!! ADDDED
(define BST4 (make-node 4 "dcj" false (make-node 7 "ruf" false false))) ;;<<<
(define BST3 (make-node 3 "ilk" BST1 BST4))
(define BST42
  (make-node 42 "ily"
             (make-node 27 "wit" (make-node 14 "olp" false false) false)
             (make-node 50 "dug" false false)))
(define BST10
  (make-node 10 "why" BST3 BST42))
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
; rendering of that bst. Emphasis on SIMPLE. You might want to
; skip the lines for example.
;

; .

;; BST -> Image
;; produce SIMPLE rendering of bst
(check-expect (render-bst false) MTTREE)
(check-expect (render-bst BST1)  (above (text (string-append "1" KEY-VAL-SEPARATOR "abc")
                                              TEXT-SIZE
                                              TEXT-COLOR)
                                        VSPACE
                                        (beside MTTREE HSPACE MTTREE)))

(check-expect (render-bst BST4)  (above (text (string-append "4" KEY-VAL-SEPARATOR "dcj")
                                              TEXT-SIZE
                                              TEXT-COLOR)
                                        VSPACE
                                        (beside MTTREE HSPACE (render-bst BST7))))

(check-expect (render-bst BST3) (above (text (string-append "3" KEY-VAL-SEPARATOR "ilk")
                                              TEXT-SIZE
                                              TEXT-COLOR)
                                       VSPACE
                                       (beside (render-bst BST1)
                                               HSPACE
                                               (render-bst BST4))))

;<template from BST>

(define (render-bst bst)
  (cond [(false? bst) MTTREE]
        [else
         (above (text (string-append (number->string (node-key bst))
                                     KEY-VAL-SEPARATOR
                                     (node-val bst))
                      TEXT-SIZE
                      TEXT-COLOR)
                VSPACE
                (beside (render-bst (node-l bst))
                        HSPACE
                        (render-bst (node-r bst))))]))
