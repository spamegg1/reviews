# -*- coding: utf-8 -*-
"""
Created on Fri Sep 20 17:59:41 2019.

@author: spamegg1
"""
from random import shuffle


class Myheap():
    """Implements heaps using arrays."""

    def __init__(self):
        """Create empty heap."""
        self.array = []
        self.size = 0

    def __str__(self):
        """Pretty printing for Myheap objects."""
        return str(self.array)

    def insert(self, elt):
        """Insert elt into heap in O(logn) time, maintain heap invariant."""
        self.array.append(elt)             # add node to the right end of array
        self.size += 1        # increment array size, now elt = array[size - 1]
        child = self.size - 1  # child is the last (newly added) elt's index
        siftup(self.array, child)  # restore heap invariant by sifting child up

    def extractmin(self):
        """Pop and return heap element with minimum key in O(logn) time.

        Maintain heap invariant.
        """
        # by heap invariant, min elt is always the first elt (index 0)
        # swap first and last elements, pop last elt, decrement array size
        self.array[0], self.array[-1] = self.array[-1], self.array[0]
        minval = self.array.pop()
        self.size -= 1
        # new first element violates heap invariant, restore it by sifting down
        siftdown(self.array, self.size, 0)
        return minval

    def findmin(self):
        """Return heap element with minimum key in O(1) time."""
        return self.array[0]

    def heapify(self, array):
        """Heapify given list in O(n) and assign it to this heap object."""
        size = len(array)
        for index in range(size):
            siftup(array, index)
        self.array = array
        self.size = size

    def delete(self, index):
        """Delete element from heap in O(logn), maintain heap invariant."""
        self.array[index] = self.array[-1]    # move elt to last place in array
        self.array.pop()                                      # remove last elt
        self.size -= 1                                    # decrement heap size
        if index < self.size:
            parent, child1, child2 = (index - 1)//2, 2*index + 1, 2*index + 2
            if 0 <= parent and self.array[index] < self.array[parent]:
                siftup(self.array, index)
            elif child1 < self.size and self.array[child1] < self.array[index]:
                siftdown(self.array, self.size, index)
            elif child2 < self.size and self.array[child2] < self.array[index]:
                siftdown(self.array, self.size, index)


def siftup(array, child):
    """Take an array and an array index as input.

    'Sift' the child entry up the heap structure until heap invariant restores.
    """
    # if parent has index i, child has index 2i+1 or 2i+2
    parent = child // 2 - 1 if child % 2 == 0 else (child - 1) // 2

    # while heap invariant is violated:
    while array[child] < array[parent] and parent >= 0:
        # swap child and parent
        array[child], array[parent] = array[parent], array[child]
        # continue upward. Parent becomes new child, go up to grandparent etc.
        child = parent
        parent = child // 2 - 1 if child % 2 == 0 else (child - 1) // 2


def siftdown(array, size, parent):
    """Take an array, its size, and an array index as input.

    'Sift' the parent entry down heap structure until heap invariant restores.
    """
    # if parent has index i, children have indices 2i+1, 2i+2
    child1, child2 = 2*parent + 1, 2*parent + 2
    # we need to make sure both child1 and child2 are valid indices in heap
    ch1val, ch2val = child1 < size, child2 < size

    while ch1val:
        # we need to check if parent,child1 violate invariant
        ch1viol = array[child1] < array[parent]
        if ch2val:
            # we need to check if parent,child2 violate invariant
            ch2viol = array[child2] < array[parent]
            # we will swap parent with the smaller of its two children
            smaller = child1 if array[child1] < array[child2] else child2
            # if either child violates heap invariant, swap parent with smaller
            if ch1viol or ch2viol:
                array[smaller], array[parent] = array[parent], array[smaller]
        else:  # chil1 = size - 1 (in bounds), child2 = size (out of bounds)
            smaller = child1  # only one choice, child2 doesn't exist
            if ch1viol:  # only child1 can violate invar, child2 doesn't exist
                array[smaller], array[parent] = array[parent], array[smaller]
        parent = smaller  # parent has been swapped, now it's further down
        # recalculate and recheck new children of new parent
        child1, child2 = 2*parent + 1, 2*parent + 2
        ch1val, ch2val = child1 < size, child2 < size


# TESTING
def check_heap_invar(heap):
    """Take Myheap object as input.

    Return True if given Myheap object's array satisfies heap invariant.
    """
    for i in range((heap.size - 1)//2):
        if heap.array[i] > heap.array[2*i + 1]:
            return False
        if heap.array[i] > heap.array[2*i + 2]:
            return False
    return True


if __name__ == "__main__":
    # test insert
    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13, 7, 10]
    MYHEAP.size = 11
    assert check_heap_invar(MYHEAP)
    MYHEAP.insert(5)
    assert MYHEAP.array == [4, 4, 5, 9, 4, 8, 9, 11, 13, 7, 10, 12]

    # test extractmin
    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    assert check_heap_invar(MYHEAP)
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [4, 4, 8, 9, 13, 12, 9, 11]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [4, 9, 8, 11, 13, 12, 9]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [8, 9, 9, 11, 13, 12]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [9, 9, 12, 11, 13]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [9, 11, 12, 13]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [11, 13, 12]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [12, 13]
    MYHEAP.extractmin()
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [13]

    # test delete
    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(1)  # delete the second 4
    assert MYHEAP.array == [4, 4, 8, 9, 13, 12, 9, 11]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(2)  # delete the 8
    assert MYHEAP.array == [4, 4, 9, 9, 4, 12, 13, 11]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(3)  # delete the first 9
    assert MYHEAP.array == [4, 4, 8, 11, 4, 12, 9, 13]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(4)  # delete the third 4
    assert MYHEAP.array == [4, 4, 8, 9, 13, 12, 9, 11]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(5)  # delete 12
    assert MYHEAP.array == [4, 4, 8, 9, 4, 13, 9, 11]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(6)  # delete the second 9
    assert MYHEAP.array == [4, 4, 8, 9, 4, 12, 13, 11]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(7)  # delete 11
    assert MYHEAP.array == [4, 4, 8, 9, 4, 12, 9, 13]

    MYHEAP = Myheap()
    MYHEAP.array = [4, 4, 8, 9, 4, 12, 9, 11, 13]
    MYHEAP.size = 9
    MYHEAP.delete(8)  # delete 13
    assert MYHEAP.array == [4, 4, 8, 9, 4, 12, 9, 11]

    # test heapify
    MYHEAP = Myheap()
    ARRAY = [0, 1, 1]
    shuffle(ARRAY)
    MYHEAP.heapify(ARRAY)
    assert check_heap_invar(MYHEAP)
    assert MYHEAP.array == [0, 1, 1]

    MYHEAP = Myheap()
    ARRAY = [0, 1, 1, 2, 2, 2, 2]
    shuffle(ARRAY)
    MYHEAP.heapify(ARRAY)
    assert check_heap_invar(MYHEAP)
    HEAPS = [[0, 1, 1, 2, 2, 2, 2], [0, 1, 2, 1, 2, 2, 2],
             [0, 1, 2, 2, 1, 2, 2], [0, 2, 1, 2, 2, 1, 2],
             [0, 2, 1, 2, 2, 2, 1]]
    assert MYHEAP.array in HEAPS

    MYHEAP = Myheap()
    ARRAY = [i for i in range(100)]
    for _ in range(100):
        shuffle(ARRAY)
        MYHEAP.heapify(ARRAY)
        assert check_heap_invar(MYHEAP)

    print("tests pass")
