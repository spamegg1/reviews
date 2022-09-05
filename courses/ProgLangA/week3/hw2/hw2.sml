(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) = s1 = s2

(* put your solutions for problem 1 here *)

(*  returns NONE if string not in list,
 *  or SOME list without string if string is in list
 *  assumes string occurs in list at most once  *)
fun all_except_option(s: string, lst: string list): string list option =
    case lst of
        [] => NONE
    |   x :: xs =>
        let
            val rest = all_except_option(s, xs)
        in
            case (same_string(x, s), rest) of
                (true,  NONE)     => SOME(xs)
            |   (false, NONE)     => NONE
            |   (true,  SOME(ys)) => rest
            |   (false, SOME(ys)) => SOME(x :: ys)
        end

(*  returns list of other strings from lists that contain given string
 *  assumes each list has no repetitions  *)
fun get_substitutions1(lst: string list list, s: string): string list =
    case lst of
        [] => []
    |   x :: xs =>
        case all_except_option(s, x) of
            NONE   =>     get_substitutions1(xs, s)
        |   SOME y => y @ get_substitutions1(xs, s)

(*  returns list of other strings from lists that contain given string
 *  assumes each list has no repetitions, is tail recursive  *)
fun get_substitutions2(lst: string list list, s: string): string list =
    let
        fun aux(lst, s, acc) =
            case lst of
                [] => acc
            |   x :: xs =>
                case all_except_option(s, x) of
                    NONE   => aux(xs, s, acc)
                |   SOME y => aux(xs, s, acc @ y)
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
    |   (Diamonds, _) => Red
    |   (Hearts, _)   => Red
    |   (Spades, _)   => Black

fun card_rank(card: card): rank = case card of (_, x) => x

(*  returns value of card  *)
fun card_value(card: card): int =
    case card of
        (_, Num i) => i
    |   (_, Ace)   => 11
    |   (_, _)     => 10

(*  checks if card is in card list.  *)
fun myfind(c: card, []: card list): bool = false
|   myfind(x, c :: cs) = (x = c) orelse myfind(x, cs)

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

(* helper for officiate. Useful to have it outside officiate. *)
fun state(held: card list, _: card list, []: move list, goal: int): int =
    score(held, goal)
|   state(held, cs, (Discard c) :: ms, goal) =
        state(remove_card(held, c, IllegalMove), cs, ms, goal)
|   state(held, [], Draw :: ms, goal) = score(held, goal)
|   state(held, c :: cs, Draw :: ms, goal) =
        if   sum_cards(c :: held) > goal
        then score(c :: held, goal)
        else state(c :: held, cs, ms, goal)

(*  runs a game, returns score *)
fun officiate(cards: card list, moves: move list, goal: int): int =
    state([], cards, moves, goal)

