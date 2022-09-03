(* quadratic algorithm rather than sorting which is nlog n *)
use "hw1.sml";

fun mem(x: int, xs: int list) =
    not (null xs) andalso (x = hd xs orelse mem(x, tl xs))

fun remove_duplicates_old(xs: int list): int list =
    if null xs
    then []
    else
        let
            val tl_ans = remove_duplicates_old(tl xs)
        in
            if mem(hd xs, tl_ans)
            then tl_ans
            else (hd xs) :: tl_ans
        end

fun merge(xs: int list, ys: int list): int list =
    case (xs, ys) of
        (nil, _) => ys
    |   (_, nil) => xs
    |   _ =>
        if hd xs < hd ys
        then (hd xs) :: merge(tl xs, ys)
        else (hd ys) :: merge(xs, tl ys)

fun sort(xs: int list): int list =
    if null xs orelse null (tl xs) then xs else
    let
        val mid = (length xs) div 2
        val left = List.take(xs, mid)
        val right = List.drop(xs, mid)
    in
        merge(sort left, sort right)
    end

fun remove_duplicates(xs: int list): int list =
    let
        val ys = sort xs
    in
        case ys of
            y1 :: y2 :: rest =>
                if y1 = y2
                then remove_duplicates(y2 :: rest)
                else y1 :: remove_duplicates(y2 :: rest)
        |   _ => ys
    end

fun number_in_months_challenge(dates: (int * int * int) list, months: int list) =
    number_in_months(dates, remove_duplicates months)

fun dates_in_months_challenge (dates: (int * int * int) list, months: int list) =
    dates_in_months(dates, remove_duplicates months)

fun reasonable_date (date: int * int * int) =
    let
        fun get_nth (lst: int list, n: int) =
            if n = 1 then hd lst else get_nth(tl lst, n - 1)
        val year  = #1 date
        val month = #2 date
        val day   = #3 date
        val leap  = year mod 400 = 0 orelse
                    (year mod 4 = 0 andalso year mod 100 <> 0)
        val feb_len = if leap then 29 else 28
        val lengths = [31, feb_len, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in
        year > 0 andalso month >= 1 andalso month <= 12
        andalso day >= 1 andalso day <= get_nth(lengths, month)
    end

val testdupl1: bool = remove_duplicates [] = []
val testdupl2: bool = remove_duplicates [2, 1, 2, 3, 3, 3, 4, 4, 3, 2, 1] = [1, 2, 3, 4]
val testsort1: bool = sort [5, 4, 3, 2, 1] = [1, 2, 3, 4, 5]
val testsort2: bool = sort [1, 5, 2, 4, 3] = [1, 2, 3, 4, 5]
val testsort3: bool = sort [1, 4, 3, 5, 2] = [1, 2, 3, 4, 5]
