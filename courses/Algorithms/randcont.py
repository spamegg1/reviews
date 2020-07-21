# -*- coding: utf-8 -*-
"""
Created on Thu Sep 12 15:49:40 2019.

@author: spamegg1
"""
import random
from copy import deepcopy
import graph as g


def mergenodes(grph, node1, node2):
    """Take an undirected graph and two nodes on the graph as input.

    Merge node1 and node2 into a single node (node1).
    To do this, replace all occurences of node2 in graph with node1.
    Then delete node2 entry in node dictionary.
    Mutates graph (very badly and deeply), does not return.
    """
    # Ex: say we are merging 's' with 'v'. So node1 = 's', node2 = 'v'.
    # merge nodes in nodes dictionary. We can safely delete node2
    del grph.nodes[node2]

    # merge nodes in edges dictionary. Say we replace 'v' with 's',
    # and edge dict has entry: 'e': {'u', 'v'}
    for edge in grph.edges:  # 'e' in graph.edges
        nodes = grph.edges[edge]  # nodes = {'u', 'v'}
        if node2 in nodes:  # 'v' in {'u', 'v'}
            nodes.remove(node2)  # 'e': {'u'}
            nodes.add(node1)  # 'e': {'u', 's'}


def remloop(grph, node):
    """Take an undirected graph and a node on the graph as input.

    Remove self-loops around node, if any exists.
    Mutates graph (very badly and deeply), does not return.
    """
    # delete self loops from node dictionary. Example: say node = 's'

    # delete self loops from edge dictionary. Say node = 's',
    # and edge dict is {'e': {'u', 's'}, 'f': {'s', 's'}}
    edges = list(grph.edges.keys())
    for edge in edges:  # 'f' in grph.edges
        if grph.get_edge(edge) == {node}:  # 'f': {'s', 's'} is a self-loop
            del grph.edges[edge]  # delete that edge from dictionary


def randcont(grph):
    """Take an undirected connected graph as input (parallel edges allowed).

    Return a minimum cut of graph (a cut with fewest crossing edges).
    Randomly select an edge. Consider its endpoint vertices u and v.
    'Contract' the edge, and merge u and v into a single vertex
    (this might cause parallel edges).
    Then remove self-loops if necessary.
    Keep repeating until there are two vertices left.
    Return cut represented by the final two vertices.

    Example: suppose we have the undirected graph with edge dictionary
        {'e': {'u', 's'},
         'f': {'v', 's'},
         'g': {'u', 'w'},
         'h': {'w', 'v'},
         'i': {'u', 'v'}}
    Say randomly from the edges, 'f' is chosen for contraction.
    So 's' and 'v' merge into a single vertex. Let's choose to use 's'.
    Edge dictionary becomes
        {'e': {'u', 's'},
         'g': {'u', 'w'},
         'h': {'w', 's'},
         'i': {'u', 's'}}
    Now there are two parallel edges between 'u' and 's', namely 'e' and 'i'.
    Say next 'e' is randomly chosen for contraction.
    This time 's' and 'u' are merged into a single vertex. Let's use 's'.
    'e' was one of the two parallel edges between 's' and 'u', now it's gone.
    Now 'i' becomes a self-loop around 's'. So we have
        {'g': {'u', 'w'},
         'h': {'w', 's'},
         'i': {'s', 's'}}  <-- self-loop
    We also remove the self-loop as part of this contraction step:
        {'g': {'u', 'w'},
         'h': {'w', 's'}}
    This means that a minimum cut of the original graph is made of 2 crossing
    edges {'g', 'h'}, separating the graph into a 3-node group {'s', 'u', 'v'}
    and a 1-node group {'w'}.

    HOWEVER!
    Depending on the random choice of the edge to be contracted, sometimes
    the algorithm will fail to find a minimum cut. In the above example it
    might find a cut with 3 crossing edges.

    What is the probability that this algorithm will find a minimum cut?
    It is greater than 1/n^2 where n is the number of nodes.
    Probability of all N trials failing is <= (1-1/n^2)^N.
    If we perform N = n^2*log(n) trials, failure chance is <= 1/n.
    """
    # make a deepcopy of graph because we will mutate it very very badly
    nodes = deepcopy(grph.nodes)
    edges = deepcopy(grph.edges)
    copy = g.Ugraph(graph=(nodes, edges))
    while len(copy.nodes) > 2:
        # randomly pick edge to be contracted
        edge, vertices = random.choice(list(copy.edges.items()))
        node1, node2 = vertices
        # contract (remove) edge
        del copy.edges[edge]
        # merge endpoints of contracted edge into one vertex
        mergenodes(copy, node1, node2)
        # remove any self-loops that happened as a result of the merge
        remloop(copy, node1)

    return copy.edges  # return the edges that comprise a cut


def mincutsize(grph):
    """Take undirected connected graph as input.

    Return size of minimum cut as found by Randomized Contraction algorithm.
    """
    size = len(grph.nodes)  # number of vertices, or n
    trials = size  # number of times we run randcont, INCREASE IF NEEDED!
    minsofar = size**2  # start with a very large min

    for _ in range(trials):
        result = len(randcont(grph))
        minsofar = min(result, minsofar)
    return minsofar


# TESTING
if __name__ == "__main__":
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

    # all the possible cuts of the above graph (due to randomness)
    POSSIBLE = [{'f', 'i', 'h'}, {'e', 'g', 'i'}, {'g', 'h'}, {'e', 'f'},
                {'e', 'i', 'h'}, {'f', 'g', 'i'}]

    for j in range(1000):  # test it a lot, due to randomness
        assert set(randcont(UTEST).keys()) in POSSIBLE

    # INCREASE trials IF TESTS ARE FAILING!
    FILES = [('kargertest.txt', 3),
             ('kargertest2.txt', 2),
             ('kargertest3.txt', 2),
             ('kargertest4.txt', 1)]
    for file, answer in FILES:
        graph = g.node_to_graph(file)
        assert mincutsize(graph) == answer

    # this is the programming assignment 3
    GRAPH = g.node_to_graph('kargerMinCut.txt')
    assert mincutsize(GRAPH) == 17

    print("tests pass")
