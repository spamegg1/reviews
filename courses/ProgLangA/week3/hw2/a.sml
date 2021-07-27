type student_id = int
type grade = int (* must be in 0 to 100 range *)
type final_grade = {id: student_id, grade: int option}
datatype pass_fail = pass | fail
type name_record = {student_id  : int option,
                    name : string ,
                    middle : string option,
                    surname : string}

val myname = {student_id = SOME 4, name = "name",
                middle = SOME "middle", surname = "surname"}

fun pass_or_fail (x: final_grade) =
    let
        val {grade=g, id=i} = x
    in
        case g of
          SOME y => if y >= 75 then pass else fail
        | NONE => fail
    end

fun helper (xs: int list, pos: bool) =
    if null xs then 0
    else if pos then helper(tl xs, false) - (hd xs)
    else (hd xs) + helper(tl xs, true)

fun alternate (xs: int list) = helper(xs, false)
