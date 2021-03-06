'Hand'
import random


class Hand(object):
    def __init__(self, n):
        '''
        Initialize a Hand.

        n: integer, the size of the hand.
        '''
        assert type(n) == int
        self.HAND_SIZE = n
        self.VOWELS = 'aeiou'
        self.CONSONANTS = 'bcdfghjklmnpqrstvwxyz'

        # Deal a new hand
        self.dealNewHand()

    def dealNewHand(self):
        '''
        Deals a new hand, and sets the hand attribute to the new hand.
        '''
        # Set self.hand to a new, empty dictionary
        self.hand = {}

        # Build the hand
        numVowels = self.HAND_SIZE // 3

        for i in range(numVowels):
            x = self.VOWELS[random.randrange(0, len(self.VOWELS))]
            self.hand[x] = self.hand.get(x, 0) + 1

        for i in range(numVowels, self.HAND_SIZE):
            x = self.CONSONANTS[random.randrange(0, len(self.CONSONANTS))]
            self.hand[x] = self.hand.get(x, 0) + 1

    def setDummyHand(self, handString):
        '''
        Allows you to set a dummy hand. Useful for testing your implementation.

        handString: A string of letters you wish to be in the hand. Length of
        this string must be equal to self.HAND_SIZE.

        This method converts sets the hand attribute to a dictionary
        containing the letters of handString.
        '''
        st = "Length of handString ({0}) must equal length of HAND_SIZE ({1})"
        frmtd_st = st.format(len(handString), self.HAND_SIZE)
        assert len(handString) == self.HAND_SIZE, frmtd_st
        self.hand = {}
        for char in handString:
            self.hand[char] = self.hand.get(char, 0) + 1

    def calculateLen(self):
        '''
        Calculate the length of the hand.
        '''
        ans = 0
        for k in self.hand:
            ans += self.hand[k]
        return ans

    def __str__(self):
        '''
        Display a string representation of the hand.
        '''
        output = ''
        hand_keys = sorted(self.hand.keys())
        for letter in hand_keys:
            for j in range(self.hand[letter]):
                output += letter
        return output

    def update(self, word):
        """
        Does not assume that self.hand has all the letters in word.

        Updates the hand: if self.hand does have all the letters to make
        the word, modifies self.hand by using up the letters in the given word.

        Returns True if the word was able to be made with the letter in
        the hand; False otherwise.

        word: string
        returns: Boolean (if the word was or was not made)
        """
        if all(word.count(letter) <= self.hand.get(letter, 0)
               for letter in word):
            for letter in word:
                self.hand[letter] -= 1
            return True
        return False


myHand = Hand(7)
print(myHand)
print(myHand.calculateLen())

myHand.setDummyHand('aazzmsp')
print(myHand)
print(myHand.calculateLen())

myHand.update('zaza')
print(myHand)
print(myHand.calculateLen())
