(* COMMUNITY PROBLEMS FOR WEEK 2
   solution credit goes to: spamegg
    this is community.sml
*)

(* 1.
Write a function
    alternate : int list -> int
that takes a list of numbers and adds them with alternating sign. For example
    alternate [1,2,3,4] = 1 - 2 + 3 - 4 = -2
*)
fun alternate_helper (numbers: int list, positive: bool): int =
    if null numbers then 0
    else if positive then (hd numbers) + alternate_helper (tl numbers, false)
    else alternate_helper(tl numbers, true) - (hd numbers)

fun alternate (numbers: int list): int =
    alternate_helper(numbers, true)

(* 2.
Write a function
    min_max : int list -> int * int
that takes a non-empty list of numbers, and returns a pair
    (min, max)
of the minimum and maximum of the numbers in the list.
*)
fun min_max_helper (numbers: int list, min_so_far: int, max_so_far: int): int * int =
    if null numbers then (min_so_far, max_so_far)
    else if hd numbers < min_so_far
    then min_max_helper(tl numbers, hd numbers, max_so_far)
    else if hd numbers > max_so_far
    then min_max_helper(tl numbers, min_so_far, hd numbers)
    else min_max_helper(tl numbers, min_so_far, max_so_far)

fun min_max (numbers: int list): int * int =
    min_max_helper(tl numbers, hd numbers, hd numbers)

(* 3.
Write a function
    cumsum : int list -> int list
that takes a list of numbers and returns a list
of the partial sums of those numbers. For example
    cumsum [1,4,20] = [1,5,25]
*)
fun cumsum (numbers: int list): int list =
    if null numbers then []
    else if null (tl numbers) then numbers
    else (hd numbers) :: cumsum (((hd numbers) + (hd (tl numbers))) :: tl (tl numbers))

(* 4.
Write a function
    greeting : string option -> string
that given a string  option SOME name returns the string
    "Hello there, ...!"
where the dots would be replaced by name.
Note that the name is given as an option,
so if it is NONE then replace the dots with "you".
*)
fun greeting (name: string option): string =
    if isSome name then "Hello there, " ^ (valOf name) ^ "!"
    else "Hello there, you!"

(* 5.
Write a function
    repeat : int list * int list -> int list
that given a list of integers and another list of nonnegative integers,
repeats the integers in the first list according to the numbers
indicated by the second list. For example:
    repeat ([1, 2, 3], [4, 0, 3]) = [1, 1, 1, 1, 3, 3, 3]
*)
fun repeat_helper (number: int, count: int): int list =
    if count = 0 then []
    else number :: repeat_helper (number, count - 1)

fun repeat (numbers: int list, counts: int list): int list =
    if null numbers then []
    else repeat_helper (hd numbers, hd counts) @ repeat (tl numbers, tl counts)

(* 6.
Write a function
    addOpt  : int option * int option -> int option
that given two "optional" integers, adds them if they are both present
(returning SOME of their sum),
or returns NONE if at least one of the two arguments is NONE.
*)
fun addOpt (int1: int option, int2: int option): int option =
    if (isSome int1) andalso (isSome int2)
    then SOME (valOf int1 + valOf int2)
    else NONE

(* 7.
Write a function
    addAllOpt  : int option list -> int option
that given a list of "optional" integers, adds those integers
that are there (i.e. adds all the SOME i). For example:
    addAllOpt ([SOME 1, NONE, SOME 3]) = SOME 4.
If the list does not contain any SOME i is in it, i.e. they are
all NONE or the list is empty, the function should return NONE.
*)
fun addAllOpt_helper(numbers: int option list): int =
    if null numbers then 0
    else if isSome (hd numbers) then addAllOpt_helper(tl numbers) + (valOf (hd numbers))
    else addAllOpt_helper(tl numbers)

fun addAllOpt (numbers: int option list): int option =
    let val total = addAllOpt_helper(numbers)
    in if total = 0 then NONE else SOME total end

(* 8.
Write a function
    any : bool list -> bool
that given a list of booleans
returns true if there is at least one of them that is true
otherwise returns false.
(If the list is empty it should return false because there is no true.)
*)
fun any (bools: bool list): bool =
    if null bools then false
    else (hd bools) orelse any (tl bools)

(* 9.
Write a function
    all : bool list -> bool
that given a list of booleans returns true if all of them true,
otherwise returns false.
(If the list is empty it should return true because there is no false.)
*)
fun all (bools: bool list): bool =
    if null bools then true
    else (hd bools) andalso all (tl bools)

(* 10.
Write a function
    zip : int list * int list -> int * int
that given two lists of integers creates consecutive pairs,
and stops when one of the lists is empty. For example:
    zip ([1, 2, 3], [4, 6]) = [(1, 4), (2, 6)].
*)
fun zip (nums1: int list, nums2: int list): (int * int) list =
    if null nums1 orelse null nums2 then []
    else (hd nums1, hd nums2) :: zip(tl nums1, tl nums2)

