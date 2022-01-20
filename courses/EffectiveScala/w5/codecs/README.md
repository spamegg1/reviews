## Overview

First, download the [handout archive](https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3/effective-codecs.zip) and extract it somewhere on your file system.

The goal of this assignment is to implement a small serialization library. This library will be able to encode Scala values (such as instances of case classes) into [`Json`](https://www.`Json`.org) documents that can be sent over the wire (or saved to a file). Conversely, the library will be able to decode `Json` documents as Scala values. `Json` serialization is often used by web servers.

Please make sure you are familiar with the [`Json`](https://www.`Json`.org) serialization format before starting the assignment.

The library will follow a "type-directed" approach. This means that it will use a type-class Encoder[A] to model the ability of encoding a value of type A into `Json`, and a type-class Decoder[A] to model the ability of decoding a `Json` document as a value of type A.

First, you will define given instances for simple types (e.g., Int, String, etc.). Then, you will define conditional instances to combine encoders and decoders together to handle more complex types.

To make things easier, the encoders and decoders implemented in this assignment don’t directly work with `Json` blobs, but they work with an intermediate `Json` data type:

```scala
```

Here is an example of a `Json` value:

```scala
```

It can be represented as follows with the `Json` data type:

```scala
```

With this `Json` type being defined, **encoding** a value of type A consists of transforming it into a value of type `Json`:

```scala
```

Unlike arbitrary `Json` values, `Json` objects have the special property that two objects can be combined to build a bigger `Json` object containing both objects’ fields. We use this combining property of `Json` objects to define the zip method on Encoder returning `Json` objects. We do so in a subclass of Encoder called `ObjectEncoder`:

```scala
```

Conversely, **decoding** a value of type A consists in transforming a value of type `Json` into a value of type A:

```scala
```

Note that the decoding operation returns an Option[A] instead of just an A because the decoding process can fail (None means that it was impossible to produce an A from the supplied `Json` data).

Given instances of encoders and decoders are defined in the `EncoderInstances` and `DecoderInstances` traits, respectively. These traits are inherited by the Encoder and Decoder companion objects, respectively. By doing so, we make the given instances automatically available, removing the need for explicitly importing them.

We provide you with a parse`Json` function that parses a `Json` blob from a String into a `Json` value, and a render`Json` function that turns a `Json` value into a `Json` blob in a String. You can use them to experiment with the encoders and decoders that you write. The functions parse`Json` and render`Json` are defined in the file **Util.scala**.

## Your Task

Your work consists in writing codec instances that can be combined together to build codec instances for more complex types. You will start by writing instances for basic types (such as String or Boolean) and you will end up writing instances for case classes.

Open the file `Codecs.scala`. It contains the definition of the types `Json`, `Encoder`, `ObjectEncoder` and `Decoder`. Complete the partially implemented given instance definitions (replace the ??? with proper implementations) and introduce new given instance definitions as needed (look for TODO comments).

At any time, you can follow your progress by running the test sbt task. You can also use the run sbt task to run the Main program defined at the bottom of the codecs.`Json` file. Last, you can create a REPL session with the console sbt task, and then experiment with your code:

```scala
```

Remember that if you make any change to the code, you'll need to quit the console and start it again to run the updated code.

### Basic Codecs

Start by implementing the given instances of `Encoder[String]` and `Encoder[Boolean]`, and the corresponding given instances of `Decoder[Int]`, `Decoder[String]` and `Decoder[Boolean]`.

Hint: use the provided methods `Encoder.fromFunction`, `Decoder.fromFunction`, and `Decoder.fromPartialFunction`.

Make sure that your Int decoder rejects `Json` floating point numbers.

### Derived Codecs

The next step consists in implementing a codec for lists of elements of type A, given a codec for type A. The encoder instance for lists is already implemented. Fill in the definition of the corresponding decoder.

Once you have defined the encoder and decoder for lists, you should be able to **summon** them for any list containing elements that can be encoded and decoded. You can try, for instance, to evaluate the following expressions in the REPL:

```scala
```

### `Json` Object Codecs

Next, implement codecs for `Json` objects. The approach consists in defining codecs for `Json` objects having a single field, and then combining such codecs to handle `Json` objects with multiple fields.

For example, consider the following `Json` object with one field x:

```scala
```

An encoder for this `Json` object can be defined by using the provided `ObjectEncoder.field` operation, which takes the field name as a parameter and an implicit Encoder for the field value:

```scala
```

Here is an example of use of the `xField` encoder:

```scala
```

Last, to define an encoder producing a `Json` object with more than one field, you can combine two `ObjectEncoder` instances with the zip operation. This operation returns an `ObjectEncoder` producing a `Json` object with the fields of the two combined encoders. For instance, you can define an encoder producing an object with two fields x and y as follows:

```scala
```

Implement a `Decoder.field` operation corresponding to the `ObjectEncoder.field` operation.

### Codecs for Case Classes

The zip operation mentioned in the previous section only returns codecs for tuples. It would be more convenient to work with high-level data types (for instance, a Point data type, rather than a pair of coordinates).

Both encoders and decoders have a transform operation that can be used to transform the type `Json` values are encoded from, or decoded to, respectively. You can see in the assignment how it is used to define a given `Encoder[Person]`.

Define a corresponding `Decoder[Person]`, and then define a given instance of `Encoder[Contacts]` and a given instance of `Decoder[Contacts]`.

## Bonus Questions

- Can you **explicitly** write the value inferred by the compiler when it summons the `Encoder[List[Person]]`?
- Would it be possible to define codecs for optional fields?
- Would it be possible to define a function that provides a given instance of `Encoder[Option[A]]` for any type A for which there is a given instance of `Encoder[A]` (like we do with `Encoder[List[A]]`)?
- Would it be possible to define codecs for sealed traits in addition to case classes?