type date = int * int * int

fun number_in_month(lon: date list, m: int): int =
    if null lon then 0
    else if (#2 (hd lon) = m)
    then 1 +  number_in_month(tl lon, m)
    else number_in_month(tl lon, m)
