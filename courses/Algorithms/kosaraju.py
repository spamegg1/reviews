# -*- coding: utf-8 -*-
"""
Created on Sun Sep 15 15:21:39 2019.

@author: spamegg1
"""
# from operator import itemgetter
import dfs
import graph as g


def kosaraju(dgraph):
    """Take a directed graph as input.

    Return graph's strongly connected components.
    This is a dictionary of node:value pairs; nodes with the same value belong
    to the same strongly connected component.
    """
    # use the deque returned by toposort, which has nodes sorted by topo value
    topo = dfs.toposort(dgraph, reverse=True)[1]

    explored = {}
    numscc = 0
    scc_sizes = {}

    for node, _ in topo:
        if node not in explored:
            numscc += 1
            scc_sizes[numscc] = 0    # create new entry for size of SCC #numscc
            dfs_scc(dgraph, node, explored, numscc, scc_sizes)
    return explored
    # return sorted(scc_sizes.items(), key=itemgetter(1), reverse=True)[:5]


def dfs_scc(dgraph, node, explored, numscc, scc_sizes):
    """Take a directed graph and one of its nodes as input.

    Also take a dictionary of node:int pairs, a positive integer and a
    dictionary as input.
    Assign Strongly Connected Component values to nodes (by updating explored).
    Also keeps track of the sizes of SCCs (by updating scc_sizes).
    Does not return.
    """
    explored[node] = numscc  # a node has been labeled to belong to SCC #numscc
    scc_sizes[numscc] += 1                      # so increment size of that SCC
    for edge in dgraph.get_node(node)[0]:
        newnode = dgraph.get_edge(edge)[1]
        if newnode not in explored:
            dfs_scc(dgraph, newnode, explored, numscc, scc_sizes)


# TESTING
if __name__ == "__main__":
    DNODES = {'1': [{'1_3'}, {'5_1'}],
              '2': [{'2_4', '2_10'}, {'9_2'}],
              '3': [{'3_5', '3_11'}, {'1_3'}],
              '4': [{'4_7'}, {'2_4', '9_4'}],
              '5': [{'5_1', '5_7', '5_9'}, {'3_5'}],
              '6': [{'6_10'}, {'8_6', '11_6'}],
              '7': [{'7_9'}, {'4_7', '5_7'}],
              '8': [{'8_6'}, {'9_8', '10_8', '11_8'}],
              '9': [{'9_2', '9_4', '9_8'}, {'5_9', '7_9'}],
              '10': [{'10_8'}, {'2_10', '6_10'}],
              '11': [{'11_6', '11_8'}, {'3_11'}]}
    DEDGES = {'1_3': ('1', '3'),
              '2_4': ('2', '4'), '2_10': ('2', '10'),
              '3_5': ('3', '5'), '3_11': ('3', '11'),
              '4_7': ('4', '7'),
              '5_1': ('5', '1'), '5_7': ('5', '7'), '5_9': ('5', '9'),
              '6_10': ('6', '10'),
              '7_9': ('7', '9'),
              '8_6': ('8', '6'),
              '9_2': ('9', '2'), '9_4': ('9', '4'), '9_8': ('9', '8'),
              '10_8': ('10', '8'),
              '11_6': ('11', '6'), '11_8': ('11', '8')}
    DTEST = g.Dgraph(graph=(DNODES, DEDGES))
    print(kosaraju(DTEST))  # SCCs: {1, 3, 5}, {2, 4, 7, 9}, {6, 8, 10}, {11}

    FILES = ['problem8.10test1.txt',  # SCC sizes: 3, 3, 3
             'problem8.10test2.txt',  # SCC sizes: 3, 3, 2
             'problem8.10test3.txt',  # SCC sizes: 3, 3, 1, 1
             'problem8.10test4.txt',  # SCC sizes: 7, 1
             'problem8.10test5.txt']  # SCC sizes: 6, 3, 2, 1
    for file in FILES:
        DGRAPH = g.edge_to_graph(file, directed=True)
        print(kosaraju(DGRAPH))

    # THIS IS THE 5 MILLION EDGE GRAPH! DON'T RUN THIS!
    # Run it on Linux or a VM box with:
    # import resource
    # import sys
    # resource.setrlimit(resource.RLIMIT_STACK,
    #                    (resource.RLIM_INFINITY, resource.RLIM_INFINITY))
    # sys.setrecursionlimit(2 ** 17)
    # On Linux it finishes in 1-2 minutes.
    # The sizes of the five largest SCCs are:
    # 434821 968 459 313 211
    # This is verified correct by the Stanford course website
    # DGRAPH = g.edge_to_graph('SCC.txt', directed=True)
    # print(kosaraju(DGRAPH))

    print("tests pass")
