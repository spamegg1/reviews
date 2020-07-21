# -*- coding: utf-8 -*-
"""
Created on Wed Sep  4 08:59:39 2019.

@author: spamegg1
"""


# STRASSEN'S MATRIX MULTIPLICATION ALGORITHM
# Given two nxn matrices X and Y, where n is even, divide X and Y into 4
# (n/2)x(n/2) submatrices like this:
#
#    |A : B|     |E : F|           |AE + BG : AF + BH|
# X =.......  Y =.......  Now X*Y =...................
#    |C : D|     |G : H|           |CE + DG : CF + DH|
#
# Step 1: recursively compute 7 (cleverly chosen) products:
#     P1 = A(F-H), P2 = (A+B)H, P3 = (C+D)E, P4 = D(G-E),
#     P5 = (A+D)(E+H), P6 = (B-D)(G+H), P7 = (A-C)(E+F)
#
# Step 2: do the necessary (cleverly chosen) additions and subtractions:
#          |AE + BG : AF + BH|  | P4+P5+P6-P2 :   P1+P2    |
#     X*Y =................... =............................
#          |CE + DG : CF + DH|  |     P3+P4   : P1+P5-P3-P7|
#
# We will use an array of arrays to simulate a matrix in Python.
# For example, a 2x2 matrix looks like this:
#   matrix = [[1,2],
#             [3,4]]
# This is a matrix whose first row is 1, 2 and second row 3, 4.
# So, matrix[0] would return the first row, matrix[1] the second row.
# If we want to access the ith row, jth column entry in a matrix, we would
# have to use: matrix[i][j].


def add_sub(first, second, operation=True):
    """Take two nxn matrices (list of lists) as input.

    Also take a Boolean as input. True means addition, False subtraction.
    Returns an nxn matrix that is the sum/diff of the two given matrices.
    """
    size = len(first)  # both first and second are nxn matrices for some n.

    # base case for recursion in strassen: both matrices are 1x1
    if size == 1:
        if operation:
            return [[first[0][0] + second[0][0]]]
        return [[first[0][0] - second[0][0]]]

    # initialize an nxn matrix with all entries 0
    result = [[0 for j in range(len(first))] for i in range(len(first))]

    for i in range(size):  # ith row of result
        for j in range(size):  # jth column in ith row of result
            if operation:
                result[i][j] = first[i][j] + second[i][j]
            else:
                result[i][j] = first[i][j] - second[i][j]
    return result


def strassen(first, second):
    """Take two nxn matrices as input. n is a power of 2.

    Return an nxn matrix, which is the product of the two matrices.
    """
    size = len(first)

    # base case for recursion: both matrices are 1x1.
    if size == 1:
        return [[first[0][0] * second[0][0]]]

    # Main case: n is a power of 2 greater than 1.
    # First initialize the 8 submatrices
    A = [first[i][:size//2] for i in range(size//2)]  # top left of frst
    B = [first[i][size//2:] for i in range(size//2)]  # top rght of frst
    C = [first[i][:size//2] for i in range(size//2, size)]  # bot left of frst
    D = [first[i][size//2:] for i in range(size//2, size)]  # bot rght of frst
    E = [second[i][:size//2] for i in range(size//2)]  # top left of scnd
    F = [second[i][size//2:] for i in range(size//2)]  # top rght of scnd
    G = [second[i][:size//2] for i in range(size//2, size)]  # bot left o scnd
    H = [second[i][size//2:] for i in range(size//2, size)]  # bot rght o scnd

    # Recursively calculate the 7 clever products:
    # P1 = A(F-H), P2 = (A+B)H, P3 = (C+D)E, P4 = D(G-E),
    # P5 = (A+D)(E+H), P6 = (B-D)(G+H), P7 = (A-C)(E+F)
    P1 = strassen(A, add_sub(F, H, False))
    P2 = strassen(add_sub(A, B), H)
    P3 = strassen(add_sub(C, D), E)
    P4 = strassen(D, add_sub(G, E, False))
    P5 = strassen(add_sub(A, D), add_sub(E, H))
    P6 = strassen(add_sub(B, D, False), add_sub(G, H))
    P7 = strassen(add_sub(A, C, False), add_sub(E, F))

    # Calculate the 4 clever sums that give quadrants of the product
    #          |AE + BG : AF + BH|  | P4+P5+P6-P2 :   P1+P2    |
    #     X*Y =................... =............................
    #          |CE + DG : CF + DH|  |     P3+P4   : P1+P5-P3-P7|
    AEBG = add_sub(P4, add_sub(P5, add_sub(P6, P2, False)))
    AFBH = add_sub(P1, P2)
    CEDG = add_sub(P3, P4)
    CFDH = add_sub(add_sub(P1, add_sub(P5, P3, False)), P7, False)

    # construct the final product
    result = [[] for i in range(size)]
    for i in range(size//2):
        result[i] = AEBG[i] + AFBH[i]  # list concatenation to merge rows
    for i in range(size//2, size):
        result[i] = CEDG[i - size//2] + CFDH[i - size//2]
    return result


# TESTING
if __name__ == "__main__":
    FIRST = [[1, 2, 3, 4],
             [5, 6, 7, 8],
             [9, 10, 11, 12],
             [13, 14, 15, 16]]
    SECOND = [[-13, -14, -15, -16],
              [-9, -10, -11, -12],
              [-5, -6, -7, -8],
              [-1, -2, -3, -4]]
    ADD = [[-12, -12, -12, -12],
           [-4, -4, -4, -4],
           [4, 4, 4, 4],
           [12, 12, 12, 12]]
    SUB = [[14, 16, 18, 20],
           [14, 16, 18, 20],
           [14, 16, 18, 20],
           [14, 16, 18, 20]]
    MULTIPLY = [[-50, -60, -70, -80],
                [-162, -188, -214, -240],
                [-274, -316, -358, -400],
                [-386, -444, -502, -560]]

    # test add_sub
    assert add_sub(FIRST, SECOND) == ADD
    assert add_sub(FIRST, SECOND, False) == SUB

    # test Strassen multiplication algorithm
    assert strassen([[2]], [[3]]) == [[6]]
    assert strassen([[1, 0], [0, 1]], [[1, 2], [3, 4]]) == [[1, 2], [3, 4]]
    assert strassen(FIRST, SECOND) == MULTIPLY
    print("tests pass")
