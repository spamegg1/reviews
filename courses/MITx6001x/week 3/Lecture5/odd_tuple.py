# -*- coding: utf-8 -*-
"""
Created on Wed Sep 12 20:24:56 2018

@author:
"""


def odd_tuples(a_tup):
    '''
    a_tup: a tuple

    returns: tuple, every other element of a_tup.
    '''
    result = ()
    for index in range(0, len(a_tup), 2):
        result += (a_tup[index],)
    return result


print(odd_tuples(('I', 'am', 'a', 'test', 'tuple')))
