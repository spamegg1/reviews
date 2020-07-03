# -*- coding: utf-8 -*-
"""
Created on Fri Jun 10 13:01:06 2016

@author: ericgrimson
"""


def g(x):
    def h():
        x = 'abc'

    x = x + 1
    print('in g(x): x =', x)
    h()
    return x


x = 3
z = g(x)
