'The Word Game: User or Computer Play'
from ps4a import is_valid_word, get_word_score, calc_hand_len, display_hand
from ps4a import update_hand, deal_hand, play_hand, load_words
from ps4a import HAND_SIZE


#
#
# Computer chooses a word
#
#
def comp_choose_word(hand, word_list, num):
    """
    Given a hand and a word_list, find the word that gives
    the maximum value score, and return it.

    This word should be calculated by considering all the words
    in the word_list.

    If no words in the word_list can be made from the hand, return None.

    hand: dictionary (string -> int)
    word_list: list (string)
    num: integer (HAND_SIZE; i.e., hand size required for additional points)

    returns: string or None
    """
    # Create new variable to store the maximum score seen so far (initially 0)
    best_score = 0
    # Create new variable to store the best word seen so far (initially None)
    best_word = None
    # For each word in the word_list
    for word in word_list:
        # If you can construct the word from your hand
        if is_valid_word(word, hand, word_list):
            # find out how much making that word is worth
            score = get_word_score(word, num)
            # If the score for that word is higher than your best score
            if score > best_score:
                # update your best score, and best word accordingly
                best_score = score
                best_word = word
    # return the best word you found.
    return best_word

#
# Computer plays a hand
#
def comp_play_hand(hand, word_list, num):
    """
    Allows the computer to play the given hand, following the same procedure
    as play_hand, except instead of the user choosing a word, the computer
    chooses it.

    1) The hand is displayed.
    2) The computer chooses a word.
    3) After every valid word: the word and the score for that word is
    displayed, the remaining letters in the hand are displayed, and the
    computer chooses another word.
    4)  The sum of the word scores is displayed when the hand finishes.
    5)  The hand finishes when the computer has exhausted its possible
    choices (i.e. comp_choose_word returns None).

    hand: dictionary (string -> int)
    word_list: list (string)
    num: integer (HAND_SIZE; i.e., hand size required for additional points)
    """
    # Keep track of the total score
    total_score = 0
    # As long as there are still letters left in the hand:
    while calc_hand_len(hand) > 0:
        # Display the hand
        print("Current Hand: ", end=' ')
        display_hand(hand)
        # computer's word
        word = comp_choose_word(hand, word_list, num)
        # If the input is a single period:
        if word is None:
            # End the game (break out of the loop)
            break

        # Otherwise (the input is not a single period):
        else:
            # If the word is not valid:
            if not is_valid_word(word, hand, word_list):
                print('This is a terrible error! I need to check my own code!')
                break
            # Otherwise (the word is valid):
            else:
                # Tell the user how many points the word earned,
                # and the updated total score
                score = get_word_score(word, num)
                total_score += score
                print('"' + word + '" earned ' + str(score) + ' points. '
                      + 'Total: ' + str(total_score) + ' points')
                # Update hand and show the updated hand to the user
                hand = update_hand(hand, word)
                print()
    # Game is over (user entered a '.' or ran out of letters), so tell user the total score
    print('Total score: ' + str(total_score) + ' points.')


#
# Problem #6: Playing a game
#
#
def playgame(word_list):
    """
    Allow the user to play an arbitrary number of hands.

    1) Asks the user to input 'n' or 'r' or 'e'.
        * If the user inputs 'e', immediately exit the game.
        * If the user inputs anything that's not 'n', 'r', or 'e',
          keep asking them again.

    2) Asks the user to input a 'u' or a 'c'.
        * If the user inputs anything that's not 'c' or 'u',
          keep asking them again.

    3) Switch functionality based on the above choices:
        * If the user inputted 'n', play a new (random) hand.
        * Else, if the user inputted 'r', play the last hand again.

        * If the user inputted 'u', let the user play the game
          with the selected hand, using play_hand.
        * If the user inputted 'c', let the computer play the
          game with the selected hand, using comp_play_hand.

    4) After the computer or user has played the hand, repeat from step 1

    word_list: list (string)
    """
    # Keep track of last hand played
    last_hand = {}

    while True:
        # Ask user to input 'n' or 'r' or 'e'
        command = input("Enter n to deal a new hand, r to replay the last" +
                        " hand, or e to end game: ")


        # If user inputs anything that's not 'n', 'r', or 'e',
        # keep asking them again
        while command not in ['n', 'r', 'e']:
            print("Invalid command.")
            command = input("Enter n to deal a new hand, r to replay the" +
                            " last hand, or e to end game: ")

        # If no hand has been played, impossible to replay last hand
        while command == 'r' and not last_hand:
            print('You have not played a hand yet.', end=' ')
            print('Please play a new hand first!')
            command = input("Enter n to deal a new hand, r to replay the"
                            + " last hand, or e to end game: ")

        # If user inputs 'e', immediately exit game
        if command == 'e':
            return

        # Ask the user to input a 'u' or a 'c'
        player = input("Enter u to have yourself play, c to have the computer"
                       + " play: ")

        # If the user inputs anything that's not 'c' or 'u',
        # keep asking them again
        while player not in ['u', 'c']:
            print("Invalid command.")
            player = input("Enter u to have yourself play, c to have the"
                           + " computer play: ")

        if command == 'n':
            # deal new random hand
            new_hand = deal_hand(HAND_SIZE)

            # update last_hand
            last_hand = new_hand.copy()

        elif command == 'r':
            new_hand = last_hand

        if player == 'u':
            # call play_hand
            play_hand(new_hand, word_list, HAND_SIZE)

        elif player == 'c':
            # call comp_play_hand
            comp_play_hand(new_hand, word_list, HAND_SIZE)


#
# Build data structures used for entire session and play game
#
if __name__ == '__main__':
    WORD_LIST = load_words()
    playgame(WORD_LIST)
