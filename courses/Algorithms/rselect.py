# -*- coding: utf-8 -*-
"""
Created on Mon Sep  9 14:40:20 2019.

@author: spamegg1
"""
from random import shuffle
import quicksort as qs


def rselect(array, order):
    """Take an n-sized array and an integer i, 1 <= i <= n, as input.

    Return ith order statistic (the ith smallest element) in array (order=i).
    """
    size = len(array)

    # base case
    if size == 1:
        return array[0]

    # main case
    comp = qs.Comparison()

    # choose pivot randomly from whole array
    pivot = qs.pivot_rand(array, 0, size-1)

    # move pivot element to the leftmost position in array
    array[0], array[pivot] = array[pivot], array[0]

    # partition whole array around pivot, return final location of pivot
    newpivot = qs.partition(array, 0, size-1, comp)

    # since 1 <= order <= n, we decrement order to comply with array indices
    # check where pivot is compared to ith order stat
    if order - 1 == newpivot:  # pivot is ith order, by dumb luck
        return array[newpivot]  # done!
    if order - 1 < newpivot:  # ith order is to the left of pivot
        return rselect(array[:newpivot], order)  # recurse on left of pivot

    # otherwise ith order is to the right of pivot, recurse on right of pivot
    # instead of looking for ith order stat, look for (i - pivot)th order stat
    return rselect(array[newpivot + 1:], order - 1 - newpivot)


# TESTING
if __name__ == "__main__":
    # basic tests
    TESTLIST = [i+1 for i in range(100)]
    for i in range(len(TESTLIST)):
        shuffle(TESTLIST)  # shuffle integers from 1 to 100
        assert rselect(TESTLIST, i + 1) == i + 1

    # Challenge tests
    FILES = [('problem6.5test1.txt', 5469),
             ('problem6.5test2.txt', 4715),
             ('10000.txt', 5048608250)]
    for filename, answer in FILES:
        arr = []
        with open(filename, 'r') as rselectchal:
            for line in rselectchal:  # convert text file to list
                arr.append(int(line))  # fill array with textfile
        assert rselect(arr, len(arr)//2) == answer

    print("tests pass")
