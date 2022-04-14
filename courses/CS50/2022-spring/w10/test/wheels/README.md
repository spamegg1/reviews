​            

# [This is CS50](https://cs50.harvard.edu/college/2022/spring/)

[Harvard College](https://www.college.harvard.edu/)
 Spring 2022

​                       [Week 0](https://cs50.harvard.edu/college/2022/spring/weeks/0/) Scratch  [Week 1](https://cs50.harvard.edu/college/2022/spring/weeks/1/) C  [Week 2](https://cs50.harvard.edu/college/2022/spring/weeks/2/) Arrays  [Week 3](https://cs50.harvard.edu/college/2022/spring/weeks/3/) Algorithms  [Week 4](https://cs50.harvard.edu/college/2022/spring/weeks/4/) Memory  [Week 5](https://cs50.harvard.edu/college/2022/spring/weeks/5/) Data Structures  [Week 6](https://cs50.harvard.edu/college/2022/spring/weeks/6/) Python  [Week 7](https://cs50.harvard.edu/college/2022/spring/weeks/7/) SQL  [Week 8](https://cs50.harvard.edu/college/2022/spring/weeks/8/) HTML, CSS, JavaScript  [Week 9](https://cs50.harvard.edu/college/2022/spring/weeks/9/) Flask  [Week 10](https://cs50.harvard.edu/college/2022/spring/weeks/10/) Emoji     [🙋](https://cs50.harvard.edu/college/2022/spring/ed/)[Ed Discussion](https://cs50.harvard.edu/college/2022/spring/ed/) for Q&A     [Announcements](https://cs50.harvard.edu/college/2022/spring/#announcements)  [FAQs](https://cs50.harvard.edu/college/2022/spring/faqs/)  [Final Projects](https://cs50.harvard.edu/college/2022/spring/projects/)  [Info Session](https://cs50.harvard.edu/college/2022/spring/info/)  [Labs](https://cs50.harvard.edu/college/2022/spring/labs/)  [Office Hours](https://cs50.harvard.edu/college/2022/spring/hours/)  [Peer Tutors](https://cs50.harvard.edu/college/2022/spring/tutors/)  [Problem Sets](https://cs50.harvard.edu/college/2022/spring/psets/)  [Quizzes](https://cs50.harvard.edu/college/2022/spring/quizzes/)  [Sections](https://cs50.harvard.edu/college/2022/spring/sections/)  [Staff](https://cs50.harvard.edu/college/2022/spring/staff/)  [Study Buddies](https://cs50.harvard.edu/college/2022/spring/buddies/)  [Syllabus](https://cs50.harvard.edu/college/2022/spring/syllabus/)  [Test](https://cs50.harvard.edu/college/2022/spring/test/)  [Tutorials](https://cs50.harvard.edu/college/2022/spring/tutorials/)     [CS50 Manual Pages](https://manual.cs50.io/)  [CS50 Status Page](https://cs50.statuspage.io/)  [Style Guide for C](https://cs50.readthedocs.io/style/c/)  [Visual Studio Code](https://code.cs50.io/)                                      



# [Reinventing Some Wheels](https://cs50.harvard.edu/college/2022/spring/test/wheels/#reinventing-some-wheels)

A common practice in industry is [test-driven development](https://en.wikipedia.org/wiki/Test-driven_development), whereby you write tests for code before you write the code itself. The  tests themselves should be simple enough that they don’t need their own  tests. For instance, a test might call some function one or more times,  each time with different arguments, testing that the return values are  expected. Let’s do just that.

Suppose that you’ve forgotten about [`ctype.h`](https://manual.cs50.io/#ctype.h) and so you’ve decided to implement your own versions of `isalpha`, `isdigit`, and `isalnum` as well as another function of your own, `areupper`. You’ve already declared prototypes for them in `wheels/ctype.h`, and you’ve even started to implement them in `wheels/ctype.c` but mostly in comments, temporarily returning `false` from most of them (so that the file will at least compile). You have, though, implemented `isalpha` already.

In `wheels/check50.c`, meanwhile, you’ve written a simplified implementation of `check50`. Notice how it calls `test_isalpha`, `test_isdigit`, `test_isalnum`, and `test_areupper`, all of which are declared in `tests.h`, and then, if all four of those functions work as expected, prints a single smiley face. Only one of those functions, `test_isalpha`, is actually implemented at the moment in `wheels/tests.c`. As you might expect, it tests `isalpha`, calling the function multiple times with different arguments, testing with `assert` that the return values are as expected. Per its [manual page](https://manual.cs50.io/3/assert), if the input to `assert` is an expression that evaluates to `false`, `assert` will print an error message and abort the program (i.e., `check50`) immediately. If, though, the input to `assert` is an expression that evaluates to `true`, `assert` will do nothing, allowing execution continue.

Let’s test your (well, our) implementation of `isalpha`. In `wheels/`, execute

```
make check50
```

in order to compile the simplified version of `check50` and, with it, `ctype.c` and `tests.c`. Then execute `check50` and, with it, `test_isalpha`, with:

```
./check50
```

You should see a smiley face, because `isalpha` is not only implemented, it also passes all of the tests in `test_isalpha`!

Let’s implement the rest of the tests and the functions they test!

1. (5 points.) Complete the implementation of `test_isdigit` in `wheels/tests.c` in such a way that it tests `isdigit`, calling `assert` at least three times, each time with a different argument, testing that the function’s return value is as expected. Then, complete the  implementation of `isdigit` itself, running `check50` thereafter to ensure that you still see a smiley.

1. (5 points.) Complete the implementation of `test_isalnum` in `wheels/tests.c` in such a way that it tests `isalnum`, calling `assert` at least three times, each time with a different argument, testing that the function’s return value is as expected. Then, complete the  implementation of `isalnum` itself, running `check50` thereafter to ensure that you still see a smiley.

1. (5 points.) Complete the implementation of `test_areupper` in `wheels/tests.c` in such a way that it tests `areupper`, calling `assert` at least three times, each time with a different argument, testing that the function’s return value is as expected. Then, complete the  implementation of `areupper` itself, running `check50` thereafter to ensure that you still see a smiley.

Just keep in mind that that a smiley face means that your functions  have passed all of your tests, but it doesn’t mean that all of your  tests are correct!