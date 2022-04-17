                CS 415 - Programming Languages - Wes Weimer 
                Version 1.00 - Sat Jan  6 12:08:02 EST 2007

This package contains a few simple Cool programs to help you get started.
These are useful both for understanding Cool and as basic testcases 
for your scanner, parser, type checker and interpreter. 

These are all positive test cases. That is, they should all scan, parse, type
check and execute correctly. You will have to construct your own negative test
cases to exercise failure modes. We have many held-out testcases, both
positive and negative, that are not present in this set. 

You are welcome to incorporate any of this code in your own assignments (e.g.,
in PA1). However, you *must* properly credit the inclusion in your readme. 

The files are: 

hello-world.cl  prints "Hello, world.\n" to standard output 

atoi.cl         an integer-to-string and string-to-integer class for Cool

list.cl         a well-commented linked list example 

sort-list.cl    another linked list example; this one includes sorting

arith.cl        a "menu-driven" interactive desktop calculator 
                (includes an inlined version of atoi) 

cells.cl        simple cellular automaton simulator 

primes.cl       a "methodless" program that enumerates all of the primes
                between 2 and 500. It is not a good example of object-oriented
                style, but it does show off some corner cases of Cool. 

print-cool.cl   a more complicated hello-world that prints "cool\n" 

new-complex.cl  a complex arithmetic and equality regression test 

hs.cl           "hairy-scary" is a torture regression test

That's it! 
