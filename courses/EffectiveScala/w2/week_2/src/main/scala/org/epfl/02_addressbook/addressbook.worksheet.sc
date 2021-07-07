case class AddressBook(contacts: List[Contact])

case class Contact(
  name: String,
  email: String,
  phoneNumbers: List[String]
)

val alice = Contact("Alice", "alice@sca.la", List())
val bob   = Contact("Bob", "bob@sca.la", List("+41787829420"))

val addressBook = AddressBook(List(alice, bob))

// Constructing Lists

val fruits = List("apples", "oranges", "pears")
val nums   = List(1, 2, 3, 4)
val diag3  = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
val empty  = List()

// Addressbook continued...

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
