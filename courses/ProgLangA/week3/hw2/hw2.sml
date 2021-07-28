(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(*  string, string list -> string list option  *)
(*  returns NONE if string not in list, *)
(*  or SOME list without string if string is in list  *)
(*  assumes string occurs in list at most once  *)
fun all_except_option(s, lst) = case lst of
    [] => NONE
    | x :: xs =>
        let val rest = all_except_option(s, xs)
            val same = same_string(x, s)
        in case rest of
              NONE => if same then SOME(xs) else NONE
            | SOME y => if same then rest else SOME(x :: y) end

(*  string list list, string -> string list  *)
(*  returns list of other strings from lists that contain given string  *)
(*  assumes each list has no repetitions  *)
fun get_substitutions1(lst, s) = case lst of
    [] => []
    | x :: xs =>
        case all_except_option(s, x) of
          NONE => get_substitutions1(xs, s)
        | SOME y => y @ get_substitutions1(xs, s)

(*  string list list, string -> string list  *)
(*  returns list of other strings from lists that contain given string  *)
(*  assumes each list has no repetitions, is tail recursive  *)
fun get_substitutions2(lst, s) =
    let fun aux(lst, s, acc) = case lst of
        [] => acc
        | x :: xs => case all_except_option(s, x) of
              NONE => aux(xs, s, acc)
            | SOME y => aux(xs, s, acc @ y)
    in aux(lst, s, []) end

(*  string list list, {first:string, last:string, middle:string}
    -> {first:string, last:string, middle:string} list  *)
(*  returns list of full names that can be produced *)
(* by substituting only the first name *)
fun similar_names(lst, fullname) =
    let val {first=x, last=y, middle=z} = fullname
        fun sub_fn([]) = []
        |   sub_fn(head :: tail) = {first=head, last=y, middle=z} :: sub_fn(tail)
    in fullname :: sub_fn(get_substitutions2(lst, x)) end

(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw

exception IllegalMove

(* put your solutions for problem 2 here *)

(*  card -> color  *)
(*  returns color of card  *)
fun card_color(card) = case card of
      (Clubs, _) => Black
    | (Diamonds, _) => Red
    | (Hearts, _) => Red
    | (Spades, _) => Black

(*  card -> int  *)
(*  returns value of card  *)
fun card_value(card) = case card of
      (_, Num i) => i
    | (_, Ace) => 11
    | (_, _) => 10

(*  card list, card, exception ->  *)
(*  remove card from list (only once), raise exception if card not in list  *)
fun remove_card(cs, c, e) = case cs of
    [] => raise e
    | x :: xs => if c = x then xs else x :: remove_card(xs, c, e)

(*  card list -> bool  *)
(*  returns true if all cards in list have same color  *)
fun all_same_color([]) = true
|   all_same_color([_]) = true
|   all_same_color(head :: (neck :: rest)) =
        card_color(head) = card_color(neck) andalso all_same_color(neck :: rest)

(*  card list -> int  *)
(*  returns sum of values of cards in list  *)
fun sum_cards(cardlist) = let
    fun aux([], sum) = sum
    |   aux(card :: cards, sum) = aux(cards, sum + card_value(card))
    in aux(cardlist, 0) end

(*  card list, int -> int  *)
(*  computes score for held cards  *)
fun score(heldcards, goal) =
    let val sum = sum_cards(heldcards)
        val same = all_same_color(heldcards)
        val prelim = if (sum > goal) then ((sum - goal) * 3) else (goal - sum)
    in prelim div (if same then 2 else 1) end

(*  card list, move list, int -> int  *)
(*  runs a game, returns score *)
fun officiate(cardlist, movelist, goal) =
    let fun state(heldcards, cardlist, movelist) = case movelist of
        [] => score(heldcards, goal)
        | (Discard c) :: ms =>
            state(remove_card(heldcards, c, IllegalMove), cardlist, ms)
        | (Draw) :: ms => case cardlist of
            [] => score(heldcards, goal)
            | c::cs =>  if sum_cards(c :: heldcards) > goal
                        then score(c :: heldcards, goal)
                        else state(c :: heldcards, cs, ms)
    in state([], cardlist, movelist) end
