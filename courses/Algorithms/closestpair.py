# -*- coding: utf-8 -*-
"""
Created on Thu Sep  5 21:36:15 2019.

@author: spamegg1
"""
from mergesort import mergesort


def dist(first, second):
    """Take two pairs (x1, y1), (x2, y2) of real numbers as input.

    Return the square of the Euclidean distance between the points.
    """
    return (first[0] - second[0])**2 + (first[1] - second[1])**2


# first solve the one dimensional version of the problem, just for fun
def onedim(array):
    """Take list of real numbers as input. List size at least 2.

    Return pair of numbers on list closest to each other.
    Sorts in O(nlogn) time, and searches in linear O(n) time.
    Overall runtime is O(nlogn).
    """
    array = mergesort(array)  # first sort the array with mergesort
    shortest = array[1] - array[0]  # keep track of shortest distance so far
    pair = (array[0], array[1])  # keep track of closest pair so far
    for i in range(len(array) - 1):  # now do a linear search in sorted list
        if array[i+1] - array[i] < shortest:  # check adjacent pairs
            pair = (array[i], array[i+1])  # for ties, choose earlier pair
    return pair


def mergeby(left, right, coord=True):
    """Take two sorted lists of pairs of real numbers as input.

    The two lists are both in ascending order of their x or y coordinates.
    Also take a Boolean as input, which is True by default, which means
    sort by x coordinate. Set it to False to sort by y coordinate.
    This must match the coordinate by which the two lists are already sorted.
    Return a list which is a union of the two lists, in ascending order of
    either their x or y coordinates (coord=True for x, False for y).
    """
    # Base cases: if either the left or the right is empty
    if not left:
        return right
    if not right:
        return left

    # Main case: both lists are nonempty.
    if coord:  # sort by x coordinate
        if left[0][0] < right[0][0]:  # compare x coordinates
            return [left[0]] + mergeby(left[1:], right)
        return [right[0]] + mergeby(left, right[1:])

    # otherwise sort by y coordinate
    if left[0][1] < right[0][1]:  # compare y coordinates
        return [left[0]] + mergeby(left[1:], right, False)
    return [right[0]] + mergeby(left, right[1:], False)


def sortby(array, coord=True):
    """Take array of points (x,y) in real plane as input. At least 2 points.

    Also take a coordinate by which to sort array (x or y). By default it is
    True (which means x coordinate). Set it to False to sort by y coordinate.
    Return same array sorted in increasing order of their x or y coordinate.
    Running time is O(nlogn).
    """
    size = len(array)

    # Base case: list is a singleton
    if size == 1:
        return array

    # split list into halves
    half = int(size/2)
    left = array[:half]  # for list of odd size, left is 1 smaller than right
    right = array[half:]  # for list of odd size, right is 1 bigger than left

    # sort the left half and the right half
    left_sorted = sortby(left, coord)
    right_sorted = sortby(right, coord)

    # merge the two sorted halves
    return mergeby(left_sorted, right_sorted, coord)


def base_cases(array):
    """Take array (of length 0, 1, 2 or 3) of pairs of real numbers as input.

    Return None if array has length 0 or 1, and a pair of points if 2 or 3.
    This function handles the trivial base cases for closest_pair below.
    """
    size = len(array)
    assert size in [0, 1, 2, 3]

    # base case: array contains 0, 1 or 2 points only
    if size in [0, 1]:  # just in case bad input is given; should not happen
        return None
    if size == 2:
        return (array[0], array[1])

    # otherwise size is 3
    zero_one = dist(array[0], array[1])
    one_two = dist(array[1], array[2])
    zero_two = dist(array[0], array[2])
    shortest = min([zero_one, zero_two, one_two])
    if shortest == zero_one:
        return (array[0], array[1])
    if shortest == zero_two:
        return (array[0], array[2])
    return (array[1], array[2])


