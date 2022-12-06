(*
fun all_answers f xs =
    case xs of
        [] => SOME []
      | x::xs' => (case f x of
                       NONE => NONE
                     | SOME [x] => (case all_answers f xs' of
                                       NONE => NONE
                                     | _ => SOME xs)
                     | SOME[x,y] => SOME (valOf (SOME [x,y])@(valOf (all_answers f xs')))
                     | _ => SOME xs)
*)

(*  ('a -> 'b list option) -> 'a list -> 'b list option *)
fun all_answers(f : 'a -> 'b list option) (xs : 'a list) : 'b list option =
let
    fun helper(f, xs, acc) =
        case xs of
            []       => SOME acc
        |   x :: xs' =>
            case f x of
                NONE     => NONE
            |   SOME lst =>  helper(f, xs', acc @ lst)
    in
        helper (f, xs, [])
    end

fun isNone(opt: 'a option): bool = not(isSome opt)

val test8a = all_answers(fn x => if x = 1 then SOME [x] else NONE) [] = SOME []
val test8b = isNone(
    all_answers(fn x => if x = 1 then SOME [x] else NONE) [2,3,4,5,6,7]
)
val test8c = all_answers(fn x => if x > 1 then SOME [x] else NONE) [2,3,4,5]
    = SOME [2,3,4,5]
val test8d = isNone(
    all_answers(fn x => if x > 2 then SOME [x] else NONE) [2,3,4,5,6,7]
)
val test8e = all_answers(fn x => if x = 3 then SOME [x] else NONE) [3]
    = SOME [3]
val test8f = all_answers(fn x => if x > 1 then SOME [x-2, x-1] else NONE) [2,3]
     = SOME [0,1,1,2]
