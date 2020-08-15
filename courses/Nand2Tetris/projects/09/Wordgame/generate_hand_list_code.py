# Generates Jack code for creating a list of 100 hands
# spamegg 1/20/2019
from random import randrange


VOWELS = "aeiou".upper()
CONSONANTS = "bcdfghjklmnpqrstvwxyz".upper()
HAND_SIZE = 8


def deal_hand(num):
    """
    Returns a random hand containing num lowercase letters.
    At least num/3 the letters in the hand should be VOWELS.

    Hands are represented as dictionaries. The keys are
    letters and the values are the number of times the
    particular letter is repeated in that hand.

    num: int >= 0
    returns: string
    """
    hand = '"'
    num_vowels = num // 3

    for i in range(num_vowels):
        vowel = VOWELS[randrange(0, len(VOWELS))]
        hand += vowel

    for i in range(num_vowels, num):
        consonant = CONSONANTS[randrange(0, len(CONSONANTS))]
        hand += consonant

    hand += '"'

    return hand


outfile = open('handslist.txt', 'w')

for i in range(50):
    currenthand = deal_hand(HAND_SIZE)
    outfile.write('let handlist[' + str(i) + '] = ' + currenthand + ';\n')
