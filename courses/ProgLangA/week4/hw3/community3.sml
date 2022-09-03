(* COMMUNITY PROBLEMS FOR WEEK 4
    credit goes to: spamegg
    this is community3.sml
*)

(* 1.
Write a function
    compose_opt : ('b -> 'c option) -> ('a -> 'b option) -> 'a -> 'c option
that composes two functions with "optional" values.
If either function returns NONE, then the result is NONE. *)
fun compose_opt (f: 'b -> 'c option) (g: 'a -> 'b option) (x: 'a) : 'c option =
    case g x of NONE => NONE | SOME y => f y

(* 2.
Write a function
    do_until : ('a -> 'a) -> ('a -> bool) -> 'a -> 'a
    do_until f p x
will apply f to x and f again to that result and so on until p x is false.
Example:
    do_until (fn x => x div 2) (fn x => x mod 2 <> 1)
will evaluate to a function of type int->int that divides its argument by 2 until it reaches an odd number.
In effect, it will remove all factors of 2 from its argument.
*)
fun do_until (f: 'a -> 'a) (pred: 'a -> bool) (x: 'a) : 'a =
    if pred x
    then do_until f pred (f x)
    else x

(* 3.
Use do_until to implement factorial.
*)
fun factorial1 (n: int): int =
    let
        fun nonzero(x, _) = x <> 0
        fun multiply(count, product) = (count - 1, count * product)
    in
        #2 (do_until multiply nonzero (n, 1))
    end

(* 4.
Use do_until to write a function
    fixed_point: (''a -> ''a) -> ''a -> ''a
that given a function f and an initial value x applies f to x until f x = x.
(Notice the use of '' to indicate equality types.)
*)
fun fixed_point (f: ''a -> ''a) (init: ''a) : ''a =
    do_until f (fn x => (f x) <> x) init

(* 5.
Write a function
    map2 : ('a -> 'b) -> 'a * 'a -> 'b * 'b
that given a function that takes 'a values to 'b values and a pair of 'a values
returns the corresponding pair of 'b values.
*)
fun map2 (f: 'a -> 'b) ((x, y): 'a * 'a) : 'b * 'b = (f x, f y)

(* 6.
Write a function
    app_all : ('b -> 'c list) -> ('a -> 'b list) -> 'a -> 'c list
so that:
    app_all f g x will apply f to every element of the list g x
    and concatenate the results into a single list.
For example, for fun f n = [n, 2 * n, 3 * n], we have
    app_all f f 1 = [1, 2, 3, 2, 4, 6, 3, 6, 9]
*)
fun app_all (f: 'b -> 'c list) (g: 'a -> 'b list)  (x: 'a) : 'c list =
    let fun helper _ [] = []
        |   helper func (l :: ls) = (func l) @ helper func ls
    in helper f (g x) end

(* 7.
Implement List.foldr (see http://sml-family.org/Basis/list.html#SIG:LIST.foldr:VAL).
Its type is ('a * 'b -> 'b) -> 'b -> 'a list -> 'b
    foldr f init [x1, x2, ..., xn] returns
    f(x1, f(x2, ..., f(xn, init)...))
    or init if the list is empty.
*)
fun myfoldr (f: 'a * 'b -> 'b) (init: 'b) (lst: 'a list) : 'b =
    case lst of
    [] => init
    | x :: xs => f (x, (myfoldr f init xs))

(* 8.
Write a function
    partition : ('a -> bool) -> 'a list -> 'a list * 'a list
where the first part of the result contains the second argument elements for which the first
element evaluates to true and the second part of the result contains the other second argument elements.
Traverse the second argument only once.
*)
fun partition (f: 'a -> bool) (lst: 'a list) : ('a list * 'a list) =
    case lst of
    [] => ([], [])
    | x :: xs =>
        let val (first, second) = partition f xs
        in
            if (f x)
            then (x :: first, second)
            else (first, x :: second)
        end

(* 9.
Write a function
    unfold : ('a -> ('b * 'a) option) -> 'a -> 'b list
that produces a list of 'b values, given:
a "seed" of type 'a, and
a function that given a seed
    produces SOME of a pair of a 'b value and a new seed, or
    NONE if it is done seeding.
For example, here is an elaborate way to count down from 5:
    unfold (fn n => if n = 0 then NONE else SOME(n, n-1)) 5 = [5, 4, 3, 2, 1]
*)
fun unfold (f: 'a -> ('b * 'a) option) (seed: 'a) : 'b list =
    case (f seed) of
      NONE => []
    | SOME (bval, newseed) => bval :: (unfold f newseed)

(* 10.
Use unfold and foldl to implement factorial.
*)
fun factorial2 (n: int) : int =
    let val countdown = fn n => if n = 0 then NONE else SOME (n, n-1)
        val mult = fn (x, y) => x * y
    in foldr mult 1 (unfold countdown n) end

(* 11.
Implement map using List.foldr.
*)
fun mymap (f: 'a -> 'b) (lst: 'a list) : 'b list =
    foldr (fn (a, alist) => (f a) :: alist) [] lst

(* 12.
Implement filter using List.foldr.
*)
fun myfilter (pred: 'a -> bool) (lst: 'a list) : 'a list =
    let fun binOp (a, alist) =
            if (pred a)
            then (a :: alist)
            else alist
    in foldr binOp [] lst end

(* 13.
Implement foldl using foldr on functions. (This is challenging.)
spamegg's note: one way is to simply reverse the list and use foldr,
but I'm not sure if this is the solution they intended.
   foldl f init [x1, x2, ..., xn]
    returns
    f(xn,...,f(x2, f(x1, init))...)
    or init if the list is empty.
*)
fun myfoldl (f: 'a * 'b -> 'b) (init: 'b) (lst: 'a list) : 'b =
    foldr f init (rev lst)

fun myfoldl2 (f: 'a * 'b -> 'b) (init: 'b) (lst: 'a list) : 'b =
    case lst of
      [] => init
    | x :: xs => myfoldl2 f (f(x, init)) xs

(* 14. this solution is taken from Forums, not by me!
        I just added function signatures. - spamegg
Define a (polymorphic) type for binary trees where data is at internal nodes but not at leaves.
Define map and fold functions over such trees.
You can define filter as well where we interpret a "false" as meaning:
the entire subtree rooted at the node with data that produced false should be replaced with a leaf.
*)
datatype 'a tree = leaf | node of { value : 'a, left : 'a tree, right : 'a tree }

(* Apply function f to the internal nodes only, and recurse *)
fun tree_map (f: 'a -> 'a) (tree: 'a tree) : 'a tree = case tree of
      leaf => leaf
    | node {value= v, left= l, right= r} =>
        node {value=f v, left= tree_map f l, right= tree_map f r}

fun tree_filter (f: 'a -> bool) (tree: 'a tree) : 'a tree = case tree of
      leaf => leaf
    | node {value= v, left= l, right= r} =>
        if f v
        then node {value= v, left= tree_filter f l, right= tree_filter f r}
        else leaf

fun tree_fold (f: 'a * 'a * 'a -> 'a) (acc: 'a) (tree: 'a tree) : 'a =
    case tree of
      leaf => acc
    | node {value= v, left= l, right= r} =>
        f (tree_fold f acc l, v, tree_fold f acc r)
