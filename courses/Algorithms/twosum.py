# -*- coding: utf-8 -*-
"""
Created on Sun Sep 29 23:11:58 2019.

@author: spamegg1
"""


def twosum(array, target):
    """Take an unsorted array of integers and a target integer t as input.

    Return True if there are integers x,y in array such that x + y = t.
    """
    dic = {}
    for integer in array:
        dic[integer] = integer
    for integer in array:
        diff = target - integer
        if diff in dic:
            return True
    return False


def twosum_distinct(array, target):
    """Take an unsorted array of integers and a target integer t as input.

    Return True if there are DISTINCT integers x,y in array with x + y = t.
    """
    dic = {}
    for integer in array:
        dic[integer] = integer
    for integer in array:
        diff = target - integer
        if diff in dic and diff != integer:
            return True
    return False


def twosum_text(textfile, targetrange):
    """Take a textfile of integers and an array of target integers t as input.

    Return the number of targets t in target range, for which there are two
    DISTINCT integers x, y in textfile such that x + y = t.
    """
    dic = {}
    count = 0
    with open(textfile, 'r') as file:
        for line in file:
            integer = int(line)
            dic[integer] = integer
    for target in targetrange:
        for integer in dic:
            diff = target - integer
            if diff in dic and diff != integer:
                count += 1
                break
    return count


# TESTING
if __name__ == "__main__":
    TEST = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    for i in range(-10, 2):
        assert not twosum(TEST, i)
        assert not twosum_distinct(TEST, i)
    for i in range(2, 11):
        assert twosum(TEST, i)
    for i in range(3, 11):
        assert twosum_distinct(TEST, i)

    TARGETS = [i for i in range(3, 11)]
    assert twosum_text('problem12.4test.txt', TARGETS) == 8

    # This is the insane 1 million integer challenge data set!
    # Don't run this! It takes about 1 hour!
    # BIGTARGETS = [i for i in range(-10000, 10001)]
    # print(twosum_text('problem12.4.txt', BIGTARGETS))  # == 427

    print("tests pass")
