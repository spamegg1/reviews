(* I wrote these to test each functionality separately.
    Compile with coolc stack.cl atoi.cl test.cl *)
class Test inherits IO {
    testPushPop(): IO {
        let
            st: Stack <- (new Stack).init((new Nil).init()),
            x : String
        in {
            st <- st.push("a");
            out_string("After pushing a, stack is: \n");
            out_string(st.display());

            st <- st.push("bc");
            out_string("After pushing bc, stack is: \n");
            out_string(st.display());

            st <- st.push("def");
            out_string("After pushing def, stack is: \n");
            out_string(st.display());

            x <- st.pop();
            out_string("After popping def, stack is: \n");
            out_string(st.display());
            out_string("Popped string was: ".concat(x).concat("\n"));

            x <- st.pop();
            out_string("After popping bc, stack is: \n");
            out_string(st.display());
            out_string("Popped string was: ".concat(x).concat("\n"));

            x <- st.pop();
            out_string("After popping a, stack is: \n");
            out_string(st.display());
            out_string("Popped string was: ".concat(x).concat("\n"));
        }
    };

    testAdd(): IO {
        let
            st: Stack <- (new Stack).init((new Nil).init())
        in {
            st <- st.push("42");
            st <- st.push("-3");
            st <- st.push("+");
            out_string("Before adding, stack is: \n");
            out_string(st.display());
            st <- st.eval();
            out_string("After adding, stack is: \n");
            out_string(st.display());
        }
    };

    testSwap(): IO {
        let
            st: Stack <- (new Stack).init((new Nil).init())
        in {
            st <- st.push("I'm going up! :)");
            st <- st.push("I'm going down :(");
            st <- st.push("s");
            out_string("Before swapping, stack is: \n");
            out_string(st.display());
            st <- st.eval();
            out_string("After swapping, stack is: \n");
            out_string(st.display());
        }
    };

    runTests(): IO {{
        testPushPop();
        testAdd();
        testSwap();
    }};
};
