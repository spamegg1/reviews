# -*- coding: utf-8 -*-
"""
Created on Sat Sep 21 18:56:56 2019.

@author: spamegg1
"""


class SortedArray():
    """Sorted Array class."""

    def __init__(self):
        """."""
        self.array = []

    def __str__(self):
        """Pretty printing for SortedArray."""
        return str(self.array)

    def search(self, key, array):
        """Return index of key in array, or None. Runs in O(logn)."""
        if len(array) == 1 and key not in array:
            return None

        mid = len(array)//2
        if array[mid] == key:
            return mid
        if array[mid] > key:
            return self.search(key, array[:mid])
        if array[mid] < key:
            result = self.search(key, array[mid:])
            if result:  # if result is not None
                return result + mid
            return result  # return None

    def smin(self):
        """Return min key of sorted array in O(1)."""
        return self.array[0]

    def smax(self):
        """Return max key of sorted array in O(1)."""
        return self.array[-1]

    def predecessor(self, key):
        """Return index of predecessor of key in array, or None in O(logn)."""
        result = self.search(key, self.array)
        if result in [0, None]:
            return None
        return result - 1

    def successor(self, key):
        """Return index of successor of key in array, or None in O(logn)."""
        result = self.search(key, self.array)
        if result in [len(self.array) - 1, None]:
            return None
        return result + 1

    def output_sorted(self):
        """Output elements in increasing order in O(n)."""
        for key in self.array:
            print(key)

    def select(self, index):
        """Given index i, return ith smallest element in array in O(1)."""
        return self.array[index]

    def rank(self, key):
        """Given key k, return number of elts with key at most k in O(logn)."""
        def helper(key, array):
            if len(array) == 1:
                if key < array[0]:
                    return -1
                if key not in array:
                    return 0
            mid = len(array)//2
            if array[mid] == key:
                return mid
            if array[mid] < key:
                return helper(key, array[mid:]) + mid
            if array[mid] > key:
                return helper(key, array[:mid])
        return helper(key, self.array) + 1


# TESTING
if __name__ == "__main__":
    TEST = SortedArray()
    TEST.array = [3, 6, 10, 11, 17, 23, 30, 36]

    # testing search
    for i in range(8):
        assert TEST.search(TEST.array[i], TEST.array) == i
    assert TEST.search(1, TEST.array) is None
    assert TEST.search(16, TEST.array) is None
    assert TEST.search(37, TEST.array) is None

    # testing rank
    assert TEST.rank(2) == 0
    assert TEST.rank(3) == 1  #
    assert TEST.rank(4) == 1
    assert TEST.rank(5) == 1
    assert TEST.rank(6) == 2  #
    assert TEST.rank(7) == 2
    assert TEST.rank(9) == 2
    assert TEST.rank(10) == 3  #
    assert TEST.rank(11) == 4  #
    assert TEST.rank(12) == 4
    assert TEST.rank(16) == 4
    assert TEST.rank(17) == 5  #
    assert TEST.rank(18) == 5
    assert TEST.rank(22) == 5
    assert TEST.rank(23) == 6  #
    assert TEST.rank(24) == 6
    assert TEST.rank(29) == 6
    assert TEST.rank(30) == 7  #
    assert TEST.rank(31) == 7
    assert TEST.rank(35) == 7
    assert TEST.rank(36) == 8  #
    assert TEST.rank(37) == 8
    assert TEST.rank(100) == 8
    print("tests pass")
