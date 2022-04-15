module HuttonRazor where

data Expr = Lit Integer | Add Expr Expr

-- Prelude> eval (Add (Lit 1) (Lit 9001))
-- 9002
eval :: Expr -> Integer
eval (Lit a) = a
eval (Add expr1 expr2) = (eval expr1) + (eval expr2)

-- Prelude> printExpr (Add (Lit 1) (Lit 9001))
-- "1 + 9001"
-- Prelude> let a1 = Add (Lit 9001) (Lit 1)
-- Prelude> let a2 = Add a1 (Lit 20001)
-- Prelude> let a3 = Add (Lit 1) a2
-- Prelude> printExpr a3
-- "1 + 9001 + 1 + 20001"
printExpr :: Expr -> String
printExpr (Lit a) = show a
printExpr (Add expr1 expr2) = printExpr expr1 ++ " + " ++ printExpr expr2