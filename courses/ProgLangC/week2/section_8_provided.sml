(* Provided code for section 8 practice problem (Standard ML) *)

datatype character =
    knight of int * int (* a knight has hitpoints and armor points *)
  | wizard of int * int (* a wizard has hitpoints and mana points *)
    (* whenever a character's hitpoints reach zero -- or become negative --
       he expires and shuffles off this mortal coil *)

datatype encounter =
    floor_trap of int (* a floor trap hurts anyone walking over it, reducing their hitpoints *)
  | monster of int * int (* a monster has attack strength and hitpoints *)
  | potion of int * int (* potions may restore some hitpoints and some mana points (if applicable) *)
  | armor of int (* armor pieces may boost a characters armor points (if applicable) *)

type dungeon = encounter list

fun is_dead character =
    case character of
        knight (hp, _) => hp <= 0
        (* this is something of a dirty hack, as it simplifies the encounter mechanics below
           at the cost of wizard state being slightly crazy after a lethal encounter with a
           monster *)
      | wizard (hp, mp) => hp <= 0 orelse mp < 0

fun damage_knight dam (hp, ap) =
    case ap of
        0 => (hp - dam, 0)
      | _ => if dam > ap then damage_knight (dam - ap) (hp, 0) else (hp, ap - dam)

fun play_out_encounter character encounter =
    case (character, encounter) of
        (* knights just walk over traps, grimly accepting their fate *)
        (knight state, floor_trap dam) => knight (damage_knight dam state)
        (* knights take damage from monsters, as their armor hinders their mobility,
           but they are strong enough to take out any monster with a single blow afterwards *)
      | (knight state, monster (dam, _)) => knight (damage_knight dam state)
        (* knights can be healed by potions, but they have no use for mana *)
      | (knight (hp, ap), potion (hp', _)) => knight (hp + hp', ap)
        (* knights just love shiny armor, as it improves their survivability
           and makes them look cool! *)
      | (knight (hp, ap), armor ap') => knight (hp, ap + ap')
        (* wizards can levitate, so floor traps can't harm them... as long as they can spend a
           single mana point on the spell *)
      | (wizard (hp, mp), floor_trap dam) => if mp > 0 then wizard (hp, mp - 1) else wizard (hp - dam, mp)
        (* wizards can hurl powerful fireballs from great distances... unfortunately, they
           need mana points equal to the damage dealt to do that, and if a monster gets close,
           they're toast, as their martial skills are nonexistent *)
      | (wizard (hp, mp), monster (_, hp')) => wizard (hp, mp - hp')
        (* wizards love potions, as they help them all around! *)
      | (wizard (hp, mp), potion (hp', mp')) => wizard (hp + hp', mp + mp')
        (* wizards couldn't care less for armor, as it does them absolutely no good *)
      | (wizard state, armor _) => wizard state

fun resolve_encounter character encounter =
    if is_dead character
        then character (* dead characters have already done all their adventuring... *)
        else play_out_encounter character encounter

(* produces no side effects, but might be useful for testing *)
val compute_final_outcome = List.foldl (fn (x, y) => resolve_encounter y x)

fun print_char character =
    case character of
        knight (hp, ap) => print ("HP: " ^ Int.toString hp ^ " AP: " ^ Int.toString ap ^ "\n")
      | wizard (hp, mp) => print ("HP: " ^ Int.toString hp ^ " MP: " ^ Int.toString mp ^ "\n")

fun print_enc encounter =
    case encounter of
        floor_trap dam => print ("A deadly floor trap dealing " ^ Int.toString dam ^
            " point(s) of damage lies ahead!\n")
      | monster (dam, hp) => print ("A horrible monster lurks in the shadows ahead. It can attack for " ^
            Int.toString dam ^ " point(s) of damage and has " ^ Int.toString hp ^ " hitpoint(s).\n")
      | potion (hp, mp) => print ("There is a potion here that can restore " ^ Int.toString hp ^
            " hitpoint(s) and " ^ Int.toString mp ^ " mana point(s).\n")
      | armor ap => print ("A shiny piece of armor, rated for " ^ Int.toString ap ^
            " AP, is gathering dust in an alcove!\n")

(* tells a story of a given hero trying to storm a dungeon represented as a list of
   sequential encounters *)
fun play_out_adventure character dungeon =
    if is_dead character
        then (print "Alas, the hero is dead.\nThe adventure ends here.\n"; character)
        else (print_char character;
            case dungeon of
                [] => (print "The hero emerges victorious!\nTheir adventures are over...\nFOR NOW.\n"; character)
              | (encounter :: rest_of_the_dungeon) => (print_enc encounter;
                    play_out_adventure (resolve_encounter character encounter) rest_of_the_dungeon))

(* some heroes and dungeons to try out for your enjoyment *)

val sir_foldalot = knight (15, 3)
val knight_of_lambda_calculus = knight (10, 10)
val sir_pinin_for_the_fjords = knight (0, 15)
val alonzo_the_wise = wizard (3, 50)
val dhuwe_the_unready = wizard (8, 5)

val dungeon_of_mupl = [
    monster (1, 1),
    floor_trap 3,
    monster (5, 3),
    potion (5, 5),
    monster (1, 15),
    armor 10,
    floor_trap 5,
    monster (10, 10)
    ]
val the_dark_castle_of_proglang = [
    potion (3, 3),
    monster (1, 1),
    monster (2, 2),
    monster (4, 4),
    floor_trap 3,
    potion (3, 3),
    monster (4, 4),
    monster (8, 8),
    armor 5,
    monster (3, 5),
    monster (6, 6),
    floor_trap 5
    ]
