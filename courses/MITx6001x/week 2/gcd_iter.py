# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 16:49:50 2018

@author:
"""


def gcd_iter(a, b):
    '''
    a, b: positive integers

    returns: a positive integer, the greatest common divisor of a & b.
    '''
    guess = min(a, b)
    while not (a % guess == 0 and b % guess == 0) and guess > 1:
        guess -= 1
    return guess


print(gcd_iter(177, 99))