(* Challenge stuff *)
(*  general utility HOFs *)
fun mymap(func: 'a -> 'b)([]: 'a list): 'b list = []
|   mymap func (x :: xs) = (func x) :: mymap func xs

fun myfilter(pred: 'a -> bool)([]: 'a list): 'a list = []
|   myfilter pred (x :: xs) =
        if (pred x)
        then x :: (myfilter pred xs)
        else (myfilter pred xs)

fun myexists(_: 'a -> bool)([]: 'a list): bool = false
|   myexists pred (x :: xs) = (pred x) orelse (myexists pred xs)

fun myfold(func: ('a * 'b) -> 'b)(zero: 'b)([]: 'a list): 'b = zero
|   myfold func zero (x :: xs) = myfold func (func(x, zero)) xs

fun mymin(x: int, y: int): int = if x < y then x else y

fun minlist(ints: int list): int = case ints of
        [] => 0                                   (* dummy, should not happen *)
    |   [n] => n
    |   n :: ns => myfold mymin n ns

fun myall(func: 'a -> bool)(lst: 'a list): bool =
    myfold (fn (x, y) => (func x) andalso y) true lst

(*  assumes lists have equal length. *)
fun myzip([]: 'a list)([]: 'b list): ('a * 'b) list = []
|   myzip(x :: xs)(y :: ys) = (x, y) :: myzip xs ys

(*  helpers for score_challenge  *)
fun new_sums([]: int list, value: int): int list = [value]
|   new_sums(sums, value) = mymap (fn x => x + value) sums

fun sum_cards_challenge([]: card list): int list = [0]
|   sum_cards_challenge(cards) =
    let
        fun aux([]: card list, sums: int list): int list = sums
        |   aux(c :: cs, sums) =
                if card_rank(c) = Ace
                then aux(cs, new_sums(sums, 1) @ new_sums(sums, 11))
                else aux(cs, new_sums(sums, card_value(c)))
    in
        aux(cards, [])
    end

(*  computes lowest score for held cards  *)
fun score_challenge(heldcards: card list, goal: int): int =
    let
        val sums: int list = sum_cards_challenge(heldcards)
        val same: bool = all_same_color(heldcards)
        fun prelim_fun(sum: int): int =
            if sum > goal then (sum - goal) * 3 else goal - sum
        val prelims: int list = mymap prelim_fun sums
        fun result(prelim: int): int = prelim div (if same then 2 else 1)
        val results: int list = mymap result prelims
    in
        minlist results
    end

(*  helper for officiate_challenge *)
fun state_challenge(
    held: card list, _: card list, []: move list, goal: int
): int =
    score_challenge(held, goal)
|   state_challenge(held, cs, (Discard c) :: ms, goal) =
        state_challenge(remove_card(held, c, IllegalMove), cs, ms, goal)
|   state_challenge(held, [], Draw :: ms, goal) = score_challenge(held, goal)
|   state_challenge(held, c :: cs, Draw :: ms, goal) =
        if myall (fn sum => sum > goal) (sum_cards_challenge(c :: held))
        then score_challenge(c :: held, goal)
        else state_challenge(c :: held, cs, ms, goal)

(*  runs a game, returns score *)
fun officiate_challenge(cards: card list, moves: move list, goal: int): int =
    state_challenge([], cards, moves, goal)

(*  careful_player stuff  *)
(*             held       cards    goal  score   *)
type stat = card list * card list * int * int

(*  Returns the next state after making a move.
    Checks if the move is possible. If not, returns input state.
    Checks if the move puts hand total above goal. If so, returns input state.
 *)
fun next_state(st: stat)(mv: move): stat =
    let
        val (held, cards, gl, scr) = st
    in
        case (held, cards, mv) of
            (_, [], Draw) => st
        |   (_, c :: cs, Draw) =>
            if sum_cards(c :: held) > gl
            then st
            else (c :: held, cs, gl, score(c :: held, gl))
        |   (_, _, Discard(c)) =>
            if myfind(c, held)
            then
                let
                    val newheld = remove_card(held, c, IllegalMove)
                in
                    (newheld, cards, gl, score(newheld, gl))
                end
            else st
    end

(*  Checks if it's possible to draw without going over goal.
    This applies only when goal - sum_cards(held) <= 10.  *)
fun possible_to_draw(st: stat): bool =
    let
        val (held, cards, gl, _) = st
    in
        case cards of
            [] => false
        |   c :: cs => sum_cards(c :: held) <= gl
    end

(*  Checks if it's possible to reach 0 by discarding-then-drawing. *)
fun possible_to_discard_then_draw(st: stat): bool =
    let
        val (held, cards, _, _) = st
    in
        case (held, cards) of
            (_, []) => false                          (* not possible to draw *)
        |   ([], _) => false                       (* not possible to discard *)
        |   (h :: hs, c :: cs) =>
            let
                val discard_states: stat list =
                    mymap (fn crd => next_state st (Discard crd)) held
                val discard_then_draw_states: stat list =
                    mymap (fn sta => next_state sta Draw) discard_states
            in
                myexists (fn (_, _, _, scr) => scr = 0) discard_then_draw_states
            end
    end

(*  Assumes it's possible to reach 0 by discarding-then-drawing.  *)
fun discard_then_draw(st: stat): stat * card =
    let
        val (held, cards, _, _) = st
        val discard_states: (stat * card) list =
            mymap (fn crd => (next_state st (Discard crd), crd)) held
        val discard_then_draw_states: (stat * card) list =
            mymap (fn (sta, crd) => (next_state sta Draw, crd)) discard_states
        val zero_states: (stat * card) list =          (* guaranteed nonempty *)
            myfilter (fn ((_, _, _, scr), _) => scr = 0) discard_then_draw_states
    in
        case zero_states of                     (* just to avoid using hd, tl *)
            [] => (st, (Hearts, Ace))           (* dummy, should never happen *)
        |   x :: xs => x
    end

fun careful_helper(st: stat): move list =
    let
        val (held, cards, gl, scr) = st
        val drawn_state = next_state st Draw
    in
        if scr = 0 then
            []
        else if gl - sum_cards(held) > 10 orelse possible_to_draw(st) then
            if cards <> [] then
                Draw :: (careful_helper drawn_state)
            else [Draw] (* avoid inf loop if attempting to draw on empty deck *)
        else if possible_to_discard_then_draw(st) then
            let
                val (newstate, discarded) = discard_then_draw(st)
            in
                Discard(discarded) :: Draw :: careful_helper(newstate)
            end
        else []
    end

fun careful_player(cards: card list, gl: int): move list =
    careful_helper([], cards, gl, gl div 2)
