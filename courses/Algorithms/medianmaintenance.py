# -*- coding: utf-8 -*-
"""
Created on Thu Sep 19 05:11:11 2019.

@author: spamegg1
"""
import heapq as h


def medmain():
    """Take user keyboard input.

    Print median of successive nonnegative integers entered by user.
    """
    # hleft will contain negations of positive numbers
    # (because Python only supports min heaps)
    # so -hleft[0] is actually the max element of hleft
    # heaps contain same number of elts when size is even
    # hright contains exactly 1 more elt when size is odd
    hleft, hright = [], []                             # initialize empty heaps
    size = 0                     # keep track of size of hleft + size of hright

    while True:
        new = input("Enter nonnegative integer (q to quit): ")
        if new == 'q':                                 # user requested to exit
            return
        new = int(new)                 # assume user entered a valid nonneg int

        if size == 0:                      # always add first elt to right heap
            h.heappush(hright, new)
        if size == 1:                      # always add second elt to left heap
            if hright[0] < new:     # if second elt wants to go into right heap
                rightmin = h.heappop(hright)
                h.heappush(hleft, -rightmin)
                h.heappush(hright, new)
            else:
                h.heappush(hleft, -new)

        if size > 1:
            if new < -hleft[0]:      # if second elt wants to go into left heap
                h.heappush(hleft, -new)
                if size % 2 == 0:         # now left heap has too many elements
                    last = h.heappop(hleft)
                    h.heappush(hright, -last)
            else:                   # if second elt wants to go into right heap
                h.heappush(hright, new)
                if size % 2 == 1:            # now right heap has too many elts
                    first = h.heappop(hright)
                    h.heappush(hleft, -first)

        size += 1
        print("Median is: ", hright[0])    # median is ALWAYS min of right heap


def medmainfile(textfile):
    """Take text file as input. File has one positive integer on each line.

    Return sum of kth medians, the median of the first k numbers in the stream:
    the (k+1)/2th smallest number among the first k if k is odd,
    the k/2th smallest if k is even.
    """
    # hleft will contain negations of positive numbers
    # (because Python only supports min heaps)
    # so -hleft[0] is actually the max element of hleft
    # heaps contain same number of elts when size is even
    # hright contains exactly 1 more elt when size is odd
    hleft, hright = [], []
    size = 0                     # keep track of size of hleft + size of hright
    sum_of_kth = 0

    file = open(textfile, 'r')
    for line in file:
        new = int(line)

        if size == 0:                      # always add first elt to right heap
            h.heappush(hright, new)
        if size == 1:
            if hright[0] < new:     # if second elt wants to go into right heap
                rightmin = h.heappop(hright)
                h.heappush(hleft, -rightmin)
                h.heappush(hright, new)
            else:
                h.heappush(hleft, -new)    # always add second elt to left heap

        if size > 1:
            if new < -hleft[0]:      # if second elt wants to go into left heap
                h.heappush(hleft, -new)
                if size % 2 == 0:         # now left heap has too many elements
                    last = h.heappop(hleft)
                    h.heappush(hright, -last)
            else:                   # if second elt wants to go into right heap
                h.heappush(hright, new)
                if size % 2 == 1:            # now right heap has too many elts
                    first = h.heappop(hright)
                    h.heappush(hleft, -first)

        size += 1
        sum_of_kth += hright[0] if size % 2 == 1 else -hleft[0]
    return sum_of_kth


# TESTING
if __name__ == "__main__":
    # the sequence of entries 5, 2, 7, 3, 4, 11, 9, 8, 2, 10, 6, 0
    # should print medians:   5, 5, 5, 5, 4,  5, 5, 7, 5,  7, 6, 6
    # medmain()
    assert medmainfile('problem11.3test.txt') == 29335
    assert medmainfile('problem11.3.txt') == 46831213
    print("tests pass")
