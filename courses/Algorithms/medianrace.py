# -*- coding: utf-8 -*-
"""
Created on Sun Sep 29 17:49:59 2019

@author: spamegg1
"""
import time
import medianmaintenance as m1
import medianmaintenance2 as m2


def timedcall(func, *args):
    """Call function with args; return elapsed time and result."""
    start = time.process_time()
    result = func(*args)
    end = time.process_time()
    return end - start, result


# TESTING
if __name__ == "__main__":
    print(timedcall(m1.medmainfile, 'problem11.3.txt'))
    print(timedcall(m2.medianbst, 'problem11.3.txt'))
    print("tests pass")
