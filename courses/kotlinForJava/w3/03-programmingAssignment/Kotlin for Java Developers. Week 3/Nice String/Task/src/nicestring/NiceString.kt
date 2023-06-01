package nicestring

fun String.isNice(): Boolean {
    // TODO()
    val noBadSubs = setOf("bu", "ba", "be").none { contains(it) }
    val has3Vowels = count { it in "aeiou" } >= 3
    val hasDouble = zipWithNext().any { it.first == it.second }
    return listOf(noBadSubs, has3Vowels, hasDouble).count { it } >= 2
}
