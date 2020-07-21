# -*- coding: utf-8 -*-
"""
Created on Mon Oct 21 16:29:02 2019.

@author: spamegg1
"""
from operator import itemgetter
import graph as g
import unionfind as uf


def cluster(uwgraph, number_of_clusters):
    """Take undirected weighted connected graph as input.

    Return maximum spacing of a k-clustering (where k=number_of_clusters).
    """
    ufnodes = uf.UnionFind(list(uwgraph.nodes))
    sortededges = sorted(uwgraph.edges.values(), key=itemgetter(1))

    graphsize = len(uwgraph.nodes)
    treesize = 0

    for edge, cost in sortededges:
        node1, node2 = edge
        if ufnodes.find(node1) != ufnodes.find(node2):
            ufnodes.union_by_rank(node1, node2)
            treesize += 1
        # Let k = number_of_clusters.
        # When treesize = graphsize - k, k clusters are already formed.
        # Max spacing between these k clusters is the cost of the edge
        # that WOULD have been added NEXT by Kruskal if it continued.
        # In other words, max spacing =
        # cost of (graphsize - number_of_clusters + 1)st edge added by Kruskal.
        if treesize == graphsize - number_of_clusters + 1:
            return cost


# TESTING
if __name__ == "__main__":
    UWGRAPH = g.edge_to_uwgraph('clustering1.txt')
    print(cluster(UWGRAPH, 4))
    print("Tests pass.")
