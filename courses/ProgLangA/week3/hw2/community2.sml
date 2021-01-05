(* COMMUNITY PROBLEM FOR WEEK 3
   solution credit goes to:  Escanor
   This is community2.sml *)

(* Problem 1 - 4 *)
type student_id = int
type grade = int (* must be in 0 to 100 range *)
type final_grade = { id : student_id, grade : grade option }
datatype pass_fail = pass | fail

fun pass_or_fail final_grade =
    case final_grade of
	{grade = SOME i, id = myId} => (if i >= 75
					then pass
					else fail)
      | {grade = NONE, id = myId} => fail

fun has_passed final_grade =
    case pass_or_fail final_grade of
	pass => true
      | fail => false

fun number_passed final_grade =
    case final_grade of
	[] => 0
      | x::xs' => if has_passed x
		  then 1 + number_passed xs'
		  else number_passed xs'

fun number_misgraded final_list =
    case final_list of
	[] => 0
      | (pf,x)::xs' => if pass_or_fail x = pf
		       then number_misgraded xs'
		       else 1 + number_misgraded xs'

(* PROBLEM 5 - 7 *)
datatype 'a tree = leaf
		 | node of { value : 'a, left : 'a tree, right : 'a tree }
datatype flag = leave_me_alone | prune_me

fun tree_height tr =
    case tr of
	leaf => 0
      | node {value = _, left = l, right = r}
	=> 1 + Int.max(tree_height l, tree_height r)

fun sum_tree tr =
    case tr of
	leaf => 0
      | node {value = i, left = l, right = r} => i + sum_tree l + sum_tree r

fun gardener ftree =
    case ftree of
	leaf => leaf
      | node {value = leave_me_alone, left = l, right = r}
	=> node {value = leave_me_alone, left = gardener l, right = gardener r}
      | node {value = prune_me, left = _, right = _} => leaf

(* PROBLEM 8 skipped *)

(* PROBLEM 9 - 16 *)
datatype nat = ZERO | SUCC of nat

fun is_positive n =
    case n of
	ZERO => false
      | SUCC _ => true

exception Negative

fun pred (n) =
    case n of
	ZERO => raise Negative
     | SUCC m => m

fun nat_to_int n =
    case n of
	ZERO => 0
     | SUCC m => 1 + nat_to_int m

fun int_to_nat i =
    if i < 0
    then raise Negative
    else if i = 0
    then ZERO
    else SUCC (int_to_nat (i - 1))

fun add nat_pair =
    case nat_pair of
	(ZERO, n2) => n2
     | (SUCC m1, n2) => add (m1, SUCC n2)

fun sub nat_pair =
    case nat_pair of
	(n1, ZERO) => n1
      | (SUCC m1, SUCC n1) => sub (m1, n1)
      | _ => raise Negative

fun mult nat_pair =
    case nat_pair of
	(ZERO, n2) => ZERO
      | (n1, ZERO) => ZERO
      | (SUCC m1, n2) => add (n2, mult (m1, n2))

fun less_than nat_pair =
    case nat_pair of
	(ZERO, _) => false
      | (_, ZERO) => true
      | (SUCC m, SUCC n) => less_than (m, n)

(* PROBLEM 17 - 19 *)
datatype intSet =
	 Elems of int list (* list of integards, possibly with duplicated to be ignored *)
	 | Range of { from : int, to : int } (* assume both included *)
	 | Union of intSet * intSet (* Union of the  two sets *)
	 | Intersection of intSet * intSet (* intersection of the two sets *)

(* Helper functions used *)
fun does_include (lst, n) =
    case lst of
	[] => false
      | x::xs' => (x = n) orelse does_include (xs', n)

fun intersection (l1, l2) =
    let fun intersection (l1, rsf) =
	    case (l1, l2) of
		([], _) => rsf
	      | (_, []) => rsf
	      | (x::xs', l2) => if does_include (l2, x)
				then intersection (xs', x::rsf)
				else intersection (xs', rsf)
    in
	intersection (l1, [])
    end

fun union (l1, l2) =
    let fun union (l1, rsf) =
	    case l1 of
		[] => rsf
	      | (x::xs') => if does_include (l2, x)
			    then union (xs', rsf)
			    else union (xs', x::rsf)
    in
	union (l1, l2)
    end

fun range (m, n) =
    if m > n
    then []
    else m::(range (m + 1, n))

fun filter_duplicate lst =
    let fun filter_duplicate (lst, rsf) =
	    case lst of
		[] => rsf
	      | x::xs' => if does_include (rsf, x)
			  then filter_duplicate (xs', rsf)
			  else filter_duplicate (xs', x::rsf)
    in
	filter_duplicate (lst, [])
    end

(* PROBLEM 18 *)
fun contains (mySet, num) =
    case mySet of
	Elems [] => false
      | Elems xs => does_include (xs, num)
      | Range {from = m, to = n} => (m <= num) andalso (n >= num)
      | Union (i1, i2) => contains (i1, num) orelse contains (i2, num)
      | Intersection (i1, i2) => contains (i1, num) andalso contains (i2, num)

(* PROBLEM 19 *)
fun toList mySet =
    case mySet of
	Elems xs => filter_duplicate xs
      | Range {from = m, to = n} => range (m, n)
      | Union (is1, is2) => union (toList is1, toList is2)
      | Intersection (is1, is2) => intersection (toList is1, toList is2)

(* PROBLEM 17 *)
fun isEmpty mySet =
    case mySet of
	Elems [] => true
      | Elems _ => false
      | Range {from = m, to = n} => m > n
      | Union (i1, i2) => isEmpty i1 andalso isEmpty i2
      | Intersection (i1, i2) => if isEmpty i1 orelse isEmpty i2
				 then true
				 else intersection (toList i1, toList i2) = []
