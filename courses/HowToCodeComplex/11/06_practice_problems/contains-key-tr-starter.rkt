
;; contains-tr-starter.rkt

; Problem:
;
; Starting with the following data definition for a binary tree (not a binary search tree)
; design a tail-recursive function called contains? that consumes a key and a binary tree
; and produces true if the tree contains the key.
;


(define-struct node (k v l r))
;; BT is one of:
;;  - false
;;  - (make-node Integer String BT BT)
;; Interp. A binary tree, each node has a key, value and 2 children
(define BT1 false)
(define BT2 (make-node 1 "a"
                       (make-node 6 "f"
                                  (make-node 4 "d" false false)
                                  false)
                       (make-node 7 "g" false false)))
