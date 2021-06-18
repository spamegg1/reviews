# -*- coding: utf-8 -*-
"""
Created on Mon Sep 16 18:37:24 2019.

@author: spamegg1
"""
import random
from collections import deque
import graph as g


def prim(uwgraph):
    """Take an undirected, (nonnegative) weighted, connected graph as input.

    Return the edges of a minimum spanning tree (MST) for the graph.
    """
    graph_size = len(uwgraph.nodes)
    node = random.choice(list(uwgraph.nodes.keys()))
    explored = {node}
    edges_seen = {}
    frontier = deque(uwgraph.get_node(node))          # edges yet to be explored
    winner, least = node, -1      # choose node and path that minimizes distance
    new_node = None

    while frontier:
        best_edge = None
        for edge in frontier:    # consider each edge in frontier, find shortest
            node1, node2 = uwgraph.get_edge(edge)[0]
            if node1 in explored:
                new_node = node2
            elif node2 in explored:
                new_node = node1
            cost = uwgraph.get_edge(edge)[1]

            # if this is the first edge we look at, or gives us a shorter path
            if least < 0 or cost < least:
                winner = new_node
                least = cost
                best_edge = edge

        explored.add(winner)
        edges_seen[best_edge] = least
        if len(explored) == graph_size:
            break
        frontier.remove(best_edge)
        least = -1                     # reset shortest path cost for next round

        # because explored increased, some frontier edges must be removed
        # iterate over list of frontier, so that we can safely mutate deque
        for edge in list(frontier):
            node1, node2 = uwgraph.get_edge(edge)[0]
            # both end points cannot be in explored simultaneously
            if node1 in explored and node2 in explored:
                frontier.remove(edge)

        # consider edges of winner, filter edges that were already seen,
        # are already in frontier, or have both endpoints in explored.
        for edge in uwgraph.get_node(winner):
            if edge not in edges_seen and edge not in frontier:
                node1, node2 = uwgraph.get_edge(edge)[0]
                if not (node1 in explored and node2 in explored):
                    frontier.append(edge)

    return edges_seen


# TESTING
if __name__ == "__main__":
    UWGRAPH = g.node_to_uwgraph('uwdijktest.txt')
    MST = {'1_3': 1, '3_5': 4, '3_11': 5, '8_11': 1, '6_11': 2, '8_10': 3,
           '2_10': 3, '2_4': 2, '8_9': 4, '4_7': 6}
    assert prim(UWGRAPH) == MST

    UWGRAPH = g.edge_to_uwgraph('problem15.9test.txt')
    assert sum(prim(UWGRAPH).values()) == 14

    # CHALLENGE SET! 2184 EDGES! MST IS NOT UNIQUE!
    UWGRAPH = g.edge_to_uwgraph('problem15.9.txt')
    print(sum(prim(UWGRAPH).values()))  # DIFFERENT ANSWER EVERY TIME

    print("Tests pass.")
