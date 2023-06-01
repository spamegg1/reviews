package mastermind

import kotlin.random.Random

val ALPHABET = 'A'..'F'
const val CODE_LENGTH = 4

fun main() {
    val differentLetters = false
    playMastermind(differentLetters)
}

fun playMastermind(
        differentLetters: Boolean,
        secret: String = generateSecret(differentLetters)
) {
    var evaluation: Evaluation

    do {
        print("Your guess: ")
        var guess = readLine()!!
        while (hasErrorsInInput(guess)) {
            println("Incorrect input: $guess. " +
                    "It should consist of $CODE_LENGTH characters from $ALPHABET. " +
                    "Please try again.")
            guess = readLine()!!
        }
        evaluation = evaluateGuess(secret, guess)
        if (evaluation.isComplete()) {
            println("The guess is correct!")
        } else {
            println("Right positions: ${evaluation.rightPosition}; " +
                    "wrong positions: ${evaluation.wrongPosition}.")
        }

    } while (!evaluation.isComplete())
}

fun Evaluation.isComplete(): Boolean = rightPosition == CODE_LENGTH

fun hasErrorsInInput(guess: String): Boolean {
    val possibleLetters = ALPHABET.toSet()
    return guess.length != CODE_LENGTH || guess.any { it !in possibleLetters }
}

fun generateSecret(differentLetters: Boolean): String {
    val chars = ALPHABET.toMutableList()
    return buildString {
        for (i in 1..CODE_LENGTH) {
            val letter = chars[Random.nextInt(chars.size)]
            append(letter)
            if (differentLetters) {
                chars.remove(letter)
            }
        }
    }
}
