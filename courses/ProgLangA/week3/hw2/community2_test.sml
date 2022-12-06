(* TEST file for community2.sml
   solution    credit goes to:  Escanor *)

use "community2.sml";

val test1a: bool = pass_or_fail {grade=SOME 20, id=10} = fail
val test1b: bool = pass_or_fail {grade=SOME 80, id=6} = pass
val test1c: bool = pass_or_fail {grade=NONE, id=10} = fail

val test2a: bool = not(has_passed {grade=SOME 20, id=10})
val test2b: bool = has_passed {grade=SOME 80, id=6}
val test2c: bool = not(has_passed {grade=NONE, id=10})

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

val test9a: bool = not(is_positive ZERO)
val test9b: bool = is_positive (SUCC ZERO)

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

val test16a: bool = not(less_than (ZERO, SUCC ZERO))
val test16b: bool = not(less_than (ZERO, ZERO))
val test16c: bool = less_than (SUCC (SUCC ZERO), SUCC ZERO)

(* Test cases for helper functions *)
val testH1a: bool = not(does_include ([], 1))
val testH1b: bool = not(does_include ([1 ,2 ,3], 4))
val testH1c: bool = does_include ([1 ,2 ,3, 4], 4)

val testH2a: bool = null(intersection ([], []))
val testH2b: bool = null(intersection ([1], []))
val testH2c: bool = null(intersection ([], [1]))
val testH2d: bool = intersection ([1], [1]) = [1]
val testH2e: bool = null(intersection ([1], [2]))
val testH2f: bool = intersection ([1, 2, 3, 4, 10], [2, 4, 5, 7, 10]) = [10, 4, 2]

val testH3a: bool = null(union ([], []))
val testH3b: bool = union ([1], []) = [1]
val testH3c: bool = union ([], [1]) = [1]
val testH3d: bool = union ([1], [1]) = [1]
val testH3e: bool = union ([1], [2]) = [1, 2]
val testH3f: bool = union ([1, 2, 3, 4, 10], [2, 4, 5, 7, 10]) = [3, 1, 2, 4, 5, 7, 10]

val testH4a: bool = range (1, 1) = [1]
val testH4b: bool = null(range (2, 1))
val testH4c: bool = range (2, 7) = [2, 3, 4, 5, 6, 7]

val testH5a: bool = null(filter_duplicate [])
val testH5b: bool = filter_duplicate [1, 2, 3, 2, 5, 1] = [5, 3, 2, 1]

val test17a: bool = isEmpty (Elems [])
val test17b: bool = not(isEmpty (Elems [1]))
val test17c: bool = not(isEmpty (Intersection (Union (Elems [1, 2, 3],
                        Elems [2, 3, 4]), Elems [2, 4, 6])))
val test17d: bool = isEmpty (Intersection (Union (Elems [1, 2, 3],
                         Elems [2, 3, 4]), Elems [6, 8]))

val test18a: bool = not(contains (Elems [1, 2, 3], 3))
val test18b: bool = not(contains (Elems [1, 2, 3], 4))
val test18c: bool = contains (Intersection (Union (Elems [1, 2, 3],
                          Elems [2, 3, 4]), Elems [2, 4, 6]), 2)
val test18d: bool = not(contains (Intersection (Union (Elems [1, 2, 3],
                          Elems [2, 3, 4]), Elems [6, 8]), 2))

val test19a: bool = toList (Elems [1, 2, 3, 2, 5, 1]) = [5, 3, 2, 1]
val test19b: bool = toList (Range {from=2, to=5}) = [2, 3, 4, 5]
val test19c: bool = toList (Intersection (Range {from=2, to=5},
                                          Range {from=3, to=7})) = [5, 4, 3]
val test19d: bool = toList (Intersection (Union (Elems [1, 2, 3],
                        Elems [2, 3, 4]), Elems [2, 4, 6])) = [2, 4]
