(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

datatype pattern = Wildcard
         | Variable of string
         | UnitP
         | ConstP of int
         | TupleP of pattern list
         | ConstructorP of string * pattern

datatype valu = Const of int
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

datatype typ = Anything
         | UnitT
         | IntT
         | TupleT of typ list
         | Datatype of string

(**** you can put all your code here ****)

(*  string list -> string list  *)
(*  returns list of strings that start with an uppercase letter  *)
(*  assumes all strings in list have at least 1 character  *)
val only_capitals = List.filter (fn s => Char.isUpper(String.sub(s, 0)))

(*  string list -> string  *)
(*  returns longest string in list, "" if list is empty,  *)
(*  earliest in case of tie  *)
val longest_string1 = List.foldl
    (fn (x, y) => if String.size(x) > String.size(y) then x else y) ""

(*  string list -> string  *)
(*  returns longest string in list, "" if list is empty, latest in case of tie  *)
val longest_string2 = List.foldl
    (fn (x, y) => if String.size(x) >= String.size(y) then x else y) ""

(*  (int * int -> bool) -> string list -> string  *)
(*  if function passed in behaves like >, this acts like longest_string1  *)
fun longest_string_helper f =
    List.foldl (fn (x, y) => if f(String.size(x), String.size(y)) then x else y) ""

(*  string list -> string  *)
(*  returns longest string in list, "" if list is empty, earliest in case of tie  *)
val longest_string3 = longest_string_helper (fn (x, y) => x > y)

(*  string list -> string  *)
(*  returns longest string in list, "" if list is empty, latest in case of tie  *)
val longest_string4 = longest_string_helper (fn (x, y) => x >= y)

(*  string list -> string  *)
(*  returns longest string in list that begins with uppercase,  *)
(*  "" if there are no such strings  *)
(*  returns earliest longest string in case of tie  *)
(*  assumes all strings in list have at least 1 character  *)
val longest_capitalized = longest_string1 o only_capitals

(*  string -> string  *)
(*  returns string in reverse  *)
val rev_string = String.implode o List.rev o String.explode

(*  ('a -> 'b option) -> 'a list -> 'b  *)
fun first_answer f [] = raise NoAnswer
|   first_answer f (a :: rest) = case f(a) of
      NONE => first_answer f rest
    | SOME v => v

(*  ('a -> 'b list option) -> 'a list -> 'b list option *)
fun all_answers _ [] = SOME []
|   all_answers f (a :: alist) = case all_answers f alist of
      NONE => NONE
    | SOME bs => case f(a) of
          NONE => NONE
        | SOME bs' => SOME(bs' @ bs)

(*  pattern -> int  *)
val count_wildcards = g (fn x => 1) (fn x => 0)

(*  pattern -> int  *)
val count_wild_and_variable_lengths = g (fn x => 1) String.size

(*  string * pattern -> int  *)
fun count_some_var (s,p) = g (fn x => 0) (fn x => if x = s then 1 else 0) p

(*  pattern -> bool  *)
(*  returns true if all variable names in pattern are same  *)
fun check_pat (p) =
    (*pattern -> string list*)
    (*returns list of strings occurring in variables in pattern*)
    let fun get_var_strings (pattern) =
        case pattern of
           Wildcard => []
         | Variable str => [str]
         | UnitP => []
         | ConstP i => []
         | TupleP plist => List.foldl (fn (p,vs) => get_var_strings(p) @ vs) [] plist
         | ConstructorP (str, pat) => get_var_strings(pat)
    (*string list -> bool*)
        (*returns true if there are reps, false if no reps*)
        fun check_string_reps (strlist) =
        case strlist of [] => false
             | s::rest => List.exists (fn x => s=x) rest
    in not(check_string_reps(get_var_strings(p))) end

(*  valu * pattern -> (string * valu) list option  *)
fun match (value, pattern) =
    case (value, pattern) of
       (_, Wildcard) => SOME []
     | (_, Variable str) => SOME [(str, value)]
     | (Unit, UnitP) => SOME []
     | (Const i, ConstP j) => if i=j then SOME [] else NONE
     | (Tuple vlist, TupleP plist) =>
       if List.length vlist = List.length plist
       then all_answers match (ListPair.zip(vlist, plist))
       else NONE
     | (Constructor(s1,v), ConstructorP(s2,p)) => if s1=s2 then match(v, p) else NONE
         | (_,_) => NONE


(*  valu * (pattern list) -> (string * valu) list option  *)
fun first_match value plist =
    SOME(first_answer (fn p => match(value,p)) plist)
    handle NoAnswer => NONE
