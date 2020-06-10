# Spam's CS Corner: Course Reviews (IN PROGRESS)
DISCLAIMER: These are my subjective personal opinions! Make your own judgment. Also, I will talk a lot about my feelings! You are warned.

- [Harvard's CS50 (first half)](#cs50)
- [How to Code 1 & 2](#how-to-code)
- [Programming Languages A, B, C](#plabc)
- [Learn Prolog Now!](#lpn)
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
- [Udacity CS 212](#cs212)

### <a name="cs50"></a> Harvard's CS50 (first half)
https://cs50.harvard.edu/
This was my first course. In 2018 this was the "intro/beginner" course on OSSU curriculum. For the sake of completeness I took the whole course at once, including the [second half](#cs50-2). It's pretty challenging for a beginner course, because that's how Harvard does it. In terms of college course numbering, they go with 050 but it feels more like a 200-level class.

The course uses an online IDE which was really top-notch in my opinion. They even invented their own version of the C compiler that gives more "beginner-friendly" and more helpful error messages. They created a much more beginner-friendly version of the "man-pages" for C. They even teach you how to use "valgrind", a code analysis/memory leak detection tool that even some pros don't know or use.

For me the assignments were at the right level of difficulty. Even though it uses C and gets into low-level matters of memory management, it was not too difficult. Yes, initially pointer arithmetic and pointer logic is gonna drive you crazy, but you'll get used to it. Each assignment had 2 versions called "more" and "less", meaning more comfortable or less comfortable with programming. "More" versions are the more challenging ones if I remember correctly, which I preferred to do. Assignments were: Scratch (a visual, block-based programming language for kids), displaying some text, breaking ciphers, credit card number verification, image decoding, image resizing, dictionary word search and more. Really throws lots of different types of problems and file/data types at you! Very good variation for a beginner course.

The instructor David Malan is very energetic and fun, but I really disliked the 1.5 hour long lecture videos recorded in a lecture hall. The lectures were not very useful when doing the assignments. But they had separate much shorter "helper" videos for the assignments, done by the TAs; those were very helpful. The assignments had super long wordy text descriptions that I disliked. It was usually more difficult to decipher all that text than doing the assignments themselves.

I think it's a challenging beginner course. These days Python is the preferred language for beginner courses (which is covered in the second half), but if you want to baptize yourself in fire, you can jump into this course.

*Spam's Recommendation:* Take it. It's good!

### <a name="how-to-code"></a> How to Code 1 & 2
https://www.edx.org/course/how-to-code-simple-data
https://www.edx.org/course/how-to-code-complex-data

*Spam's recommendation:* I am personally biased to like these courses. Definitely take if you are interested in pursuing functional programming. If you already had an introductory course, you can skip these and go to Programming Languages A, B, C. PLA uses ML to teach very similar functional programming principles, and PLB already uses the same language, Racket.

### <a name="plabc"></a> Programming Languages A, B, C
https://www.coursera.org/learn/programming-languages
https://www.coursera.org/learn/programming-languages-part-b
https://www.coursera.org/learn/programming-languages-part-c

*Spam's recommendation:* Drop what you are doing and take these now! 

### <a name="lpn"></a> Learn Prolog Now!
https://lpn.swi-prolog.org/lpnpage.php?pageid=top
This is a text-only course, like a textbook. There is a book version of it too I believe. No videos.

If you are one of those people who like to debate programming languages and paradigms, well... The others are pretty similar, but the only "truly different" programming paradigm in my opinion is this one: Logic Programming. 

It is so very different! You have to think in a completely different way: Unification and Backtracking. You have to think in terms of search trees. Even some simple exercises will trip you up. It helps that it has recursion, so it has some familiarity at least. Also you think of your programs as knowledge bases, to which you make queries: a bit database-like, declarative-style programming. So there is some familiarity there too if you used SQL. But I must admit that Logic programming is not suitable for most problems out there. It can also be computationally inefficient. It's super cool though.

Backtracking is used in some "normal" programs in "normal" (non-Logic) languages though. For example you will write a brute-force Sudoku puzzle solver in How to Code 2 and then also in Udacity's Software Testing. The straightforward, non-clever solution necessitates backtracking. Another example of backtracking in "normal" programs is maze solvers: https://github.com/mikepound/mazesolving and https://www.youtube.com/watch?v=rop0W4QDOUI

This was right up my alley, because the language works exactly like a [proof tree](https://www.quora.com/What-are-proof-trees) which I studied in Mathematical Logic. Oh and the Cut Rule! (to stop backtracking) Goes back to Gentzen's 1934 [theorem](https://en.wikipedia.org/wiki/Cut-elimination_theorem).

Originally Prolog was created by a linguist, so the main application is studying Grammars. The course will teach you context free grammars and definite clause grammars. Very important stuff if you want to go into parsing and compilers! That will be your "final project". You'll probably never use Prolog in the real world. The website claims it is coded in Prolog though!

*Spam's recommendation:* Take it only if you like complex mathematical algorithmic thinking, or have strong interest/background in Mathematical Logic/Proof Theory/Linguistics. But the Logic Programming's way of thinking can be VERY USEFUL if you plan to go into an area of CS with hard problems. 

### <a name="hpffp"></a> Haskell Programming from First Principles
https://haskellbook.com/
I didn't pay for this. $59? Are you kidding? My Specialization cost me $0, please. In my country I can eat for a month with $59 (local currency crap against the mighty dollar). It's a great book though. Although SUPER SUPER SUPER LONG! Like 1900 pages. I think I quit at page 1700 or so, after Applicative. 

This was by far the most useful thing for my Specialization. Scala totally rips off Haskell in so many ways, even the syntax is mostly the same. Even though I never saw a single line of Scala code before starting my Spec, I flew through Martin Odersky's courses.

I love Haskell! It's awesome. Most of your time, like 95%, will be spent battling the type checker. It's like martial arts practice. I call it "Type-Kata". The type checker will reject almost all of your code. Just when you think you wisened up and grasped a concept, the type checker will throw it back in your face; and remind you of what a useless, incompetent piece of garbage you are. Go back to Python, you loser. But magically, if your code passes the type checker, it will be usually correct and bug-free. That's why they call it *The Venerable Glasgow Haskell Compiler*.

Did I already tell you that this is hard? It's very hard. The book is full of code though, and I typed all of it. Many of the exercises are of the "guess the correct type" type. (Did I just make a pun?) It also takes you step by step through creating and compiling projects using Haskell's build tool, Stack.

*Spam's recommendation:* I'm gonna say... don't take it. Unless you are an evil pirate like me who is also into masochism. It's super duper hard anyways, and definitely not worth $59. It will take you a LONG TIME to get through this book. But if you do, your Functional Scala Specialization will be a total cakewalk.

You might try (I haven't) [Learn You a Haskell for a Great Good!](http://www.learnyouahaskell.com/) if you MUST learn Haskell. 

### <a name="n2t"></a> Nand2Tetris 1,2
https://www.nand2tetris.org/

This was my favorite course ever! It's purely project focused. No lectures or academic topics. It probably won't be directly useful in real life, but the ideas/systems will indirectly help you understand machine instructions/assembly/VM/compilers.

It's quite tough and there is a huge gap of time and difficulty between parts 1 and 2. I can understand the instructors' decision to split it that way; they cut off the first course where the "hardware stuff" ends so that people who are only interested in those parts can take only the first course.

The goal is to build a 16-bit single-tasking toy computer with 32K RAM called Hack that was designed by the instructors. It's actually 15-bit, the instruction set does not use one of the bits. Only 24K RAM is used.

Part 1 makes you build basic logic gates, then bigger chips, then eventually an ALU (Arithmetic Logic Unit), a CPU and RAM chips in a Hardware Simulator. You use a hardware description language to do this. (Editors like Sublime Text and VS Code have syntax highlighting plugins for this HDL.) Then you write some code that translates a simplified pseudo-assembly language (with only 3 registers) to 16-bit machine instructions specified by the instructors. The hardware simulator, and all the tests for your chips/assembler are provided.

Part 2 is much more software focused, longer and harder. You will write code that translates a Java Virtual Machine-like pseudo VM-code to assembly code. Then you will write a compiler for a made-up programming language called Jack, which looks like C and Java. Both of these are spread to multiple assignments. Lastly you write some code for the "OS" of your Hack computer, which is really just a Jack library providing some basic functions. Again, all the simulators and test cases are provided. Eventually you can run some complicated program on it, like Tetris or Pong. You can even write your own Jack program! I remember writing a guess-the-word game with a small dictionary loaded into memory.

I have nothing bad to say about this course, maybe except that in Part 2, towards the end, with the Hack OS, Jack-to-VM compiler and VM-to-assembly translator, things can get really difficult to debug. Thankfully the instructors thought of this and provided "reference solution" Hack OS modules, Jack compiler and VM translator. I found it easiest to just compare my output with correct output line by line. The instructors even provide a TextComparer application to do that!

These courses gave me the best feeling! I felt euphoric, like I just flashed through computing history and built everything from the ground up. I felt like I could do anything. Very empowering stuff. It's quite challenging but you get a taste of every level of programming, from low-level assembly all the way up to operating systems and applications.

*Spam's recommendation:* Take it if you want to get a whirlwind tour of all the layers of how a computer works from the chips on the ground level all the way up to software, and feel awesome doing it! 

### <a name="intro-to-net"></a> Intro to Networking

This course was a major bummer coming off the euphoric high of Nand2Tetris. It's very long, technical, academic, hard and boring. It has no programming assignments. The two instructors (one of which keeps appearing with a different hair color every lecture) don't do a very good job of explaining things. Lecture videos are usually very long. This course is a HUGE, many metric tons of info dump on your brain.

The quizzes and exams are very difficult, the questions are hard to understand, very wordy and vague (like what students call "word problems" in math classes), and very difficult to get feedback on. I finished with a 79/100 overall on the course. The exams don't let you try again, also don't give feedback on right/wrong answers. The quizzes let you try as many times as you want, but sometimes I had to do blind-guessing for 100 different values just so I could see the correct answer explanation. They are not multiple-choice. These questions are so difficult, riddled with so many different unit conversions and constants, it's so hard to know even when I'm doing something right.

The best parts were those about Internet history, earlier versions of TCP and so on. There were also some routing algorithms like Dijkstra, Bellman-Ford which I relearned later in Algorithms. Those were good too. There were some interviews with what I assume important people in networking, but the video/audio quality is so poor on these it actually hurt my ears and eyes.

Each topic had so many nitty gritty technical details, it's impossible to keep them all in mind. Routing, queuing algorithms, IP addresses, NAT translations, DHCP systems, DNS systems, Encodings on the physical layer, all the different speeds in different versions of TCP, dozens of separate bits and parts of a typical TCP/IP package, security and cryptography, and so on... the textbook they chose (Kurose) is so long, wordy and unreadable I found it to be of no help. Before quizzes/exams I had to re-read PDF slides and try to make questions look like those on the slides.

*Spam's recommendation:* Skip this. There has to be a better, more practical way to learn Networking. I don't know what it is though. 

### <a name="ostep"></a> Operating Systems: Three Easy Pieces
http://pages.cs.wisc.edu/~remzi/OSTEP/

*Spam's recommendation:* This is THE book if you want to understand operating systems.

### <a name="mit6001x"></a> Intro to CS and Programming with Python
https://www.edx.org/course/introduction-computer-science-mitx-6-00-1x-10

This is probably the best intro course ever. This is much gentler than CS50 as an intro class, obviously. I took it one year into my studies, because it got added to the OSSU curriculum one year after I started, and I wanted to do it for completeness. Obviously at that point I was not a beginner so this course was very easy and fun. I think it took me 6 days. It went so quickly because I was having so much fun.

Although it was easy for me, it's not so easy from a beginner's perspective. I found the challenge level to be just right. Even with 1 year experience, the assignments made me think for a bit. It packs a lot into just 6-7 weeks and finds a perfect balance of programming and computer science, theory and practice. Starts all the way from the beginning, basic data types, integers, strings, lists, dictionaries and so on... then includes the big-O notation and runtime analysis of basic sorting algorithms, object-oriented programming, even data visualization. The instructor Eric Grimson is very nice. Friendly and good at explaining things.

*Spam's recommendation:* TAKE THIS! BEST intro course ever. Ignore the other "intro/beginner" stuff like Py4e or Fundamentals of Computing (nothing against those courses).

### <a name="coretheory"></a> Algorithms (Tim Roughgarden)
https://www.coursera.org/learn/algorithms-divide-conquer
https://www.coursera.org/learn/algorithms-graphs-data-structures
https://www.coursera.org/learn/algorithms-greedy
https://www.coursera.org/learn/algorithms-npcomplete
### <a name="db"></a> Databases
### <a name="graphics"></a> Computer Graphics
https://www.edx.org/course/computer-graphics-uc-san-diegox-cse167x
### <a name="ml"></a> Machine Learning
https://www.coursera.org/learn/machine-learning
Wow, this was one of the BEST courses I took ever. So good. The instructor Andrew Ng is, if I understand correctly, the founder of Coursera. His teaching style is great. The material, PDFs/notes, videos, assignments are all top-notch quality. They also introduced me to the free MATLAB alternative, GNU Octave.

You learn a lot in this course (single/multivariate linear regression, gradient descent algorithms, neural networks, supervised/unsupervised learning, PCA: principle component analysis, K-means clustering), and it's all applied immediately with the programming assignments. Moreover what you learn is directly applicable in the real world. You can take these "basics of ML" and immediately start solving problems with them! The PAs are very cool: handwritten digit recognition, spam filtering, predicting housing prices, OCR detection, movie recommendations, and so on. You also learn how to analyze a poorly performing ML algorithm, how to diagnose its problems, how to play around with parameters and so on.

The mathematics/linear algebra is complicated, the formulas look super dense, and admittedly this course (and ML in general) has a SERIOUS notation problem. Subscripts, superscripts, all over the place. The formulas are very difficult to read sometimes. But surprisingly you actually don't need to know any partial derivatives or serious linear algebra! The instructor explains everything clearly. 

You also need to spend some time thinking about how to correctly "vectorize" the formulas. Quite often in contrast to how they are presented in the slides, you have to switch rows and columns of matrices when you write the code. Thankfully the PA instructions make that very clear.

*Spam's recommendation:* What are you waiting for? TAKE IT RIGHT NOW!

### <a name="compilers"></a> Compilers
https://www.udacity.com/course/compilers-theory-and-practice--ud168
I took this because the Stanford compilers course became unavailable. This course has no programming assignments, only quizzes, which was OK by me. I'm interested in compilers but not that much. It's a pretty good course. Especially the beginning parts that teach you the theory of regular expressions and deterministic/non-deterministic finite state machines, and later, context-free grammars. It's a very long course, and it gets super complicated towards the end when you deal with low-level code generation matters. However the problems involved are extremely interesting and hard, like parse trees, register allocation and control flow graphs.

*Spam's recommendation:* Don't take it. What, are you crazy enough to write a compiler? Nobody is gonna write a compiler in their career. If you want to write a "fun compiler" take Nand2Tetris Part 2 instead. You'll probably have to write a simple parser at some point in your life though, so you can take the beginning part (regex, FSMs, parsing).

### <a name="debug"></a> Software Debugging
https://www.udacity.com/course/software-debugging--cs259
### <a name="test"></a> Software Testing
https://www.udacity.com/course/software-testing--cs258
### <a name="softdesg"></a> Software Architecture & Design
https://www.udacity.com/course/software-architecture-design--ud821
This was quite atrocious. There are 30 Lessons, each with 25-50 videos. Some videos are so short (like 3 seconds) it gets incredibly annoying to go through the in-between-video transitions.

You basically listen to a nice old man read tons and tons of definitions from some Software Architecture textbook. He constantly shifts his eyes between the camera and the teleprompter from which he is reading. It's one of THOSE courses where they try to sound super cool and talk about famous architects, buildings, designers etc.

This is a purely verbal course. No programming assignments, no homeworks, just a few quizzes here and there that are very, very subjective. It's like a super long, boring audio-book to put you to sleep. Maybe you can figure out a way to listen to it when you are jogging or something. Personally I had the videos playing in the background while I was doing pushups and ab crunches in front of my PC.

There was only one useful Lesson. It's a scenario where two instructors are play-acting as software engineer and client. They go through the design decisions of a Library ID card/book checkout system. It actually made a lot of sense. This was extremely similar to what they did in the Software Construction 1,2 classes.

*Spam's recommendation:* Do not take this. Total waste of time. But let me know if you figure out the audio-book version!

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

*Spam's recommendation:* DO NOT take this course! Don't do it. It's "optional" and of interest to researchers and specialists. No use to you.

### <a name="cs344"></a> Intro to Parallel Programming
https://classroom.udacity.com/courses/cs344
### <a name="scala"></a> Functional Programming in Scala (5 courses)
https://www.coursera.org/specializations/scala
### <a name="softcons"></a> Software Construction 1, 2
https://www.edx.org/course/software-construction-data-abstraction
https://www.edx.org/course/software-construction-object-oriented-design
These were the follow-up courses to How to Code 1,2. They have NOTHING in common with HtC courses. They do not follow up on or build up on HtC in any way. Completely orthogonal. I think that was the intention actually.

The courses are supposed to have 2 instructors but we only see one. The other lady with the short hair appears only once or twice in an interview style video.

*Spam's recommendation:* They are OK courses. You don't learn too much or do much coding at all. But they are a TOLERABLE way to get through Java concepts. Definitely preferable to Udacity's Software Architecture and Design, and Coursera's Software Architecture. These courses have actual code you can download and look at.
### <a name="crypt"></a> Cryptography 1
https://www.coursera.org/learn/crypto/
Wow this course was HARD. The easiest part for me was close to the ending where it goes into number theory (Euler phi function and such). The last part of the course is just setting you up for Cryptography 2, the next course, which I'm told is even more math heavy. Yikes! 

Even though I used to teach math, this course was really hard for me because the math is done in a non-rigorous, hand-wavy fashion. All the definitions (of semantic security, security against existential forgery, and MANY other definitions) are in terms of "negligible" probabilities; so you never make precise probability calculations; instead you say "well this looks negligible, so that will be negligible" and so on. It was so vague and hand-wavy I could not deal with it. 

There is a companion textbook but it is so wordy it's unreadable. The explanations in the videos are somewhat quick and gloss over the details so they went over my head. To do the exams I had to keep re-reading the PDF slides many many times over and over. You can attempt the exams only 3 times within 24 hours, and each time some questions are randomized/changed. THAT SUCKS.

You learn the design of many, many, many ciphers. It's super technical and complicated and hard to understand and sometimes boring. You learn about secrecy, integrity, authenticity, and other cryptography concepts.

This course scared me so much that I am afraid of using any cryptography at all. No matter what you do it's always unsafe :( The course says not to DESIGN your own ciphers, and not to IMPLEMENT your own ciphers based on someone else's design, and to be CAREFUL when using well researched, publicly implemented/tested ciphers, but it's so scary it makes you not wanna USE ANY ciphers ever at all.

Stuff I said above sounds all very negative, but it's a good course and the instructor Dan Boneh is also really good (he was a "guest interview" in Intro to Networking too!) There are some optional but really cool and hard programming exercises (which I did in Python) where you break ciphers, implement some well-known attacks against websites, and so on. For one of the assignments they even created a fake Stanford website that you can attack. Super cool!

*Spam's recommendation:* Probably don't take it. This is a hard MATH course, it doesn't really teach you about security (well it does, but in painful mathematical technical detail, not in simple practical terms).

### <a name="cs50-2"></a> CS50, second half, Final Project
https://cs50.harvard.edu/
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

*Spam's recommendation:* Take it only if you like messy challenges. This is the world of web programming, where nothing makes sense, and code from multiple languages are meshed together in spaghetti and it's all raining on your head, meatballs and all. I liked it but it was super frustrating. For me it replaces the [Software Engineering Capstone Project](https://www.edx.org/course/software-development-capstone-project-ubcx-softengprjx) which I did not take due to paywall.

### <a name="softproc"></a> Software Processes
https://www.coursera.org/learn/software-processes
I decided to take this when I realized [Software Engineering: Introduction](https://www.edx.org/course/software-engineering-introduction-ubcx-softeng1x) is behind a paywall. That course was talking about Software Methodologies, so I took this.

Another verbal course. You learn about all the cool catchphrases, ahem, I mean, Software Methodologies used in software companies. Waterfall, Agile, Kanban, Sashimi, Iterative model, Incremental Model, Requirements, Specification, Reusability, Extensibility, Coupling, Cohesion, Modularity, Encapsulation, Information Hiding, and sooooo many other words. There are some practice quizzes, and some "real" quizzes behind a paywall. There are some "scenario" questions where you are given a client and their needs, and you must write an essay describing the best approach to solve their software problems.

*Spam's recommendation:* Skip it. The exams are behind a paywall anyway. You can see the questions though. Just can't submit.

### <a name="softarch"></a> Software Architecture
https://www.coursera.org/learn/software-architecture
I decided to take this when I realized [Software Engineering: Introduction](https://www.edx.org/course/software-engineering-introduction-ubcx-softeng1x) is behind a paywall.

Another verbal course. It's like a literature class. [Words, words, words, words, words.](https://www.youtube.com/watch?v=-lqqDvWF45w) They hired a nice young acting or dance major student to read all the text from the teleprompter for a nice presentation. She clearly has no idea what she is saying.

This one makes you draw a ton of UML diagrams using some online tools. There are more than 20 kinds of UML diagrams. Oh my god. I felt like I was taking an art class. I made my diagrams [reeeeaaal pretty](https://d3c33hcgiwev3.cloudfront.net/imageAssetProxy.v1/_4b8b74b11840729713d3725d2f08b997_Deployment_Diagram.png?expiry=1591920000000&hmac=8NXnWXX6LIzSv2I1Aagnm-ZBo7VoAQ4ToxpYKAYT6GY). It was kinda fun. These assignments are all peer-graded. You get a free certificate at the end, no paywall, but it's pretty meaningless. 

*Spam's recommendation:* What do you think? Skip it, of course.

### <a name="haskell"></a> Functional Programming in Haskell
https://www.futurelearn.com/courses/functional-programming-haskell
This was a 6-week "course" that cannot really be called a course. It was more like a few short tutorials put together. It was so insanely short I finished it in one day. I think the last "week" was just some interview videos with some academics. In week 6 they jump into Monads (which you won't understand), because for some reason FP people MUST mention Monads! What's cool is that it's from U of Glasgow, the creators of Haskell. You get to meet Simon Peyton Jones. They talk about the history of the ML-family of languages from the 70s and 80s. They talk about Alonzo Church, Stephen Kleene and lambda calculus too. History is cool.

*Spam's recommendation:* Well, I'm a sucker for computing history. I also like Scottish nerds. If you have a few hours of spare time and interested in Functional Programming, its people and its history, go for it.

### <a name="cs212"></a> Udacity CS212

I took this course twice! Once a few years ago before I started OSSU. I had to quit because it was too hard and I was frustrated. The second time around I finished it.

The instructor Peter Norvig is a super smart guy leading Google AI Research (at the time, 2013?). His teaching style is very tough and definitely not for most people. He leaves A LOT up to you: he wants you to think through the problem, go to Python website and look up functions from the Python library to solve the problem, and not only that, but to solve it in a clever, short, elegant way. The first time I took it, a lot of students were really butthurt in the forums complaining about him.

But the course is incredibly valuable. It teaches you so many "high-level" programming ideas and tricks. I first learned the concept of Refactoring here. It's a purely problem-solving, puzzle-solving course, except for the language theory section in the middle. That part just didn't click even the second time around. You solve the famous Zebra puzzle, the Pouring problem (from Die Hard 3 with Bruce Willis and Samuel Jackson), create a Poker game, solve a parking problem, among others. Norvig teaches you the concepts from breadth-first-search and depth-first-search, the problem space, the "frontier", and uses all kinds of functional programming tricks available in Python to make the code short and elegant.

The assignments are very hard. Definitely up there with Algorithms or even Advanced Programming. Interestingly my Scala Specialization mentioned the Pouring problem, and Martin Odersky offered a Scala solution after praising Norvig's Python solution as "elegant".

*Spam's recommendation:* Take it only if you want a real challenge, you're OK with a very indirect instructor, and you are self-driven to do your own searches through documentation and figure things out.
