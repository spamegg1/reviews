# -*- coding: utf-8 -*-
"""
Created on Wed Sep  4 19:53:19 2019.

@author: spamegg1
"""


def zero_pad(numberstring, zeros, left=True):
    """Return the string with zeros added to the left or right."""
    for i in range(zeros):
        if left:
            numberstring = '0' + numberstring
        else:
            numberstring = numberstring + '0'
    return numberstring


def karatsuba(x, y):
    """Multiply two integers using Karatsuba's algorithm."""
    # convert to strings for easy access to digits
    x = str(x)
    y = str(y)
    # base case for recursion
    if len(x) == 1 and len(y) == 1:
        return int(x) * int(y)
    if len(x) < len(y):
        x = zero_pad(x, len(y) - len(x))
    elif len(y) < len(x):
        y = zero_pad(y, len(x) - len(y))
    n = len(x)
    j = n//2
    # for odd digit integers
    if (n % 2) != 0:
        j += 1
    bzero_padding = n - j
    azero_padding = bzero_padding * 2
    a = int(x[:j])
    b = int(x[j:])
    c = int(y[:j])
    d = int(y[j:])
    # recursively calculate
    ac = karatsuba(a, c)
    bd = karatsuba(b, d)
    k = karatsuba(a + b, c + d)
    A = int(zero_pad(str(ac), azero_padding, False))
    B = int(zero_pad(str(k - ac - bd), bzero_padding, False))
    return A + B + bd


if __name__ == "__main__":
    FIRST = 3141592653589793238462643383279502884197169399375105820974944592
    SECOND = 2718281828459045235360287471352662497757247093699959574966967627
    print(karatsuba(FIRST, SECOND))
