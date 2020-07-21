# -*- coding: utf-8 -*-
"""
Created on Tue Nov 5 07:07:03 2019.

@author: spamegg1
"""


def optbst(freqs, length):
    """Take list of nonnegative real numbers and an integer as input.

    :param freqs: frequencies of keys 1, 2, ..., n in a binary search tree.
    :param length: number of keys. n = length.
    :return: minimum weighted search time of a bst with keys 1, 2, ..., n.
    """
    cache = [[0 for _ in range(length + 1)] for _ in range(length + 1)]

    for s in range(length):
        for i in range(length - s):
            myrange = range(i, i + s + 1)
            freqsum = sum([freqs[k] for k in myrange])
            mins = min([cache[i][r-1] + cache[r+1][i+s] for r in myrange])
            cache[i][i + s] = freqsum + mins

    return cache[0][length - 1]


def text_to_freq(filename):
    """Take name of text file as input.

    Text file has two lines: on the first line an integer (number of keys),
    on the second line, frequencies of keys, separated by commas. Example:
        4
        5,3,2,6
    :param filename: string
    :return: a list of frequencies (real numbers) and number of keys (int).
    """
    with open(filename, 'r') as file:
        firstline = file.readline()
        secondline = file.readline()
        return [int(freq) for freq in secondline.split(',')], int(firstline)


# TESTING
if __name__ == '__main__':
    FREQS, LENGTH = [0.8, 0.1, 0.1], 3
    assert optbst(FREQS, LENGTH) == 1.3

    FREQS, LENGTH = text_to_freq('problem17.8optbst.txt')
    assert optbst(FREQS, LENGTH) == 2780

    FREQS, LENGTH = [0.05, 0.4, 0.08, 0.04, 0.1, 0.1, 0.23], 7
    print(optbst(FREQS, LENGTH))

    FREQS, LENGTH = [0.2, 0.05, 0.17, 0.1, 0.2, 0.03, 0.25], 7
    print(optbst(FREQS, LENGTH))
    print("Tests pass.")
