## README file for Programming Assignment 1

Your directory should now contain the following files:

```bash
Makefile
README
atoi.cl -> [cool root]/assignments/PA1/atoi.cl
stack.cl
stack.test -> [cool root]/assignments/PA1/stack.test
```

- The Makefile contains targets for compiling and running your program, as well as handing it in.

- The README contains this info. Part of the assignment is to answer the questions at the end of this README file. Just edit this file.
- `atoi.cl` is an implementation in Cool of the string to integer conversion function known from the C language.
- `stack.cl` is the skeleton file which you should fill in with your program.
- `stack.test` is a short test input to the stack machine.
- The symlinked files (see `man ln`) are that way to emphasize that they are read-only.

## Instructions

To compile and run your program, type:
```bash
make test
```
Try it now -- it should work, and print `Nothing implemented` (among a few other things). After completing the assignment, `make test` should print:
```bash
 ➜ make test
stack.test
../../bin/spim -file stack.s < stack.test
SPIM Version 6.5 of January 4, 2003
Copyright 1990-2003 by James R. Larus (larus@cs.wisc.edu).
All Rights Reserved.
See the file README for a full copyright notice.
Loaded: ../lib/trap.handler
>>>>>>>s
2
+
1
>>>3
>>>>>>>s
s
s
1
+
3
>>>>>>>4
>COOL program successfully executed
```
Here the multiple `>` signs on the same line are due to the fact that `spim` is reading the inputs from the file `stack.test` instead of being inputted by hand one at a time. So the inputs themselves are not displayed, only the prompts `>` and the results of `d` commands are displayed.

This makes sense, since the PA1 handout says:
```
Your stack machine should not produce any output aside from whitespace (which our testing harness will ignore), ‘>’ prompts, and the output of a ‘d’ command.
```

The output of `d` also starts without a new line (like `>>>3`) for the same reason (normally when we input by hand and hit Enter, there would be a new line there). See the `proof.png` and `proof2.png` images for what the interactive session looks like.

To simply compile your program, type
```bash
make compile
```
Instructions for turning in the assignment will be posted on the course web page.

GOOD LUCK!

## Questions on PA1

1. Describe your implementation of the stack machine in a single short
   paragraph.

2. List 3 things that you like about the Cool programming language.

    **ANSWER:**
    - It's very similar to ML
    - `let`
    - arrow `<-` binding notation

3. List 3 things you DON'T like about Cool.

    **ANSWER:**
    - Curly braces (understandable, makes compiler writing easier)
    - Semicolons (understandable, makes compiler writing easier)
    - OOP