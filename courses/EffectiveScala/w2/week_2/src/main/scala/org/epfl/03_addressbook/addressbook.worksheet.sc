case class AddressBook(contacts: List[Contact])

case class Contact(
  name: String,
  email: String,
  phoneNumbers: List[String]
)

val alice = Contact("Alice", "alice@sca.la", List())
val bob   = Contact("Bob", "bob@sca.la", List("+41787829420"))
val carol = Contact("Carol", "carol@sca.la", List())

val addressBook = AddressBook(List(alice, bob))

val numberOfContacts: Int = addressBook.contacts.size

val isAliceInContacts = addressBook.contacts.contains(alice)

val contactNames: List[String] =
  addressBook
    .contacts
    .map(contact => contact.name)

val contactsWithPhone: List[Contact] =
  addressBook
    .contacts
    .filter(contact => contact.phoneNumbers.nonEmpty)

// An aside on functions

val increment: Int => Int =
  x => {
    val result = x + 1
    result
  }

val add =
  (x: Int, y: Int) => x + y

add(1, increment(2))

// Addressbook continued...

val allPhoneNumbersFirstAttempt =
  addressBook.contacts.map(contact => contact.phoneNumbers)

val allPhoneNumbers =
  addressBook.contacts.flatMap(contact => contact.phoneNumbers)

val contacts1 = List(alice, bob)

val contacts2 = carol :: contacts1

println(contacts1)
println(contacts2)

List(alice, bob) == alice :: bob :: Nil

// Note that the "::" operator is right-associative
alice :: bob :: Nil == (alice :: (bob :: Nil))

alice :: bob :: Nil == Nil.::(bob).::(alice)

// Operator "+" is left-associative
0 + 1 + 2 == ((0 + 1) + 2)
0 + 1 + 2 == 0.+(1).+(2)

// Pattern Matching on Lists
addressBook.contacts match
  case contact :: tail => println(contact.name)
  case Nil             => println("No contacts")

addressBook.contacts match
  case first :: second :: Nil => println(second.name)
  case _                      => println("Unexpected number of contacts")

// Non-exhaustive Pattern Match
addressBook.contacts match
  case first :: second :: Nil => println(second.name)
  case Nil                    => println("Unexpected number of contacts")

// Random Access
val fruits = List("apples", "oranges", "pears")
fruits.head == "apples"
fruits.tail == List("oranges", "pears")
fruits.tail.head == "oranges"
fruits(0) == "apples"
fruits(2) == "pears"

