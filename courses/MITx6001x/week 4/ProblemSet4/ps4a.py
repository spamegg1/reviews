# The 6.00 Word Game
'The Word Game'
from random import randrange

VOWELS = 'aeiou'
CONSONANTS = 'bcdfghjklmnpqrstvwxyz'
HAND_SIZE = 7

SCRABBLE_LETTER_VALUES = {
    'a': 1, 'b': 3, 'c': 3, 'd': 2, 'e': 1, 'f': 4, 'g': 2, 'h': 4, 'i': 1,
    'j': 8, 'k': 5, 'l': 1, 'm': 3, 'n': 1, 'o': 1, 'p': 3, 'q': 10, 'r': 1,
    's': 1, 't': 1, 'u': 1, 'v': 4, 'w': 4, 'x': 8, 'y': 4, 'z': 10
}

# -----------------------------------
# Helper code
# (you don't need to understand this helper code)

WORD_LIST_FILENAME = "words.txt"


def load_words():
    """
    Returns a list of valid words. Words are strings of lowercase letters.

    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # in_file: file
    in_file = open(WORD_LIST_FILENAME, 'r')
    # word_list: list of strings
    word_list = []
    for line in in_file:
        word_list.append(line.strip().lower())
    print("  ", len(word_list), "words loaded.")
    return word_list


def get_freq_dict(sequence):
    """
    Returns a dictionary where the keys are elements of the sequence
    and the values are integer counts, for the number of times that
    an element is repeated in the sequence.

    sequence: string or list
    return: dictionary
    """
    # freqs: dictionary (element_type -> int)
    freq = {}
    for key in sequence:
        freq[key] = freq.get(key, 0) + 1
    return freq


# (end of helper code)
# -----------------------------------

#
# Problem #1: Scoring a word
#
def get_word_score(word, num):
    """
    Returns the score for a word. Assumes the word is a valid word.

    The score for a word is the sum of the points for letters in the
    word, multiplied by the length of the word, PLUS 50 points if all num
    letters are used on the first turn.

    Letters are scored as in Scrabble; A is worth 1, B is worth 3, C is
    worth 3, D is worth 2, E is worth 1, and so on (see SCRABBLE_LETTER_VALUES)

    word: string (lowercase letters)
    num: integer (HAND_SIZE; i.e., hand size required for additional points)
    returns: int >= 0
    """
    score = 0
    for letter in word:
        score += SCRABBLE_LETTER_VALUES[letter]
    score *= len(word)
    if len(word) == num:
        score += 50
    return score


#
# Problem 2: Make sure you understand how this function works and what it does!
#
def display_hand(hand):
    """
    Displays the letters currently in the hand.

    For example:
    >>> display_hand({'a':1, 'x':2, 'l':3, 'e':1})
    Should print out something like:
       a x x l l l e
    The order of the letters is unimportant.

    hand: dictionary (string -> int)
    """
    print("Current hand: ", end='')
    for letter in hand.keys():
        print((letter + " ")*hand[letter], end='')  # print all on same line
    print()                                         # print an empty line

#
# Problem 2: Make sure you understand how this function works and what it does!


def deal_hand(num):
    """
    Returns a random hand containing num lowercase letters.
    At least num/3 the letters in the hand should be VOWELS.

    Hands are represented as dictionaries. The keys are
    letters and the values are the number of times the
    particular letter is repeated in that hand.

    num: int >= 0
    returns: dictionary (string -> int)
    """
    hand = {}
    num_vowels = num // 3

    for i in range(num_vowels):
        key = VOWELS[randrange(0, len(VOWELS))]
        hand[key] = hand.get(key, 0) + 1

    for i in range(num_vowels, num):
        key = CONSONANTS[randrange(0, len(CONSONANTS))]
        hand[key] = hand.get(key, 0) + 1

    return hand

#
# Problem #2: Update a hand by removing letters
#


def update_hand(hand, word):
    """
    Assumes that 'hand' has all the letters in word.
    In other words, this assumes that however many times
    a letter appears in 'word', 'hand' has at least as
    many of that letter in it.

    Updates the hand: uses up the letters in the given word
    and returns the new hand, without those letters in it.

    Has no side effects: does not modify hand.

    word: string
    hand: dictionary (string -> int)
    returns: dictionary (string -> int)
    """
    result = hand.copy()
    for letter in word:
        result[letter] -= 1
    return result


#
# Problem #3: Test word validity
#
def is_valid_word(word, hand, word_list):
    """
    Returns True if word is in the word_list and is entirely
    composed of letters in the hand. Otherwise, returns False.

    Does not mutate hand or word_list.

    word: string
    hand: dictionary (string -> int)
    word_list: list of lowercase strings
    """
    return (word in word_list) and \
        all(word.count(letter) <= hand.get(letter, 0) for letter in word)


#
# Problem #4: Playing a hand
#

def calc_hand_len(hand):
    """
    Returns the length (number of letters) in the current hand.

    hand: dictionary (string-> int)
    returns: integer
    """
    return sum(hand.values())


def play_hand(hand, word_list, num):
    """
    Allows the user to play the given hand, as follows:

    * The hand is displayed.
    * The user may input a word or a single period (the string ".")
      to indicate they're done playing
    * Invalid words are rejected, and a message is displayed asking
      the user to choose another word until they enter a valid word or "."
    * When a valid word is entered, it uses up letters from the hand.
    * After every valid word: the score for that word is displayed,
      the remaining letters in the hand are displayed, and the user
      is asked to input another word.
    * The sum of the word scores is displayed when the hand finishes.
    * The hand finishes when there are no more unused letters or the user
      inputs a "."

      hand: dictionary (string -> int)
      word_list: list of lowercase strings
      n: integer (HAND_SIZE; i.e., hand size required for additional points)

    """
    # Keep track of the total score
    score = 0

    # As long as there are still letters left in the hand:
    while calc_hand_len(hand) > 0:

        # Display the hand
        display_hand(hand)

        # Ask user for input
        word = input('Enter word, or a "." ' +
                     ' to indicate that you are finished: ')

        # If the input is a single period:
        if word == '.':
            # End the game (break out of the loop)
            break

        # Otherwise (the input is not a single period):
        else:
            # If the word is not valid:
            if not is_valid_word(word, hand, word_list):
                # Reject invalid word (print message followed by a blank line)
                print("Invalid word, please try again.")
                print()
            # Otherwise (the word is valid):
            else:
                # Tell the user how many points the word earned, and the
                # updated total score, in one line followed by a blank line
                word_score = get_word_score(word, num)
                score += word_score
                print('"' + word + '"' + ' earned ' + str(word_score)
                      + ' points. Total: ' + str(score) + ' points')
                print()
                # Update the hand
                hand = update_hand(hand, word)

    # Game is over (user entered a '.' or ran out of letters), so tell user
    # the total score
    if calc_hand_len(hand) == 0:
        print('Run out of letters. Total score: ' + str(score) + ' points.\n')
    else:
        print("Goodbye! Total score: " + str(score) + " points.\n")


#
# Problem #5: Playing a game
#

def play_game(word_list):
    """
    Allow the user to play an arbitrary number of hands.

    1) Asks the user to input 'n' or 'r' or 'e'.
      * If the user inputs 'n', let the user play a new (random) hand.
      * If the user inputs 'r', let the user play the last hand again.
      * If the user inputs 'e', exit the game.
      * If the user inputs anything else, tell them their input was invalid.

    2) When done playing the hand, repeat from step 1
    """
    # Keep track of last hand played
    last_hand = {}

    while True:
        # Ask user for input
        command = input("Enter n to deal a new hand, r to replay the last"
                        + " hand, or e to end game: ")

        # If no hand has been played, impossible to replay last hand
        while command == 'r' and not last_hand:
            print('You have not played a hand yet.', end=' ')
            print('Please play a new hand first!')
            command = input("Enter n to deal a new hand, r to replay the"
                            + " last hand, or e to end game: ")

        # If user inputs 'n', play a new random hand
        if command == 'n':
            new_hand = deal_hand(HAND_SIZE)
            # update last_hand
            last_hand = new_hand.copy()
            # play
            play_hand(new_hand, word_list, HAND_SIZE)

        # If user inputs 'r', play last hand again
        if command == 'r':
            play_hand(last_hand, word_list, HAND_SIZE)

        # If user inputs 'e', exit the game
        if command == 'e':
            return

        # If user inputs anything other than n, r, e, ask again
        if command not in ['n', 'r', 'e']:
            print("Invalid command.")


#
# Build data structures used for entire session and play game
#
if __name__ == '__main__':
    WORD_LIST = load_words()
    play_game(WORD_LIST)
