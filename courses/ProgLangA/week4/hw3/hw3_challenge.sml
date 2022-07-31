datatype pattern =
      Wildcard
    | Variable of string
    | UnitP
    | ConstP of int
    | TupleP of pattern list
    | ConstructorP of string * pattern

(* some examples of patterns *)
val pat1 = Wildcard
val pat2 = Variable "pat2"
val pat3 = UnitP
val pat4 = ConstP 17
val pat5 = TupleP [pat1, pat2, pat3, pat4]
val pat6 = ConstructorP("pat5", pat5)
val pat7 = TupleP [pat1, pat1, pat1, pat1, pat1, pat1, pat1]
val pat8 = TupleP [pat1, TupleP [pat5, pat2], pat2, pat7,
                   TupleP [pat7, pat3, pat4], pat6]

datatype typ =
      Anything           (* any type of value is okay *)
    | UnitT              (* type for Unit *)
    | IntT               (* type for integers *)
    | TupleT of typ list (* tuple types *)
    | Datatype of string (* some named datatype *)

(* some examples of typs *)
val anything = Anything
val unitT = UnitT
val intT = IntT
val tuple = TupleT [anything, unitT, intT]
val dtype = Datatype "named"
val nested = TupleT [dtype, tuple]

fun typecheck_patterns(
    patlist: ((string * string * typ) list) * pattern list
): typ option =
    NONE

val test_ch1 = typecheck_patterns(
    (nil, nil)
) = NONE

val test_ch2 = typecheck_patterns(
    ([("a", "a", Anything)], nil)
) = NONE

val test_ch3 = typecheck_patterns(
    (nil, [Wildcard])
) = NONE

val test_ch4 = typecheck_patterns(
    ([("TupleP", "a", Anything)], [TupleP[Variable("x")]])
) = NONE
