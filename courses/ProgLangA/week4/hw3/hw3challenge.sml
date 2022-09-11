datatype pattern =                       (* Corresponding typ: *)
        Wildcard                         (* Anything *)
    |   Variable of string               (* Anything *)
    |   UnitP                            (* UnitT *)
    |   ConstP of int                    (* IntT *)
    |   TupleP of pattern list           (* TupleT [typs] *)
    |   ConstructorP of string * pattern (* Datatype str *)

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
    |   UnitT              (* type for Unit *)
    |   IntT               (* type for integers *)
    |   TupleT of typ list (* tuple types *)
    |   Datatype of string (* some named datatype *)

(* some examples of typs *)
val anythg: typ = Anything
val unitT : typ = UnitT
val intT  : typ = IntT
val tuple : typ = TupleT [anythg, unitT, intT]
val dtype : typ = Datatype "named"
val nested: typ = TupleT [dtype, tuple]

(*  Checks if two typs have a common type.  *)
fun compatible(s: typ, t: typ): bool = case (s, t) of
        (Anything,  _) => true
    |   (_,  Anything) => true
    |   (UnitT, UnitT) => true
    |   ( IntT,  IntT) => true
    |   (Datatype s1, Datatype s2) => s1 = s2
    |   (TupleT lst1, TupleT lst2) => ListPair.allEq compatible (lst1, lst2)
    |   _ => false

(*  Converts a single pattern to its typ. Uses compatible. *)
fun p2t(triples: (string * string * typ) list)(p: pattern): typ option =
    case p of
        Wildcard   => SOME(Anything)
    |   Variable _ => SOME(Anything)
    |   UnitP      => SOME(UnitT)
    |   ConstP _   => SOME(IntT)
    |   TupleP lst =>
        let
            val converted = List.map (p2t triples) lst
        in
            if   List.all isSome converted
            then SOME(TupleT (List.map valOf converted))
            else NONE
        end
    |   ConstructorP(str, pat) =>
        let
            val lookup = List.find (fn (s1, _, _) => s1 = str) triples
        in
            case (lookup, p2t triples pat) of
                (NONE, _) => NONE           (* could not find str in metadata *)
            |   (_, NONE) => NONE                     (* pat fails to convert *)
            |   (SOME(s1, s2, t), SOME(t')) =>
                if   compatible(t, t')
                then SOME(Datatype s2)
                else NONE                   (* pat incompatible with metadata *)
        end

(* Merges two types to their common type. Assumes the types are compatible. *)
fun coalesce(s: typ, t: typ): typ = case (s, t) of
        (Anything,  x) => x
    |   ( x, Anything) => x
    |   (UnitT, UnitT) => UnitT
    |   ( IntT,  IntT) => IntT
    |   (Datatype s1, Datatype s2) => Datatype s1           (* assume s1 = s2 *)
    |   (TupleT lst1, TupleT lst2) =>           (* assume len lst1 = len lst2 *)
        TupleT (List.map coalesce (ListPair.zip(lst1, lst2)))
    |   _ => s         (* to satisfy type checker. This case shouldn't happen *)

(* Merges two typ options. Checks if they are compatible. *)
fun merge(s: typ option, t: typ option): typ option =
    case (s, t) of
        (NONE, _) => NONE
    |   (_, NONE) => NONE
    |   (SOME(s1), SOME(t1)) =>
        if   compatible(s1, t1)
        then SOME(coalesce(s1, t1))
        else NONE

(* Assumes the list of patterns is not empty. *)
fun typecheck_patterns(
    triples: (string * string * typ) list, patterns: pattern list
): typ option =
    case List.map (p2t triples) patterns of       (* convert all pats to typs *)
        t :: [] => t
    |   t :: ts => List.foldl merge t ts
    |   _ => NONE     (* to satisfy type checker. This case should not happen *)
