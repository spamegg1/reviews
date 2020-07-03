# -*- coding: utf-8 -*-
"""
Created on Thu Oct  4 00:25:10 2018

@author:
"""


def genPrimes():
    primelist = []
    i = 2
    while i > 0:
        if all(i % p != 0 for p in primelist):
            primelist.append(i)
            next = i
            yield next
        i += 1
