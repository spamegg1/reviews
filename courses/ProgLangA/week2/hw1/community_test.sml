(* this is community_test.sml
   solution credit goes to: spamegg *)
use "community.sml";

val test1a: bool = alternate [1] = 1
val test1b: bool = alternate [1, 2] = ~1
val test1c: bool = alternate [1, 2, 3] = 2
val test1d: bool = alternate [1, 2, 3, 4] = ~2

val test2a: bool = min_max [1] = (1, 1)
val test2b: bool = min_max [1, 2] = (1, 2)
val test2c: bool = min_max [1, 2, 3] = (1, 3)
val test2d: bool = min_max [1, 2, 3, 4] = (1, 4)
val test2e: bool = min_max [9, 1, 8, 2, 7, 3, 6, 4, 5] = (1, 9)

val test3a: bool = cumsum [1, 4, 20] = [1, 5, 25]
val test3b: bool = cumsum [1] = [1]
val test3c: bool = cumsum [1, 2] = [1, 3]
val test3d: bool = cumsum [1, 2, 3] = [1, 3, 6]
val test3e: bool = cumsum [1, 2, 3, 4] = [1, 3, 6, 10]

val test4a: bool = greeting (SOME "world") = "Hello there, world!"
val test4b: bool = greeting NONE = "Hello there, you!"

val test5a: bool = repeat ([1, 2, 3], [4, 0, 3]) = [1, 1, 1, 1, 3, 3, 3]
val test5b: bool = repeat ([1, 2, 3], [0, 0, 0]) = []

val test6a: bool = addOpt (SOME 1, SOME 2) = SOME 3
val test6b: bool = addOpt (SOME 1, NONE) = NONE
val test6c: bool = addOpt (NONE, SOME 2) = NONE
val test6d: bool = addOpt (NONE, NONE) = NONE

val test7a: bool = addAllOpt [NONE, NONE, NONE] = NONE
val test7b: bool = addAllOpt [SOME 1, NONE, NONE] = SOME 1
val test7c: bool = addAllOpt [NONE, SOME 1, NONE] = SOME 1
val test7d: bool = addAllOpt [NONE, NONE, SOME 1] = SOME 1
val test7e: bool = addAllOpt [SOME 1, NONE, SOME 2] = SOME 3
val test7f: bool = addAllOpt [SOME 1, SOME 2, SOME 3] = SOME 6
val test7g: bool = addAllOpt [] = NONE

val test8a: bool = any [] = false
val test8b: bool = any [false] = false
val test8c: bool = any [true] = true
val test8d: bool = any [true, false] = true
val test8e: bool = any [false, true] = true
val test8f: bool = any [false, false, false] = false

val test9a: bool = all [] = true
val test9b: bool = all [false] = false
val test9c: bool = all [true] = true
val test9d: bool = all [true, false] = false
val test9e: bool = all [false, true] = false
val test9f: bool = all [true, true, true] = true
val test9g: bool = all [true, false, true] = false

val test10a: bool = zip ([], []) = []
val test10b: bool = zip ([1], []) = []
val test10c: bool = zip ([], [1]) = []
val test10d: bool = zip ([1, 2, 3], [4, 6]) = [(1, 4), (2, 6)]
val test10e: bool = zip ([1, 2], [4, 6, 3]) = [(1, 4), (2, 6)]

val test11a: bool = zipRecycle ([], []) = []
val test11b: bool = zipRecycle ([1], []) = []
val test11c: bool = zipRecycle ([], [1]) = []
val test11d: bool = zipRecycle ([1, 2, 3], [1, 2, 3, 4, 5, 6, 7])
    = [(1, 1), (2, 2), (3, 3), (1, 4), (2, 5), (3, 6), (1, 7)]
val test11e: bool = zipRecycle ([1, 2, 3, 4, 5, 6, 7], [1, 2, 3])
    = [(1, 1), (2, 2), (3, 3), (4, 1), (5, 2), (6, 3), (7, 1)]

val test12a: bool = zipOpt ([], []) = SOME []
val test12b: bool = zipOpt ([1], []) = NONE
val test12c: bool = zipOpt ([], [1]) = NONE
val test12d: bool = zipOpt ([1], [2]) = SOME [(1, 2)]
val test12e: bool = zipOpt ([1], [2, 3]) = NONE
val test12f: bool = zipOpt ([1, 2], [3, 4]) = SOME [(1, 3), (2, 4)]

val test13a: bool = lookup ([], "a") = NONE
val test13b: bool = lookup ([("a", 1)], "b") = NONE
val test13c: bool = lookup ([("a", 1)], "a") = SOME 1
val test13d: bool = lookup ([("a", 1), ("b", 2)], "b") = SOME 2
val test13e: bool = lookup ([("a", 1), ("b", 2), ("c", 3)], "d") = NONE

val test14a: bool = splitup [] = ([], [])
val test14b: bool = splitup [1, 2, 3] = ([1, 2, 3], [])
val test14c: bool = splitup [~1, ~2, ~3] = ([], [~1, ~2, ~3])
val test14d: bool = splitup [1, ~1, 2, ~2, 3, ~3, 0]
    = ([1, 2, 3, 0], [~1, ~2, ~3])

