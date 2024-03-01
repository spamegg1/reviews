# Scala collections overview

Scala collections comprise a vast set of data structures that offer powerful and flexible ways to manipulate and organize data. 
The Scala collections framework is designed to be both user-friendly and expressive, enabling you to perform complex operations with concise and readable code. 
To achieve this, numerous methods take functions as arguments.

Scala collections are divided into two main categories: mutable and immutable. 
Immutable collections cannot be altered post-creation but can be duplicated with modifications, while mutable collections permit in-place updates. 
By default, Scala encourages the use of immutable collections because they are simpler to reason with and can aid in preventing bugs caused by unanticipated side effects.

Here's an overview of the main traits and classes:
1. `Iterable`: All collections that can be traversed in a linear sequence extend `Iterable`. It provides methods like `iterator`, `map`, `flatMap`, `filter`, and others, which we will discuss shortly.
2. `Seq`: This trait represents sequences, i.e., ordered collections of elements. It extends `Iterable` and provides methods like `apply(index: Int): T` (which allows you access an element at a specific index) and `indexOf(element: T): Int` (which returns the index of the first occurrence in the sequence that matches the provided element, or -1 if the element can't be found). Some essential classes implementing the `Seq` trait include `List`, `Array`, `Vector`, and `Queue`.
3. `Set`: Sets are unordered collections of unique elements. It extends Iterable but not `Seq` — you can't assign fixed indices to its elements. The most common implementation of `Set` is `HashSet`.
4. `Map`: A map is a collection of key-value pairs. It extends Iterable and provides methods like `get`, `keys`, `values`, `updated`, and more. It's unordered, similar to `Set`. The most common implementation of `Map` is `HashMap`.

We will now quickly review some of the most frequently used methods of Scala collections: `filter`, `find`, `foreach`, `map`, `flatMap`, and `foldLeft`. 
In each case, you will see a code example and be asked to do an exercise using the given method. 
Please note that many other methods exist. We encourage you to consult the [Scala collections documentation](https://www.scala-lang.org/api/current/scala/collection/index.html) and browse through them. Being aware of their existence and realizing that you can use them instead of constructing some logic yourself may save you a substantial amount of effort.

