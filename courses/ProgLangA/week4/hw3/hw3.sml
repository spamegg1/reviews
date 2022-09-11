(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

datatype pattern =
      Wildcard
    | Variable of string
    | UnitP
    | ConstP of int
    | TupleP of pattern list
    | ConstructorP of string * pattern

datatype valu =
      Const of int
    | Unit
    | Tuple of valu list
    | Constructor of string * valu

fun g f1 f2 p =
    let
        val r = g f1 f2
    in
        case p of
          Wildcard          => f1 ()
        | Variable x        => f2 x
        | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
        | ConstructorP(_,p) => r p
        | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ =
      Anything
    | UnitT
    | IntT
    | TupleT of typ list
    | Datatype of string

(**** you can put all your code here ****)

(*  returns list of strings that start with an uppercase letter
 *  assumes all strings in list have at least 1 character  *)
val only_capitals: string list -> string list =
    List.filter (fn s => Char.isUpper(String.sub(s, 0)))

(*  returns longest string in list, "" if list is empty,
 *  earliest in case of tie  *)
val longest_string1: string list -> string =
    List.foldl
        (fn (x, y) => if String.size(x) > String.size(y) then x else y)
        ""

(* returns longest string in list, "" if list is empty, latest in case of tie *)
val longest_string2: string list -> string =
    List.foldl
        (fn (x, y) => if String.size(x) >= String.size(y) then x else y)
        ""

(*  if function passed in behaves like >, this acts like longest_string1  *)
fun longest_string_helper(f: int * int -> bool): string list -> string =
    List.foldl
        (fn (x, y) => if f(String.size(x), String.size(y)) then x else y)
        ""

(*  returns longest string in list, "" if empty, earliest in case of tie  *)
val longest_string3: string list -> string =
    longest_string_helper(fn (x, y) => x > y)

(* returns longest string in list, "" if list is empty, latest in case of tie *)
val longest_string4: string list -> string =
    longest_string_helper(fn (x, y) => x >= y)

(*  returns longest string in list that begins with uppercase,
 *  "" if there are no such strings
 *  returns earliest longest string in case of tie
 *  assumes all strings in list have at least 1 character  *)
val longest_capitalized: string list -> string =
    longest_string1 o only_capitals

(*  returns string in reverse  *)
val rev_string: string -> string = String.implode o List.rev o String.explode

fun first_answer(f: 'a -> 'b option)([]: 'a list): 'b = raise NoAnswer
|   first_answer f (a :: rest) =
        case f(a) of
          NONE   => first_answer f rest
        | SOME v => v

fun all_answers(_: 'a -> 'b list option)([]: 'a list): 'b list option = SOME []
|   all_answers f (a :: alist) =
        case all_answers f alist of
          NONE => NONE
        | SOME bs =>
            case f(a) of
              NONE => NONE
            | SOME bs' => SOME(bs' @ bs)

val count_wildcards: pattern -> int = g (fn _ => 1) (fn _ => 0)

val count_wild_and_variable_lengths: pattern -> int = g (fn _ => 1) String.size

fun count_some_var(s: string, p: pattern): int =
    g (fn _ => 0) (fn x => if x = s then 1 else 0) p

(*  returns true if all variable names in pattern are same  *)
fun check_pat(p: pattern): bool =
    let
        fun get_var_strings(p: pattern): string list =
            (* returns list of strings occurring in variables in pattern *)
            case p of
              Wildcard     => []
            | Variable str => [str]
            | UnitP        => []
            | ConstP i     => []
            | TupleP plist =>
                List.foldl (fn (p, vs) => get_var_strings(p) @ vs) [] plist
            | ConstructorP (str, pat)  => get_var_strings(pat)

        (* returns true if there are reps, false if no reps *)
        fun check_string_reps([]: string list): bool = false
        |   check_string_reps(s :: rest) = List.exists (fn x => s = x) rest
    in
        not(check_string_reps(get_var_strings(p)))
    end

fun match(v: valu, p: pattern): (string * valu) list option =
    case (v, p) of
      (_, Wildcard)               => SOME []
    | (_, Variable str)           => SOME [(str, v)]
    | (Unit, UnitP)               => SOME []
    | (Const i, ConstP j)         => if i = j then SOME [] else NONE
    | (Tuple vlist, TupleP plist) =>
        if   List.length vlist = List.length plist
        then all_answers match (ListPair.zip(vlist, plist))
        else NONE
    | (Constructor(s1, v), ConstructorP(s2, p)) =>
        if   s1 = s2
        then match(v, p)
        else NONE
    | (_, _) => NONE

fun first_match(v: valu)(ps: pattern list): (string * valu) list option =
    SOME(first_answer (fn p => match(v, p)) ps) handle NoAnswer => NONE
