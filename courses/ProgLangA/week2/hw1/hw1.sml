(*  date is (int * int * int): year, month, day  *)
type date = int * int * int

(*  returns true if date1 is older than date2  *)
fun is_older (date1: date, date2: date): bool =
    (#1 date1) < (#1 date2)
    orelse
    ((#1 date1) = (#1 date2) andalso (#2 date1) < (#2 date2))
    orelse
    ((#1 date1) = (#1 date2) andalso
     (#2 date1) = (#2 date2) andalso
     (#3 date1) < (#3 date2))

(*  returns how many dates in list are in given month  *)
fun number_in_month (dates: date list, month: int): int =
    if   null dates
    then 0
    else
        let
            val rest = number_in_month((tl dates), month)
        in
            if month = (#2 (hd dates)) then rest + 1 else rest
        end

(*  returns how many dates in date list are in any of the months in months list
 *  Assumes that int list has no numbers repeated  *)
fun number_in_months(dates: date list, months: int list): int =
    if   null months
    then 0
    else number_in_month(dates, hd months) +
        number_in_months(dates, tl months)

(*  returns list of dates that are in given month  *)
fun dates_in_month (dates: date list, month: int): date list =
    if   null dates
    then []
    else
        let
            val rest = dates_in_month(tl dates, month)
        in
            if   #2 (hd dates) = month
            then (hd dates) :: rest
            else rest
        end

(*  returns list of dates that are in any of the months in the list of months
 *  Assumes that int list has no numbers repeated  *)
fun dates_in_months (dates: date list, months: int list): date list =
    if   null months
    then []
    else
        dates_in_month (dates, hd months) @
        dates_in_months(dates, tl months)

(*  returns nth string in list
 *  Assumes list is nonempty and n >= 1  *)
fun get_nth(strlist: string list, n: int): string =
    if   n = 1
    then hd strlist
    else get_nth(tl strlist, n - 1)

(*  returns string of the form January 20, 2013 (for example)  *)
fun date_to_string (date: date): string = let
    val months = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"]
    in
        get_nth(months, (#2 date)) ^ " " ^
        Int.toString(#3 date) ^ ", " ^ Int.toString(#1 date)
    end

(*  returns first n such that sum of first n elts of list < sum,
 *  but of first n + 1 elts >= sum
 *  Assumes sum > 0, list has positive numbers,
 *  and entire list sums to more than sum   *)
fun number_before_reaching_sum(sum: int, poslist: int list): int =
    if   sum <= hd poslist
    then 0
    else 1 + number_before_reaching_sum(sum - hd poslist, tl poslist)

(*  returns what month given day[1, 365] is in  *)
fun what_month(day: int): int =
    let val days_in_months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in number_before_reaching_sum(day, days_in_months) + 1 end

(*  returns list of months of all days in between given two days  *)
fun month_range(day1: int, day2: int): int list =
    if   day1 > day2
    then []
    else what_month(day1) :: month_range(day1 + 1, day2)

(*  returns oldest date in list, NONE if list empty  *)
fun oldest(dates: date list): date option =
    if   null dates
    then NONE
    else
        let
            val tl_ans = oldest(tl dates)
        in
            if not(isSome(tl_ans)) orelse is_older(hd dates, valOf(tl_ans))
            then SOME (hd dates)
            else tl_ans
        end
