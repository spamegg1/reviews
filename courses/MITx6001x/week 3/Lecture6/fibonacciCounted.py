# -*- coding: utf-8 -*-
"""
Created on Wed Jun  8 19:29:10 2016

@author: ericgrimson
"""


def fib(n):
    global numFibCalls
    numFibCalls += 1
    if n == 1:
        return 1
    elif n == 2:
        return 2
    else:
        return fib(n-1) + fib(n-2)


def fib_efficient(n, d):
    global numFibCalls
    numFibCalls += 1
    if n in d:
        return d[n]
    else:
        ans = fib_efficient(n-1, d) + fib_efficient(n-2, d)
        d[n] = ans
        return ans


numFibCalls = 0
fibArg = 34

print(fib(fibArg))
print('function calls', numFibCalls)

numFibCalls = 0

d = {1: 1, 2: 2}
print(fib_efficient(fibArg, d))
print('function calls', numFibCalls)
