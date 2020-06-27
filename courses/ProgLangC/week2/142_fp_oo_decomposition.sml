(* Programming Languages, Dan Grossman *)
(* Section 8: OOP vs. Functional Decomposition *)

datatype exp = 
    Int    of int 
  | Negate of exp 
  | Add    of exp * exp 

exception BadResult of string

(* this helper function is overkill here but will provide a more 
   direct contrast with more complicated examples soon *)
fun add_values (v1,v2) =
    case (v1,v2) of
	(Int i, Int j) => Int (i+j)
      | _ => raise BadResult "non-values passed to add_values"

fun eval e = (* no environment because we don't have variables *)
    case e of
	Int _       => e
      | Negate e1   => (case eval e1 of 
			    Int i => Int (~i)
			  | _ => raise BadResult "non-int in negation")
      | Add(e1,e2)  => add_values (eval e1, eval e2)

fun toString e =
    case e of
	Int i       => Int.toString i
      | Negate e1   => "-(" ^ (toString e1) ^ ")"
      | Add(e1,e2)  => "("  ^ (toString e1) ^ " + " ^ (toString e2) ^ ")"

fun hasZero e =
    case e of
	Int i       => i=0 
      | Negate e1   => hasZero e1
      | Add(e1,e2)  => (hasZero e1) orelse (hasZero e2)

