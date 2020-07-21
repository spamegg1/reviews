# -*- coding: utf-8 -*-
"""
Created on Fri Sep 13 11:19:06 2019.

@author: spamegg1
"""
from collections import deque
import graph as g


# this is the Augmented BFS that also finds shortest path lengths
def bfs(graph, node, directed=False):
    """Take a graph and a node on the graph as input.

    Return set of nodes reachable from node, with their shortest path lengths.
    Works on both undirected and directed graphs (undirected by default).
    For a directed graph, set directed=True.
    Implements Breadth First Search (BFS) using a queue structure.
    Unlike lists, deques in Python are efficient at popping leftmost element.
    """
    explored = {node: 0}  # nodes explored sofar and their shortest path length
    edges = graph.get_node(node)                     # edges yet to be explored
    if directed:
        edges = edges[0]                           # OUTGOING edges if directed
    frontier = deque(edges)         # create FIFO queue out of unexplored edges
    prevnode, dist = node, 0   # node we came from and its shortest path length

    while frontier:       # continue while there are still edges to be explored
        newedge = frontier.popleft()       # pop FIRST edge in O(1) time (FIFO)
        if directed:                                 # we have a directed graph
            newnode = graph.get_edge(newedge)[1]        # HEAD of OUTGOING edge
            prevnode = graph.get_edge(newedge)[0]       # TAIL of OUTGOING edge
        else:                                     # we have an undirected graph
            node1, node2 = graph.get_edge(newedge)   # get endpoints of newedge
            newnode = node1 if node2 in explored else node2   # unexplored node
            prevnode = node1 if newnode == node2 else node2  # nod we came from
        dist = explored[prevnode]     # distance of prev node from initial node

        if newnode not in explored:
            explored[newnode] = dist + 1     # mark the other endpoint explored
            newfrontier = graph.get_node(newnode)    # get new unexplored edges
            if directed:
                newfrontier = newfrontier[0]       # OUTGOING edges if directed
            frontier.extend(newfrontier)  # extend frontier with new edges LILO
    return explored   # dict of reachable nodes and their shortest path lengths


def ucc(graph):
    """Take an undirected graph as input.

    Return connected components of the graph.
    """
    explored = set()
    components = set()

    for node in graph.nodes:
        if node not in explored:
            component = set(bfs(graph, node).keys())
            explored.union(component)
            components.add(frozenset(component))
    return components


def reachable(graph, node1, node2, directed=False):
    """Take a graph and two nodes on graph as input.

    Return True if node2 is reachable from node1.
    Works on both undirected and directed graphs (undirected by default).
    For a directed graph, set directed=True.
    """
    return node2 in bfs(graph, node1, directed)


# TESTING
if __name__ == "__main__":
    # testing an undirected graph
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

    # UTEST is connected, so every node is reachable from every other node
    for nod in UNODES:
        assert set(bfs(UTEST, nod).keys()) == set(UNODES.keys())
    for nod1 in UNODES:
        for nod2 in UNODES:
            assert reachable(UTEST, nod1, nod2)

    # test some shortest path lengths
    assert bfs(UTEST, 's')['u'] == 1
    assert bfs(UTEST, 's')['v'] == 1
    assert bfs(UTEST, 's')['w'] == 2
    assert bfs(UTEST, 'u')['v'] == 1
    assert bfs(UTEST, 'u')['w'] == 1
    assert bfs(UTEST, 'v')['w'] == 1

    # testing a directed graph
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

    # not every node is reachable from every other (because of directed graph)
    assert set(bfs(DTEST, 's', directed=True).keys()) == {'s', 'u', 'v'}
    assert set(bfs(DTEST, 'u', directed=True).keys()) == {'u', 'v'}
    assert set(bfs(DTEST, 'v', directed=True).keys()) == {'v'}
    assert set(bfs(DTEST, 'w', directed=True).keys()) == {'w', 'u', 'v'}
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

    # test some shortest path lengths
    assert bfs(UTEST, 's')['u'] == 1
    assert bfs(UTEST, 's')['v'] == 1
    assert bfs(UTEST, 'u')['v'] == 1
    assert bfs(UTEST, 'w')['u'] == 1
    assert bfs(UTEST, 'w')['v'] == 1

    # the undirected graph versions of below files are connected
    FILES = ['kargertest.txt', 'kargertest2.txt',
             'kargertest3.txt', 'kargertest4.txt']
    for file in FILES:
        grph = g.node_to_graph(file)  # undirected graphs
        nodes = grph.nodes
        for nod in nodes:
            assert set(bfs(grph, nod).keys()) == set(nodes)

    # test some shortest path lengths
    GRAPH = g.node_to_graph('kargertest3.txt')
    EXPLORED = bfs(GRAPH, '1')
    assert EXPLORED['6'] == 2
    assert EXPLORED['7'] == 2
    assert EXPLORED['8'] == 3
    assert EXPLORED['9'] == 3
    assert EXPLORED['10'] == 3
    assert bfs(GRAPH, '6')['1'] == 2
    assert bfs(GRAPH, '7')['1'] == 2
    assert bfs(GRAPH, '8')['1'] == 3
    assert bfs(GRAPH, '9')['1'] == 3
    assert bfs(GRAPH, '10')['1'] == 3

    # this is the 200 node connected graph from Programming Assignment 3
    GRAPH = g.node_to_graph('kargerMinCut.txt')
    NODES = set(GRAPH.nodes.keys())
    for nod1 in NODES:
        assert set(bfs(GRAPH, nod1).keys()) == NODES

    # testing UCC (undirected connected components)
    GRAPH = g.edge_to_graph('ucctest.txt')
    assert ucc(GRAPH) == {frozenset({'1', '3', '5', '7', '9'}),
                          frozenset({'2', '4'}),
                          frozenset({'6', '8', '10'})}
    GRAPH = g.edge_to_graph('ucctest2.txt')
    assert ucc(GRAPH) == {frozenset({'1', '3', '5', '7', '9'}),
                          frozenset({'2', '4'}),
                          frozenset({'6', '8', '10'}),
                          frozenset({'11', '13', '15', '17'}),
                          frozenset({'12', '14', '16', '18'}),
                          frozenset({'19', '20', '21', '22'})}

    print("tests pass")
