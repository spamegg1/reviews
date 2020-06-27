(* Programming Languages, Dan Grossman *)
(* Section 7: Dynamic Dispatch Versus Closures *)

(* ML functions do not use use late binding: simple example: *)

fun even x = (print "in even\n" ; if x=0 then true else odd (x-1))
and odd x = (print "in odd\n" ; if x=0 then false else even (x-1))

val a1 = odd 7
val _ = print "\n"

(* does not change behavior of odd -- which is too bad *)
fun even x = (x mod 2) = 0

val a2 = odd 7
val _ = print "\n"

(* does not change behavior of odd -- which is good *)
fun even x = false

val a3 = odd 7
val _ = print "\n"

