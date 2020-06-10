# Spam's CS Corner: Course Reviews
DISCLAIMER: These are my subjective personal opinions! Make your own judgment. Also, I will talk a lot about my feelings! You are warned.

- [Harvard's CS50 (first half)](#cs50)
- [How to Code 1 & 2](#how-to-code)
- [Programming Languages A, B, C](#plabc)
- [Learn Prolog Now!](#lpn)
- [Haskell Programming from First Principles](#hpffp)
- [Nand2Tetris 1,2](#n2t)
- [Intro to Networking](#intro-to-net)
- [Hack the Kernel](https://github.com/ossu/computer-science/issues/690)
- [Intro to CS and Programming with Python](#mit6001x)
- [Algorithms (Tim Roughgarden)](#coretheory)
- [Databases](#db)
- [Computer Graphics](#graphics)
- [Machine Learning](#ml)
- [Compilers](#compilers)
- [Software Debugging](#debug)
- [Software Testing](#test)
- [Software Architecture & Design](#softdesg)
- [LAFF - On programming for Correctness](#laff)
- [Intro to Parallel Programming](#cs344)
- [Functional Programming in Scala (5 courses)](#scala)
- [Software Construction 1, 2](#softcons)
- [Cryptography 1](#crypt)
- [CS50, second half, Final Project](#cs50-2)
- [Software Processes](#softproc)
- [Software Architecture](#softarch)
- [Functional Programming in Haskell](#haskell)

### <a name="cs50"></a> Harvard's CS50 (first half)
This was my first course. In 2018 this was the "intro/beginner" course on OSSU curriculum. For the sake of completeness I took the whole course at once, including the [second half](#cs50-2). It's pretty challenging for a beginner course, because that's how Harvard does it. In terms of college course numbering, they go with 050 but it feels more like a 200-level class.

The course uses an online IDE which was really top-notch in my opinion. They even invented their own version of the C compiler that gives more "beginner-friendly" and more helpful error messages. They created a much more beginner-friendly version of the "man-pages" for C. They even teach you how to use "valgrind", a code analysis/memory leak detection tool that even some pros don't know or use.

For me the assignments were at the right level of difficulty. Even though it uses C and gets into low-level matters of memory management, it was not too difficult. Yes, initially pointer arithmetic and pointer logic is gonna drive you crazy, but you'll get used to it. Each assignment had 2 versions called "more" and "less", meaning more comfortable or less comfortable with programming. "More" versions are the more challenging ones if I remember correctly, which I preferred to do. Assignments were: Scratch (a visual, block-based programming language for kids), displaying some text, breaking ciphers, credit card number verification, image decoding, image resizing, dictionary word search and more. Really throws lots of different types of problems and file/data types at you! Very good variation for a beginner course.

The instructor David Malan is very energetic and fun, but I really disliked the 1.5 hour long lecture videos recorded in a lecture hall. The lectures were not very useful when doing the assignments. But they had separate much shorter "helper" videos for the assignments, done by the TAs; those were very helpful. The assignments had super long wordy text descriptions that I disliked. It was usually more difficult to decipher all that text than doing the assignments themselves.

I think it's a challenging beginner course. These days Python is the preferred language for beginner courses (which is covered in the second half), but if you want to baptize yourself in fire, you can jump into this course.

*Spam's Recommendation:* Take it. It's good!

### <a name="how-to-code"></a> How to Code 1 & 2
### <a name="plabc"></a> Programming Languages A, B, C
### <a name="lpn"></a> Learn Prolog Now!
### <a name="hpffp"></a> Haskell Programming from First Principles
### <a name="n2t"></a> Nand2Tetris 1,2
### <a name="intro-to-net"></a> Intro to Networking
### <a name="mit6001x"></a> Intro to CS and Programming with Python
### <a name="coretheory"></a> Algorithms (Tim Roughgarden)
### <a name="db"></a> Databases
### <a name="graphics"></a> Computer Graphics
### <a name="ml"></a> Machine Learning
### <a name="compilers"></a> Compilers
### <a name="debug"></a> Software Debugging
### <a name="test"></a> Software Testing
### <a name="softdesg"></a> Software Architecture & Design
### <a name="laff"></a> LAFF - On programming for Correctness
### <a name="cs344"></a> Intro to Parallel Programming
### <a name="scala"></a> Functional Programming in Scala (5 courses)
### <a name="softcons"></a> Software Construction 1, 2
### <a name="crypt"></a> Cryptography 1
### <a name="cs50-2"></a> CS50, second half, Final Project
OK, this was a total mess. (Apparently they improved it later with "tracks" you can follow, like Android, game, web, iOS.)

They throw so many languages at you all at once, and you don't really have time to learn any of them properly: Python, HTML, CSS, JavaScript, SQL, Jinja (templating language), the Flask web framework for Python, holy hell. I already knew some Python so all was well on that front. But the learning curve gets SUPER STEEP all of a sudden after 6 weeks of C. You'll want to go back to pointers, if you can believe it!

Assignments were pretty hard: text comparison, genetic edit distance (this is actually a pretty hard algorithm that you will learn later in Algorithms/Dynamic Programming), and some kind of location-map Web-App. Once again, that's how Harvard does it: HARD! They should call it HARDvard. (OK I'll let myself out.)

And the final project. They give you so much freedom, you can literally do anything. From archealogical image-reconstruction of artifacts with missing/destroyed pieces, to machine learning algorithms for emoji detection... There is an [Expo](https://expo.cs50.net/) for the projects. It's all very celebratory and encouraging actually. The freedom was both... freeing, and scary because of too many options.

I had some ideas. First I was going to make an interactive Game Theory web app where you can play the classic games like Prisoner's Dilemma etc. It was going to have adjustable AI opponents and everything. Then I found out that such sites already exist. Then I decided to make a math learning site with an interactive Python console inside it.

Here's my project: https://number-python.herokuapp.com/

Check out the source code of that page. It's beautiful! You'll never see HTML like that anywhere. I wrote it like I was writing nice, styled code in a proper programming language. In fact I wrote a Python script to generate the HTML code automatically from other text/script files. I FELT SUPER SMART! I automated stuff and nobody even taught me or told me to do it!

Looking for options on an interactive Python console that can be embedded in a web page, I came across [Brython](https://brython.info). Not only it provided an easily embeddable fully functional Python console that had way more modules of the Python library and performed much better than any other alternatives, it let me write Python DIRECTLY INSIDE HTML! Bye bye JavaScript! No need for Flask or any other web framework either. Just straight up HTML. Everything is client-side, no server/user-login involved. You can see the Python scripts in the source code. Brython is SUPER SLOW on initial load (has to translate a large chunk of Python standard library to JS), but once loaded, there are no page-reloads afterwards (all client-side) so the interaction is fast.

I had to figure out how to use LaTeX (mathematical typesetting) on a web page. The go-to "industry standard" is considered to be [MathJaX](https://www.mathjax.org/) but actually it performed so slowly that my CSS animations would trigger earlier than MathJaX could render math symbols, so they would not show up. Then I found a much better alternative called [KaTeX](https://katex.org/) which performs WAY faster and it worked.

I spent about 5 weeks making my project after "finishing" CS50. I'm pretty sure I spent one entire week on trying to figure out something called Window.getComputedStyle() to do a certain animation/user-input update thing. Only to learn later that the W3 School made a custom CSS template that does it automatically... I still have a list of improvements I was going to do. Like learn how to use HTML5 local storage so the next time a user visits the page, it remembers where they left off. I realized that I could keep spending MONTHS on polishing this project. So I decided to move on.

*Spam's recommendation:* Take it only if you like messy challenges. This is the world of web programming, where nothing makes sense, and code from multiple languages are meshed together in spaghetti and it's all raining on your head, meatballs and all.  I liked it but it was super frustrating. For me it replaces the Software Engineering Capstone Project which I did not take due to paywall.

### <a name="softproc"></a> Software Processes
### <a name="softarch"></a> Software Architecture
### <a name="haskell"></a> Functional Programming in Haskell
This was a 6-week "course" that cannot really be called a course. It was more like a few short tutorials put together. It was so insanely short I finished it in one day. I think the last "week" was just some interview videos with some academics. In week 6 they jump into Monads (which you won't understand), because for some reason FP people MUST mention Monads! What's cool is that it's from U of Glasgow, the creators of Haskell. You get to meet Simon Peyton Jones. They talk about the history of the ML-family of languages from the 70s and 80s. Well, I'm a sucker for computing history. If you have some spare time and interested in Functional Programming and its history, go for it.
