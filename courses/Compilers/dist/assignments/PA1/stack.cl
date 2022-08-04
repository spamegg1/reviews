(* CS164 Fall 94
 * Programming Assignment 1
 * Implementation of a simple stack machine.
 * Skeleton file
 * compile with: coolc stack.cl atoi.cl test.cl
 *)

class List {                                                  -- list of strings
    size  : Int;
    car   : String;                                         -- first elt of list
    cdr   : List;                                                -- rest of list
    head(): String { car };                              -- getter for first elt
    tail(): List   { cdr };                                   -- getter for rest
    len() : Int    { size };                                  -- getter for size
    cons(i: String): List { (new Cons).init(i, self) };  -- add new elt in front
};

class Nil inherits List {                                          -- empty list
    init(): List {{ size <- 0; self; }};         -- leave car, cdr uninitialized
};

class Cons inherits List {                                      -- nonempty list
    init(s: String, rest: List): List {{                      -- rest can be Nil
        size <- 1 + rest.len();        -- recursion with dynamic dispatch! YEAH!
        car  <- s;
        cdr  <- rest;
        self;
    }};
};

class Stack {
    stack: List;                                          -- modeled with a List

    init(list: List): Stack {{
        stack <- list;
        self;
    }};

    push(s: String): Stack {{
        stack <- (new Cons).init(s, stack);
        self;
    }};

    pop(): String {                                    -- pop head and return it
        let result: String <- stack.head()
        in {
            stack <- stack.tail();                      -- remove head from list
            result;
        }
    };

    display(): String {                             -- build up string with loop
        let
            i     : Int    <- 0,                                     -- loop var
            bound : Int    <- stack.len(),                         -- loop bound
            list  : List   <- stack,                       -- don't mutate stack
            result: String <- ""                           -- string to build up
        in {
            while i < bound loop {
                result <- result.concat(list.head()).concat("\n"); -- print head
                list   <- list.tail();                   -- stack is not mutated
                i      <- i + 1;
            } pool;
            result;
        }
    };

    eval(): Stack {                             -- evaluate addition or swapping
        if stack.head() = "+" then add() else                   -- mutates stack
        if stack.head() = "s" then swap()                       -- mutates stack
        else self fi fi                               -- else don't change stack
    };

    add(): Stack {                               -- does addition, mutates stack
        let
            atoi: A2I    <- new A2I,
            plus: String <- pop(),                     -- "+" is on top of stack
            num1: Int    <- atoi.a2i(pop()),               -- followed by 2 ints
            num2: Int    <- atoi.a2i(pop())
        in  push(atoi.i2a(num1 + num2))               -- new stack w/ sum on top
    };

    swap(): Stack {                                  -- does swap, mutates stack
        let
            s  : String <- pop(),                      -- "s" is on top of stack
            top: String <- pop(),                       -- followed by 2 strings
            bot: String <- pop()
        in  push(top).push(bot)               -- pop them, push in reverse order
    };

    handle(s: String): Stack {            -- input handling: either eval or push
        if s = "e"                     -- exit "x" and display "d" handled below
        then eval()
        else push(s)                        -- both eval and push return a stack
        fi
    };
};

class Main inherits IO {
    runMachine(): Object {                             -- runs the stack machine
        let
            input: String <- "",
            st   : Stack  <- (new Stack).init((new Nil).init())
        in
            while not input = "x" loop {                 -- exit if input is "x"
                out_string(">");                               -- display prompt
                input <- in_string();                               -- get input
                if   input = "d"                              -- if input is "d"
                then out_string(st.display())                   -- display stack
                else st.handle(input)            -- else input is "e" or a value
                fi;
            } pool
    };

    main(): Object {{
        (new Test).runTests(); -- see test.cl
        --runMachine();
    }};
};
