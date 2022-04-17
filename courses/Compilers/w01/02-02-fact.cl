class Main inherits A2I {
    main(): Object {
        (new IO).out_string(
            i2a(
                factIter(
                    a2i((new IO).in_string())
                )
            ).concat("\n")
        )
    };

    factRec(n: Int): Int {
        if   (n = 0)
        then 1
        else n * factRec(n - 1)
        fi
    };

    factIter(n: Int): Int {
        let
            fact: Int <- 1
        in
        {
            while (not (n = 0)) loop
                {
                    fact <- fact * n;
                    n <- n - 1;
                }
            pool;
            fact;
        }
    };
};
