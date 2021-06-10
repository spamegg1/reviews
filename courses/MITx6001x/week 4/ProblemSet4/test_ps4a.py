'Tests for ps4a'
from ps4a import getWordScore, getFreqDict, updateHand, isValidWord
from ps4a import loadWords

#
# Test code
# You don't need to understand how this test code works
# (but feel free to look it over!)

# To run these tests, simply run this file (open up in your IDE,
# then run the file as normal)


def test_get_word_score():
    """
    Unit test for get_word_score
    """
    failure = False
    # dictionary of words and scores
    words = {("", 7): 0, ("it", 7): 4, ("was", 7): 18, ("scored", 7): 54,
             ("waybill", 7): 155, ("outgnaw", 7): 127, ("fork", 7): 44,
             ("fork", 4): 94}
    for key in words:
        (word, num) = key
        score = getWordScore(word, num)
        if score != words[(word, num)]:
            print("FAILURE: test_get_word_score()")
            print("\tExpected", words[(word, num)], "points but got '"
                  + str(score) + "' for word '" + word + "', n=" + str(num))
            failure = True
    if not failure:
        print("SUCCESS: test_get_word_score()")

# end of test_get_word_score


def test_update_hand():
    """
    Unit test for update_hand
    """
    # test 1
    hand_orig = {'a': 1, 'q': 1, 'l': 2, 'm': 1, 'u': 1, 'i': 1}
    hand_copy = hand_orig.copy()
    word = "quail"

    hand2 = updateHand(hand_copy, word)
    expected_hand1 = {'l': 1, 'm': 1}
    expected_hand2 = {'a': 0, 'q': 0, 'l': 1, 'm': 1, 'u': 0, 'i': 0}
    if hand2 not in (expected_hand1, expected_hand2):
        print("FAILURE: test_update_hand('" + word + "', "
              + str(hand_orig) + ")")
        print("\tReturned: ", hand2, "\n\t-- but expected:", expected_hand1,
              "or", expected_hand2)

        return  # exit function
    if hand_copy != hand_orig:
        print("FAILURE: test_update_hand('" + word + "', "
              + str(hand_orig) + ")")
        print("\tOriginal hand was", hand_orig)
        print("\tbut implementation of update_hand mutated the original hand!")
        print("\tNow the hand looks like this:", hand_copy)

        return  # exit function

    # test 2
    hand_orig = {'e': 1, 'v': 2, 'n': 1, 'i': 1, 'l': 2}
    hand_copy = hand_orig.copy()
    word = "evil"

    hand2 = updateHand(hand_copy, word)
    expected_hand1 = {'v': 1, 'n': 1, 'l': 1}
    expected_hand2 = {'e': 0, 'v': 1, 'n': 1, 'i': 0, 'l': 1}
    if hand2 not in (expected_hand1, expected_hand2):
        print("FAILURE: test_update_hand('" + word + "', "
              + str(hand_orig) + ")")
        print("\tReturned: ", hand2, "\n\t-- but expected:",
              expected_hand1, "or", expected_hand2)

        return  # exit function

    if hand_copy != hand_orig:
        print("FAILURE: test_update_hand('" + word + "', "
              + str(hand_orig) + ")")
        print("\tOriginal hand was", hand_orig)
        print("\tbut implementation of update_hand mutated the original hand!")
        print("\tNow the hand looks like this:", hand_copy)

        return  # exit function

    # test 3
    hand_orig = {'h': 1, 'e': 1, 'l': 2, 'o': 1}
    hand_copy = hand_orig.copy()
    word = "hello"

    hand2 = updateHand(hand_copy, word)
    expected_hand1 = {}
    expected_hand2 = {'h': 0, 'e': 0, 'l': 0, 'o': 0}
    if hand2 not in (expected_hand1, expected_hand2):
        print("FAILURE: test_update_hand('" + word + "', "
              + str(hand_orig) + ")")
        print("\tReturned: ", hand2, "\n\t-- but expected:", expected_hand1,
              "or", expected_hand2)

        return  # exit function

    if hand_copy != hand_orig:
        print("FAILURE: test_update_hand('" + word + "', "
              + str(hand_orig) + ")")
        print("\tOriginal hand was", hand_orig)
        print("\tbut implementation of update_hand mutated the original hand!")
        print("\tNow the hand looks like this:", hand_copy)

        return  # exit function

    print("SUCCESS: test_update_hand()")

