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

addressBook.contacts.foldLeft(0) { (n, contact) =>
  if contact.name.startsWith("A") then n + 1
  else n
}

// How `foldLeft` expands...

def f(acc: String, x: Int): String = s"f($acc, xs($x))"

val z = "z"

val xs = List(0, 1, 2, 3)

xs.foldLeft(z)( (acc, n) => f(acc, n))

// Aside: Multiple parameter lists
def foo(x: Int, y: Int)(f: Int => Int): Int = f(x + 2) + f(y)

foo(0, 10)(i => i * i)

foo(0, 10){ i =>
  i * i
}
