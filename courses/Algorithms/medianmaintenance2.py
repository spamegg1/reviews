# -*- coding: utf-8 -*-
"""
Created on Sun Sep 29 16:22:01 2019.

@author: spamegg1
"""
from bst import Bst, insert, select


def medianbst(textfile):
    """Take text file as input. File has one positive integer on each line.

    Return sum of kth medians, the median of the first k numbers in the stream:
    the (k+1)/2th smallest number among the first k if k is odd,
    the k/2th smallest if k is even.

    Use a binary search tree to accomplish this. The root is always the median.
    """
    tree = Bst()
    sumofkth = 0
    size = 0

    with open(textfile, 'r') as file:
        for line in file:
            new = int(line)
            if size == 0:      # special case to insert root for the first time
                insert(tree, new, None)
            else:
                insert(tree, new, tree.root.name)
            size += 1

            rank = (size + 1)//2 if size % 2 == 1 else size//2
            nodename = select(tree, rank, tree.root.name)
            sumofkth += tree.getval(nodename)

    return sumofkth


# TESTING
if __name__ == "__main__":
    assert medianbst('problem11.3test.txt') == 29335
    assert medianbst('problem11.3.txt') == 46831213
    print("tests pass")
