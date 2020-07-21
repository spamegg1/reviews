# -*- coding: utf-8 -*-
"""
Created on Mon Oct 21 16:29:02 2019.

@author: spamegg1
"""
from collections import deque
import unionfind as uf


def hamming(codes, spacing):
    """Take a deque of 24-bit binary codes.

    Return largest k such that there is a k-clustering of the codes with
    at least given spacing.
    """
    clusters = 0
    firstdeque = codes
    seconddeque = deque()

    while firstdeque:
        first = firstdeque.popleft()
        cluster = deque([first])
        while firstdeque:
            second = firstdeque.popleft()
            incluster = False
            clusterlist = list(cluster)
            for thing in clusterlist:
                if hammingdistance(thing, second) < spacing:
                    cluster.append(second)
                    incluster = True
                    break
            if not incluster:
                seconddeque.append(second)
        clusters += 1
        print(clusters)
        firstdeque = seconddeque
        seconddeque = deque()

    return clusters


def hamming2(codes, spacing):
    """Take a deque of 24-bit binary codes.

    Return largest k such that there is a k-clustering of the codes with
    at least given spacing.
    """
    clusters = len(codes)
    leftover = codes
    while leftover:
        newdeque = deque()
        first = leftover.popleft()
        cluster = deque([first])
        cluster_changed = True
        while cluster_changed:
            cluster_changed = False
            clusterlist = list(cluster)
            while leftover:
                second = leftover.popleft()
                for node in clusterlist:
                    if hammingdistance(second, node) < spacing:
                        cluster.append(second)
                        clusters -= 1
                        print(clusters)
                        cluster_changed = True
                    else:
                        newdeque.append(second)
            leftover = newdeque
    return clusters


def hamming3(codes, spacing):
    """Take a deque of 24-bit binary codes.

    Return largest k such that there is a k-clustering of the codes with
    at least given spacing.
    """
    clusters = len(codes)
    ufcodes = uf.UnionFind(codes)
    for code1 in codes:
        for code2 in codes:
            if ufcodes.find(code1) != ufcodes.find(code2):
                if hammingdistance(code1, code2) < spacing:
                    ufcodes.union_by_rank(code1, code2)
                    clusters -= 1
                    print(clusters)
    return clusters


def hammingdistance(code1, code2):
    """Take two 24-bit Hamming codes as input.

    Return the Hamming distance between the codes.
    """
    return bin(int(code1, 2) ^ int(code2, 2)).count('1')


def codes_to_deque_list(filename, islist=False):
    """Take name of text file as input.

    Text file has, on each line, a Hamming code consisting of 24 bits.
    Each code is a string; the bits are separated by spaces.
    Return a deque or a list of these codes (deque by default).
    To return a list, set optional parameter islist to True.
    """
    result = deque()
    if islist:
        result = []
    with open(filename, 'r') as file:
        for line in file:
            code = ''.join(line.split())
            result.append(code)
    return result


# TESTING
if __name__ == "__main__":
    CODES = codes_to_deque_list('clustering_big.txt')
    print(hamming(CODES, 3))  # 81120, 54179
    print("Tests pass.")