(* 11.
Challenge: Write a version
    zipRecycle
where when one list is empty it starts recycling from its start
until the other list completes. For example:
    zipRecycle ([1, 2, 3], [1, 2, 3, 4, 5, 6, 7])
    = [(1, 1), (2, 2), (3, 3), (1, 4), (2, 5), (3, 6), (1, 7)]
*)
(* This helper removes elements from the left of a list:
    head_remover ([1, 2, 3, 4, 5, 6, 7], 3) = [4, 5, 6, 7]
If the number of elements to be removed is greater than the length of the list,
it returns the empty list:
    head_remover([7], 3) = []
*)
fun head_remover (numbers: int list, size: int): int list =
    if size > (length numbers) then []
    else if size = 0 then numbers
    else head_remover(tl numbers, size - 1)

(* We call this helper assuming that length nums1 <= length nums2 *)
fun zipRecycle_helper (nums1: int list, nums2: int list, order: bool): (int * int) list =
    if (null nums2) then []
    else
    let val zipped = if order then zip(nums1, nums2) else zip(nums2, nums1) in
        zipped @ zipRecycle_helper(nums1, head_remover(nums2, (length nums1)), order)
    end

(* if length nums1 >= length nums2
then we switch their places and the order in which pairs get zipped *)
fun zipRecycle (nums1: int list, nums2: int list): (int * int) list =
    if (null nums1) orelse (null nums2) then []
    else if (length nums1) < (length nums2)
    then zipRecycle_helper(nums1, nums2, true)
    else zipRecycle_helper(nums2, nums1, false)

(* 12.
Lesser challenge: Write a version
    zipOpt
of zip with return type (int * int) list option.
This version should return SOME of a list
when the original lists have the same length,
and NONE if they do not.
*)
fun zipOpt (nums1: int list, nums2: int list): (int * int) list option =
    if length nums1 = length nums2 then SOME(zip(nums1, nums2))
    else NONE

(* 13.
Write a function
    lookup : (string * int) list * string -> int option
that takes a list of pairs (s, i) and also a string s2 to look up.
It then goes through the list of pairs looking for the string s2 in the first component.
If it finds a match with corresponding number i, then it returns SOME i.
If it does not, it returns NONE.
*)
fun lookup (str_int_list: (string * int) list, s2: string): int option =
    if null str_int_list then NONE
    else if #1 (hd str_int_list) = s2 then SOME (#2 (hd str_int_list))
    else lookup ((tl str_int_list), s2)

(* 14.
Write a function
    splitup : int list -> int list * int list
that given a list of integers creates two lists of integers,
one containing the non-negative entries, the other containing the negative entries.
Relative order must be preserved: All non-negative entries must appear in the same
order in which they were on the original list, and similarly for the negative entries.
*)
fun splitup (numbers: int list): int list * int list =
    if null numbers then ([], [])
    else
    let val list_pair = splitup (tl numbers)
        val pos = #1 list_pair
        val neg = #2 list_pair
    in
        if (hd numbers) < 0 then (pos, (hd numbers) :: neg)
        else ((hd numbers) :: pos, neg)
    end

(* 15.
Write a version
    splitAt : int list * int -> int list * int list
of the previous function that takes an extra "threshold" parameter,
and uses that instead of 0 as the separating point for the two resulting lists.
*)
fun splitAt (numbers: int list, threshold: int): int list * int list =
    if null numbers then ([], [])
    else
    let val list_pair = splitAt ((tl numbers), threshold)
        val bigger = #1 list_pair
        val smaller = #2 list_pair
    in
        if (hd numbers) < threshold then (bigger, (hd numbers) :: smaller)
        else ((hd numbers) :: bigger, smaller)
    end

(* 16.
Write a function
    isSorted : int list -> boolean
that given a list of integers determines whether the list is sorted in increasing order.
*)
fun isSorted (numbers: int list): bool =
    if null numbers orelse null (tl numbers) then true
    else (hd numbers) < (hd (tl numbers)) andalso isSorted (tl numbers)

(* 17.
Write a function
    isAnySorted : int list -> boolean,
that given a list of integers determines whether the list
is sorted in either increasing or decreasing order.
*)
fun isAnySorted (numbers: int list): bool =
    isSorted numbers orelse isSorted (rev numbers)

(* 18.
Write a function
    sortedMerge : int list * int list -> int list
that takes two lists of integers that are each sorted from smallest to largest,
and merges them into one sorted list. For example:
    sortedMerge ([1, 4, 7], [5, 8, 9]) = [1, 4, 5, 7, 8, 9]
*)
fun sortedMerge (nums1: int list, nums2: int list): int list =
    if null nums1 orelse null nums2 then nums1 @ nums2
    else if (hd nums1) < (hd nums2) then (hd nums1) :: sortedMerge(tl nums1, nums2)
    else (hd nums2) :: sortedMerge(nums1, tl nums2)

