(* Programming Languages, Dan Grossman *)
(* Section 1: Examples to Demonstrate Shadowing *)
val a = 10

(* a : int
   a -> 10 *) 

val b = a * 2

(* a -> 10
   b -> 20 *)
		
val a = 5

(* a -> 5, b -> 20 *)
	    
val c = b

(* a -> 5, b -> 20, c -> 20 *)

val d = a

(* a -> 5, b -> 20, c -> 20, d -> 5 *)
	    
val a = a + 1

(* a -> 6 *)		
(* next line does not type-check, f not in environment *)
(* val g = f - 3  *)

val f = a * 2

(* f -> 12 *)

