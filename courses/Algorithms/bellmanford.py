# -*- coding: utf-8 -*-
"""
Created on Tue Nov 12 18:07:03 2019.

@author: spamegg1
"""
import graph as g


def bellmanford(wdgraph, source):
    """Take weighted directed graph and a single source node as input.

    :return shortest path lengths from source to other nodes, or indicate that
    a negative cycle exists.
    """
    nodes = wdgraph.nodes
    nodecount = len(nodes)
    inf = float("inf")
    cache = {}

    # base cases
    for node in nodes:
        cache[(0, node)] = 0 if node == source else inf

    # main double for loops
    for index in range(1, nodecount + 1):
        stable = True

        for node in nodes:
            incedges = wdgraph.get_node(node)[1]
            inpaths = []
            for edge in incedges:
                othernode, _, cost = wdgraph.get_edge(edge)
                inpaths.append(cache[(index - 1, othernode)] + cost)

            mininpaths = 0 if not inpaths else min(inpaths)
            cache[(index, node)] = min(cache[(index - 1, node)], mininpaths)
            if cache[(index, node)] != cache[(index - 1, node)]:
                stable = False

    #     if stable:
    #         return {node: cache[(index - 1, node)] for node in nodes}
    # return None  # negative cycle detected!
    return {node: cache[(index - 1, node)] for node in nodes}


# TESTING
if __name__ == '__main__':
    WDGRAPH = g.edge_to_dwgraph('problem18.8test1.txt')
    for SOURCE in WDGRAPH.nodes:
        print(bellmanford(WDGRAPH, SOURCE))
    WDGRAPH = g.edge_to_dwgraph('problem18.8test2.txt')
    for SOURCE in WDGRAPH.nodes:
        assert bellmanford(WDGRAPH, SOURCE) is None

    # Challenge sets (TAKES A LONG TIME!!! MORE THAN 10 HOURS!)
    # These are not meant for Bellman-Ford. Runs way too slow.
    # They are meant for Floyd-Warshall.
    # FILES = ['problem18.8file1.txt', 'problem18.8file2.txt',
    #          'problem18.8file3.txt', 'problem18.8file4.txt']
    # for FILE in FILES:
    #     WDGRAPH = g.edge_to_dwgraph(FILE)
    #     RESULTS = []
    #     for SOURCE in WDGRAPH.nodes:
    #         RESULT = bellmanford(WDGRAPH, SOURCE)
    #         if RESULT:
    #             RESULTS += RESULT
    #     print(min(RESULTS))

    print("Tests pass.")
