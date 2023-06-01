// Functions 'all', 'none' and 'any' are interchangeable, you can use one or
// the other to implement the same functionality.
// Implement the functions 'allNonZero' and 'containsZero' using all three
// predicates in turn. 'allNonZero' checks that all the elements in the list
// are non-zero; 'containsZero' checks that the list contains zero element.
// Add the negation before the whole call (right before 'any', 'all' or 'none')
// when necessary, not only inside the predicate.

fun List<Int>.allNonZero() =  all { it != 0 } // TODO()
fun List<Int>.allNonZero1() =  none { it == 0 } // TODO()
fun List<Int>.allNonZero2() =  !any { it == 0 } // TODO()

fun List<Int>.containsZero() =  any { it == 0 } // TODO()
fun List<Int>.containsZero1() =  !all { it != 0 } // TODO()
fun List<Int>.containsZero2() =  !none { it == 0 } // TODO()

fun main(args: Array<String>) {
    val list1 = listOf(1, 2, 3)
    list1.allNonZero() eq true
    list1.allNonZero1() eq true
    list1.allNonZero2() eq true

    list1.containsZero() eq false
    list1.containsZero1() eq false
    list1.containsZero2() eq false

    val list2 = listOf(0, 1, 2)
    list2.allNonZero() eq false
    list2.allNonZero1() eq false
    list2.allNonZero2() eq false

    list2.containsZero() eq true
    list2.containsZero1() eq true
    list2.containsZero2() eq true
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
