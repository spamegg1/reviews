# -*- coding: utf-8 -*-
"""
Created on Fri Sep 13 11:19:07 2019.

@author: spamegg1
"""
from collections import deque
import graph as g


def dfs(graph, node, directed=False):
    """Take a graph and a node on graph as input.

    Return set of reachable nodes from given node (including itself).
    Works on both undirected and directed graphs (undirected by default).
    For a directed graph, set directed=True.
    Implements Depth First Search (DFS). It uses lists as stacks.
    """
    explored = {node}                                   # nodes explored so far
    edges = graph.get_node(node)                     # edges yet to be explored
    if directed:
        edges = edges[0]                           # OUTGOING edges if directed
    frontier = list(edges)                            # USING A LIST AS A STACK

    while frontier:       # continue while there are still edges to be explored
        newedge = frontier.pop()  # pop LAST edge in frontier, O(1) time (LIFO)
        if directed:            # follow the OUTGOING edge to its HEAD endpoint
            newnode = graph.get_edge(newedge)[1]
        else:                           # follow the edge to its OTHER endpoint
            node1, node2 = graph.get_edge(newedge)   # get edge's two endpoints
            newnode = node1 if node2 in explored else node2   # unexplored node

        if newnode not in explored:
            explored.add(newnode)            # mark the other endpoint explored
            newfrontier = graph.get_node(newnode)    # get new unexplored edges
            if directed:
                newfrontier = newfrontier[0]           # get new OUTGOING edges
            frontier += newfrontier       # extend frontier with new edges LIFO
    return explored


def toposort(dgraph, reverse=False):
    """Take a directed acyclic graph as input.

    Return a topological ordering of the graph.
    This is a dictionary of nodes and their topological sort values.
    """
    explored = {}    # dictionary of explored nodes with their topo sort values
    deck = deque()        # explored, but built up in order of topo sort values
    curlabel = len(dgraph.nodes)           # topo sort value to assign to nodes

    for node in dgraph.nodes:                   # visit each node at least once
        if node not in explored:              # subroutine will expand explored
            curlabel = dfs_topo(dgraph, node, explored, deck, curlabel, reverse)
    return explored, deck


def dfs_topo(dgraph, node, explored, deck, curlabel, reverse=False):
    """Take a directed acyclic graph and a node on the graph as input.

    Also take a dictionary of node: positive integer pairs, a deque, a positive
    integer and a Boolean as input.
    Assign topological sort values to nodes on graph (by updating explored).
    Add (node, value) pairs to deck in order sorted by value.
    (This is so that we don't have to resort explored in Kosaraju, which would
    have added an O(nlogn) overhead.)
    If reverse is set to True, use incoming edges rather than outgoing.
    Return updated value of curlabel.
    """
    explored[node] = 0  # mark node as explored
    edges = dgraph.get_node(node)[1] if reverse else dgraph.get_node(node)[0]
    for edge in edges:  # for each outgoing edge of node
        if reverse:
            newnode = dgraph.get_edge(edge)[0]  # follow incom edge to its tail
        else:
            newnode = dgraph.get_edge(edge)[1]  # follow outgn edge to its head
        if newnode not in explored:
            curlabel = dfs_topo(dgraph, newnode, explored, deck, curlabel, reverse)
    explored[node] = curlabel
    deck.appendleft((node, curlabel))
    return curlabel - 1


def ucc(graph):
    """Take an undirected graph as input.

    Return connected components of the graph.
    """
    explored = set()
    components = set()

    for node in graph.nodes:
        if node not in explored:
            component = dfs(graph, node)
            explored.union(component)
            components.add(frozenset(component))
    return components


def reachable(graph, node1, node2, directed=False):
    """Take a graph and two nodes on graph as input.

    Return True if node2 is reachable from node1.
    Works on both undirected and directed graphs (undirected by default).
    For a directed graph, set directed=True.
    """
    return node2 in dfs(graph, node1, directed)


