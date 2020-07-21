# -*- coding: utf-8 -*-
"""
Created on Thu Sep  5 13:20:30 2019.

@author: spamegg1
"""


def unimodal(array):
    """Take a unimodal array of n distinct numbers as input.

    Return largest number in array. Does not check if input is unimodal.
    Unimodal means the array is in increasing order up to its maximum element,
    after which it is in decreasing order.
    e.g. [3,4,5,6,2,1] is unimodal.
    Running time is Big Theta of logarithm of input size.
    """
    size = len(array)
    # base case: array has only one or two elements
    if size == 1:
        return array[0]
    if size == 2:
        return max(array[0], array[1])

    # main case
    mid = size//2
    # if middle element is bigger than both its neighbors, it's largest
    if array[mid] > max(array[mid - 1], array[mid + 1]):
        return array[mid]
    # otherwise recurse on the half that has the larger neighbor
    if array[mid - 1] > array[mid]:
        return unimodal(array[:mid])
    return unimodal(array[mid:])


# TESTING
if __name__ == "__main__":
    assert unimodal([1, 2, 3, 4, 5, 4, 3, 2, 1]) == 5
    assert unimodal([5, 4, 3, 2, 1]) == 5
    assert unimodal([1, 2, 3, 4, 5]) == 5
    assert unimodal([5]) == 5
    assert unimodal([4, 5]) == 5
    assert unimodal([5, 4]) == 5
    assert unimodal([1, 3, 50, 10, 9, 7, 6]) == 50
    assert unimodal([10, 20, 30, 40, 50]) == 50
    assert unimodal([120, 100, 80, 20, 0]) == 120
    assert unimodal([8, 10, 20, 80, 100, 200, 400, 500, 3, 2, 1]) == 500
    print("tests pass")
