# -*- coding: utf-8 -*-
"""
Created on Mon Nov 18 18:07:03 2019.

@author: spamegg1
"""
import graph as g
import dijkheap as d
import bellmanford as b


def johnsonize(wdgraph):
    """Take weighted directed graph as input.

    :param wdgraph: will get mutated!
    :return: same graph with one more node outgoing to all others with cost 0.
    """
    nodes = list(wdgraph.nodes.keys())
    wdgraph.add_node('new')
    for node in nodes:
        edgelabel = 'new_' + node
        wdgraph.add_edge(edgelabel, 'new', node, 0)
    return wdgraph


def positivize(jwdgraph):
    """Take johnsonized's output on a weighted directed graph as input.

    :param jwdgraph: output of johnsonize(wdgraph).
    :return: original wdgraph with nonnegative edge costs, same path lengths.
    """
    shortestpaths = b.bellmanford(jwdgraph, 'new')
    jwdgraph.rem_node('new')
    for edge in jwdgraph.edges:
        tail, head, cost = jwdgraph.get_edge(edge)
        tailpath, headpath = shortestpaths[tail], shortestpaths[head]
        newcost = cost + tailpath - headpath
        jwdgraph.edges[edge] = (tail, head, newcost)
    return jwdgraph, shortestpaths


def negativize(pjwdgraph, shortestpaths):
    """Take output of positivize on a weighted directed graph as input.

    :param pjwdgraph:
    :param shortestpaths:
    :return: shortest path distances in the original wdgraph.
    """
    results = []
    for node in pjwdgraph.nodes:
        result = d.dijkheap(pjwdgraph, node)
        for other in result:
            nodepath, otherpath = shortestpaths[node], shortestpaths[other]
            result[other] += -nodepath + otherpath
        results.append(result)
    return results


def johnson(wdgraph):
    """Take weighted directed graph with no negative cycles as input.

    :param wdgraph:
    :return: shortest path distances for all pairs of nodes in graph.
    """
    johnsonized = johnsonize(wdgraph)
    positivized, shortestpaths = positivize(johnsonized)
    return negativize(positivized, shortestpaths)


# TESTING
if __name__ == '__main__':
    WDNODES = {'1': [{'1_2', '1_3'}, {'4_1'}],
               '2': [{'2_3'}, {'1_2'}],
               '3': [{'3_4'}, {'2_3', '1_3'}],
               '4': [{'4_1'}, {'3_4'}]}
    WDEDGES = {'1_2': ('1', '2', -2),  # 2
               '2_3': ('2', '3', 1),  # 1
               '3_4': ('3', '4', -3),  # 3
               '4_1': ('4', '1', 4),  # 4
               '1_3': ('1', '3', 5)}  # 5
    WDGRAPH = g.Wgraph(graph=(WDNODES, WDEDGES))
    # testing johnsonize
    JWDGRAPH = johnsonize(WDGRAPH)
    print(JWDGRAPH)
    # testing positivize
    PJWDGRAPH, _ = positivize(JWDGRAPH)
    print(PJWDGRAPH)

    # testing johnson
    WDNODES = {'1': [{'1_2', '1_3'}, {'4_1'}],
               '2': [{'2_3'}, {'1_2'}],
               '3': [{'3_4'}, {'2_3', '1_3'}],
               '4': [{'4_1'}, {'3_4'}]}
    WDEDGES = {'1_2': ('1', '2', -2),  # 2
               '2_3': ('2', '3', 1),  # 1
               '3_4': ('3', '4', 3),  # 3
               '4_1': ('4', '1', -4),  # 4
               '1_3': ('1', '3', 5)}  # 5
    WDGRAPH = g.Wgraph(graph=(WDNODES, WDEDGES))
    print(johnson(WDGRAPH))

    # Test case provided by algorithmsilluminated.org
    WDGRAPH = g.edge_to_dwgraph('problem18.8test1.txt')
    print(johnson(WDGRAPH))  # shortest distance = -2

    print("Tests pass.")
