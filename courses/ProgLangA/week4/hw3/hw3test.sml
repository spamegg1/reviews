(* Homework3 Simple Test*)
(* These are basic test cases. Passing these tests does not
   guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file:
   use "homeworkname.sml"; *)
(* All the tests should evaluate to true.
   For example, the REPL should say: val test1 = true : bool *)
use "hw3.sml";

(* test data *)
val f = (fn (x,y) => x > y)
val h = (fn (x,y) => x >= y)
val five = [1, 2, 3, 4, 5]
val pat1 = Wildcard
val pat2 = Variable "pat2"
val pat3 = UnitP
val pat4 = ConstP 17
val pat5 = TupleP [pat1, pat2, pat3, pat4]
val pat6 = ConstructorP("pat5", pat5)
val pat7 = TupleP [pat1, pat1, pat1, pat1, pat1, pat1, pat1]
val pat8 = TupleP [pat1, TupleP [pat5, pat2], pat2, pat7,
    TupleP [pat7, pat3, pat4], pat6]
val val5 = Tuple [Const(1), Const(2), Unit, Const(17)]

val test1a = only_capitals [] = []
val test1b = only_capitals ["A", "B", "C"] = ["A", "B", "C"]
val test1c = only_capitals ["a", "B", "C"] = ["B", "C"]
val test1d = only_capitals ["A", "b", "C"] = ["A", "C"]
val test1e = only_capitals ["a", "b", "c"] = []

val test2a = longest_string1 [] = ""
val test2b = longest_string1 ["A", "bc", "C"] = "bc"
val test2c = longest_string1 ["ABC", "bc", "C"] = "ABC"
val test2d = longest_string1 ["A", "bc", "abC"] = "abC"
val test2e = longest_string1 ["A", "bc", "ABC", "abcd"] = "abcd"
val test2f = longest_string1 ["A", "bc", "bC"] = "bc"
val test2g = longest_string1 ["ABC", "bcA", "bC", "abc"] = "ABC"
val test2h = longest_string1 ["ABC", "bcA", "abcd", "bC", "ABCD", "abc"] = "abcd"

val test3a = longest_string2 [] = ""
val test3b = longest_string2 ["A", "bc", "C"] = "bc"
val test3c = longest_string2 ["ABC", "bc", "C"] = "ABC"
val test3d = longest_string2 ["A", "bc", "abC"] = "abC"
val test3e = longest_string2 ["A", "bc", "ABC", "abcd"] = "abcd"
val test3f = longest_string2 ["A", "bc", "bC"] = "bC"
val test3g = longest_string2 ["ABC", "bcA", "bC", "abc"] = "abc"
val test3h = longest_string2 ["ABC", "bcA", "abcd", "bC", "ABCD", "abc"] = "ABCD"

val test4a1 = longest_string_helper f [] = ""
val test4b1 = longest_string_helper f ["A", "bc", "C"] = "bc"
val test4c1 = longest_string_helper f ["ABC", "bc", "C"] = "ABC"
val test4d1 = longest_string_helper f ["A", "bc", "abC"] = "abC"
val test4e1 = longest_string_helper f ["A", "bc", "ABC", "abcd"] = "abcd"
val test4f1 = longest_string_helper f ["A", "bc", "bC"] = "bc"
val test4g1 = longest_string_helper f ["ABC", "bcA", "bC", "abc"] = "ABC"
val test4a2 = longest_string_helper h [] = ""
val test4b2 = longest_string_helper h ["A", "bc", "C"] = "bc"
val test4c2 = longest_string_helper h ["ABC", "bc", "C"] = "ABC"
val test4d2 = longest_string_helper h ["A", "bc", "abC"] = "abC"
val test4e2 = longest_string_helper h ["A", "bc", "ABC", "abcd"] = "abcd"
val test4f2 = longest_string_helper h ["A", "bc", "bC"] = "bC"
val test4g2 = longest_string_helper h ["ABC", "bcA", "bC", "abc"] = "abc"
val test4a3 = longest_string3 [] = ""
val test4b3 = longest_string3 ["A", "bc", "C"] = "bc"
val test4c3 = longest_string3 ["ABC", "bc", "C"] = "ABC"
val test4d3 = longest_string3 ["A", "bc", "abC"] = "abC"
val test4e3 = longest_string3 ["A", "bc", "ABC", "abcd"] = "abcd"
val test4f3 = longest_string3 ["A", "bc", "bC"] = "bc"
val test4g3 = longest_string3 ["ABC", "bcA", "bC", "abc"] = "ABC"
val test4h3 = longest_string3 ["ABC", "bcA", "abcd", "bC", "ABCD", "abc"] = "abcd"
val test4a4 = longest_string4 [] = ""
val test4b4 = longest_string4 ["A", "B", "C"] = "C"
val test4c4 = longest_string4 ["A", "bc", "C"] = "bc"
val test4d4 = longest_string4 ["ABC", "bc", "C"] = "ABC"
val test4e4 = longest_string4 ["A", "bc", "abC"] = "abC"
val test4f4 = longest_string4 ["A", "bc", "ABC", "abcd"] = "abcd"
val test4g4 = longest_string4 ["A", "bc", "bC"] = "bC"
val test4h4 = longest_string4 ["ABC", "bcA", "bC", "abc"] = "abc"
val test4i4 = longest_string4 ["ABC", "bcA", "abcd", "bC", "ABCD", "abc"] = "ABCD"

