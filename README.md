# Spam's CS Corner: Course Reviews
DISCLAIMER: These are my subjective personal opinions! Make your own judgment. Also, I will talk a lot about my feelings! You are warned.

Originally written June 2020

- [Harvard's CS50 (first half)](#cs50)
- [CS50, second half, Final Project](#cs50-2)
- [How to Code 1 & 2](#how-to-code)
- [Software Construction 1, 2](#softcons)
- [Programming Languages A, B, C](#plabc)
- [Learn Prolog Now!](#lpn)
- [Udacity CS 212](#cs212)
- [Functional Programming in Haskell](#haskell)
- [Haskell Programming from First Principles](#hpffp)
- [Nand2Tetris 1,2](#n2t)
- [Intro to Networking](#intro-to-net)
- [Operating Systems: Three Easy Pieces](#ostep)
- [Hack the Kernel](https://github.com/ossu/computer-science/issues/690)
- [Intro to CS and Programming with Python](#mit6001x)
- [Algorithms (Tim Roughgarden)](#coretheory)
- [Databases](#db)
- [Computer Graphics](#graphics)
- [Machine Learning](#ml)
- [Cryptography 1](#crypt)
- [Software Processes](#softproc)
- [Software Architecture](#softarch)
- [Compilers](#compilers)
- [Software Debugging](#debug)
- [Software Testing](#test)
- [Software Architecture & Design](#softdesg)
- [LAFF - On programming for Correctness](#laff)
- [Intro to Parallel Programming](#cs344)
- [Functional Programming in Scala (5 courses)](#scala)

### <a name="cs50"></a> Harvard's CS50 (first half)
https://cs50.harvard.edu/

This was my first course. In 2018 this was the "intro/beginner" course on OSSU curriculum. For the sake of completeness I took the whole course at once, including the [second half](#cs50-2). It's pretty challenging for a beginner course, because that's how Harvard does it. In terms of college course numbering, they go with 050 but it feels more like a 200-level class.

The course uses an online IDE which was really top-notch in my opinion. IT HAS A DARK MODE! Instantly approved. They even invented their own version of the error messages of the C compiler that are more "beginner-friendly" and more helpful. They created a much more beginner-friendly version of a small section of the "man-pages" for C. They even teach you how to use "valgrind", a code analysis/memory leak detection tool that even some pros don't know or use.

For me the assignments were at the right level of difficulty. Even though it uses C and gets into low-level matters of memory management, it was not too difficult. Yes, initially pointer arithmetic and pointer logic is gonna drive you crazy, but you'll get used to it. Each assignment had 2 versions called "more" and "less", meaning more comfortable or less comfortable with programming. "More" versions are the more challenging ones if I remember correctly, which I preferred to do. Assignments were: Scratch (a visual, block-based programming language for kids), displaying some text, breaking ciphers, credit card number verification, image decoding, image resizing, dictionary word search and more. Really throws lots of different types of problems and file/data types at you! Very good variation for a beginner course.

The instructor David Malan is very energetic and fun, but I really disliked the 1.5 hour long lecture videos recorded in a lecture hall. The lectures were not very useful when doing the assignments. But they had separate much shorter "helper" videos for the assignments, done by the TAs; those were very helpful. The assignments had super long wordy text descriptions that I disliked. It was usually more difficult to decipher all that text than doing the assignments themselves.

I think it's a challenging beginner course. These days Python is the preferred language for beginner courses (which is covered in the second half), but if you want to baptize yourself in fire, you can jump into this course.

*Spam's Recommendation:* Take it. It's good!

### <a name="cs50-2"></a> CS50, second half, Final Project
https://cs50.harvard.edu/

OK, this was a total mess. (Apparently they improved it later with "tracks" you can follow, like Android, game, web, iOS.)

They throw so many languages at you all at once, and you don't really have time to learn any of them properly: Python, HTML, CSS, JavaScript, SQL, Jinja (templating language), the Flask web framework for Python, holy hell. I already knew some Python so all was well on that front. But the learning curve gets SUPER STEEP all of a sudden after 6 weeks of C. You'll want to go back to pointers, if you can believe it!

Assignments were pretty hard: text comparison, genetic edit distance (this is actually a pretty hard algorithm that you will learn later in Algorithms/Dynamic Programming), and some kind of location-map Web-App. Once again, that's how Harvard does it: HARD! They should call it HARDvard. (OK I'll let myself out.)

And the final project. They give you so much freedom, you can literally do anything. From archealogical image-reconstruction of artifacts with missing/destroyed pieces, to machine learning algorithms for emoji detection... There is an [Expo](https://expo.cs50.net/) for the projects. It's all very celebratory and encouraging actually. The freedom was both... freeing, and scary because of too many options.

I had some ideas. First I was going to make an interactive Game Theory web app where you can play the classic games like Prisoner's Dilemma etc. It was going to have adjustable AI opponents and everything. Then I found out that such sites already exist. Then I decided to make a math learning site with an interactive Python console inside it. (I know, those exist too.)

Here's my project: https://number-python.herokuapp.com/ (don't laugh at me please!)

Check out the source code of that page. It's beautiful! You'll never see HTML like that anywhere. I wrote it like I was writing nice, styled code in a proper programming language. In fact I wrote a Python script to generate the HTML code automatically from other text/script files. I FELT SUPER SMART! I automated stuff and nobody even taught me or told me to do it! This feeling ranks up there with finishing Nand2Tetris 2 and Algorithms.

Looking for options on an interactive Python console that can be embedded in a web page, I came across [Brython](https://brython.info). Not only it provided an easily embeddable fully functional Python console that had way more modules of the Python library and performed much better than any other alternatives, it let me write Python DIRECTLY INSIDE HTML! Bye bye JavaScript! No need for Flask or any other web framework either. Just straight up HTML. Everything is client-side, no server/user-login involved. You can see the Python scripts in the source code. Brython is SUPER SLOW on initial load (has to translate a large chunk of Python standard library to JS), but once loaded, there are no page-reloads afterwards (all client-side) so the interaction is fast.

I had to figure out how to use LaTeX (mathematical typesetting) on a web page. The go-to "industry standard" is considered to be [MathJaX](https://www.mathjax.org/) but actually it performed so slowly that my CSS animations would trigger earlier than MathJaX could render math symbols, so they would not show up. Then I found a much better alternative called [KaTeX](https://katex.org/) which performs WAY faster and it worked.

I spent about 5 weeks making my project after "finishing" CS50. I'm pretty sure I spent one entire week on trying to figure out something called Window.getComputedStyle() to do a certain animation/user-input update thing. Only to learn later that the W3 School made a custom CSS template that does it automatically... I still have a list of improvements I was going to do. Like learn how to use HTML5 local storage so the next time a user visits the page, it remembers where they left off. I realized that I could keep spending MONTHS on polishing this project. So I decided to move on.

*Spam's recommendation:* Take it only if you like messy challenges. This is the world of web programming, where nothing makes sense, and code from multiple languages are meshed together in spaghetti and it's all raining on your head, meatballs and all. I liked it but it was super frustrating. For me it replaces the [Software Engineering Capstone Project](https://www.edx.org/course/software-development-capstone-project-ubcx-softengprjx) which I did not take due to paywall.

### <a name="how-to-code"></a> How to Code 1 & 2
https://www.edx.org/course/how-to-code-simple-data
https://www.edx.org/course/how-to-code-complex-data

These courses are intended for absolute beginners but in OSSU they come after some other introductory courses. Personally I think it's more appropriate as someone's first course. Experienced functional programming teachers and academics claim that it's much easier for someone to learn FP if they were NOT exposed to programming before, but those used to procedural programming have a harder time.

With that pedagogical point out of the way, let's get to the courses. They are two courses but really intended as one big course. You will use Racket, a [Lisp](https://lisp-lang.org/)-like language. Lisp has been around since the late 1960s I believe. Brace yourself (get it?) for tons of [parentheses](https://xkcd.com/297/)! Seriously, A LOT of parentheses. Recursion is the name of the game here. You will recurse till the cows come home, then recurse some more on the cows.

You'll be using an IDE called Dr. Racket, which is really excellent in my opinion. Comes with the language, debugger, code editor etc. everything installed. It comes pre-installed with 3 levels of the Racket language: Beginner Student, Intermediate Student, Advanced Student (and eventually you can take off ALL the training wheels and use the full Racket language). The instructor is great. He sits down and writes code with you in many videos. (I don't know why more courses don't do this live coding with the instructor.) You will be doing tons of small coding exercises, and lots of multiple choice quizzes. These are fairly easy and good.

The instructor is a big proponent of [Aspect Oriented Programming](https://en.wikipedia.org/wiki/Aspect-oriented_programming) (he is even mentioned in the Wikipedia article). First course teaches you the function writing discipline: you first write the signature (input/output types) of the function, then you create a stub for it, then you write tests for the function, and only at the very end you write the function body. You can create blank templates for the function body by more-or-less copy-pasting the input data type, and putting question marks in some places. The data type determines the structure of the code for the function, which takes all the guesswork and confusion out of it. This is an excellent approach for FP beginners. 

Second course teaches you generic recipes and formulas for building bigger, more complex data types from basic data types. When combining types they are kept as orthogonal as possible to separate concerns and decrease coupling. These principles are simply built-in to later, more serious FP languages like Haskell and Scala, which have built-in type checkers, algebraic data types, and automated test generation (quickCheck and scalaCheck). 

First course ends with a Space Invaders type game (it's cool that Racket has a built-in way of doing events/mutation called "big bang"), while second course ends with a very difficult programming assignment of brute-force solving a scheduling problem for TAs and office hours. You will also write other fun and challenging stuff like a Sudoku solver, a fractal image generator, and so on. This is recursion on steroids basically. There are no auto-graders to which you submit your code. You'll be the judge of the correctness of your code. I'm told you can pay edX to have your final exam graded by the instructor, but don't do that.

*Spam's recommendation:* I am personally biased to like these courses. Definitely take if you are interested in pursuing functional programming. You can also take them as your first programming course. If you already had an introductory course, you can skip these and go to Programming Languages A, B, C. PLA uses ML to teach very similar functional programming principles, and PLB already uses the same language, Racket.

### <a name="softcons"></a> Software Construction 1, 2
https://www.edx.org/course/software-construction-data-abstraction
https://www.edx.org/course/software-construction-object-oriented-design

These were the follow-up courses to How to Code 1,2. They have NOTHING in common with HtC courses. They do not follow up on or build up on HtC in any way. Completely orthogonal. I think that was the intention actually. Functional versus OOP. The courses are supposed to have 2 instructors but we only see one. The other lady with the short hair appears only once or twice in an interview style video.

You download the code from their Github repo and open it up in IntelliJ. First few assignments are all about DRAWING. Yep, drawing UML diagrams of projects with already complete code. Afterwards you go through a few more example projects that you open up in IntelliJ and answer various questions about them on edX. There is almost no coding involved. These are just teaching some OOP concepts such as control flow, data abstraction, encapsulation and so on. There are some extra optional practice assignments, but it's very boring. I did them anyway. These are various apps like a Library membership management, a beauty salon appointment program, a Gym membership program, a Ferry service/schedule program, and so on. These are all very small, short projects that are quickly forgotten afterwards. There is one video that teaches you to use IntelliJ's debugger and find some really obvious, deliberate bug in the code, and fix it.

The first course finishes with a project that is a music playing GUI. You just need to write a few lines of code here and there marked as *TODO*. Almost all of the code is done for you already. You can't have it graded unless you pay.

The second course gets into more serious OOP concepts: class hierarchy, associations, sequence diagrams, robustness, design principles: Single Responsibility principle, Liskov Substitution principle, Coupling, Refactoring; Design Patterns: Observer/Observable, Composite pattern, Iterator Pattern; and some more practical things: HashMap in Java, overriding the Equals, Exception handling, Assertions. The format is the same. You download code, don't write much at all, read and answer multiple choice questions on edX.

The second course finishes with a project that is some sort of Twitter app. You need to complete code, using the Observer/Observable pattern. 

*Spam's recommendation:* I'd say skip them. They are OK courses I guess. You don't learn too much or do much coding at all. But they are a TOLERABLE way to get through Java concepts. Definitely preferable to Udacity's Software Architecture and Design, and Coursera's Software Architecture. These courses have actual code you can download and look at.

### <a name="plabc"></a> Programming Languages A, B, C
https://www.coursera.org/learn/programming-languages
https://www.coursera.org/learn/programming-languages-part-b
https://www.coursera.org/learn/programming-languages-part-c

I loved these courses! These were right around the time of my grandmother passing away, so they really helped me pick up my spirits afterwards. Dan Grossman (Mr. PhD in programming languages jumping up and down waving his arms LOL) is an awesome instructor! He is rated as a Top Instructor on Coursera (4.9/5.0). Congrats to him and his wife on their baby boy. Grossman explains very well why he chose ML, Racket and Ruby (and not Haskell or Java), why there are 3 courses, and why there is a strong bias towards functional languages (in Week 4 of first course), which was an awesome surprise for me; because I love them due to my math background but I thought that the world considered FP to be some useless academic thing.

The installation is a bit tricky. For the first course which uses the ML language, you have to install Emacs and an ML-plugin for it. I had a lot of trouble at this stage due to Emacs' weird plugin and internet connection system. If you've never used Emacs before and you are a typical Windows/Mac user, you're gonna take some time getting used to all the weird keyboard shortcuts. You can't even Ctrl+C like usual.

The classes are very much in a college-class format. You get lengthy lecture notes and assignments on PDF files. You gotta read a lot. I normally dislike that, but thankfully these notes were actually excellent. There are some severe restrictions though. You can submit your code, or your multiple choice "midterm exam" only once per 24 hours. I understand Grossman's reasoning: he wants you to really be thorough, and resist the urge to do blind-guessing. I disliked the midterm exams. Some of the multiple choice questions are very nitpicky and detailed. I still don't get some of them. At least there are PRACTICE midterm exams before you attempt the real thing. That was super useful.

First course uses ML (statically typed) to teach language features from functional programming. A lot of emphasis on ML's type checker, HOFs, lexical scope and closures, currying, immutability, pattern matching, parametric types, accumulators and tail recursion, and so on. PAs of the first course are about writing... functions. A LOT of functions. You might get sick and tired of writing functions, but keep at it, they are usually short.

Second one uses Racket (which is dynamically typed) to introduce more features, like thunking and laziness (delaying evaluation), mutation, and so on. It makes us write a language inside another language (Racket): really excellent programming assignment! If you took How to Code 1,2 this should be a piece of cake. If not, it's challenging but doable.

Third course uses Ruby to teach OOP concepts: classes, inheritance, multiple inheritance via Mixins, double dispatch, dynamic dispatch and so on. It has a PA writing a Tetris game using some graphical Ruby library. Then it has an excellent FP vs OOP compare and contrast PA where we write the same program in ML and in Ruby. It's about 5 kinds of geometric objects and their intersections, so there are 25 total combinations. In Ruby, committing fully to the OOP style (with Double Dispatch), this is done by creating 5 kinds of classes and writing 5 methods for each; whereas in ML we write one big function with 25 cases of pattern matching (functional decomposition). There is also the EXTREMELY IMPORTANT discussion of subtyping, covariance and contravariance (super important in Scala for me later!) It's also interesting to note that Ruby has some functional-style inspired features; in fact its creator Yukihiro Matsumoto said that Ruby was "a Lisp" at its core.

Each week there are some community contributed resources with extra problems. These are nice but I did not do them. In each course there are some optional videos touching upon languages not covered in the courses, such as Java, C or C++. This seems to be a point of confusion because some experience in Java/C/C++ is mentioned as a prerequisite, really scaring some people away. NO, THESE ARE OPTIONAL VIDEOS. Plus you can still understand them even if you never saw Java or C++.

The concepts I learned here carried me all the way through; in fact in my Scala Spec, the first two courses by Martin Odersky were pretty much a repetition of these three courses. I was able to do the PAs of my Scala Spec without even watching Odersky's lecture videos!

*Spam's recommendation:* Drop what you are doing and take these now! They will serve you forever.

### <a name="lpn"></a> Learn Prolog Now!
https://lpn.swi-prolog.org/lpnpage.php?pageid=top

This is a text-only course, like a textbook. There is a book version of it too I believe. No videos. There are some interactive widgets that can run Prolog code but they did not work well on my browser. I resorted to [this IDE for Windows](https://arbeitsplattform.bildung.hessen.de/fach/informatik/swiprolog/indexe.html). For Mac and Linux there is an Eclipse plugin.

If you are one of those people who like to debate programming languages and paradigms, well... The others are pretty similar, but the only "truly different" programming paradigm in my opinion is this one: Logic Programming. 

It is so very different! You have to think in a completely different way: Unification/Resolution and Backtracking. You have to think in terms of search trees. Even some simple exercises will trip you up. It helps that it has recursion, so it has some familiarity at least. Also you think of your programs as knowledge bases, to which you make queries: a bit database-like, declarative-style programming. So there is some familiarity there too if you used SQL. But I must admit that Logic programming is not suitable for most problems out there. It can also be computationally inefficient. It's super cool though.

Backtracking is used in some "normal" programs in "normal" (non-Logic) languages too. For example you will write a brute-force Sudoku puzzle solver in How to Code 2 and then also in Udacity's Software Testing. The straightforward, non-clever solution necessitates backtracking. Another example of backtracking in "normal" programs is maze solvers: https://github.com/mikepound/mazesolving and https://www.youtube.com/watch?v=rop0W4QDOUI

This was right up my alley, because the language works exactly like a [proof tree](https://www.quora.com/What-are-proof-trees) which I studied in Mathematical Logic. Oh and the Cut Rule! (to stop backtracking) goes back to Gentzen's 1934 [theorem](https://en.wikipedia.org/wiki/Cut-elimination_theorem).

Originally Prolog was created by a linguist, so the main application is studying Grammars. The course will teach you context free grammars and definite clause grammars. Very important stuff if you want to go into parsing and compilers! That will be your "final project". You'll probably never use Prolog in the real world. The website claims it is coded in Prolog though!

*Spam's recommendation:* Don't take it unless you have strong interest/background in Mathematical Logic/Proof Theory/Linguistics. If you like complex mathematical algorithmic thinking, the Logic Programming's way of thinking can be VERY USEFUL if you plan to go into an area of CS with hard problems. Study backtracking and search trees.

### <a name="cs212"></a> Udacity CS212
https://www.udacity.com/course/design-of-computer-programs--cs212

I took this course twice! Once a few years before I started OSSU. I had to quit because it was too hard and I was frustrated. It totally kicked my ass and wiped the floor with it. The second time around I finished it.

The instructor Peter Norvig is a super smart guy leading Google AI Research (at the time, 2013?). His teaching style is very tough and definitely not for most people. He leaves A LOT up to you: he wants you to think through the problem, go to Python website and look up functions from the Python library to solve the problem, and not only that, but to solve it in a clever, short, elegant way. The first time I took it, a lot of students were really butthurt in the forums complaining about him. Later the TAs added some extra exercises and videos to explain some of those high-level, functional Python features unfamiliar to beginners, such as the itertools module.

But the course is incredibly valuable. It teaches you so many "high-level" programming ideas and tricks. I first learned the concept of Refactoring and Memoization here. It's a purely problem-solving, puzzle-solving course, except for the language theory section in the middle. That part just didn't click even the second time around. You solve the famous Zebra puzzle, the Pouring problem (from Die Hard 3 with Bruce Willis and Samuel Jackson), create a Poker game, solve a parking problem, among others. Norvig teaches you the concepts from breadth-first-search and depth-first-search, the problem space, the "frontier", profiling, looking under the hood of program execution to optimize performance with memoization, and uses all kinds of functional programming tricks available in Python to make the code short and elegant.

The assignments are very hard. Definitely up there with Algorithms or even Advanced Programming. Interestingly years later my Scala Specialization mentioned the Pouring problem, and Martin Odersky offered a Scala solution after praising Norvig's Python solution as "elegant".

*Spam's recommendation:* Take it only if you want a real challenge, you're OK with a very indirect instructor, and you are self-driven to do your own searches through documentation and figure things out.

### <a name="haskell"></a> Functional Programming in Haskell
https://www.futurelearn.com/courses/functional-programming-haskell

This was a 6-week "course" that cannot really be called a course. It was more like a few short tutorials put together. It was so insanely short I finished it in one day. I think the last "week" was just some interview videos with some academics. In week 6 they jump into Monads (which you won't understand), because for some reason FP people MUST mention Monads! What's cool is that it's from U of Glasgow, the creators of Haskell. You get to meet Simon Peyton Jones. They talk about the history of the ML-family of languages from the 70s and 80s. They talk about Alonzo Church, Stephen Kleene and lambda calculus too. History is cool.

*Spam's recommendation:* Well, I'm a sucker for computing history. I also like Scottish nerds. If you have a few hours of spare time and interested in Functional Programming, its people and its history, go for it.

### <a name="hpffp"></a> Haskell Programming from First Principles
https://haskellbook.com/

I didn't pay for this. $59? Are you kidding? My Specialization cost me $0, please. In my country I can eat for a month with $59 (local currency crap against the mighty dollar). It's a great book though. Although SUPER SUPER SUPER LONG! Like 1900 pages. I think I quit at page 1700 or so, after Applicative. I already knew about and used Monads.

This was by far the most useful thing for my Specialization. Scala totally rips off Haskell in so many ways, even the syntax is mostly the same. Even though I never saw a single line of Scala code before starting my Spec, I flew through the Spec.

I love Haskell! It's awesome. Most of your time, like 95%, will be spent battling the type checker. It's like martial arts practice. I call it "Type-Kata". The type checker will reject almost all of your code. Just when you think you wisened up and grasped a concept, the type checker will throw it back in your face; and remind you of what a useless, incompetent piece of garbage you are. Go back to Python, you loser. But magically, if your code passes the type checker, it will be usually correct and bug-free. That's why they call it *The Venerable Glasgow Haskell Compiler*.

Did I already tell you that this is hard? It's very hard. The book is full of code though, and I typed all of it. It's too bad there wasn't a good Haskell IDE. I just used Notepad++ and WinGHCI. (However now in June 2020 there is a Haskell plugin for IntelliJ!) Many of the exercises are of the "guess the correct type" type. (Did I just make a pun?) It also takes you step by step through creating and compiling projects using Haskell's build tool, Stack. It's difficult to explain what you learn because it's so long and it's in book format.

*Spam's recommendation:* I'm gonna say... don't take it. Unless you are an evil pirate like me who is also into masochism. It's super duper hard anyways, and definitely not worth $59. It will take you a LONG TIME to get through this book. But if you do, your Functional Scala Specialization will be a total cakewalk.

You might try (I haven't) [Learn You a Haskell for a Great Good!](http://www.learnyouahaskell.com/) if you MUST learn Haskell. 

### <a name="n2t"></a> Nand2Tetris 1,2
https://www.nand2tetris.org/

This was my favorite course ever (until I took Algorithms)! It's purely project focused. No lectures or academic topics, no quizzes or exams. It probably won't be directly useful in real life, but the ideas/systems will indirectly help you understand computers at every level: machine instructions/assembly/VM/compilers.

It's quite tough and there is a huge gap of time and difficulty between parts 1 and 2. I can understand the instructors' decision to split it that way; they cut off the first course where the "hardware stuff" ends so that people who are only interested in those parts can take only the first course.

The goal is to build a 16-bit single-tasking toy computer (in a hardware simulator program) with 32K RAM called Hack that was designed by the instructors. It's actually 15-bit, the instruction set does not use one of the bits. Only 24K RAM is used.

Part 1 makes you build basic logic gates, then bigger chips, then eventually an ALU (Arithmetic Logic Unit), a CPU and RAM chips in a Hardware Simulator. You use a hardware description language to do this. (Editors like Sublime Text and VS Code have syntax highlighting plugins for this HDL.) Then you write some code that translates a simplified pseudo-assembly language (with only 3 registers) to 16-bit machine instructions specified by the instructors. The hardware simulator, and all the tests for your chips/assembler are provided.

Part 2 is much more software focused, longer and harder. You will write code that translates a Java Virtual Machine-like pseudo VM-code to assembly code. Then you will write a compiler for a made-up programming language called Jack, which looks like C and Java. Both of these are spread to multiple assignments. Lastly you write some code for the "OS" of your Hack computer, which is really just a Jack library providing some basic functions. Again, all the simulators and test cases are provided. Eventually you can run some complicated program on it, like Tetris or Pong. You can even write your own Jack program! I remember writing a guess-the-word game with a small dictionary loaded into memory.

I have nothing bad to say about this course, maybe except that in Part 2, towards the end, with the Hack OS, Jack-to-VM compiler and VM-to-assembly translator, things can get really difficult to debug. Thankfully the instructors thought of this and provided "reference solution" Hack OS modules, Jack compiler and VM translator. I found it easiest to just compare my output with correct output line by line. The instructors even provide a TextComparer application to do that!

These courses gave me the best feeling! I felt euphoric, like I just flashed through computing history and built everything from the ground up. I felt like I could do anything. Very empowering stuff. It's quite challenging but you get a taste of every level of programming, from low-level assembly all the way up to operating systems and applications.

*Spam's recommendation:* Take it if you want to get a whirlwind tour of all the layers of how a computer works from the chips on the ground level all the way up to software, and feel awesome doing it! 

### <a name="intro-to-net"></a> Intro to Networking

This course was a major bummer coming off the euphoria of Nand2Tetris. It's very long, technical, academic, hard and boring. It has no programming assignments. The two instructors (one of which keeps appearing with a different hair color every lecture) don't do a very good job of explaining things. Lecture videos are usually very long. This course is a HUGE, many metric tons of info dump on your brain.

The quizzes and exams are very difficult, the questions are hard to understand, very wordy and vague (like what students call "word problems" in math classes), and very difficult to get feedback on. I finished with a 79/100 overall on the course. The exams don't let you try again, also don't give feedback on right/wrong answers. The quizzes let you try as many times as you want, but sometimes I had to do blind-guessing for 100 different values just so I could see the correct answer explanation. They are not multiple-choice. These questions are so difficult, riddled with so many different unit conversions and constants, it's so hard to know even when I'm doing something right.

The best parts were those about Internet history, earlier versions of TCP and so on. There were also some routing algorithms like Dijkstra, Bellman-Ford which I relearned later in Algorithms. Those were good too. There were some interviews with what I assume important people in networking, but the video/audio quality is so poor on these it actually hurt my ears and eyes.

Each topic had so many nitty gritty technical details, it's impossible to keep them all in mind. Routing, queuing algorithms, IP addresses, NAT translations, DHCP systems, DNS systems, Encodings on the physical layer, all the different speeds in different versions of TCP, dozens of separate bits and parts of a typical TCP/IP package, security and cryptography, and so on... the textbook they chose (Kurose) is so long, wordy and unreadable I found it to be of no help. Before quizzes/exams I had to re-read PDF slides and try to make questions look like those on the slides.

*Spam's recommendation:* Skip this. There has to be a better, more practical way to learn Networking. I don't know what it is though. 

### <a name="ostep"></a> Operating Systems: Three Easy Pieces
http://pages.cs.wisc.edu/~remzi/OSTEP/

This is a textbook, not a course. But it became a course and replaced Hack the Kernel in OSSU. I read this whole book and did all of its exercises since HtK totally sucked and I could not understand anything.

The book is written in a very friendly, conversational style. It makes all kinds of otherwise difficult research papers very easy to understand. Also there is a lot of computing history, my favorite thing (I should just give up coding and become a historian honestly). The author really has a gift here. The chapters are usually very short. You can download code for the homework assignments at the end of each chapter. Many of these are simply reading text output that describes a situation (assembly code, multiple threads executing, disk I/O etc.) and answering questions about them. A few of the exercises require you to write some code.

You should definitely type the C code snippets in each chapter by hand. The book spends a few chapters to teach you the C thread API (which HtK DID NOT!). You can even compile and run these code snippets on your machine. The book covers A LOT of things. It's split into 3 easy pieces: Virtualization (deals with process API, system calls, kernel traps/context switches, Scheduling Theory and algorithms, virtual memory systems, paging), Concurrency (threads, synchronization primitives like semaphores, condition variables, locks) and Persistence (data/file/I/O systems). The book is long (600 pages) but very very easy to read. I finished the first two of the three easy pieces in about a month. 

Check the author's site as he is planning an update and some other excellent free books.

*Spam's recommendation:* Look no further. This is THE BOOK if you want to understand operating systems. It's OK if you don't.

### <a name="mit6001x"></a> Intro to CS and Programming with Python
https://www.edx.org/course/introduction-computer-science-mitx-6-00-1x-10

This is probably the best intro course ever. This is much gentler than CS50 as an intro class, obviously. I took it one year into my studies, because it got added to the OSSU curriculum one year after I started, and I wanted to do it for completeness. Obviously at that point I was not a beginner so this course was very easy and fun. I think it took me 6 days. It went so quickly because I was having so much fun.

Although it was easy for me, it's not so easy from a beginner's perspective. I found the challenge level to be just right. Even with 1 year experience, the assignments made me think for a bit. It packs a lot into just 6-7 weeks and finds a perfect balance of programming and computer science, theory and practice. Starts all the way from the beginning, basic data types, integers, strings, lists, dictionaries and so on... then includes bisection search, the big-O notation and runtime analysis of basic sorting algorithms, object-oriented programming, even data visualization. The instructor Eric Grimson is very nice. Friendly and good at explaining things. Python/Anaconda/Spyder are much nicer than the C Compiler.

Assignments were: finding a longest substring, a Hangman game, a dictionary word-guessing game (with an AI opponent!), decoding secret encoded messages, and some others. There is a timed Midterm exam and a timed Final exam. I think I was given 8 hours to finish them. I finished in less than 1 hour.

*Spam's recommendation:* TAKE THIS! BEST intro course ever. Ignore the other "intro/beginner" stuff like Py4e or Fundamentals of Computing (nothing against those courses).

### <a name="coretheory"></a> Algorithms (Tim Roughgarden)
https://www.coursera.org/learn/algorithms-divide-conquer
https://www.coursera.org/learn/algorithms-graphs-data-structures
https://www.coursera.org/learn/algorithms-greedy
https://www.coursera.org/learn/algorithms-npcomplete
https://algorithmsilluminated.org

These courses are right up there with Nand2Tetris and PLABC as THE BEST. I took this right after my 6 month long Hack the Kernel fiasco, and boy, what a soul-saver this was! Super awesome amazing courses. Filled with mathematical beauty that brings tears to my eyes. These are now listed as 4 courses but when I took them at Stanford's Lagunita platform there were 2 courses: the first 2 combined into 1, and the last 2 combined into 1. If you don't like Coursera you can go straight to Tim's page where there are links to videos and finish his textbooks.

You MUST get Tim's textbooks. Fourth book was not out when I was taking the classes, but it will be out this month! (June 2020) They are EXCELLENT, better than the videos. They are like the "...for Dummies" books of algorithms. Very accessible, clear, easy to read. I actually went through the books and came back to the videos only after having done all the exercises in the textbook.

First course is all about... divide and conquer. Starts right off the bat with a beauty: Merge Sort. As Tim likes to call it: "one of the greatest hits of CS". Later there is also Quicksort, more beautiful in my opinion. You will be amazed at randomized algorithms! It's mind blowing. How can randomness perform on par, or sometimes even better than determinism? Great stuff. The optional material is the hardest: the proof of correctness for Closest Pair Search, the runtime analyses of randomized Quicksort and Deterministic Linear Time Selection. You can skip these. The proofs are pretty hard. I enjoyed them a lot. 

Second course is all about... graphs. Probably the most important part for computer science. Almost everything is modeled with graphs. Some really amazing stuff here, like Breadth First Search, Depth First Search, Topological Sort, Dijkstra's algorithm, Kosaraju's algorithm for finding strongly connected components of directed graphs. This is when you start to get into Data Structures and everything starts to fall into place and become extremely beautiful. Heaps, hash tables, bloom filters, search trees (balanced or otherwise).

Third course is about greedy algorithms (which are sometimes INCORRECT), and dynamic programming (this is like induction on steroids). Here the arguments get pretty hard. It's not a simple matter of straight up induction. There are special cases to consider, and things can go wrong (no guarantee of correctness by design). Huffman Codes (which showed up again later in my Scala Spec. I was READY!), Knapsack, Sequence alignment (edit distance), Bellman-Ford, Floyd-Warshal, and Minimum Spanning Trees.

Kruskal's MST algorithm using Union-Find (also known as Disjoint-Set) data structure, with the added optimizations of Path Compression and Union by Ranks, and its brilliant running time analysis by Tarjan using the inverse Ackermann function, holds a special place in my heart. Never have I witnessed such mathematical beauty that moved me so emotionally, with tears in my eyes, since I learned the [Gauss-Bonnet Theorem](https://en.wikipedia.org/wiki/Gauss%E2%80%93Bonnet_theorem) in Differential Geometry years ago. It's like I'm part of Humanity's thousands of years of intellectual journey, standing at the pinnacle of incredible achievement, knowledge, truth and beauty.

Fourth course is the most theoretical. What to do when problems are just too hard? Where are the natural limitations of computer science? You learn the [million dollar question](https://en.wikipedia.org/wiki/Millennium_Prize_Problems): P versus NP. Some well-known "hard" (NP-complete) problems are discussed. Vertex cover, subset-sum, satisfiability, Traveling Salesman, and so on. You are forced to resort to Dynamic Programming (simply because there is nothing else to do; no known better/polynomial time algorithms).

I implemented ALL algorithms, ALL data structures, and did ALL the exercises in the textbooks (which happen to include the quizzes/midterms/finals/PAs of the online version). The "Challenge Data Sets" on Tim's website are INSANE! Graphs with millions of nodes and billions of edges and such! There is actually a really cool story here that led me to switch from Windows to Linux... I'll tell it later. Suffice it to say that the Traveling Salesman challenge problem made me run out of my 32GB of RAM! There are midterm/final exams with multiple choice questions. You can attempt them multiple times, and they give you nice feedback even when you get things wrong.

*Exact math requirements:* Course 1: You need the big-O notation, but this is one of the first chapters of the textbook. You must know the Principle of Mathematical Induction. Exercise writing some proofs by induction. Then OPTIONALLY, you need to do a [discrete probability analysis](https://en.wikibooks.org/wiki/High_School_Mathematics_Extensions/Discrete_Probability) using some random variables that are independent, exploiting the Linearity of Expectation. Both of these are covered in the Appendices of Roughgarden's textbook and you don't need to take a math course.

Course 2: Same regarding induction. Graph theory is covered in the course/textbook. Having taken Math for CS would be nice but it's not necessary. I actually prefer Roughgarden's graph coverage to that other free [textbook](https://courses.csail.mit.edu/6.042/spring18/mcs.pdf) that Roughgarden links to on his webpage. Lots of graph theoretic proofs (no new math, just solid logic and reasoning skills). There is one probabilistic analysis in Karger's Min Cut algorithm (same requirement as Course 1).

Courses 3, 4: Nothing new math-wise. Lots of induction as usual. Logic and Reasoning are the most difficult parts really.

*Spam's recommendation:* Stop reading right now and TAKE THEM! Also get Roughgarden's books. Worth every cent, will serve you FOR LIFE. They are EXCELLENT (and self-contained; they will take care of your math fears).

### <a name="db"></a> Databases

Yet another Stanford course that disappeared with the shutdown of Lagunita. I took it RIGHT BEFORE the shutdown. These are 14 mini-courses on... so many database topics. I'll be honest I can't remember some of them. These are very dull, boring, technical and there are A LOT of exercises. It's not the instructor's (Jennifer Widom) fault though. I actually liked her, she was pretty good, but the subject itself is very boring and difficult to make interesting without a more applied context.

The database world is a mess. So many different query languages and syntaxes out there. RA, XPath, XSLT, XML, XQuery, SQL, JSON, OLAP... oh my god. The software side of these was also a huge pain. Had to install so many different programs buried out there on the internet. [DBeaver](https://dbeaver.io/) is free and really excellent to run SQLite, MySQL, PostGreSQL on your local machine (although you'll have to track down some Stackoverflow answers to get them installed and working properly on your PC). I also had to install [BaseX](https://basex.org/) and [Saxon](https://www.saxonica.com/welcome/welcome.xml). Processing RA was even more difficult, I think I just used an online tool for that. There were also some tools pre-installed on my Linux system to process some of the other formats.

You will write TONS AND TONS of queries. Some of the exercises are quite hard! Mainly because query languages are so unexpressive, while these hard exercises are asking you to do things in a more general-purpose-programming-language kind of way. You will get sick and tired of these queries. Some of the mini-courses don't have any exercises, they are just over in half an hour or so. Some courses have multiple choice quizzes and exams. They let you try as many times as you want.

*Spam's recommendation:* Don't take it, even when it comes back online on edX. I think this (writing hundreds and thousands of query exercises) is the wrong way to learn databases. Learn it by doing a web project instead.

### <a name="graphics"></a> Computer Graphics
https://www.edx.org/course/computer-graphics-uc-san-diegox-cse167x

This course was very uneven. Some great parts and some awful parts. Also at the time I took it, edX started doing this thing: they cut off your audit access in one month. So I was under a lot of time pressure to finish this quickly, but it's actually tough material. Also you can't submit assignments for grading unless you pay (I didn't, even students who paid were complaining about their experiences with the grader). The discussion boards under the videos were full of unanswered questions from frustrated students. It's possible to test with the provided code but it requires some eye-balling: you have to stare at your images versus the reference solution images.

The instructor Ravi Ravamoorthi is a brilliant and leading man in his field. He is extremely well spoken and good at explaining things. Despite all that, the videos were long, boring and not very helpful. He has many, many videos where he goes over the OpenGL code for the second assignment which is nice, but they were so long and numerous I could not concentrate after a while (especially with the time pressure from edX). For assignments I would have to go back to the PDF slides. 

When he goes through some calculations, he is using a screen-marker pen that must be from the late 1990s, it has no smoothing whatsoever that everything he writes is completely angular/straight, it looks like it was written by a 3 year old. Impossible to read or follow, and very annoying. In later videos, he quickly cuts to the version of the formula that was typeset instead of handwritten.

The OpenGL pipeline is pretty complicated. There are so many parts, and I had real trouble understanding how and in what order they are connected. The math is really involved. I found it possible, but really hard, to follow the derivations of the formulas for various calculations: shading, lighting, tracing, projection, rotation, transformation, dilation, translation, and so on... For the assignments I just ignored the derivations and extracted the final formulas from the PDF slides without really understanding them fully. You really need to be on point with your vector and matrix algebra (dot/cross products, normalization, matrix-vector and matrix-matrix multiplications), but you don't need to take an entire linear algebra or an entire multivariable calculus class.

The first assignment was very nice and doable. You write a bunch of matrices to do transformations. Then you test this on a provided OpenGL rendering of a 3D teapot. The testing tool provided is interactive, you can rotate, dilate, shift the teapot. If you did everything correctly, the testing script will output a bunch of snapshots of the teapot after a particular sequence of transformations, and you can compare them to the correct results. However sometimes your solution is wrong and it's extremely subtle. You can't tell what's wrong from the interactive tool.

In the second assignment you actually have to write the code that renders the 3D teapot. The geometry is provided but you have to implement all the lighting, shading, coloring etc. This was quite challenging but still doable. You have to work across a lot of different files. Read a TON of different formulas from the slides and put the calculations in the right files in the right places. In addition to C++ you have to learn to use GLSL: GL Shading Language. Thankfully Assignment 0 has working, correct sample code that you can look at and imitate. 

They did something extremely useful: they provided guides from students who successfully completed the assignments. These were FAR MORE USEFUL than the assignment texts themselves.

The third assignment was getting close to Hack the Kernel levels of difficulty. It says: "hey, go ahead and implement a Ray Tracer!" OK... No skeleton code is provided. There is a long, wordy specification and some links to sites with "ray tracer design ideas". OK... Also you can't use the OpenGL libraries from previous assignments, because the ray tracer is software rendering only, it doesn't use any hardware acceleration. OK... Also, rendering each of the test images with your ray tracer will take hours of CPU intensive computing. OK... 

I took a look at some people's solutions and holy hell... it would have taken me at least 1-2 months to do that. Here the TAs joke that with each assignment the time required to finish goes up exponentially. They had a formula, something like (n+1)^n hours: Assignment 0 would take 1 hour, Assignment 1 would take 2, Assignment 2 would take 3^2 = 9 and Assignment 3 takes 4^3 = 64 hours. With the added time pressure from edX and no way to even test things I gave up (thanks to HtK I was beginning to master *The Art of Moving On*).

*Spam's recommendation:* Do the first two assignments, if you really want to learn computer graphics and OpenGL. Otherwise skip it. 

### <a name="ml"></a> Machine Learning
https://www.coursera.org/learn/machine-learning

Wow, this was one of the BEST courses I took ever. So good. The instructor Andrew Ng is, if I understand correctly, the founder of Coursera. His teaching style is great. The material, PDFs/notes, videos, assignments are all top-notch quality. They also introduced me to the awesome free MATLAB alternative, GNU Octave, which I ended up using later again for another course.

You learn a lot in this course (single/multivariate linear regression, gradient descent algorithms, neural networks, supervised/unsupervised learning, PCA: principle component analysis, K-means clustering), and it's all applied immediately with the programming assignments. Moreover what you learn is directly applicable in the real world. You can take these "basics of ML" and immediately start solving problems with them! The PAs are very cool: handwritten digit recognition, spam filtering, predicting housing prices, OCR detection, movie recommendations, and so on. You also learn how to analyze a poorly performing ML algorithm, how to diagnose its problems, how to play around with parameters and so on.

The mathematics/linear algebra is complicated, the formulas look super dense, and admittedly this course (and ML in general) has a SERIOUS notation problem. Subscripts, superscripts, all over the place. The formulas are very difficult to read sometimes. But surprisingly you actually don't need to know any partial derivatives or serious linear algebra! The instructor explains everything clearly. 

You also need to spend some time thinking about how to correctly "vectorize" the formulas. Quite often in contrast to how they are presented in the slides, you have to switch rows and columns of matrices when you write the code. Thankfully the PA instructions make that very clear. There are some multiple choice quizzes/exams but they are very forgiving and you can attempt many times.

*Spam's recommendation:* What are you waiting for? TAKE IT RIGHT NOW!


### <a name="crypt"></a> Cryptography 1
https://www.coursera.org/learn/crypto/

Wow this course was HARD. The easiest part for me was close to the ending where it goes into number theory (Euler phi function and such). The last part of the course is just setting you up for Cryptography 2, the next course, which I'm told is even more math heavy. Yikes! 

Even though I used to teach math, this course was really hard for me because the math is done in a non-rigorous, hand-wavy fashion. All the definitions (of semantic security, security against existential forgery, and MANY other definitions) are in terms of "negligible" probabilities; so you never make precise probability calculations; instead you say "well this looks negligible, so that will be negligible" and so on. It was so vague and hand-wavy I could not deal with it. It relies on "intuitive probability" but I just don't get probability; I'm impossible to build intuition for it.

There is a companion textbook but it is so wordy it's unreadable. The explanations in the videos are somewhat quick and gloss over the details so they went over my head. To do the exams I had to keep re-reading the PDF slides many many times over and over. You can attempt the exams only 3 times within 24 hours, and each time some questions are randomized/changed. THAT SUCKS. Really hard questions too: in some questions there are 6-8 check-boxes, all optional, and you have to get ALL of them right (all the ones that should be checked should be checked, and all the ones that should not be checked should not be checked).

You learn the design of many, many, many ciphers. It's super technical and complicated and hard to understand and sometimes boring. You learn about secrecy, integrity, authenticity, and other cryptography concepts.

This course scared me so much that I am afraid of using any cryptography at all. No matter what you do it's always unsafe :( The course says not to DESIGN your own ciphers, and not to IMPLEMENT your own ciphers based on someone else's design, and to be CAREFUL when using well researched, publicly implemented/tested ciphers, but it's so scary it makes you not wanna USE ANY ciphers ever at all.

What I said above sounds all very negative, but it's a good course and the instructor Dan Boneh is also really good (he was a "guest interview" in Intro to Networking too!) There are some optional but really cool and hard programming exercises (which I did in Python) where you break ciphers, implement some well-known attacks against websites, and so on. For one of the assignments they even created a fake Stanford website that you can attack. Super cool!

*Spam's recommendation:* Probably don't take it. This is a very hard MATH course, it doesn't really teach you about security (well it does, but in painful mathematical technical detail, not in simple practical terms).

### <a name="softproc"></a> Software Processes
https://www.coursera.org/learn/software-processes

I decided to take this when I realized [Software Engineering: Introduction](https://www.edx.org/course/software-engineering-introduction-ubcx-softeng1x) is behind a paywall. That course was talking about Software Methodologies, so I took this.

Another verbal course. You learn about all the cool catchphrases, ahem, I mean, Software Methodologies used in software companies. Waterfall, Agile, Kanban, Sashimi, Iterative model, Incremental Model, Requirements, Specification, Reusability, Extensibility, Coupling, Cohesion, Modularity, Encapsulation, Information Hiding, and sooooo many other words. There are some practice quizzes, and some "real" quizzes behind a paywall. There are some "scenario" questions where you are given a client and their needs, and you must write an essay describing the best approach to solve their software problems.

*Spam's recommendation:* Skip it. The exams are behind a paywall anyway. You can see the questions though. Just can't submit.

### <a name="softarch"></a> Software Architecture
https://www.coursera.org/learn/software-architecture

I decided to take this when I realized [Software Engineering: Introduction](https://www.edx.org/course/software-engineering-introduction-ubcx-softeng1x) is behind a paywall. I didn't know at the time but this is a repetition of Software Construction 1 and Udacity's Software Architecture and Design.

Another verbal course. It's like a literature class. [Words, words, words, words, words.](https://www.youtube.com/watch?v=-lqqDvWF45w) They hired a nice young acting or dance major student to read all the text from the teleprompter for a nice presentation. She clearly has no idea what she is saying.


This one makes you draw a ton of UML diagrams using some online tools. There are more than 20 kinds of UML diagrams. Oh my god. I felt like I was taking an art class. I made my diagrams [reeeeaaal pretty](https://d3c33hcgiwev3.cloudfront.net/imageAssetProxy.v1/_4b8b74b11840729713d3725d2f08b997_Deployment_Diagram.png?expiry=1591920000000&hmac=8NXnWXX6LIzSv2I1Aagnm-ZBo7VoAQ4ToxpYKAYT6GY). It was kinda fun. These assignments are all peer-graded. You get a free certificate at the end, no paywall, but it's pretty meaningless. 

*Spam's recommendation:* What do you think? Skip it, of course.

### <a name="compilers"></a> Compilers
https://www.udacity.com/course/compilers-theory-and-practice--ud168

I took this because the Stanford compilers course became unavailable. This course has no programming assignments, only quizzes, which was OK by me. I'm interested in compilers but not that much. It's a pretty good course. Especially the beginning parts that teach you the theory of regular expressions and deterministic/non-deterministic finite state machines, and later, context-free grammars. It's a very long course, and it gets super complicated towards the end when you deal with low-level code generation matters. However the problems involved are extremely interesting and hard, like parse trees, register allocation and control flow graphs. This is some truly hardcore Computer Science. I will learn these properly WHEN I GROW UP!

*Spam's recommendation:* Don't take it. What are you, crazy? Crazy enough to write a compiler? Nobody is gonna write a compiler in their career. If you want to write an ad-hoc "fun compiler" take Nand2Tetris Part 2 instead. You'll probably have to write a simple lexer/parser at some point in your life though, so you can take the beginning part (regex, FSMs, parsing).

### <a name="debug"></a> Software Debugging
https://www.udacity.com/course/software-debugging--cs259

Short sweet course. The instructor is great! Very funny guy with lots of interesting stories. For example, did you know that a software bug killed people by giving them an overdose of radiation? He is some sort of debugging expert and wrote books on the subject. I was surprised that [DDD](https://savannah.gnu.org/projects/ddd) that I have installed on my PC came from him and one of his PhD students years ago! There are also some other pioneers in [automated bug fixing](https://web.eecs.umich.edu/~weimerw/) (he taught [CS262](https://www.udacity.com/course/programming-languages--cs262)).

Instead of using a debugger from an IDE or something like that, in this course you write your own very primitive text-based debugger. In one programming assignment you create an Invariant checker: it tracks the seen values of a variable, and creates assertions about that variable: what values should it be in between to assure correctness? In another PA you are given a large input, a mystery test function, and tasked to find the minimum portion of the input that fails. In another PA you write a tracer: a program that traces the execution of another program. Another PA records function calls. There are even ways to numerically quantify the likelihood of lines in code being the cause of failure, using [Phi Coefficients](https://en.wikipedia.org/wiki/Phi_coefficient) from statistics. Insane!

In the final exam you put everything together to create a fully automated bug finder and reasoner that explains the bug, tracking the chain of causation in the code. Really cool, advanced debugging ideas. Particularly interesting one is the idea of [Delta Debugging](https://en.wikipedia.org/wiki/Delta_debugging): taking a test input that breaks down code, systematically narrowing down code to its minimum portion that breaks. Unfortunately it never feels like you can use them in the real world beyond the scope of the simple functions that are tested in these assignments.

It's an old course so it uses Python2. I usually translate them to Python3 and do them that way on my PC, but this time it was impossible, because Python's sys.settrace API (used throughout the whole course) changed significantly from 2 to 3. So the 2to3 conversion tool fails to produce a working version.

Some of the programming assignments have issues and bugs. There are some notes below the PA videos, telling you "this code is wrong/has a typo, it should be like this", but those are also wrong. The grader seems to be broken on many occasions. I spent some time reading old forum posts to figure things out. Another issue is that most PAs use the same "buggy code example" from the beginning of the course, over and over again.

*Spam's recommendation:* You can't really lose much by taking it. It's pretty short. It has some very cool ideas I haven't seen elsewhere. The instructor claims they are adopted by industry. Too bad it doesn't really deliver on its promise.

### <a name="test"></a> Software Testing
https://www.udacity.com/course/software-testing--cs258

Another short sweet course. Very closely tied to Software Debugging. 

It starts with Assertions. You try to find the bug in some functions with the minimum number of inputs and calls. A programming assignment makes you do the same thing, but on a buggy Mystery function that you do not see the code of. You are required to catch all the bugs by making some assertions fail, in as few calls as possible.

Then is the concept of coverage. You do some exercises on provided code to achieve 100% statement coverage ("hitting" all the statements in a code with tests) with as few tests as possible. There is also the concept of parameter value coverage: "hitting" all the values in the valid input range. There is the concept of Regression testing (a particular sequence of inputs/function calls that cause a certain result).

The rest of the course is dedicated to Random Testing, the instructor's favorite. You will write "fuzzers" that take known good input and randomly but slightly change it, and hold the code against a battery of such fuzzed input to try to find bugs. Apparently a guy named Charlie Miller found security vulnerabilities in Adobe software by running 5 lines of Python code for a few days on his computer. I did it against a bunch of PDF readers on my PC. 

Random Testing is difficult to create programming assignments for, which can be checked for correctness by a grader, so there are some seemingly irrelevant programming assignments here: implementing a credit card number checking algorithm, making a Sudoku puzzle checker (for validity), and a Sudoku solver.

In the final exam you write a fuzzer/random tester for a text viewing application and a given program, achieve full statement and parameter value coverage for strings, integers and Booleans on a given program, write a Regression tester for a program and finding all the bugs. Very easily done and fun.

*Spam's recommendation:* You can't really lose much by taking it. It's pretty short. Really cool ideas.

### <a name="softdesg"></a> Software Architecture & Design
https://www.udacity.com/course/software-architecture-design--ud821

This was quite atrocious. There are 30 Lessons, each with 25-50 videos. Some videos are so short (like 3 seconds) it gets incredibly annoying to go through the in-between-video transitions.

You basically listen to a nice old man read tons and tons of definitions from some Software Architecture textbook. He constantly shifts his eyes between the camera and the teleprompter from which he is reading. It's one of THOSE courses where they try to sound super cool and talk about famous architects, buildings, designers etc.

This is a purely verbal course. No programming assignments, no homeworks, just a few quizzes here and there that are very, very subjective. It's like a super long, boring audio-book to put you to sleep. Maybe you can figure out a way to listen to it when you are jogging or something. Personally I had the videos playing in the background while I was doing pushups and ab crunches in front of my PC.

There was only one useful Lesson. It's a scenario where two instructors are play-acting as software engineer and client. They go through the design decisions of a Library ID card/book checkout system. It actually made a lot of sense. This was extremely similar to what they did in the Software Construction 1,2 classes.

*Spam's recommendation:* Do not take this. Total waste of time. You can listen to [The Joy of Painting with Bob Ross](https://www.youtube.com/user/BobRossInc) if you have trouble going to sleep.

### <a name="laff"></a> LAFF - On programming for Correctness
https://www.edx.org/course/laff-on-programming-for-correctness

This course was very strange and uneven. It has 6 weeks. Week 1 is purely dedicated to Logic review, which I skipped. Week 2 starts with the core idea of the course: obtaining correctness proofs of algorithms along with deriving the algorithm itself, hand in hand. 

It starts with the concepts of weakest-precondition that must hold true before a code statement, and a post-condition that must hold after the code statement is executed. First formulas for these conditions for simple assignment statements are derived. Then conditions for simple if-else-statements. In Week 3 invariant conditions for while-loops are discussed. Each one of these code statements come with a theorem for its weakest-precondition and post-condition. This is all very mathematical, technical, notation-heavy and complicated. It was easy for me due to my logic background.

There is an interview with Anthony Soare (inventor of QuickSort!), which was super cool. Apparently the instructor was next-door office buddies with Edsger Dijkstra himself, who came up with the correctness proof ideas in the 1970s. There is also an interview with one David Gries, who apparently has been a proponent of these ideas for decades. He said "I opposed testing all my life" or something along those lines, which was really SHOCKING to me! Of course he does have a point: if you have correctness proof of an algorithm, no need for testing. 

Then they speak in very sad tones how their ideas were not picked up by the mainstream, by software companies etc. Apparently Microsoft Research created a language called DAFNY, which has the weakest-preconditions, post-conditions, and loop invariants built into the language; so an algorithm that you write can prove its own correctness. But... I've never heard it being used anywhere. It's difficult to find resources to learn Dafny even on Microsoft's own sites. It's like buried or abandoned. Another language that incorporates similar ideas is EIFFEL (with "contracts"). Scala has "require" statements. Many languages have assertions that we are all familiar with (not quite the same as weakest-preconditions or post-conditions though).

Then after Week 3, it gets weird. Weeks 4-5-6 go completely into matrix-vector and matrix-matrix multiplication algorithms. We derive 10, 15, sometimes 20 different versions of the SAME algorithm that does the same job. The emphasis on correctness proofs, weakest preconditions, and postconditions is gone by this point. Moreover, all the assignments are optional: they all have a button called "DONE/SKIP" so you can skip the entire course. I did just a few of the algorithms and skipped the rest. Insanely boring and repetitive. The instructor even jokes many times: "see how many hundreds of homework exercises I can generate from this?"

We don't even write the code. The instructors have a webpage that automatically generates MATLAB code based on certain parameters and invariants of the 20 versions of the same matrix algorithm: going row-by-row, going column-by-column, going top-to-bottom, bottom-to-top, left-to-right, right-to-left, diagonally, dividing the matrix into quadrants... OH MY GOD. The course comes with access to MATLAB's online IDE. I used GNU Octave on my local PC instead, as a free alternative (but I had to alter the code somewhat). We just copy-paste it into the relevant part in the homework. They also have automatically generated LaTeX code which gives the outline of the algorithm.

Then the course goes into research papers, PhD dissertations, discussion of low-level implementations of high-performance linear algebra libraries (BLAS)... It is super technical, very narrowly focused, very inappropriate for a general programmer due to its exposure of brand new, sometimes non-peer-reviewed research, and is of no general interest. Also the course videos and materials are of somewhat low quality.

Correctness proofs are provided only for EXTREMELY SIMPLE algorithms that all follow exactly the same structure: an initialization stage, a while-loop, then a finalization stage. Anything more complicated than that, like algorithms we have in the real world, are not discussed. The initial ideas are great though! I wish this was a course on Dafny programming, or how to incorporate more assertions/"require" statements/correctness proofs into more mainstream languages...

*Spam's recommendation:* DO NOT take this course! Don't do it. Hey! I said don't. It's "optional" and of interest to researchers and specialists. No use to you.

### <a name="cs344"></a> Intro to Parallel Programming
https://classroom.udacity.com/courses/cs344

This was the hardest course after Hack the Kernel. But it's insanely cool! However, this course is deprecated/abandoned by Udacity (you can't find it in their course catalog, only accessible if you know a certain URL). So there is no support at all. The auto-graders do not work (the GPU time-sharing they rented from Amazon no longer exists, the back-end of the grader is GONE). The forums are dead (I searched through thousands of posts to find anything relevant at all). The other MANY ways I tried to run the code on my machine did not work either:

- using Nvidia CUDA Toolkit (requires an Nvidia GPU but I had AMD),
- using Coriander (an open source project that converts CUDA to OpenCL to run on AMD cards),
- using Ocelot (something similar),
- tricking your computer into believing that your CPU is a GPU by installing certain specific OpenCL drivers,
- following Udacity's own instructions to install Hydrazine (the code is abandoned/unmaintained by Google, and the links are dead),
- a few other things I can't remember.

Oh I forgot to mention: my GPU died right at the beginning of this course! Ironic, isn't it? But I had a SPARE GPU! Ha ha, take that, irony! I win.

Finally some nice folks on GitHub created a Google Colab page (uses Google's GPU sharing) with the homework assignments, but the final exam is missing. They wrapped the C++/CUDA code in some iPython notebooks with correct reference solutions that automatically compiles and checks your code against it. But it's impossible to debug or know what you are doing wrong... so I was forced to peek at a few solutions on Github.

The course is about CUDA programming specifically on the GPU. Lots of GPU-only special considerations are given, like warp size, cache size, local/shared/global memory and their speeds, and so on. (These were completely absent from the Parallel Programming course in Scala Specialization.) The homework assignments are written by folks at Nvidia. It's very rare for a course to be so closely integrated with industry. And these assignments were much, much, much better than those in Hack the Kernel, even though they both deal with low-levels, memory allocation, pointers etc. It's my theory that industry people are better at creating learning content than academics.

You will go through the fundamentals of parallel programming and learn/implement all the fundamental parallel algorithms: scan, reduce, histogram, sort, and so on. The assignments are super cool and interesting (assuming you can DO THEM AT ALL!): image blurring, turning an image to B&W, red eye reduction, normalizing an image's shininess (so that super bright/super dark portions become more visible), Radix Sort, Jacobi filtering. I could not even do the final exam's PAs, I assume they were even harder.

The instructors are great! I always thought Udacity is the only one that got online pedagogy right: short videos, tons of questions in between videos (fully utilizing the Socratic method) instead of long boring info dumps. However the TAs totally suck. The TAs are tasked with the "explain HW assignment" videos, and they do a terrible job. There is one main instructor and some guests from Nvidia. They got the main CUDA guy from Nvidia to give a guest lecture (without any assignments) on the CUDA programming model. Don't worry, it does NOT play like a commercial. The only issue is that the main instructor gets into super nitty gritty details of optimizing parallel code for the GPU... while it's cool to have industry people teaching you the tricks directly, these felt like totally out of reach unless you work in the industry yourself. There is a "Histogramming for Speed" programming assignment but the optimizations you can do there are very simple and limited.

*Spam's recommendation:* DO NOT TAKE THIS! It's abandonware. Super hard, and you are all alone. You'll go through so much pain and frustration. It's too bad though, because it's actually an awesome course. Very sad, and shame on Udacity.

### <a name="scala"></a> Functional Programming in Scala (5 courses)
https://www.coursera.org/specializations/scala

OK, this was the big finish line. From the very first day I came across OSSU I've been eyeing this as my eventual goal. I set my browser homepage to the Scala wikipedia page for two years, so I would see it everyday and stay motivated. I had never seen a single line of Scala code up to this point. 

Unfortunately I came across too many outside issues during this. I was under quarantine, and starting to lose my mind from boredom and mild depression. Then to top it all off my PC broke (motherboard's CPU socket burned). I had to take a break in the middle of my Spec to go out looking for hardware in a pandemic taking some huge risk and being super bummed and depressed. Once I was able to continue I wasn't motivated or overjoyed as I have been dreaming for a year. Adjust your expectations people!

Initially I planned to pay for this, to make it more "official" and "serious", and to have something to show off at the end (I don't care for certificates otherwise, useless piece of paper). But that added a huge time constraint: $49/month. There is a 7-day free trial, so that would give me 37 days to finish the whole thing while paying the bare minimum (I don't think I could finish in 7 days! Even if I did I still have to pay $49 to get the certificate). At this point I was unaware that the whole thing is freely auditable. Good thing I decided not to pay later, because my PC broke halfway, so 1-2 weeks would have been wasted! Apparently Coursera allows you to "freeze" and stop your subscription, take some time off and come back, re-start your subscription and finish it later; but this is very iffy and precarious (just like their "financial aid" where they make you jump through so many hoops and stall you for weeks/months).

To reduce time, I did some quick reading before starting. There is an excellent write-up of lecture notes and lecture code snippets of the first 3 courses [here](https://github.com/xiaoyunyang/coursera-scala-specialization). When I started typing the code in IntelliJ, I noticed that it was all outdated and incompatible!!! What the hell? Then I started to read some reviews on Coursera. While the overall rating is high, there were MANY negative reviews, complaining about compatibility issues and the code/PAs not working on their PC. There seems to be a lot of confusion especially from people thinking that this is a "beginner's FP course" or something like that (even though it says "one year programming experience required"). Some commenters said "I'm a programmer of 20 years and these courses were impossible! Makes no sense! Terrible!" I started to have some Hack the Kernel flashbacks.

At this point I decided to test it by auditing the first course, skipping all the lectures and doing the PAs right away. Initially I had a ton of trouble getting things to work correctly in IntelliJ. There are some really finicky settings that have to be just right. Also the correct version of sbt (Scala build tool), correct version of Java (oh man this was hell, good luck reading Java's 100s of lines of stack trace error messages!) and correct version of the Scala compiler have to be present. Eventually things started working. Without watching any lecture videos I finished the first 4 weeks of PAs in 1 day. But the code was working fine. So I could keep going.

So why did the code I typed initially not work? Apparently all the versions of Scala and sbt are backwards-incompatible; the courses are 5 years old and many of the language features used in the courses are now deprecated! Apparently there is going to be some "big changes" in the Scala world soon, with [Scala Native](https://www.scala-native.org/) slowly being transitioned, ditching the JVM backend in favor of LLVM (that is awesome! but why didn't they do that from the beginning years ago?), and Scala 3 slowly being transitioned: just like Python 3 which made big, permanent, backwards incompatible changes, but from that point forward it's all compatible. The instructors changed part of the second course right when I was about to take it, and they claim they will "update the courses to Scala 3 at some point" [here](https://www.scala-lang.org/2020/03/10/functional-program-re-design.html).

Then more sad news about the hype around Scala [dying](https://www.lihaoyi.com/post/TheDeathofHypeWhatsNextforScala.html), companies like Twitter starting to move away from Scala, and so on. Why are these things important, and what do they have to do with the courses I'm supposed to be reviewing? Well, if you are going to seriously get into a language and look for a job in that language, these things are important. At this point I was even reconsidering my Spec choice. Maybe I should do something else? I decided to stick with it but not pay.

Overall the Specialization was very, very easy (at least 20x easier than Hack the Kernel). I don't know if that was because I'm so good, or because FP is right up my alley, or because of the power of FP itself. Most PAs took me 10-15 minutes to finish. Lots of clever functional one-liners! The wonderful thing is that, once you satisfy the type-checker, you almost always get the right solution. You MUST use a smart IDE such as IntelliJ that automatically type-checks BEFORE you compile, and visually shows the types AS YOU ARE TYPING and visually gives you type errors. This probably saved me 100 hours of "Type-Kata" (compiling, fighting with the type checker, going back, compiling again). Turns out Haskell's type checker is much stricter than Scala's.

The first two courses were mostly a repetition of Programming Languages A, B, C. Walk in the park. Best parts were when Odersky is live-coding in the video instead of slides.

Course 1: taught by Big Papa all-hail Lord Odersky himself (creator of Scala). He's not the best instructor, but he's pretty good. Sometimes he's too abstract and glosses over details too quickly. I had no trouble, but others would. First of all the title *Functional* Programming in Scala is a big fat lie. It's *Programming* in Scala. In fact Weeks 2-3-4 are entirely dedicated to Object Oriented Programming. At this point I was very upset. Scala seemed like a "kitchen sink" language (like C++). They threw everything in there; FP, OOP, mutation... Thankfully the later courses get more functional, less object-oriented. The PAs are: recursion, Functional Sets (lie!), Object oriented sets, Huffman Codes, Anagrams. Very easy.

Course 2: also taught by Odersky. yet another big fat lie with the title *Functional* Design in Scala. NOPE! It's *Design* in Scala, once again most of the course dedicated to Object Oriented Programming. Screw you, old man! This course is an incohesive mess, with topics that don't really relate to each other. The part about the Pouring problem (from Die Hard 3) was nice. There was the mandatory MONADS section. MONAAAADS! Are you scared enough? Never used ever in the rest of the Spec. The PAs are: quickCheck (ripped off from Haskell, you cheap bastards!), a solver for a block-based game called [Bloxorz](https://www.miniclip.com/games/bloxorz/en/), Type directed programming quizzes (see below), and a web-app calculator program.

Apparently there was a part about [Functional Reactive programming](https://www.edx.org/course/programming-reactive-systems) that was removed (why is this not in the Spec?), and replaced with a text-only treatise on Implicits: a super abstract, hard, difficult, vague, boring topic. I skipped the reading. They added some quizzes that require you to "Upgrade to Submit". This was the ONLY payment required in the entire Spec. I didn't, of course. Later I got an email from Coursera, asking me why I didn't finish this course. LOL! I can submit my final project for free, but I have to pay to submit some stupid text-quizzes? What is this? I never used implicits in the Capstone Project anyway! [The Zen of Python](https://en.wikipedia.org/wiki/Zen_of_Python#/media/File:Zen_Of_Python.png) dictates "Explicit is better than implicit." In this case, Scala is wrong! Python is right.

Course 3 can be summed up as: ".par". Yep, that's pretty much it! You take a normal Scala collection (List, Array, Iterable, Seq, Traversable, whatever) and append ".par" at the end, and it becomes... a PARALLEL collection! YAY! Unlike Intro to Parallel Programming you don't have to write the parallel code yourself. Scala compiler takes care of it. This course is super easy and super short. Even the last PA about Builders and Combiners took me 15 minutes or so.

A lot of repetition from Udacity's Intro to Parallel Programming: first PA was a blur filter (which I already did in IPP), second PA was about reduce, scan, prefixSum (which I already did in IPP), third PA was about K-Means clustering algorithm in parallel (which I knew from Roughgarden's Algorithms), and fourth PA was about celestial bodies simulation algorithm called Barnes-Hut (this was new).

Short sweet accessible doable course with nice instructors! I like those Slavic accents, man. They provide full lecture code snippets on Github so it was super easy to go through even the longer videos. The assignments have interactive GUIs where you can play around with blurring images and so on. Super nice!

Course 4: This is about Apache Spark, which is considered Scala's only "Killer App" and the main selling point for a lot of people who did this Spec. It delivers on its promise: it's super fast! And this is achieved by... LAZINESS! A concept from the academic world of functional programming making a huge impact in real life! You can process gigabytes-large data in seconds. You can abstract yourself away from threads, parallel computation, distributed networks, clusters, synchronization. 
Although it's a bit disappointing that Scala's big killer app kinda gets reduced to code that looks like a bunch of... SQL queries (later it literally becomes SQL queries!). Boring! The main data structure is called RDD: Resilient Distributed Dataset. It's very similar to SQL tables. You can join them and stuff. RDDs are lazy by default so in order to "retrieve" results from them you have to call .collect() on them at the end.

There are TRANSFORMATIONS (like map, filter etc.) that don't move data around, are LAZY and results are kept in memory as much as possible for max performance (avoiding disk I/O). There are ACTIONS (like groupBy) Third week (no PA) is about more nitty gritty details about performance of parallel code. You gotta watch out for non-lazy ACTIONS that SHUFFLE data across the distributed network (bottleneck due to latency/traffic). This is done by clever data partitioning. Alternative to RDDs there are Dataframes, which use some ninja optimizations from the world of SQL, but lack type safety, and Datasets (bit of both worlds: some optimization, some type safety).

The assignments were easy, I finished them in 15-30 minutes. The lecture videos took WAY LONGER than the PAs. Some videos were over 40 minutes long! I was going insane from boredom under quarantine trying to get through these. One assignment is about programming language popularity ranking through Wikipedia articles. Second assignment is about ranking stuff from StackOverFlow. I don't understand why this was a "two-week long assignment." I finished it in half an hour. It feels like they ran out of stuff to give you. Fourth week (3rd assignment) is about some census data of Americans and their daily activities. This one was messy because it uses Dataframes and Datasets which mix 2 or 3 different APIs so the syntax becomes spaghetti like HTML+JavaScript+SQL. 

Course 5 (Capstone Project): The goal is to create an [interactive web app](https://s3-eu-west-1.amazonaws.com/scala-capstone/index.html) displaying world-wide temperatures from 1975 to 2015. You have to process 1.3GB of CSV file data and convert them to colorful images for different zoom levels. Generating the images takes several days of CPU intensive computing, even with all the parallel tricks. 

This one has only text descriptions of what you are supposed to do. I found these descriptions sometimes vague and lacking. They leave a lot up to you. I entirely sidestepped the whole "RDD vs Dataset vs Dataframe" debate and just went straight with RDDs. Give you links to some Wikipedia pages to implement many mathematical formulas needed. Lots of them: Inverse Distance Weighting, Great Circle Distance, Linear and Bilinear Interpolation, and so on. The auto-grader has very cryptic, unhelpful messages where I spent several days not knowing what I was doing wrong. I could debug only by random trial-error of changing stuff in my code. The "teaching staff" on Coursera are useless. They have no clue about this course, and don't respond for several days/weeks. DON'T PAY!

It's broken into 6 Milestones. The first 2 were the hardest, the last 2 went super quickly. I think Milestones 5 and 6 took me 5 minutes each! Insanely easy. The GUI is mostly coded for you, you have to only code small parts of it using Signals from Course 2. Signals are a functional, immutable way to deal with events and reactive programming needed for interactive applications. In case you forgot Course 2 by now, they provide a refresher.

*Spam's recommendation:* If you are into FP, definitely take it! You know what, JUST TAKE IT. An entire specialization that you can audit FOR FREE and even submit your code for grading that you can finish in 1-2 months! Super super awesome. You might wanna wait until it is updated to Scala 3 (probably in 2021) but it works just fine today. You can take it after 1 year, probably even earlier (just after PLABC or the Haskell book).
