# -*- coding: utf-8 -*-
"""
Created on Tue Oct 29 11:07:03 2019.

@author: spamegg1
"""
import graph as g


def wis(uwpgraph):
    """Take undirected, (vertex) weighted path graph as input.

    Return cache of maximum weight independent set subproblems.
    """
    weight = uwpgraph.get_node('0')[1]
    # cache the first two base cases
    cache = [0, weight]
    for i in range(2, len(uwpgraph.nodes) + 1):
        weight = uwpgraph.get_node(str(i-1))[1]
        cache.append(max(cache[-1], cache[-2] + weight))
    return cache


def reconstruct(uwpgraph, cache):
    """Take an undirected, (vertex) weighted path graph,
    and the output of wis on that graph, as input.

    Return (vertices of) maximum weight independent set.
    """
    mwis = []
    index = len(cache) - 1

    while index >= 2:
        weight = uwpgraph.get_node(str(index - 1))[1]
        if cache[index - 1] >= cache[index - 2] + weight:
            index -= 1
        else:
            mwis.append(str(index - 1))
            index -= 2

    if index == 1:
        mwis.append('0')
    return mwis


# TESTING
if __name__ == '__main__':
    UWPGRAPH = g.node_to_uwpgraph('problem16.6test.txt')
    CACHE = wis(UWPGRAPH)
    assert CACHE[-1] == 2617
    assert reconstruct(UWPGRAPH, CACHE) == ['9', '6', '3', '1']

    UWPGRAPH = g.node_to_uwpgraph('problem16.6test2.txt')
    CACHE = wis(UWPGRAPH)
    assert CACHE[-1] == 14
    assert reconstruct(UWPGRAPH, CACHE) == ['5', '3', '0']

    UWPGRAPH = g.node_to_uwpgraph('problem16.6.txt')
    CACHE = wis(UWPGRAPH)
    assert CACHE[-1] == 2955353732
    print("Tests pass.")
