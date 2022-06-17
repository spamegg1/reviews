(* Homework1 Simple Test *)
(* These are basic test cases. Passing these tests does not guarantee that *)
(* your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "hw1.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: *)
(* val test1 = true : bool *)

use "hw1.sml";

val test1a = is_older ((1, 2, 3), (2, 3, 4)) = true
val test1b = is_older ((2, 3, 4), (1, 2, 3)) = false
val test1c = is_older ((1, 2, 3), (1, 3, 4)) = true
val test1d = is_older ((1, 3, 4), (1, 2, 3)) = false
val test1e = is_older ((1, 2, 3), (1, 2, 4)) = true
val test1f = is_older ((1, 2, 4), (1, 2, 3)) = false
val test1g = is_older ((1, 2, 3), (1, 2, 3)) = false

val test2a = number_in_month([], 2) = 0
val test2b = number_in_month([(2012, 2, 28), (2013, 12, 1)], 1) = 0
val test2c = number_in_month([(2012, 2, 28), (2013, 12, 1)], 2) = 1
val test2d = number_in_month([(2012, 12, 28), (2013, 12, 1)], 12) = 2

val test3a = number_in_months ([], []) = 0
val test3b = number_in_months (
    [(2012, 2, 28), (2013, 12, 1), (2011, 3, 31), (2011, 4, 28)], []) = 0
val test3c = number_in_months ([], [2, 3, 4]) = 0
val test3d = number_in_months (
    [(2012, 2, 28), (2013, 12, 1), (2011, 3, 31), (2011, 4, 28)], [2]) = 1
val test3e = number_in_months (
    [(2012, 2, 28), (2013, 12, 1), (2011, 3, 31), (2011, 4, 28)], [2, 3]) = 2
val test3f = number_in_months (
    [(2012, 2, 28), (2013, 12, 1), (2011, 3, 31), (2011, 4, 28)], [2, 3, 4]) = 3

val test4a = dates_in_month ([], 1) = []
val test4b = dates_in_month ([(2012, 2, 28), (2013, 12, 1)], 1) = []
val test4c = dates_in_month ([(2012, 2, 28), (2013, 12, 1)], 2) = [(2012, 2, 28)]
val test4d = dates_in_month ([(2012, 2, 28), (2013, 12, 1)], 12) = [(2013, 12, 1)]
val test4e = dates_in_month ([(2012, 3, 28), (2013, 12, 1), (2013, 3, 29)], 3)
    = [(2012, 3, 28), (2013, 3, 29)]
val test4f = dates_in_month ([(2012, 3, 28), (2013, 12, 1), (2013, 3, 29),
    (2011,1,1), (2014,12,1), (2013, 3, 2), (2015, 12, 1)], 3)
    = [(2012, 3, 28), (2013, 3, 29), (2013, 3, 2)]

val test5a = dates_in_months([], []) = []
val test5b = dates_in_months([], [2]) = []
val test5c = dates_in_months([(2012, 3, 28), (2013, 12, 1)], []) = []
val test5d = dates_in_months ([(2012, 2, 28), (2013, 12, 1), (2011, 3, 31),
    (2011, 4, 28)], [2]) = [(2012, 2, 28)]
val test5e = dates_in_months ([(2012, 2, 28), (2013, 12, 1), (2011, 3, 31),
    (2011, 4, 28)], [2,3]) = [(2012, 2, 28), (2011, 3, 31)]
val test5f = dates_in_months ([(2012, 2, 28), (2013, 12, 1), (2011, 3, 31),
    (2011, 4, 28)], [2,3,4]) = [(2012, 2, 28), (2011, 3, 31), (2011, 4, 28)]

val test6a = get_nth (["hi", "there", "how", "are", "you"], 1) = "hi"
val test6b = get_nth (["hi", "there", "how", "are", "you"], 2) = "there"
val test6c = get_nth (["hi", "there", "how", "are", "you"], 3) = "how"
val test6d = get_nth (["hi", "there", "how", "are", "you"], 4) = "are"
val test6e = get_nth (["hi", "there", "how", "are", "you"], 5) = "you"

val test7a = date_to_string (2013, 6, 1) = "June 1, 2013"

val test8a = number_before_reaching_sum (1, [2]) = 0
val test8b = number_before_reaching_sum (2, [1, 1]) = 1
val test8c = number_before_reaching_sum (5, [1, 2, 3]) = 2
val test8d = number_before_reaching_sum (10, [1, 2, 3, 4, 5]) = 3
val test8e = number_before_reaching_sum (20, [2, 3, 4, 5, 6, 4, 5, 6]) = 4
val test8f = number_before_reaching_sum (20, [6, 2, 5, 3, 4, 2, 7, 1]) = 4

val test9a = what_month 1 = 1
val test9b = what_month 31 = 1
val test9c = what_month 32 = 2
val test9d = what_month 59 = 2
val test9e = what_month 60 = 3
val test9f = what_month 90 = 3
val test9g = what_month 91 = 4
val test9h = what_month 120 = 4
val test9i = what_month 121 = 5
val test9k = what_month 151 = 5
val test9l = what_month 152 = 6
val test9m = what_month 181 = 6
val test9n = what_month 182 = 7
val test9o = what_month 212 = 7
val test9p = what_month 213 = 8
val test9q = what_month 243 = 8
val test9r = what_month 244 = 9
val test9s = what_month 273 = 9
val test9t = what_month 274 = 10
val test9u = what_month 304 = 10
val test9v = what_month 305 = 11
val test9w = what_month 334 = 11
val test9x = what_month 335 = 12
val test9y = what_month 365 = 12

val test10a = month_range (2, 1) = []
val test10b = month_range (1, 1) = [1]
val test10c = month_range (1, 2) = [1, 1]
val test10d = month_range (1, 3) = [1, 1, 1]
val test10e = month_range (31, 32) = [1, 2]
val test10f = month_range (304, 305) = [10, 11]
val test10g = month_range (242, 245) = [8, 8, 9, 9]

val test11a = oldest([]) = NONE
val test11b = oldest([(2012, 2, 28)]) = SOME (2012, 2, 28)
val test11c = oldest([(2012, 2, 28), (2011, 3, 31), (2011, 4, 28)])
    = SOME (2011, 3, 31)
val test11d = oldest([(2011, 2, 28), (2011, 2, 28), (2011, 4, 28)])
    = SOME (2011, 2, 28)
val test11e = oldest([(2011, 3, 31), (2011, 3, 31), (2011, 3, 31)])
    = SOME (2011, 3, 31)
