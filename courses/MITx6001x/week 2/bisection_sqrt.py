# -*- coding: utf-8 -*-
"""
Created on Wed Sep  5 20:16:46 2018

@author:
"""

EPSILON = 0.00000001


def bisection_sqrt(square):
    """Approximates squareroot of positive input within epsilon accuracy."""
    left = 0
    right = square
    guess = (right + left) / 2
    while abs(guess**2 - square) >= EPSILON:
        if guess**2 - square >= 0:
            right = guess
        else:
            left = guess
        guess = (right + left) / 2
    if abs(guess**2 - square) >= EPSILON:
        print('failed', guess**2)
    else:
        print('succeeded: ' + str(guess))


bisection_sqrt(25)
