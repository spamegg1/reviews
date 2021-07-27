(* quadratic algorithm rather than sorting which is nlog n *)
fun mem(x: int, xs: int list) =
    not (null xs) andalso (x = hd xs orelse mem(x, tl xs))
fun remove_duplicates(xs: int list) =
    if null xs
    then []
    else
        let
            val tl_ans = remove_duplicates (tl xs)
        in
            if mem(hd xs, tl_ans)
            then tl_ans
            else (hd xs) :: tl_ans
        end

fun number_in_months_challenge(dates: (int * int * int) list, months: int list) =
    number_in_months(dates, remove_duplicates months)

fun dates_in_months_challenge (dates: (int * int * int) list, months: int list) =
    dates_in_months(dates, remove_duplicates months)

fun reasonable_date (date: int * int * int) =
    let
        fun get_nth (lst: int list, n: int) =
        if n = 1
        then hd lst
        else get_nth(tl lst, n - 1)
        val year  = #1 date
        val month = #2 date
        val day   = #3 date
        val leap  = year mod 400 = 0 orelse (year mod 4 = 0 andalso year mod 100 <> 0)
        val feb_len = if leap then 29 else 28
        val lengths = [31, feb_len, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in
        year > 0 andalso month >= 1 andalso month <= 12
        andalso day >= 1 andalso day <= get_nth(lengths, month)
    end
