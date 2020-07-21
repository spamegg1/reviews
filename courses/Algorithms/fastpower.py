# -*- coding: utf-8 -*-
"""
Created on Thu Sep  5 13:02:04 2019.

@author: spamegg1
"""


def fastpower(base, power):
    """Take two positive integers, base and power, as input.

    Return base raised to the power.
    Running time is Big Theta of log_2(power).
    Uses 2log_2(power) many multiplications/divisions in total.
    """
    if power == 1:
        return base

    print(".")
    square = base * base
    print(".")
    answer = fastpower(square, power//2)

    if power % 2 == 1:
        print(".")
        return base * answer
    return answer


if __name__ == "__main__":
    assert fastpower(2, 32) == 4294967296
    print("tests pass")
