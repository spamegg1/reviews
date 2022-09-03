use "hw2.sml";

fun mymap(func: 'a -> 'b)([]: 'a list): 'b list = []
|   mymap func (x :: xs) = (func x) :: mymap func xs

fun new_sums(sums: int list, value: int): int list =
    case sums of
        [] => [value]
    |   _  => mymap (fn x => x + value) sums

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

fun myfold(func: ('a * 'b) -> 'b)(zero: 'b)([]: 'a list): 'b = zero
|   myfold func zero (x :: xs) = myfold func (func(x, zero)) xs

fun mymin(x: int, y: int): int = if x < y then x else y

(* Assumes ints is not empty. *)
fun minlist(ints: int list): int =
    myfold mymin (hd ints) (tl ints)

(*  computes lowest score for held cards  *)
fun score_challenge(heldcards: card list, goal: int): int =
    let
        val sums: int list = sum_cards_challenge(heldcards)
        val same: bool = all_same_color(heldcards)
        val prelim_fun: int -> int = fn sum =>
            if   sum > goal
            then (sum - goal) * 3
            else goal - sum
        val prelims: int list = mymap prelim_fun sums
        val result: int -> int = fn prelim => prelim div (if same then 2 else 1)
        val results: int list = mymap result prelims
    in
        minlist results
    end

fun myall(func: 'a -> bool)(lst: 'a list): bool =
    myfold (fn (x, y) => (func x) andalso y) true lst

(*  runs a game, returns score *)
fun officiate_challenge(cards: card list, moves: move list, goal: int): int =
    let
        fun state(held: card list, _: card list, []: move list): int =
                score_challenge(held, goal)
        |   state(held, cs, (Discard c) :: ms) =
                state(remove_card(held, c, IllegalMove), cs, ms)
        |   state(held, [], Draw :: ms) = score_challenge(held, goal)
        |   state(held, c :: cs, Draw :: ms) =
            if myall (fn sum => sum > goal) (sum_cards_challenge(c :: held))
            then score_challenge(c :: held, goal)
            else state(c :: held, cs, ms)
    in
        state([], cards, moves)
    end

(* tests for score_challenge *)
val test12a1 = score_challenge([], 100) = 50
val test12a2 = score_challenge([(Hearts, Num 2), (Clubs, Num 4)], 10) = 4
val test12a3 = score_challenge([(Hearts, Num 2), (Clubs, Num 4)], 5) = 3
val test12a4 = score_challenge([(Hearts, Num 2), (Diamonds, Num 4)], 10) = 2
val test12a5 = score_challenge([(Hearts, Num 10), (Diamonds, Num 4)], 10) = 6
val test12a6 = score_challenge([(Hearts, Ace), (Diamonds, Ace)], 10) = minlist [4, 3, 18]
val test12a7 = score_challenge(
    [(Hearts, Ace), (Diamonds, Ace), (Spades, Ace), (Clubs, Ace)], 10)
    = minlist [6, 12, 42, 72, 102]

val test12b1 = officiate_challenge([(Hearts, Num 2), (Clubs, Num 4)], [Draw], 15) = 6
val test12b2 = officiate_challenge([(Hearts, Num 2), (Clubs, Num 4)], [Draw, Draw], 15) = 9
val test12b3 = officiate_challenge([(Hearts, Num 9), (Clubs, Num 8)], [Draw, Draw], 15) = 6
val test12b4 = officiate_challenge([(Hearts, Num 9),(Clubs, Num 8), (Spades, Ace)],
    [Draw, Draw, Draw], 15) = 6
val test12b5 = officiate_challenge([(Hearts, Num 2), (Clubs, Num 4)],
    [Draw, Draw, Discard(Clubs, Num 4)], 15) = 6
val test12b6 = officiate_challenge(
    [(Clubs, Ace), (Spades, Ace), (Clubs, Ace), (Spades, Ace)],
    [Draw, Draw, Draw, Draw], 42) = 3
val test12b7 = officiate_challenge([(Clubs, Ace)], [Draw], 42)= 15
val test12b8 = officiate_challenge([(Clubs, Ace), (Spades, Ace)], [Draw, Draw], 42) = 10
val test12b9 = officiate_challenge([(Clubs, Ace), (Spades, Ace), (Clubs, Ace)],
                        [Draw, Draw, Draw], 42) = 4
val test12b10 = ((officiate_challenge([(Clubs, Jack), (Spades, Num(8))],
    [Draw, Discard(Hearts, Jack)], 42); false) handle IllegalMove => true)
val test12b11 = officiate_challenge([], [], 10) = 5
val test12b12 = officiate_challenge([(Clubs, Ace)], [], 10) = 5
val test12b13 = officiate_challenge([], [Draw], 10) = 5
val test12b14 = ((officiate_challenge([], [Discard(Diamonds, Num(9))], 10); false)
    handle IllegalMove => true)