(* 19.
Write a sorting function
    qsort : int list -> int list
that works as follows:
Takes the first element out, and uses it as the "threshold" for splitAt.
It then recursively sorts the two lists produced by splitAt.
Finally it brings the two lists together.
(Don't forget that element you took out, it needs to get back in at some point).
You could use sortedMerge for the "bring together" part,
but you do not need to as all the numbers in one list
are less than all the numbers in the other.)
*)
fun qsort (numbers: int list): int list =
    if null numbers then []
    else
    let val lists = splitAt(tl numbers, hd numbers)
        val bigger = #1 lists
        val smaller = #2 lists
    in
        qsort smaller @ [hd numbers] @ qsort bigger
        (*sortedMerge(qsort smaller, (hd numbers) :: qsort bigger)   <- this also works*)
    end


(* 20.
Write a function
    divide : int list -> int list * int list
that takes a list of integers and produces two lists by
alternating elements between the two lists. For example:
    divide ([1, 2, 3, 4, 5, 6, 7]) = ([1, 3, 5, 7], [2, 4, 6])
*)
fun divide (numbers: int list): int list * int list =
    if null numbers then ([], [])
    else if null (tl numbers) then (numbers, [])
    else
    let
        val lists = divide(tl (tl numbers))
        val firsts = #1 lists
        val seconds = #2 lists
    in
        ((hd numbers) :: firsts, (hd (tl numbers)) :: seconds)
    end

(* 21.
Write another sorting function
    not_so_quick_sort : int list -> int list
that works as follows:
Given the initial list of integers, splits it in two lists using divide,
then recursively sorts those two lists,
then merges them together with sortedMerge.
*)
fun not_so_quick_sort (numbers: int list): int list =
    if null numbers then []
    else if null (tl numbers) then numbers
    else
    let
        val lists = divide(numbers)
        val firsts = #1 lists
        val seconds = #2 lists
    in
        sortedMerge(not_so_quick_sort firsts, not_so_quick_sort seconds)
    end


(* 22.
Write a function
    fullDivide : int * int -> int * int that given two numbers k and n
it attempts to evenly divide k into n as many times as possible,
and returns a pair (d, n2) where d is the number of times
while n2 is the resulting n after all those divisions.
Examples:
    fullDivide (2, 40) = (3, 5) because 2*2*2*5 = 40 and
    fullDivide (3, 10) = (0, 10) because 3 does not divide 10.
*)
fun fullDivide (pair: int * int): int * int =
    if (#2 pair) mod (#1 pair) <> 0 then (0, #2 pair)
    else
    let
        val quotient = (#2 pair) div (#1 pair)
        val rest = fullDivide (#1 pair, quotient)
        val power = #1 rest
        val divisor = #2 rest
    in
        (1 + power, divisor)
    end

(* 23.
Using fullDivide, write a function
    factorize : int -> (int * int) list
that given a number n returns a list of pairs (d, k)
where d is a prime number dividing n and k is the number of times it fits.
The pairs should be in increasing order of prime factor,
and the process should stop when the divisor considered surpasses the square root of n.
If you make sure to use the reduced number n2 given by fullDivide for each next step,
you should not need to test if the divisors are prime:
If a number divides into n, it must be prime (if it had prime factors,
they would have been earlier prime factors of n and thus reduced earlier).
Examples:
    factorize(20) = [(2, 2), (5, 1)]
    factorize(36) = [(2, 2), (3, 2)]
    factorize(1) = []
*)
fun factorize_helper(current: int, divisor: int): (int * int) list =
    let
        val real_div = Real.fromInt divisor
        val real_cur = Real.fromInt current
    in
        (* if we found no divisors up to sqrt of current, then it's prime!
           This is computationally advantageous only for really large primes *)
        if real_div > Math.sqrt real_cur then [(current, 1)]
        else
        let
            val factors = fullDivide (divisor, current)
            val quotient = #2 factors
            val power = #1 factors
        in
            if quotient = 1 then [(divisor, power)]
            else if power = 0 then factorize_helper (quotient, divisor + 1)
            else (divisor, power) :: factorize_helper (quotient, divisor + 1)
        end
    end

fun factorize (number: int): (int * int) list =
    if number = 1 then []
    else factorize_helper (number, 2)

(* 24.
Write a function
    multiply : (int * int) list -> int
that given a factorization of a number n as described
in the previous problem computes back the number n.
So this should do the opposite of factorize.
*)
fun multiply_helper (pair: int * int): int =
    if #2 pair = 0 then 1
    else (#1 pair) * multiply_helper (#1 pair, #2 pair - 1)

fun multiply (pairlist: (int * int) list): int =
    if null pairlist then 1
    else multiply_helper (hd pairlist) * multiply (tl pairlist)

(* 25.
Challenge (hard): Write a function
    all_products : (int * int) list -> int list
that given a factorization list result from factorize creates a list of all
possible products produced from using some or all of those prime factors
no more than the number of times they are available.
This should end up being a list of all the divisors of
the number n that gave rise to the list. Example:
    all_products([(2, 2), (5, 1)]) = [1, 2, 4, 5, 10, 20]
For extra challenge, your recursive process should return the numbers in this order,
as opposed to sorting them afterwards.
*)
fun all_products (pairlist: (int * int) list): int list = []
(* DO THIS YOURSELF! I AM TIRED. *)