val test5a = longest_capitalized [] = ""
val test5b = longest_capitalized ["a", "bc", "def"] = ""
val test5c = longest_capitalized ["A", "bc", "C"] = "A"
val test5d = longest_capitalized ["AB", "bc", "BC"] = "AB"
val test5e = longest_capitalized ["a", "BCD", "cde", "DEF"] = "BCD"
val test5f = longest_capitalized ["abc", "DEF", "ABC", "def"] = "DEF"

val test6a = rev_string "" = ""
val test6b = rev_string "abc" = "cba"

val test7a = first_answer (fn x => if x > 0 then SOME x else NONE) five = 1
val test7b = first_answer (fn x => if x > 1 then SOME x else NONE) five = 2
val test7c = first_answer (fn x => if x > 2 then SOME x else NONE) five = 3
val test7d = first_answer (fn x => if x > 3 then SOME x else NONE) five = 4
val test7e = first_answer (fn x => if x > 4 then SOME x else NONE) five = 5
val test7f = ((first_answer (fn x => if x > 5 then SOME x else NONE)
                five; false)  handle NoAnswer => true)
val test8a = all_answers (fn x => if x = 1 then SOME [x] else NONE) [] = SOME []
val test8b = all_answers (fn x => if x = 1 then SOME [x] else NONE)
    [2, 3, 4, 5, 6, 7] = NONE
val test8c = all_answers (fn x => if x > 1 then SOME [x] else NONE) [2, 3, 4, 5]
    = SOME [2, 3, 4, 5]
val test8d = all_answers (fn x => if x > 2 then SOME [x] else NONE)
    [2, 3, 4, 5, 6, 7] = NONE
val test8e = all_answers (fn x => if x = 3 then SOME [x] else NONE) [3]
    = SOME [3]
val test8f = all_answers (fn x => if x > 1 then SOME [x-2, x-1] else NONE) [2,3]
    = SOME [0, 1, 1, 2]

val test9a0 = count_wildcards Wildcard = 1
val test9a1 = count_wildcards pat1 = 1
val test9a2 = count_wildcards pat7 = 7
val test9a3 = count_wildcards pat5 = 1
val test9a4 = count_wildcards pat8 = 17

val test9b0 = count_wild_and_variable_lengths (Variable("a")) = 1
val test9b1 = count_wild_and_variable_lengths pat2 = 4
val test9b2 = count_wild_and_variable_lengths pat5 = 5
val test9b3 = count_wild_and_variable_lengths pat8 = 17 + 4 + 4 + 4 + 4

val test9c0 = count_some_var ("x", Variable("x")) = 1
val test9c1 = count_some_var ("pat2", pat8) = 4

val test10a = check_pat (Variable("x")) = true
val test10b = check_pat (TupleP [Variable("x"), Variable("y")]) = true
val test10c = check_pat (TupleP [Variable("x"), Variable("y"), Variable("x")])
    = false
val test10d = check_pat (pat1) = true
val test10e = check_pat (pat2) = true
val test10f = check_pat (pat3) = true
val test10g = check_pat (pat4) = true
val test10h = check_pat (pat5) = true
val test10i = check_pat (pat6) = true
val test10j = check_pat (pat7) = true
val test10k = check_pat (pat8) = false

val test11a = match (Const(1), UnitP) = NONE
val test11b = match (Const(17), pat4) = SOME []
val test11c = match (Const(1), Wildcard) = SOME []
val test11d = match (Unit, Wildcard) = SOME []
val test11e = match (Unit, pat3) = SOME []
val test11f = match (Const(1), pat2) = SOME [("pat2", Const(1))]
val test11g = match (Const(1), pat5) = NONE
val test11h = match (Tuple[Const(1)], pat5) = NONE
val test11i = match (val5, pat5) = SOME [("pat2", Const(2))]
val test11j = match (Tuple[Const 17, Unit, Const 4, Constructor ("egg", Const 4),
    Constructor("egg", Constructor ("egg", Const 4))], TupleP[Wildcard, Wildcard])
    = NONE

val test12 = first_match Unit [UnitP] = SOME []
