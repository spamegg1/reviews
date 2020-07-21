# -*- coding: utf-8 -*-
"""
Created on Tue Sep 10 08:42:03 2019.

@author: spamegg1
"""
import dselect as ds
import quicksort as qs


def wghmed(dic):
    """Take n-size dictionary of distinct integers with weights as input.

    The integers x1, ... , xn have positive weights w1, ... , wn.
    Input consists of xi:wi pairs. All xi are distinct.
    Let W = w1 + ... + wn be the sum of the weights.
    A weighted median is some xi, for which the sum of weights of all xj < xi
    is at most W/2, and the sum of the weights of all xj > xi is at most W/2.
    Notice that here can be at most two weighted medians.
    Return a weighted median in array.
    """
    ints = list(dic.keys())
    size = len(ints)
    weights = list(dic.values())
    medwgh = sum(weights) / 2  # median weight of all integers

    # base cases
    if size == 1:
        return ints[0]
    if size == 2:
        if dic[ints[0]] >= dic[ints[1]]:
            return ints[0]
        return ints[1]

    # find median int using dselect (deterministic linear time)
    med = ds.dselect(ints, size//2)
    pivot = ints.index(med)  # get median int's index to be used as pivot

    # swap median int with leftmost element in integers array
    ints[0], ints[pivot] = ints[pivot], ints[0]

    # sort integers in place by partitioning around median int
    comp = qs.Comparison()
    # this is still deterministic linear time
    newpivot = qs.partition(ints, 0, size-1, comp)
    # now median int is positioned at index newpivot in integers

    # now calculate the sum of weights to the left and right of median int
    leftwgh = 0
    for i in range(0, newpivot):
        leftwgh += dic[ints[i]]  # this is still linear time

    rightwgh = 0
    for i in range(newpivot + 1, size):
        rightwgh += dic[ints[i]]  # this is still linear time

    # if both are less than median weight, we found a weighted median
    if leftwgh <= medwgh and rightwgh <= medwgh:
        return med

    # otherwise we need to recurse on the "heavier" side of median int
    # if right side is "heavier"
    if leftwgh < rightwgh:
        dic[med] += leftwgh  # add left side's weight to medians' weight
        # create new dictionary with entries only from right side of median
        newdic = {ints[i]: dic[ints[i]] for i in range(newpivot, size)}
        return wghmed(newdic)  # recurse on right of median (including med)

    # else left side is heavier
    dic[med] += rightwgh  # add right side's weight to medians' weight
    # create new dictionary with entries only from left side of median
    newdic = {ints[i]: dic[ints[i]] for i in range(newpivot + 1)}
    return wghmed(newdic)  # recurse on left of median (including med)


# TESTING
if __name__ == "__main__":
    # basic tests
    DIC = {i+1: i+1 for i in range(7)}
    print(wghmed(DIC))
    # assert wghmed(DIC) == 5
    print("tests pass")
