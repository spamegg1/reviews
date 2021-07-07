case class Contact(
  name: String,
  maybeEmail: Option[String],
  phoneNumbers: List[String]
)

val alice = Contact("Alice", Some("alice@sca.la"), List())
val bob   = Contact("Bob", None, List("+41787829420"))

// Pattern Matching on Option
def hasScaDotLaEmail(contact: Contact): Boolean =
  contact.maybeEmail match
    case Some(email) => email.endsWith("@sca.la")
    case None        => false

// Mapping on Option
def emailLength(contact: Contact): Int =
  contact.maybeEmail
    .map(email => email.size)
    .getOrElse(0)

// Zipping two optional values
val maybeAliceAndBobEmails: Option[(String, String)] =
  alice.maybeEmail.zip(bob.maybeEmail)

def sendSms(number: String, message: String): Unit = 
  println("Sending SMS message: '" + message + "' to number " + number)
def sendEmail(emailAddress: String, message: String): Unit =
  println("Sending message: '" + message + "' to Email address " + emailAddress)

def sendNotification(contact: Contact, message: String): Unit =
  contact.phoneNumbers.headOption match
    case Some(number) => sendSms(number, message)
    case None =>
      contact.maybeEmail match
        case Some(email) => sendEmail(email, message)
        case None        => ()
      
sendNotification(alice, "Hello")
sendNotification(bob, "Hello")

// find

case class AddressBook(contacts: List[Contact])

val addressBook = AddressBook(List(alice, bob))

addressBook.contacts.find(contact => contact.name == "Alice")
addressBook.contacts.find(contact => contact.name == "Carol")