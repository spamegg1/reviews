# -*- coding: utf-8 -*-
"""
Created on Thu Sep  5 13:44:59 2019.

@author: spamegg1
"""


def fixedpoint(array):
    """Take sorted, increasing array of distinct integers.

    Return True if there is an index i such that array[i] = i, False otherwise
    Runs in log(n) time.
    """
    size = len(array)
    # base case: array is a singleton
    if size == 1:
        return array[0] == 0

    # not base case, but can help speed up
    # since array is increasing and elements are all distinct,
    # if a[0] is positive or a[end] < end, it's impossible to have fixed pts
    if array[0] > 0 or array[size - 1] < size - 1:
        return False

    # main case: split into two,
    mid = size//2
    if array[mid] == mid:
        return True

    # if array[mid] > mid, right half can't have fixed pt,recurse on left half
    if array[mid] > mid:
        left = array[:mid]
        return fixedpoint(left)

    # otherwise recurse on right half, reduce entries of right half by size/2
    # (we need to do that because right half's indices are shifted backward)
    # for example, [-10, -3, 3, 10] becomes [-10, -3] and [1, 8]
    right = [array[i] - mid for i in range(mid, size)]
    return fixedpoint(right)


# TESTING
if __name__ == "__main__":
    assert fixedpoint([0])
    assert fixedpoint([-1, 1])
    assert fixedpoint([-3, 0, 2])
    assert fixedpoint([-10, -3, 2, 10])
    assert fixedpoint([-10, -3, 1, 2, 3, 5])
    assert fixedpoint([1]) is False
    assert fixedpoint([-1, 2]) is False
    assert fixedpoint([-3, 0, 3]) is False
    assert fixedpoint([-10, -3, 3, 10]) is False
    assert fixedpoint([-10, -3, 1, 4, 5, 8]) is False
    print("tests pass")
