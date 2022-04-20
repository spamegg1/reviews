(*  CS164 Fall 94
 *  Programming Assignment 1
 *    Implementation of a simple stack machine.
 *  Skeleton file
 *)

class List {
    size   : Int;
    car    : String;
    cdr    : List;
    head() : String { car };
    tail() : List   { cdr };
    len()  : Int    { size };
    cons(i : String) : List { (new Cons).init(i, self) };
};

class Nil inherits List {
    init(): List { { size <- 0; self; } };
};

class Cons inherits List {
    init(s: String, rest: List): List {{
        size <- 1 + rest.len();  -- recursion with dynamic dispatch! YEAH!
        car <- s;
        cdr <- rest;
        self;
    }};
};

class Stack {
    stack: List;
    init(list: List): Stack {{
        stack <- list;
        self;
    }};

    push(s: String): Stack {{
        stack <- (new Cons).init(s, stack);
        self;
    }};

    pop(): String {
        let result: String <- stack.head()
        in {
            stack <- stack.tail();
            result;
        }
    };

    display(): String {
        let
            i     : Int    <- 0,
            bound : Int    <- stack.len(),
            list  : List   <- stack,
            result: String <- ""
        in {
            while i < bound loop {
                result <- result.concat(list.head()).concat("\n");
                list   <- list.tail();
                i      <- i + 1;
            } pool;
            result;
        }
    };

    eval(): Stack {
        if stack.head() = "+" then add() else
        if stack.head() = "s" then swap()
        else self fi fi
    };

    add(): Stack {
        let
            atoi: A2I    <- new A2I,
            plus: String <- pop(),
            num1: Int    <- atoi.a2i(pop()),
            num2: Int    <- atoi.a2i(pop())
        in  push(atoi.i2a(num1 + num2))
    };

    swap(): Stack {
        let
            s  : String <- pop(),
            top: String <- pop(),
            bot: String <- pop()
        in  push(top).push(bot)
    };

    handle(s: String): Stack {
        if s = "e" then eval() else push(s) fi
    };
};

class Main inherits IO {
    runMachine(): Object {
        let
            input: String <- "",
            st   :  Stack <- (new Stack).init((new Nil).init())
        in {
            while not input = "x" loop {
                out_string(">");
                input <- in_string();
                if input = "d" then {
                    out_string(st.display());
                }
                else {
                    st.handle(input);
                }
                fi;
            } pool;
        }
    };

    main(): Object {{
        -- runTests(); -- see test.cl
        runMachine();
    }};
};
