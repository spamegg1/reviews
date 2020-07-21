# -*- coding: utf-8 -*-
"""
Created on Sat Sep  7 22:27:03 2019.

@author: spamegg1
"""
import random


class Comparison():
    """
    Object to keep track of comparisons and swaps in quicksort algorithm.

    Comparison and swap counts are updated as a list goes through quicksort
    algorithm.
    """

    def __init__(self):
        """Constructor."""
        self.comps = 0  # comparison count
        self.swaps = 0  # swap count

    def get_comp(self):
        """Return comparison count."""
        return self.comps

    def set_comp(self, comp):
        """Set comparison count."""
        self.comps = comp

    def get_swap(self):
        """Return swap count."""
        return self.swaps

    def set_swap(self, swap):
        """Set swap count."""
        self.swaps = swap


def partition(array, left, right, comp):
    """Take array A of distinct integers, and two array indices as input.

    If array A is of size n then 0 <= left <= right <= (n-1).
    Partition A[left], ... , A[right] around pivot element A[left]:
    [<-unchanged-> l <-less than p-> p <-more than p-> r <-unchanged->].
    Return final position of pivot element.
    Also takes a Comparison object to keep track of number of comparisons.

    For example, partition([3, 8, 2, 5, 1, 7, 4, 6], 1, 6) would change the
    array into [3, 4, 2, 5, 1, 7, 8, 6] and return 6.
    It pivots around the element 8 at index 1.
    It partitions the segment [8, 2, 5, 1, 7, 4] from index 1 to 6 (inclusive)
    into two, less than 8 or greater than 8: [2, 5, 1, 7, 4] and [].
    Then at the final step, puts 8 in between these two segments by swapping
    8 with the last element of first segment, which is 4: [4, 2, 5, 1, 7, 8].
    It leaves the 3 at the start of the array (index 0) unchanged.
    It leaves the 6 at the end of the array (index 7) unchanged.
    """
    pivot = array[left]  # pivot around leftmost point of subarray
    boundary = left + 1  # boundary between [< pivot] and [> pivot]
    # we maintain an invariant throughout:
    # boundary is the beginning index of the segment greater than pivot
    # anything between indices left and boundary are less than pivot
    # anything between indices boundary and right are more than pivot

    # process all array entries from index left+1 to right (inclusive)
    for index in range(left + 1, right + 1):
        if array[index] < pivot:  # if array[index] > pivot, do nothing
            array[boundary], array[index] = array[index], array[boundary]
            boundary += 1  # segment of elts less than pivot increased
            # this restores the invariant
            swaps = comp.get_swap()  # get current swap count
            comp.set_swap(swaps + 1)  # increment it

    # now segments look like this: [<stuff> p <-less-> <-more-> <stuff>]
    # swap pivot elt to correct place: [<stuff> <-less-> p <-more-> <stuff>]
    array[left], array[boundary - 1] = array[boundary - 1], array[left]
    swaps = comp.get_swap()  # get current swap count
    comp.set_swap(swaps + 1)  # increment it

    comps = comp.get_comp()  # get current comparison count
    comp.set_comp(comps + right - left)  # increment it by comparisons above
    return boundary - 1  # new position of pivot element


def pivot_first(array, left, right):
    """Choose pivot to be the leftmost point in subarray."""
    return left


def pivot_last(array, left, right):
    """Choose pivot to be the rightmost point in subarray."""
    return right


def pivot_med(array, left, right):
    """Take array of distinct integers, and two array indices as input.

    Return index of a well chosen pivot point in subarray using median-of-3.
    """
    middle = (left + right)//2
    if array[left] < array[right]:  # L < R
        if array[middle] < array[left]:  # M < L < R
            return left
        if array[middle] < array[right]:  # L < M < R
            return middle
        return right  # L < R < M
    # otherwise R < L
    if array[middle] < array[right]:  # M < R < L
        return right
    if array[middle] < array[left]:  # R < M < L
        return middle
    return left  # R < L < M


def pivot_rand(array, left, right):
    """Take array of distinct integers, and two array indices as input.

    If array A is of size n then 0 <= left <= right <= (n-1).
    Return index of a randomly chosen pivot point in subarray.
    """
    return random.randint(left, right)


def qsortseg(array, left, right, pivot_func, comp):
    """Take unsorted array of distinct integers, and two indices, as input.

    Sort subarray from left to right, in place, in increasing order.
    Does not return a value.
    Also takes a pivot function as input to choose index of pivot element.
    Also takes a Comparison object as input to track comparison count.
    """
    if left >= right:  # trivial case, 0 or 1 element subarray
        return

    # main case
    pivot = pivot_func(array, left, right)  # choose pivot index

    # move pivot element to the leftmost position in subarray
    array[left], array[pivot] = array[pivot], array[left]

    # partition segment from left to right, get new pivot position
    pivot = partition(array, left, right, comp)

    # recursively sort the two partitions
    qsortseg(array, left, pivot - 1, pivot_func, comp)
    qsortseg(array, pivot + 1, right, pivot_func, comp)


def quicksort(array, pivot_func, comp):
    """Take unsorted array of distinct integers as input.

    Sort array (in place) in increasing order.
    This is a wrapper function for qsortseg.
    """
    qsortseg(array, 0, len(array) - 1, pivot_func, comp)


def comp_counter(array, pivot_func):
    """Take array of n distinct numbers.

    Return the number of comparisons quicksort makes to sort this array.
    Also takes a pivot choosing function as input, for testing.
    """
    comp = Comparison()  # fresh new Comparison object with count 0
    # put array through algorithm, this updates comparison count
    quicksort(array, pivot_func, comp)  # sort array, and update comp count
    return comp.get_comp()


# TESTING
if __name__ == "__main__":
    FILES = [('problem5.6test1.txt', [25, 31, 21]),
             ('problem5.6test2.txt', [620, 573, 502]),
             ('problem5.6.txt', [162085, 164123, 138382])]
    PIVOTS = [pivot_first, pivot_last, pivot_med]

    for filename, answer in FILES:
        # have to use multiple copies of test array b/c quicksort mutates it
        ARRAYS = [[], [], [], []]

        with open(filename, 'r') as qsortchal:
            for line in qsortchal:  # convert text file to list
                for array in ARRAYS:
                    array.append(int(line))  # fill array with textfile

        for i in range(3):  # for each file, test all 3 pivots
            assert comp_counter(ARRAYS[i], PIVOTS[i]) == answer[i]

        # we can't directly test randomized quicksort; just print instead
        print(comp_counter(ARRAYS[3], pivot_rand))  # better than first, last!

    print("tests pass")
