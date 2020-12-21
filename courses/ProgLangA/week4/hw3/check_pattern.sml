(*  pattern -> bool  *)
(*  returns true if all variable names in pattern are same  *)
fun check_pat (p) =
    let
        (*pattern -> string list*)
        (*returns list of strings occurring in variables in pattern*)
        fun get_var_strings (pattern) =
        case pattern of
            (* do some stuff here. Follow the hints explained in problem *)
            (* Follow pattern datatype definition for cases. Use foldl, @, and recursion *)

        (*string list -> bool*)
        (*returns true if there are reps, false if no reps*)
        fun check_string_reps (strlist) =
            (* stuff here. Use List.exists *)

    in (* stuff here that uses above two functions *) end
