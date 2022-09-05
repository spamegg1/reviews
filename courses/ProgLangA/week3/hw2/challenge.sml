use "hw2.sml";

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
        [] => 0
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
type hand = card list
type deck = card list
type moves = move list
type goal = int
type scor = int
type stat = hand * deck * goal * scor

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

fun careful_helper(st: stat): moves =
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

(*   property-based testing for careful_player  *)
type property = stat * moves -> bool

(*  The value of the held cards never exceeds the goal. *)
val prop1: property = fn ((held, _, gl, _), _) => sum_cards(held) <= gl

(*  A card is drawn whenever the goal is more than 10 greater
    than the value of the held cards. *)
val prop2: property = fn ((held, _, gl, _), mvs) =>
    gl - sum_cards(held) <= 10 orelse
    case mvs of
        [] => false
    |   m :: ms => m = Draw

(*  If a score of 0 is reached, there must be no more moves. *)
val prop3: property = fn ((_, _, _, scr), mvs) => scr <> 0 orelse mvs = []

(*  If it is possible to reach a score of 0 by discarding a card
    followed by drawing a card, then this must be done. *)
val prop4: property = fn (st, mvs) =>
    not(possible_to_discard_then_draw st) orelse
    case mvs of
        (Discard _) :: Draw :: ms => true
    |   _ => false

val properties: property list = [prop1, prop2, prop3, prop4]

fun check_prop(prop: property, mvs: moves, st: stat): bool =
    case mvs of
        [] => true
    |   m :: ms => prop(st, mvs) andalso check_prop(prop, ms, next_state st m)

fun check(mvs: moves, st: stat) =
    myall (fn prop => check_prop(prop, mvs, st)) properties

(* tests for score_challenge *)
val t12a = score_challenge([(Hearts,Ace),(Diamonds,Ace)],10) = minlist [4,3,18]
val t12b = score_challenge(
    [(Hearts, Ace), (Diamonds, Ace), (Spades, Ace), (Clubs, Ace)], 10
) = minlist [6, 12, 42, 72, 102]

(*  tests for careful_player  *)
val cards: deck = [
    (Hearts, Num 2), (Spades, Num 7), (Clubs, Jack), (Diamonds, Ace)]
val st1: stat = ([], cards, 10, 5)
val st2: stat = ([], cards, 20, 10)
val st3: stat = ([], cards, 30, 15)
val st4: stat = ([], cards, 40, 20)
val st5: stat = ([], cards, 50, 25)
val stats = [st1, st2, st3, st4, st5]
val ms1: moves = careful_player(cards, 10)
val ms2: moves = careful_player(cards, 20)
val ms3: moves = careful_player(cards, 30)
val ms4: moves = careful_player(cards, 40)
val ms5: moves = careful_player(cards, 50)
val mvs = [ms1, ms2, ms3, ms4, ms5]
val moves_stats: (moves * stat) list = myzip mvs stats
val all_in_one_test = myall check moves_stats
