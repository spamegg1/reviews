# -*- coding: utf-8 -*-
"""
Created on Tue Sep  3 20:04:26 2019.

@author: spamegg1
"""


# INVERSION COUNTING PROGRAM
#
# Given an array of distinct positive integers A, the number of inversions is
# the number of pairs (i, j) of array indices with i < j and A[i] > A[j].
#
# For example, if A = [1, 3, 5, 2, 4, 6] then there are 3 inversions in total:
# (3, 5), (5, 2) and (5, 4).
#
# We observe that the number of inversions can be calculated by counting the
# number of times a "swap" occurs when the array A goes through mergesort.
#
# For example, if A = [1, 3, 5, 2, 4, 6] then a mergesort algorithm would
# divide A into [1, 3, 5] and [2, 4, 6] first (both of which are already
# ordered). When the mergesort algorithm then tries to merge these two
# sublists, first it would compare 1 (left) to 2 (right); since 1 < 2, it
# would choose 1 (left). Next it would compare the next element of the left
# list, which is 3, to 2. But now 3 > 2, so it would pick 2 (right). Since it
# picked 1 from the LEFT sublist the last time, and now it picked 2 from the
# RIGHT sublist, a "swap" occurred: an element (2) from the RIGHT sublist was
# smaller than an element (3) from the LEFT sublist, so it had to come before
# 3, so it was "swapped" to the left of 3. This means 2 was swapped to the
# left of 5 as well, which is another inversion. So the number of inversions
# upon a swap is the size of the remaining sublist that 2 got swapped to the
# left of (namely [3, 5]).
#
# If we continue in this manner, we see that 3 and 4 get compared, and 3 is
# picked, since 3 was already to the left of 4, no swap occurred; then 5 and 4
# are compared, since 5 > 4, 4 gets picked and a swap occurs because now 4
# moved to the left of [5], whereas it was to the right of 5 before. Then 5
# and 6 are compared, and 5 is picked, which was already on the left.
#
# So the total number of inversions of an array A is equal to the number of
# "swaps" it accrues as it goes through the mergesort algorithm.


class Inversion():
    """
    Object to keep track of inversions of a list.

    Inversion count is updated as the list goes through mergesort algorithm.
    """

    def __init__(self):
        """Constructor."""
        self.inversions = 0
        self.array = []

    def get_inversions(self):
        """Return inversion count."""
        return self.inversions

    def set_inversions(self, inver):
        """Set inversions."""
        self.inversions = inver

    def get_array(self):
        """Return array. Not important."""
        return self.array

    def set_array(self, arr):
        """Set array, so that array returned by mergesort is not unused."""
        self.array = arr


def merge(left, right, inver):
    """Take two ascending ordered, disjoint lists of integers as input.

    Returns an ordered list, which is a union of the two lists.
    """
    # Base cases: if either the left or the right is empty
    if not left:
        return right
    if not right:
        return left

    # Main case: this will be the array when we merge left and right
    leftl = len(left)
    rightl = len(right)
    result = [0 for x in range(leftl + rightl)]
    i = 0  # keep track of index of left
    j = 0  # keep track of index of right
    k = 0  # keep track of index of result

    while i < leftl or j < rightl:  # go through both lists together
        if i < leftl and j < rightl:
            if left[i] < right[j]:  # left's next elt is smaller than right's
                result[k] = left[i]  # add left's elt to merged list
                i += 1  # move onto next entry in left
            else:  # right's next element must be smaller than left's
                result[k] = right[j]  # add right's elt to merged list
                j += 1  # move onto next entry in right
                # THIS IS AN INVERSION! right's entry got added before left's
                current = inver.get_inversions()  # get inversion count
                inver.set_inversions(current + leftl - i)  # update it
        elif i == leftl and j < rightl:  # ran out of left! NO INVERSION
            result[k] = right[j]  # add right's elt to merged list
            j += 1  # move onto next entry in right
        elif i < leftl and j == rightl:
            result[k] = left[i]  # add left's elt to merged list
            i += 1  # move onto next entry in left
        k += 1  # move onto next entry in result

    return result


def mergesort(whole, inver):
    """Take a list of integers and an inversion object as input.

    Returns a new, sorted version of that list, in ascending order.
    Updates inversion count along the way via merge() and the object.
    """
    size = len(whole)

    # Base case: list is a singleton
    if size == 1:
        return whole

    # split list into halves
    half = int(size/2)
    left = whole[:half]  # for list of odd size, left is 1 smaller than right
    right = whole[half:]  # for list of odd size, right is 1 bigger than left

    # sort the left half and the right half
    left_sorted = mergesort(left, inver)
    right_sorted = mergesort(right, inver)

    # merge the two sorted halves
    return merge(left_sorted, right_sorted, inver)


def inversions(array):
    """Take a list of distinct integers as input.

    Returns the total number of inversions present in the list.
    """
    inv = Inversion()  # new blank inversion object with 0 inversion count
    inv.set_array(mergesort(array, inv))  # run array through mergesort
    return inv.get_inversions()  # return the inversion count that accumulated


if __name__ == "__main__":
    CHAL = []  # list to be tested for inversions
    with open('invchal.txt', 'r') as invrchal:  # this file has 1000 numbers
        for line in invrchal:  # convert text file to list
            CHAL.append(int(line))  # each line in text file becomes an entry
    assert inversions(CHAL) == 242698

    CHAL = []  # list to be tested for inversions
    with open('problem3.5test.txt', 'r') as invrchal:
        for line in invrchal:  # convert text file to list
            CHAL.append(int(line))  # each line in text file becomes an entry
    assert inversions(CHAL) == 28

    # THIS IS THE INSANE CHALLENGE DATA SET! 100000 integers!
    CHAL = []  # list to be tested for inversions
    with open('IntegerArray.txt', 'r') as invrchal:
        for line in invrchal:  # convert text file to list
            CHAL.append(int(line))  # each line in text file becomes an entry
    assert inversions(CHAL) == 2407905288

    print("tests pass")
