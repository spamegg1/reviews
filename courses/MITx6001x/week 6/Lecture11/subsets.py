# -*- coding: utf-8 -*-
"""
Created on Tue Jun 21 22:34:32 2016

@author: ericgrimson
"""


def genSubsets(L):
    if len(L) == 0:
        return [[]]  # list of empty list
    smaller = genSubsets(L[:-1])  # all subsets without last element
    extra = L[-1:]  # create a list of just last element
    new = []
    for small in smaller:
        # for all smaller solutions, add one with last element
        new.append(small+extra)
    return smaller+new  # combine those with last element and those without


TEST = [1, 2, 3, 4]

super = genSubsets(TEST)
