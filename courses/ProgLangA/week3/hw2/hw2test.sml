(* Homework2 Simple Test *)
(* These are basic test cases. Passing these tests *)
(* does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "hw2.sml"; *)
(* All the tests should evaluate to true. *)
(* For example, the REPL should say: val test1 = true : bool *)

use "hw2.sml";

val test1a = all_except_option ("string", []) = NONE
val test1b = all_except_option ("string", ["not_string"]) = NONE
val test1c = all_except_option ("string", ["string"]) = SOME([])
val test1d = all_except_option ("string", ["string", "a"]) = SOME(["a"])
val test1e = all_except_option ("string", ["a", "string"]) = SOME(["a"])
val test1f = all_except_option ("string", ["a", "string", "b"]) = SOME(["a", "b"])
val test1g = all_except_option ("string",
    ["a", "string", "b", "c", "string", "d", "e", "f"])
    = SOME(["a", "b", "c", "d", "e", "f"])

val test2a = get_substitutions1 ([["foo"], ["there"]], "foo") = []
val test2b = get_substitutions1 ([["foo"], ["foo"]], "foo") = []
val test2c = get_substitutions1 ([["there"], ["foo", "here"]], "foo") = ["here"]
val test2d = get_substitutions1 (
    [["Fred", "Fredrick"], ["Elizabeth", "Betty"], ["Freddie", "Fred", "F"]], "Fred")
    = ["Fredrick", "Freddie", "F"]
val test2e = get_substitutions1 (
    [["Fred", "Fredrick"], ["Jeff", "Jeffrey"], ["Geoff","Jeff", "Jeffrey"]], "Jeff")
    = ["Jeffrey", "Geoff", "Jeffrey"]

val test3a = get_substitutions2 ([["foo"], ["there"]], "foo") = []
val test3b = get_substitutions2 ([["foo"], ["foo"]], "foo") = []
val test3c = get_substitutions2 ([["there"], ["foo", "here"]], "foo") = ["here"]
val test3d = get_substitutions2 (
    [["Fred", "Fredrick"], ["Elizabeth", "Betty"], ["Freddie", "Fred", "F"]], "Fred")
    = ["Fredrick", "Freddie", "F"]
val test3e = get_substitutions2 (
    [["Fred", "Fredrick"], ["Jeff", "Jeffrey"], ["Geoff", "Jeff", "Jeffrey"]], "Jeff")
    = ["Jeffrey", "Geoff", "Jeffrey"]

val test4a = similar_names (
    [["Fred", "Fredrick"], ["Elizabeth", "Betty"], ["Freddie", "Fred", "F"]],
    {first="Fred", middle="W", last="Smith"}) =
    [{first="Fred", last="Smith", middle="W"},
    {first="Fredrick", last="Smith", middle="W"},
    {first="Freddie", last="Smith", middle="W"},
    {first="F", last="Smith", middle="W"}]
val test4b = similar_names (
    [["Fred", "Fredrick"], ["Jeff", "Jeffrey"], ["Geoff", "Jeff", "Jeffrey"]],
    {first="Jeff", middle="Z", last="Jeffries"}) =
    [{first="Jeff", middle="Z", last="Jeffries"},
    {first="Jeffrey", middle="Z", last="Jeffries"},
    {first="Geoff", middle="Z", last="Jeffries"},
    {first="Jeffrey", middle="Z", last="Jeffries"}]

val test5a = card_color (Clubs, Num 2) = Black
val test5b = card_color (Diamonds, Num 2) = Red
val test5c = card_color (Hearts, Num 2) = Red
val test5d = card_color (Spades, Num 2) = Black

val test6a = card_value (Clubs, Num 1) = 1
val test6b = card_value (Diamonds, Num 2) = 2
val test6c = card_value (Hearts, Num 3) = 3
val test6d = card_value (Spades, Num 4) = 4
val test6e = card_value (Clubs, Num 5) = 5
val test6f = card_value (Diamonds, Num 6) = 6
val test6g = card_value (Hearts, Num 7) = 7
val test6h = card_value (Spades, Num 8) = 8
val test6i = card_value (Clubs, Num 9) = 9
val test6j = card_value (Diamonds, Num 10) = 10
val test6k = card_value (Hearts, Jack) = 10
val test6l = card_value (Spades, Queen) = 10
val test6m = card_value (Clubs, King) = 10
val test6n = card_value (Diamonds, Ace) = 11

