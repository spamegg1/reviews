First, download the [handout](https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3/codecs.zip) and extract it somewhere on your machine.

Remember that if you make any change to the code, you'll need to quit the console and start it again to run the updated code.

## Overview

The goal of this assignment is to implement a type-directed serialization library: it uses type-directed operations to combine serializers for values of simpler types to construct serializers for values of more complex types.

The serialization format is [JSON](https://www.json.org/). Make sure to be familiar with this format.

To make things easier, the encoders and decoders implemented in this assignment don’t directly work with JSON blobs, but they work with an intermediate `Json` data type:

```scala
enum Json:  
  /** The JSON `null` value */  
  case Null  
  /** JSON boolean values */  
  case Bool(value: Boolean)  
  /** JSON numeric values */  
  case Num(value: BigDecimal)  
  /** JSON string values */  
  case Str(value: String)  
  /** JSON objects */  
  case Obj(fields: Map[String, Json])  
  /** JSON arrays */  
  case Arr(items: List[Json])
```

Here is an example of JSON value:

```json
{  "foo": 0,  "bar": [true, false] }
```

It can be represented as follows with the `Json` data type:

```scala
Json.Obj(Map(
  "foo" -> Json.Num(0),  
  "bar" -> Json.Arr(Json.Bool(true), Json.Bool(false))
))
```

With this `Json` type being defined, **encoding** a value of type A consists in transforming it into a value of type `Json`:

```scala
trait Encoder[-A]:  
  def encode(value: A): Json
```

Unlike arbitrary JSON values, JSON objects have the special property that two objects can be combined to build a bigger JSON object containing both objects’ fields. We use this combining property of JSON objects to define the zip method on `Encoder` returning JSON objects. We do so in a subclass of `Encoder` called `ObjectEncoder`:

```scala
trait ObjectEncoder[-A] extends Encoder[A]:  
  // Refines the encoding result to `Json.Obj`  
  def encode(value: A): Json.Obj   
  def zip[B](that: ObjectEncoder[B]): ObjectEncoder[(A, B)] = ...
```

Conversely, **decoding** a value of type A consists in transforming a value of type `Json` into a value of type A:

```scala
trait Decoder[+A]:  
  /**    
    * @param data The data to de-serialize    
    * @return The decoded value wrapped in `Some`, or `None` if decoding failed    
    */  
  def decode(data: Json): Option[A]
```

Note that the decoding operation returns an `Option[A]` instead of just an `A` because the decoding process can fail (None means that it was impossible to produce an `A` from the supplied JSON data).

Implicit encoders and decoders are defined in the `EncoderInstances` and `DecoderInstances` traits, respectively. These traits are inherited by the `Encoder` and `Decoder` companion objects, respectively. By doing so, we make the implicit instances automatically available, removing the need for explicitly importing them.

We provide you a `parseJson` function that parses a JSON blob in a String into a `Json` value, and a `renderJson` function that turns a `Json` value into a JSON blob in a String. You can use them to play with the encoders and decoders that you write. The functions `parseJson` and `renderJson` are defined in the file `Util.scala`.

## Your Task

Your work consists in writing codec instances that can be combined together to build codec instances for more complex types. You will start by writing instances for basic types (such as String or Boolean) and you will end up writing instances for case classes.

Open the file `codecs.scala`. It contains the definition of the types `Json`, `Encoder`, `ObjectEncoder` and `Decoder`. Complete the partially implemented implicit definitions (replace the ``???`` with proper implementations) and introduce new implicit definitions as needed (look for TODO comments).

At any time, you can follow your progress by running the `test` sbt task. You can also use the `run` sbt task to run the Main program defined at the bottom of the `codecs.json` file. Last, you can create a REPL session with the `console` sbt task, and then experiment with your code:

```scala
sbt:progfun2-codecs> console 
scala> import codecs.* 
import codecs.* 
scala> Util.parseJson(""" { "name": "Bob", "age": 10 } """) 
val res0: Option[codecs.Json] = Some(Obj(Map(name -> Str(Bob), age -> Num(10)))) 
scala> res0.flatMap(_.decodeAs[Person]) // This will crash until you implement it in this assignment 
val res1: Option[codecs.Person] = Some(Person(Bob,10)) 
scala> implictly[Encoder[Int]] 
val res2: codecs.Encoder[Int] = codecs.Encoder$$anon$1@74d8fde0 
scala> res2.encode(42) 
val res3: codecs.Json = Num(42) 
scala> :quit
```

Remember that if you make any change to the code, you'll need to quit the console and start it again to run the updated code.

### Basic Codecs

Start by implementing the implicit `Encoder[String]` and `Encoder[Boolean]` instances, and the corresponding implicit `Decoder[Int]`, `Decoder[String]` and `Decoder[Boolean]` instances.

Hint: use the provided methods `Encoder.fromFunction`, `Decoder.fromFunction`, and `Decoder.fromPartialFunction`.

Make sure that your Int decoder rejects JSON floating point numbers.

### Derived Codecs

The next step consists in implementing a codec for lists of elements of type A, given a codec for type A. The encoder instance for lists is already implemented. Fill in the definition of the corresponding decoder.

Once you have defined the encoder and decoder for lists, you should be able to **summon** them for any list containing elements that can be encoded and decoded. You can try, for instance, to evaluate the following expressions in the REPL:

```scala
summon[Encoder[List[Int]]] 
summon[Decoder[List[Boolean]]]
```

### JSON Object Codecs

Next, implement codecs for JSON objects. The approach consists in defining codecs for JSON objects having a single field, and then combining such codecs to handle JSON objects with multiple fields.

For example, consider the following JSON object with one field x:

```json
{  "x": 1 }
```

An encoder for this JSON object can be defined by using the provided `ObjectEncoder.field` operation, which takes the field name as a parameter and an implicit Encoder for the field value:

```scala
val xField = ObjectEncoder.field[Int]("x")
```

Here is an example of use of the `xField` encoder:

```scala
scala> xField.encode(42) 
val res0: codecs.Json.Obj = Obj(Map(x -> Num(42)))
```

Last, to define an encoder producing a JSON object with more than one field, you can combine two `ObjectEncoder` instances with the zip operation. This operation returns an `ObjectEncoder` producing a JSON object with the fields of the two combined encoders. For instance, you can define an encoder producing an object with two fields x and y as follows:

```scala
val pointEncoder: ObjectEncoder[(Int, Int)] =  
  val xField = ObjectEncoder.field[Int]("x")  
  val yField = ObjectEncoder.field[Int]("y")  
  xField.zip(yField)
```

Implement a `Decoder.field` operation corresponding to the `ObjectEncoder.field` operation.

### Codecs for Case Classes

The zip operation mentioned in the previous section only returns codecs for tuples. It would be more convenient to work with high-level data types (for instance, a Point data type, rather than a pair of coordinates).

Both encoders and decoders have a transform operation that can be used to transform the type Json values are encoded from, or decoded to, respectively. You can see in the assignment how it is used to define a given `Encoder[Person]`.

Define a corresponding `Decoder[Person]`, and then define an implicit `Encoder[Contacts]` and an implicit `Decoder[Contacts]`.

## Bonus Questions

- Can you **explicitly** write the value inferred by the compiler when it summons the Encoder[List[Person]]?
- Would it be possible to define codecs for optional fields?
- Would it be possible to define a function that provides an implicit instance of `Encoder[Option[A]]` for any type A for which there is an implicit `Encoder[A]` (like we do with `Encoder[List[A]]`)?
- Would it be possible to define codecs for sealed traits in addition to case classes?