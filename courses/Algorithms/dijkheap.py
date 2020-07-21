# -*- coding: utf-8 -*-
"""
Created on Tue Sep 17 14:55:40 2019.

@author: spamegg1
"""
import heapdict as h
import graph as g


def dijkheap(wgraph, start):
    """Take a weighted directed graph (with nonnegative edge weights) as input.

    Also take a node s on the graph as input.
    Return the shortest path distance of every node from s.
    Uses heap dictionary to achieve running time of O((m+n)logn).
    """
    explored = {start: 0}         # dict of nodes with their shortest distances
    frontier = h.HeapDict()               # heap dictionary of NODES, not edges
    for node in wgraph.nodes:         # start has score 0, other nodes infinity
        frontier[node] = 0 if node == start else float("inf")

    while frontier:                # while there are still nodes to be explored
        node, score = frontier.popitem()     # node with smallest score in heap
        if score == float("inf"):                 # this is an unreachable node
            break                      # we must have exhausted reachable nodes
        explored[node] = score  # add node to explored, with its Dijkstra score

        for edge in wgraph.get_node(node)[0]:  # outgoing edges of current node
            newnode = wgraph.get_edge(edge)[1]           # head of such an edge
            edgelen = wgraph.get_edge(edge)[2]    # edge length of such an edge
            if newnode not in explored:  # otherwise newnode was popped already
                # maintain heap invariant
                frontier[newnode] = min(frontier[newnode], score + edgelen)
    return explored


# TESTING
if __name__ == "__main__":
    WNODES = {'s': [{'e', 'f'}, {}],
              'v': [{'g', 'i'}, {'e'}],
              'w': [{'h'}, {'f', 'i'}],
              't': [{}, {'g', 'h'}]}
    WEDGES = {'e': ('s', 'v', 1),
              'f': ('s', 'w', 4),
              'g': ('v', 't', 6),
              'h': ('w', 't', 3),
              'i': ('v', 'w', 2)}
    WTEST = g.Wgraph(graph=(WNODES, WEDGES))
    NODES = ['s', 'v', 'w', 't']
    ANSWERS = [{'s': 0, 'v': 1, 'w': 3, 't': 6}, {'v': 0, 'w': 2, 't': 5},
               {'w': 0, 't': 3}, {'t': 0}]
    for i in range(4):
        assert dijkheap(WTEST, NODES[i]) == ANSWERS[i]

    WNODES = {'1': [{'1_3'}, {'5_1'}],
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
    WEDGES = {'1_3': ('1', '3', 1),
              '2_4': ('2', '4', 2), '2_10': ('2', '10', 3),
              '3_5': ('3', '5', 4), '3_11': ('3', '11', 5),
              '4_7': ('4', '7', 6),
              '5_1': ('5', '1', 7), '5_7': ('5', '7', 8), '5_9': ('5', '9', 9),
              '6_10': ('6', '10', 9),
              '7_9': ('7', '9', 8),
              '8_6': ('8', '6', 7),
              '9_2': ('9', '2', 6), '9_4': ('9', '4', 5), '9_8': ('9', '8', 4),
              '10_8': ('10', '8', 3),
              '11_6': ('11', '6', 2), '11_8': ('11', '8', 1)}
    WTEST = g.Wgraph(graph=(WNODES, WEDGES))
    ANSWERS = [{'1': 0, '3': 1, '5': 5, '11': 6, '8': 7, '6': 8, '7': 13,
                '9': 14, '10': 17, '4': 19, '2': 20},
               {'2': 0, '4': 2, '10': 3, '8': 6, '7': 8, '6': 13, '9': 16},
               {'3': 0, '5': 4, '11': 5, '8': 6, '6': 7, '1': 11, '7': 12,
                '9': 13, '10': 16, '4': 18, '2': 19},
               {'4': 0, '7': 6, '9': 14, '8': 18, '2': 20, '10': 23, '6': 25},
               {'5': 0, '1': 7, '3': 8, '7': 8, '9': 9, '11': 13, '8': 13,
                '4': 14, '2': 15, '6': 15, '10': 18},
               {'6': 0, '10': 9, '8': 12},
               {'7': 0, '9': 8, '8': 12, '4': 13, '2': 14, '10': 17, '6': 19},
               {'8': 0, '6': 7, '10': 16},
               {'9': 0, '8': 4, '4': 5, '2': 6, '10': 9, '7': 11, '6': 11},
               {'10': 0, '8': 3, '6': 10},
               {'11': 0, '8': 1, '6': 2, '10': 11}]
    for i in range(1, 12):
        assert dijkheap(WTEST, str(i)) == ANSWERS[i - 1]
    print("tests pass")
