(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(*  returns NONE if string not in list,
 *  or SOME list without string if string is in list
 *  assumes string occurs in list at most once  *)
fun all_except_option(s: string, lst: string list): string list option =
    case lst of
      [] => NONE
    | x :: xs =>
        let
            val rest = all_except_option(s, xs)
            val same = same_string(x, s)
        in
            case rest of
              NONE    => if same then SOME(xs) else NONE
            | SOME ys => if same then rest else SOME(x :: ys)
        end

(*  returns list of other strings from lists that contain given string
 *  assumes each list has no repetitions  *)
fun get_substitutions1(lst: string list list, s: string): string list =
    case lst of
      [] => []
    | x :: xs =>
        case all_except_option(s, x) of
          NONE   =>     get_substitutions1(xs, s)
        | SOME y => y @ get_substitutions1(xs, s)

(*  returns list of other strings from lists that contain given string
 *  assumes each list has no repetitions, is tail recursive  *)
fun get_substitutions2(lst: string list list, s: string): string list =
    let
        fun aux(lst, s, acc) =
            case lst of
              [] => acc
            | x :: xs =>
                case all_except_option(s, x) of
                  NONE   => aux(xs, s, acc)
                | SOME y => aux(xs, s, acc @ y)
    in
        aux(lst, s, [])
    end

(*  returns list of full names that can be produced
 *  by substituting only the first name *)
fun similar_names(lst: string list list,
    fullname: {first:string, last:string, middle:string})
            : {first:string, last:string, middle:string} list =
    let
        val {first=x, last=y, middle=z} = fullname
        fun sub_fn([]) = []
        |   sub_fn(head::tail) = {first=head, last=y, middle=z} :: sub_fn(tail)
    in
        fullname :: sub_fn(get_substitutions2(lst, x))
    end

(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw

exception IllegalMove

(* put your solutions for problem 2 here *)

(*  returns color of card  *)
fun card_color(card: card): color =
    case card of
      (Clubs, _)    => Black
    | (Diamonds, _) => Red
    | (Hearts, _)   => Red
    | (Spades, _)   => Black

(*  returns value of card  *)
fun card_value(card: card): int =
    case card of
      (_, Num i) => i
    | (_, Ace)   => 11
    | (_, _)     => 10

(*  remove card from list (only once), raise exception if card not in list  *)
fun remove_card([]: card list, _: card, e: exn): card list = raise e
|   remove_card(x :: xs, c, e) =
        if   c = x
        then xs
        else x :: remove_card(xs, c, e)

(*  returns true if all cards in list have same color  *)
fun all_same_color([]: card list): bool = true
|   all_same_color([_]) = true
|   all_same_color(head :: (neck :: rest)) =
        card_color(head) = card_color(neck) andalso
        all_same_color(neck :: rest)

(*  returns sum of values of cards in list  *)
fun sum_cards(cards: card list): int =
    let
        fun aux([]: card list, sum: int): int = sum
        |   aux(c :: cs, sum) = aux(cs, sum + card_value(c))
    in
        aux(cards, 0)
    end

(*  computes score for held cards  *)
fun score(heldcards: card list, goal: int): int =
    let
        val sum = sum_cards(heldcards)
        val same = all_same_color(heldcards)
        val prelim =
            if   sum > goal
            then (sum - goal) * 3
            else goal - sum
    in
        prelim div (if same then 2 else 1)
    end

(*  runs a game, returns score *)
fun officiate(cards: card list, moves: move list, goal: int): int =
    let
        fun state(held: card list, _: card list, []: move list): int =
                score(held, goal)
        |   state(held, cs, (Discard c) :: ms) =
                state(remove_card(held, c, IllegalMove), cs, ms)
        |   state(held, [], Draw :: ms) = score(held, goal)
        |   state(held, c :: cs, Draw :: ms) =
                if   sum_cards(c :: held) > goal
                then score(c :: held, goal)
                else state(c :: held, cs, ms)
    in
        state([], cards, moves)
    end

(* assume held1 and cards are both nonempty. *)
fun zero_possible(
    held1: card list, held2: card list, cards: card list, goal: int
): bool =
    if held2 = [] then false
    else if score((hd cards :: held1) @ tl held2, goal) = 0
    then true
    else zero_possible(hd held2 :: held1, tl held2, cards, goal)

(* fun careful_helper(
    cards: card list, goal: int, held: card list, acc: move list
): move list =
    if goal - sum_cards(held) > 10 andalso not(cards = [])
    then careful_helper(tl cards, goal, (hd cards) :: held, Draw :: acc)
    else if score(held, goal) = 0 then acc
    else if not(cards = []) andalso not(held = []) then
    let
        val first = hd held
        (* val discard_then_draw = *)
    in
        if score((hd cards) :: (tl held), goal) = 0
        then Discard
        else Discard
    end *)

fun careful_player(cards: card list, goal: int): move list = nil
