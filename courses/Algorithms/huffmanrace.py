# -*- coding: utf-8 -*-
"""
Created on Thu Oct 10 13:33:34 2019

@author: spamegg1
"""
import time
from huffman import huffman
from huffmanheap import huffmanheap


def timedcall(func, *args):
    """Call function with args; return elapsed time and result."""
    start = time.process_time()
    result = func(*args)
    end = time.process_time()
    return end - start, result


def text_to_alphabet(filename):
    """Take file name as input.

    File has one integer per line denoting the frequency of a symbol.
    Return alphabet: a dictionary of symbol: frequency pairs.
    """
    alphabet = {}
    with open(filename, 'r') as file:
        counter = 0
        for line in file:
            alphabet['node' + str(counter)] = int(line)
            counter += 1
    return alphabet


# TESTING
if __name__ == "__main__":
    ALPHABET = {'A': 3, 'B': 2, 'C': 6, 'D': 8, 'E': 2, 'F': 6}
    # which one is faster?
    print(timedcall(huffman, ALPHABET))
    print(timedcall(huffmanheap, ALPHABET))

    # harder testing
    ALPHABET = text_to_alphabet('problem14.6test1.txt')
    assert min([len(val) for val in huffman(ALPHABET).values()]) == 2
    assert min([len(val) for val in huffmanheap(ALPHABET).values()]) == 2
    assert max([len(val) for val in huffman(ALPHABET).values()]) == 5
    assert max([len(val) for val in huffmanheap(ALPHABET).values()]) == 5
    ALPHABET = text_to_alphabet('problem14.6test2.txt')
    assert min([len(val) for val in huffman(ALPHABET).values()]) == 3
    assert min([len(val) for val in huffmanheap(ALPHABET).values()]) == 3
    assert max([len(val) for val in huffman(ALPHABET).values()]) == 6
    assert max([len(val) for val in huffmanheap(ALPHABET).values()]) == 6

    # THIS IS THE 1000 SYMBOL CHALLENGE DATA SET!
    ALPHABET = text_to_alphabet('problem14.6.txt')
    HUFFTIME, _ = timedcall(huffman, ALPHABET)
    HEAPTIME, _ = timedcall(huffmanheap, ALPHABET)
    # which one is faster? Double-Queue version seems faster.
    print(HUFFTIME, HEAPTIME)
    assert min([len(val) for val in huffman(ALPHABET).values()]) == 9
    assert min([len(val) for val in huffmanheap(ALPHABET).values()]) == 9
    assert max([len(val) for val in huffman(ALPHABET).values()]) == 19
    assert max([len(val) for val in huffmanheap(ALPHABET).values()]) == 19
    print("tests pass")