val test7a = ((remove_card (
    [], (Hearts, Ace), IllegalMove); false) handle IllegalMove => true)
val test7b = remove_card ([(Hearts, Ace)], (Hearts, Ace), IllegalMove) = []
val test7c = remove_card ([(Diamonds, Jack),(Hearts, Ace)],
    (Hearts, Ace), IllegalMove) = [(Diamonds, Jack)]
val test7d = remove_card ([(Hearts, Ace), (Diamonds, Jack)],
    (Hearts, Ace), IllegalMove) = [(Diamonds, Jack)]
val test7e = remove_card ([(Spades, Queen), (Hearts, Ace), (Diamonds, Jack)],
    (Hearts, Ace), IllegalMove) = [(Spades, Queen), (Diamonds, Jack)]
val test7f = remove_card (
    [(Spades, Num 1), (Hearts, Ace), (Diamonds, Num 5), (Hearts, Ace)],
    (Hearts, Ace), IllegalMove)
    = [(Spades, Num 1), (Diamonds, Num 5), (Hearts, Ace)]

val test8a = all_same_color [] = true
val test8b = all_same_color [(Hearts, Ace)] = true
val test8c = all_same_color [(Hearts, Ace), (Diamonds, Queen)] = true
val test8d = all_same_color [(Hearts, Ace), (Spades, King)] = false
val test8e = all_same_color [(Clubs, Ace), (Spades, Ace), (Clubs, Jack)] = true
val test8f = all_same_color [
    (Clubs, Ace), (Spades, Ace), (Clubs, Jack), (Hearts, Ace)] = false

val test9a = sum_cards [] = 0
val test9b = sum_cards [(Clubs, Num 1)] = 1
val test9c = sum_cards [(Clubs, Num 2),(Clubs, Num 10)] = 12
val test9d = sum_cards [(Clubs, Num 3),(Spades, Jack)] = 13
val test9e = sum_cards [(Clubs, Num 4),(Diamonds, Queen)] = 14
val test9f = sum_cards [(Clubs, Num 5),(Hearts, King)] = 15
val test9g = sum_cards [(Clubs, Num 5),(Clubs, Ace)] = 16
val test9h = sum_cards [(Clubs, Ace),(Clubs, King), (Clubs, Jack)] = 31

val test10a = score([], 100) = 50
val test10b = score ([(Hearts, Num 2), (Clubs, Num 4)], 10) = 4
val test10c = score ([(Hearts, Num 2), (Clubs, Num 4)], 5) = 3
val test10d = score ([(Hearts, Num 2), (Diamonds, Num 4)], 10) = 2
val test10e = score ([(Hearts, Num 10), (Diamonds, Num 4)], 10) = 6

val test11a = officiate([(Hearts, Num 2), (Clubs, Num 4)], [Draw], 15) = 6
val test11b = officiate([(Hearts, Num 2), (Clubs, Num 4)], [Draw, Draw], 15) = 9
val test11c = officiate([(Hearts, Num 9), (Clubs, Num 8)], [Draw, Draw], 15) = 6
val test11cc = officiate([(Hearts, Num 9),(Clubs, Num 8), (Spades, Ace)],
    [Draw, Draw, Draw], 15) = 6
val test11d = officiate([(Hearts, Num 2), (Clubs, Num 4)],
    [Draw, Draw, Discard(Clubs, Num 4)], 15) = 6
val test11e = officiate(
    [(Clubs, Ace), (Spades, Ace), (Clubs, Ace), (Spades, Ace)],
    [Draw, Draw, Draw, Draw], 42) = 3
val test11f = officiate([(Clubs, Ace)], [Draw], 42)= 15
val test11g = officiate([(Clubs, Ace), (Spades, Ace)], [Draw, Draw], 42) = 10
val test11h = officiate([(Clubs, Ace), (Spades, Ace), (Clubs, Ace)],
                        [Draw, Draw, Draw], 42) = 4
val test11i = ((officiate([(Clubs, Jack), (Spades, Num(8))],
    [Draw, Discard(Hearts, Jack)], 42); false) handle IllegalMove => true)
val test11j = officiate([], [], 10) = 5
val test11k = officiate([(Clubs, Ace)], [], 10) = 5
val test11l = officiate([], [Draw], 10) = 5
val test11m = ((officiate([], [Discard(Diamonds, Num(9))], 10); false)
    handle IllegalMove => true)
