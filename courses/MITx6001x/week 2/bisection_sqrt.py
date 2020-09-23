# -*- coding: utf-8 -*-
"""
Created on Wed Sep  5 20:16:46 2018

@author: spamegg
"""

EPSILON = 0.00000001


def bisection_sqrt(square):
    """Approximates squareroot of positive input within epsilon accuracy."""
    if square < 0:
        print("Negative input")
        return

    left = 0
    right = square
    guess = (right + left) / 2
    diff = guess**2 - square

    while abs(diff) >= EPSILON:
        if diff >= 0:
            right = guess
        else:
            left = guess
        guess = (right + left) / 2
        diff = guess**2 - square

    if abs(diff) >= EPSILON:
        print('failed', guess**2)
    else:
        print('succeeded: ' + str(guess))


bisection_sqrt(4096)
