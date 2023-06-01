// Implement member extension functions 'record' and 'unaryPlus' so that the
// code below compiled and stored specified words. These functions should be
// unavailable outside of the 'Words' class.

class Words {
    private val list = mutableListOf<String>()

    // TODO
    fun String.record() {
        list += this
    }

    operator fun String.unaryPlus() {
        record()
    }

    override fun toString() = list.toString()
}

// TODO()
fun Words.addFoo() {
    "foo".record()
}

fun main(args: Array<String>) {
    val words = Words()
    with(words) {
        // The following two lines should compile:
        "one".record()
        +"two"
    }
    words.toString() eq "[one, two]"
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
