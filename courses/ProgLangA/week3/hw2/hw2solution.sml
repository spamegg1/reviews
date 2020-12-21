fun all_except_option (s,xs) =
  case xs of
      [] => NONE
    | x::xs' => if same_string(s,x)
                then SOME xs'
                else case all_except_option(s,xs') of
                         NONE => NONE
                       | SOME y => SOME(x::y)

fun get_substitutions1 (substitutions,str) =
    case substitutions of
        [] => []
      | x::xs => case all_except_option(str,x) of
                     NONE => get_substitutions1(xs,str)
                   | SOME y => y @ get_substitutions1(xs,str)

fun get_substitutions2 (substitutions,str) =
    let fun loop (acc,substs_left) =
        case substs_left of
            [] => acc
          | x::xs => case all_except_option(str,x) of
       NONE => loop(acc,xs)
           | SOME y => loop(acc @ y,xs))
    in loop([],substitutions) end

fun similar_names (substitutions,name) =
    let val {first=f, middle=m, last=l} = name
        fun make_names xs =
            case xs of
               [] => []
             | x::xs' => {first=x, middle=m, last=l}::(make_names(xs'))
    in name::make_names(get_substitutions2(substitutions,f)) end

fun card_color card =
    case card of
        (Clubs,_)    => Black
      | (Diamonds,_) => Red
      | (Hearts,_)   => Red
      | (Spades,_)   => Black

fun card_value card =
    case card of
        (_,Jack) => 10
      | (_,Queen) => 10
      | (_,King) => 10
      | (_,Ace) => 11
      | (_,Num n) => n

fun remove_card (cs,c,e) =
    case cs of
        [] => raise e
      | x::cs' => if x = c then cs' else x :: remove_card(cs',c,e)

fun all_same_color cs =
    case cs of
        [] => true
      | [_] => true
      | head::neck::tail => card_color head = card_color neck
          andalso all_same_color(neck::tail)

fun sum_cards cs =
    let fun loop (acc,cs) =
      case cs of
          [] => acc
        | c::cs' => loop (acc + card_value c, cs')
    in
      loop (0,cs)
    end


fun score (cs,goal) =
    let
        val sum = sum_cards cs
    in
        (if sum >= goal then 3 * (sum - goal) else goal - sum)
        div (if all_same_color cs then 2 else 1)
    end

fun officiate (cards,plays,goal) =
    let
        fun loop (current_cards,cards_left,plays_left) =
            case plays_left of
                [] => score(current_cards,goal)
              | (Discard c)::tail =>
                loop (remove_card(current_cards,c,IllegalMove),cards_left,tail)
              | Draw::tail =>
                (* note: must score immediately if go over goal! *)
                case cards_left of
                    [] => score(current_cards,goal)
                  | c::rest => if sum_cards (c::current_cards) > goal
                               then score(c::current_cards,goal)
                               else loop (c::current_cards,rest,tail)
    in
        loop ([],cards,plays)
    end

