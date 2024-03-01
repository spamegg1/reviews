## Berliner Pattern 

In functional programming, data rarely needs to be mutable. 
Theoretically, it is possible to completely avoid mutability, especially in such programming languages as Haskell. 
However, this might feel cumbersome and unnecessarily complicated to many coders. 
Thankfully, you can get the best of both worlds with the languages that combine functional and imperative programming.   
In particular, Scala was specifically designed with this fusion in mind. 

The Berliner Pattern is an architectural pattern introduced by Bill Venners and Frank Sommers at Scala Days 2018 in Berlin.
Its goal is to restrict mutability to only those parts of a program for where it is unavoidable.
The application can be thought of as divided into three layers: 

* The external layer, which has to interact with the outside world. 
  This layer enables the application to communicate with other programs, services, or the operating system.
  It's practically impossible to implement this layer in a purely functional way, 
  but the good news is that there is no need to do so. 
* The internal layer, where we connect to databases or write into files. 
  This part of the application is usually performance-critical, so it's only natural to use mutable data structures here. 
* The middle layer, which connect the previous two. 
  This is where our business logic resides and where functional programming shines. 
  
Pushing mutability to the thin inner and outer layers offers its advantages. 
First of all, the more we restrict the data, the more future-proof our code becomes. 
We not only provide more information to the compiler, but also signal future developers that some data ought not to be modified.

Secondly, it simplifies the writing of concurrent code. 
When multiple threads can modify the same data, we may quickly end up in an invalid state, making it complicated to debug. 
There is no need to resort to mutexes, monitors, or other patterns when there is no actual way to modify data. 

Finally, the common pattern in imperative programming with mutable data involves first assigning some default value to a variable,
and then modifying it. 
For example, you start with an empty collection and then populate it with some specific values. 
However, default values are evil. 
Coders often forget to change them into something meaningful, leading to many bugs, such as 
the billion-dollar mistake caused by using `null`. 

We encourage you to familiarize yourself with this pattern by watching the [original video](https://www.youtube.com/watch?v=DhNw60hxCeY).

### Exercise

We provide you with a sample implementation of the application that handles creating, modifying, and deleting users in a database. 
We mock the database and http layers, and your task will be to implement methods processing requests following the Berliner pattern.

Start by implementing the `onNewUser` and `onVerification` methods in `BerlinerPatternTask.scala`.
We provide the implementations for the database and the client for these methods, so you could familiarize yourself
with the application.
Execute the `run` script in `HttpClient.scala` to make sure your implementation works correctly.

Then implement the functionality related to changing the password as well as removing users. 
You will need to implement all layers for these methods, so check out `Database.scala` and `HttpClient.scala`. 
Don't forget to uncomment the last several lines in the `run` script for this task. 
