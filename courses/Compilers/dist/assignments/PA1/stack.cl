(*
 *  CS164 Fall 94
 *
 *  Programming Assignment 1
 *    Implementation of a simple stack machine.
 *
 *  Skeleton file
 *)

class StackCommand {

};

class PushInt inherits StackCommand {

};

class Main inherits IO {

    main() : Object {
        let
            prompt: IO <- out_string(">"),
            input: String <- in_string()
        in
            out_string(input.concat("\n"))
    };
};
