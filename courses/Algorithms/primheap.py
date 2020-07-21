# -*- coding: utf-8 -*-
"""
Created on Sat Oct 11 08:55:40 2019.

@author: spamegg1
"""
import random
import heapdict as h
import graph as g


def primheap(uwgraph):
    """Take an undirected, (nonnegative) weighted, connected graph as input.

    Return the edges of a minimum spanning tree (MST) for the graph.
    Uses heap dictionary to achieve running time of O((m+n)logn).
    """
    start = random.choice(list(uwgraph.nodes.keys()))
    explored = set()
    frontier = h.HeapDict()                 # dict of node: (cost, edge) pairs.
    edges = {}
    spanning = {}

    for node in uwgraph.nodes:
        frontier[node] = 0 if node == start else float("inf")
        edges[node] = None

    while frontier:
        node, cost = frontier.popitem()
        edge = edges[node]
        explored.add(node)
        if node != start:
            spanning[edge] = cost

        newnode = None
        for edge in uwgraph.get_node(node):
            nodes, cost = uwgraph.get_edge(edge)
            node1, node2 = nodes
            if node1 in explored:
                newnode = node2
            elif node2 in explored:
                newnode = node1
            if newnode not in explored:  # otherwise newnode was popped already
                oldcost = frontier[newnode]
                if cost < oldcost:
                    frontier[newnode] = cost
                    edges[newnode] = edge

    return spanning


# TESTING
if __name__ == "__main__":
    UWGRAPH = g.node_to_uwgraph('uwdijktest.txt')
    MST = {'1_3': 1, '3_5': 4, '3_11': 5, '8_11': 1, '6_11': 2, '8_10': 3,
           '2_10': 3, '2_4': 2, '8_9': 4, '4_7': 6}
    assert primheap(UWGRAPH) == MST

    UWGRAPH = g.edge_to_uwgraph('problem15.9test.txt')
    assert sum(primheap(UWGRAPH).values()) == 14

    # CHALLENGE SET! 2184 EDGES!
    UWGRAPH = g.edge_to_uwgraph('problem15.9.txt')
    assert sum(primheap(UWGRAPH).values()) == -3612829

    print("Tests pass.")
