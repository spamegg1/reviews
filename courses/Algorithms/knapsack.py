# -*- coding: utf-8 -*-
"""
Created on Thu Oct 31 08:07:03 2019.

@author: spamegg1
"""


def knapsack(array, constraint):
    """Take list of pairs of positive integers and an integer as input.

    Each pair is of the form (value, size).
    Return cache of solutions to knapsack problems on initial subarrays and
    smaller constraints. The cache is a list of lists.
    """
    # cache the base cases
    cache = [[0 for _ in range(constraint + 1)]
             for _ in range(len(array) + 1)]

    for i in range(1, len(array) + 1):
        for j in range(constraint + 1):
            value, size = array[i-1]
            if size > j:
                cache[i][j] = cache[i - 1][j]
            else:
                cache[i][j] = max(cache[i-1][j], cache[i-1][j-size] + value)
    return cache


def reconstruct(array, constraint, cache):
    """Take an int, a list of int pairs and knapsack's output on it as input.

    Return array of pairs that realize the maximum returned by knapsack.
    """
    sack = []
    cap = constraint
    ind = len(array)

    while ind >= 1:
        value, size = array[ind - 1]
        condition = cache[ind - 1][cap - size] + value >= cache[ind - 1][cap]
        if size <= cap and condition:
            sack.append((value, size))
            cap -= size
        ind -= 1
    return sack


def knapdict(array, constraint, cache):
    """Take list of pairs of positive integers and an integer as input.

    Each pair is of the form (value, size).
    Return maximum sum of values from items whose sizes sum below constraint.
    """
    lnt = len(array)

    if not array:
        cache[(0, constraint)] = 0
        return 0

    if not constraint:
        cache[(lnt, 0)] = 0
        return 0

    cache[(lnt, 0)] = 0
    cache[(0, constraint)] = 0

    lastvalue, lastsize = array[-1]
    if lastsize > constraint:
        try:
            return cache[(lnt - 1, constraint)]
        except KeyError:
            cache[(lnt - 1, constraint)] = result \
                = knapdict(array[:-1], constraint, cache)
            return result
    else:
        try:
            return max(cache[(lnt - 1, constraint)],
                       cache[(lnt - 1, constraint - lastsize)] + lastvalue)
        except KeyError:
            cache[(lnt - 1, constraint)] = result1 \
                = knapdict(array[:-1], constraint, cache)
            cache[(lnt - 1, constraint - lastsize)] = result2 \
                = knapdict(array[:-1], constraint - lastsize, cache)
            return max(result1, result2 + lastvalue)


def text_to_knapsack(filename):
    """Take a string (name of text file) as input.

    First line of text file has two integers: constraint, item count.
    Each line after that has a pair of integers: (value, size).
    Return array, constraint pair ready to be inputted into knapsack.
    """
    with open(filename, 'r') as file:
        array, constraint = [], 0
        linenumber = 0
        for line in file:
            if linenumber == 0:
                constraint = int(line.split()[0])
                linenumber += 1
            else:
                splitline = line.split()
                value, size = int(splitline[0]), int(splitline[1])
                array.append((value, size))
    return array, constraint


# TESTING
if __name__ == '__main__':
    ARRAY, CONSTRAINT = text_to_knapsack('problem16.7test2.txt')
    CACHE = knapsack(ARRAY, CONSTRAINT)
    assert CACHE[-1][-1] == 8
    assert reconstruct(ARRAY, CONSTRAINT, CACHE) == [(4, 3), (4, 2)]

    ARRAY, CONSTRAINT = text_to_knapsack('problem16.7test.txt')
    CACHE = knapsack(ARRAY, CONSTRAINT)
    assert CACHE[-1][-1] == 2493893
    # print(reconstruct(ARRAY, CONSTRAINT, CACHE))

    ARRAY, CONSTRAINT = text_to_knapsack('problem16.7test2.txt')
    assert knapdict(ARRAY, CONSTRAINT, {}) == 8

    ARRAY, CONSTRAINT = text_to_knapsack('problem16.7test.txt')
    assert knapdict(ARRAY, CONSTRAINT, {}) == 2493893

    # Challenge data set, 2000 items, 2000000 constraint
    # UNCOMMENT THE BELOW ON LINUX ONLY!
    # Takes about 5-6 mins and around 1 GB RAM
    import resource
    import sys
    resource.setrlimit(resource.RLIMIT_STACK,
                       (resource.RLIM_INFINITY, resource.RLIM_INFINITY))
    sys.setrecursionlimit(2 ** 17)
    ARRAY, CONSTRAINT = text_to_knapsack('problem16.7.txt')
    print(knapdict(ARRAY, CONSTRAINT, {}))  # 4243395 CORRECT!
    
    print("Tests pass.")
