// Change 'sum' so that it was declared as an extension to List<Int>.

//fun sum(list: List<Int>): Int { // given
fun List<Int>.sum(): Int { // todo
    var result = 0
    for (i in this) {
        result += i
    }
    return result
}

fun main(args: Array<String>) {
//     val sum = sum(listOf(1, 2, 3)) // given
    val sum = listOf(1, 2, 3).sum() // todo
    println(sum)    // 6
}
