# -*- coding: utf-8 -*-
"""
Created on Sat Nov 2 11:07:03 2019.

@author: spamegg1
"""
from collections import deque


def seqalign(words, costs, lengths):
    """Take three pairs (one of strings, two of integers) as input.

    :return: cache of sequence alignment subproblem solutions of the strings.
    """
    word1, word2 = words
    gap, mismatch = costs
    len1, len2 = lengths

    cache = [[0 for _ in range(len2 + 1)] for _ in range(len1 + 1)]
    for i in range(len1 + 1):
        cache[i][0] = i * gap
    for j in range(len2 + 1):
        cache[0][j] = j * gap

    for i in range(1, len1 + 1):
        for j in range(1, len2 + 1):
            penalty = 0 if word1[i-1] == word2[j-1] else mismatch
            case1 = cache[i-1][j-1] + penalty
            case2 = cache[i-1][j] + gap
            case3 = cache[i][j-1] + gap
            cache[i][j] = min([case1, case2, case3])

    return cache


def reconstruct(words, costs, lengths, cache):
    """Take pairs of two strings, two ints, two ints, and the output of seqalign
    on these 3 pairs, as input.

    :return: list of aligned versions of strings with lowest NW score.
    """
    word1, word2 = words
    gap, mismatch = costs
    len1, len2 = lengths
    new1, new2 = deque(), deque()

    i, j = len1, len2
    while i > 0 and j > 0:
        entry = cache[i][j]
        penalty = 0 if word1[i - 1] == word2[j - 1] else mismatch
        case1 = cache[i - 1][j - 1] + penalty
        case2 = cache[i - 1][j] + gap
        case3 = cache[i][j - 1] + gap
        if entry == case1:
            new1.appendleft(word1[i - 1])
            new2.appendleft(word2[j - 1])
            i -= 1
            j -= 1
        elif entry == case2:
            new1.appendleft(word1[i - 1])
            new2.appendleft('_')
            i -= 1
        elif entry == case3:
            new1.appendleft('_')
            new2.appendleft(word2[j - 1])
            j -= 1

    if i == 0:
        while j > 0:
            new1.appendleft('_')
            new2.appendleft(word2[j - 1])
            j -= 1
    elif j == 0:
        while i > 0:
            new1.appendleft(word1[i - 1])
            new2.appendleft('_')
            i -= 1

    return [''.join(new1), ''.join(new2)]


def text_to_seqalign(filename):
    """Take name of text file as input.

    Text file has, on its first line, two integers (lengths of words).
    On its second line, it has two real numbers (gap and mismatch costs).
    On its third and fourth lines it has strings (of letters AGTC).
    :return: tuples of 2 strings (words), 2 ints (costs), 2 ints (lengths).
    """
    with open(filename, 'r') as file:
        firstline = file.readline().split()
        length1, length2 = int(firstline[0]), int(firstline[1])
        secondline = file.readline().split()
        gapcost, mismatchcost = int(secondline[0]), int(secondline[1])
        word1 = file.readline()
        word2 = file.readline()
        return (word1, word2), (gapcost, mismatchcost), (length1, length2)


# TESTING
if __name__ == '__main__':
    WORDS, COSTS, LENGTHS = ['AGTACG', 'ACATAG'], [1, 2], [6, 6]
    CACHE = seqalign(WORDS, COSTS, LENGTHS)
    assert CACHE[-1][-1] == 4
    assert reconstruct(WORDS, COSTS, LENGTHS, CACHE) == ['A_GTACG', 'ACATA_G']

    WORDS, COSTS, LENGTHS = text_to_seqalign('problem17.8nw.txt')
    CACHE = seqalign(WORDS, COSTS, LENGTHS)
    assert CACHE[-1][-1] == 224
    # print(reconstruct(WORDS, COSTS, LENGTHS, CACHE))

    print("Tests pass.")
