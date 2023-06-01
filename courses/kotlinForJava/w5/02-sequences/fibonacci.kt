// Implement the function that builds a sequence of Fibonacci
// numbers using 'sequence' function. Use 'yield'.

fun fibonacci(): Sequence<Int> = sequence {
    // TODO()
    var fibs = Pair(0, 1)
    while (true) {
        yield(fibs.first)
        fibs = Pair(fibs.second, fibs.first + fibs.second)
    }
}

fun main(args: Array<String>) {
    fibonacci().take(4).toList().toString() eq
            "[0, 1, 1, 2]"

    fibonacci().take(10).toList().toString() eq
            "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
