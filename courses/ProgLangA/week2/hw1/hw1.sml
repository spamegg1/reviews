(*  date is (int*int*int): year, month, day  *)

(*  date1, date2 -> bool  *)
(*  returns true if date1 is older than date2  *)
fun is_older (date1: (int * int * int), date2: (int * int * int)) =
    (#1 date1) < (#1 date2)
         orelse ((#1 date1) = (#1 date2) andalso (#2 date1) < (#2 date2))
         orelse ((#1 date1) = (#1 date2) andalso (#2 date1) = (#2 date2)
             andalso (#3 date1) < (#3 date2))

(*  date list, int -> int  *)
(*  returns how many dates in list are in given month  *)
fun number_in_month (datelist: (int * int * int) list, month: int) =
    if null datelist
    then 0
    else
    let
        val x = number_in_month((tl datelist), month)
        in
        if month = (#2 (hd datelist)) then x + 1 else x
    end

(*  date list, int list -> int  *)
(*  returns how many dates in date list are in any of the months in months list *)
(*  Assumes that int list has no numbers repeated  *)
fun number_in_months (datelist: (int * int * int) list, monthlist: int list) =
    if null monthlist
    then 0
    else
    number_in_months(datelist, (tl monthlist)) + number_in_month(datelist, (hd monthlist))

(*  date list, int -> date list  *)
(*  returns list of dates that are in given month  *)
fun dates_in_month (datelist: (int * int * int) list, month: int) =
    if null datelist
    then []
    else
    let
        val x = dates_in_month((tl datelist), month)
    in
        if (#2 (hd datelist)) = month then (hd datelist)::x else x
    end

(*  date list, int list -> date list  *)
(*  returns list of dates that are in any of the months in the list of months  *)
(*  Assumes that int list has no numbers repeated  *)
fun dates_in_months (datelist: (int*int*int) list, monthlist: int list) =
    if null monthlist
    then []
    else
    dates_in_month(datelist, (hd monthlist)) @ dates_in_months(datelist, (tl monthlist))

(*  string list, int -> string  *)
(*  returns nth string in list  *)
(*  Assumes list is nonempty and n >= 1  *)
fun get_nth(strlist: string list, n: int) =
    if n = 1
    then hd strlist
    else get_nth((tl strlist), n - 1)

(*  date -> string  *)
(*  returns string of the form January 20, 2013 (for example)  *)
fun date_to_string (date: (int*int*int)) =
    let
    val months = ["January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"]
    in
        get_nth(months, (#2 date)) ^ " " ^ Int.toString(#3 date) ^ ", " ^ Int.toString(#1 date)
    end

(*  int, int list -> int  *)
(*  returns first n such that sum of first n elts of list < sum, *)
(*  but of first n + 1 elts >= sum  *)
(*  Assumes sum > 0, list has positive numbers, *)
(*  and entire list sums to more than sum   *)
fun number_before_reaching_sum(sum: int, poslist: int list) =
    if sum <= (hd poslist)
    then 0
    else 1 + number_before_reaching_sum(sum - (hd poslist), (tl poslist))

(*  int -> int  *)
(*  returns what month given day[1, 365] is in  *)
fun what_month(day: int) =
    let
        val days_in_months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in
        number_before_reaching_sum(day, days_in_months) + 1
    end

(*  int, int -> int list  *)
(*  returns list of months of all days in between given two days  *)
fun month_range(day1: int, day2: int) =
    if day1 > day2
    then []
    else what_month(day1) :: month_range(day1 + 1, day2)

(*  date list -> date option  *)
(*  returns oldest date in list, NONE if list empty  *)
fun oldest(datelist: (int * int * int) list) =
    if null datelist
    then NONE
    else
    let
        val tl_ans = oldest(tl datelist)
    in
        if not(isSome(tl_ans)) orelse is_older((hd datelist), valOf(tl_ans))
        then SOME (hd datelist)
        else tl_ans
    end
