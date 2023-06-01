// Complete the declaration of the class A without throwing
// NullPointerException explicitly so that NPE was thrown during
// the creation of its subclass B instance.

open class A(open val value: String) {
    init {
        value.length // TODO
    }
}

class B(override val value: String) : A(value)

fun main(args: Array<String>) {
    B("a")
}
