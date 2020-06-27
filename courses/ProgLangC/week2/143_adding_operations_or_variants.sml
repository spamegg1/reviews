(* Programming Languages, Dan Grossman *)
(* Section 8: Adding Operations or Variants *)

(* we take our previous version and add noNegConstants without changing
   old code and a Mult constructor, which requires changing all previous code,
   with a nice to-do list due to inexhaustive pattern-matches. *)

datatype exp = 
    Int    of int 
  | Negate of exp 
  | Add    of exp * exp 
  | Mult   of exp * exp

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
      | Mult(e1,e2) => (case (eval e1, eval e2) of
			    (Int i, Int j) => Int (i*j)
			  | _ => raise BadResult "non-ints in multiply")

fun toString e =
    case e of
	Int i       => Int.toString i
      | Negate e1   => "-(" ^ (toString e1) ^ ")"
      | Add(e1,e2)  => "("  ^ (toString e1) ^ " + " ^ (toString e2) ^ ")"
      | Mult(e1,e2) => "("  ^ (toString e1) ^ " * " ^ (toString e2) ^ ")"

fun hasZero e =
    case e of
	Int i       => i=0 
      | Negate e1   => hasZero e1
      | Add(e1,e2)  => (hasZero e1) orelse (hasZero e2)
      | Mult(e1,e2) => (hasZero e1) orelse (hasZero e2)

fun noNegConstants e =
    case e of
	Int i       => if i < 0 then Negate (Int(~i)) else e
      | Negate e1   => Negate(noNegConstants e1)
      | Add(e1,e2)  => Add(noNegConstants e1, noNegConstants e2)
      | Mult(e1,e2) => Mult(noNegConstants e1, noNegConstants e2)
