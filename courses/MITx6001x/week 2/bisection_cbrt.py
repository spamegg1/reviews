# -*- coding: utf-8 -*-
"""
Created on Wed Sep  5 20:16:46 2018

@author:
"""

EPSILON = 0.00001


def bisection_cbrt(cube):
    """Approximates cuberoot of input within epsilon accuracy."""
    left, right = 0, 0
    if cube >= 0:
        left, right = 0, cube
    else:
        left, right = cube, 0
    guess = (right + left) / 2
    while abs(guess**3 - cube) >= EPSILON:
        if guess**3 - cube >= 0:
            right = guess
        else:
            left = guess
        guess = (right + left) / 2
    if abs(guess**3 - cube) >= EPSILON:
        print('failed', guess**2)
    else:
        print('succeeded: ' + str(guess))


bisection_cbrt(-216)
