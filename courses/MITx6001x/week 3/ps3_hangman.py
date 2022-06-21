# Hangman game
""" bla """

# -----------------------------------
# Helper code
# You don't need to understand this helper code,
# but you will have to know how to use the functions
# (so be sure to read the docstrings!)
import random
WORDLIST_FILENAME = "words.txt"


def load_words():
    """
    Returns a list of valid words. Words are strings of lowercase letters.

    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # infile: file
    infile = open(WORDLIST_FILENAME, 'r')
    # line: string
    line = infile.readline()
    # wordlist: list of strings
    wordlist = line.split()
    print("  ", len(wordlist), "words loaded.")
    return wordlist


def choose_word(wordlist):
    """
    wordlist (list): list of words (strings)

    Returns a word from wordlist at random
    """
    return random.choice(wordlist)


# end of helper code
# -----------------------------------

# Load the list of words into the variable wordlist
# so that it can be accessed from anywhere in the program
WORDLIST = load_words()


def is_word_guessed(secret_word, letters_guessed):
    """
    secret_word: string, the word the user is guessing
    letters_guessed: list, what letters have been guessed so far
    returns: boolean, True if all letters of secret_word are in letters_guessed;
                      False otherwise
    """
    return all(map(lambda x: x in letters_guessed, secret_word))


def get_guessed_word(secret_word, letters_guessed):
    """
    secret_word: string, the word the user is guessing
    letters_guessed: list, what letters have been guessed so far
    returns: string, comprised of letters and underscores that represents
      what letters in secret_word have been guessed so far.
    """
    result = ''
    for letter in secret_word:
        if letter in letters_guessed:
            result += letter
        else:
            result += '_ '
    return result


def get_available_letters(letters_guessed):
    """
    letters_guessed: list, what letters have been guessed so far
    returns: string, comprised of letters that represents what letters have
    not yet been guessed.
    """
    import string
    letters = string.ascii_lowercase
    result = ''
    for letter in letters:
        if letter not in letters_guessed:
            result += letter
    return result


def hangman(secret_word):
    """
    secret_word: string, the secret word to guess.

    Starts up an interactive game of Hangman.

    * At the start of the game, let the user know how many
      letters the secret_word contains.

    * Ask the user to supply one guess (i.e. letter) per round.

    * The user should receive feedback immediately after each guess
      about whether their guess appears in the computers word.

    * After each round, you should also display to the user the
      partially guessed word so far, as well as letters that the
      user has not yet guessed.

    Follows the other limitations detailed in the problem write-up.
    """
    print("Welcome to the game, Hangman!")
    print("I am thinking of a word that is " + str(len(secret_word)) +
          " letters long.")

    mistakes_made = 0
    letters_guessed = []

    while mistakes_made < 8:
        print("-------------")
        print("You have " + str(8 - mistakes_made) + " guesses left.")
        available_letters = get_available_letters(letters_guessed)
        print("Available letters: " + available_letters)

        guess = input("Please guess a letter: ")
        so_far = get_guessed_word(secret_word, letters_guessed)

        if guess in letters_guessed:
            print("Oops! You've already guessed that letter: " + so_far)
        else:
            letters_guessed.append(guess)
            if guess in secret_word:
                so_far = get_guessed_word(secret_word, letters_guessed)
                print("Good guess: " + so_far)
                if is_word_guessed(secret_word, letters_guessed):
                    print("-------------")
                    print("Congratulations, you won!")
                    break
            else:
                print("Oops! That letter is not in my word: " + so_far)
                mistakes_made += 1
                if mistakes_made == 8:
                    print("-------------")
                    print("Sorry, you ran out of guesses. The word was " +
                          secret_word + ".")


# When you've completed your hangman function, uncomment these two lines
# and run this file to test! (hint: you might want to pick your own
# secret_word while you're testing)

SECRET_WORD = choose_word(WORDLIST).lower()
hangman(SECRET_WORD)
