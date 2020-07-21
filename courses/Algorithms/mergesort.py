# -*- coding: utf-8 -*-
"""
Created on Tue Sep  3 17:40:31 2019.

@author: spamegg1
"""


# Merge Sort

def merge(left, right):
    """Take two ascending ordered, disjoint lists of integers as input.

    Returns an ordered list, which is a union of the two lists.
    """
    # Base cases: if either the left or the right is empty
    if not left:
        return right
    if not right:
        return left

    # Main case: both lists are nonempty.
    least_left = left[0]
    least_right = right[0]

    # If least element L of left is smaller than least element R of right,
    # then L is less than all elements of right (because right ascends).
    # So L will be the least element in the merged list. (And vice versa.)
    if least_left < least_right:
        return [least_left] + merge(left[1:], right)
    # since left and right are disjoint, L and R are never equal
    return [least_right] + merge(left, right[1:])


def mergesort(whole):
    """Take a list of integers as input.

    Returns a new, sorted version of that list, in ascending order.
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
    left_sorted = mergesort(left)
    right_sorted = mergesort(right)

    # merge the two sorted halves
    return merge(left_sorted, right_sorted)


# Testing
if __name__ == "__main__":
    MYLIST = [10, 2, 8, 4, 6, 11, 5, 7, 3, 9, 1]
    MYLIST_SORTED = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
    assert mergesort(MYLIST) == MYLIST_SORTED
    assert mergesort([1, 1, 1, 1]) == [1, 1, 1, 1]

    HUNDRED = [57, 15, 72, 22, 91, 92, 29, 52, 16, 42,
               84, 25, 54, 45, 11, 56, 53, 50, 20, 88,
               43, 41, 38, 2, 35, 83, 65, 75, 80, 34,
               76, 36, 82, 63, 46, 64, 23, 96, 71, 69,
               62, 49, 26, 1, 94, 77, 85, 70, 10, 31,
               7, 21, 68, 24, 17, 93, 5, 73, 18, 78,
               61, 3, 86, 81, 58, 79, 47, 40, 14, 4,
               19, 66, 95, 90, 13, 87, 98, 44, 30, 60,
               48, 59, 9, 100, 51, 27, 55, 39, 33, 99,
               32, 37, 28, 67, 12, 74, 8, 97, 89, 6]
    HUNDRED_SORTED = [i+1 for i in range(100)]
    assert mergesort(HUNDRED) == HUNDRED_SORTED

    print("tests pass")
