# -*- coding: utf-8 -*-
"""
Created on Thu Sep 12 15:34:40 2019.

@author: spamegg1
"""
import graph as g


def generic_search(graph, node, directed=False):
    """Take a graph and a node on graph as input.

    Return set of reachable nodes from given node (including itself).
    Works on both undirected and directed graphs (undirected by default).
    For a directed graph, set directed=True.
    Implements Breadth First Search (BFS). However it uses lists, not queues.
    In Python, popping first element of a list is slow (O(n)).
    """
    explored = {node}  # nodes explored so far
    edges = graph.get_node(node)  # edges yet to be explored
    if directed:
        edges = edges[0]  # (OUTGOING edges if directed)
    frontier = list(edges)

    while frontier:  # continue while there are still edges to be explored
        newedge = frontier.pop(0)  # get next edge in frontier, remove it
        if directed:  # follow the OUTGOING edge to its TAIL endpoint
            newnode = graph.get_edge(newedge)[1]
        else:  # follow the edge to its OTHER endpoint
            node1, node2 = graph.get_edge(newedge)  # get edge's two endpoints
            newnode = node1 if node2 in explored else node2

        if newnode not in explored:
            explored.add(newnode)  # mark the other endpoint explored
            # expand frontier with edges from newnode to other nodes
            newfrontier = graph.get_node(newnode)  # get new edges
            if directed:
                newfrontier = newfrontier[0]  # get new OUTGOING edges
            for newfront in newfrontier:  # loop through newnode's edges
                if newfront not in frontier:  # check if they are in frontier
                    frontier.append(newfront)  # if not, add to frontier
    return explored


def reachable(graph, node1, node2, directed=False):
    """Take a graph and two nodes on graph as input.

    Return True if node2 is reachable from node1.
    Works on both undirected and directed graphs (undirected by default).
    For a directed graph, set directed=True.
    """
    return node2 in generic_search(graph, node1, directed)


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

    for nod in UNODES:
        assert generic_search(UTEST, nod) == set(UNODES.keys())

    for nod1 in UNODES:
        for nod2 in UNODES:
            assert reachable(UTEST, nod1, nod2)

    DNODES = {'s': [{'e', 'f'}, {}],
              'u': [{'i'}, {'e', 'g'}],
              'v': [{}, {'f', 'i', 'h'}],
              'w': [{'g', 'h'}, {}]}
    DEDGES = {'e': ('s', 'u'),
              'f': ('s', 'v'),
              'g': ('w', 'u'),
              'h': ('w', 'v'),
              'i': ('u', 'v')}
    DTEST = g.Dgraph(graph=(DNODES, DEDGES))

    assert generic_search(DTEST, 's', directed=True) == {'s', 'u', 'v'}
    assert generic_search(DTEST, 'u', directed=True) == {'u', 'v'}
    assert generic_search(DTEST, 'v', directed=True) == {'v'}
    assert generic_search(DTEST, 'w', directed=True) == {'w', 'u', 'v'}

    assert reachable(DTEST, 's', 'u', directed=True)
    assert reachable(DTEST, 's', 'v', directed=True)
    assert reachable(DTEST, 'u', 'v', directed=True)
    assert reachable(DTEST, 'w', 'u', directed=True)
    assert reachable(DTEST, 'w', 'v', directed=True)
    assert reachable(DTEST, 's', 'w', directed=True) is False
    assert reachable(DTEST, 'u', 's', directed=True) is False
    assert reachable(DTEST, 'u', 'w', directed=True) is False
    assert reachable(DTEST, 'v', 's', directed=True) is False
    assert reachable(DTEST, 'v', 'u', directed=True) is False
    assert reachable(DTEST, 'v', 'w', directed=True) is False
    assert reachable(DTEST, 'w', 's', directed=True) is False

    # this is the 200 node connected graph from Programming Assignment 3
    GRAPH = g.node_to_graph('kargerMinCut.txt')
    NODES = set(GRAPH.nodes.keys())
    for nod1 in NODES:  # THIS IS SLOW!!! (because of O(n) time in pop(0))
        assert generic_search(GRAPH, nod1) == NODES

    print("tests pass")
