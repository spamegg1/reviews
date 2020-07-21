# -*- coding: utf-8 -*-
"""
Created on Thu Oct 17 14:51:01 2019.

@author: spamegg1
"""
from operator import itemgetter
from collections import deque
import graph as g


def kruskal(uwgraph):
    """Take undirected weighted connected graph as input.

    Return minimum spanning tree. Runs in O(mn) time.
    """
    graphsize = len(uwgraph.nodes)
    tree = {}
    treesize = 0
    sortededges = sorted(uwgraph.edges.values(), key=itemgetter(1))

    for edge, cost in sortededges:
        node1, node2 = edge
        if not path(tree, node1, node2):
            tree[(node1, node2)] = cost
            treesize += 1
        if treesize == graphsize - 1:
            break
    return tree


def path(tree, node1, node2):
    """Take a tree and two nodes as input.

    Return True if there is a path between nodes through tree, False otherwise.
    Tree has form: {('1', '3'): 5, ('11', '8'): 2, ...} etc.
    """
    explorededges = []
    explorednodes = [node1]

    frontier = deque()
    for edge in tree:
        if node1 in edge:
            frontier.append(edge)

    while frontier:
        edge = frontier.popleft()
        first, second = edge
        if first in explorednodes:
            newnode = second
        elif second in explorednodes:
            newnode = first

        if newnode == node2:
            return True

        explorededges.append(edge)
        explorednodes.append(newnode)

        for edge in tree:
            if edge not in explorededges and newnode in edge:
                frontier.append(edge)

    return False


# TESTING
if __name__ == "__main__":
    # testing path
    TREE = {('1', '3'): 1, ('5', '1'): 7, ('2', '4'): 2, ('2', '9'): 6}
    # trivially true:
    assert path(TREE, '1', '3')
    assert path(TREE, '3', '1')
    assert path(TREE, '1', '5')
    assert path(TREE, '5', '1')
    assert path(TREE, '2', '4')
    assert path(TREE, '4', '2')
    assert path(TREE, '2', '9')
    assert path(TREE, '9', '2')
    # nontrivially true:
    assert path(TREE, '5', '3')
    assert path(TREE, '3', '5')
    assert path(TREE, '4', '9')
    assert path(TREE, '9', '4')
    # false:
    assert path(TREE, '1', '2') is False
    assert path(TREE, '2', '1') is False
    assert path(TREE, '1', '4') is False
    assert path(TREE, '4', '1') is False
    assert path(TREE, '1', '9') is False
    assert path(TREE, '9', '1') is False
    assert path(TREE, '3', '2') is False
    assert path(TREE, '2', '3') is False
    assert path(TREE, '3', '4') is False
    assert path(TREE, '4', '3') is False
    assert path(TREE, '3', '9') is False
    assert path(TREE, '9', '3') is False
    assert path(TREE, '5', '2') is False
    assert path(TREE, '2', '5') is False
    assert path(TREE, '5', '4') is False
    assert path(TREE, '4', '5') is False
    assert path(TREE, '5', '9') is False
    assert path(TREE, '9', '5') is False

    UWGRAPH = g.node_to_uwgraph('uwdijktest.txt')
    MST = {('1', '3'): 1, ('11', '8'): 1, ('2', '4'): 2, ('6', '11'): 2,
           ('10', '8'): 3, ('2', '10'): 3, ('5', '3'): 4, ('9', '8'): 4,
           ('11', '3'): 5, ('4', '7'): 6}
    assert kruskal(UWGRAPH) == MST

    UWGRAPH = g.edge_to_uwgraph('problem15.9test.txt')
    assert sum(kruskal(UWGRAPH).values()) == 14

    # CHALLENGE SET! 2184 EDGES! TAKES A LONG TIME!
    UWGRAPH = g.edge_to_uwgraph('problem15.9.txt')
    assert sum(kruskal(UWGRAPH).values()) == -3612829
    print("tests pass")
