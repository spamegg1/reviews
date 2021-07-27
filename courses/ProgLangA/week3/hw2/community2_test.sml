(* TEST file for community2.sml
   solution    credit goes to:  Escanor *)

use "community2.sml";

val test1a: bool = pass_or_fail {grade=SOME 20, id=10} = fail
val test1b: bool = pass_or_fail {grade=SOME 80, id=6} = pass
val test1c: bool = pass_or_fail {grade=NONE, id=10} = fail

val test2a: bool = has_passed {grade=SOME 20, id=10} = false
val test2b: bool = has_passed {grade=SOME 80, id=6} = true
val test2c: bool = has_passed {grade=NONE, id=10} = false

val test3a: bool = number_passed [{grade=SOME 20, id=10}] = 0
val test3b: bool = number_passed [{grade=SOME 80, id=6},
                                  {grade=SOME 20, id=10},
                                  {grade=SOME 75, id=10}] = 2
val test3c: bool = number_passed [{grade=NONE, id=10}] = 0

val test4a: bool= number_misgraded [(pass, {grade=SOME 20, id=10})] = 1
val test4b: bool= number_misgraded [(fail, {grade=SOME 80, id=6}),
                                    (pass, {grade=SOME 20, id=10}),
                                    (pass, {grade=SOME 75, id=10})] = 2
val test4c: bool= number_misgraded [(fail, {grade=NONE, id=10})] = 0

val test5a: bool = tree_height leaf = 0
val test5b: bool = tree_height (node {value=5,left=leaf,right=leaf}) = 1
val test5c: bool = tree_height (node {value=5,
                    left=node {value=4, left=leaf,right=leaf},
                    right=leaf}) = 2

val test5d: bool = tree_height (node {value=5,
                    left=node {value=4,
                        left=node {value=2,
                               left=leaf,
                               right=node {value=3, left=leaf, right=leaf}},
                        right=leaf},
                    right=leaf}) = 4

val test5e: bool = tree_height (node {value=5,
                    left=leaf,
                    right=node {value=4,
                        left=node {value=2,
                            left=leaf,
                            right=node {value=3, left=leaf, right=leaf}},
                        right=leaf}}) = 4

val test6a: bool = sum_tree leaf = 0
val test6b: bool = sum_tree (node {value=5,left=leaf,right=leaf}) = 5
val test6c: bool = sum_tree (node {value=5,
                     left=node {value=4, left=leaf,right=leaf},
                     right=leaf}) = 9

val test6d: bool = sum_tree (node {value=5,
                     left=node {value=4,
                        left=node {value=2,
                                left=leaf,
                                right=node {value=3, left=leaf, right=leaf}},
                        right=leaf},
                    right=leaf}) = 14

val test6e: bool = sum_tree (node {value=5,
                    left=leaf,
                    right=node {value=4,
                        left=node {value=2,
                            left=leaf,
                            right=node {value=3, left=leaf, right=leaf}},
                        right=leaf}}) = 14

val test7a: bool = gardener leaf = leaf
val test7b: bool = gardener (node {value=prune_me, left=leaf, right=leaf}) = leaf
val test7c: bool = gardener (node {value=leave_me_alone,
                     left=node {value=prune_me, left=leaf, right=leaf},
                     right=leaf})
                     = node {value=leave_me_alone, left=leaf, right=leaf}

val test7d: bool = gardener (node {value=leave_me_alone,
                    left=node {value=leave_me_alone,
                        left=node {value=prune_me,
                            left=leaf,
                            right=node {value=leave_me_alone, left=leaf, right=leaf}},
                        right=leaf},
                    right=leaf})
                    = node {value=leave_me_alone,
                            left=node {value=leave_me_alone,
                                left=leaf,
                                right=leaf},
                            right=leaf}


val test7e: bool = gardener (node {value=prune_me,
                    left=leaf,
                    right=node {value=leave_me_alone,
                        left=node {value=leave_me_alone,
                            left=leaf,
                            right=node {value=leave_me_alone, left=leaf, right=leaf}},
                        right=leaf}}) = leaf

val test9a: bool = is_positive ZERO = false
val test9b: bool = is_positive (SUCC ZERO) = true

val test10a: bool = pred (SUCC ZERO) = ZERO
val test10b: bool = pred (SUCC (SUCC ZERO)) = SUCC ZERO

