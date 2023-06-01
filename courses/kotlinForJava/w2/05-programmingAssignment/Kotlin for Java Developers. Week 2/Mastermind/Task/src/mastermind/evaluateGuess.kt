package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val rightPos =
        guess
            .zip(secret)
            .count { (s, g) -> s == g }

    val commonLetters = "ABCDEF".sumBy { char ->
        minOf(
            secret.count { it == char },
            guess.count { it == char }
        )
    }

    return Evaluation(rightPos, commonLetters - rightPos)
}