# end of test_update_hand


def test_is_valid_word(word_list):
    """
    Unit test for is_valid_word
    """
    failure = False
    # test 1
    word = "hello"
    hand_orig = getFreqDict(word)
    hand_copy = hand_orig.copy()

    if not isValidWord(word, hand_copy, word_list):
        print("FAILURE: test_is_valid_word()")
        print("\tExpected True, but got False for word: '" + word
              + "' and hand:", hand_orig)

        failure = True

    # Test a second time to see if word_list or hand has been modified
    if not isValidWord(word, hand_copy, word_list):
        print("FAILURE: test_is_valid_word()")

        if hand_copy != hand_orig:
            print("\tTesting word", word,
                  "for a second time - be sure you're not modifying hand.")
            print("\tAt this point, hand ought to be", hand_orig,
                  "but it is", hand_copy)

        else:
            print("\tTesting word", word,
                  "for a second time - have you modified word_list?")
            word_in_wl = word in word_list
            print("The word", word, "should be in word_list - is it?",
                  word_in_wl)

        print("\tExpected True, but got False for word: '"
              + word + "' and hand:", hand_copy)

        failure = True

    # test 2
    hand = {'r': 1, 'a': 3, 'p': 2, 'e': 1, 't': 1, 'u': 1}
    word = "rapture"

    if isValidWord(word, hand, word_list):
        print("FAILURE: test_is_valid_word()")
        print("\tExpected False, but got True for word: '"
              + word + "' and hand:", hand)

        failure = True

    # test 3
    hand = {'n': 1, 'h': 1, 'o': 1, 'y': 1, 'd': 1, 'w': 1, 'e': 2}
    word = "honey"

    if not isValidWord(word, hand, word_list):
        print("FAILURE: test_is_valid_word()")
        print("\tExpected True, but got False for word: '"
              + word + "' and hand:", hand)

        failure = True

    # test 4
    hand = {'r': 1, 'a': 3, 'p': 2, 't': 1, 'u': 2}
    word = "honey"

    if isValidWord(word, hand, word_list):
        print("FAILURE: test_is_valid_word()")
        print("\tExpected False, but got True for word: '"
              + word + "' and hand:", hand)

        failure = True

    # test 5
    hand = {'e': 1, 'v': 2, 'n': 1, 'i': 1, 'l': 2}
    word = "evil"

    if not isValidWord(word, hand, word_list):
        print("FAILURE: test_is_valid_word()")
        print("\tExpected True, but got False for word: '"
              + word + "' and hand:", hand)

        failure = True

    # test 6
    word = "even"

    if isValidWord(word, hand, word_list):
        print("FAILURE: test_is_valid_word()")
        print("\tExpected False, but got True for word: '"
              + word + "' and hand:", hand)
        print("\t(If this is the only failure, make sure is_valid_word()"
              + " isn't mutating its inputs)")

        failure = True

    if not failure:
        print("SUCCESS: test_is_valid_word()")


WORD_LIST = loadWords()
print("----------------------------------------------------------------------")
print("Testing get_word_score...")
test_get_word_score()
print("----------------------------------------------------------------------")
print("Testing update_hand...")
test_update_hand()
print("----------------------------------------------------------------------")
print("Testing is_valid_word...")
test_is_valid_word(WORD_LIST)
print("----------------------------------------------------------------------")
print("All done!")