val test11a: bool = nat_to_int ZERO = 0
val test11b: bool = nat_to_int (SUCC (SUCC (SUCC ZERO))) = 3

val test12a: bool = int_to_nat 0 = ZERO
val test12b: bool = int_to_nat 3 = (SUCC (SUCC (SUCC ZERO)))

val test13a: bool = add (ZERO, ZERO) = ZERO
val test13b: bool = add (ZERO, SUCC ZERO) = SUCC ZERO
val test13c: bool = add (int_to_nat 5, int_to_nat 7) = int_to_nat 12

val test14a: bool = sub (ZERO, ZERO) = ZERO
val test14b: bool = sub (SUCC ZERO, ZERO) = SUCC ZERO
val test14c: bool = sub (int_to_nat 7, int_to_nat 5) = int_to_nat 2

val test15a: bool = mult (ZERO, SUCC ZERO) = ZERO
val test15b: bool = mult (SUCC ZERO, ZERO) = ZERO
val test15c: bool = mult (SUCC ZERO, SUCC ZERO) = SUCC ZERO
val test15d: bool = mult (int_to_nat 7, int_to_nat 5) = int_to_nat 35

val test16a: bool = less_than (ZERO, SUCC ZERO) = false
val test16b: bool = less_than (ZERO, ZERO) = false
val test16c: bool = less_than (SUCC (SUCC ZERO), SUCC ZERO) = true

(* Test cases for helper functions *)
val testH1a: bool = does_include ([], 1) = false
val testH1b: bool = does_include ([1 ,2 ,3], 4) = false
val testH1c: bool = does_include ([1 ,2 ,3, 4], 4) = true

val testH2a: bool = intersection ([], []) = []
val testH2b: bool = intersection ([1], []) = []
val testH2c: bool = intersection ([], [1]) = []
val testH2d: bool = intersection ([1], [1]) = [1]
val testH2e: bool = intersection ([1], [2]) = []
val testH2f: bool = intersection ([1, 2, 3, 4, 10], [2, 4, 5, 7, 10]) = [10, 4, 2]

val testH3a: bool = union ([], []) = []
val testH3b: bool = union ([1], []) = [1]
val testH3c: bool = union ([], [1]) = [1]
val testH3d: bool = union ([1], [1]) = [1]
val testH3e: bool = union ([1], [2]) = [1, 2]
val testH3f: bool = union ([1, 2, 3, 4, 10], [2, 4, 5, 7, 10]) = [3, 1, 2, 4, 5, 7, 10]

val testH4a: bool = range (1, 1) = [1]
val testH4b: bool = range (2, 1) = []
val testH4c: bool = range (2, 7) = [2, 3, 4, 5, 6, 7]

val testH5a: bool = filter_duplicate ([]) = []
val testH5b: bool = filter_duplicate ([1, 2, 3, 2, 5, 1]) = [5, 3, 2, 1]

val test17a: bool = isEmpty (Elems []) = true
val test17b: bool = isEmpty (Elems [1]) = false
val test17c: bool = isEmpty (Intersection (Union (Elems [1, 2, 3],
                        Elems [2, 3, 4]), Elems [2, 4, 6])) = false
val test17d: bool = isEmpty (Intersection (Union (Elems [1, 2, 3],
                         Elems [2, 3, 4]), Elems [6, 8])) = true

val test18a: bool = contains (Elems [1, 2, 3], 3) = true
val test18b: bool = contains (Elems [1, 2, 3], 4) = false
val test18c: bool = contains (Intersection (Union (Elems [1, 2, 3],
                          Elems [2, 3, 4]), Elems [2, 4, 6]), 2) = true
val test18d: bool = contains (Intersection (Union (Elems [1, 2, 3],
                          Elems [2, 3, 4]), Elems [6, 8]), 2) = false

val test19a: bool = toList (Elems [1, 2, 3, 2, 5, 1]) = [5, 3, 2, 1]
val test19b: bool = toList (Range {from=2, to=5}) = [2, 3, 4, 5]
val test19c: bool = toList (Intersection (Range {from=2, to=5},
                                          Range {from=3, to=7})) = [5, 4, 3]
val test19d: bool = toList (Intersection (Union (Elems [1, 2, 3],
                        Elems [2, 3, 4]), Elems [2, 4, 6])) = [2, 4]
