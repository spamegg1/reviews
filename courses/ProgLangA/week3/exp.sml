datatype exp = Constant of int
             | Negate of exp
             | Add of exp * exp
             | Multiply of exp * exp

fun number_of_adds (e : exp)=
    let
        fun number_of_adds_e (e: exp, worklist : exp list, result : int) =
        case e of
          Constant i      => number_of_adds_l(worklist, result)
        | Negate e2       => number_of_adds_e(e2, worklist, result)
        | Add(e1,e2)      => number_of_adds_e(e1, e2::worklist, result + 1)
        | Multiply(e1,e2) => number_of_adds_e(e1, e2::worklist, result)

        and number_of_adds_l(worklist: exp list, result : int) =
            if null worklist
            then result
            else number_of_adds_e(hd worklist, tl worklist, result)
    in
        number_of_adds_e(e, [], 0)
    end

val test1: bool = number_of_adds(Add(Constant(4), Constant(5))) = 1
val test2: bool = number_of_adds(Add(
    Add(Constant(4), Constant(5)), Constant(3))) = 2
val test3: bool = number_of_adds(Multiply(
    Add(Constant(4), Constant(5)), Constant(3))) = 1
