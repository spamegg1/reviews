(* Programming Languages, Dan Grossman *)
(* Section 6: Static Versus Dynamic Typing, Part 1 *)

datatype t = Int of int | String of string
fun f y = if y > 0 then Int(y+y) else String "hi"

fun foo x = case f x of
		Int i => Int.toString i
	      | String s => s

fun cube x = x * x * x

val z = cube 7

(* fun f g = (g 7, g true) *) (* does not type-check *)
(* val pair_of_pairs = f (fn x => (x,x)) *)

datatype tort = Int of int
              | String of string
              | Cons of tort * tort
              | Fun of tort -> tort
              (* would have more constructors *)

val _ = if true
	then Fun (fn x => case x of Int i => Int (i*i*i))
	else Cons (Int 7, String "hi")

(* does not type-check *)
(*
fun pow x y =
    if y = 0
    then 1
    else x * pow (x,y-1)
*)

(* wrong algorithm *)
fun pow x y =  (* curried *)
    if y = 0
    then 1
    else x + pow x (y-1) (* oops *)