def split_pair(Px, Py, delta):
    """Take two lists of points in R^2 and a positive real number as input.

    Both input lists contain the same points, possibly in different orders.
    First argument is a list sorted increasing in x coordinates.
    Second argument is the same list but sorted increasing in y coordinates.
    Return two points, one from each list, that are closest to each other,
    compared to all other pairs with one from each list.
    Third argument is used to ignore pairs of points that are at least delta
    apart in our search to speed things up.
    """
    median = Px[:len(Px)//2][-1][0]  # largest x coord in left half of Px

    # now filter Py for elts whose x coord is within +-delta of median
    # since Py is sorted by y-coord, so is Sy
    Sy = []
    for (x, y) in Py:
        if median - delta < x < median + delta:
            Sy.append((x, y))

    # don't understand this part yet
    best = delta
    bestpair = None
    length = len(Sy)
    for i in range(length - 1):
        for j in range(1, min(7, length - i)):
            if dist(Sy[i], Sy[i + j]) < best:
                best = dist(Sy[i], Sy[i + j])
                bestpair = (Sy[i], Sy[i + j])

    return bestpair


def closest_pair(Px, Py):
    """Take two arrays of points (x,y) in real plane as input.

    Both arrays are the same set-wise. They each contain at least 2 points.
    First argument is sorted in increasing order of x coordinate.
    Second argument is sorted in increasing order of y coordinate.
    Return a pair of points closest to each other (Euclidean distance).
    Runtime is O(nlogn), which improves upon naive brute force search O(n^2).
    """
    size = len(Px)
    # base case: arrays have 2 or 3 points
    if size in [0, 1, 2, 3]:  # 0 and 1 should not happen but just in case
        return base_cases(Px)

    mid = size//2
    Lx = Px[:mid]  # first half of Px, sorted by x coordinate
    Rx = Px[mid:]  # second half of Px sorted by x coordinate
    Ly = sortby(Lx, False)  # first half of Px, sorted by y coordinate
    Ry = sortby(Rx, False)  # second half of Px, sorted by y coordinate

    l1, l2 = closest_pair(Lx, Ly)  # best left pair
    r1, r2 = closest_pair(Rx, Ry)  # best right pair
    left_dist = dist(l1, l2)
    right_dist = dist(r1, r2)
    delta = min(left_dist, right_dist)  # this is passed into split_pair()

    split = split_pair(Px, Py, delta)  # best split pair (one left one right)
    if split:  # split_pair might return None, better check
        s1, s2 = split
        split_dist = dist(s1, s2)

    # now return the best pair among the above three pairs
    if split:  # again, gotta check if split is None or not
        best_dist = min([left_dist, right_dist, split_dist])
    else:
        best_dist = min(left_dist, right_dist)
    if best_dist == left_dist:
        return l1, l2
    if best_dist == right_dist:
        return r1, r2
    return s1, s2


def closest(array):
    """Take an array of points (x, y) on the real plane (at least 2 points).

    Return pair of points from array that are closest to each other.
    This function is a wrapper for closest_pair, which takes two ordered
    arrays as input. We sort given array by x-coord and y-coord, then
    call closest_array().
    """
    Px = sortby(array)  # sort array in increasing order of x-coord
    Py = sortby(array, False)  # sort array in increasing order of y-coord
    return closest_pair(Px, Py)


# TESTING
if __name__ == "__main__":
    assert onedim([1, 2]) == (1, 2)  # trivial base case
    assert onedim([6, 2, 1, 9, 7]) == (1, 2)
    assert onedim([8, 1, 7, 2, 6, 3, 5, 4]) == (1, 2)

    assert sortby([(3, 2), (1, 4)]) == [(1, 4), (3, 2)]
    assert sortby([(3, 2), (1, 4), (5, 6)]) == [(1, 4), (3, 2), (5, 6)]
    assert sortby([(3, 2), (1, 4)], False) == [(3, 2), (1, 4)]
    assert sortby([(3, 2), (1, 4), (5, 6)], False) == [(3, 2), (1, 4), (5, 6)]

    ELEVEN = [(1, 2), (9, -1), (3, 4), (-2, -3), (7, 8), (5, 6), (-4, -5),
              (-6, -7), (-8, -9), (-1, 0), (0, -2)]
    ELEVENX = [(-8, -9), (-6, -7), (-4, -5), (-2, -3), (-1, 0), (0, -2),
               (1, 2), (3, 4), (5, 6), (7, 8), (9, -1)]
    ELEVENY = [(-8, -9), (-6, -7), (-4, -5), (-2, -3), (0, -2), (9, -1),
               (-1, 0), (1, 2), (3, 4), (5, 6), (7, 8)]
    assert sortby(ELEVEN) == ELEVENX
    assert sortby(ELEVEN, False) == ELEVENY

    assert closest([(3, 2), (1, 4)]) == ((1, 4), (3, 2))
    assert closest([(0, 0), (3, 0), (3, 4)]) == ((0, 0), (3, 0))
    assert closest([(6, 0), (0, 0), (3, 4)]) == ((0, 0), (3, 4))
    assert closest([(6, 0), (0, 0), (2, 0), (3, 4)]) == ((0, 0), (2, 0))
    assert closest([(6, 0), (0, 0), (1, 1), (3, 4)]) == ((0, 0), (1, 1))
    assert closest([(6, 0), (0, 0), (2, 3), (3, 4)]) == ((2, 3), (3, 4))
    assert closest([(1, 2), (3, 4), (5, 6), (7, 8)]) == ((1, 2), (3, 4))
    assert closest([(1, 2), (4, 5), (6, 7), (7, 8)]) == ((6, 7), (7, 8))
    print("tests pass")
