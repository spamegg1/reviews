# -*- coding: utf-8 -*-
"""
Created on Fri Jun 10 13:06:06 2016

@author: ericgrimson
"""


def fib(num):
    """assumes num an int >= 0
       returns Fibonacci of num"""
    if num in (0, 1):
        return 1
    return fib(num-1) + fib(num-2)
