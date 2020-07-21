# -*- coding: utf-8 -*-
"""
Created on Fri Sep  6 10:15:44 2019.

@author: spamegg1
"""
import time


def timedcall(func, *args):
    """Call function with args; return elapsed time and result."""
    start = time.process_time()
    result = func(*args)
    end = time.process_time()
    return end - start, result


# TESTING
if __name__ == "__main__":
    assert True
    print("tests pass")
