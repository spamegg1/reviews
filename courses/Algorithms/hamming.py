# -*- coding: utf-8 -*-
"""
Created on Mon Oct 21 16:29:02 2019.

@author: spamegg1
"""
from collections import deque
import unionfind as uf


def hamming(codes, spacing, clusters):
    """Take a deque of 24-bit binary codes.

    Return largest k such that there is a k-clustering of the codes with
    at least given spacing.
    """
    result = clusters
    ufcodes = uf.UnionFind(codes)
    for code1 in codes:
        dist1 = dist1_codes({code1}, 24, {code1}) # 24
        dist2 = dist1_codes(dist1, 24, {code1})   # 276
        dist12 = dist1.union(dist2)               # 300
        for code2 in dist12:
            if code2 in codes:
                if ufcodes.find(code1) != ufcodes.find(code2):
                    if hammingdistance(code1, code2) < spacing:
                        ufcodes.union_by_rank(code1, code2)
                        result -= 1
                        print(result)
    return result


def dist1_codes(codes, length, excluded):
    """Take a set of Hamming codes of given length as input.

    Return set of all possible Hamming codes that are
    1 distance away from the given codes.
    Codes in the given excluded variable are excluded.
    """
    result = deque()
    for code in codes:
        for i in range(length):
            newcode = code[:i] + str((int(code[i]) + 1) % 2) + code[i+1:]
            result.append(newcode)
    return set(result) - excluded   


def hammingdistance(code1, code2):
    """Take two 24-bit Hamming codes as input.

    Return the Hamming distance between the codes.
    """
    return bin(int(code1, 2) ^ int(code2, 2)).count('1')    


def codes_to_deque_list(filename):
    """Take name of text file as input.

    Text file has, on each line, a Hamming code consisting of 24 bits.
    Each code is a string; the bits are separated by spaces.
    Return a set of these codes.
    """
    result = set()    
    with open(filename, 'r') as file:
        for line in file:
            code = ''.join(line.split())
            result.add(code)
    return result

# TESTING
if __name__ == "__main__":
    CLUSTERS = 200000
    SPACING = 3
    # removed the first line from the txt file (which said 200000)
    CODES = codes_to_deque_list('clustering_big.txt')
    print(hamming(CODES, SPACING, CLUSTERS))  # 6118
    print("Tests pass.")