# TESTING
if __name__ == "__main__":
    # testing an undirected graph for dfs, reachable
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
        assert dfs(UTEST, nod) == set(UNODES.keys())
    for nod1 in UNODES:
        for nod2 in UNODES:
            assert reachable(UTEST, nod1, nod2)

    # testing a directed graph for dfs, reachable, toposort
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
    TOPOSORTS = [{'w': 1, 's': 2, 'u': 3, 'v': 4},
                 {'s': 1, 'w': 2, 'u': 3, 'v': 4}]

    assert toposort(DTEST)[0] in TOPOSORTS
    assert dfs(DTEST, 's', directed=True) == {'s', 'u', 'v'}
    assert dfs(DTEST, 'u', directed=True) == {'u', 'v'}
    assert dfs(DTEST, 'v', directed=True) == {'v'}
    assert dfs(DTEST, 'w', directed=True) == {'w', 'u', 'v'}
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

    # these ones are only interesting for toposort
    DNODES2 = {'s': [{'e', 'f'}, {}],
               'v': [{'g'}, {'f'}],
               'w': [{'h'}, {'f'}],
               't': [{}, {'g', 'h'}]}
    DEDGES2 = {'e': ('s', 'v'),
               'f': ('s', 'w'),
               'g': ('v', 't'),
               'h': ('w', 't')}
    DTEST2 = g.Dgraph(graph=(DNODES2, DEDGES2))
    TOPOSORTS2 = [{'s': 1, 'v': 2, 'w': 3, 't': 4},
                  {'s': 1, 'w': 2, 'v': 3, 't': 4}]
    assert toposort(DTEST2)[0] in TOPOSORTS2

    DNODES3 = {'s': [{'e', 'f', 'a', 'b'}, {}],
               'v': [{'g'}, {'f'}],
               'w': [{'h'}, {'f'}],
               't': [{}, {'g', 'h'}],
               'x': [{'c'}, {'a'}],
               'y': [{'d'}, {'b'}],
               'z': [{}, {'c', 'd'}]}
    DEDGES3 = {'e': ('s', 'v'), 'f': ('s', 'w'), 'g': ('v', 't'),
               'h': ('w', 't'), 'a': ('s', 'x'), 'b': ('s', 'y'),
               'c': ('x', 'z'), 'd': ('y', 'z')}
    DTEST3 = g.Dgraph(graph=(DNODES3, DEDGES3))
    TOPOSORTS3 = [{'s': 1, 'v': 2, 'w': 3, 't': 4, 'x': 5, 'y': 6, 'z': 7},
                  {'s': 1, 'v': 2, 'w': 3, 't': 4, 'x': 5, 'y': 6, 'z': 7},
                  {'s': 1, 'v': 3, 'w': 2, 't': 4, 'x': 6, 'y': 5, 'z': 7},
                  {'s': 1, 'v': 3, 'w': 2, 't': 4, 'x': 6, 'y': 5, 'z': 7},
                  {'s': 1, 'v': 5, 'w': 6, 't': 7, 'x': 2, 'y': 3, 'z': 4},
                  {'s': 1, 'v': 5, 'w': 6, 't': 7, 'x': 2, 'y': 3, 'z': 4},
                  {'s': 1, 'v': 6, 'w': 5, 't': 7, 'x': 3, 'y': 2, 'z': 4},
                  {'s': 1, 'v': 6, 'w': 5, 't': 7, 'x': 3, 'y': 2, 'z': 4}]
    assert toposort(DTEST3)[0] in TOPOSORTS3

    # testing DFS on a non-connected graph
    GRAPH = g.edge_to_graph('ucctest2.txt')
    COMPONENTS = [{'1', '3', '5', '7', '9'},
                  {'2', '4'},
                  {'6', '8', '10'},
                  {'11', '13', '15', '17'},
                  {'12', '14', '16', '18'},
                  {'19', '20', '21', '22'}]
    for comp in COMPONENTS:
        for nod in comp:
            assert dfs(GRAPH, nod) == comp
    assert ucc(GRAPH) == {frozenset({'1', '3', '5', '7', '9'}),
                          frozenset({'2', '4'}),
                          frozenset({'6', '8', '10'}),
                          frozenset({'11', '13', '15', '17'}),
                          frozenset({'12', '14', '16', '18'}),
                          frozenset({'19', '20', '21', '22'})}

    # this is the 200 node connected graph from Programming Assignment 3
    # takes long (1 min), so comment out if needed
    # GRAPH = g.node_to_graph('kargerMinCut.txt')
    # NODES = set(GRAPH.nodes.keys())
    # for nod1 in NODES:
    #     assert dfs(GRAPH, nod1) == NODES

    print("tests pass")
