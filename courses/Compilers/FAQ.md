- [OpenClassroom](http://openclassroom.stanford.edu/MainFolder/HomePage.php)

​				[ 				    ![Compilers](http://openclassroom.stanford.edu/MainFolder/courses/Compilers/Compilers-logo.png)  				](http://openclassroom.stanford.edu/MainFolder/CoursePage.php?course=Compilers) 			

[Compilers](http://openclassroom.stanford.edu/MainFolder/CoursePage.php?course=Compilers)

## Alex Aiken

# FAQ

​    [General Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#general)
​    [Video Lecture Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#video)
​    [Programming Assignment 1 Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#pa1)
​    [Programming Assignment 2 Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#pa2)
​    [Programming Assignment 3 Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#pa3)
​    [Programming Assignment 4 Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#pa4)
​    [Programming Assignment 5 Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#pa5)
​    [Optimizer Questions](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html#opt)

### General Questions

Q. What is the purpose of this page?

A. This page is a list of the most frequently asked questions received by  the staff for the programming assignments. If you have a question regarding  specification or implementation details of the assignments, you should check  this FAQ page for an answer.

Q. What do I do when I have a question?  

A. A couple of places to look for help:   

-  Check this page for the answer
-  Re-read the assignment handout to see if it addresses the question
-  Look through the comments of the source code files we have provided for relevant comments
-  Look at our sample output (if appropriate)
-  The [Other Materials](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/other_materials.html) page has some links to references and documentation



Q. How can I run the reference compiler?  

A. Try

```bash
[cool root]/bin/coolc test.cl
```

where `[cool root]` is the root directory of your copy of the programming assignment source tree.

Q. How do I execute the program using SPIM?  

A. Try

```bash
[cool root]/bin/spim -f test.s
```

where `[cool root]` is the root directory of your copy of the programming assignment source tree.



### Video Lecture Questions

Q. Why does the play/pause button sometimes stop working  during quiz questions?

A. We've had intermittent issues with the OpenClassroom video applet used  on this site. Occasionally the play/pause button will stop working, usually  during a quiz question. This can sometimes be fixed by dragging the playhead  to a different time in the video, then pressing play again. If not, refreshing  the page is a surefire way to fix the problem (though you will lose your place  in the video).

### Programming Assignment 1 Questions
None at this time.

### Programming Assignment 2 Questions
Q. How can I run the reference lexer?
A. Try
```bash
[cool root]/bin/lexer test.cl
```
where `[cool root]` is the root directory of your copy of the programming assignment source tree.

Q. Do I report errors by printing on the console?
A. No, you do not report error messages by outputting to the console. In Flex, you can report errors like this:

```c
yylval.error_msg = "EOF in comment";
```

and in JLex, you report them like this:

```java
return new Symbol(TokenConstants.ERROR, "EOF in comment");
```

Q. How do I write the null character to a file for testing?
A. This depends on the editor you use.  In VI, you can write the null character as "Ctrl-V Ctrl-@"; in Emacs try "C-q 000".

Q. What do you mean by nested comments? Is `(* (* Hello *)` a valid nested comment?
A. No, each `(*` should be matched by a closing `*)`.



### Programming Assignment 3 Questions

Q. How can I run the reference parser?
A. Try

```bash
[cool root]/bin/lexer test.cl | [cool root]/bin/parser
```

where `[cool root]` is the root directory of your copy of the programming assignment source tree.

Q. The let construct described in the Cool manual  allows binding multiple variables in one let expression; but the let  constructor defined in the starter code allows binding only one  variable. How can we handle binding multiple variables? 
A. You should treat  the expression

```ocaml
let x:T1<-e1, y:T2<-e2 in e3
```
the same as

```ocaml
let x:T1<-e1 in (let y:T2<-e2 in e3)
```

### Programming Assignment 4 Questions

Q. How can I run the reference semantic analyzer?
A. Try
```bash
[cool root]/bin/lexer test.cl | [cool root]/bin/parser | [cool root]/bin/semant
```
where `[cool root]` is the root directory of your copy of the programming assignment source tree.

Q. How can I run the debugger on my semantic analyzer?
A. Try
```bash
[cool root]/bin/lexer test.cl | [cool root]/bin/parser > temp.txt
gdb ./semant
```

where `[cool root]` is the root directory of your copy of the programming assignment source tree. In GDB type

```bash
run < temp.txt
```

### Programming Assignment 5 Questions

None at this time.

### Optimizer Questions

Q. When should I do the optimizer?

A. You should, under no circumstances, start on the optimizer before you finish your code generator.

Q. I plan to do the extra credit assignment. Is it a good idea to convert the AST to a low-level intermediate  representation that I can take advantage of in both the code generator  and the optimizer?

A. No, that would not be a good idea. We recommend  that you completely finish your code generator before you do invest any  time and effort into the extra credit assignment.

Q. Could you give us some specific suggestions about what kinds of optimizations might have a large impact on Cool programs?

A.  The most important improvement you can make in  Cool code is to unbox integers.  No other optimization will make much difference unless you improve the integer representation first.

"Unboxing" a value means storing it as a raw value rather than an object. Integers in Cool are boxed by default—they live inside of a "box" (the object) and can only be gotten out by dereferencing a pointer.  An unboxed integer, in contrast, is an integer held in a register or stored directly on the stack (not as part of an object).

There are two complications in unboxing integers. First, you will now have different kinds of representations that the generated code must deal with; you have to know when something is an integer and when it is   an object.  Second, the garbage collector will have the same   problem—you can't have integers that look like pointers anywhere that   the garbage collector might stumble across them.   In particular, you   can't just store unboxed integers in the stack without somehow telling   the garbage collector that they are not pointers.

For problem 1, you can use the type system to help you out.  Maintain   the following invariant: any expression of type Int is represented by an   unboxed integer; any expression of any other type is represented by an   object.  The only problem is when type `Int` is implicitly or explicitly   cast to type `Object`, or vice-versa.  For example, in a method call, the   actual parameter might be of type `Int` and the formal parameter might be   of type `Object`.  Similarly, a case expression might downcast a value of   type `Object` to `Int`.  There are other places in the language where such a   conversion can happen, but you can find them all by looking for uses of   subtyping in the type checking rules.  Whenever a value of static type Int is   converted to a value of static type `Object`,    you will have to produce code that boxes the integer (allocates an   integer object and stores the unboxed integer inside it).  Similarly,   when a case downcasts an `Object` to type `Int`, you need to unbox the   integer: load the integer value in the object into the accumulator.

For problem 2, you can either tag integers stored in the stack, or you can store the unboxed integers somewhere other than the stack.  There   are several ways you could do this.

The first solution is  used widely in Lisp and functional language   implementations.  The low order bit of an unboxed pointer or integer is   reserved as a tag: if it is 0 the word is a pointer, if it is 1 the word   is an integer.  For pointers that is the end of the story; they function   just as usual.  For integers, the upper 31 bits are the integer value.   To do arithmetic operations, the simplest thing is to shift each   argument right to get rid of the low order bit, do the op, and then left   shift 1 bit and or in the tag bit.  So, for example, `c = a * b`,   where `a`, `b`, and `c` are all registers, would   translate to

```assembly
shiftr a 1
shiftr b 1
mul c a b
shiftl c 1
or c #0001
```

and an add  of the form `c = a + b` can be translated into a   two instruction sequence:

```assembly
and a  #FFFE
add c a b
```

These values can be saved in the stack and the garbage collector will   ignore them since they do not look like pointers.  The downsides of this   approach are the slower than optimal instruction sequences and the fact   that you now have only 31 bit integers, which is a problem sometimes in   practice.  If you take this approach please document it and we won't   penalize you for test cases that appear to fail due to the limited   integer range.

Another, non-standard, approach would be to simply box integers that get   stored in the stack—saving an integer into the stack is a coercion   that allocates an object, just like those introduced by subtyping `Int ->   Object`.  This preserves the full range of integers and all programs will   work as expected.

A third approach would be to allocate a separate stack just for integer   values in an area not touched by the garbage collector.  The only space   readily available for that is the global data area, so that stack would   need to be fixed size.  For non-recursive procedures, or for integers   that do no survive across function calls, you could just pre-allocate   memory for every int the procedure uses.  For recursive procedures you   would have to do something different, presumably saving a (boxed)   integer in the regular stack.

### Resources

- [Syllabus](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/syllabus.html) 
- [Video Slides](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/slides.html) 
- [Handouts](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/handouts.html) 
- [Programming Assignments](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/pa.html) 
- [Other Materials](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/other_materials.html) 
- [FAQ](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html) 