# -*- coding: utf-8 -*-
"""
Created on Wed Sep 18 22:29:44 2019.

@author: spamegg1
"""
import heapq as h
from random import shuffle


def heapsort(array):
    """Take an array of n distinct integers as input.

    Return array with same integers sorted from smallest to largest.
    """
    h.heapify(array)                                        # takes linear time
    return [h.heappop(array) for _ in range(len(array))]       # takes O(nlogn)


# TESTING
if __name__ == "__main__":
    SORTED = [i for i in range(1, 101)]
    RANDOM = [i for i in range(1, 101)]
    shuffle(RANDOM)
    assert heapsort(RANDOM) == SORTED
    print("tests pass")
