# Example Assignment

# Getting Started

The goal of this assignment is to familiarize yourself with the infrastructure and the tools required during this class. Even though the grade in this assignment will be excluded from your final grade for the course, it is important that you work through this assignment carefully.

## Installing Required Tools

Before anything else, it is crucial that you make sure that all the tools are correctly installed. Take a careful look at the Tools Setup page and verify that all the listed tools work on your machine.

# The Assignment

## Part 1: Obtain the Project Files

[Download the example.zip](https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3/progfun1-example.zip) handout archive file and extract it somewhere on your machine.

## Part 2: Using the Scala REPL

During this class we will always launch the Scala REPL (the interactive Scala console) through sbt. This way you don't need to install the Scala distribution on your machine, having sbt is enough. (In case you prefer to have the scala command available on your machine, you can download the Scala distribution from the [scala-lang.org website](http://www.scala-lang.org/downloads).)

Open the Sbt Tutorial page and follow the first steps up to (and including) "Running the Scala Interpreter". Note that sbt can only be started inside a project directory, so first navigate to the project directory that you created in Part 1.

Enter a few Scala expressions to make sure everything is working as expected.

## Part 3: Opening the Project in an IDE

In case you like using an IDE, import the project into your IDE. If you are new to Scala and don’t know which IDE to choose, we recommend to try IntelliJ. You can find information on how to import a Scala project into IntelliJ in the IntelliJ IDEA Tutorial page.

Then, in the folder src/main/scala, open the package example and double-click the file Lists.scala. There are two methods in this file that need to be implemented (sum and max).

When working on an assignment, it is important that you don't change any existing method, class or object names or types. When doing so, our automated grading tools will not be able to recognize your code and you have a high risk of not obtaining any points for your solution.

## Part 4: Running your Code

Once you start writing some code, you might want to experiment with Scala, execute small snippets of code or also run some methods that you already implemented. We present two possibilities to run Scala code.

Note that these tools are recommended for exploring Scala, but should not be used for testing your code. The next part of this assignment will explain how to write tests in Scala.

### Using the Scala REPL

In the sbt console, start the Scala REPL by typing console:

\> console [info] Starting scala interpreter... scala>

In the REPL you can try out arbitrary snippets of Scala code, for example:

scala> val l = List(3,7,2) l: List[Int] = List(3, 7, 2) scala> l.isEmpty res0: Boolean = false scala> l.tail.head res1: Int = 7 scala> List().isEmpty res2: Boolean = true

The classes of the assignment are available inside the REPL, so you can for instance import all the methods from object Lists:

scala> import example.Lists.* import example.Lists.* scala> max(List(1,3,2)) res1: Int = 3

In order to exit the Scala REPL and go back to sbt, just type ctrl-d.

### Using a Main Object

Another way to run your code is to create a new @main program entry point that can be executed by the Java Virtual Machine.

In your code editor, right-click on the package example in src/main/scala and create a new file main.scala

In order to make the program executable it has to define a top-level method annotated with @main. Change the file content to the following:

@main def run(): Unit =  println(Lists.max(List(1,3,2)))

Now the run method can be used as a program entry point.

You can run it in the sbt prompt by using the command run.

In case you use an IDE, the run method will be decorated with a button that you can click on to execute it.

Note: make sure that the main.scala files contains the line package example at the beginning. Add it if that is not the case

## Part 5: Writing Tests

Throughout the assignments of this course we will require you to write unit tests for the code that you write. Unit tests are the preferred way to test your code because unlike REPL commands, unit tests are saved and can be re-executed as often as required. This is a great way to make sure that nothing breaks when you have go back later to change some code that you wrote earlier on.

We will be using the MUnit testing library to write our unit tests. In your code editor, navigate to the folder src/test/scala and open the file ListsSuite.scala in package example. This file contains a step-by-step tutorial to learn how to write and execute MUnit tests.

## Part 6: Submitting your Solution

Once you implemented all the required methods and tested you code thoroughly, you can submit it to Coursera. The only way to submit your solution is through sbt, so you need to start sbt in your project directory.

**Warning**: Make sure that you run sbt in te root folder of the project (where the file build.sbt is located).

**Warning**: Make sure that the console line is > and not scala>. Otherwise, you are inside the Scala console, and not sbt.

In order to submit, you need to have your Coursera email and your submission token. **Note that the submission token is NOT your login password**, instead it is a special token generated by Coursera (see the section “How to submit”, in this page).

Submitting in sbt is simply done by invoking the submit task:

\> submit your.email@domain.com submissionToken [info] Connecting to coursera. Obtaining challenge... [info] Computing challenge response... [info] Submitting solution... [success] Your code was successfully submitted: Your submission has been accepted and will be graded shortly. [success] Total time: 2 s, completed Aug 30, 2012 4:30:10 PM > 

You are allowed to **resubmit an unlimited number of times**! Once you submit your solution, you should see your grade and a feedback about your code on the Coursera website within 10 minutes. If you want to improve your grade, just submit an improved solution. The best of all your submissions will count as the final grade.