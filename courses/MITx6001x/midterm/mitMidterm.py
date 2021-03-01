# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""


def uniqueValues(aDict):
    '''
    aDict: a dictionary that maps to integer values.
    returns: a list
    Returns a list, consisting of keys in aDict that map to unique integer
    values. If no key maps to a unique integer value, returns empty list.
    The returned list is in increasing order.
    Example:
        aDict = {'a': 1, 'b': 2, 'c': 2, 'd': 3}
        uniqueValues(aDict) -> ['a', 'd']
    '''
    result = []
    for key in aDict:
        value = aDict[key]
        if all((value != aDict[otherkey] or key == otherkey)
                for otherkey in aDict):
            result.append(key)
    return sorted(result)


def testUniqueValues():
    '''Tests uniqueValues function.'''
    dict1 = {}
    dict2 = {'a': 1}
    dict3 = {'a': 1, 'b': 1}
    dict4 = {'d': 1, 'z': 2, 'a': 3}
    dict5 = {'b': 2, 'd': 3, 'c': 2, 'a': 1}
    dict6 = {'a': 1, 'b': 1, 'c': 2, 'd': 2, 'e': 3, 'f': 3}

    assert(uniqueValues(dict1) == [])
    assert(uniqueValues(dict2) == ['a'])
    assert(uniqueValues(dict3) == [])
    assert(uniqueValues(dict4) == ['a', 'd', 'z'])
    assert(uniqueValues(dict5) == ['a', 'd'])
    assert(uniqueValues(dict6) == [])
    print("Testing uniqueValues: PASS")


testUniqueValues()


def laceStringsRecur(s1, s2):
    """
    s1 and s2 are strings.

    Returns a new str with elements of s1 and s2 interlaced,
    beginning with s1. If strings are not of same length,
    then the extra elements should appear at the end.
    """
    def helpLaceStrings(s1, s2, out):
        if s1 == '':
            return out + s2
        if s2 == '':
            return out + s1
        else:
            return helpLaceStrings(s1[1:], s2[1:], out + s1[0] + s2[0])
    return helpLaceStrings(s1, s2, '')


def testLaceStringsRecur():
    '''Tests laceStringsRecur function.'''
    s1 = ''
    s2 = 'a'
    s3 = 'bc'
    s4 = 'qwerty'
    s5 = 'ppppppppppppp'

    assert(laceStringsRecur(s1, s1) == '')
    assert(laceStringsRecur(s1, s2) == 'a')
    assert(laceStringsRecur(s2, s1) == 'a')
    assert(laceStringsRecur(s1, s3) == 'bc')
    assert(laceStringsRecur(s3, s1) == 'bc')
    assert(laceStringsRecur(s2, s3) == 'abc')
    assert(laceStringsRecur(s3, s2) == 'bac')
    assert(laceStringsRecur(s3, s3) == 'bbcc')
    assert(laceStringsRecur(s4, s5) == 'qpwpeprptpypppppppp')
    assert(laceStringsRecur(s5, s4) == 'pqpwpeprptpyppppppp')
    print("Testing laceStringsRecur: PASS")


testLaceStringsRecur()


def general_poly(L):
    """ L, a list of numbers (n0, n1, n2, ... nk)
    Returns a function, which when applied to a value x, returns the value
    n0 * x^k + n1 * x^(k-1) + ... nk * x^0 """
    def f(x):
        if not L:
            return 0
        else:
            return L[0] * x**(len(L) - 1) + general_poly(L[1:])(x)
    return f


def test_general_poly():
    '''Tests general_poly function.'''
    l0 = []
    l1 = [1]
    l2 = [2, 3]
    l3 = [4, 5, 6]
    l4 = [7, 8, 9, 10]
    l5 = [11, 12, 13, 14, 15]
    l6 = [-16, -17, -18, -19, -20, -21]
    l7 = [9, 8, 7, 6, 5, 4, 3]
    l8 = [2, 1, 0, -1, -2, -3, -4, -5]

    x0 = 0
    x1 = -1
    x2 = 1
    x3 = 5
    x4 = -5

    assert(general_poly(l0)(x1) == 0)
    assert(general_poly(l1)(x2) == 1)
    assert(general_poly(l2)(x3) == 13)
    assert(general_poly(l3)(x4) == 81)
    assert(general_poly(l4)(x1) == 2)
    assert(general_poly(l5)(x0) == 15)
    assert(general_poly(l6)(x3) == -63471)
    assert(general_poly(l7)(x2) == 42)
    assert(general_poly(l8)(x4) == -141060)
    print("Testing general_poly: PASS")


test_general_poly()
