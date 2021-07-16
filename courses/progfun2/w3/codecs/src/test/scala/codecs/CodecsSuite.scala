package codecs

import org.scalacheck
import org.scalacheck.{ Gen, Prop }
import org.scalacheck.Prop.propBoolean

class CodecsSuite
  extends munit.FunSuite
    with EncoderInstances with TestEncoders
    with DecoderInstances with TestDecoders
    with PersonCodecs
    with ContactsCodecs:

  def checkProperty(prop: Prop): Unit =
    val result = scalacheck.Test.check(scalacheck.Test.Parameters.default, prop)
    def fail(labels: Set[String], fallback: String): Nothing =
      if labels.isEmpty then throw AssertionError(fallback)
      else throw AssertionError(labels.mkString(". "))
    result.status match
      case scalacheck.Test.Passed | _: scalacheck.Test.Proved => ()
      case scalacheck.Test.Failed(_, labels)                  => fail(labels, "A property failed.")
      case scalacheck.Test.PropException(_, e, labels)        => fail(labels, s"An exception was thrown during property evaluation: $e.")
      case scalacheck.Test.Exhausted                          => fail(Set.empty, "Unable to generate data.")

  /**
    * Check that a value of an arbitrary type `A` can be encoded and then successfully
    * decoded with the given pair of encoder and decoder.
    */
  def encodeAndThenDecodeProp[A](a: A)(using encA: Encoder[A], decA: Decoder[A]): Prop =
    val maybeDecoded = decA.decode(encA.encode(a))
    maybeDecoded.contains(a) :| s"Encoded value '$a' was not successfully decoded. Got '$maybeDecoded'."

  test("it is possible to encode and decode the 'Unit' value (0pts)") {
    checkProperty(Prop.forAll((unit: Unit) => encodeAndThenDecodeProp(unit)))
  }

  test("it is possible to encode and decode 'Int' values (1pt)") {
    checkProperty(Prop.forAll((x: Int) => encodeAndThenDecodeProp(x)))
  }

  test("the 'Int' decoder should reject invalid 'Int' values (2pts)") {
    val decoded = implicitly[Decoder[Int]].decode(Json.Num(4.2))
    assert(decoded.isEmpty, "decoding 4.2 as an integer value should fail")
  }

  test("a 'String' value should be encoded as a JSON string (1pt)") {
    assert(implicitly[Encoder[String]].encode("foo") == Json.Str("foo"))
  }

  test("it is possible to encode and decode 'String' values (1pt)") {
    checkProperty(Prop.forAll((s: String) => encodeAndThenDecodeProp(s)))
  }

  test("a 'Boolean' value should be encoded as a JSON boolean (1pt)") {
    val encoder = implicitly[Encoder[Boolean]]
    assert(encoder.encode(true) == Json.Bool(true))
    assert(encoder.encode(false) == Json.Bool(false))
  }

  test("it is possible to encode and decode 'Boolean' values (1pt)") {
    checkProperty(Prop.forAll((b: Boolean) => encodeAndThenDecodeProp(b)))
  }

  test("a 'List[A]' value should be encoded as a JSON array (0pts)") {
    val xs = 1 :: 2 :: Nil
    val encoder = implicitly[Encoder[List[Int]]]
    assert(encoder.encode(xs) == Json.Arr(List(Json.Num(1), Json.Num(2))))
  }

  test("it is possible to encode and decode lists (5pts)") {
    checkProperty(Prop.forAll((xs: List[Int]) => encodeAndThenDecodeProp(xs)))
  }

  test("a 'Person' value should be encoded as a JSON object (1pt)") {
    val person = Person("Alice", 42)
    val json = Json.Obj(Map("name" -> Json.Str("Alice"), "age" -> Json.Num(42)))
    val encoder = implicitly[Encoder[Person]]
    assert(encoder.encode(person) == json)
  }

  test("it is possible to encode and decode people (4pts)") {
    checkProperty(Prop.forAll((s: String, x: Int) => encodeAndThenDecodeProp(Person(s, x))))
  }

  test("a 'Contacts' value should be encoded as a JSON object (1pt)") {
    val contacts = Contacts(List(Person("Alice", 42)))
    val json = Json.Obj(Map("people" ->
      Json.Arr(List(Json.Obj(Map("name" -> Json.Str("Alice"), "age" -> Json.Num(42)))))
    ))
    val encoder = implicitly[Encoder[Contacts]]
    assert(encoder.encode(contacts) == json)
  }

  test("it is possible to encode and decode contacts (4pts)") {
    val peopleGenerator = Gen.listOf(Gen.resultOf((s: String, x: Int) => Person(s, x)))
    checkProperty(Prop.forAll(peopleGenerator)(people => encodeAndThenDecodeProp(Contacts(people))))
  }


trait TestEncoders extends EncoderFallbackInstance

trait EncoderFallbackInstance:
  given encoderSentinel[A]: Encoder[A] =
    throw AssertionError(s"No given encoder could be found")


trait TestDecoders extends DecoderFallbackInstance

trait DecoderFallbackInstance:
  given decoderSentinel[A]: Decoder[A] =
    throw AssertionError(s"No given decoder could be found")
