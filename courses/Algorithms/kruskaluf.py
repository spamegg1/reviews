# -*- coding: utf-8 -*-
"""
Created on Thu Oct 17 14:51:02 2019.

@author: spamegg1
"""
from operator import itemgetter
import graph as g
import unionfind as uf


def kruskaluf(uwgraph):
    """Take undirected weighted connected graph as input.

    Return minimum spanning tree. Runs in O(mlogn) time.
    """
    ufnodes = uf.UnionFind(list(uwgraph.nodes))
    sortededges = sorted(uwgraph.edges.values(), key=itemgetter(1))

    graphsize = len(uwgraph.nodes)
    treesize = 0
    tree = {}

    for edge, cost in sortededges:
        node1, node2 = edge
        if ufnodes.find(node1) != ufnodes.find(node2):
            tree[(node1, node2)] = cost
            ufnodes.union_by_rank(node1, node2)
            treesize += 1
        if treesize == graphsize - 1:
            break

    return tree


# TESTING
if __name__ == "__main__":
    UWGRAPH = g.node_to_uwgraph('uwdijktest.txt')
    MST = {('3', '1'): 1, ('11', '8'): 1, ('4', '2'): 2, ('11', '6'): 2,
           ('10', '2'): 3, ('10', '8'): 3, ('5', '3'): 4, ('9', '8'): 4,
           ('11', '3'): 5, ('7', '4'): 6}
    print(kruskaluf(UWGRAPH))  # should look like MST maybe with nodes switched
    # assert kruskaluf(UWGRAPH) == MST

    UWGRAPH = g.edge_to_uwgraph('problem15.9test.txt')
    assert sum(kruskaluf(UWGRAPH).values()) == 14

    # CHALLENGE SET! 2184 EDGES!
    UWGRAPH = g.edge_to_uwgraph('problem15.9.txt')
    assert sum(kruskaluf(UWGRAPH).values()) == -3612829
    print("Tests pass.")
