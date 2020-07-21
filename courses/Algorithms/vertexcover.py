# -*- coding: utf-8 -*-
"""
Created on Tue Nov 19 18:07:03 2019.

@author: spamegg1
"""
import graph as g
from copy import deepcopy


def vertexcover(ugraph, coversize):
    """Take undirected graph with at least 2 nodes and integer >= 2 as input.

    :param ugraph: undirected graph with at least 2 nodes.
    :param coversize: integer >= 2.
    :return: vertex cover of given graph of given cover size, or None.
    """
    # base case:
    if coversize == 1:
        # check if the graph is a star graph
        if len(ugraph.edges) == len(ugraph.nodes) - 1:
            for node in ugraph.nodes:
                edges = ugraph.get_node(node)
                if len(edges) == len(ugraph.nodes) - 1:
                    return set(node)
        return None

    # non-base case
    else:
        if not ugraph.edges:
            return set()
        edge = list(ugraph.edges)[0]
        node1, node2 = ugraph.get_edge(edge)
        nodes1, edges1 = deepcopy(ugraph.nodes), deepcopy(ugraph.edges)
        ugraph1 = g.Ugraph(graph=(nodes1, edges1))
        ugraph1.rem_node(node1)
        first = vertexcover(ugraph1, coversize - 1)

        if first:
            first.add(node1)
            return first
        else:
            del nodes1
            del edges1
            del ugraph1
            nodes2, edges2 = deepcopy(ugraph.nodes), deepcopy(ugraph.edges)
            ugraph2 = g.Ugraph(graph=(nodes2, edges2))
            ugraph2.rem_node(node2)
            second = vertexcover(ugraph2, coversize - 1)
            if second:
                second.add(node2)
                return second
            del nodes2
            del edges2
            del ugraph2
            return None


# TESTING
if __name__ == '__main__':
    UNODES = {'s': {'e', 'f'},
              'u': {'e', 'g', 'i'},
              'v': {'f', 'i', 'h'},
              'w': {'g', 'h'}}
    UEDGES = {'e': {'u', 's'},
              'f': {'v', 's'},
              'g': {'u', 'w'},
              'h': {'w', 'v'},
              'i': {'u', 'v'}}
    UTEST = g.Ugraph(graph=(UNODES, UEDGES))
    for COVERSIZE in range(2, 5):
        print(vertexcover(UTEST, COVERSIZE))
    print("Tests pass.")
