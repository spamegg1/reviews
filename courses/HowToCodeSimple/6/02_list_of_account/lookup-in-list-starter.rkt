
;; lookup-in-list-starter.rkt

;
; Consider the following data definition for representing an arbitrary number of user
; accounts.
;



(define-struct account (num name))
;; Accounts is one of:
;;  - empty
;;  - (cons (make-account Natural String) Accounts)
;; interp. a list of accounts, where each
;;           num  is an account number
;;           name is the person's first name
(define ACS1 empty)
(define ACS2
  (list (make-account 1 "abc") (make-account 4 "dcj") (make-account 3 "ilk")   (make-account 7 "ruf")))
#;
(define (fn-for-accounts accs)
  (cond [(empty? accs) (...)]
        [else
         (... (account-num  (first accs)) ;Natural
              (account-name (first accs)) ;String
              (fn-for-accounts (rest accs)))]))



; PROBLEM:
;
; Complete the design of the lookup-name function below. Note that because this is a search function
; it will sometimes 'fail'. This happens if it is called with an account number that does not exist
; in the accounts list it is provided. If this happens the function should produce false. The signature
; for such a function is written in a special way as shown below.
;


;; Accounts Natural -> String or false
;; Try to find account with given number in accounts. If found produce name, otherwise produce false.

(define (lookup accs n) "")
