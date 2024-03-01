## Scala Collections instead of Imperative Loops

In the imperative programming style, you will often find the following pattern: a variable is initially set to some 
default value, such as an empty collection, an empty string, a zero, or null. 
Then, step-by-step, initialization code runs in a loop to create the proper value . 
After this process, the value assigned to the variable does not change anymore — or if it does, 
it’s done in a way that could be replaced by resetting the variable to its default value and rerunning the initialization. 
However, the potential for modification remains, despite its redundancy. 
Throughout the whole lifespan of the program, it hangs like a loose end of an electric cable, tempting everyone to touch it.

Functional Programming, on the other hand, allows us to build useful values without the need for initial default values and temporary mutability. 
Even a highly complex data structure can be computed using a higher-order function extensively and then 
assigned to a constant, preventing future modifications. 
If we need an updated version, we can create a new data structure instead of modifying the old one.

Scala provides a rich library of collections — `Array`, `List`, `Vector`, `Set`, `Map`, and many others — 
and includes methods for manipulating these collections and their elements. 
You have already learned about some of those methods in the first chapter. 
In this chapter, you will learn more about how to avoid mutability and leverage immutability to write safer and sometimes 
even more performant code.
