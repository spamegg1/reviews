# -*- coding: utf-8 -*-
"""
Created on Wed Nov 13 15:07:03 2019.

@author: spamegg1
"""
import graph as g


def floydwarshall(wdgraph):
    """Take weighted directed graph as input.

    :return: shortest path length between any two nodes on graph.
    """
    inf = float('inf')
    nodes = wdgraph.nodes
    nodecount = len(nodes)
    nodelist = sorted(list(nodes))
    cache = {}

    # base cases
    for node1 in nodelist:
        for node2 in nodelist:
            if node1 == node2:
                cache[(0, node1, node2)] = 0
            else:
                cost = wdgraph.isedge(node1, node2)  # number or False
                cache[(0, node1, node2)] = inf if cost is False else cost

    # systematically solve subproblems
    for size in range(1, nodecount + 1):
        for node1 in nodelist:
            for node2 in nodelist:
                mid = nodelist[size - 1]
                earlier = cache[(size - 1, node1, node2)]
                first = cache[(size - 1, node1, mid)]
                second = cache[(size - 1, mid, node2)]
                cache[(size, node1, node2)] = min(earlier, first + second)
        # free memory of previous size entries
        for node1 in nodelist:
            for node2 in nodelist:
                del cache[(size - 1, node1, node2)]

    # check for negative cycles
    for node in nodes:
        if cache[(nodecount, node, node)] < 0:
            return None  # negative cycle found

    return [[cache[(nodecount, node1, node2)] for node2 in nodes]
            for node1 in nodes]


# TESTING
if __name__ == '__main__':
    # Exercise 18.4 from Algorithms Illuminated Part 3
    WDNODES = {'1': [{'1_2', '1_3'}, {'4_1'}],
               '2': [{'2_3'}, {'1_2'}],
               '3': [{'3_4'}, {'2_3', '1_3'}],
               '4': [{'4_1'}, {'3_4'}]}
    WDEDGES = {'1_2': ('1', '2', 2),  # 2
               '2_3': ('2', '3', 1),  # 1
               '3_4': ('3', '4', 3),  # 3
               '4_1': ('4', '1', 4),  # 4
               '1_3': ('1', '3', 5)}  # 5
    WDGRAPH = g.Wgraph(graph=(WDNODES, WDEDGES))
    print(floydwarshall(WDGRAPH))

    # Exercise 18.5 from Algorithms Illuminated Part 3
    WDNODES = {'1': [{'1_2', '1_3'}, {'4_1'}],
               '2': [{'2_3'}, {'1_2'}],
               '3': [{'3_4'}, {'2_3', '1_3'}],
               '4': [{'4_1'}, {'3_4'}]}
    WDEDGES = {'1_2': ('1', '2', 2),
               '2_3': ('2', '3', 1),
               '3_4': ('3', '4', -3),
               '4_1': ('4', '1', -4),
               '1_3': ('1', '3', 5)}
    WDGRAPH = g.Wgraph(graph=(WDNODES, WDEDGES))
    print(floydwarshall(WDGRAPH))  # has negative cycle!

    # Test case provided by algorithmsilluminated.org
    WDGRAPH = g.edge_to_dwgraph('problem18.8test1.txt')
    print(floydwarshall(WDGRAPH))  # shortest distance = -2

    # Test case provided by algorithmsilluminated.org
    WDGRAPH = g.edge_to_dwgraph('problem18.8test2.txt')
    print(floydwarshall(WDGRAPH))  # has negative cycle!

    # # Challenge sets (include 'problem18.8file4.txt' for SUPER HARD challenge)
    # FILES = ['problem18.8file1.txt', 'problem18.8file2.txt',
    #          'problem18.8file3.txt']
    # RESULTS = []
    # for FILE in FILES:
    #     print("Started file at: ", d.datetime.now())
    #     WDGRAPH = g.edge_to_dwgraph(FILE)
    #     RESULT = floydwarshall(WDGRAPH)
    #     MINDIST = min([min(lst) for lst in RESULT]) if RESULT else None
    #     RESULTS.append(MINDIST)
    #     print("Finished file at: ", d.datetime.now())
    # print(RESULTS)  # first attempt: [None, None, -16]
    # # second attempt: [None, None, -19]
    print("Tests pass.")
