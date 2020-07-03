# -*- coding: utf-8 -*-
"""
Created on Thu May 19 08:44:36 2016

@author: ericgrimson
"""


def genFib():
    fibn_1 = 1  # fib(n-1)
    fibn_2 = 0  # fib(n-2)
    while True:
        # fib(n) = fib(n-1) + fib(n-2)
        next = fibn_1 + fibn_2
        yield next
        fibn_2 = fibn_1
        fibn_1 = next
