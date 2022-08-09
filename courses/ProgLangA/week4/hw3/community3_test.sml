(* this is community3_test.sml
    credit goes to: spamegg *)
use "community3.sml";

val test1a: bool = compose_opt (fn x => NONE) (fn y => SOME y) 5 = NONE
val test1b: bool = compose_opt (fn x => SOME x) (fn y => NONE) 5 = NONE
val test1c: bool = compose_opt (fn x => SOME (x * x)) (fn y => SOME(y * 2)) 5
    = SOME 100

val test2a: bool = do_until (fn x => x div 2) (fn x => x mod 2 <> 1) 1048576 = 1
val test2b: bool = do_until (fn x => x div 2) (fn x => x mod 2 <> 1) 6 = 3
val test2c: bool = do_until (fn x => x div 2) (fn x => x mod 2 <> 1) 10 = 5
val test2d: bool = do_until (fn (x, acc) => (x - 1, x * acc))
    (fn (x, acc) => x <> 0) (6, 1) = (0, 720)

val test3a: bool = factorial1 1 = 1
val test3b: bool = factorial1 2 = 2
val test3c: bool = factorial1 3 = 6
val test3d: bool = factorial1 4 = 24
val test3e: bool = factorial1 8 = 40320

val test4a: bool = fixed_point (fn x => if x > 1 then x div 2 else x) 1024 = 1
val test4b: bool = fixed_point (fn x => if x < 1024 then x * 2 else x) 1 = 1024

val test5a: bool = map2 (fn x => x * x) (1, 2) = (1, 4)
val test5b: bool = map2 (fn x => [1, x, x * x]) (1, 2) = ([1, 1, 1], [1, 2, 4])
val test5c: bool = map2 (fn x => if isSome x then valOf x else 0) (SOME 1, NONE)
    = (1, 0)

fun f n = [n, 2 * n, 3 * n]
fun g n = [n * n, 2 * n * n]
val test6a: bool = app_all f f 1 = [1, 2, 3, 2, 4, 6, 3, 6, 9]
val test6b: bool = app_all f f 2 = [2, 4, 6, 4, 8, 12, 6, 12, 18]
val test6c: bool = app_all f g 1 = [1, 2, 3, 2, 4, 6]
val test6d: bool = app_all f g 2 = [4, 8, 12, 8, 16, 24]

val test7a: bool = true
val test7b: bool = true
val test7c: bool = true

val test8a: bool = partition (fn n => n mod 2 = 0) [1, 2, 3, 4, 5, 6]
    = ([2, 4, 6], [1, 3, 5])
val test8b: bool = partition (fn n => n mod 3 = 0) [1, 2, 3, 4, 5, 6]
    = ([3, 6], [1, 2, 4, 5])

val test9a: bool = unfold (fn n => if n = 0 then NONE else SOME(n, n-1)) 5
    = [5, 4, 3, 2, 1]

val test10a: bool = factorial2 1 = 1
val test10b: bool = factorial2 2 = 2
val test10c: bool = factorial2 3 = 6
val test10d: bool = factorial2 4 = 24
val test10e: bool = factorial2 8 = 40320

val test11a: bool = mymap (fn n => n * n) [1, 2, 3] = [1, 4, 9]
val test11b: bool = mymap (fn x => [1, x, x*x]) [1, 2] = [[1, 1, 1], [1, 2, 4]]

val test12a: bool = myfilter (fn n => n mod 2 = 0) [1, 2, 3, 4, 5, 6] = [2, 4, 6]
val test12b: bool = myfilter (fn n => n mod 2 <> 0) [1, 2, 3, 4, 5, 6] = [1, 3, 5]
val test12c: bool = myfilter (fn n => n > 0) [1, ~2, ~3, 4, 5, ~6] = [1, 4, 5]

val test13a: bool = myfoldl (fn (x, y) => x + y) 0 [1, 2, 3, 4, 5, 6] = 21
val test13b: bool = myfoldl (fn (x, y) => x - y) 0 [1, 2, 3, 4, 5, 6] = 3
val test13c: bool = myfoldl2 (fn (x, y) => x + y) 0 [1, 2, 3, 4, 5, 6] = 21
val test13d: bool = myfoldl2 (fn (x, y) => x - y) 0 [1, 2, 3, 4, 5, 6] = 3
val test13e: bool = myfoldr (fn (x, y) => x + y) 0 [1, 2, 3, 4, 5, 6] = 21
val test13f: bool = myfoldr (fn (x, y) => x - y) 0 [1, 2, 3, 4, 5, 6] = ~3


val bottomtree4: int tree = node {value=4, left=leaf, right=leaf}
val bottomtree5: int tree = node {value=5, left=leaf, right=leaf}
val bottomtree6: int tree = node {value=6, left=leaf, right=leaf}
val bottomtree7: int tree = node {value=7, left=leaf, right=leaf}
val bottomtree16: int tree = node {value=16, left=leaf, right=leaf}
val bottomtree25: int tree = node {value=25, left=leaf, right=leaf}
val bottomtree36: int tree = node {value=36, left=leaf, right=leaf}
val bottomtree49: int tree = node {value=49, left=leaf, right=leaf}
val midtree2: int tree = node {value=2, left=bottomtree4, right=bottomtree5}
val midtree3: int tree = node {value=3, left=bottomtree6, right=bottomtree7}
val midtree3filtered: int tree = node {value=3, left=leaf, right=bottomtree7}
val midtree4: int tree = node {value=4, left=bottomtree16, right=bottomtree25}
val midtree9: int tree = node {value=9, left=bottomtree36, right=bottomtree49}
val toptree: int tree = node {value=1, left=midtree2, right=midtree3}
fun treeadder (x, y, z) = x + y + z
fun treemult (x, y, z) = x * y * z

val test14a: bool = tree_map (fn x => x*x) bottomtree4 = bottomtree16
val test14b: bool = tree_map (fn x => x*x) midtree2
    = node {value=4, left=bottomtree16, right=bottomtree25}
val test14c: bool = tree_map (fn x => x*x) toptree
    = node {value= 1, left=midtree4, right=midtree9}
val test14d: bool = tree_filter (fn n => n mod 2 <> 0) bottomtree4 = leaf
val test14e: bool = tree_filter (fn n => n mod 2 <> 0) midtree3 = midtree3filtered
val test14f: bool = tree_filter (fn n => n mod 2 <> 0) toptree
    = node {value=1, left=leaf, right=midtree3filtered}
val test14g: bool = tree_fold treeadder 0 toptree = 28
val test14h: bool = tree_fold treemult 1 toptree = 5040
