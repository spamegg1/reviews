# -*- coding: utf-8 -*-
"""
Created on Thu Sep  6 16:49:50 2018

@author:
"""


def gcd_recur(a, b):
    '''
    a, b: positive integers

    returns: a positive integer, the greatest common divisor of a & b.
    '''
    if b == 0:
        return a
    return gcd_recur(b, a % b)


print(gcd_recur(177, 99))
