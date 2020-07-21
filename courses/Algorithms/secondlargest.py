# -*- coding: utf-8 -*-
"""
Created on Wed Sep  4 10:21:24 2019.

@author: spamegg1
"""
from math import log2


# FIND THE SECOND LARGEST ALGORITHM
# Given an unsorted array of n numbers where n is a power of 2,
# this algorithm finds the second largest number in the array with no more
# than n + log_2(n) - 2 comparisons (amount of space used does not matter).
# We do this by using a divide and conquer algorithm to find the largest
# number, but we keep track of the "losers": the numbers that "lost" to the
# largest number along the way.

class Comparison():
    """
    Object to keep track of comparisons.

    Comparison count is updated as the list goes through largest algorithm.
    """

    def __init__(self):
        """Constructor."""
        self.comparisons = 0  # comparison count
        self.second = 0  # the second largest number in array

    def get_comparisons(self):
        """Return inversion count."""
        return self.comparisons

    def set_comparisons(self, comp):
        """Set inversions."""
        self.comparisons = comp


def compare(left, right, comp):
    """Take two (number, list) pairs.

    Returns the larger of the two numbers, along with its list now appended
    by the other number.
    """
    current = comp.get_comparisons()  # get current comparison count
    comp.set_comparisons(current + 1)  # increment it by 1 (comparison below)
    if left[0] > right[0]:  # here is the comparison
        return (left[0], left[1] + [right[0]])
    return (right[0], right[1] + [left[0]])


def largest(whole, comp):
    """Take unsorted array of numbers of size n (a power of 2) as input.

    Return largest number in array in at most n - 1 comparisons.
    Keep track of comparison count with a Comparison object.
    """
    size = len(whole)

    # Base case: list is a singleton
    if size == 1:
        return (whole[0], [])  # largest number and the (empty) list of losers

    # split list into halves
    half = size//2
    left = whole[:half]
    right = whole[half:]

    # find largest numbers in the left half and the right half
    left_largest = largest(left, comp)  # largest num on left, loser list
    right_largest = largest(right, comp)  # largest num on right, loser list

    # compare the largest of two halves (uses n - 1 comparisons total)
    return compare(left_largest, right_largest, comp)


def second_largest(array, comp):
    """Take array of n distinct numbers as input where n is a power of 2.

    Return second largest number in array in at most n + log_2(n) - 2 comps.
    """
    loserlist = largest(array, comp)[1]  # loserlist has size log_2(n)
    current = comp.get_comparisons()
    # max uses at most log_2(n) - 1 comparisons.
    comp.set_comparisons(current + int(log2(len(array))) - 1)
    return max(loserlist)


def comparison_counter(array):
    """Take array of n distinct numbers, where n is a power of 2.

    Returns second largest number and the number of comparisons it took.
    """
    comp = Comparison()  # fresh new Comparison object with count 0
    # put array through algorithm, this updates comparison count
    second = second_largest(array, comp)
    return second, comp.get_comparisons()


if __name__ == "__main__":
    EIGHT = [8, 1, 7, 2, 6, 3, 5, 4]  # 2^3, so 8+3-2=9 comparisons
    SIXTEEN = [16, 1, 15, 2, 14, 3, 13, 4, 12, 5, 11, 6, 10, 7, 9, 8]  # 2^4
    SIXTYFOUR = [57, 15, 72, 22, 91, 92, 29, 52, 16, 42,
                 84, 25, 54, 45, 11, 56, 53, 50, 20, 88,
                 43, 41, 38, 2, 35, 83, 65, 75, 80, 34,
                 76, 36, 82, 63, 46, 64, 23, 96, 71, 69,
                 62, 49, 26, 1, 94, 77, 85, 70, 10, 31,
                 7, 21, 68, 24, 17, 93, 5, 73, 18, 78,
                 61, 3, 86, 81]  # 96 max, 94 second, 2^6, 64+6-2=68 com
    print(comparison_counter(EIGHT))
    print(comparison_counter(SIXTEEN))
    print(comparison_counter(SIXTYFOUR))
