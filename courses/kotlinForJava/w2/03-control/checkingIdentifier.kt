// Implement the function that checks whether a string is a valid identifier.
// A valid identifier is a non-empty string that starts with a letter or
// underscore and consists of only letters, digits and underscores.

fun isValidIdentifier(s: String): Boolean {
//     TODO()
    val startsOK: (Char) -> Boolean = { it == '_' || it.isLetter() }
    val valid: (Char) -> Boolean = { it == '_' || it.isLetterOrDigit() }
    return !s.isEmpty() && startsOK(s[0]) && s.all(valid)
}

fun main(args: Array<String>) {
    println(isValidIdentifier("name"))   // true
    println(isValidIdentifier("_name"))  // true
    println(isValidIdentifier("_12"))    // true
    println(isValidIdentifier(""))       // false
    println(isValidIdentifier("012"))    // false
    println(isValidIdentifier("no$"))    // false
}
