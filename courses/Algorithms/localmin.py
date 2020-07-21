# -*- coding: utf-8 -*-
"""
Created on Sat Sep  7 06:43:19 2019.

@author: spamegg1
"""

# FINDING LOCAL MINIMA IN A MATRIX
# Given an nxn grid of numbers, a number on the grid is a local
# minimum if it is strictly smaller than four of its neighbors (top, bottom,
# left, right). Most local minima have 4 neighbors, some have 3 (if they are
# on the edges of the grid), and only the four corners of the grid have 2.
#
# Examples: |0 1| here the top left corner is a CORNER local minimum
#           |1 1| (has exactly 2 neighbors)
#
#           |1 0 1| here the (0,1) entry is an EDGE local minimum
#           |1 1 1| (has exactly 3 neighbors)
#           |1 1 1|
#
#           |1 1 1| here the (1,1) entry is an INTERIOR local minimum
#           |1 0 1| (has exactly 4 neighbors)
#           |1 1 1|
#
# We will use divide and conquer strategy. Divide grid into 4 quadrants of
# (n/2)x(n/2) matrices. Recursively search those 4 quadrants for local minima.
# If one of the 4 recursive calls finds an INTERIOR local min, that is also
# a local min for the WHOLE matrix, so we are done.
# If one of the 4 recursive calls finds a CORNER or an EDGE local min, this
# might also be local min for the whole matrix, depending on whether the edge
# / corner belongs to the whole matrix or not. If not, we must check 1 or 2
# neighboring quadrants' entries to ensure this is a local min for the whole
# matrix.
#
# Example:
# |1 1 1 1|
# |1 0 0 1|
# |1 1 1 1|
# |1 1 1 1|
#
# Top left quadrant would have a local min 0 in its bottom right corner:
# |1 1|
# |1 0|
# but this is not a local min for the whole matrix.     |1 1|
# Similar for top right quadrant's bottom left corner:  |0 1|


def base_case(grid):
    """Take 2x2 grid of numbers as input.

    Return list of all local minima.
    This is a helper function for localmin below.
    """
    result = []
    if grid[0][0] < min(grid[0][1], grid[1][0]):
            result.append((0, 0))
    if grid[0][1] < min(grid[0][0], grid[1][1]):
        result.append((0, 1))
    if grid[1][0] < min(grid[0][1], grid[1][0]):
        result.append((1, 0))
    if grid[1][1] < min(grid[0][1], grid[1][0]):
        result.append((1, 1))
    return result


def localmin(grid):
    """Take an nxn grid of numbers as input. Assume n >= 2 is a power of 2.

    Return row/column entry of a local minimum if it exists, None otherwise.
    Example: localmin([[1, 0], [1, 1]]) = (0, 1)
    If there are more than one local minima, we return the first found.
    """
    size = len(grid)
    # base case: grid is 2x2
    if size == 2:
        return base_case(grid)

    # main case: size >= 2
    topl = [grid[i][:size//2] for i in range(size//2)]  # top left quad
    topr = [grid[i][size//2:] for i in range(size//2)]  # top right quad
    botl = [grid[i][:size//2] for i in range(size//2, size)]  # bottom left
    botr = [grid[i][size//2:] for i in range(size//2, size)]  # bottom right

    # get (possibly fake) local minima from 4 quadrants
    return grid


if __name__ == "__main__":
    TWO = [[1, 1],
           [1, 0]]  # corner min at (1, 1)
    FOURINT = [[1, 1, 1, 1],
               [1, 1, 1, 1],
               [1, 1, 0, 1],
               [1, 1, 1, 1]]  # interior min at (2, 2)
    FOUREDGE = [[1, 1, 1, 1],
                [1, 1, 1, 1],
                [1, 1, 1, 0],
                [1, 1, 1, 1]]  # edge min at (2, 3)
    FAKE = [[1, 1, 1, 1, 1, 1, 1, 1],  # has edge and corner mins in
            [1, 1, 1, 0, 0, 1, 1, 1],  # subquadrants, but they are not
            [1, 1, 1, 1, 1, 1, 1, 1],  # local mins for the whole grid
            [1, 0, 1, 0, 0, 1, 0, 1],  # in fact, whole grid has no local min
            [1, 0, 1, 0, 0, 1, 0, 1],
            [1, 1, 1, 1, 1, 1, 1, 1],
            [1, 1, 1, 0, 0, 1, 1, 1],
            [1, 1, 1, 1, 1, 1, 1, 1]]
    assert localmin(TWO) == (2, 1)
    assert localmin(FOURINT) == (2, 2)
    assert localmin(FOUREDGE) == (2, 3)
    assert localmin(FAKE) is None
    print("tests pass")