val test15a: bool = splitAt ([], 10) = ([], [])
val test15b: bool = splitAt ([1, 2, 3], 0) = ([1, 2, 3], [])
val test15c: bool = splitAt ([1, 2, 3], 1) = ([1, 2, 3], [])
val test15d: bool = splitAt ([1, 2, 3], 2) = ([2, 3], [1])
val test15e: bool = splitAt ([1, 2, 3], 3) = ([3], [1, 2])
val test15f: bool = splitAt ([1, 2, 3], 4) = ([], [1, 2, 3])
val test15g: bool = splitAt ([~1, ~2, ~3], 0) = ([], [~1, ~2, ~3])
val test15h: bool = splitAt ([1, ~1, 2, ~2, 3, ~3, 0], 0)
    = ([1, 2, 3, 0], [~1, ~2, ~3])
val test15i: bool = splitAt ([1, ~1, 2, ~2, 3, ~3, 0], 2)
    = ([2, 3], [1, ~1, ~2, ~3, 0])

val test16a: bool = isSorted [] = true
val test16b: bool = isSorted [1] = true
val test16c: bool = isSorted [1, 2] = true
val test16d: bool = isSorted [2, 1] = false
val test16e: bool = isSorted [1, 2, 3] = true
val test16f: bool = isSorted [2, 1, 3] = false
val test16g: bool = isSorted [2, 3, 1] = false
val test16h: bool = isSorted [3, 1, 2] = false
val test16i: bool = isSorted [3, 2, 1] = false

val test17a: bool = isAnySorted [] = true
val test17b: bool = isAnySorted [1] = true
val test17c: bool = isAnySorted [1, 2] = true
val test17d: bool = isAnySorted [2, 1] = true
val test17e: bool = isAnySorted [1, 2, 3] = true
val test17f: bool = isAnySorted [2, 1, 3] = false
val test17g: bool = isAnySorted [2, 3, 1] = false
val test17h: bool = isAnySorted [3, 1, 2] = false
val test17i: bool = isAnySorted [3, 2, 1] = true

val test18a: bool = sortedMerge ([], []) = []
val test18b: bool = sortedMerge ([1, 2, 3], []) = [1, 2, 3]
val test18c: bool = sortedMerge ([], [1, 2, 3]) = [1, 2, 3]
val test18d: bool = sortedMerge ([1, 4, 7], [5, 8, 9]) = [1, 4, 5, 7, 8, 9]
val test18e: bool = sortedMerge ([5, 8, 9], [1, 4, 7]) = [1, 4, 5, 7, 8, 9]

val test19a: bool = qsort [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
    = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
val test19b: bool = qsort [9, 0, 8, 1, 7, 2, 6, 3, 5, 4]
    = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

val test20a: bool = divide ([1, 2, 3, 4, 5, 6, 7]) = ([1, 3, 5, 7], [2, 4, 6])
val test20b: bool = divide ([1, 2, 3, 4, 5, 6]) = ([1, 3, 5], [2, 4, 6])
val test20c: bool = divide [] = ([], [])
val test20d: bool = divide [1] = ([1], [])
val test20e: bool = divide [1, 2] = ([1], [2])
val test20f: bool = divide [1, 2, 3] = ([1, 3], [2])

val test21a: bool = not_so_quick_sort [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
    = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
val test21b: bool = not_so_quick_sort [9, 0, 8, 1, 7, 2, 6, 3, 5, 4]
    = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
val test21c: bool = not_so_quick_sort [] = []
val test21d: bool = not_so_quick_sort [1] = [1]
val test21e: bool = not_so_quick_sort [2, 1] = [1, 2]
val test21f: bool = not_so_quick_sort [2, 3, 1] = [1, 2, 3]

val test22a: bool = fullDivide (2, 40) = (3, 5)
val test22b: bool = fullDivide (3, 10) = (0, 10)
val test22c: bool = fullDivide (3, 3) = (1, 1)
val test22d: bool = fullDivide (3, 9) = (2, 1)
val test22e: bool = fullDivide (3, 27) = (3, 1)
val test22f: bool = fullDivide (2, 1048576) = (20, 1)
val test22g: bool = fullDivide (2, 2*3*5*7*11) = (1, 3*5*7*11)
val test22h: bool = fullDivide (3, 2*3*5*7*11) = (1, 2*5*7*11)
val test22i: bool = fullDivide (5, 2*3*5*7*11) = (1, 2*3*7*11)

val test23a: bool = factorize 20 = [(2, 2), (5, 1)]
val test23b: bool = factorize 36 = [(2, 2), (3, 2)]
val test23c: bool = factorize 1 = []
val test23d: bool = factorize (2*3*5*7*11)
    = [(2, 1), (3, 1), (5, 1), (7, 1), (11, 1)]
val test23e: bool = factorize 1048576 = [(2, 20)]
val test23f: bool = factorize (2 * 997) = [(2, 1), (997, 1)]
val test23g: bool = factorize 27644437 = [(27644437, 1)]
val test23h: bool = factorize (2*2*3*3*3*5*5*5*5*5) = [(2, 2), (3, 3), (5, 5)]

val test24a: bool = multiply [] = 1
val test24b: bool = multiply [(2, 2), (5, 1)] = 20
val test24c: bool = multiply [(2, 2), (3, 2)] = 36
val test24d: bool = multiply [(2, 1), (3, 1), (5, 1), (7, 1), (11, 1)]
    = 2*3*5*7*11
val test24e: bool = multiply [(2, 20)] = 1048576
val test24f: bool = multiply [(2, 2), (3, 3), (5, 5)] = 2*2*3*3*3*5*5*5*5*5
