// Add and implement an extension function 'isEmptyOrNull()' on the
// type String?. It should return true, if the string is null or empty.
fun String?.isEmptyOrNull(): Boolean {
    val x: String = this ?: ""
    return x == ""
}

fun main(args: Array<String>) {
    val s1: String? = null
    val s2: String? = ""
    s1.isEmptyOrNull() eq true
    s2.isEmptyOrNull() eq true

    val s3 = "   "
    s3.isEmptyOrNull() eq false
}
infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
